package com.qt.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.log.Log;

public class Logger
{
	protected static final String TAG = "Partymate";
	protected static boolean _debug_mode = true;
	
	public static void LogTime()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.print(df.format(new Date()) + ": ");
	}
	
	public static void Info(Object _obj, String _function, String _message)
	{
		Info(_obj.getClass().getName() + "." + _function, _message);
	}
		
	public static void Info(String _function, String _message)
	{
		LogTime();
		System.out.println("INFO: " + TAG + ": " + _function + ": " + _message);
	}
	
	public static void Warning(Object _obj, String _function, String _message)
	{
		Warning(_obj.getClass().getName() + "." + _function, _message);
	}

	public static void Warning(String _function, String _message)
	{
		LogTime();
		System.out.println("Warning: " + TAG + ": " + _function + ": " + _message);
	}
	
	public static void Error(Object _obj, String _function, String _message)
	{
		Error(_obj.getClass().getName() + "." + _function, _message);
	}

	public static void Error(String _function, String _message)
	{
		LogTime();
		System.out.println("Error: " + TAG + ": " + _function + ": " + _message);
	}
	
	public static void Debug(Object _obj, String _function, String _message)
	{
		Debug(_obj.getClass().getName() + "." + _function, _message);
	}

	public static void Debug(String _function, String _message)
	{
		if (!_debug_mode) return;
		LogTime();
		System.out.println("Debug: " + TAG + ": " + _function + ": " + _message);
	}
}