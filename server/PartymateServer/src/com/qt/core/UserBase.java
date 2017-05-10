package com.qt.core;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.qt.util.Logger;
import com.qt.util.SocketBuffer;

public class UserBase
{
	protected Socket _socket = null;
	protected SocketBuffer _read_buffer = null;
	protected SocketBuffer _write_buffer = null;
	
	public UserBase(Socket socket)
	{
		_socket = socket;
		_read_buffer = new SocketBuffer();
		_write_buffer = new SocketBuffer();
	}
	
	public DataInputStream getDataInputStream()
	{
		try
		{
			return new DataInputStream(_socket.getInputStream());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Logger.Error(this, "getDataInputStream", e.toString());
			return null;
		}
	}
	
	public DataOutputStream getDataOutputStream()
	{
		try
		{
			return new DataOutputStream(_socket.getOutputStream());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Logger.Error(this, "getDataOutputStream", e.toString());
			return null;
		}
	}
	
	public void addReadData(byte[] data, int len)
	{
		_read_buffer.addPackage(data, len);
	}
	
	public byte[] getReadPackage()
	{
		return _read_buffer.getPackage();
	}
	
	public void addWriteData(byte[] data, int len)
	{
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteOutput);
        try
		{
        	dataOutput.writeInt(len);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		synchronized (_write_buffer)
		{
			_write_buffer.addPackage(byteOutput.toByteArray(), Integer.SIZE/Byte.SIZE);
			_write_buffer.addPackage(data, len);
		}
	}
	
	public byte[] getWriteData()
	{
		return _write_buffer.getData();
	}
}
