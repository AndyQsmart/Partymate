package com.android.util;

import android.util.Log;

public class Logger
{
	protected static final String TAG = "Partymate";
		
	public static void Info(String _function, String _message)
	{
		Log.i(TAG, _function + ": " + _message);
	}
	
	public static void Warning(String _function, String _message)
	{
		Log.i(TAG, _function + ": " + _message);
	}
	
	public static void Error(String _function, String _message)
	{
		Log.i(TAG, _function + ": " + _message);
	}
}
