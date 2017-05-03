package com.android.partymate;

import com.android.util.Logger;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class RegisterActivity extends Activity
{
	
	protected Button _back_btn;
	protected Button _register_btn;
	protected EditText _username_txt;
	protected EditText _password_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		this.findObjects();
	}
	
	protected void findObjects()
	{
		_back_btn = (Button)this.findViewById(R.id.back_btn);
		_back_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				RegisterActivity.this.finish();
			}
		});

		_register_btn = (Button)this.findViewById(R.id.btn_register);
		_username_txt = (EditText)this.findViewById(R.id.editTextName2);
		_password_txt = (EditText)this.findViewById(R.id.editTextPassword2);
	}
}
