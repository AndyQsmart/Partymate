package com.qt.server;

import java.net.ServerSocket;
import java.net.Socket;

import com.qt.util.Logger;

public class ClientListener implements Runnable
{
	protected ServerSocket server = null;
	protected int SERVER_PORT = 4700;
	protected boolean _stoped = true;

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
				//start a server
			}
		}
	}
	
	public static void main(String[] args)
	{
		Thread server_thread = new Thread(new ClientListener());
		server_thread.start();
	}
}
