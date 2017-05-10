package com.qt.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.qt.core.UnauthedUser;
import com.qt.core.UserBase;
import com.qt.util.Logger;

public class ClientListener implements Runnable
{
	protected ServerSocket server = null;
	protected int SERVER_PORT = 4700;
	protected boolean _stoped = true;
	
	protected EventManager _event_manager = null;
	
	public ClientListener(EventManager event_manager)
	{
		_event_manager = event_manager;
	}
	
	public void tryToStop()
	{
		this._stoped = true;
	}

	public void run()
	{
		try
		{
			server = new ServerSocket(SERVER_PORT);
			//����һ��ServerSocket�ڶ˿�4700�����ͻ�����
			Logger.Info(this, "run", "server start sucessfully");
		}
		catch (Exception e)
		{
			Logger.Error(this, "run", "server start failed:" + e.toString());
			return;
			//������ӡ������Ϣ
		}
		this._stoped = false;
		while (!_stoped)
		{
			Socket socket = null;
			try
			{
				Logger.Info(this, "run", "wait for client");
				socket = server.accept();
				socket.setSoTimeout(4);
				//ʹ��accept()�����ȴ��ͻ������п�
				//�����������һ��Socket���󣬲�����ִ��
			}
			catch (Exception e)
			{
				Logger.Error(this, "run", "connect to client failed" + e.toString());
				//������ӡ������Ϣ
			}
			if (socket != null)
			{
				Logger.Info(this, "run", "accept a client successfully");
				UnauthedUser user_base = new UnauthedUser(socket);
				_event_manager.addEvent(user_base);
			}
		}
	}
}
