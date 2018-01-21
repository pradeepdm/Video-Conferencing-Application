package com.example.ovxexample;

import com.openclove.ovx.OVXException;
import com.openclove.ovx.OVXView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OVXExample extends Activity {

	OVXView ovxView;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ovxexample);
		ovxView = OVXView.getOVXContext(this);
		
		try {
			ovxView.setApiKey("k5p4y4xmza89r8e3dqv8gbqt");
			ovxView.setOvxGroupId("myovxroom");
			ovxView.setKeyValue("ovx-width","600");
			ovxView.setKeyValue("ovx-height","600");
		} catch (OVXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Button startcall = (Button)findViewById(R.id.startcall);
		startcall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					ovxView.call();
				} catch (OVXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				
			}
			
		});
		
		Button endcall = (Button)findViewById(R.id.endcall);
		endcall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ovxView.exitCall();
				// TODO Auto-generated method stub
				
			}
			
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ovxexample, menu);
		return true;
	}

}
