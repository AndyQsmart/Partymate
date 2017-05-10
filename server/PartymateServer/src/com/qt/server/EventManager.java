package com.qt.server;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

import org.omg.PortableInterceptor.LOCATION_FORWARD;

import com.mysql.jdbc.log.Log;
import com.qt.core.PartyInfo;
import com.qt.core.User;
import com.qt.core.UserBase;
import com.qt.proto.Partymate;
import com.qt.proto.Partymate.CSRequestAttendParty;
import com.qt.proto.Partymate.CSRequestChangeInfo;
import com.qt.proto.Partymate.CSRequestCreateParty;
import com.qt.proto.Partymate.CSRequestLogin;
import com.qt.proto.Partymate.CSRequestRegister;
import com.qt.proto.ProtoTool;
import com.qt.util.Logger;

public class EventManager implements Runnable
{
	protected Queue<UserBase> _event_queue = null;
	protected boolean _stoped = true;
	
	public EventManager()
	{
		_event_queue = new LinkedList<UserBase>();
	}
	
	public void addEvent(UserBase user_base)
	{
		synchronized (_event_queue)
		{
			_event_queue.offer(user_base);
		}
	}
	
	public void tryToStop()
	{
		this._stoped = true;
	}
	
	public UserBase checkLogin(UserBase user_base, Object _proto_data)
	{
		Partymate.CSRequestLogin _message = (CSRequestLogin)_proto_data;
		byte[] _data = null;
		if (SQLTool.getSQLTool().judgeLogin(_message.getUsername(), _message.getPasswd()))
		{
			_data = ProtoTool.newCSNotifyLogin(true, _message.getUsername());
			user_base = new User(user_base, _message.getUsername());
		}
		else
		{
			_data = ProtoTool.newCSNotifyLogin(false, _message.getUsername());
		}
		user_base.addWriteData(_data, _data.length);
		return user_base;
	}
	
	public UserBase tryRegister(UserBase user_base, Object _proto_data)
	{
		Partymate.CSRequestRegister _message = (CSRequestRegister)_proto_data;
		byte[] _data = null;
		if (SQLTool.getSQLTool().addUser(_message.getUsername(), _message.getPasswd()))
		{
			_data = ProtoTool.newCSNotifyRegister(true);
		}
		else
		{
			_data = ProtoTool.newCSNotifyRegister(false);
		}
		user_base.addWriteData(_data, _data.length);
		return user_base;
	}

	public UserBase tryChangeInfo(UserBase user_base, Object _proto_data)
	{
		Partymate.CSRequestChangeInfo _message = (CSRequestChangeInfo)_proto_data;
		boolean result = SQLTool.getSQLTool().changeInfo(
				((User)user_base).getUserName(), _message.getPassword(), _message.getNickname());

		byte[] _data = null;
		_data = ProtoTool.newCSNotifyChangeInfo(result);
		user_base.addWriteData(_data, _data.length);
		return user_base;
	}

	public UserBase getAllPartys(UserBase user_base, Object _proto_data)
	{
		LinkedList<PartyInfo> partys = SQLTool.getSQLTool().getAllPartys();
		byte[] _data = ProtoTool.newCSNotifyGetAllParty(partys);
		user_base.addWriteData(_data, _data.length);
		return user_base;
	}
	
	public UserBase getMyPartys(UserBase user_base, Object _proto_data)
	{
		if (!user_base.getClass().getName().equals(User.class.getName())) return user_base;
		LinkedList<PartyInfo> partys = SQLTool.getSQLTool().getMyPartys(((User)user_base).getUserName());
		byte[] _data = ProtoTool.newCSNotifyGetMyParty(partys);
		user_base.addWriteData(_data, _data.length);
		return user_base;
	}
	
	public UserBase tryCreateParty(UserBase user_base, Object _proto_data)
	{
		Partymate.CSRequestCreateParty _message = (CSRequestCreateParty)_proto_data;
		boolean result = false;
		if (!user_base.getClass().getName().equals(User.class.getName())) result = false;
		else
		{
			result = SQLTool.getSQLTool().tryCreateParty(
					_message.getName(), _message.getTime(), _message.getPlace(), ((User)user_base).getUserName());
		}
		byte[] _data = ProtoTool.newCSNotifyCreateParty(result);
		user_base.addWriteData(_data, _data.length);
		return user_base;
	}
	
