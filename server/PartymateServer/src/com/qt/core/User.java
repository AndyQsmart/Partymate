package com.qt.core;

import java.net.Socket;

import com.qt.util.SocketBuffer;

public class User extends UserBase
{
	protected String _username = null;
	public String _last_check_time = "";

	public User(UserBase user_base, String user_name)
	{
		super(user_base._socket);
		// TODO Auto-generated constructor stub
		_username = user_name;
		_read_buffer = user_base._read_buffer;
		_write_buffer = user_base._write_buffer;
	}

	public String getUserName()
	{
		return _username;
	}
}
