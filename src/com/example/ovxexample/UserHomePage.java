package com.example.ovxexample;

import com.openclove.ovx.OVXException;
import com.openclove.ovx.OVXView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UserHomePage extends Activity {

	OVXView ovxView;
	static TextView resultView;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uservideo);
		ovxView = OVXView.getOVXContext(this);
		Log.e("videoconf: ","Inside");
		
		String myName = getIntent().getStringExtra("userId");
		resultView = (TextView) findViewById(R.id.username);
		resultView.setText(myName);
		
		Button home = (Button)findViewById(R.id.home);
		home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ovxView.exitCall();
		        goHome();
			}			
		});	
		
		Button back = (Button)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ovxView.exitCall();
		        goBack();
			}			
		});		
		
		Button joinconf = (Button)findViewById(R.id.joinconf);
		
		joinconf.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Log.e("videoconf: ","Clicked");
					ovxView.setApiKey("k5p4y4xmza89r8e3dqv8gbqt");
					ovxView.setOvxGroupId("myovxroom");
					ovxView.setKeyValue("ovx-width","700");
					ovxView.setKeyValue("ovx-height","500");
					ovxView.setRemoteViewX(50);
					ovxView.setRemoteViewY(200);					
					if (ovxView.isCallOn()) {
						Log.e("videoconf: ","STARTING CALL");
						ovxView.call();
					} else{
						Log.e("videoconf: ","NO CALL");
						ovxView.call();
					}
					Log.e("videoconf: ","DONE");
					
				} catch (OVXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
			
		});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present. joinconf
		getMenuInflater().inflate(R.menu.ovxexample, menu);
		return true;
	}
	
    private void goHome() {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
        finish();
    }    
    
    private void goBack() {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
        finish();
    }  	

}
