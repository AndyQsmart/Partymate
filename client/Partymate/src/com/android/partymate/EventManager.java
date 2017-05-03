package com.android.partymate;

import java.util.LinkedList;
import java.util.Queue;

import com.android.util.SocketEvent;

public class EventManager implements Runnable
{
	protected static EventManager _event_manager = null;
	protected Queue<SocketEvent> _event_queue = null;
	
	protected EventManager()
	{
		_event_queue = new LinkedList<SocketEvent>();
	}
	
	public static EventManager getEventManager()
	{
		if (_event_manager != null)
		{
			synchronized (EventManager.class)
			{
				if (_event_manager != null)
				{
					_event_manager = new EventManager();
				}
			}
		}
		return _event_manager;
	}
	
	public void addEvent(SocketEvent _event)
	{
		synchronized (_event_queue)
		{
			_event_queue.offer(_event);
		}
	}
	
	public SocketEvent popEvent()
	{
		synchronized (_event_queue)
		{
			return _event_queue.poll();
		}
	}
	
	public void run()
	{
		for (;;)
		{
			SocketEvent _socket_event = this.popEvent();
			if (_socket_event != null)
			{
				if (_socket_event.isReadEvent())
				{
					
				}
				else if (_socket_event.isWriteEvent())
				{
					
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
