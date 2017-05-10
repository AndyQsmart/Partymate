package com.android.util;

import android.util.Log;

public class Logger
{
	protected static final String TAG = "Partymate";
	protected static boolean _debug_mode = true;
		
	public static void Info(Object _obj, String _function, String _message)
	{
		Info(_obj.getClass().getName() + "." + _function, _message);
	}
	
	public static void Info(String _function, String _message)
	{
		Log.i(TAG, _function + ": " + _message);
	}
	
	public static void Warning(Object _obj, String _function, String _message)
	{
		Warning(_obj.getClass().getName() + "." + _function, _message);
	}
	
	public static void Warning(String _function, String _message)
	{
		Log.w(TAG, _function + ": " + _message);
	}
	
	public static void Error(Object _obj, String _function, String _message)
	{
		Error(_obj.getClass().getName() + "." + _function, _message);
	}
	
	public static void Error(String _function, String _message)
	{
		Log.e(TAG, _function + ": " + _message);
	}
	
	public static void Debug(String _function, String _message)
	{
		if (!_debug_mode) return;
		Log.d(TAG, _function + ": " + _message);
	}
	
	public static void Debug(Object _obj, String _function, String _message)
	{
		Debug(_obj.getClass().getName() + "." + _function, _message);
	}
}
