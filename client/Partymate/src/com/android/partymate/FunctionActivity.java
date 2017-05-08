package com.android.partymate;

import com.android.util.Logger;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_function);
		this.findObjects();
	}

	protected void findObjects()
	{
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
				setMyPartys();
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
				setOtherPartys();
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
		_info_page.setVisibility(View.VISIBLE);
		_pre_page = _info_page;

		_my_party_page = (FrameLayout) this.findViewById(R.id.my_party_page);
		_my_party_page.setVisibility(View.GONE);

		_create_party_page = (FrameLayout) this.findViewById(R.id.create_party_page);
		_create_party_page.setVisibility(View.GONE);

		_enter_party_page = (FrameLayout) this.findViewById(R.id.enter_party_page);
		_enter_party_page.setVisibility(View.GONE);
	}

	protected int _my_party_len_before = 0;

	protected void setMyPartys()
	{
		TableLayout _table_layout = (TableLayout) findViewById(R.id.my_party_table);
		TextView _my_party_table_name_title = (TextView) findViewById(R.id.my_party_table_name_title);
		
		for (int i = 0; i < _my_party_len_before; ++i)
			_table_layout.removeViewAt(2);

		for (int i = 0; i < 3; i++)
		{
			TableRow tableRow = new TableRow(this);

			TextView _item_name = new TextView(this);
			_item_name.setLayoutParams(_my_party_table_name_title.getLayoutParams());
			
			_item_name.setTextColor(Color.WHITE);
			_item_name.setGravity(Gravity.CENTER);
			_item_name.setBackgroundColor(Color.BLACK);

			_item_name.setText("party" + i);
			tableRow.addView(_item_name);

			TextView _item_time = new TextView(this);
			_item_time.setLayoutParams(_my_party_table_name_title.getLayoutParams());
			
			_item_time.setTextColor(Color.WHITE);
			_item_time.setGravity(Gravity.CENTER);
			_item_time.setBackgroundColor(Color.BLACK);

			_item_time.setText("party" + i);
			tableRow.addView(_item_time);

			TextView _item_place = new TextView(this);
			_item_place.setLayoutParams(_my_party_table_name_title.getLayoutParams());

			_item_place.setTextColor(Color.WHITE);
			_item_place.setGravity(Gravity.CENTER);
			_item_place.setBackgroundColor(Color.BLACK);

			_item_place.setText("party" + i);
			tableRow.addView(_item_place);

			_table_layout.addView(tableRow, new TableLayout.LayoutParams());
		}
		_my_party_len_before = 3;
	}
	
	protected int _other_party_len_before = 0;

	protected void setOtherPartys()
	{
		TableLayout _table_layout = (TableLayout) findViewById(R.id.enter_party_table);
		TextView _other_party_table_name_title = (TextView) findViewById(R.id.other_party_table_name_title);

		for (int i = 0; i < _other_party_len_before; ++i)
			_table_layout.removeViewAt(2);

		for (int i = 0; i < 3; i++)
		{
			TableRow tableRow = new TableRow(this);

			TextView _item_name = new TextView(this);
			_item_name.setLayoutParams(_other_party_table_name_title.getLayoutParams());
			
			_item_name.setTextColor(Color.WHITE);
			_item_name.setGravity(Gravity.CENTER);
			_item_name.setBackgroundColor(Color.BLACK);

			_item_name.setText("party" + i);
			tableRow.addView(_item_name);

			TextView _item_time = new TextView(this);
			_item_time.setLayoutParams(_other_party_table_name_title.getLayoutParams());
			
			_item_time.setTextColor(Color.WHITE);
			_item_time.setGravity(Gravity.CENTER);
			_item_time.setBackgroundColor(Color.BLACK);

			_item_time.setText("party" + i);
			tableRow.addView(_item_time);

			TextView _item_place = new TextView(this);
			_item_place.setLayoutParams(_other_party_table_name_title.getLayoutParams());

			_item_place.setTextColor(Color.WHITE);
			_item_place.setGravity(Gravity.CENTER);
			_item_place.setBackgroundColor(Color.BLACK);

			_item_place.setText("party" + i);
			tableRow.addView(_item_place);
			
			Button _item_enter = new Button(this);

			_item_enter.setGravity(Gravity.CENTER);
			_item_enter.setText("¼ÓÈëÅÉ¶Ô");
			_item_enter.setHeight(45);
			_item_enter.setOnClickListener(new OnClickListenerWithStringParam(_item_name.getText().toString())
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					Logger.Info("setOtherPartys", "enter party" + _param);
				}
			});
			tableRow.addView(_item_enter);

			_table_layout.addView(tableRow, new TableLayout.LayoutParams());
		}
		_other_party_len_before = 3;
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
}
