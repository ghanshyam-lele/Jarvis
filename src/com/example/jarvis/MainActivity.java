package com.example.jarvis;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity 
{
	ImageButton redButton,greenButton,blueButton,yellowButton;
	Button aboutUsButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		redButton=(ImageButton) findViewById(R.id.imageButtonRed);
		greenButton=(ImageButton) findViewById(R.id.imageButtonGreen);
		blueButton=(ImageButton) findViewById(R.id.imageButtonBlue);
		yellowButton=(ImageButton) findViewById(R.id.imageButtonYellow);
		aboutUsButton=(Button) findViewById(R.id.buttonAboutUs);
		
		redButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(), TextEditorActivity.class);
				intent.putExtra("background", "red");
				startActivity(intent);
			}
		});
		
		blueButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(), TextEditorActivity.class);
				intent.putExtra("background", "blue");
				startActivity(intent);	
			}
		});
		
		yellowButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(), TextEditorActivity.class);
				intent.putExtra("background", "yellow");
				startActivity(intent);	
			}
		});
		
		greenButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(), TextEditorActivity.class);
				intent.putExtra("background", "green");
				startActivity(intent);
			}
		});
		
		aboutUsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
			}
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent=new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intent );
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
