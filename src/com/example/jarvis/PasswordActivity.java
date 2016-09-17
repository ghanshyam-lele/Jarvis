package com.example.jarvis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends Activity {
	Button doneButton;
	EditText passwordEditText;
	SharedPreferences pref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);
		doneButton=(Button) findViewById(R.id.buttonSubmit);
		passwordEditText=(EditText) findViewById(R.id.editTextPassword);
		pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		final String actualPassword=pref.getString("prefUserPassword", "NOPASSWORD");
		if(actualPassword.equals("NOPASSWORD") || actualPassword.equals(""))
		{	
			finish();
			startActivity(new Intent(getApplicationContext(), MainActivity.class));
			
		}
		doneButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String typedPassword=passwordEditText.getText().toString();
				
					
				if(typedPassword.equals(actualPassword))
					startActivity(new Intent(getApplicationContext(), MainActivity.class));
				else
					Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_LONG).show();
			}
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.password, menu);
		return true;
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
