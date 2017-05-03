package com.android.partymate;

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

	protected void setMyPartys()
	{
		TableLayout _table_layout = (TableLayout) findViewById(R.id.my_party_table);

		for (int i = 0; i < 3; i++)
		{
			TableRow tableRow = new TableRow(this);

			TextView _item_name = new TextView(this);
			
			_item_name.setTextColor(Color.WHITE);
			_item_name.setGravity(Gravity.CENTER);
			_item_name.setText("party1");
			_item_name.setBackgroundColor(Color.BLACK);
			tableRow.addView(_item_name);

			TextView _item_time = new TextView(this);
			_item_time.setText("party1");
			tableRow.addView(_item_time);

			TextView _item_place = new TextView(this);
			_item_place.setText("party1");
			tableRow.addView(_item_place);

			_table_layout.addView(tableRow, new TableLayout.LayoutParams());
		}
	}
}
