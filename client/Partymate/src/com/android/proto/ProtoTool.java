package com.android.proto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.protobuf.ByteString;


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
	
	public static byte[] newCSRequestLogin(String username, String password)
	{
		Partymate.CSRequestLogin.Builder package_builder = Partymate.CSRequestLogin.newBuilder();
		package_builder.setUsername(username);
		package_builder.setPasswd(password);
		
		Partymate.CSRequestLogin _package = package_builder.build();
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
		
		return getPackage(Partymate.MessageType.REQUEST_LOGIN_VALUE, _output.toByteArray());
	}
	
	public static byte[] newCSRequestRegister(String username, String password)
	{
		Partymate.CSRequestRegister.Builder package_builder = Partymate.CSRequestRegister.newBuilder();
		package_builder.setUsername(username);
		package_builder.setPasswd(password);
		
		Partymate.CSRequestRegister _package = package_builder.build();
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
		
		return getPackage(Partymate.MessageType.REQUEST_REGISTER_VALUE, _output.toByteArray());
	}
	
	public static byte[] newCSRequestChangeInfo(String password, String nickname)
	{
		Partymate.CSRequestChangeInfo.Builder package_builder = Partymate.CSRequestChangeInfo.newBuilder();
		package_builder.setPassword(password);
		package_builder.setNickname(nickname);
		
		Partymate.CSRequestChangeInfo _package = package_builder.build();
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
		
		return getPackage(Partymate.MessageType.REQUEST_CHANGE_INFO_VALUE, _output.toByteArray());
	}
	
	public static byte[] newCSRequestGetAllParty()
	{
		Partymate.CSRequestGetAllParty.Builder package_builder = Partymate.CSRequestGetAllParty.newBuilder();
		
		Partymate.CSRequestGetAllParty _package = package_builder.build();
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
		
		return getPackage(Partymate.MessageType.REQUEST_GET_ALL_PARTY_VALUE, _output.toByteArray());
	}
	
	public static byte[] newCSRequestGetMyParty()
	{
		Partymate.CSRequestGetMyParty.Builder package_builder = Partymate.CSRequestGetMyParty.newBuilder();
		
		Partymate.CSRequestGetMyParty _package = package_builder.build();
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
		
		return getPackage(Partymate.MessageType.REQUEST_GET_MY_PARTY_VALUE, _output.toByteArray());
	}
	
	public static byte[] newCSRequestCreateParty(String party_name, String party_time, String party_place)
	{
		Partymate.CSRequestCreateParty.Builder package_builder = Partymate.CSRequestCreateParty.newBuilder();
		package_builder.setName(party_name);
		package_builder.setTime(party_time);
		package_builder.setPlace(party_place);
		
		Partymate.CSRequestCreateParty _package = package_builder.build();
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
		
		return getPackage(Partymate.MessageType.REQUEST_CREATE_PARTY_VALUE, _output.toByteArray());
	}
	
	public static byte[] newCSRequestAttendParty(String party_name)
	{
		Partymate.CSRequestAttendParty.Builder package_builder = Partymate.CSRequestAttendParty.newBuilder();
		package_builder.setName(party_name);
		
		Partymate.CSRequestAttendParty _package = package_builder.build();
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
		
		return getPackage(Partymate.MessageType.REQUEST_ATTEND_PARTY_VALUE, _output.toByteArray());
	}

	public static Object getProtoData(byte[] _data)
	{
		Object _proto_data = null;
		ByteArrayInputStream _input = new ByteArrayInputStream(_data);
		Partymate.Package _package = null;
		byte[] _data_array = null;

		try
		{
			_package = Partymate.Package.parseFrom(_input);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch (_package.getMessageId())
		{
		case Partymate.MessageType.NOTIFY_LOGIN_VALUE:
		{
			_data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSNotifyLogin _message = null;
			try
			{
				_message = Partymate.CSNotifyLogin.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_proto_data = _message;
		}
			break;
			
		case Partymate.MessageType.NOTIFY_REGISTER_VALUE:
		{
			_data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSNotifyRegister _message = null;
			try
			{
				_message = Partymate.CSNotifyRegister.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_proto_data = _message;
		}
			break;
		
		case Partymate.MessageType.NOTIFY_CHANGE_INFO_VALUE:
		{
			_data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSNotifyChangeInfo _message = null;
			try
			{
				_message = Partymate.CSNotifyChangeInfo.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_proto_data = _message;
		}
			break;
			
		case Partymate.MessageType.NOTIFY_GET_ALL_PARTY_VALUE:
		{
			_data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSNotifyGetAllParty _message = null;
			try
			{
				_message = Partymate.CSNotifyGetAllParty.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_proto_data = _message;
		}
			break;
			
		case Partymate.MessageType.NOTIFY_GET_MY_PARTY_VALUE:
		{
			_data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSNotifyGetMyParty _message = null;
			try
			{
				_message = Partymate.CSNotifyGetMyParty.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_proto_data = _message;
		}
			break;
			
		case Partymate.MessageType.NOTIFY_CREATE_PARTY_VALUE:
		{
			_data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSNotifyCreateParty _message = null;
			try
			{
				_message = Partymate.CSNotifyCreateParty.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_proto_data = _message;
		}
			break;
			
		case Partymate.MessageType.NOTIFY_PARTY_VALUE:
		{
			_data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSNotifyParty _message = null;
			try
			{
				_message = Partymate.CSNotifyParty.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_proto_data = _message;
		}
			break;
			
		case Partymate.MessageType.NOTIFY_ATTEND_PARTY_VALUE:
		{
			_data_array = _package.getData().toByteArray();
			_input = new ByteArrayInputStream(_data_array);
			Partymate.CSNotifyAttendParty _message = null;
			try
			{
				_message = Partymate.CSNotifyAttendParty.parseFrom(_input);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_proto_data = _message;
		}
			break;

		default:
			break;
		}
		return _proto_data;
	}
}
