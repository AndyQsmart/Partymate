package com.android.partymate;

import com.android.util.Logger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends Activity
{
	
	protected Button _back_btn;
	protected Button _register_btn;
	protected EditText _username_txt;
	protected EditText _password_txt;
	protected AppSystem _app_system;
	protected Toast _toast = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		this.findObjects();
	}
	
	public void dealRegister(boolean result)
	{
		if (result)
		{
			Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
			startActivity(intent);
		}
		else
		{
			_toast.show();
		}	
	}
	
	protected void findObjects()
	{
		_app_system = (AppSystem)getApplication();
		_app_system.setRegisterActivity(this);
		_toast = Toast.makeText(
				RegisterActivity.this, "’À∫≈“—¥Ê‘⁄", Toast.LENGTH_LONG);
		
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
		_register_btn.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				_app_system._network_system.tryRegister(
						_username_txt.getText().toString(), 
						_password_txt.getText().toString());
			}
		});
		
		_username_txt = (EditText)this.findViewById(R.id.editTextName2);
		_password_txt = (EditText)this.findViewById(R.id.editTextPassword2);
	}
}
