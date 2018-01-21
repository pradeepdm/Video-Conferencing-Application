package com.example.ovxexample;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Welcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		Button admin = (Button)findViewById(R.id.wcButton1);
		
		admin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginAsAdmin();				
			}
			
		});		
		
		Button atri = (Button)findViewById(R.id.atri);
		
		atri.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginAsUser("Atri");				
			}
			
		});			
		
		Button vijetha = (Button)findViewById(R.id.vijetha);
		
		vijetha.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginAsUser("Vijetha");				
			}
			
		});
		
		Button chris = (Button)findViewById(R.id.chris);
		
		chris.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginAsUser("Chris");				
			}
			
		});	
		
		Button kevin = (Button)findViewById(R.id.kevin);
		
		kevin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginAsUser("Kevin");				
			}
			
		});				
	}
	
    private void loginAsAdmin() {
        Intent intent = new Intent(this, AdminHomePage.class);
        startActivity(intent);
        finish();
    }	
    
    private void loginAsUser(String user) {
        Intent intent = new Intent(this, UserHomePage.class);
        intent.putExtra("userId", user);
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
