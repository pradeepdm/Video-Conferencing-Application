package com.example.ovxexample;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

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
import android.widget.VideoView;

public class ViewRecording extends Activity {

	OVXView ovxView;
	static TextView resultView;
	static TextView cts1View, cts2View;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewrecording);
		ovxView = OVXView.getOVXContext(this);
		Log.e("viewrecording:: ","Inside");
		
		resultView = (TextView) findViewById(R.id.message);
		cts1View = (TextView) findViewById(R.id.record1);
		cts2View = (TextView) findViewById(R.id.record2);
		
		
		viewRecord();
		
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
		
		Button view1 = (Button)findViewById(R.id.view1);
		
		view1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
        		String result = "";
        		InputStream isr = null;
        		try{
        			HttpClient httpclient = new DefaultHttpClient();
         			HttpPost httppost = new HttpPost("http://50.161.194.10/Hello/geturl1.php");
        			HttpResponse response = httpclient.execute(httppost);
        			HttpEntity entity = response.getEntity();
        			isr = entity.getContent();
        		}
        		catch (Exception e){
        			Log.e("log_tag1","Error in httpconnection "+e.toString());
        		}	
        		
        		try{
        			BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
        			StringBuilder sb = new StringBuilder();
        			String line = null;
        			while((line = reader.readLine()) != null) {
        				sb.append(line+"\n");
        			}
        			isr.close();
        			
        			result = sb.toString();
        			Log.e("log_result1","Resulting String: "+result);
        			JSONArray jArray = new JSONArray(result);
        			String url1 = "";
        			
        			if(jArray.length() >= 1){        			
	        			JSONObject json = jArray.getJSONObject(0);
	        			url1 = json.getString("URL");
	        			Log.e("log_tag1","view Recording "+url1);
	        			resultView.setText("Viewing Video 1");
	        			DisplayRecording(url1);
        			} else{
        				Log.e("log_tag1","view Recording Not working "+url1);
        			}
	        			
        			Log.e("log_result2","Resulting String: "+url1);
        		}
        		catch(Exception e){
        			Log.e("log_tag2","Error converting result "+e.toString());
        		}          		
		
			}
			
		});	
		
		Button view2 = (Button)findViewById(R.id.view2);
		
		view2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
        		String result = "";
        		InputStream isr = null;
        		try{
        			HttpClient httpclient = new DefaultHttpClient();
         			HttpPost httppost = new HttpPost("http://50.161.194.10/Hello/geturl1.php");
        			HttpResponse response = httpclient.execute(httppost);
        			HttpEntity entity = response.getEntity();
        			isr = entity.getContent();
        		}
        		catch (Exception e){
        			Log.e("log_tag2","Error in httpconnection "+e.toString());
        		}	
        		
        		try{
        			BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
        			StringBuilder sb = new StringBuilder();
        			String line = null;
        			while((line = reader.readLine()) != null) {
        				sb.append(line+"\n");
        			}
        			isr.close();
        			
        			result = sb.toString();
        			Log.e("log_result2","Resulting String: "+result);
        			JSONArray jArray = new JSONArray(result);
        			String url2 = "";
        			
        			if(jArray.length() >= 2){        			
	        			JSONObject json = jArray.getJSONObject(1);
	        			url2 = json.getString("URL");
	        			Log.e("log_tag2","view Recording "+url2);
	        			resultView.setText("Viewing Video 2");	        			
	        			DisplayRecording(url2);
        			} else{
        				Log.e("log_tag2","view Recording Not working "+url2);
        			}
	        			
        			Log.e("log_result2","Resulting String: "+url2);
        		}
        		catch(Exception e){
        			Log.e("log_tag2","Error converting result "+e.toString());
        		}
			}
			
		});			
		
		
		Button delete1 = (Button)findViewById(R.id.delete1);
		
		delete1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
        		String result = "";
        		InputStream isr = null;
        		try{
        			HttpClient httpclient = new DefaultHttpClient();
         			HttpPost httppost = new HttpPost("http://50.161.194.10/Hello/deleteurl1.php");
        			HttpResponse response = httpclient.execute(httppost);
        			HttpEntity entity = response.getEntity();
        			isr = entity.getContent();
        			resultView.setText("Deleted Video 1");   
        			viewRecord();
        		}
        		catch (Exception e){
        			Log.e("log_tag3","Error in httpconnection "+e.toString());
        		}	
			}
		});		
		
		
		Button delete2 = (Button)findViewById(R.id.delete2);
		
		delete2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
        		String result = "";
        		InputStream isr = null;
        		try{
        			HttpClient httpclient = new DefaultHttpClient();
         			HttpPost httppost = new HttpPost("http://50.161.194.10/Hello/deleteurl2.php");
        			HttpResponse response = httpclient.execute(httppost);
        			HttpEntity entity = response.getEntity();
        			isr = entity.getContent();
        			resultView.setText("Deleted Video 2");        
        			viewRecord();        			
        		}
        		catch (Exception e){
        			Log.e("log_tag3","Error in httpconnection "+e.toString());
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
	
	public void DisplayRecording(String url){
		final VideoView videoView1 = (VideoView) findViewById(R.id.VideoView1);
	    videoView1.setVideoPath(
				//"http://www.ebookfrenzy.com/android_book/movie.mp4");
    		   //"http://192.168.2.4/Hello/sample.3gp"); 
	    		url);	    
			
		videoView1.start();			
	}
	
    private void goHome() {
        Intent intent = new Intent(this, AdminHomePage.class);
        startActivity(intent);
        finish();
    }    
    
    private void goBack() {
        Intent intent = new Intent(this, AdminHomePage.class);
        startActivity(intent);
        finish();
    }  	
    
    private void viewRecord(){
		String result = "", cts1 = "", cts2 = "";
		InputStream isr = null;
		try{
			HttpClient httpclient = new DefaultHttpClient();
 			HttpPost httppost = new HttpPost("http://50.161.194.10/Hello/geturl1.php");
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			isr = entity.getContent();
		}
		catch (Exception e){
			Log.e("log_tag3","Error in httpconnection "+e.toString());
		}	
		
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null) {
				sb.append(line+"\n");
			}
			isr.close();
			
			result = sb.toString();
			Log.e("log_result3","Resulting String: "+result);
			JSONArray jArray = new JSONArray(result);
			
			if(jArray.length() >= 2){        			
    			JSONObject json = jArray.getJSONObject(0);
    			cts1 = json.getString("TIMESTAMP");
    			Log.e("log_tag3","view Recording "+cts1);
    			cts1View.setText(cts1);	     
    			
    			json = jArray.getJSONObject(1);
    			cts2 = json.getString("TIMESTAMP");
    			Log.e("log_tag3","view Recording "+cts2);
    			cts2View.setText(cts2);	     			
			} else{
				Log.e("log_tag2","view Recording Not working "+cts1);
			}
    			
			Log.e("log_result2","Resulting String: "+cts1);
		}
		catch(Exception e){
			Log.e("log_tag2","Error converting result "+e.toString());
		}  	
    	
    }

}
