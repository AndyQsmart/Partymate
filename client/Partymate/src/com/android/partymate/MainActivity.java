package com.android.partymate;

import com.android.util.Logger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity
{
	protected Button _login_btn;
	protected Button _register_btn;
	protected EditText _username_txt;
	protected EditText _password_txt;
	protected AppSystem _app_system;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.findObjects();
	}
	
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onRestart()
	{
		// TODO Auto-generated method stub
		super.onRestart();
	}
	
	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	protected void findObjects()
	{
		_app_system = (AppSystem)getApplication();
		
		_login_btn = (Button)this.findViewById(R.id.buttonLogin);
		_login_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (_app_system._network_system.tryLogin(
						_username_txt.getText().toString(),
						_password_txt.getText().toString()))
				{
					Intent intent = new Intent(MainActivity.this, FunctionActivity.class);
					startActivity(intent);
				}
				else
				{
					Toast _toast = Toast.makeText(
							MainActivity.this, "’À∫≈ªÚ√‹¬Î¥ÌŒÛ", Toast.LENGTH_LONG);
					_toast.show();
				}
			}
		});

		//registerBtn
		_register_btn = (Button)this.findViewById(R.id.btn_register);
		_register_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
		
		_username_txt = (EditText)this.findViewById(R.id.editTextName2);
		
		_password_txt = (EditText)this.findViewById(R.id.editTextPassword2);
	}
}
