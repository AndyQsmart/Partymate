package com.android.partymate;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.android.proto.Partymate;
import com.android.proto.Partymate.CSNotifyGetAllParty;
import com.android.proto.Partymate.CSNotifyGetMyParty;
import com.android.proto.Partymate.CSNotifyParty;
import com.android.proto.ProtoTool;
import com.android.util.Logger;
import com.android.util.PartyInfo;
import com.android.util.SocketBuffer;
import com.android.util.SocketEvent;
import com.android.util.SocketEvent.EventType;
import com.google.protobuf.ByteString;

import android.R.bool;
import android.accounts.NetworkErrorException;
import android.nfc.tech.IsoDep;
import android.os.Debug;
import android.text.LoginFilter;

public class NetworkSystem implements Runnable
{
	protected static final String IP_ADDRESS = "10.135.33.23";
	//protected static final String IP_ADDRESS = "127.0.0.1";
	protected AppSystem _app_system = null;
	protected static final int IP_PORT = 4700;
	protected boolean _is_connected = false;
	protected Socket _socket = null;
	protected SocketBuffer _read_buffer = null;
	protected SocketBuffer _write_buffer = null;
	
	protected boolean _stoped = true;

	public NetworkSystem(AppSystem app_system)
	{
		Logger.Info(this, "NetworkSystem", "Network System start");
		_app_system = app_system;
		_read_buffer = new SocketBuffer();
		_write_buffer = new SocketBuffer();
	}
	
