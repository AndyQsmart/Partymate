package com.qt.proto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import com.google.protobuf.ByteString;
import com.qt.core.PartyInfo;
import com.qt.util.Logger;

public class ProtoTool
{
	public static byte[] getPackage(int message_type, byte[] data)
	{
		Partymate.Package.Builder package_builder = Partymate.Package.newBuilder();
		package_builder.setMessageId(message_type);
		package_builder.setData(ByteString.copyFrom(data));
		Partymate.Package _package = package_builder.build();	
		ByteArrayOutputStream _output = new ByteArrayOutputStream();
		try
		{
			_package.writeTo(_output);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return _output.toByteArray();	
	}
	
	public static byte[] newCSNotifyLogin(boolean vaild, String username)
	{
		Partymate.CSNotifyLogin.Builder package_builder = Partymate.CSNotifyLogin.newBuilder();
		package_builder.setResult(vaild);
		package_builder.setUsername(username);
		
		Partymate.CSNotifyLogin _package = package_builder.build();
		ByteArrayOutputStream _output = new ByteArrayOutputStream();
		try
		{
			_package.writeTo(_output);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getPackage(Partymate.MessageType.NOTIFY_LOGIN_VALUE, _output.toByteArray());
	}
	
	public static byte[] newCSNotifyRegister(boolean vaild)
	{
		Partymate.CSNotifyRegister.Builder package_builder = Partymate.CSNotifyRegister.newBuilder();
		package_builder.setResult(vaild);
		
		Partymate.CSNotifyRegister _package = package_builder.build();
		ByteArrayOutputStream _output = new ByteArrayOutputStream();
		try
		{
			_package.writeTo(_output);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getPackage(Partymate.MessageType.NOTIFY_REGISTER_VALUE, _output.toByteArray());
	}
	
	public static byte[] newCSNotifyChangeInfo(boolean vaild)
	{
		Partymate.CSNotifyChangeInfo.Builder package_builder = Partymate.CSNotifyChangeInfo.newBuilder();
		package_builder.setResult(vaild);
		
		Partymate.CSNotifyChangeInfo _package = package_builder.build();
		ByteArrayOutputStream _output = new ByteArrayOutputStream();
		try
		{
			_package.writeTo(_output);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getPackage(Partymate.MessageType.NOTIFY_CHANGE_INFO_VALUE, _output.toByteArray());
	}
	
	public static byte[] newCSNotifyGetAllParty(LinkedList<PartyInfo> partys)
	{
		Partymate.CSNotifyGetAllParty.Builder package_builder = Partymate.CSNotifyGetAllParty.newBuilder();
		Partymate.PBPartyInfo.Builder item_builder = Partymate.PBPartyInfo.newBuilder();
		PartyInfo party_info = null;
		while (!partys.isEmpty())
		{
			party_info = partys.poll();
			item_builder.setName(party_info.partyname);
			item_builder.setTime(party_info.partytime);
			item_builder.setPlace(party_info.partyplace);
			package_builder.addPartys(item_builder.build());
		}		

		Partymate.CSNotifyGetAllParty _package = package_builder.build();
		ByteArrayOutputStream _output = new ByteArrayOutputStream();
		try
		{
			_package.writeTo(_output);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getPackage(Partymate.MessageType.NOTIFY_GET_ALL_PARTY_VALUE, _output.toByteArray());
	}
	
	public static byte[] newCSNotifyGetMyParty(LinkedList<PartyInfo> partys)
	{
		Partymate.CSNotifyGetMyParty.Builder package_builder = Partymate.CSNotifyGetMyParty.newBuilder();
		Partymate.PBPartyInfo.Builder item_builder = Partymate.PBPartyInfo.newBuilder();
		PartyInfo party_info = null;
		while (!partys.isEmpty())
		{
			party_info = partys.poll();
			item_builder.setName(party_info.partyname);
			item_builder.setTime(party_info.partytime);
			item_builder.setPlace(party_info.partyplace);
			package_builder.addPartys(item_builder.build());
		}		

		Partymate.CSNotifyGetMyParty _package = package_builder.build();
		ByteArrayOutputStream _output = new ByteArrayOutputStream();
		try
		{
			_package.writeTo(_output);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getPackage(Partymate.MessageType.NOTIFY_GET_MY_PARTY_VALUE, _output.toByteArray());
	}
	
	public static byte[] newCSNotifyCreateParty(boolean result)
	{
		Partymate.CSNotifyCreateParty.Builder package_builder = Partymate.CSNotifyCreateParty.newBuilder();
		package_builder.setResult(result);

		Partymate.CSNotifyCreateParty _package = package_builder.build();
		ByteArrayOutputStream _output = new ByteArrayOutputStream();
		try
		{
			_package.writeTo(_output);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getPackage(Partymate.MessageType.NOTIFY_CREATE_PARTY_VALUE, _output.toByteArray());
	}
	
	public static byte[] newCSNotifyAttendParty(boolean result)
	{
		Partymate.CSNotifyAttendParty.Builder package_builder = Partymate.CSNotifyAttendParty.newBuilder();
		package_builder.setResult(result);

		Partymate.CSNotifyAttendParty _package = package_builder.build();
		ByteArrayOutputStream _output = new ByteArrayOutputStream();
		try
		{
			_package.writeTo(_output);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getPackage(Partymate.MessageType.NOTIFY_ATTEND_PARTY_VALUE, _output.toByteArray());
	}
	
	public static byte[] newCSNotifyParty(LinkedList<PartyInfo> partys)
	{
		Partymate.CSNotifyParty.Builder package_builder = Partymate.CSNotifyParty.newBuilder();
		Partymate.PBPartyInfo.Builder item_builder = Partymate.PBPartyInfo.newBuilder();
		PartyInfo party_info = null;
		while (!partys.isEmpty())
		{
			party_info = partys.poll();
			item_builder.setName(party_info.partyname);
			item_builder.setTime(party_info.partytime);
			item_builder.setPlace(party_info.partyplace);
			package_builder.addPartys(item_builder.build());
		}		

		Partymate.CSNotifyParty _package = package_builder.build();
		ByteArrayOutputStream _output = new ByteArrayOutputStream();
		try
		{
			_package.writeTo(_output);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getPackage(Partymate.MessageType.NOTIFY_PARTY_VALUE, _output.toByteArray());
	}
	
	public static Object getProtoData(byte[] _data)
	{
		Object _proto_data = null;
		ByteArrayInputStream _input = new ByteArrayInputStream(_data);
		Partymate.Package _package = null;

		try
		{
			_package = Partymate.Package.parseFrom(_input);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Logger.Debug("ProtoTool", _package.getMessageId() + "");

		switch (_package.getMessageId())
		{
		case Partymate.MessageType.REQUEST_LOGIN_VALUE:
		{
			byte[] _data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSRequestLogin _message = null;
			try
			{
				_message = Partymate.CSRequestLogin.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Logger.Debug("ProtoTool", _message.getUsername());
			//Logger.Debug("ProtoTool", _message.getPasswd());
			_proto_data = _message;
		}
			break;

		case Partymate.MessageType.REQUEST_REGISTER_VALUE:
		{
			byte[] _data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSRequestRegister _message = null;
			try
			{
				_message = Partymate.CSRequestRegister.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Logger.Debug("ProtoTool", _message.getUsername());
			//Logger.Debug("ProtoTool", _message.getPasswd());
			_proto_data = _message;
		}
			break;
		
		case Partymate.MessageType.REQUEST_CHANGE_INFO_VALUE:
		{
			byte[] _data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSRequestChangeInfo _message = null;
			try
			{
				_message = Partymate.CSRequestChangeInfo.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Logger.Debug("ProtoTool", _message.getUsername());
			//Logger.Debug("ProtoTool", _message.getPasswd());
			_proto_data = _message;
		}
			break;
			
		case Partymate.MessageType.REQUEST_GET_ALL_PARTY_VALUE:
		{
			byte[] _data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSRequestGetAllParty _message = null;
			try
			{
				_message = Partymate.CSRequestGetAllParty.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Logger.Debug("ProtoTool", _message.getUsername());
			//Logger.Debug("ProtoTool", _message.getPasswd());
			_proto_data = _message;
		}
			break;
			
		case Partymate.MessageType.REQUEST_GET_MY_PARTY_VALUE:
		{
			byte[] _data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSRequestGetMyParty _message = null;
			try
			{
				_message = Partymate.CSRequestGetMyParty.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Logger.Debug("ProtoTool", _message.getUsername());
			//Logger.Debug("ProtoTool", _message.getPasswd());
			_proto_data = _message;
		}
			break;
			
		case Partymate.MessageType.REQUEST_CREATE_PARTY_VALUE:
		{
			byte[] _data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSRequestCreateParty _message = null;
			try
			{
				_message = Partymate.CSRequestCreateParty.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Logger.Debug("ProtoTool", _message.getUsername());
			//Logger.Debug("ProtoTool", _message.getPasswd());
			_proto_data = _message;
		}
			break;
		
		case Partymate.MessageType.REQUEST_ATTEND_PARTY_VALUE:
		{
			byte[] _data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSRequestAttendParty _message = null;
			try
			{
				_message = Partymate.CSRequestAttendParty.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Logger.Debug("ProtoTool", _message.getUsername());
			//Logger.Debug("ProtoTool", _message.getPasswd());
			_proto_data = _message;
		}
			break;

		default:
			break;
		}
		return _proto_data;
	}
}
