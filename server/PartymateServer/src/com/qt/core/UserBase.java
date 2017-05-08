package com.qt.core;

import java.net.Socket;

public class UserBase
{
	protected Socket _socket = null;
	
	public UserBase(Socket socket)
	{
		_socket = socket;
	}
}
