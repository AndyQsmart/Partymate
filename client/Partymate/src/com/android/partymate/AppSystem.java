package com.android.partymate;

import android.app.Application;

public class AppSystem extends Application
{
	public NetworkSystem _network_system = null;
	
	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		_network_system = new NetworkSystem();
		Thread _network_thread = new Thread(_network_system);
		_network_thread.start();
	}
}
