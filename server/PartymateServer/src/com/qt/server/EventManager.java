package com.qt.server;

import java.util.LinkedList;
import java.util.Queue;

import org.omg.PortableInterceptor.LOCATION_FORWARD;

import com.qt.core.UserBase;
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

	public void run()
	{
		UserBase user_base = null;
		_stoped = false;
		while (!_stoped)
		{
			synchronized (_event_queue)
			{
				user_base = _event_queue.poll();
				if (user_base != null)
				{
					Logger.Info(this, "run", user_base.getClass().getName());
				}
			}
			
			//do something
			
			addEvent(user_base);
		}
	}
}
