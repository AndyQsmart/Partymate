package com.qt.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.FormatFlagsConversionMismatchException;
import java.util.LinkedList;

import javax.print.attribute.standard.RequestingUserName;

import com.qt.core.PartyInfo;
import com.qt.util.Logger;

public class SQLTool
{
    protected static final String _url = "jdbc:mysql://localhost:3306/partymate";
	protected static final String _username = "root";
	protected static final String _password = "qq123456";
	
	protected static SQLTool _sql_tool = null;
	protected Connection _connection = null;
	protected Statement _statement = null;
	
	protected SQLTool()
	{
		try
		{
            Class.forName("com.mysql.jdbc.Driver");
            Logger.Info(this, "SQLTool", "load sql driver successfully");
        }
		catch (ClassNotFoundException e1)
		{
            //e1.printStackTrace();
			Logger.Error(this, "SQLTool", e1.toString());
        }
        
        try
        {
            _connection = DriverManager.getConnection(_url, _username, _password); 
            _statement = _connection.createStatement();
            //conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }	
	}
	
	public static SQLTool getSQLTool()
	{
		if (_sql_tool == null)
		{
			_sql_tool = new SQLTool();
		}
		return _sql_tool;
	}
	
	public boolean judgeLogin(String username, String password)
	{
		String cmd = "select * from userinfo where username = '" + username + "' and password = '" + password + "'";
		Logger.Debug(this, "judgeLogin", "try to exe: " + cmd);
        boolean isok = false;
        ResultSet result = null;
		try
		{
			result = _statement.executeQuery(cmd);
			if (result.next())
				isok = true;
			result.close();
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			Logger.Error(this, "judgeLogin", e.toString());
		}
		return isok;
	}
	
	public boolean addUser(String username, String password)
	{
		String cmd = "select * from userinfo where username = '" + username + "'";
		Logger.Debug(this, "addUser", "try to exe: " + cmd);
		ResultSet result = null;
		boolean isok = true;
		try
		{
			result = _statement.executeQuery(cmd);
			if (result.next())
				isok = false;
			result.close();
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			Logger.Error(this, "addUser", e.toString());
		}
		
		cmd = "insert into userinfo values('" + username + "', '" + password + "' , '')";
		Logger.Debug(this, "addUser", "try to exe: " + cmd);
		
		try
		{
			_statement.executeUpdate(cmd);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isok;
	}
	
	public boolean changeInfo(String username, String password, String nickname)
	{
		boolean isok = true;
		String cmd = "update userinfo set password = '" + password + "' , nickname = '" + nickname + "' where username = '" + username + "'";
		Logger.Debug(this, "changeInfo", "try to exe: " + cmd);
		
		try
		{
			_statement.executeUpdate(cmd);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			isok = false;
		}
		return isok;
	}
	
	public LinkedList<PartyInfo> getAllPartys()
	{
		LinkedList<PartyInfo>  partys = new LinkedList<PartyInfo>();
		String cmd = "select * from partyinfo";
		Logger.Debug(this, "getAllPartys", "try to exe: " + cmd);
        ResultSet result = null;
        PartyInfo party_info = null;
		try
		{
			result = _statement.executeQuery(cmd);
			while (result.next())
			{
				party_info = new PartyInfo();
				party_info.partyname = result.getString(1);
				party_info.partytime = result.getString(2);
				party_info.partyplace = result.getString(3);
				partys.offer(party_info);
			}
			result.close();
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			Logger.Error(this, "getAllPartys", e.toString());
		}
		return partys;
	}
	
	public LinkedList<PartyInfo> getMyPartys(String username)
	{
		LinkedList<PartyInfo>  partys = new LinkedList<PartyInfo>();
		String cmd = "select * from partyinfo natural join partytouser where username = '" + username + "'";
		Logger.Debug(this, "getMyPartys", "try to exe: " + cmd);
        ResultSet result = null;
        PartyInfo party_info = null;
		try
		{
			result = _statement.executeQuery(cmd);
			while (result.next())
			{
				party_info = new PartyInfo();
				party_info.partyname = result.getString(1);
				party_info.partytime = result.getString(2);
				party_info.partyplace = result.getString(3);
				partys.offer(party_info);
			}
			result.close();
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			Logger.Error(this, "getMyPartys", e.toString());
		}
		return partys;
	}
	
	public boolean tryCreateParty(String party_name, String party_time, String party_place, String user_name)
	{
		String cmd = "select * from partyinfo where partyname = '" + party_name + "'";
		Logger.Debug(this, "tryCreateParty", "try to exe: " + cmd);
		ResultSet result = null;
		boolean isok = true;
		try
		{
			result = _statement.executeQuery(cmd);
			if (result.next())
				isok = false;
			result.close();
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			Logger.Error(this, "tryCreateParty", e.toString());
		}
		
		if (isok == false) return isok;
		
		cmd = "insert into partyinfo values('" + party_name + "', '" + party_time + "','" + party_place + "')";
		Logger.Debug(this, "tryCreateParty", "try to exe: " + cmd);
		
		try
		{
			_statement.executeUpdate(cmd);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		cmd = "insert into partytouser values('" + party_name + "','" + user_name + "')";
		Logger.Debug(this, "tryCreateParty", "try to exe: " + cmd);
		
		try
		{
			_statement.executeUpdate(cmd);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isok;
	}
	
	public boolean tryAttendParty(String party_name, String user_name)
	{
		String cmd = "select * from partyinfo where partyname = '" + party_name + "'";
		Logger.Debug(this, "tryCreateParty", "try to exe: " + cmd);
		ResultSet result = null;
		boolean isok = false;
		try
		{
			result = _statement.executeQuery(cmd);
			if (result.next())
				isok = true;
			result.close();
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			Logger.Error(this, "tryCreateParty", e.toString());
		}
		
		if (isok == false) return isok;
		
		cmd = "insert into partytouser values('" + party_name + "','" + user_name + "')";
		Logger.Debug(this, "tryCreateParty", "try to exe: " + cmd);
		
		try
		{
			_statement.executeUpdate(cmd);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isok;
	}
	
	public LinkedList<PartyInfo> remindPartys(String user_name, String current_time)
	{
		LinkedList<PartyInfo>  partys = new LinkedList<PartyInfo>();
		String cmd = "select * from partyinfo natural join partytouser where username = '" + user_name + "' and partytime = '" + current_time + "'";
		Logger.Debug(this, "remindPartys", "try to exe: " + cmd);
        ResultSet result = null;
        PartyInfo party_info = null;
		try
		{
			result = _statement.executeQuery(cmd);
			while (result.next())
			{
				party_info = new PartyInfo();
				party_info.partyname = result.getString(1);
				party_info.partytime = result.getString(2);
				party_info.partyplace = result.getString(3);
				partys.offer(party_info);
			}
			result.close();
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			Logger.Error(this, "remindPartys", e.toString());
		}
		return partys;	
	}
}
