package com.android.util;

public class SocketEvent
{
	public enum EventType
	{
		READ_TYPE,
		WRITE_TYPE
	};
	
	protected EventType _event_type;
	protected byte[] _event_data;

	public SocketEvent(EventType _type, byte[] _data)
	{
		_event_type = _type;
		_event_data = _data;
	}
	
	public boolean isReadEvent()
	{
		return _event_type == EventType.READ_TYPE;
	}
	
	public boolean isWriteEvent()
	{
		return _event_type == EventType.WRITE_TYPE;
	}
}
