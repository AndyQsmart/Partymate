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
			//创建一个ServerSocket在端口4700监听客户请求
			Logger.Info(this, "run", "server start sucessfully");
		}
		catch (Exception e)
		{
			Logger.Error(this, "run", "server start failed:" + e.toString());
			return;
			//出错，打印出错信息
		}
		this._stoped = false;
		while (!_stoped)
		{
			Socket socket = null;
			try
			{
				Logger.Info(this, "run", "wait for client");
				socket = server.accept();
				//使用accept()阻塞等待客户请求，有客
				//请求到来则产生一个Socket对象，并继续执行
			}
			catch (Exception e)
			{
				Logger.Error(this, "run", "connect to client failed" + e.toString());
				//出错，打印出错信息
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
