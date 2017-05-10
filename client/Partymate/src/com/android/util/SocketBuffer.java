package com.android.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;

import org.apache.http.util.ByteArrayBuffer;

import android.util.Log;

public class SocketBuffer
{
	protected byte[] _data = null;
	protected int _max_len = 0;
	protected int _from = 0;
	protected int _to = 0;
	
	public SocketBuffer()
	{
		_max_len = 1024;
		_data = new byte[_max_len];
		_from = _to = 0;
	}

	public SocketBuffer(int _len)
	{
		_max_len = _len;
		_data = new byte[_max_len];
		_from = _to = 0;
	}
	
	public boolean addPackage(byte[] _new_package, int _package_len)
	{
		if ((_to-_from+_max_len)%_max_len + _package_len >= _max_len) return false;
		for (int i = 0; i < _package_len; ++i)
		{
			_data[_to] = _new_package[i];
			_to = (_to+1)%_max_len;
		}
		return true;
	}
	
	public byte[] getData()
	{
		if ((_to-_from+_max_len)%_max_len == 0) return null;
		int package_len = (_to-_from+_max_len)%_max_len;
		byte[] package_byte = new byte[package_len];
		for (int i = 0; i < package_len; ++i)
			package_byte[i] = _data[(_from+i)%_max_len];
		_from = _to;
		return package_byte;
	}
	
	public byte[] getPackage()
	{
		if ((_to-_from+_max_len)%_max_len < 4) return null;

		/*
		 *need to deal,
		 *if the package is invaild
		*/

		//get buffer len
		int package_len = 0;
		byte[] package_len_byte = new byte[4];
		for (int i = 0; i < 4; ++i)
			package_len_byte[i] = _data[(_from+i)%_max_len];

		ByteArrayInputStream byteInput = new ByteArrayInputStream(package_len_byte);
		DataInputStream dataInput = new DataInputStream(byteInput);
		try
		{
			package_len = dataInput.readInt();
		}
		catch (IOException e)
		{
			Logger.Error("SocketBuffer.getPackage", e.toString());
		}

		if ((_to-_from+_max_len)%_max_len < 4+package_len) return null;

		//deal package??
		byte[] package_byte = new byte[package_len];
		int all_len = 4+package_len;
		for (int i = 4; i < all_len; ++i)
			package_byte[i-4] = _data[(_from+i)%_max_len];
		_from = (_from+package_len+4)%_max_len;
		return package_byte;
	}
}