	protected void initSocket()
	{
		try
		{
			_socket = new Socket(IP_ADDRESS, IP_PORT);
			_socket.setKeepAlive(true);
			_socket.setSoTimeout(10);
		}
		catch (UnknownHostException e)
		{
			Logger.Error("NetworkSystem.NetworkSystem", "can not connect to server: " + e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		catch (IOException e)
		{
			Logger.Error("NetworkSystem.NetworkSystem", "can not create socket: " + e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		_is_connected = true;
	}
	
	public void tryToStop()
	{
		this._stoped = true;
	}
	
	public void tryLogin(String _username, String _password)
	{
		this.sendData(ProtoTool.newCSRequestLogin(_username, _password));
	}
	
	public void dealLogin(Object obj)
	{
		Partymate.CSNotifyLogin result = (Partymate.CSNotifyLogin) obj;
		_app_system.notifyLogin(result.getResult(), result.getUsername());
	}
	
	public void tryRegister(String _username, String _password)
	{
		this.sendData(ProtoTool.newCSRequestRegister(_username, _password));	
	}
	
	public void dealRegister(Object obj)
	{
		Partymate.CSNotifyRegister result = (Partymate.CSNotifyRegister) obj;
		_app_system.notifyRegister(result.getResult());
	}
	
	public void tryChangeInfo(String _password, String _nickname)
	{
		this.sendData(ProtoTool.newCSRequestChangeInfo(_password, _nickname));
	}
	
	public void dealChangeInfo(Object obj)
	{
		Partymate.CSNotifyChangeInfo result = (Partymate.CSNotifyChangeInfo) obj;
		_app_system.notifyChangeInfo(result.getResult());
	}
	
	public void getAllPartys()
	{
		this.sendData(ProtoTool.newCSRequestGetAllParty());
	}
	
	public void dealAllPartys(Object obj)
	{
		LinkedList<PartyInfo> partys = new LinkedList<PartyInfo>();
		Partymate.CSNotifyGetAllParty result = (CSNotifyGetAllParty) obj;
		List<Partymate.PBPartyInfo> items = result.getPartysList();
		PartyInfo item = null;
		for (int i = 0; i < items.size(); ++i)
		{
			item = new PartyInfo();
			item.partyname = items.get(i).getName();
			item.partytime = items.get(i).getTime();
			item.partyplace = items.get(i).getPlace();
			partys.offer(item);
		}
		_app_system.notifyAllPartys(partys);
	}
	
	public void getMyPartys()
	{
		this.sendData(ProtoTool.newCSRequestGetMyParty());
	}
	
	public void dealMyPartys(Object obj)
	{
		LinkedList<PartyInfo> partys = new LinkedList<PartyInfo>();
		Partymate.CSNotifyGetMyParty result = (CSNotifyGetMyParty) obj;
		List<Partymate.PBPartyInfo> items = result.getPartysList();
		PartyInfo item = null;
		for (int i = 0; i < items.size(); ++i)
		{
			item = new PartyInfo();
			item.partyname = items.get(i).getName();
			item.partytime = items.get(i).getTime();
			item.partyplace = items.get(i).getPlace();
			partys.offer(item);
		}
		_app_system.notifyMyPartys(partys);
	}
	
	public void tryCreateNewParty(String party_name, String party_time, String party_place)
	{
		this.sendData(ProtoTool.newCSRequestCreateParty(party_name, party_time, party_place));
	}
	
	public void dealCreateParty(Object obj)
	{
		Partymate.CSNotifyCreateParty result = (Partymate.CSNotifyCreateParty) obj;
		_app_system.notifyCreateParty(result.getResult());
	}
	
	public void tryEnterParty(String party_name)
	{
		this.sendData(ProtoTool.newCSRequestAttendParty(party_name));
	}
	
	public void dealAttendParty(Object obj)
	{
		Partymate.CSNotifyAttendParty result = (Partymate.CSNotifyAttendParty) obj;
		_app_system.notifyAttendParty(result.getResult());
	}
	
	public void remindParty(Object obj)
	{
		LinkedList<PartyInfo> partys = new LinkedList<PartyInfo>();
		Partymate.CSNotifyParty result = (CSNotifyParty) obj;
		List<Partymate.PBPartyInfo> items = result.getPartysList();
		PartyInfo item = null;
		for (int i = 0; i < items.size(); ++i)
		{
			item = new PartyInfo();
			item.partyname = items.get(i).getName();
			item.partytime = items.get(i).getTime();
			item.partyplace = items.get(i).getPlace();
			partys.offer(item);
		}
		_app_system.notifyPartys(partys);
	}
	
	public void dealMessage(Object obj)
	{
		if (obj.getClass().getName().equals(Partymate.CSNotifyLogin.class.getName()))
		{
			dealLogin(obj);
		}
		else if (obj.getClass().getName().equals(Partymate.CSNotifyRegister.class.getName()))
		{
			dealRegister(obj);
		}
		else if (obj.getClass().getName().equals(Partymate.CSNotifyGetAllParty.class.getName()))
		{
			dealAllPartys(obj);
		}
		else if (obj.getClass().getName().equals(Partymate.CSNotifyGetMyParty.class.getName()))
		{
			dealMyPartys(obj);
		}
		else if (obj.getClass().getName().equals(Partymate.CSNotifyCreateParty.class.getName()))
		{
			dealCreateParty(obj);
		}
		else if (obj.getClass().getName().equals(Partymate.CSNotifyParty.class.getName()))
		{
			remindParty(obj);
		}
		else if (obj.getClass().getName().equals(Partymate.CSNotifyAttendParty.class.getName()))
		{
			dealAttendParty(obj);
		}
		else if (obj.getClass().getName().equals(Partymate.CSNotifyChangeInfo.class.getName()))
		{
			dealChangeInfo(obj);
		}
	}
	
	public void sendData(byte[] _data)
	{
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteOutput);
        try
		{
        	dataOutput.writeInt(_data.length);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		synchronized (_write_buffer)
		{
			_write_buffer.addPackage(byteOutput.toByteArray(), Integer.SIZE/Byte.SIZE);
			_write_buffer.addPackage(_data, _data.length);
		}
	}
	
	public void run()
	{
		while (_is_connected == false)
		{
			initSocket();
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				Logger.Info(this, "run", "try init socket");
				e.printStackTrace();
			}
		}
		Logger.Info(this, "run", "init socket successfully");

		DataInputStream is = null;
		DataOutputStream os = null;
		byte[] _rbuffer = new byte[1024];
		int _rlen = 0;
		byte[] _wbuffer = null;
		try
		{
			is = new DataInputStream(_socket.getInputStream());
			//is.read(_buffer);
			os = new DataOutputStream(_socket.getOutputStream());
			//os.write(_buffer);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this._stoped = false;

		while (!_stoped)
		{
			try
			{
				_rlen = is.read(_rbuffer);
				_read_buffer.addPackage(_rbuffer, _rlen);
			}
			catch(SocketTimeoutException e)
			{
				//e.printStackTrace(); 
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (;;)
			{
				_wbuffer = _read_buffer.getPackage();
				if (_wbuffer == null) break;
				Logger.Info(this, "run", "get read package");
				Object obj = ProtoTool.getProtoData(_wbuffer);
				if (obj == null) continue;
				dealMessage(obj);
				
				/*
				EventManager.getEventManager().addEvent(
						new SocketEvent(EventType.READ_TYPE, _wbuffer));;
				*/
			}

			for (;;)
			{
				_wbuffer = _write_buffer.getData();
				if (_wbuffer == null) break;
				try
				{
					os.write(_wbuffer);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try
			{
				os.flush();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try
			{
				Thread.sleep(1);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
