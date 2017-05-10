package com.android.partymate;

import java.util.LinkedList;

import com.android.proto.Partymate;
import com.android.util.PartyInfo;

import android.R.bool;
import android.app.Application;
import android.os.Message;

public class AppSystem extends Application
{
	public NetworkSystem _network_system = null;
	protected MainActivity _main_activity = null;
	protected RegisterActivity _register_activity = null;
	protected FunctionActivity _function_activity = null;
	
	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		_network_system = new NetworkSystem(this);
		Thread _network_thread = new Thread(_network_system);
		_network_thread.start();
	}
	
	@Override
	public void onTerminate()
	{
		// TODO Auto-generated method stub
		_network_system.tryToStop();
		super.onTerminate();
	}
	
	public void setMainActivity(MainActivity main_activity)
	{
		this._main_activity = main_activity;
	}
	
	public void notifyLogin(boolean result, String username)
	{
		_main_activity.dealLogin(result);
	}
	
	public void setRegisterActivity(RegisterActivity register_activity)
	{
		_register_activity = register_activity;
	}
	
	public void notifyRegister(boolean result)
	{
		_register_activity.dealRegister(result);
	}
	
	public void setFunctionActivity(FunctionActivity function_activity)
	{
		_function_activity = function_activity;
	}
	
	public void notifyAllPartys(LinkedList<PartyInfo> partys)
	{
		Message _message = new Message();
		_message.obj = partys;
		_message.what = Partymate.MessageType.NOTIFY_GET_ALL_PARTY_VALUE;
		_function_activity.handler.sendMessage(_message);
		//_function_activity.setOtherPartys(partys);
	}
	
	public void notifyMyPartys(LinkedList<PartyInfo> partys)
	{
		Message _message = new Message();
		_message.obj = partys;
		_message.what = Partymate.MessageType.NOTIFY_GET_MY_PARTY_VALUE;
		_function_activity.handler.sendMessage(_message);
		//_function_activity.setOtherPartys(partys);
	}
	
	public void notifyCreateParty(boolean result)
	{
		Message _message = new Message();
		_message.obj = result;
		_message.what = Partymate.MessageType.NOTIFY_CREATE_PARTY_VALUE;
		_function_activity.handler.sendMessage(_message);

	}
	
	public void notifyAttendParty(boolean result)
	{
		Message _message = new Message();
		_message.obj = result;
		_message.what = Partymate.MessageType.NOTIFY_ATTEND_PARTY_VALUE;
		_function_activity.handler.sendMessage(_message);

	}
	
	public void notifyPartys(LinkedList<PartyInfo> partys)
	{
		Message _message = new Message();
		_message.obj = partys;
		_message.what = Partymate.MessageType.NOTIFY_PARTY_VALUE;
		if (_function_activity != null)
			_function_activity.handler.sendMessage(_message);
		//_function_activity.setOtherPartys(partys);
	}

	public void notifyChangeInfo(boolean result)
	{
		Message _message = new Message();
		_message.obj = result;
		_message.what = Partymate.MessageType.NOTIFY_CHANGE_INFO_VALUE;
		if (_function_activity != null)
			_function_activity.handler.sendMessage(_message);
		//_function_activity.setOtherPartys(partys);
	
	}
}
