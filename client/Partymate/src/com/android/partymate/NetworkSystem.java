package com.android.partymate;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.android.util.Logger;
import com.android.util.SocketBuffer;
import com.android.util.SocketEvent;
import com.android.util.SocketEvent.EventType;

import android.nfc.tech.IsoDep;

public class NetworkSystem implements Runnable
{
	protected static final String IP_ADDRESS = "127.0.0.1";
	protected static final int IP_PORT = 4700;
	protected Socket _socket = null;
	protected SocketBuffer _read_buffer = null;
	protected SocketBuffer _write_buffer = null;

	public NetworkSystem()
	{
		try
		{
			_socket = new Socket(IP_ADDRESS, IP_PORT);
			_socket.setKeepAlive(true);
			_socket.setSoTimeout(10);
		}
		catch (UnknownHostException e)
		{
			Logger.Error("NetworkSystem.NetworkSystem", "can not connect to server");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			Logger.Error("NetworkSystem.NetworkSystem", "can not create socket");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean tryLogin(String _username, String _password)
	{
		Logger.Info("NetworkSystem.tryLogin", _username + " " + _password);
		if (_username.equals("admin") && _password.equals("admin"))
			return true;
		else
			return false;
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
			_write_buffer.addPackage(byteOutput.toByteArray(), 4);
			_write_buffer.addPackage(_data, _data.length);
		}
	}
	
	public void run()
	{
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

		for (;;)
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
				EventManager.getEventManager().addEvent(
						new SocketEvent(EventType.READ_TYPE, _wbuffer));;
			}

			for (;;)
			{
				_wbuffer = _write_buffer.getPackage();
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
