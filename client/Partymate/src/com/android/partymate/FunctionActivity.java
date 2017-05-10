package com.android.partymate;

import java.util.LinkedList;

import com.android.proto.Partymate;
import com.android.util.Logger;
import com.android.util.PartyInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class FunctionActivity extends Activity
{
	protected Button _info_btn;
	protected Button _my_party_btn;
	protected Button _create_party_btn;
	protected Button _enter_party_btn;
	protected Button _quit_btn;

	protected FrameLayout _pre_page;
	protected FrameLayout _info_page;
	protected FrameLayout _my_party_page;
	protected FrameLayout _create_party_page;
	protected FrameLayout _enter_party_page;

	protected AppSystem _app_system;

	NotificationManager notification_manager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_function);
		this.findObjects();
	}

	protected void findObjects()
	{
		_app_system = (AppSystem)getApplication();
		_app_system.setFunctionActivity(this);
		
		notification_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);  

		_info_btn = (Button) this.findViewById(R.id.info_btn);
		_info_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				_pre_page.setVisibility(View.GONE);
				_info_page.setVisibility(View.VISIBLE);
				_pre_page = _info_page;
			}
		});

		_my_party_btn = (Button) this.findViewById(R.id.my_party_btn);

		_my_party_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				_pre_page.setVisibility(View.GONE);
				_my_party_page.setVisibility(View.VISIBLE);
				_pre_page = _my_party_page;
				_app_system._network_system.getMyPartys();
				//setMyPartys();
			}
		});

		_create_party_btn = (Button) this.findViewById(R.id.create_party_btn);
		_create_party_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				_pre_page.setVisibility(View.GONE);
				_create_party_page.setVisibility(View.VISIBLE);
				_pre_page = _create_party_page;
			}
		});

		_enter_party_btn = (Button) this.findViewById(R.id.enter_party_btn);
		_enter_party_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				_pre_page.setVisibility(View.GONE);
				_enter_party_page.setVisibility(View.VISIBLE);
				_pre_page = _enter_party_page;
				_app_system._network_system.getAllPartys();
				//setOtherPartys();
			}
		});

		_quit_btn = (Button) this.findViewById(R.id.quit_btn);
		_quit_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				FunctionActivity.this.finish();
			}
		});

		_pre_page = null;
		_info_page = (FrameLayout) this.findViewById(R.id.info_page);
		_info_page.setVisibility(View.GONE);
		_pre_page = _info_page;
		((Button)this.findViewById(R.id.change_info_btn)).setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				String password = ((EditText)findViewById(R.id.info_page_editTextPassword)).getText().toString();
				String nick_name = ((EditText)findViewById(R.id.info_page_editTextNickName)).getText().toString();
				_app_system._network_system.tryChangeInfo(password, nick_name);
			}
		});

		_my_party_page = (FrameLayout) this.findViewById(R.id.my_party_page);
		_my_party_page.setVisibility(View.GONE);

		_create_party_page = (FrameLayout) this.findViewById(R.id.create_party_page);
		_create_party_page.setVisibility(View.GONE);
		((TimePicker)this.findViewById(R.id.timePicker)).setIs24HourView(true);;
		((Button)this.findViewById(R.id.create_party_page_create_party_btn)).setOnClickListener(
				new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				String party_name = "";
				String party_time = "";
				String party_place = "";
				
				party_name = ((EditText)findViewById(R.id.create_party_page_editTextParty)).getText().toString();
				
				DatePicker date_picker = (DatePicker)findViewById(R.id.datePicker);
				party_time = party_time + date_picker.getYear();
				party_time = party_time + ((date_picker.getMonth()+1) > 9 ? (date_picker.getMonth()+1) : "0"+(date_picker.getMonth()+1));
				party_time = party_time + (date_picker.getDayOfMonth() > 9 ? date_picker.getDayOfMonth() : "0"+date_picker.getDayOfMonth());

				TimePicker time_picker = (TimePicker)findViewById(R.id.timePicker);
				party_time = party_time + (time_picker.getCurrentHour() > 9 ? time_picker.getCurrentHour() : "0"+time_picker.getCurrentHour());
				party_time = party_time + (time_picker.getCurrentMinute() > 9 ? time_picker.getCurrentMinute() : "0"+time_picker.getCurrentMinute());
				
				party_place = ((EditText)findViewById(R.id.create_party_page_editTextPlace)).getText().toString();

				_app_system._network_system.tryCreateNewParty(party_name, party_time, party_place);
			}
		});

		_enter_party_page = (FrameLayout) this.findViewById(R.id.enter_party_page);
		_enter_party_page.setVisibility(View.GONE);
	}

	protected int _my_party_len_before = 0;

	protected void setMyPartys(LinkedList<PartyInfo> partys)
	{
		TableLayout _table_layout = (TableLayout) findViewById(R.id.my_party_table);
		TextView _my_party_table_name_title = (TextView) findViewById(R.id.my_party_table_name_title);
		
		for (int i = 0; i < _my_party_len_before; ++i)
			_table_layout.removeViewAt(2);

		_my_party_len_before = 0;
		PartyInfo item = null;
		while (!partys.isEmpty())
		{
			item = partys.poll();
			TableRow tableRow = new TableRow(this);

			TextView _item_name = new TextView(this);
			_item_name.setLayoutParams(_my_party_table_name_title.getLayoutParams());
			
			_item_name.setTextColor(Color.WHITE);
			_item_name.setGravity(Gravity.CENTER);
			_item_name.setBackgroundColor(Color.BLACK);

			_item_name.setText(item.partyname);
			tableRow.addView(_item_name);

			TextView _item_time = new TextView(this);
			_item_time.setLayoutParams(_my_party_table_name_title.getLayoutParams());
			
			_item_time.setTextColor(Color.WHITE);
			_item_time.setGravity(Gravity.CENTER);
			_item_time.setBackgroundColor(Color.BLACK);

			_item_time.setText(item.partytime);
			tableRow.addView(_item_time);

			TextView _item_place = new TextView(this);
			_item_place.setLayoutParams(_my_party_table_name_title.getLayoutParams());

			_item_place.setTextColor(Color.WHITE);
			_item_place.setGravity(Gravity.CENTER);
			_item_place.setBackgroundColor(Color.BLACK);

			_item_place.setText(item.partyplace);
			tableRow.addView(_item_place);

			_table_layout.addView(tableRow, new TableLayout.LayoutParams());
			_my_party_len_before++;
		}
	}
	
	protected int _other_party_len_before = 0;

	public void setOtherPartys(LinkedList<PartyInfo> partys)
	{
		TableLayout _table_layout = (TableLayout) findViewById(R.id.enter_party_table);
		TextView _other_party_table_name_title = (TextView) findViewById(R.id.other_party_table_name_title);

		for (int i = 0; i < _other_party_len_before; ++i)
			_table_layout.removeViewAt(2);

		
		_other_party_len_before = 0;
		PartyInfo item = null;
		while (!partys.isEmpty())
		{
			item = partys.poll();
			TableRow tableRow = new TableRow(this);

			TextView _item_name = new TextView(this);
			_item_name.setLayoutParams(_other_party_table_name_title.getLayoutParams());
			
			_item_name.setTextColor(Color.WHITE);
			_item_name.setGravity(Gravity.CENTER);
			_item_name.setBackgroundColor(Color.BLACK);

			_item_name.setText(item.partyname);
			tableRow.addView(_item_name);

			TextView _item_time = new TextView(this);
			_item_time.setLayoutParams(_other_party_table_name_title.getLayoutParams());
			
			_item_time.setTextColor(Color.WHITE);
			_item_time.setGravity(Gravity.CENTER);
			_item_time.setBackgroundColor(Color.BLACK);

			_item_time.setText(item.partytime);
			tableRow.addView(_item_time);

			TextView _item_place = new TextView(this);
			_item_place.setLayoutParams(_other_party_table_name_title.getLayoutParams());

			_item_place.setTextColor(Color.WHITE);
			_item_place.setGravity(Gravity.CENTER);
			_item_place.setBackgroundColor(Color.BLACK);

			_item_place.setText(item.partyplace);
			tableRow.addView(_item_place);
			
			Button _item_enter = new Button(this);

			_item_enter.setGravity(Gravity.CENTER);
			_item_enter.setText("加入派对");
			_item_enter.setHeight(45);
			_item_enter.setOnClickListener(new OnClickListenerWithStringParam(_item_name.getText().toString())
			{
				
				@Override
				public void onClick(View v)
				{
					Logger.Info("setOtherPartys", "enter party" + _param);
					_app_system._network_system.tryEnterParty(_param);
				}
			});
			tableRow.addView(_item_enter);

			_table_layout.addView(tableRow, new TableLayout.LayoutParams());
			_other_party_len_before++;
		}
	}
	
	protected void dealCreateParty(Boolean result)
	{
		Toast _toast = null;
		if (result)
		{
			_toast = Toast.makeText(
					FunctionActivity.this, "派对创建成功", Toast.LENGTH_LONG);
		}
		else
		{
			_toast = Toast.makeText(
					FunctionActivity.this, "派对已存在", Toast.LENGTH_LONG);
		}
		_toast.show();
	}
	
	protected void dealAttendParty(Boolean result)
	{
		Toast _toast = null;
		if (result)
		{
			_toast = Toast.makeText(
					FunctionActivity.this, "派对加入成功", Toast.LENGTH_LONG);
		}
		else
		{
			_toast = Toast.makeText(
					FunctionActivity.this, "派对加入失败", Toast.LENGTH_LONG);
		}
		_toast.show();
	}
	
	protected void dealChangeInfo(Boolean result)
	{
		Toast _toast = null;
		if (result)
		{
			_toast = Toast.makeText(
					FunctionActivity.this, "修改信息成功", Toast.LENGTH_LONG);
		}
		else
		{
			_toast = Toast.makeText(
					FunctionActivity.this, "修改信息失败", Toast.LENGTH_LONG);
		}
		_toast.show();
	}
	
	@SuppressLint("NewApi")
	protected void notifyPartys(LinkedList<PartyInfo> partys)
	{
		Logger.Debug(this, "notifyPartys", partys.size()+"");
		PartyInfo item = null;
		while (!partys.isEmpty())
		{
			item = partys.poll();
			String tips = "";
			tips = "您的派对：" + item.partyname + "即将开始。";
			tips = tips + "地点是" + item.partyplace;
			Notification notification = new Notification.Builder(this)
					.setContentTitle("派对提醒")
				    .setContentText(tips)
				    .setSmallIcon(R.drawable.ic_launcher).build();
		    notification_manager.notify(100, notification);
		}
	}

	protected class OnClickListenerWithStringParam implements View.OnClickListener
	{
		protected String _param;
		
		public OnClickListenerWithStringParam(String param)
		{
			this._param = param;
		}

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
		}
	}
	
	public Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) 
		{
			switch(msg.what)
			{
				case Partymate.MessageType.NOTIFY_GET_ALL_PARTY_VALUE:
					setOtherPartys((LinkedList<PartyInfo>) msg.obj);
					break;

				case Partymate.MessageType.NOTIFY_GET_MY_PARTY_VALUE:
					setMyPartys((LinkedList<PartyInfo>) msg.obj);
					break;
					
				case Partymate.MessageType.NOTIFY_CREATE_PARTY_VALUE:
					dealCreateParty((Boolean)msg.obj);
					break;
					
				case Partymate.MessageType.NOTIFY_PARTY_VALUE:
					notifyPartys((LinkedList<PartyInfo>) msg.obj);
					break;
					
				case Partymate.MessageType.NOTIFY_ATTEND_PARTY_VALUE:
					dealAttendParty((Boolean) msg.obj);
					break;

				case Partymate.MessageType.NOTIFY_CHANGE_INFO_VALUE:
					dealChangeInfo((Boolean) msg.obj);
					break;

				default:
					break;
			}
		}
	};
}