	public UserBase tryAttendParty(UserBase user_base, Object _proto_data)
	{
		Partymate.CSRequestAttendParty _message = (CSRequestAttendParty)_proto_data;
		boolean result = false;
		if (!user_base.getClass().getName().equals(User.class.getName())) result = false;
		else
		{
			result = SQLTool.getSQLTool().tryAttendParty(
					_message.getName(), ((User)user_base).getUserName());
		}
		byte[] _data = ProtoTool.newCSNotifyAttendParty(result);
		user_base.addWriteData(_data, _data.length);
		return user_base;
	}
	
	public UserBase dealMessage(UserBase user_base, Object _proto_data)
	{
		if (_proto_data.getClass().getName().equals(Partymate.CSRequestLogin.class.getName()))
		{
			user_base = checkLogin(user_base, _proto_data);
		}
		else if (_proto_data.getClass().getName().equals(Partymate.CSRequestRegister.class.getName()))
		{
			user_base = tryRegister(user_base, _proto_data);
		}
		else if (_proto_data.getClass().getName().equals(Partymate.CSRequestGetAllParty.class.getName()))
		{
			user_base = getAllPartys(user_base, _proto_data);
		}
		else if (_proto_data.getClass().getName().equals(Partymate.CSRequestGetMyParty.class.getName()))
		{
			user_base = getMyPartys(user_base, _proto_data);
		}
		else if (_proto_data.getClass().getName().equals(Partymate.CSRequestCreateParty.class.getName()))
		{
			user_base = tryCreateParty(user_base, _proto_data);
		}
		else if (_proto_data.getClass().getName().equals(Partymate.CSRequestAttendParty.class.getName()))
		{
			user_base = tryAttendParty(user_base, _proto_data);
		}
		else if (_proto_data.getClass().getName().equals(Partymate.CSRequestChangeInfo.class.getName()))
		{
			user_base = tryChangeInfo(user_base, _proto_data);
		}
		
		return user_base;
	}
	
	public UserBase remindMessage(UserBase user_base) 
	{
		if (!user_base.getClass().getName().equals(User.class.getName())) return user_base;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
		String current_time = df.format(new Date());
		if (((User)user_base)._last_check_time.equals(current_time)) return user_base;
		LinkedList<PartyInfo> partys = SQLTool.getSQLTool().remindPartys(((User)user_base).getUserName(), current_time);
		byte[] _data = ProtoTool.newCSNotifyParty(partys);
		user_base.addWriteData(_data, _data.length);
		((User)user_base)._last_check_time = current_time;
		return user_base;
	}

	public void run()
	{
		UserBase user_base = null;
		DataInputStream is = null;
		DataOutputStream os = null;
		byte[] _rbuffer = new byte[1024];
		int _rlen = 0;
		byte[] _wbuffer = null;
		
		_stoped = false;
		while (!_stoped)
		{
			synchronized (_event_queue)
			{
				user_base = _event_queue.poll();
			}
			
			//do something
			if (user_base != null)
			{
				//Logger.Info(this, "run", user_base.getClass().getName());
				is = user_base.getDataInputStream();
				if (is != null)
				{
					try
					{
						_rlen = is.read(_rbuffer);
					}
					catch(SocketTimeoutException e)
					{
						_rlen = 0;
						//e.printStackTrace(); 
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						//e.printStackTrace();
						_rlen = 0;
						Logger.Error(this, "run", e.toString());
					}
					user_base.addReadData(_rbuffer, _rlen);
					if (_rlen != 0)
					{
						//Logger.Debug(this, "run", "len: " + _rlen);
						//Logger.Debug(this, "run", new String(_rbuffer));
					}
					for (;;)
					{
						_wbuffer = user_base.getReadPackage();
						if (_wbuffer == null) break;
						//deal
						Object _package = ProtoTool.getProtoData(_wbuffer);
						if (_package == null) continue;
						user_base = dealMessage(user_base, _package);
						
					}
				}
				
				
				user_base = remindMessage(user_base);
				
				
				os = user_base.getDataOutputStream();
				for (;;)
				{
					_wbuffer = user_base.getWriteData();
					if (_wbuffer == null) break;
					try
					{
						Logger.Debug(this, "run", "response");
						os.write(_wbuffer);
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						//e.printStackTrace();
						Logger.Error(this, "run", e.toString());
					}
				}
				try
				{
					os.flush();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			addEvent(user_base);
		}
	}
}
