package com.launcher;

import java.io.IOException;
import java.util.List;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Collections;

import android.content.ContentResolver;
import dalvik.system.VMRuntime;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.net.Uri;
import android.util.Log;

import java.net.URLEncoder;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.content.ComponentName;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.net.wifi.WifiManager;
import android.net.ConnectivityManager;
import android.amlogic.Tv;
import android.view.View.OnTouchListener;
import android.service.wallpaper.WallpaperService;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;

import java.io.UnsupportedEncodingException;

import android.os.SystemProperties;
import android.view.View.OnFocusChangeListener;;
import android.amlogic.Tv;
import android.widget.VideoView;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceHolder;
import android.graphics.PixelFormat;
import android.graphics.Canvas;

public class SwitchViewDemoActivity extends Activity implements 
		Callback,OnViewChangeListener,OnGestureListener{
		
	private final static String TAG = "SwitchViewDemoActivity";	
	
	//tvPreview
	public static TvPreview mTvPreview;	
	//dock
	private ImageView pageBackground;
	//first page
	//the tvVideoView and the firstPageFirstLineIcon0 are 
	//in same position and with same size just z-order different
	private VideoView tvVideoView;
	public static ImageView firstPageFirstLineIcon0;
	private ImageView firstPageFirstLineIcon1;
	private ImageView firstPageFirstLineIcon2;
	private ImageView firstPageFirstLineIcon3;	
	private ImageView firstPageFirstLineIcon4;	
	private ImageView firstPageSecondLineIcon1;
	private ImageView firstPageSecondLineIcon2;
	private ImageView firstPageSecondLineIcon3;
	private ImageView firstPageSecondLineIcon4;		
	//second page
	private ImageView secondPageFirstLineIcon0;
	private ImageView secondPageFirstLineIcon1;
	private ImageView secondPageFirstLineIcon2;
	private ImageView secondPageSecondLineIcon1;
	private ImageView secondPageSecondLineIcon2;
	private ImageView secondPageSecondLineIcon3;
	private ImageView secondPageSecondLineIcon4;
	//third page
	private ImageView thirdPageFirstLineIcon1;
	private ImageView thirdPageFirstLineIcon2;
	private ImageView thirdPageFirstLineIcon3;
	private ImageView thirdPageFirstLineIcon4;
	private ImageView thirdPageFirstLineIcon5;
	private ImageView thirdPageSecondLineIcon1;
	private ImageView thirdPageSecondLineIcon2;
	private ImageView thirdPageSecondLineIcon3;
	private ImageView thirdPageSecondLineIcon4;
	private ImageView thirdPageSecondLineIcon5;
	//fourth page
	private ImageView fourthPageFirstLineIcon0;
	private ImageView fourthPageFirstLineIcon1;
	private ImageView fourthPageFirstLineIcon2;;
	private ImageView fourthPageSecondLineIcon1;
	private ImageView fourthPageSecondLineIcon2;
	//statusbarStatus
	private ImageView faceImageView;
	private ImageView ethernetImageView;
	private ImageView usbImageView;
	private ImageView wifiImageView;
	//statusbarWeather
	private TextView cityTextView;
	private TextView weatherTextView;
	private TextView tempTextView;
	//statusbarTime
	private TextView weekTextView;
	private TextView timeTextView;
	//concept screen
	private LinearLayout conceptScreen;
	//action of receiver
	private static final String ACTION_USB_MOUNTED = 	Intent.ACTION_MEDIA_MOUNTED;
	private static final String ACTION_USB_UNMOUNTED = Intent.ACTION_MEDIA_UNMOUNTED;
	private static final String ACTION_THTF_FACE_IDENTIFY = "com.thtfcd.face.swiths";
	private static final String ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
	private static final String ACTION_WIFI_CHANGE = "android.net.wifi.WIFI_STATE_CHANGED";
	private static final String ACTION_TIME_TICK = Intent.ACTION_TIME_TICK;
	private static final String ACTION_THTF_WEATHER = "com.amlogic.tv.requestStartTV";
	private static final String ACTION_THTF_START_TV  = "com.thtfcd.face.swiths";
	private static final String ACTION_THTF_START_APP = "com.amlogic.tv.requestStartApp";
	private GlobalReceiver gloableReceiver = null;
	private UsbReceiver usbReceiver = null;
	//messages
	private final int conceptScreenAppearMsg = 0x3001;
	private final int conceptScreenDisappearMsg = 0x3002;
	private final int greennetAppearMsg = 0x3003;
	private final int greennetDisappearMsg = 0x3004;	
	private final int showTvpreviewDelayTime = 1000;
	private final int handleUserAppMsg = 0x3005;	
	private final int startBrowserDelayTime = 5000;	
	//mouse flip condition
	private final int FLIP_DISTANCE = 300;
	//mouse support
	private GestureDetector detector;
	//record the basic needed process as reference
	private static List<ActivityManager.RunningAppProcessInfo> basicNeededProcess;
	//variables
	private int lastSource;	
	private int mViewCount;
	private final int TIME_OVER = 1;
	private final int maxSpeedOfFocusMove = 3;
	private static int resumeCount =1;
	private static int mCurSel;
	private static int mTvPriviewIndex = 0;
	private static int screenWidth = 1920;
	private MyScrollLayout mScrollLayout;
	private final static float TARGET_HEAP_UTILIZATION = 0.75f;
	private final static int HEAP_SIZE = 600 * 1024 * 1024;				
	private String StartPlayer = "com.amlogic.tvservice.startplayer";
	public static final String StartPlayDTV = "com.launcher.play.dtv";	
	private StartPlayerHandler  mystartPlayerHandler = null;	
	private static boolean   first_preview_start_atv = true;	
	//the source menu state , hide or show	
	private String menuState;
	private String lastMenuState = "false";
	private boolean menuStateChanged = false;	
	//disable remote control
	private static final String REMOTE_DISABLE = "wab 0x14 0 0";
	//enable remote control
	private static final String REMOTE_ENABLE = "wab 0x14 0 1";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workspace);
		VMRuntime.getRuntime().setMinimumHeapSize(HEAP_SIZE);
		VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION);
		init();

		int port = 0;
		if(mTvPreview.tv != null)
			port = mTvPreview.tv.SSMReadTVPortal() ;
			Log.v(TAG,"SSMReadTVPortal " + port);
			if(port == Tv.SrcInput.MPEG.ordinal() &&  first_preview_start_atv &&  (mTvPreview.tv.SSMReadLastSelectSourceType()==0
				|| (mTvPreview.tv.SSMReadLastSelectSourceType()== Tv.SrcInput.DTV.toInt()))){
			mystartPlayerHandler = new StartPlayerHandler();
		    int flag = (mTvPreview.tv.SSMReadLastSelectSourceType()== Tv.SrcInput.TV.toInt() ? 1 : 2);
		 	Message msg = mystartPlayerHandler.obtainMessage(flag);
		 	int delayMillis = 6000;
		 	mystartPlayerHandler.sendMessageDelayed(msg, delayMillis);
		 	first_preview_start_atv = false;
		 	Log.v(TAG,"play preview window ");
		}			 
	}

	@Override
	protected void onStart(){
		super.onStart();
		inintVideoView();
	}
	
	private void init() {
		initIcons();
		registerAllReceiver();		
		initDock();		
		initGestureDetector();
		getBasicNeededProcess();
		forceStartSinaService();
		setSourceIcon();
	}
	
	private void initIcons(){
		faceImageView = (ImageView)findViewById(R.id.statusbar_face);
       usbImageView =(ImageView)findViewById(R.id.statusbar_usb);		
		conceptScreen = (LinearLayout) findViewById(R.id.conceptPic);
		wifiImageView = (ImageView)findViewById(R.id.statusbar_wifi);
		timeTextView = (TextView)findViewById(R.id.statusbar_time_time);
		//weekTextView = (TextView)findViewById(R.id.statusbar_time_week);		
		ethernetImageView=(ImageView)findViewById(R.id.statusbar_ethernet); 
		
		cityTextView = (TextView) findViewById(R.id.statusbar_weather_city);	
		tempTextView = (TextView) findViewById(R.id.statusbar_weather_temp);					
		weatherTextView = (TextView) findViewById(R.id.statusbar_weather_weather);

		pageBackground=(ImageView) findViewById(R.id.pageBackground);

		firstPageFirstLineIcon0 = (ImageView) findViewById(R.id.firstPage000ImageView);
		inintVideoView(R.id.firstPage000VideoView);
		firstPageFirstLineIcon1 = (ImageView) findViewById(R.id.firstPage101ImageView);
		firstPageFirstLineIcon2 = (ImageView) findViewById(R.id.firstPage102ImageView);
		firstPageFirstLineIcon3 = (ImageView) findViewById(R.id.firstPage102ImageView);
		firstPageFirstLineIcon4 = (ImageView) findViewById(R.id.firstPage104ImageView);
		firstPageSecondLineIcon1 = (ImageView) findViewById(R.id.firstPage201ImageView);
		firstPageSecondLineIcon2 = (ImageView) findViewById(R.id.firstPage202ImageView);
		firstPageSecondLineIcon3 = (ImageView) findViewById(R.id.firstPage203ImageView);		
		firstPageSecondLineIcon4 = (ImageView) findViewById(R.id.firstPage204ImageView);
		
		//special process for display GreenNet
		firstPageSecondLineIcon4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				firstPageSecondLineIcon4.setFocusableInTouchMode(true);
				firstPageSecondLineIcon4.requestFocus();
				firstPageSecondLineIcon4.requestFocusFromTouch();
				mScrollLayout.setFocusIconName("none");			
				startGreenNet();
			}
		});
		
		secondPageFirstLineIcon0 = (ImageView) findViewById(R.id.secondPage000ImageView);
		secondPageFirstLineIcon1 = (ImageView) findViewById(R.id.secondPage101ImageView);
		secondPageFirstLineIcon2 = (ImageView) findViewById(R.id.secondPage102ImageView);
		secondPageSecondLineIcon1 = (ImageView) findViewById(R.id.secondPage201ImageView);
		secondPageSecondLineIcon2 = (ImageView) findViewById(R.id.secondPage202ImageView);
		secondPageSecondLineIcon3 = (ImageView) findViewById(R.id.secondPage203ImageView);
		secondPageSecondLineIcon4 = (ImageView) findViewById(R.id.secondPage204ImageView);

		thirdPageFirstLineIcon1 = (ImageView) findViewById(R.id.thirdPage101ImageView);
		thirdPageFirstLineIcon2 = (ImageView) findViewById(R.id.thirdPage102ImageView);
		thirdPageFirstLineIcon3 = (ImageView) findViewById(R.id.thirdPage103ImageView);
		thirdPageFirstLineIcon4 = (ImageView) findViewById(R.id.thirdPage104ImageView);
		thirdPageFirstLineIcon5 = (ImageView) findViewById(R.id.thirdPage105ImageView);
		thirdPageSecondLineIcon1 = (ImageView) findViewById(R.id.thirdPage201ImageView);
		thirdPageSecondLineIcon2 = (ImageView) findViewById(R.id.thirdPage202ImageView);
		thirdPageSecondLineIcon3 = (ImageView) findViewById(R.id.thirdPage203ImageView);
		thirdPageSecondLineIcon4 = (ImageView) findViewById(R.id.thirdPage204ImageView);
		thirdPageSecondLineIcon5 = (ImageView) findViewById(R.id.thirdPage205ImageView);

		fourthPageFirstLineIcon0 = (ImageView) findViewById(R.id.fourthPage000ImageView);
		fourthPageFirstLineIcon1 = (ImageView) findViewById(R.id.fourthPage101ImageView);
		fourthPageFirstLineIcon2 = (ImageView) findViewById(R.id.fourthPage102ImageView);
		fourthPageSecondLineIcon1 = (ImageView) findViewById(R.id.fourthPage201ImageView);		
		fourthPageSecondLineIcon2 = (ImageView) findViewById(R.id.fourthPage202ImageView);
	}

	private void registerAllReceiver(){
		if(gloableReceiver == null){
			IntentFilter filter = new IntentFilter(ACTION_THTF_FACE_IDENTIFY);			
			filter.addAction(ACTION_CONNECTIVITY_CHANGE);
			filter.addAction(ACTION_WIFI_CHANGE);
			filter.addAction(ACTION_TIME_TICK);
			filter.addAction(ACTION_THTF_WEATHER);
			filter.addAction(ACTION_THTF_START_TV);
			filter.addAction(ACTION_THTF_START_APP);
			
			gloableReceiver = new GlobalReceiver();
			registerReceiver(gloableReceiver, filter);
		}
		if(usbReceiver == null){
			IntentFilter filter = new IntentFilter(ACTION_USB_MOUNTED);
			filter.addAction(ACTION_USB_UNMOUNTED);
			filter.addDataScheme("file");
			usbReceiver = new UsbReceiver();
			registerReceiver(usbReceiver, filter);
		}
	}

	public void inintVideoView(int resourceId){
		tvVideoView= (VideoView) findViewById(resourceId);
	}
	
	public void inintVideoView(){
		tvVideoView.getHolder().addCallback(this);
       tvVideoView.getHolder().setFormat(PixelFormat.VIDEO_HOLE_REAL);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		initSurface(holder);
		Log.d(TAG, "====surfaceChanged====");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		initSurface(holder);
		Log.d(TAG, "====surfaceCreated====");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "====surfaceDestroyed====");
	}

	private void initSurface(SurfaceHolder h) {
		Canvas c = null;
		try {
			Log.d(TAG, "initSurface");
			c = h.lockCanvas();
		} finally {
			if (c != null)
			h.unlockCanvasAndPost(c);
		}
	}

	private void initDock(){
		mTvPreview = new TvPreview(getApplicationContext());				
		mScrollLayout = (MyScrollLayout) findViewById(R.id.ScrollLayout);		
		mViewCount = mScrollLayout.getChildCount();
		mCurSel = 0;
		pageBackground.setBackgroundResource(R.drawable.page1);
		
		//set the default page property   
	    SystemProperties.set("tv.launcher_page", "0");             		
		mScrollLayout.SetOnViewChangeListener(this);
	}

	private void initGestureDetector(){
		detector = new GestureDetector(this);
		firstPageFirstLineIcon0.setOnTouchListener(mOnTouchListener);		
		firstPageFirstLineIcon1.setOnTouchListener(mOnTouchListener);		
		firstPageFirstLineIcon2.setOnTouchListener(mOnTouchListener);
		firstPageFirstLineIcon3.setOnTouchListener(mOnTouchListener);
		firstPageFirstLineIcon4.setOnTouchListener(mOnTouchListener);
		firstPageSecondLineIcon1.setOnTouchListener(mOnTouchListener);
		firstPageSecondLineIcon2.setOnTouchListener(mOnTouchListener);
		firstPageSecondLineIcon3.setOnTouchListener(mOnTouchListener);
		firstPageSecondLineIcon4.setOnTouchListener(mOnTouchListener);

		secondPageFirstLineIcon0.setOnTouchListener(mOnTouchListener);
		secondPageFirstLineIcon1.setOnTouchListener(mOnTouchListener);
		secondPageFirstLineIcon2.setOnTouchListener(mOnTouchListener);
		secondPageSecondLineIcon1.setOnTouchListener(mOnTouchListener);
		secondPageSecondLineIcon2.setOnTouchListener(mOnTouchListener);
		secondPageSecondLineIcon3.setOnTouchListener(mOnTouchListener);
		secondPageSecondLineIcon4.setOnTouchListener(mOnTouchListener);

		thirdPageFirstLineIcon1.setOnTouchListener(mOnTouchListener);
		thirdPageFirstLineIcon2.setOnTouchListener(mOnTouchListener);
		thirdPageFirstLineIcon3.setOnTouchListener(mOnTouchListener);
		thirdPageFirstLineIcon4.setOnTouchListener(mOnTouchListener);
		thirdPageFirstLineIcon5.setOnTouchListener(mOnTouchListener);
		thirdPageSecondLineIcon1.setOnTouchListener(mOnTouchListener);
		thirdPageSecondLineIcon2.setOnTouchListener(mOnTouchListener);
		thirdPageSecondLineIcon3.setOnTouchListener(mOnTouchListener);
		thirdPageSecondLineIcon4.setOnTouchListener(mOnTouchListener);
		thirdPageSecondLineIcon5.setOnTouchListener(mOnTouchListener);

		fourthPageFirstLineIcon0.setOnTouchListener(mOnTouchListener);
		fourthPageFirstLineIcon1.setOnTouchListener(mOnTouchListener);
		fourthPageFirstLineIcon2.setOnTouchListener(mOnTouchListener);
		fourthPageSecondLineIcon1.setOnTouchListener(mOnTouchListener);
		fourthPageSecondLineIcon2.setOnTouchListener(mOnTouchListener);
	}
	
	private void updateStatus() {
		updateWifiStatus();
		updateUsbStatus();
		updateTimeStatus();
		if (mCurSel == mTvPriviewIndex ) {
			StartTvPreviewHandler.postDelayed( StartTvPreviewRunnable,200);
			//mTvPreview.startTvPreview(firstPageFirstLineIcon1,mCurSel * screenWidth);
		}else if( mScrollLayout.resumeFromAtvScreen ){
			//delay for showing concept screen
		    //SetVideoSizeHandler.postDelayed( SetVideoSizeRunnable,2000);
		}  
	}

	private void startGreenNet(){
		//close the tvpreview window	
		//mTvPreview.SetWindowSize(0 , 0 , 0 , 0 , 0);		
		appearGreennet();
		
		String packageName = "com.android.browser";
		final Intent intent = this.getPackageManager().getLaunchIntentForPackage(packageName);
		
		// delay 2 seconds for display GreenNet then start browser
		new Timer().schedule(new TimerTask(){
			@Override
			public void run() {
				mScrollLayout.resumeFromAtvScreen = false;			
				mScrollLayout.releaseFirstThenStartApk(intent);		
			}
		}, showTvpreviewDelayTime * 2 );
		
	}
	
	//display conceptScreen for 2 second
	private void appearConceptScreen(){
	 	Message appearConceptScreenMsg = new Message();
		appearConceptScreenMsg.what = conceptScreenAppearMsg;
		conceptScreenHandler.sendMessage(appearConceptScreenMsg);

		new Timer().schedule(new TimerTask(){
			@Override
			public void run() {
			 	Message disAppearConceptScreenMsg = new Message();
				disAppearConceptScreenMsg.what = conceptScreenDisappearMsg;
				conceptScreenHandler.sendMessage(disAppearConceptScreenMsg);
				mScrollLayout.resumeFromAtvScreen = false;
				mScrollLayout.inAtvScreen = false;
				if(mCurSel == mTvPriviewIndex ){
					if(!SystemProperties.getBoolean("persist.tv.factory_aging_mode", false )){
						Log.d(TAG,"not in aging mode");//add this for aging mode remote cannot use
						//enable remote control
						mTvPreview.SetRegBit(REMOTE_ENABLE);
					}
					//delay for source change  completed
					setSourceIconAfterResume(1000);	
				}				
				Log.d(TAG,"Send concetpDisappear Command");
			}
			
		}, showTvpreviewDelayTime * 2);		
	}

	//display when start internetBrowser
	public void appearGreennet(){		
		mTvPreview.SetRegBit(REMOTE_DISABLE);
	 	Message appearGreennetMsg = new Message();
		appearGreennetMsg.what = greennetAppearMsg;
		greenNetScreenHandler.sendMessage(appearGreennetMsg);
		Log.d(TAG,"Send greennetAppear Command");
		//set the page property	if (launcher not in first page){hide the tvprevie source icon}
		SystemProperties.set("tv.launcher_page", "8888");	
		
		new Timer().schedule(new TimerTask(){
			@Override
			public void run() {
			 	Message greennetMsg = new Message();
				greennetMsg.what = greennetDisappearMsg;
				greenNetScreenHandler.sendMessage(greennetMsg);
				mTvPreview.SetRegBit(REMOTE_ENABLE);
				Log.d(TAG,"Send greennetDisappear Command");
			}
			
		}, startBrowserDelayTime);		
	}

	Handler conceptScreenHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	if(msg.what == conceptScreenAppearMsg){
				conceptScreen.setBackgroundResource(R.drawable.concept);
				conceptScreen.setVisibility(View.VISIBLE);
				Log.d(TAG,"appearConceptScreen");
			}else if(msg.what == conceptScreenDisappearMsg){
				conceptScreen.setVisibility(View.GONE);
				Log.d(TAG,"disConceptScreen");			
			}
	        super.handleMessage(msg);
	    }
	};

	Handler greenNetScreenHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	if(msg.what == greennetAppearMsg){
			conceptScreen.setBackgroundResource(R.drawable.greennet);
			conceptScreen.setVisibility(View.VISIBLE);
			Log.d(TAG,"appearConceptScreen");
		}else if(msg.what == greennetDisappearMsg){
			conceptScreen.setVisibility(View.GONE);
		}
	       super.handleMessage(msg);
	    }
	};
	
	private void updateEthernetStatus(){
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET).isConnected()){
			ethernetImageView.setVisibility(View.VISIBLE);
		}else{
			ethernetImageView.setVisibility(View.GONE);
		}		
	}
	
	private void updateWifiStatus(){
		WifiManager wifimanager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		if(wifimanager.isWifiEnabled()){
			Log.d(TAG,"wifi Enabled");
			wifiImageView.setVisibility(View.VISIBLE);
		}
		if(!wifimanager.isWifiEnabled()){
			Log.d(TAG,"wifi disabled");
			wifiImageView.setVisibility(View.GONE);
		}
	}
	
	private void updateUsbStatus(){
		File dir = new File("/storage/external_storage");
		boolean usbConnected = false;
		for (File file : dir.listFiles()) {
			usbConnected = false;
			String path = file.getAbsolutePath();
			if (path.startsWith("/storage/external_storage/sd") && !path.startsWith("/storage/external_storage/sdcard")) {
				usbConnected = true;
				break;
			}
		} 
		
		if (usbConnected) {
			usbImageView.setVisibility(View.VISIBLE);				
		}else{
			usbImageView.setVisibility(View.GONE);
		}		
		
	}

	private void updateTimeStatus(){
		int [] aWeek ={R.string.sunday,R.string.monday,R.string.tuesday,R.string.wednesday,
				R.string.thursday,R.string.friday,R.string.saturday };
		ContentResolver cv = this.getContentResolver();
		String strTimeFormat = android.provider.Settings.System.getString(cv,
				android.provider.Settings.System.TIME_12_24);
	        	        	        
		Calendar ca = Calendar.getInstance();
		String minute=String.valueOf(ca.get(Calendar.MINUTE));
		String hour;
		minute =  ca.get(Calendar.MINUTE) < 10  ?  "0" + minute  :  minute;
		if(strTimeFormat.equals("12")){
			hour=String.valueOf(ca.get(Calendar.HOUR));
			Log.d(TAG,"___12");
		}else{
			hour=String.valueOf(ca.get(Calendar.HOUR_OF_DAY));
			Log.d(TAG,"___24");	
		}		 
		//String WeekOfYear = this.getString( aWeek[ca.get( Calendar.DAY_OF_WEEK ) - 1] ) ;
		//weekTextView.setText(WeekOfYear);
		timeTextView.setText(hour + " : " + minute);
	}

	private void updateWeatherStatus(Intent intent){
		Bundle weather = intent.getExtras();
		String city = weather.getString("city");
		String wea = weather.getString("weather");
		String temp = weather.getString("temp");
		if( city != null ){
			cityTextView.setText(city);
			weatherTextView.setText(wea);
			tempTextView.setText(temp);
		}else{
			Log.d(TAG,"weather empty");
		}
	}

	private void updateFaceStatus(Intent intent){
		boolean data = false;
		if(intent != null){
			data = intent.getExtras().getBoolean("face_switchs");
		}else{
			Log.d(TAG,"Face Recogize Intent is null");
			return;
		}
		if( data){
			Log.d(TAG,"Recogize Scuess!");
			faceImageView.setVisibility(View.VISIBLE);
		}else{
			Log.d(TAG,"Recogize Failed!");		
			faceImageView.setVisibility(View.GONE);
		}
	}

	class UsbReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context arg0,Intent arg1){
			String action = arg1.getAction();
			
			if(ACTION_USB_MOUNTED.equals(action)){
				Log.d(TAG,"usb mounted");		  	 
				updateUsbStatus();	
			}else if(ACTION_USB_UNMOUNTED.equals(action)){
				Log.d(TAG,"usb unmounted");		  	 
				updateUsbStatus();	
			}			
		}
	}
	
	class GlobalReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context arg0, Intent arg1) {		
			String action = arg1.getAction();
			
			if(ACTION_THTF_FACE_IDENTIFY.equals(action)){
				Log.d(TAG,"face idetifing");
				updateFaceStatus(arg1);
			}else if(ACTION_CONNECTIVITY_CHANGE.equals(action)){
				Log.d(TAG,"Ethernet process");
				updateEthernetStatus();		
			}else if(ACTION_WIFI_CHANGE.equals(action)){
				Log.d(TAG,"wifi process");
				updateWifiStatus();	
			}else if(ACTION_TIME_TICK.equals(action)){
				Log.d(TAG,"Time process");
				updateTimeStatus();							
			}else if(ACTION_THTF_WEATHER.equals(action)){
				Log.d(TAG,"weather process");
				updateWeatherStatus(arg1);
			}else if(ACTION_THTF_START_TV.equals(action)){
				Log.d(TAG,"StartTv process");
				if( mTvPriviewIndex == 1 ){
					mScrollLayout.snapPrevious();
				}else if( mTvPriviewIndex == 2){
					mScrollLayout.atFourthPageSnapRight();
				}
			   	//mTvPreview.startTV();
			}else if(ACTION_THTF_START_APP.equals(action)){
				Log.d(TAG,"voice command process");
				processVoiceCommand(arg1);
			}
		}
	}

	//set source icon when the source menu display or hide
	private void setSourceIcon(){
		new Thread(new Runnable(){
			public void run(){
				while(true){
					try{
						Thread.sleep(100);
					}catch(Exception e){
						e.printStackTrace();
					}
					readMenuState();
					if(menuStateChanged){
						displayOrHideSourceIcon();
						menuStateChanged = false;
					}
				}  		
			}

		}).start();
	}

	private void readMenuState(){
		menuState = SystemProperties.get("tv.globalsetup_show_status");
		if(!menuState.equals(lastMenuState)){
			menuStateChanged = true;
			lastMenuState = menuState;
		}
	}

	private void displayOrHideSourceIcon(){
		if( mCurSel == mTvPriviewIndex && SystemProperties.get("tv.in_launcher").equals("true") ){
			//means menu displaying
			if( Boolean.valueOf(menuState) ){
				SystemProperties.set("tv.launcher_page", "xiangxing.wu"); 	
			}else{
				//write 2 times for ensure to set the property
				SystemProperties.set("tv.launcher_page", "0");					
				SystemProperties.set("tv.launcher_page", "0");					
				try {//set the source icon on tvpreview
					setSourceImage();
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
	}

	private void processVoiceCommand(Intent voiceCommand){
		Bundle startAppInformation = voiceCommand.getExtras();
		String packageName = startAppInformation.getString("packageName");
		String action = startAppInformation.getString("action");
		String className = startAppInformation.getString("className");
		String receiveMusicKeyWord = startAppInformation.getString("musicKeyWord");
		String newMusicKeyword = "";
		String URL = startAppInformation.getString("URL");
		/*
		try {//encode the musicKeyWord
			if( receiveMusicKeyWord != null){
				newMusicKeyword = URLEncoder.encode(receiveMusicKeyWord,"gb2312");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		*/
		if(className != null && className.equals("com.reconova.demo.SplashActivity") ){
			Intent intent = new Intent();
			intent.setClassName("com.reconova.tongfang", "com.reconova.demo.SplashActivity");
			mScrollLayout.releaseFirstThenStartApk(intent);
		}else if(className != null && className.equals("com.thtf.facerealize.FaceMainActivity") ){
			Intent intent = new Intent();
			intent.setClassName("com.reconova.tongfang", "com.thtf.facerealize.FaceMainActivity");
			mScrollLayout.releaseFirstThenStartApk(intent);
		}else if( URL != null ){//means it is kaikou shang wang
			Uri uri = Uri.parse(URL);
			Intent intent = new Intent(Intent.ACTION_VIEW,uri);
			mScrollLayout.releaseFirstThenStartApk(intent);			
		}else if( receiveMusicKeyWord != null ){//launch baidu to search the keyword of music
			Uri uri = Uri.parse( "http://mp3.baidu.com/m?word=" + receiveMusicKeyWord);
			Log.d(TAG,"_uri = " + uri);
			Intent intent = new Intent(Intent.ACTION_VIEW,uri);
			mScrollLayout.releaseFirstThenStartApk(intent);			
		}else if( packageName != null && packageName.equals("com.amlogic.bestv") ){//means it is BestTv or xingxingguo or search Moive
			String appCode = startAppInformation.getString("AppCode");
			if( appCode != null && appCode.equals( "kids" ) ){//means it is xingxingguo
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("AppCode","kids");
				intent.putExtras(bundle);
				intent.setClassName("com.amlogic.bestv", "com.amlogic.bestv.BesTVActivity");
				mScrollLayout.releaseFirstThenStartApk(intent);
			}else if(appCode != null && appCode.equals( "search" )){
				String movieName = startAppInformation.getString("movieKeyWord");
				Intent intent = new Intent();
				intent.setClassName("com.amlogic.bestv", "com.amlogic.bestv.BesTVActivity");				
				intent.putExtra("AppCode","search");
				if(movieName != null){
					intent.putExtra("keyword",movieName);
				}
				mScrollLayout.releaseFirstThenStartApk(intent);
			}else{//means it is besTV
				//-----------------------------------this variable is set for BesTv
				SwitchViewDemoActivity.mTvPreview.tv.SetBestvSoundCurveEnable(1);
				//----------------------------------------------------------			
				Intent intent = new Intent();
				intent.setClassName("com.amlogic.bestv", "com.amlogic.bestv.BesTVActivity");
				mScrollLayout.releaseFirstThenStartApk(intent);
			}
		}else if( packageName != null && packageName.equals("com.amlogic.filebrowser") ){//means it is mediaFileBrowser or Samba
			String launch_what = startAppInformation.getString("launch_what");
			//means it is mediFileBrowser
			if( launch_what != null && launch_what.equals("usb") ){
				killProcess(packageName);
				//delay 1 second to wait package "com.amlogic.filebrowser" has been killed
				new Timer().schedule(new TimerTask(){
					@Override
					public void run() {
						Intent intent = new Intent();
						intent.setClassName("com.amlogic.filebrowser", "com.amlogic.filebrowser.MediaFilebrowser");
						intent.putExtra("launch_what","usb");					
						mScrollLayout.releaseFirstThenStartApk(intent);
					}
					
				}, 1000);
			}else if(launch_what != null && launch_what.equals("samba")){//means it is samba
				killProcess(packageName);						
				new Timer().schedule(new TimerTask(){
					@Override
					public void run() {
						Intent intent = new Intent();
						intent.setClassName("com.amlogic.filebrowser", "com.amlogic.filebrowser.MediaFilebrowser");
						intent.putExtra("launch_what","samba");					
						mScrollLayout.releaseFirstThenStartApk(intent);
					}
					
				}, 1000);
			}
		}else if( packageName != null && packageName.equals("com.android.browser") ){
			startGreenNet();
		}else{//others only need packageName
			if( packageName != null ){
				Intent intent = this.getPackageManager().getLaunchIntentForPackage(packageName);
				mScrollLayout.releaseFirstThenStartApk(intent);
			}
		}			
	}

	private void setDefaultPoint(){
		pageBackground.setBackgroundResource(R.drawable.page1);
	}
		
	private void setCurPoint(int index) {
		if (index < 0 || index > mViewCount - 1 || mCurSel == index) {
			return;
		}
		
		pageBackground.setBackgroundResource(R.drawable.nothing);
		
		if(index == 0 ){
			pageBackground.setBackgroundResource(R.drawable.page1);
			//set the source icon on tvpreview
			try {
				setSourceImage();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (index == 1){
			pageBackground.setBackgroundResource(R.drawable.page2);
		}	
		if(index == 2){
			pageBackground.setBackgroundResource(R.drawable.page3);
		}
		if(index == 3){
			pageBackground.setBackgroundResource(R.drawable.page4);
		}		
		//set the page property	
       SystemProperties.set("tv.launcher_page", "" + index);       
		mCurSel = index;
	}

	@Override
	public void OnViewChange(int viewIndex, boolean hasFocus) {
		Log.d(TAG, "====screen index===" + viewIndex + "====hasFocus====" + hasFocus);
		if (!hasFocus && viewIndex == mTvPriviewIndex) {
			UpdateTvPerviewHandler.removeCallbacks(UpdateTvPerviewRunnable);
			mTvPreview.DisablePerview();			
		} else if (hasFocus && viewIndex == mTvPriviewIndex) {
			if((mTvPreview.tv.QueryResourceState("wallpaper").owner_name).equals("atv")){
		  		//mTvPreview.ShowPerview();
		  	}else{
		  		//UpdateTvPerviewHandler.postDelayed(UpdateTvPerviewRunnable,2500);
		  		StartTvPreviewHandler.postDelayed( StartTvPreviewRunnable,200);
		  	}
			mTvPreview.ShowPerview();					
		}
		setCurPoint(viewIndex);
	}
	
	private Handler UpdateTvPerviewHandler = new Handler();
	private Runnable UpdateTvPerviewRunnable = new Runnable(){
		public void run(){
			mScrollLayout.invalidate();//refresh for display tvPreview
		}
	};
	
	private Handler StartTvPreviewHandler = new Handler();
	private Runnable StartTvPreviewRunnable = new Runnable(){
		public void run(){
			mTvPreview.startTvPreview(firstPageFirstLineIcon0,mCurSel * screenWidth);

		}
	};

	private void forceStartSinaService(){
		Intent intent = new Intent("com.lfzd.enews.thtfservice"); 
		this.startService(intent); 
	}

	private void sendBroadCastToRefreshNews(){
		Intent intent = new Intent("com.lfzd.enews.action.UPDATE_WIDGET");
		sendBroadcast(intent);
	}

	//set the source icon on tvpreview
	private synchronized void setSourceImage()throws IOException {
		boolean cn = getResources().getConfiguration().locale.getCountry().equals("CN");
		Log.d(TAG,"CN:" + getResources().getConfiguration().locale.getCountry());
		
		if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.TV.toInt()) {// tv
			lastSource = Tv.SrcInput.TV.toInt();
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/atv_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/atv_us.png");
			}
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.DTV.toInt()) {// dtv
			lastSource = Tv.SrcInput.DTV.toInt();
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/dtv_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/dtv_us.png");
			}			
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.AV1.toInt()) {//av1
			lastSource = Tv.SrcInput.AV1.toInt();
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/av1_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/av1_us.png");
			}			
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.AV2.toInt()) {//av2
			lastSource = Tv.SrcInput.AV2.toInt();
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/av2_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/av2_us.png");
			}			
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.YPBPR1.toInt()) {//YPBPR
			lastSource = Tv.SrcInput.YPBPR1.toInt();  
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/ypbpr_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/ypbpr_us.png");
			}
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.HDMI1.toInt()) {//HDMI1
			lastSource = Tv.SrcInput.HDMI1.toInt(); 
			SystemProperties.set("sys.show_pic", "/system/etc/hdmi1.png");			
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.HDMI2.toInt()) {//HDMI2
			lastSource = Tv.SrcInput.HDMI2.toInt(); 
			SystemProperties.set("sys.show_pic", "/system/etc/hdmi2.png");			
			Runtime.getRuntime().exec("/system/bin/showSource");
		}else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.VGA.toInt()) {//VGA0
			lastSource = Tv.SrcInput.VGA.toInt();
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/vga_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/vga_us.png");
			}			
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.MPEG.toInt()){//MPEG
			setSourceImage(lastSource);     
		} else {//default
			lastSource = Tv.SrcInput.TV.toInt();
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/atv_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/atv_us.png");
			}
			Runtime.getRuntime().exec("/system/bin/showSource");        
		}
	}

	private synchronized void setSourceImage(int source)throws IOException {

		boolean cn = getResources().getConfiguration().locale.getCountry().equals("CN");
		Log.d(TAG,"CN:" + getResources().getConfiguration().locale.getCountry());
		
		if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.TV.toInt()) {// tv
			lastSource = Tv.SrcInput.TV.toInt();
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/atv_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/atv_us.png");
			}			
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (lastSource == Tv.SrcInput.DTV.toInt()) {// dtv
			lastSource = Tv.SrcInput.DTV.toInt();
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/dtv_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/dtv_us.png");
			}			
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (lastSource == Tv.SrcInput.AV1.toInt()) {//av1
			lastSource = Tv.SrcInput.AV1.toInt();
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/av1_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/av1_us.png");
			}			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (lastSource == Tv.SrcInput.AV2.toInt()) {//av2
			lastSource = Tv.SrcInput.AV2.toInt();
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/av2_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/av2_us.png");
			}			
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (lastSource == Tv.SrcInput.YPBPR1.toInt()) {//YPBPR
			lastSource = Tv.SrcInput.YPBPR1.toInt();        
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/ypbpr_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/ypbpr_us.png");
			}			
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (lastSource == Tv.SrcInput.HDMI1.toInt()) {//HDMI1
			lastSource = Tv.SrcInput.HDMI1.toInt();    
			SystemProperties.set("sys.show_pic", "/system/etc/hdmi1.png");						
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (lastSource == Tv.SrcInput.HDMI2.toInt()) {//HDMI2
			lastSource = Tv.SrcInput.HDMI2.toInt();    
			SystemProperties.set("sys.show_pic", "/system/etc/hdmi2.png");									
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else if (lastSource == Tv.SrcInput.VGA.toInt()) {//VGA0
			lastSource = Tv.SrcInput.VGA.toInt();
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/vga_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/vga_us.png");
			}				
			Runtime.getRuntime().exec("/system/bin/showSource");
		} else {//default 
			lastSource = Tv.SrcInput.TV.toInt();
			if(cn){
				SystemProperties.set("sys.show_pic", "/system/etc/atv_cn.png");
			}else{
				SystemProperties.set("sys.show_pic", "/system/etc/atv_us.png");
			}			
			Runtime.getRuntime().exec("/system/bin/showSource");       
		}
	}	


	private void setSourceIconAfterResume(int delay){
		new Timer().schedule(new TimerTask(){
			@Override
			public void run() {
				if(mCurSel == mTvPriviewIndex && SystemProperties.get("tv.in_launcher").equals("true")){
		       		SystemProperties.set("tv.launcher_page", "0");//set the page property                										
					try {//set the source icon on tvpreview
						setSourceImage();
					} catch (IOException e) {
						e.printStackTrace();
					}	
				}
			}
			
		}, delay);	
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "====onResume=====");
		Log.d(TAG,"_____RESUME COUNT____"+resumeCount);
		//show only resume from atvscreen
		if(mScrollLayout.resumeFromAtvScreen){
			//set the page property 
	       SystemProperties.set("tv.launcher_page", "0");
			//don't disable remote control just boot completed	
			if( resumeCount > 1){
				//disable remote control
				mTvPreview.SetRegBit(REMOTE_DISABLE);
			}
			appearConceptScreen();
		}else{
			//delay 1 second to wait resume completed		
			setSourceIconAfterResume(1000);
		}
		//don't kill background process  first time for record basic needed process
		if(resumeCount >1){
			killExtraProcess();
		}
		updateStatus();
		//the newsServie receive this broadCast to refresh news
		sendBroadCastToRefreshNews();
		
		//run 2 times to ensure tvpreview be refreshed 
       if (mCurSel == mTvPriviewIndex) {
         	UpdateTvPerviewHandler.postDelayed(UpdateTvPerviewRunnable,2500);
         	UpdateTvPerviewHandler.postDelayed(UpdateTvPerviewRunnable,5000);			
       }else{
       	mTvPreview.DisablePerview();
       }
			 
		Log.d(TAG,"Resume count:" + resumeCount);
		
		resumeCount ++;
		mScrollLayout.setColorKeyFlag("off");
		SystemProperties.set("tv.in_launcher", "true");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG,"resumeCount=========="+resumeCount);
		
		Log.d(TAG, "====onPause=====");
		SystemProperties.set("tv.in_launcher", "false");
		//set the page property	if (launcher not in first page){hide the tvprevie source icon}
     	SystemProperties.set("tv.launcher_page", "8888"); 	
		UpdateTvPerviewHandler.removeCallbacks(UpdateTvPerviewRunnable);
		StartTvPreviewHandler.removeCallbacks(StartTvPreviewRunnable);
		//mTvPreview.SetRegBit(REMOTE_ENABLE);
		mTvPreview.StopTvPreview();
		if(mystartPlayerHandler != null){
			mystartPlayerHandler.removeMessages(1);
		}			   
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		UpdateTvPerviewHandler.removeCallbacks(UpdateTvPerviewRunnable);
		mTvPreview.StopTvPreview();
		mTvPreview.SetVideoSize(0 , 0 , 1920 , 1080);

		unregisterGlobalReceiver();
	}

	private void unregisterGlobalReceiver(){
	    if(gloableReceiver != null){
            this.unregisterReceiver(gloableReceiver);
            gloableReceiver = null;
        }
		if(usbReceiver != null){
            this.unregisterReceiver(usbReceiver);
            usbReceiver = null;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return detector.onTouchEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// prevent back into tvscreen
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		
		/*slow down the focus move
		if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
			if(event.getRepeatCount() % maxSpeedOfFocusMove == 0){
				mScrollLayout.onKeyDown(keyCode,event);
			}else{
				return true;
			}
		}else{
			mScrollLayout.onKeyDown(keyCode, event);
		}*/
		mScrollLayout.onKeyDown(keyCode, event);
		return super.onKeyDown(keyCode, event);
	}

	public void killBrowser() {
		ActivityManager am = (ActivityManager) this.getSystemService(this.ACTIVITY_SERVICE);
		am.restartPackage("com.android.browser");
	}

	public void getBasicNeededProcess() {
		ActivityManager activityManger = (ActivityManager) this.getSystemService(this.ACTIVITY_SERVICE);
		basicNeededProcess = activityManger.getRunningAppProcesses();
		if (basicNeededProcess != null){
			Log.d(TAG,"__basicNeededProcess.size() = " + basicNeededProcess.size());
			for (int i = 0; i < basicNeededProcess.size(); i++) {
				ActivityManager.RunningAppProcessInfo apinfo = basicNeededProcess.get(i);
				Log.d(TAG,"basicAll_pid_"+apinfo.pid+"_processName_|"+apinfo.processName+"|importance_"+apinfo.importance);
			}
		}
	}
	
	//kill process by given name
	public void killProcess(String processName){
		ActivityManager activityManger = (ActivityManager) this.getSystemService(this.ACTIVITY_SERVICE);
		activityManger.forceStopPackage(processName);		
	}
	
	public void killExtraProcess() {
		ActivityManager activityManger = (ActivityManager) this.getSystemService(this.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> runningAppProcesslist = activityManger.getRunningAppProcesses();
		List<ResolveInfo> wallpaperServiceList = getPackageManager().queryIntentServices(new Intent(WallpaperService.SERVICE_INTERFACE),PackageManager.GET_META_DATA);//getWallpaperServices 
		boolean added,wallpaperProcess;
		if (runningAppProcesslist != null){
			Log.d(TAG,"__runningAppProcesslist.size() = " + runningAppProcesslist.size() );			
			for (int i = 0; i < runningAppProcesslist.size(); i++) {
				added = true;
				ActivityManager.RunningAppProcessInfo apinfo = runningAppProcesslist.get(i);
				Log.d(TAG,"__All_pid_"+apinfo.pid+"_processName_||"+apinfo.processName+"||_importance_"+apinfo.importance);
				for( int j = 0 ; j < basicNeededProcess.size() ; j ++){
					if( runningAppProcesslist.get(i).processName.equals( basicNeededProcess.get(j).processName ) ){
						added = false;
					}
				}
				if( added ){
					wallpaperProcess = false;
					for(int k = 0 ; k < wallpaperServiceList.size() ; k ++){
						Log.d(TAG,"_wallpaperServiceList.get("+  k + ").packageName = ||" + wallpaperServiceList.get(k).serviceInfo.packageName + "||");
						if( apinfo.processName.equals("com.lfzd.enews") 
								|| apinfo.processName.equals(wallpaperServiceList.get(k).serviceInfo.packageName) 
								|| apinfo.processName.equals("com.amlogic.tvscreen")
								|| apinfo.processName.equals("com.amlogic.AtvScreen") 
								|| apinfo.processName.equals("com.amlogic.tvservice")
								|| apinfo.processName.equals("com.reconova.tongfang")
								|| apinfo.processName.equals("com.rockitv.android")
                    			|| apinfo.processName.equals("com.rockitv.ai")
                    			|| apinfo.processName.equals("com.awindinc.mirroropservice")){
							wallpaperProcess = true;
						}
					}
					//kill the not wallpaper added service
					if(!wallpaperProcess){
						Log.d(TAG,"__Been KILLED__pid_"+apinfo.pid+"_processName_||||"+apinfo.processName+"||||_importance_"+apinfo.importance);
						activityManger.forceStopPackage(apinfo.processName);
					}
				}
			}
		}
	}



	



	private class StartPlayerHandler extends Handler{
		@Override
		public void handleMessage(Message msg){
		    Intent intent = null;
		    switch (msg.what){
		        /*case TIME_OVER:
		            Log.d(TAG, ".......StartPlayerHandler......");
		                Intent intent = new Intent(StartPlayer);
		                sendBroadcast(intent);
		          
		            break;*/
		        case 1:
		            Log.d(TAG, ".......Start ATV PlayerHandler......");
	                intent = new Intent(StartPlayer);
	                sendBroadcast(intent);
		            break;
		        case 2:
		            Log.d(TAG, ".......Start DTV PlayerHandler......");
	                intent = new Intent(StartPlayDTV);
	                sendBroadcast(intent);
		            break;            
		    }
		}
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if ( ( e1.getX() - e2.getX() > FLIP_DISTANCE ) ){
			if( mCurSel != 3 ){
				mScrollLayout.snapNext();
			}
			return true;
		}
		else if ( ( e2.getX() - e1.getX() > FLIP_DISTANCE ) ){
			if( mCurSel != 0){
				mScrollLayout.snapPrevious();
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {		
	}
	
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
	
	OnTouchListener mOnTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return detector.onTouchEvent(event);
		}
	};		
}
