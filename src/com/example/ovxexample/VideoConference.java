package com.example.ovxexample;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.openclove.ovx.OVXCallListener;
import com.openclove.ovx.OVXException;
import com.openclove.ovx.OVXView;

public class VideoConference extends Activity {

	private OVXView ovxView;
	protected RelativeLayout media_control;
	private Dialog dialog;
	private EditText ovx_text;
	protected TextView chat_box;
	private String groupid;
	private TextView text_gid;
	private VideoConference currentActivity;
	private String gid;
	private NotificationManager notmgr;
	private Notification noti;
	protected ProgressDialog progressDialog;
	private ToggleButton tgl;
	protected static final int NOTIFY_ME_ID = 200;
	private boolean chk_recordedvideo;
	private String videourl;
	private String ccode;
	private String phn_number;
	ToggleButton record;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videoconf);

		// Comments provided for ovx sdk code
		Log.d("INDUS", "onCreate");

		currentActivity = this;

		//new api
		/*  Following OVX parameters can be set using this function:
		 
		        Key                             Parameter                                    Example
		    1. ovx-title                    Title of app                        ovxView.setKeyValue("ovx-title","OVX Example") ;
		    2. ovx-apiKey                   API Key of application              ovxView.setKeyValue("ovx-apiKey","Api Key String");
		    3. ovx-apiSecret                API secret of application           ovxView.setKeyValue("ovx-apiSecret","Api Key Secret");
		                                                                        // if not provided, takes default value
		    4. ovx-userId                   User ID                             ovxView.setKeyValue("ovx-userId","userid");
		    5. ovx-name                     user name                           ovxView.setKeyValue("ovx-name" ,"User name");
		    6. ovx-session                  Group Id                            ovxView.setKeyValue("ovx-session","abc123");
		    7. ovx-mood                     mood id  ("1", "2", "3")         	ovxView.setKeyValue("ovx-mood","1");
		    8. ovx-width                    width of video view                 ovxView.setKeyValue("ovx-width","320");
		    9. ovx-height                   height of video view                ovxView.setKeyValue("ovx-height","240");
		    10. ovx-type                     "video" / "voice"                  ovxView.setKeyValue("ovx-type" ,"video");
		    11. ovx-chat                    "enable" / "disable"                ovxView.setKeyValue("ovx-chat","enable");
		    12. ovx-record                  "enable" / "disable"                ovxView.setKeyValue("ovx-record","enable");
		    13. ovx-dialout                 "enable" / "disable"                ovxView.setKeyValue("ovx-dialout","enable");
		                                                                        //to start voice call using ovx-cc and ovx-number
		 
		    14. ovx-cc                      country code string for dial out   ovxView.setKeyValue("ovx-cc" ,"91");
		    15. ovx-number                  number string for dial out         ovxView.setKeyValue("ovx-number","1111111111");
		    16. ovx-debug                   "enable"/ "disable"                ovxView.setKeyValue("ovx-debug","enable");
		    17. ovx-enableUserInteraction   "enable"/ "disable"                ovxView.setKeyValue("ovx-enableUserInteraction" ,"enable");
		    18. ovx-showOVXMenuOnTap"       "enable"/ "disable"                ovxView.setKeyValue("ovx-showOVXMenuOnTap","enable" );
		 
		 */


		
		
		// Access the Shared Instance of the OVXView

		ovxView = OVXView.getOVXContext(this);
		
		
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
		
		try {

			// To get UID(User ID) for the device.

			String ovxuserId = Secure.getString(this.getContentResolver(),
					Secure.ANDROID_ID);

			/*We set the OVX User ID as the device id since it is unique. You can use any logic to set 
			*the userId as long as it distinguishes your device from the other devices involved in the conference.    
			*/
			
			
			ovxView.setKeyValue("ovx-userId", ovxuserId);

			/* api key received on creation of developers account */
	
			//ovxView.setKeyValue("ovx-apiKey", "n9j573vhaak6fyhj2qx438j2");
			ovxView.setKeyValue("ovx-apiKey", "k5p4y4xmza89r8e3dqv8gbqt");
			
			/*secret key received on creation of developers account */
			
			//ovxView.setKeyValue("ovx-apiSecret", "p2MDJVWSwD7lFZvd2GXUySlXQwA=");
			//ovxView.setKeyValue("ovx-apiSecret", "p2MDJVWSwD7lFZvd2GXUySlXQwA=");
			/*You have to set the User name  */
			
			ovxView.setKeyValue("ovx-name","Administrator");
			
			/*Here we set the OVX Group Id as a unique identifier. Users who initiate call with 
			 * the same group id will end up in a video conference. You can use own logic to 
			 * share this group id, for example via links as invite to another user to join conference.    
			 */
			
			ovxView.setKeyValue("ovx-session",UUID.randomUUID().toString().replaceAll("-", "" ));//Mandatory
			

			ovxView.setKeyValue("ovx-debug", "enable");
			
			/* Here you can set whether to show the OVX menu when the user taps
			* the video view. OVX menu contains call control features,like audio mute,video mute etc;
			* it also allows you to minimize or maximize the video view.  
			*/
			ovxView.setKeyValue("ovx-showOVXMenuOnTap", "disable");
			
			/*Remote gesture api is true by default, setting it to false 
			 * will disable the pinch/zoom and drag event of the video view 
			 * and also prevents you from maximizing the video on double tap, 
			 * hence setting it to false will lock the video view in a fixed 
			 * position on the screen.  
			 */
			
			ovxView.setKeyValue("ovx-enableUserInteraction","enable");//enable by default 
			
			
			
			/*set chat attribute to true to send to and receive data from the remote peer who has joined the same room*/  
			ovxView.setKeyValue("ovx-chat", "enable");
			
			/*set record attribute to enable to record the video conference */
			ovxView.setKeyValue("ovx-record", "enable");//disable by default
			
			
			/* Set The title of the Dialog Menu */
			ovxView.setKeyValue("ovx-title","OVXExample");
			
			/*Set the background video theme */
			ovxView.setKeyValue("ovx-mood", "1");
			
			//ovxView.setKeyValue("setOvxGroupId","myovxroom");
			/*set the  width size of the video view */
			
			ovxView.setKeyValue("ovx-width","700");
			
			/* set the height size of the video view */
			
			ovxView.setKeyValue("ovx-height","500");
		
		
			/* setting auto-start profile to true will automatically start the call without invoking the call() api*/
			/*mandatory to use this api last to configure other other api values */
			ovxView.setKeyValue("ovx-autostart", "false");
		
			
			
			/*Has to call this function only if 'ovx-autostart' set to true so that call will be auto
			 * 
			 * started with above configure values..
 
			 */
			
			//ovxView.launch();
		
			
			/* You can set the x and y position of the video view which is relative to 
			*  the top,left position of the screen.
			*/
				
			ovxView.setRemoteViewX(50);
			ovxView.setRemoteViewY(200);

			/*We use a TextView for displaying the current GroupId set through the sdk.*/
			/* 
			chat_box = (TextView) findViewById(R.id.chat_text_box);
			final TextView txt = (TextView) findViewById(R.id.txt);
			
			txt.setText("Video Profile");
			
			text_gid = (TextView) findViewById(R.id.app);
			text_gid.setText("Current Group ID : " + ovxView.getOvxGroupId());
			*/
			
			
			
			
			/*toggle button to toggle between voice profile and video profile , setting attribute to voice will start a voice call 
			and setting it to video will start a video call*/ 
			/*
			 tgl = (ToggleButton) findViewById(R.id.tgl);
			tgl.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!ovxView.isCallOn()) {
						if (tgl.isChecked()) {

							try {
								//api to toggle between voice and video profile
								
								ovxView.setKeyValue("ovx-type", "video");
								tgl.setEnabled(true);
							} catch (OVXException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							txt.setText("Video Profile");
							if (notmgr != null)
								notmgr.cancel(NOTIFY_ME_ID);

						}

						else {

							try {
								
								ovxView.setKeyValue("ovx-type", "voice");
								tgl.setEnabled(true);
							} catch (OVXException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							txt.setText("Voice Profile");
						}
					}

				}
			});
			*/
			
			/* We can launch the OVX menu through the sdk by 
			 * binding it to a buttons click event. We had earlier disabled 
			 * the api to launch OVX menu on click of the video view to show case this feature.  
			 */
			
			/*
			
			Button ovx_call_menu = (Button) findViewById(R.id.call_menu);
			ovx_call_menu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					ovxView.showOVXMenu(); // Launches the OVX menu on click of the call_menu button

				}
			});
			*/
			
			// Bind the the call event to the click of start_call button  

			// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			
			ovxView.setApiKey("k5p4y4xmza89r8e3dqv8gbqt");
			ovxView.setOvxGroupId("myovxroom");
			//ovxView.setKeyValue("ovx-width","700");
			//ovxView.setKeyValue("ovx-height","500");
			ovxView.call();		
			
			record = (ToggleButton)findViewById(R.id.recording);
			record.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						if (record.isChecked()) {
							ovxView.ovxVideoRecord(true);
						} else{
							ovxView.ovxVideoRecord(false);
						}
					} catch (OVXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
			
			Button stopconf = (Button)findViewById(R.id.stopconf);
			stopconf.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ovxView.exitCall();
					goHome();
					// TODO Auto-generated method stub
					
				}
				
			});					
			
			
			// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			
			/*
			Button ovx_call_button = (Button) findViewById(R.id.start_call);
			ovx_call_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
				
					if (!ovxView.isCallOn()) //Checks if the call is on 
						try {
							
							if(ovxView.isVoiceProfileEnabled())
							{
								progressDialog = ProgressDialog.show(currentActivity, "Connecting to voice call","Connecting");
							}
							
							ovxView.call(); // Initiates call and starts a session with the specified OVX group id and other parameters set earlier.
							tgl.setEnabled(false);

						} catch (OVXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					else { // If call is already started
						CharSequence[] ch = { "Call is already on" };
						showDialog("Warning", ch);
					}

				}
			});
			*/

			// Bind the end call event to the click of end_call button

			/*
			Button ovx_end_call = (Button) findViewById(R.id.end_call);
			ovx_end_call.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (ovxView.isCallOn()) { // 
						ovxView.exitCall(); // ends the existing call and removes the user from the live conference.
					} 

				}
			});
			*/

	/* To start the recorded voice/video call  ovxVideoRecord api is invoked 
	 * by setting  flag to true.This method checks, if call is on and records. 
	 * if Call is not  on, request is ignored
	 * 
	 *  */		
			/*
			Button strt_rec=(Button)findViewById(R.id.start_record);
			strt_rec.setOnClickListener(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
			if(ovxView.isCallOn())		
				{	
					try 
					{
						ovxView.ovxVideoRecord(true);
					}
					catch (OVXException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
				else // shows a warning dialog if user tries to call
					{
						CharSequence[] ch = { "Start or join a call first" };
						showDialog("Warning", ch);
					}	
				}
			});
			*/
			
	/* To stop the recorded voice/video call  ovxVideoRecord api is invoked 
		 * by setting flag to false.This method checks, if call is on and records. 
			 * if Call is not  on, request is ignored
			 * 
			 *  */	
			/*
			Button stop_rec=(Button)findViewById(R.id.stop_record);
			stop_rec.setOnClickListener(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
			if(ovxView.isCallOn())		
				{	
					try 
					{
						if(ovxView.isRecordingEnabled())
							Toast.makeText(currentActivity, "Click on Recorded view Button to view the recordedvideo", Toast.LENGTH_LONG).show();
						ovxView.ovxVideoRecord(false);
						
					}
					catch (OVXException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else // shows a warning dialog if user tries to call
				{
					CharSequence[] ch = { "Start or join a call first" };
					showDialog("Warning", ch);
				}
				}
			});
			*/
	
			
			/* On click of switch_layer button a dialog is launched with an option to focus on a particular 
			 * participant in the conference. 
			 */
			/*
			Button ovx_switch = (Button) findViewById(R.id.switch_layer);
			ovx_switch.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (ovxView.isCallOn()) {
						
							loadSwitchDialog(); // launches the dialog with switch layer options.  
						
					}

					else // shows a warning dialog if user tries to call
					{
						CharSequence[] ch = { "Start or join a call first" };
						showDialog("Warning", ch);
					}

				}
			});
			*/

			// Dialog for Setting Group Id..
			/*
			Button setgrpid = (Button) findViewById(R.id.setgrpid);
			setgrpid.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					sharelayout();  //Enter the GroupId which u want to set in this dialog..

				}
			});
			*/
			// Setting Audio Stream to other parties in the conference based on
			// flag..

			/*
			Button audio_mute = (Button) findViewById(R.id.audio_mute);
			audio_mute.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (ovxView.isCallOn())
						ovxView.setAudioMute(!ovxView.isAudioMuteOn()); // flag
																		// based
																		// on if
																		// muted
																		// or
																		// not.
					else {
						CharSequence[] ch = { "Start or join a call first" };
						showDialog("Warning", ch);
					}

				}
			});
			*/
			
			/*
			Button switchCamera = (Button) findViewById(R.id.switch_camera);
			switchCamera.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// *
					// * This allows you to switch between the back and front camera and send video to the other
					// *  participants in the video conference captured by either your back or front camera. 
					// *  Switch is disabled in case device has single camera 
					//
					if (ovxView.isCallOn())
						ovxView.switchCamera();
					else {
						CharSequence[] ch = { "Start or join a call first" };
						showDialog("Warning", ch);
					}

				}
			});
			*/
			
			/*
			Button video_mute = (Button) findViewById(R.id.video_mute);
			video_mute.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					// checks if the video is muted and will mute if not muted or will undo mute if muted 
					
					if (ovxView.isCallOn())
						try {
							ovxView.setVideoMute(!ovxView.isVideoMuteOn());
						} catch (OVXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					else 
					{
						CharSequence[] ch = { "Start or join a call first" };
						showDialog("Warning", ch);
					}

				}
			});
			*/
			
			
			/*
			Button update_orient = (Button) findViewById(R.id.orientation);
			update_orient.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					//
					// * Should be called to update the dimensions and position
					// * of the video view that had been changed after the call was started or 
					// * to resume the video stream if it had been paused while launching another activity.  
					// 
					
					if (ovxView.isCallOn())
						ovxView.updateVideoOrientation();
					else 
					{
						CharSequence[] ch = { "Start or join a call first" };
						showDialog("Warning", ch);
					}

				}
			});
			*/
			 
			/*
			 * This feature allows you to dial an msisdn and 
			 * add it to the existing conference.
			 * 
			 */
			/*
			Button addFriends = (Button) findViewById(R.id.add_friends);
			addFriends.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					
					if (ovxView.isCallOn())
					{
						dialout_dialog();
					}
					else {
						CharSequence[] ch = { "Start or join a call first" };
						showDialog("Warning", ch);
					}
				}
			});
			*/
			// Binding send message events to send_image button
			/*
			Button send_image = (Button) findViewById(R.id.send_image);
			send_image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					//
					 // we can use this to send data to other parties in the conference. 
					 // * The first parameter is the message type which in this case is an image,
					// * the second parameter contains the data.
					// * It is up to the developer to 
					// * render the link as an image on a view.   
					// /
					
					if (ovxView.isCallOn()) {

						try {
							ovxView.sendData("image",
									"http://zkychat.com/img/openclove-color.png");
						} catch (OVXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						chat_box.append("\n" + ovxView.getOvxUserName() + " : "
								+ "http://zkychat.com/img/openclove-color.png");
						// Function for auto scrolling of text in TextView..
						focusOnText();
						
					}

					else
					{
						CharSequence[] ch = { "Start or join a call first" };
						showDialog("Warning", ch);
					}

				}
			});
			*/
			// For sharing URL
			/*
			Button send_link = (Button) findViewById(R.id.send_link);
			send_link.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (ovxView.isCallOn()) {

												
						// *
						// * We can use this to send data to other parties in the conference. 
						// * The first parameter is the message type which in this case is a link,
						// * the second parameter contains the data.
						// * It is up to the developer to 
						// * display the link any way he wants.   
						// 
						
						try {
							ovxView.sendData("link", "http://www.google.com");
						} catch (OVXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// Appending link to the chat_box textView..
						chat_box.append("\n" + ovxView.getOvxUserName() + " : "
								+ "http://www.google.com");

						focusOnText();

					} else {
						CharSequence[] ch = { "Start or join a call first" };
						showDialog("Warning", ch);
					}

				}
			});
			*/
			
			//To View the Recorded url
			/*
			  Button rec_vid=(Button)findViewById(R.id.rec_video);
			  rec_vid.setOnClickListener(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					
					if(chk_recordedvideo)
					{
							if(videourl!=null)
							{
								
								
								Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videourl));
								startActivity(intent);
								
							}
					}
					
					else
					{
						CharSequence[] ch = { "No Recorded Conference file Available" };
						showDialog("Warning", ch);
					}
					
				}
			});
			  */

			// When performing action on EditText..

			/*  

			ovx_text = (EditText) findViewById(R.id.chat_text);
			ovx_text.setTextColor(Color.BLACK);

			ovx_text.setOnEditorActionListener(new OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView v, int actionId,
						KeyEvent event) {

					if (!ovx_text.getText().toString().trim().equals("")
							&& ovxView.isCallOn()) {

						//
						// * We can use this to send data to other parties in the conference. 
						// * The first parameter is the message type which in this case is a link, 
						// * the second parameter contains the data.
						// * It is up to the developer to 
						// * display the data any way he wants.   
						// 

						try {
							ovxView.sendData("chat", ovx_text.getText().toString());
						} catch (OVXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						chat_box.append("\n" + ovxView.getOvxUserName() + " : "
								+ ovx_text.getText().toString());
						ovx_text.setText("");

						focusOnText();

					}
					return true;

				}

			});
			*/
			
			/* This method contains implementation of 
			 * OVX call listeners. Look into the method
			 * definition for further description
			 * 
			 */
			callListener();

			
			/*Once call is started the scroll event will not take place when user tries to scroll through the 
			*text box since it has its own custom scroll. Users will have to scroll through the sides(regions other than the chat text box)
			*/
			/*
			ScrollView scroll_layout = (ScrollView) findViewById(R.id.scroll_layout);

			scroll_layout.setOnTouchListener(new View.OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					// Log.d("INDUS", "PARENT TOUCH");
					findViewById(R.id.chat_text_box).getParent()
							.requestDisallowInterceptTouchEvent(false);
					return false;
				}
			});

			
			chat_box.setOnTouchListener(new View.OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					// Log.v("INDUS", "CHILD TOUCH");
					// Disallow the touch request for parent scroll on touch of
					// child view
					v.getParent().requestDisallowInterceptTouchEvent(true);
					return false;
				}
			});
			*/

		} catch (OVXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	 

	//Mandatory to call updateVideoorientation in onwindowfocus to update the video  

	/*
	 * Should be called to update the dimensions and position
	 * of the video view that had been changed after the call was started or 
	 * to resume the video stream if it had been paused while launching the activity.  
	 */
		/*
		@Override
		public void onWindowFocusChanged(boolean hasFocus)
		{
			// TODO Auto-generated method stub
			if(hasFocus)
				if(ovxView.isCallOn())
					ovxView.updateVideoOrientation();
			super.onWindowFocusChanged(hasFocus);
		}


		// An input dialog that allows you to input string to set OVX group id of your choice.
		protected void sharelayout() {

		LayoutInflater factory = LayoutInflater.from(this);
		final View entry = factory.inflate(R.layout.shr, null);
		final EditText edit = (EditText) entry.findViewById(R.id.edt);

		edit.setText("", TextView.BufferType.EDITABLE);

		AlertDialog.Builder lmenu = new AlertDialog.Builder(this);

		lmenu.setTitle("Enter the GroupId :").setView(entry);

		lmenu.setPositiveButton("Set", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int whichButton) {

				groupid = edit.getText().toString().trim();

				if (groupid != null || !groupid.trim().equals("")) {
					try {
						// Sets the user input string as OVX group id. 
						ovxView.setKeyValue("ovx-session",groupid); 

					} catch (OVXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					text_gid.setText("Current Group ID : "
							+ ovxView.getOvxGroupId());

				}

			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				dialog.cancel();
			}
		});
		lmenu.show();
		
	}*/

	

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// don't reload the current page when the orientation is changed
		Log.d("INDUS", "onConfigurationChanged() Called");
		super.onConfigurationChanged(newConfig);
		try {
			ovxView.setRemoteViewX(100);
			ovxView.setRemoteViewY(100);

		} catch (OVXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * Should be called to update the dimensions and position
		 * of the video view that had been changed after the call was started or 
		 * to resume the video stream if it had been paused while launching another activity.  
		 */
		
		ovxView.updateVideoOrientation();

	}

	@Override
	public void onDestroy() {
	
		
		//exit the call before destroying the activity
		ovxView.exitCall();
		
		//and free the resources used by OVX context
		ovxView.unregister();		

		android.os.Process.killProcess(android.os.Process.myPid());
		super.onDestroy();
	}

	// generic dialog used to display messages

	public void showDialog(String title, CharSequence[] items) {

		final CharSequence[] fitems = items;

		AlertDialog.Builder lmenu = new AlertDialog.Builder(this);

		final AlertDialog ad = lmenu.create();
		lmenu.setTitle(title);
		lmenu.setMessage(fitems[0]);
		lmenu.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				ad.dismiss();
			}
		});
		lmenu.show();
	}

	/* Opens the dialog with buttons containing composite layer and layers numbered 1-9. These correspond 
	*to the users in the conference, click of the layered buttons will focus on a particular participant in the conference.
	*Click of the composite layer button will show all the participants in the conference.   
	*/
	private void loadSwitchDialog() {
		/*
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.switch_dialog);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(true);

		Button composite = (Button) dialog.findViewById(R.id.composite_layer);

		composite.setOnClickListener(new OnClickListener() {

			

			@Override
			public void onClick(View v) {

				if (ovxView.isCallOn())
					try {
						// Switches to composite Layer which contains layers of the all participants in the conference,
						 // after switching the the video view will stream the video feed of all participants.
						 //
						ovxView.switchLayer("0"); 
						
						
					} catch (OVXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				dialog.dismiss();
			}
		});
		
		Button layer1 = (Button) dialog.findViewById(R.id.layer1);

		layer1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (ovxView.isCallOn())
					try {
											
						//Switches to the first participant of the video conference and 
						//after switching the video view will stream the video feed of only first participant.  
						//
						ovxView.switchLayer("1");

					} catch (OVXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				dialog.dismiss();
			}
		});

		Button layer2 = (Button) dialog.findViewById(R.id.layer2);

		layer2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (ovxView.isCallOn())
					try {
						//Switches to the second participant of the video conference and 
						//after switching the video view will stream the video feed of only the second participant.  
						//

						ovxView.switchLayer("2");
					} catch (OVXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				dialog.dismiss();
			}
		});

		Button layer3 = (Button) dialog.findViewById(R.id.layer3);

		layer3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (ovxView.isCallOn())
					try {

						//Switches to the third participant of the video conference and 
						//after switching the video view will stream the video feed of only the third participant.  
						//

						ovxView.switchLayer("3");
					} catch (OVXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				dialog.dismiss();
			}
		});

		Button layer4 = (Button) dialog.findViewById(R.id.layer4);

		layer4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (ovxView.isCallOn())
					try {
						//Switches to the fourth participant of the video conference and 
						//after switching the video view will stream the video feed of only the fourth participant.  
						//

						ovxView.switchLayer("4");
					} catch (OVXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				dialog.dismiss();
			}
		});

		Button layer5 = (Button) dialog.findViewById(R.id.layer5);

		layer5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (ovxView.isCallOn())
					try {
						//Switches to the fifth participant of the video conference and 
						//after switching the video view will stream the video feed of only the fifth participant.  
						//

						ovxView.switchLayer("5");
					} catch (OVXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				dialog.dismiss();
			}
		});

		Button layer6 = (Button) dialog.findViewById(R.id.layer6);

		layer6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (ovxView.isCallOn())
					try {
						//Switches to the sixth participant of the video conference and 
						//after switching the video view will stream the video feed of only the sixth participant.  
						//

						ovxView.switchLayer("6");
					} catch (OVXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				dialog.dismiss();
			}
		});

		Button layer7 = (Button) dialog.findViewById(R.id.layer7);

		layer7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (ovxView.isCallOn())
					try {
						//Switches to the seventh participant of the video conference and 
						//after switching the video view will stream the video feed of only the seventh participant.  
						//
						ovxView.switchLayer("7");
					} catch (OVXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				dialog.dismiss();
			}
		});

		Button layer8 = (Button) dialog.findViewById(R.id.layer8);

		layer8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (ovxView.isCallOn())
					try {
						//Switches to the eigth participant of the video conference and 
						//after switching the video view will stream the video feed of only the eigth participant.  
						//
						ovxView.switchLayer("8");
					} catch (OVXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				dialog.dismiss();
			}
		});
		
		Button layer9 = (Button) dialog.findViewById(R.id.layer9);

		layer9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (ovxView.isCallOn())
					try {
						//Switches to the ninth participant of the video conference and 
						//after switching the video view will stream the video feed of only the ninth participant.  
						
						ovxView.switchLayer("9");
					} catch (OVXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				dialog.dismiss();
			}
		});
		*/
		// To Cancel the switch Dialog..

		/*
		View cancelBtn = dialog.findViewById(R.id.cancel);
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
		*/

	}


	 /* 
	 * We can listen to call related events like call started , call ended , call failed and perform appropriate actions 
	 * in these callback functions, we can also receive messages sent from the other parties in the conference using the call listener. 
	 */
	public void callListener() {
		

		final Activity a = this;
		
		/** call back listener to listen to call events  */
		ovxView.setCallListener(new OVXCallListener() {

			
			//invoked when the call has been disconnected by the user.
			@Override
			public void callEnded() {
				// TODO Auto-generated method stub
				
				Log.d("INDUS", "Call Ended ");

				//chat_box.setText("");
				//chat_box.setHint("Welcome to openclove");
				//tgl.setEnabled(true);
				if (notmgr != null)
				     notmgr.cancel(NOTIFY_ME_ID);
				
			
			}

			// invoked when the call fails due to some reasons.

			@Override
			public void callFailed() {
				// TODO Auto-generated method stub
				//chat_box.clearComposingText();
				//chat_box.setHint("Welcome to openclove");
				//tgl.setEnabled(true);
			}

			// invoked when the call has been established.

			@Override
			public void callStarted()
			{
			
				chk_recordedvideo=false;
				
				if(progressDialog != null)
				{
					progressDialog.dismiss();
					progressDialog=null;
				}
				
				if(ovxView.isVoiceProfileEnabled())
				    //launchNtfctn();
				
				//tgl.setEnabled(false);
				
				Log.d("INDUS", "Call Started");
			}

			/* Invoked when messages are sent from other parties in the conference and 
			*the server as notifications.
			*/
			/*
			@Override
			public void ovxReceivedData(String arg0)
			{
				Uri uri = Uri.parse("http://dummyserver.com?" + arg0);
				String type = uri.getQueryParameter("type");
				String data = uri.getQueryParameter("data");
				String sender = uri.getQueryParameter("sender");
				// TODO Auto-generated method stub
				Log.d("INDUS", "Received message from ac_server:" + arg0);
				chat_box.setMovementMethod(new ScrollingMovementMethod());

				

				if (chat_box.getText().toString()
						.equals("Welcome to openclove"))
					chat_box.setText(sender + " : " + data);
				else
					chat_box.append("\n" + URLDecoder.decode(sender) + " : " + URLDecoder.decode(data));

				chat_box.setTextColor(Color.BLACK);
				focusOnText();
				
				
			}
			*/

			//invoked when the call has been terminated due to n/w issues.
			@Override
			public void callTerminated(String arg0) 
			{
				// TODO Auto-generated method stub
				//chat_box.clearComposingText();
				//chat_box.setHint("Welcome to openclove");
				//tgl.setEnabled(true);
			}

			@Override
			public void recordedCallStart()
			{
				// TODO Auto-generated method stub
				Log.d("INDUS","Recording Started");
				Toast.makeText(currentActivity, "Recording Started",Toast.LENGTH_LONG).show();
				
			}

			@Override
			public void recordedCallStop(String arg0)
			{
				// TODO Auto-generated method stub
				
				videourl=arg0;
			
				Log.d("INDUS","Recorded Url.. "+videourl);
				
				chk_recordedvideo=true;
				
				// Call HTTP Post here
				uploadURL(videourl);
				
			}

			@Override
			public void onNotificationEvent(String arg0, String arg1)
			{
				// TODO Auto-generated method stub
				String event_type=arg0;
				String data_url=arg1;
				
				if(event_type.equals("broadcastUrl"))
				{
					Log.d("OVX","Broadcast url:"+data_url);
					//chat_box.append("\n" + "Broadcast url"+ " : " + data_url);
				}
				
			}

			@Override
			public void handlePeerMessage(String arg1)
			{
		
				Log.d("INDUS","Peer Message:"+arg1);
			}

			@Override
			public void handleServerMessage(String arg0)
			{
				// TODO Auto-generated method stub
				Log.d("INDUS","Server Register Response"+arg0);
				
			}

			@Override
			public void ovxReceivedData(String arg0) {
				// TODO Auto-generated method stub
				
			}

		});

	}

	/*
	protected void dialout_dialog()
	{
		// TODO Auto-generated method stub
		LayoutInflater factory = LayoutInflater.from(this);
		final View entry = factory.inflate(R.layout.shr, null);
		final EditText edit = (EditText) entry.findViewById(R.id.edt);

		edit.setText("", TextView.BufferType.EDITABLE);
		edit.setHint("Please Enter Countrycode-Telephonenumber");

		AlertDialog.Builder lmenu = new AlertDialog.Builder(this);

		lmenu.setTitle("Telephone Number:").setView(entry);

		lmenu.setPositiveButton("Dial-Out",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int whichButton) {

						String msisdn = edit.getText().toString().trim();

				
					
						if (msisdn != null && !msisdn.equals("")) {
							try {
								// Look at method definition for further description
								ccode=msisdn.split("-")[0];
								phn_number=msisdn.split("-")[1];
								
								ovxView.setKeyValue("ovx-cc", ccode);//set country code
								
								ovxView.setKeyValue("ovx-number", phn_number);//set msisdn
								
								// allows you to dial the number set as ovx-cc attribute and ovx-number during the conference 
								
								ovxView.setKeyValue("ovx-dialout", "enable");//disable by default
								
								
								 ovxView.addOtherUserToGroupChat("voice", "TEL", ccode+phn_number);
								
								Log.d("INDUS", "msisdn number::" + msisdn);

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							CharSequence[] ch = { "Please Enter the correct number to make call" };
							showDialog("Warning", ch);
							Log.d("INDUS", "msisdn number::" + msisdn);
						}

					}
				}).setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						dialog.cancel();
					}
				});
		lmenu.show();

	}
	*/

	
	@Override
	public void onResume() {

		super.onResume();
		Log.d("INDUS", "OnResume:" + this);


	}
	
	
	
	@Override
	public void onPause() {

		super.onPause();
		Log.d("INDUS", "On Pause");

	}
	
	/*
	private void launchNtfctn() {
		  // TODO Auto-generated method stub
		  notmgr = (NotificationManager) currentActivity.getSystemService(NOTIFICATION_SERVICE);
		  
		  Intent i = new Intent(currentActivity, OVXChat.class);
		  
		  PendingIntent pi = PendingIntent.getActivity(this, 1, i, 0);

		  Vibrator vb = (Vibrator) currentActivity.getSystemService(VIBRATOR_SERVICE);
		  
		  long[] pattern = { 0, 100, 600, 100, 700 };
		  
		  vb.vibrate(pattern, -1);
		 
		  noti = new Notification(R.drawable.phoneicon, "Voice Call ....",System.currentTimeMillis());

		  
		  String sms = getSharedPreferences("SMS_Pref", MODE_PRIVATE).getString("incoming", "");// to get The text in the contentView
		  
		  noti.setLatestEventInfo(getApplicationContext(), "OVX Voice Call", sms, pi);
		  
		  // noti.flags |= Notification.FLAG_AUTO_CANCEL;
		  
		  noti.number += 5;// number of times to show the icon in the status bar
		  
		  noti.flags |= Notification.FLAG_SHOW_LIGHTS;// How the notifcations
		             // should be like in the
		             // seeker content after
		             // clicking ...
		  notmgr.notify(NOTIFY_ME_ID, noti);// Using Mgr to show the
		           // notifications...

	}
	*/
	/*
	private void focusOnText()
	{
		// append the new string

		// find the amount we need to scroll. This works by
		// asking the TextView's internal layout for the position
		// of the final line and then subtracting the TextView's height
		final int scrollAmount = chat_box.getLayout().getLineTop(
				chat_box.getLineCount())
				- chat_box.getHeight();
		// if there is no need to scroll, scrollAmount will be <=0
		if (scrollAmount > 0)
			chat_box.scrollTo(0, scrollAmount);
		else
			chat_box.scrollTo(0, 0);
	}
	*/
	
	private void uploadURL(String urlLink){
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://50.161.194.10/Hello/uploadurl.php");
		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("url", urlLink));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String response = httpclient.execute(httppost, responseHandler);
			Log.e("GOT RESPONSE....",response);
		} catch (IOException e) {
			Log.e("PHP....",e.toString());
		}
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

}
