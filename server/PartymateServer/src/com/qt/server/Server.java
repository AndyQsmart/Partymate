package com.qt.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.qt.util.Logger;

public class Server
{
	public static void main(String[] args)
	{
		EventManager event_manager = new EventManager();
		Thread event_thread = new Thread(event_manager);
		event_thread.start();

		ClientListener client_listener = new ClientListener(event_manager);
		Thread server_thread = new Thread(client_listener);
		server_thread.start();

		BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
		String cmd;
		for (;;)
		{
			try
			{
				cmd = sin.readLine();
				if (cmd.equals("exit"))
				{
					Logger.Info("main", "exit");
					client_listener.tryToStop();
					event_manager.tryToStop();
					break;
					//System.exit(0);
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
