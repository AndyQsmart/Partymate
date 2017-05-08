package com.qt.util;

public class Logger
{
	protected static final String TAG = "Partymate";
	
	public static void Info(Object _obj, String _function, String _message)
	{
		Info(_obj.getClass().getName() + "." + _function, _message);
	}
		
	public static void Info(String _function, String _message)
	{
		System.out.println("INFO: " + TAG + ": " + _function + ": " + _message);
	}
	
	public static void Warning(Object _obj, String _function, String _message)
	{
		Warning(_obj.getClass().getName() + "." + _function, _message);
	}

	public static void Warning(String _function, String _message)
	{
		System.out.println("Warning: " + TAG + ": " + _function + ": " + _message);
	}
	
	public static void Error(Object _obj, String _function, String _message)
	{
		Error(_obj.getClass().getName() + "." + _function, _message);
	}

	public static void Error(String _function, String _message)
	{
		System.out.println("Error: " + TAG + ": " + _function + ": " + _message);
	}
}