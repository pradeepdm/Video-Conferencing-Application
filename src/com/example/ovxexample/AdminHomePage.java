package com.example.ovxexample;

import com.openclove.ovx.OVXException;
import com.openclove.ovx.OVXView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AdminHomePage extends Activity {
	
	OVXView ovxView;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhomepage);
		
		Button home = (Button)findViewById(R.id.home);
		home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		        goHome();
			}			
		});	
		
		Button back = (Button)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		        goBack();
			}			
		});			
		
		
		Button svc = (Button)findViewById(R.id.startvideoconf);
		svc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startVideoConference();
			}			
		});		
		
		Button viewVideo = (Button)findViewById(R.id.viewrecordings);
		viewVideo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ViewRecording();
			}			
		});			
		
	}
	
    private void startVideoConference() {
        Intent intent = new Intent(this, VideoConference.class);
        startActivity(intent);
        finish();
    }	
    
    private void ViewRecording() {
        Intent intent = new Intent(this, ViewRecording.class);
        startActivity(intent);
        finish();
    }	    
    
    private void goHome() {
        Intent intent = new Intent(this, AdminHomePage.class);
        startActivity(intent);
        finish();
    }    
    
    private void goBack() {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
        finish();
    }      
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ovxexample, menu);
		return true;
	}

}
