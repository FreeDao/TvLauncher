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




public class SwitchViewDemoActivity extends Activity implements Callback,OnViewChangeListener,OnGestureListener{

	private int mViewCount;
	private static int mCurSel;
	private static int mTvPriviewIndex = 0;
	private static int screenWidth = 1920;
	private MyScrollLayout mScrollLayout;
	private final static String TAG = "SwitchViewDemoActivity";

	private final static float TARGET_HEAP_UTILIZATION = 0.75f;
	private final static int HEAP_SIZE = 600 * 1024 * 1024;
	
	//tvPreview
	public static TvPreview mTvPreview;
	
	//dock
	private ImageView dockImageView1;
	private ImageView dockImageView2;
	private ImageView dockImageView3;
	private ImageView dockImageView4;
		
	//first page
	private VideoView firstPageFirstLineIcon1;
	private ImageView firstPageFirstLineIcon2;
	private ImageView firstPageFirstLineIcon3;	
	private ImageView firstPageSecondLineIcon1;
	private ImageView firstPageSecondLineIcon2;
	private ImageView firstPageSecondLineIcon3;
	private ImageView firstPageSecondLineIcon4;	
	
	//second page
	private ImageView secondPageFirstLineIcon1;
	private ImageView secondPageFirstLineIcon2;
	private ImageView secondPageFirstLineIcon3;
	private ImageView secondPageSecondLineIcon1;
	private ImageView secondPageSecondLineIcon2;
	private ImageView secondPageSecondLineIcon3;

	//third page
	private ImageView thirdPageFirstLineIcon1;
	private ImageView thirdPageFirstLineIcon2;
	private ImageView thirdPageFirstLineIcon3;
	private ImageView thirdPageFirstLineIcon4;
	private ImageView thirdPageSecondLineIcon1;
	private ImageView thirdPageSecondLineIcon2;
	private ImageView thirdPageSecondLineIcon3;
	private ImageView thirdPageSecondLineIcon4;

	//fourth page
	private ImageView fourthPageFirstLineIcon1;
	private ImageView fourthPageFirstLineIcon2;
	private ImageView fourthPageFirstLineIcon3;
	private ImageView fourthPageFirstLineIcon4;
	private ImageView fourthPageSecondLineIcon1;
	private ImageView fourthPageSecondLineIcon2;
	private ImageView fourthPageSecondLineIcon3;
	private ImageView fourthPageSecondLineIcon4;
	private ImageView fourthPageSecondLineIcon5;
	private ImageView fourthPageSecondLineIcon6;
	private TextView fourthPageFirstLineName3;
	private TextView fourthPageFirstLineName4;
	private TextView fourthPageSecondLineName1;
	private TextView fourthPageSecondLineName2;
	private TextView fourthPageSecondLineName3;
	private TextView fourthPageSecondLineName4;
	private TextView fourthPageSecondLineName5;
	private TextView fourthPageSecondLineName6;
	private LinearLayout fourthPageFirstLineApp3Parent;
	private LinearLayout fourthPageFirstLineApp4Parent;
	private LinearLayout fourthPageSecondLineApp1Parent;
	private LinearLayout fourthPageSecondLineApp2Parent;
	private LinearLayout fourthPageSecondLineApp3Parent;
	private LinearLayout fourthPageSecondLineApp4Parent;
	private LinearLayout fourthPageSecondLineApp5Parent;
	private LinearLayout fourthPageSecondLineApp6Parent;
	private LinearLayout fourthPageFirstLineIcon3Layout;
	private LinearLayout fourthPageFirstLineIcon4Layout;
	private LinearLayout fourthPageSecondLineIcon1Layout;
	private LinearLayout fourthPageSecondLineIcon2Layout;
	private LinearLayout fourthPageSecondLineIcon3Layout;
	private LinearLayout fourthPageSecondLineIcon4Layout;
	private LinearLayout fourthPageSecondLineIcon5Layout;
	private LinearLayout fourthPageSecondLineIcon6Layout;
	
	private OnFocusChangeListener fourthPageFirstLineApp3FocusChangeListener;
	private OnFocusChangeListener fourthPageFirstLineApp4FocusChangeListener;
	private OnFocusChangeListener fourthPageSecondLineApp1FocusChangeListener;
	private OnFocusChangeListener fourthPageSecondLineApp2FocusChangeListener;
	private OnFocusChangeListener fourthPageSecondLineApp3FocusChangeListener;
	private OnFocusChangeListener fourthPageSecondLineApp4FocusChangeListener;
	private OnFocusChangeListener fourthPageSecondLineApp5FocusChangeListener;
	private OnFocusChangeListener fourthPageSecondLineApp6FocusChangeListener;

	private OnClickListener fourthPageFirstLineApp3ClickListener;
	private OnClickListener fourthPageFirstLineApp4ClickListener;
	private OnClickListener fourthPageSecondLineApp1ClickListener;
	private OnClickListener fourthPageSecondLineApp2ClickListener;
	private OnClickListener fourthPageSecondLineApp3ClickListener;
	private OnClickListener fourthPageSecondLineApp4ClickListener;
	private OnClickListener fourthPageSecondLineApp5ClickListener;
	private OnClickListener fourthPageSecondLineApp6ClickListener;
	
	//statusbarStatus
	private ImageView muteImageView;
	private ImageView ethernetImageView;
	private ImageView usbImageView;
	private ImageView wifiImageView;

	private final int ethernetStatusMsg = 0x3000;
	private final int conceptScreenAppearMsg = 0x3001;
	private final int conceptScreenDisappearMsg = 0x3002;
	private final int greennetAppearMsg = 0x3003;
	private final int greennetDisappearMsg = 0x3004;	
	private final int showTvpreviewDelayTime = 1000;
	
	//statusbarWeather
	private TextView cityTextView;
	private TextView weatherTextView;
	private TextView tempTextView;
	//statusbarTime
	private TextView weekTextView;
	private TextView timeTextView;

	//flip condition
	private final int FLIP_DISTANCE = 100;

	//mouse support
	private GestureDetector detector;

	//record the basic needed process as reference
	private static List<ActivityManager.RunningAppProcessInfo> basicNeededProcess;

	private static int resumeCount =1;
	
	private LinearLayout conceptScreen;
	
	private ImageView tvPreView;
			
	private String StartPlayer = "com.amlogic.tvservice.startplayer";
	public static final String StartPlayDTV = "com.launcher.play.dtv";
	
	private StartPlayerHandler  mystartPlayerHandler = null;
	
	private final int TIME_OVER = 1;
	private final int maxSpeedOfFocusMove = 3;
	
	private static boolean   first_preview_start_atv = true;

	//user App
	private List<ResolveInfo> userResolveInfo; 
	private ArrayList<ApplicationInfo> userApplications;
	private int userAppSize;
	private UserAppProcess userAppProcess;

	private int lastSource;
	
	//the source menu state , hide or show	
	private String menuState;
	private String lastMenuState = "false";
	private boolean menuStateChanged = false;
	
	//disable remote control
	private static final String REMOTE_DISABLE = "wab 0x14 0 0";
	//enable remote control
	private static final String REMOTE_ENABLE = "wab 0x14 0 1";
	
	//all kinds of receiver
	private StartTvReceiver startTvReceiver = new StartTvReceiver();	
	private StatusbarWifiReceiver wifiReceiver = new StatusbarWifiReceiver();
	private StatusbarUsbReceiver usbReceiver = new StatusbarUsbReceiver();
	private StatusbarTimeReceiver timeReceiver = new StatusbarTimeReceiver();
	private StatusbarWeatherReceiver weatherReceiver = new StatusbarWeatherReceiver();
	private StatusbarEthernetReceiver ethernetReceiver = new StatusbarEthernetReceiver();	
	private VoiceCommandReceiver voiceCommandReceiver = new VoiceCommandReceiver();
	private UserAppReceiver userAppReceiver = new UserAppReceiver();
	private UserAppReceiver2 userAppReceiver2 = new UserAppReceiver2();

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
	
	private void init() {
		initIcons();
		initUserApp();
		registerReceiver();		
		initDock();		
		initGestureDetector();
		getBasicNeededProcess();
		forceStartSinaService();
		setSourceIcon();
	}

	private void registerReceiver(){
		registerStatusbarEthernetReceiver();
		registerStatusbarWifiReceiver();
		registerStatusbarUsbReceiver();
		registerStatusbarTimeReceiver();
		registerWeatherReceiver();
		registerStartTvReceiver();
		registerVoiceCommandReceiver();
		registerUserAppReceiver();
		registerUserAppReceiver2();
	}
	
	private void initIcons(){
       usbImageView =(ImageView)findViewById(R.id.statusbar_usb);		
		conceptScreen = (LinearLayout) findViewById(R.id.concept_pic);
		wifiImageView = (ImageView)findViewById(R.id.statusbar_wifi);
		timeTextView = (TextView)findViewById(R.id.statusbar_time_time);
		weekTextView = (TextView)findViewById(R.id.statusbar_time_week);		
		ethernetImageView=(ImageView)findViewById(R.id.statusbar_ethernet); 
		
		cityTextView = (TextView) findViewById(R.id.statusbar_weather_city);	
		tempTextView = (TextView) findViewById(R.id.statusbar_weather_temp);					
		weatherTextView = (TextView) findViewById(R.id.statusbar_weather_weather);

		dockImageView1=(ImageView) findViewById(R.id.dockImageView1);
		dockImageView2=(ImageView) findViewById(R.id.dockImageView2);
		dockImageView3=(ImageView) findViewById(R.id.dockImageView3);
		dockImageView4=(ImageView) findViewById(R.id.dockImageView4);

		//firstPageFirstLineIcon1 = (VideoView) findViewById(R.id.first_page101);
		inintVideoView(R.id.first_page101);
		firstPageFirstLineIcon2 = (ImageView) findViewById(R.id.first_page102);
		firstPageFirstLineIcon3 = (ImageView) findViewById(R.id.first_page103);
		firstPageSecondLineIcon1 = (ImageView) findViewById(R.id.first_page201);
		firstPageSecondLineIcon2 = (ImageView) findViewById(R.id.first_page202);
		firstPageSecondLineIcon3 = (ImageView) findViewById(R.id.first_page203);		
		firstPageSecondLineIcon4 = (ImageView) findViewById(R.id.first_page204);

		//special process for display GreenNet
		firstPageSecondLineIcon3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startGreenNet();
			}
		});
		
		secondPageFirstLineIcon1 = (ImageView) findViewById(R.id.second_page101);
		secondPageFirstLineIcon2 = (ImageView) findViewById(R.id.second_page102);
		secondPageFirstLineIcon3 = (ImageView) findViewById(R.id.second_page103);
		secondPageSecondLineIcon1 = (ImageView) findViewById(R.id.second_page201);
		secondPageSecondLineIcon2 = (ImageView) findViewById(R.id.second_page202);
		secondPageSecondLineIcon3 = (ImageView) findViewById(R.id.second_page203);	

		thirdPageFirstLineIcon1 = (ImageView) findViewById(R.id.third_page101);
		thirdPageFirstLineIcon2 = (ImageView) findViewById(R.id.third_page102);
		thirdPageFirstLineIcon3 = (ImageView) findViewById(R.id.third_page103);
		thirdPageFirstLineIcon4 = (ImageView) findViewById(R.id.third_page104);
		thirdPageSecondLineIcon1 = (ImageView) findViewById(R.id.third_page201);
		thirdPageSecondLineIcon2 = (ImageView) findViewById(R.id.third_page202);
		thirdPageSecondLineIcon3 = (ImageView) findViewById(R.id.third_page203);
		thirdPageSecondLineIcon4 = (ImageView) findViewById(R.id.third_page204);

		fourthPageFirstLineIcon1 = (ImageView) findViewById(R.id.fourth_page101);
		fourthPageFirstLineIcon2 = (ImageView) findViewById(R.id.fourth_page102);
		fourthPageFirstLineIcon3 = (ImageView) findViewById(R.id.fourth_page103_icon);
		fourthPageFirstLineIcon4 = (ImageView) findViewById(R.id.fourth_page104_icon);
		fourthPageSecondLineIcon1 = (ImageView) findViewById(R.id.fourth_page201_icon);
		fourthPageSecondLineIcon2 = (ImageView) findViewById(R.id.fourth_page202_icon);
		fourthPageSecondLineIcon3 = (ImageView) findViewById(R.id.fourth_page203_icon);
		fourthPageSecondLineIcon4 = (ImageView) findViewById(R.id.fourth_page204_icon);
		fourthPageSecondLineIcon5 = (ImageView) findViewById(R.id.fourth_page205_icon);
		fourthPageSecondLineIcon6 = (ImageView) findViewById(R.id.fourth_page206_icon);	
		fourthPageFirstLineName3 = (TextView) findViewById(R.id.fourth_page103_name);
		fourthPageFirstLineName4 = (TextView) findViewById(R.id.fourth_page104_name);
		fourthPageSecondLineName1 = (TextView) findViewById(R.id.fourth_page201_name);
		fourthPageSecondLineName2 = (TextView) findViewById(R.id.fourth_page202_name);
		fourthPageSecondLineName3 = (TextView) findViewById(R.id.fourth_page203_name);
		fourthPageSecondLineName4 = (TextView) findViewById(R.id.fourth_page204_name);
		fourthPageSecondLineName5 = (TextView) findViewById(R.id.fourth_page205_name);
		fourthPageSecondLineName6 = (TextView) findViewById(R.id.fourth_page206_name);
		fourthPageFirstLineApp3Parent = (LinearLayout) findViewById(R.id.fourth_page103_parent);
		fourthPageFirstLineApp4Parent = (LinearLayout) findViewById(R.id.fourth_page104_parent);
		fourthPageSecondLineApp1Parent = (LinearLayout) findViewById(R.id.fourth_page201_parent);
		fourthPageSecondLineApp2Parent = (LinearLayout) findViewById(R.id.fourth_page202_parent);
		fourthPageSecondLineApp3Parent = (LinearLayout) findViewById(R.id.fourth_page203_parent);
		fourthPageSecondLineApp4Parent = (LinearLayout) findViewById(R.id.fourth_page204_parent);
		fourthPageSecondLineApp5Parent = (LinearLayout) findViewById(R.id.fourth_page205_parent);
		fourthPageSecondLineApp6Parent = (LinearLayout) findViewById(R.id.fourth_page206_parent);
		fourthPageFirstLineIcon3Layout =(LinearLayout) findViewById(R.id.fourth_page103_layout);
		fourthPageFirstLineIcon4Layout =(LinearLayout) findViewById(R.id.fourth_page104_layout);
		fourthPageSecondLineIcon1Layout =(LinearLayout) findViewById(R.id.fourth_page201_layout);
		fourthPageSecondLineIcon2Layout =(LinearLayout) findViewById(R.id.fourth_page202_layout);
		fourthPageSecondLineIcon3Layout =(LinearLayout) findViewById(R.id.fourth_page203_layout);
		fourthPageSecondLineIcon4Layout =(LinearLayout) findViewById(R.id.fourth_page204_layout);
		fourthPageSecondLineIcon5Layout =(LinearLayout) findViewById(R.id.fourth_page205_layout);
		fourthPageSecondLineIcon6Layout =(LinearLayout) findViewById(R.id.fourth_page206_layout);
	}
	
	public void inintVideoView(int resourceId){
		firstPageFirstLineIcon1 = (VideoView) findViewById(R.id.first_page101);
		firstPageFirstLineIcon1.getHolder().addCallback(this);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
		Log.d(TAG, "====surfaceChanged====");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		Log.d(TAG, "====surfaceCreated====");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "====surfaceDestroyed====");
	}

	private void initUserApp(){
		if(userAppProcess == null){
			userAppProcess = new UserAppProcess(this);
		}
		userAppProcess.readGroupDetail();
		loadAllAppInfo();
		setUserAppInfo();
	}

	private void initDock(){
		mTvPreview = new TvPreview(getApplicationContext());				
		mScrollLayout = (MyScrollLayout) findViewById(R.id.ScrollLayout);		
		mViewCount = mScrollLayout.getChildCount();
		mCurSel = 0;
		dockImageView1.setImageResource(R.drawable.dock1);
		dockImageView2.setImageResource(R.drawable.dock_image2_unselected);
		dockImageView3.setImageResource(R.drawable.dock_image3_unselected);
		dockImageView4.setImageResource(R.drawable.dock_image4_unselected);
		//set the default page property   
	    SystemProperties.set("tv.launcher_page", "0");             		
		mScrollLayout.SetOnViewChangeListener(this);
	}

	private void initGestureDetector(){
		detector = new GestureDetector(this);
		firstPageFirstLineIcon1.setOnTouchListener(mOnTouchListener);		
		firstPageFirstLineIcon2.setOnTouchListener(mOnTouchListener);
		firstPageFirstLineIcon3.setOnTouchListener(mOnTouchListener);
		firstPageSecondLineIcon1.setOnTouchListener(mOnTouchListener);
		firstPageSecondLineIcon2.setOnTouchListener(mOnTouchListener);
		firstPageSecondLineIcon3.setOnTouchListener(mOnTouchListener);
		firstPageSecondLineIcon4.setOnTouchListener(mOnTouchListener);

		secondPageFirstLineIcon1.setOnTouchListener(mOnTouchListener);
		secondPageFirstLineIcon2.setOnTouchListener(mOnTouchListener);
		secondPageFirstLineIcon3.setOnTouchListener(mOnTouchListener);
		secondPageSecondLineIcon1.setOnTouchListener(mOnTouchListener);
		secondPageSecondLineIcon2.setOnTouchListener(mOnTouchListener);
		secondPageSecondLineIcon3.setOnTouchListener(mOnTouchListener);

		thirdPageFirstLineIcon1.setOnTouchListener(mOnTouchListener);
		thirdPageFirstLineIcon2.setOnTouchListener(mOnTouchListener);
		thirdPageFirstLineIcon3.setOnTouchListener(mOnTouchListener);
		thirdPageFirstLineIcon4.setOnTouchListener(mOnTouchListener);
		thirdPageSecondLineIcon1.setOnTouchListener(mOnTouchListener);
		thirdPageSecondLineIcon2.setOnTouchListener(mOnTouchListener);
		thirdPageSecondLineIcon3.setOnTouchListener(mOnTouchListener);
		thirdPageSecondLineIcon4.setOnTouchListener(mOnTouchListener);

		fourthPageFirstLineIcon1.setOnTouchListener(mOnTouchListener);
		fourthPageFirstLineIcon2.setOnTouchListener(mOnTouchListener);
		fourthPageSecondLineIcon1.setOnTouchListener(mOnTouchListener);
		fourthPageSecondLineIcon2.setOnTouchListener(mOnTouchListener);
		fourthPageSecondLineIcon3.setOnTouchListener(mOnTouchListener);
		fourthPageSecondLineIcon4.setOnTouchListener(mOnTouchListener);
		fourthPageSecondLineIcon5.setOnTouchListener(mOnTouchListener);
		fourthPageSecondLineIcon6.setOnTouchListener(mOnTouchListener);
	}
	
	private void updateStatus() {
		updateWifiStatus();
		updateUsbStatus();
		updateTimeStatus();
		if (mCurSel == mTvPriviewIndex ) {
			mTvPreview.startTvPreview(firstPageFirstLineIcon1,mCurSel * screenWidth);
		}else if( mScrollLayout.resumeFromAtvScreen ){
			//delay for showing concept screen
		    //SetVideoSizeHandler.postDelayed( SetVideoSizeRunnable,2000);
		}  
	}

	private void startGreenNet(){
		//close the tvpreview window	
		//mTvPreview.SetWindowSize(0 , 0 , 0 , 0 , 0);		
		appearGreennet();
		//mScrollLayout.mImageView=mScrollLayout.multiScreenImageView;
		
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
					//enable remote control
					mTvPreview.SetRegBit(REMOTE_ENABLE);
					//delay for source change  completed
					setSourceIconAfterResume(1000);	
				}				
				Log.d(TAG,"Send concetpDisappear Command");
			}
			
		}, showTvpreviewDelayTime * 2);		
	}

	//display 1 second when start internetBrowser
	public void appearGreennet(){
	 	Message appearGreennetMsg = new Message();
		appearGreennetMsg.what = greennetAppearMsg;
		greenNetScreenHandler.sendMessage(appearGreennetMsg);
		Log.d(TAG,"Send greennetAppear Command");

		new Timer().schedule(new TimerTask(){
			@Override
			public void run() {
			 	Message greennetMsg = new Message();
				greennetMsg.what = greennetDisappearMsg;
				greenNetScreenHandler.sendMessage(greennetMsg);
				Log.d(TAG,"Send greennetDisappear Command");				
			}
			
		}, 2500);		
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
	
	Handler ethernetStatusHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	if(msg.what == ethernetStatusMsg){
			updateEthernetStatus();
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
		int [] aWeek ={R.string.sunday,R.string.monday,R.string.tuesday,R.string.wednesday,R.string.thursday,R.string.friday,R.string.saturday };
		ContentResolver cv = this.getContentResolver();
		String strTimeFormat = android.provider.Settings.System.getString(cv,android.provider.Settings.System.TIME_12_24);
	        	        	        
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
		String WeekOfYear = this.getString( aWeek[ca.get( Calendar.DAY_OF_WEEK ) - 1] ) ;
		weekTextView.setText(WeekOfYear);
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
	
	class StatusbarEthernetReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context arg0, Intent arg1) {		
			Log.d(TAG,"Ethernet process");
			updateEthernetStatus();							
		}		
	}

	class StatusbarWifiReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context arg0, Intent arg1) {		
			Log.d(TAG,"wifi process");
			updateWifiStatus();							
		}		
	}
	
	class StatusbarUsbReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context arg0, Intent arg1) {		
			Log.d(TAG,"usb process");
			updateUsbStatus();							
		}		
	}
	
	class StatusbarTimeReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context arg0, Intent arg1) {		
			Log.d(TAG,"Time process");
			updateTimeStatus();							
		}		
	}

	class StatusbarWeatherReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context arg0, Intent arg1) {		
			Log.d(TAG,"weather process");
			updateWeatherStatus(arg1);							
		}		
	}
	

	class StartTvReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			Log.d(TAG,"StartTv process");
			if( mTvPriviewIndex == 1 ){
				mScrollLayout.snapPrevious();
			}else if( mTvPriviewIndex == 2){
				mScrollLayout.atThirdPageSnapRight();
			}
		   	//mTvPreview.startTV();
		}		
	}

	class VoiceCommandReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context arg0, Intent arg1) {		
			processVoiceCommand(arg1);								
		}		
	}

	class UserAppReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			initUserApp();
		}
	}

	class UserAppReceiver2 extends BroadcastReceiver{
		@Override
		public void onReceive(Context arg0, Intent arg1) {		
			initUserApp();							
		}		
	}

	private void loadAllAppInfo() {
		PackageManager pm = getPackageManager(); 

		if( userApplications== null ){
			userApplications = new ArrayList<ApplicationInfo>();
		}
		userApplications.clear();
		
		for (int i = 0; i < UserAppProcess.allApksToDisplayInDesktop.size(); i++) {
			
			ApplicationInfo appInfo = new ApplicationInfo();
			String curApkPkgName=UserAppProcess.allApksToDisplayInDesktop.get(i);

			Log.d(TAG,"=======================get PKG name :" + curApkPkgName);
			
			ResolveInfo info2 = getResolveInfoByPackage( curApkPkgName );
			
			if(info2 != null){   
				appInfo.title = info2.loadLabel(pm);
				Log.d(TAG,"title :" + appInfo.title);
				appInfo.icon = info2.activityInfo.loadIcon(pm);
				Log.d(TAG,"icon :" + appInfo.icon);
				appInfo.intent = pm.getLaunchIntentForPackage( curApkPkgName );
				userApplications.add(appInfo);
			}
			
		}
			
	}	

	private ResolveInfo getResolveInfoByPackage(String packageName) {
		final List<ResolveInfo> matches = findActivitiesForPackage( packageName);
		int count = (matches != null) ? matches.size() : 0;
		
		for(int i=0; i<count; i++) {
			ResolveInfo info = matches.get(i);
			ActivityInfo ainfo = (info != null) ? info.activityInfo : null;
			String packName = (ainfo != null) ? ainfo.packageName : null;
			if(packageName.equals(packName))
				return info;
		}
		return null;
	}
	 
	private List<ResolveInfo> findActivitiesForPackage(String packageName) {
       final PackageManager packageManager = getPackageManager();

       final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
       mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
       mainIntent.setPackage(packageName);

       final List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
       return apps != null ? apps : new ArrayList<ResolveInfo>();
	}

	private void setUserAppInfo(){

		initUserAppInfo();
		userAppSize = userApplications.size();

		if(userAppSize == 0){
			firstPageFirstLineIcon1.setNextFocusLeftId(R.id.fourth_page102);
			firstPageSecondLineIcon1.setNextFocusLeftId(R.id.fourth_page102);
			thirdPageSecondLineIcon4.setNextFocusRightId(R.id.fourth_page101);			
			fourthPageFirstLineIcon2.setNextFocusRightId(R.id.first_page101);
			//process down key focus
			fourthPageFirstLineIcon1.setNextFocusDownId(R.id.fourth_page101);
			fourthPageFirstLineIcon2.setNextFocusDownId(R.id.fourth_page102);			
		}else if( userAppSize == 1){
			setThirdUserApp();
			
			firstPageFirstLineIcon1.setNextFocusLeftId(R.id.fourth_page103_icon);
			firstPageSecondLineIcon1.setNextFocusLeftId(R.id.fourth_page103_icon);
			thirdPageSecondLineIcon4.setNextFocusRightId(R.id.fourth_page101);						
			fourthPageFirstLineIcon2.setNextFocusRightId(R.id.fourth_page103_icon);			
			fourthPageFirstLineIcon3.setNextFocusRightId(R.id.first_page101);
			//process down key focus
			fourthPageFirstLineIcon1.setNextFocusDownId(R.id.fourth_page101);
			fourthPageFirstLineIcon2.setNextFocusDownId(R.id.fourth_page102);
			fourthPageFirstLineIcon3.setNextFocusDownId(R.id.fourth_page103_icon);			
		}else if( userAppSize == 2){
			setFourthUserApp();

			firstPageFirstLineIcon1.setNextFocusLeftId(R.id.fourth_page104_icon);
			firstPageSecondLineIcon1.setNextFocusLeftId(R.id.fourth_page104_icon);
			thirdPageSecondLineIcon4.setNextFocusRightId(R.id.fourth_page101);
			//process down key focus
			fourthPageFirstLineIcon1.setNextFocusDownId(R.id.fourth_page101);
			fourthPageFirstLineIcon2.setNextFocusDownId(R.id.fourth_page102);
			fourthPageFirstLineIcon3.setNextFocusDownId(R.id.fourth_page103_icon);
			fourthPageFirstLineIcon4.setNextFocusDownId(R.id.fourth_page104_icon);
		}else if( userAppSize == 3){
			setFifthUserApp();
			
			firstPageFirstLineIcon1.setNextFocusLeftId(R.id.fourth_page104_icon);
			firstPageSecondLineIcon1.setNextFocusLeftId(R.id.fourth_page201_icon);
			thirdPageSecondLineIcon4.setNextFocusRightId(R.id.fourth_page201_icon);		
			fourthPageSecondLineIcon1.setNextFocusRightId(R.id.first_page201);
			//process down key focus			
			fourthPageFirstLineIcon1.setNextFocusDownId(R.id.fourth_page201_icon);
			fourthPageFirstLineIcon2.setNextFocusDownId(R.id.fourth_page201_icon);
			fourthPageFirstLineIcon3.setNextFocusDownId(R.id.fourth_page201_icon);
			fourthPageFirstLineIcon4.setNextFocusDownId(R.id.fourth_page201_icon);			
		}else if( userAppSize == 4){
			setSixthUserApp();
			
			firstPageFirstLineIcon1.setNextFocusLeftId(R.id.fourth_page104_icon);
			firstPageSecondLineIcon1.setNextFocusLeftId(R.id.fourth_page202_icon);
			thirdPageSecondLineIcon4.setNextFocusRightId(R.id.fourth_page201_icon);
			fourthPageSecondLineIcon1.setNextFocusRightId(R.id.fourth_page202_icon);
			fourthPageSecondLineIcon2.setNextFocusRightId(R.id.first_page201);
			//process down key focus			
			fourthPageFirstLineIcon1.setNextFocusDownId(R.id.fourth_page202_icon);
			fourthPageFirstLineIcon2.setNextFocusDownId(R.id.fourth_page202_icon);
			fourthPageFirstLineIcon3.setNextFocusDownId(R.id.fourth_page202_icon);
			fourthPageFirstLineIcon4.setNextFocusDownId(R.id.fourth_page202_icon);			
		}else if( userAppSize == 5){
			setSeventhUserApp();

			firstPageFirstLineIcon1.setNextFocusLeftId(R.id.fourth_page104_icon);
			firstPageSecondLineIcon1.setNextFocusLeftId(R.id.fourth_page203_icon);
			thirdPageSecondLineIcon4.setNextFocusRightId(R.id.fourth_page201_icon);
			fourthPageSecondLineIcon1.setNextFocusRightId(R.id.fourth_page202_icon);
			fourthPageSecondLineIcon2.setNextFocusRightId(R.id.fourth_page203_icon);
			fourthPageSecondLineIcon3.setNextFocusRightId(R.id.first_page201);
			//process down key focus			
			fourthPageFirstLineIcon1.setNextFocusDownId(R.id.fourth_page202_icon);
			fourthPageFirstLineIcon2.setNextFocusDownId(R.id.fourth_page203_icon);
			fourthPageFirstLineIcon3.setNextFocusDownId(R.id.fourth_page203_icon);
			fourthPageFirstLineIcon4.setNextFocusDownId(R.id.fourth_page203_icon);				
		}else if( userAppSize == 6){
			setEighthUserApp();

			firstPageFirstLineIcon1.setNextFocusLeftId(R.id.fourth_page104_icon);
			firstPageSecondLineIcon1.setNextFocusLeftId(R.id.fourth_page204_icon);
			thirdPageSecondLineIcon4.setNextFocusRightId(R.id.fourth_page201_icon);	
			fourthPageSecondLineIcon1.setNextFocusRightId(R.id.fourth_page202_icon);
			fourthPageSecondLineIcon2.setNextFocusRightId(R.id.fourth_page203_icon);
			fourthPageSecondLineIcon3.setNextFocusRightId(R.id.fourth_page204_icon);
			fourthPageSecondLineIcon4.setNextFocusRightId(R.id.first_page201);
			//process down key focus			
			fourthPageFirstLineIcon1.setNextFocusDownId(R.id.fourth_page202_icon);
			fourthPageFirstLineIcon2.setNextFocusDownId(R.id.fourth_page204_icon);
			fourthPageFirstLineIcon3.setNextFocusDownId(R.id.fourth_page204_icon);
			fourthPageFirstLineIcon4.setNextFocusDownId(R.id.fourth_page204_icon);			
		}else if( userAppSize == 7){
			setNinthUserApp();

			firstPageFirstLineIcon1.setNextFocusLeftId(R.id.fourth_page104_icon);
			firstPageSecondLineIcon1.setNextFocusLeftId(R.id.fourth_page205_icon);
			thirdPageSecondLineIcon4.setNextFocusRightId(R.id.fourth_page201_icon);	
			fourthPageSecondLineIcon1.setNextFocusRightId(R.id.fourth_page202_icon);
			fourthPageSecondLineIcon2.setNextFocusRightId(R.id.fourth_page203_icon);
			fourthPageSecondLineIcon3.setNextFocusRightId(R.id.fourth_page204_icon);
			fourthPageSecondLineIcon4.setNextFocusRightId(R.id.fourth_page205_icon);
			fourthPageSecondLineIcon5.setNextFocusRightId(R.id.first_page201);
			//process down key focus			
			fourthPageFirstLineIcon1.setNextFocusDownId(R.id.fourth_page202_icon);
			fourthPageFirstLineIcon2.setNextFocusDownId(R.id.fourth_page204_icon);
			fourthPageFirstLineIcon3.setNextFocusDownId(R.id.fourth_page205_icon);
			fourthPageFirstLineIcon4.setNextFocusDownId(R.id.fourth_page205_icon);				
		}else if( userAppSize >= 8){
			setTenthUserApp();

			firstPageFirstLineIcon1.setNextFocusLeftId(R.id.fourth_page104_icon);
			firstPageSecondLineIcon1.setNextFocusLeftId(R.id.fourth_page206_icon);
			thirdPageSecondLineIcon4.setNextFocusRightId(R.id.fourth_page201_icon);	
			fourthPageSecondLineIcon1.setNextFocusRightId(R.id.fourth_page202_icon);
			fourthPageSecondLineIcon2.setNextFocusRightId(R.id.fourth_page203_icon);
			fourthPageSecondLineIcon3.setNextFocusRightId(R.id.fourth_page204_icon);
			fourthPageSecondLineIcon4.setNextFocusRightId(R.id.fourth_page205_icon);
			fourthPageSecondLineIcon5.setNextFocusRightId(R.id.fourth_page206_icon);
			fourthPageSecondLineIcon6.setNextFocusRightId(R.id.first_page201);
			//process down key focus			
			fourthPageFirstLineIcon1.setNextFocusDownId(R.id.fourth_page202_icon);
			fourthPageFirstLineIcon2.setNextFocusDownId(R.id.fourth_page204_icon);
			fourthPageFirstLineIcon3.setNextFocusDownId(R.id.fourth_page205_icon);
			fourthPageFirstLineIcon4.setNextFocusDownId(R.id.fourth_page206_icon);			
		}
	}

	private void setThirdUserApp(){
		fourthPageFirstLineIcon3.setBackgroundDrawable(userApplications.get(0).icon);
		fourthPageFirstLineName3.setText(userApplications.get(0).title);
		Log.d(TAG,"______________________icon 0 " + userApplications.get(0).icon);		
		Log.d(TAG,"______________________title 0 " + userApplications.get(0).title);
		fourthPageFirstLineIcon3.setVisibility(View.VISIBLE);
		fourthPageFirstLineName3.setVisibility(View.VISIBLE);
		fourthPageFirstLineApp3Parent.setBackgroundResource(R.drawable.user_app_background);		
		userAppFocuschange(fourthPageFirstLineIcon3,fourthPageFirstLineIcon3Layout,fourthPageFirstLineApp3FocusChangeListener);
		userAppClick(fourthPageFirstLineIcon3,fourthPageFirstLineApp3ClickListener,userApplications.get(0).intent);
	}

	private void setFourthUserApp(){
		setThirdUserApp();
		fourthPageFirstLineIcon4.setBackgroundDrawable(userApplications.get(1).icon);
		fourthPageFirstLineName4.setText(userApplications.get(1).title);
		Log.d(TAG,"______________________icon 1 " + userApplications.get(1).icon);		
		Log.d(TAG,"______________________title 1 " + userApplications.get(1).title);		
		fourthPageFirstLineIcon4.setVisibility(View.VISIBLE);
		fourthPageFirstLineName4.setVisibility(View.VISIBLE);
		fourthPageFirstLineApp4Parent.setBackgroundResource(R.drawable.user_app_background);		
		userAppFocuschange(fourthPageFirstLineIcon4,fourthPageFirstLineIcon4Layout,fourthPageFirstLineApp4FocusChangeListener);		
		userAppClick(fourthPageFirstLineIcon4,fourthPageFirstLineApp4ClickListener,userApplications.get(1).intent);
	}

	private void setFifthUserApp(){
		setFourthUserApp();
		fourthPageSecondLineIcon1.setBackgroundDrawable(userApplications.get(2).icon);
		fourthPageSecondLineName1.setText(userApplications.get(2).title);
		Log.d(TAG,"______________________icon 2 " + userApplications.get(2).icon);		
		Log.d(TAG,"______________________title 2 " + userApplications.get(2).title);		
		fourthPageSecondLineIcon1.setVisibility(View.VISIBLE);
		fourthPageSecondLineName1.setVisibility(View.VISIBLE);
		fourthPageSecondLineApp1Parent.setBackgroundResource(R.drawable.user_app_background);		
		userAppFocuschange(fourthPageSecondLineIcon1,fourthPageSecondLineIcon1Layout,fourthPageSecondLineApp1FocusChangeListener);		
		userAppClick(fourthPageSecondLineIcon1,fourthPageSecondLineApp1ClickListener,userApplications.get(2).intent);
	}
	
	private void setSixthUserApp(){
		setFifthUserApp();
		fourthPageSecondLineIcon2.setBackgroundDrawable(userApplications.get(3).icon);
		fourthPageSecondLineName2.setText(userApplications.get(3).title);
		Log.d(TAG,"______________________icon 3 " + userApplications.get(3).icon);		
		Log.d(TAG,"______________________title 3 " + userApplications.get(3).title);		
		fourthPageSecondLineIcon2.setVisibility(View.VISIBLE);
		fourthPageSecondLineName2.setVisibility(View.VISIBLE);
		fourthPageSecondLineApp2Parent.setBackgroundResource(R.drawable.user_app_background);		
		userAppFocuschange(fourthPageSecondLineIcon2,fourthPageSecondLineIcon2Layout,fourthPageSecondLineApp2FocusChangeListener);		
		userAppClick(fourthPageSecondLineIcon2,fourthPageSecondLineApp2ClickListener,userApplications.get(3).intent);
	}

	private void setSeventhUserApp(){
		setSixthUserApp();
		fourthPageSecondLineIcon3.setBackgroundDrawable(userApplications.get(4).icon);
		fourthPageSecondLineName3.setText(userApplications.get(4).title);
		Log.d(TAG,"______________________icon 4 " + userApplications.get(4).icon);		
		Log.d(TAG,"______________________title 4 " + userApplications.get(4).title);			
		fourthPageSecondLineIcon3.setVisibility(View.VISIBLE);
		fourthPageSecondLineName3.setVisibility(View.VISIBLE);
		fourthPageSecondLineApp3Parent.setBackgroundResource(R.drawable.user_app_background);		
		userAppFocuschange(fourthPageSecondLineIcon3,fourthPageSecondLineIcon3Layout,fourthPageSecondLineApp3FocusChangeListener);		
		userAppClick(fourthPageSecondLineIcon3,fourthPageSecondLineApp3ClickListener,userApplications.get(4).intent);
	}

	private void setEighthUserApp(){
		setSeventhUserApp();
		fourthPageSecondLineIcon4.setBackgroundDrawable(userApplications.get(5).icon);
		fourthPageSecondLineName4.setText(userApplications.get(5).title);
		Log.d(TAG,"______________________icon 5 " + userApplications.get(5).icon);		
		Log.d(TAG,"______________________title 5 " + userApplications.get(5).title);		
		fourthPageSecondLineIcon4.setVisibility(View.VISIBLE);
		fourthPageSecondLineName4.setVisibility(View.VISIBLE);
		fourthPageSecondLineApp4Parent.setBackgroundResource(R.drawable.user_app_background);		
		userAppFocuschange(fourthPageSecondLineIcon4,fourthPageSecondLineIcon4Layout,fourthPageSecondLineApp4FocusChangeListener);		
		userAppClick(fourthPageSecondLineIcon4,fourthPageSecondLineApp4ClickListener,userApplications.get(5).intent);
	}

	private void setNinthUserApp(){
		setEighthUserApp();
		fourthPageSecondLineIcon5.setBackgroundDrawable(userApplications.get(6).icon);
		fourthPageSecondLineName5.setText(userApplications.get(6).title);
		Log.d(TAG,"______________________icon 6 " + userApplications.get(6).icon);		
		Log.d(TAG,"______________________title 6 " + userApplications.get(6).title);		
		fourthPageSecondLineIcon5.setVisibility(View.VISIBLE);
		fourthPageSecondLineName5.setVisibility(View.VISIBLE);
		fourthPageSecondLineApp5Parent.setBackgroundResource(R.drawable.user_app_background);		
		userAppFocuschange(fourthPageSecondLineIcon5,fourthPageSecondLineIcon5Layout,fourthPageSecondLineApp5FocusChangeListener);		
		userAppClick(fourthPageSecondLineIcon5,fourthPageSecondLineApp5ClickListener,userApplications.get(6).intent);
	}

	private void setTenthUserApp(){
		setNinthUserApp();
		fourthPageSecondLineIcon6.setBackgroundDrawable(userApplications.get(7).icon);
		fourthPageSecondLineName6.setText(userApplications.get(7).title);
		Log.d(TAG,"______________________icon 7 " + userApplications.get(7).icon);		
		Log.d(TAG,"______________________title 7 " + userApplications.get(7).title);		
		fourthPageSecondLineIcon6.setVisibility(View.VISIBLE);
		fourthPageSecondLineName6.setVisibility(View.VISIBLE);
		fourthPageSecondLineApp6Parent.setBackgroundResource(R.drawable.user_app_background);		
		userAppFocuschange(fourthPageSecondLineIcon6,fourthPageSecondLineIcon6Layout,fourthPageSecondLineApp6FocusChangeListener);		
		userAppClick(fourthPageSecondLineIcon6,fourthPageSecondLineApp6ClickListener,userApplications.get(7).intent);
	}
	
	private void initUserAppInfo(){
		fourthPageFirstLineIcon2.setNextFocusRightId(R.id.fourth_page103_icon);			
		fourthPageFirstLineIcon3.setNextFocusRightId(R.id.fourth_page104_icon);			
		fourthPageFirstLineIcon4.setNextFocusRightId(R.id.first_page101);
			
		fourthPageFirstLineIcon3.setVisibility(View.GONE);
		fourthPageFirstLineIcon4.setVisibility(View.GONE);
		fourthPageSecondLineIcon1.setVisibility(View.GONE);
		fourthPageSecondLineIcon2.setVisibility(View.GONE);
		fourthPageSecondLineIcon3.setVisibility(View.GONE);
		fourthPageSecondLineIcon4.setVisibility(View.GONE);
		fourthPageSecondLineIcon5.setVisibility(View.GONE);
		fourthPageSecondLineIcon6.setVisibility(View.GONE);

		fourthPageFirstLineName3.setVisibility(View.GONE);		
		fourthPageFirstLineName4.setVisibility(View.GONE);		
		fourthPageSecondLineName1.setVisibility(View.GONE);		
		fourthPageSecondLineName2.setVisibility(View.GONE);		
		fourthPageSecondLineName3.setVisibility(View.GONE);		
		fourthPageSecondLineName4.setVisibility(View.GONE);		
		fourthPageSecondLineName5.setVisibility(View.GONE);		
		fourthPageSecondLineName6.setVisibility(View.GONE);		
		
		fourthPageFirstLineApp3Parent.setBackgroundResource(R.drawable.nothing);
		fourthPageFirstLineApp4Parent.setBackgroundResource(R.drawable.nothing);
		fourthPageSecondLineApp1Parent.setBackgroundResource(R.drawable.nothing);
		fourthPageSecondLineApp2Parent.setBackgroundResource(R.drawable.nothing);
		fourthPageSecondLineApp3Parent.setBackgroundResource(R.drawable.nothing);
		fourthPageSecondLineApp4Parent.setBackgroundResource(R.drawable.nothing);
		fourthPageSecondLineApp5Parent.setBackgroundResource(R.drawable.nothing);
		fourthPageSecondLineApp6Parent.setBackgroundResource(R.drawable.nothing);

		fourthPageFirstLineApp3FocusChangeListener = null;
		fourthPageFirstLineApp4FocusChangeListener = null;
		fourthPageSecondLineApp1FocusChangeListener = null;
		fourthPageSecondLineApp2FocusChangeListener = null;
		fourthPageSecondLineApp3FocusChangeListener = null;
		fourthPageSecondLineApp4FocusChangeListener = null;
		fourthPageSecondLineApp5FocusChangeListener = null;
		fourthPageSecondLineApp6FocusChangeListener = null;	

		fourthPageFirstLineApp3ClickListener = null;
		fourthPageFirstLineApp4ClickListener = null;
		fourthPageSecondLineApp1ClickListener = null;
		fourthPageSecondLineApp2ClickListener = null;
		fourthPageSecondLineApp3ClickListener = null;
		fourthPageSecondLineApp4ClickListener = null;
		fourthPageSecondLineApp5ClickListener = null;
		fourthPageSecondLineApp6ClickListener = null;			
	}

	private void userAppFocuschange(final ImageView imageButton,final LinearLayout linearLayout,OnFocusChangeListener onFocusChangeListener) {
		onFocusChangeListener = new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					linearLayout.setBackgroundResource(R.drawable.item_selected_little);					
				} else if (hasFocus == false) {
					linearLayout.setBackgroundResource(R.drawable.nothing);
			   }
			}
		};
		imageButton.setOnFocusChangeListener(onFocusChangeListener);
	}

	private void userAppClick(final ImageView imageButton,OnClickListener onClickListener,final Intent intent) {
		onClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick");
				mScrollLayout.releaseFirstThenStartApk(intent);
			}
		};
		imageButton.setOnClickListener(onClickListener);
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
		String receiveMusicKeyWord = startAppInformation.getString("musicKeyWord");
		String newMusicKeyword = "";
		String URL = startAppInformation.getString("URL");
		
		try {//encode the musicKeyWord
			if( receiveMusicKeyWord != null){
				newMusicKeyword = URLEncoder.encode(receiveMusicKeyWord,"gb2312");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if(action != null && action.equals("start.settings.activity") ){//means it is myapp
			Intent intent = new Intent();
			intent.setAction("start.settings.activity");
			mScrollLayout.releaseFirstThenStartApk(intent);
		}else if( URL != null ){//means it is kaikou shang wang
			Uri uri = Uri.parse(URL);
			Intent intent = new Intent(Intent.ACTION_VIEW,uri);
			mScrollLayout.releaseFirstThenStartApk(intent);			
		}else if( receiveMusicKeyWord != null ){//launch baidu to search the keyword of music
			Uri uri = Uri.parse( "http://mp3.baidu.com/m?word=" + newMusicKeyword);
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
		
	private void setCurPoint(int index) {
		if (index < 0 || index > mViewCount - 1 || mCurSel == index) {
			return;
		}
		
		dockImageView1.setImageResource(R.drawable.dock_image1_unselected);
		dockImageView2.setImageResource(R.drawable.dock_image2_unselected);
		dockImageView3.setImageResource(R.drawable.dock_image3_unselected);
		dockImageView4.setImageResource(R.drawable.dock_image4_unselected);

		if(index == 0 ){
			dockImageView1.setImageResource(R.drawable.dock1);
			//set the source icon on tvpreview
			try {
				setSourceImage();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (index == 1){
			dockImageView2.setImageResource(R.drawable.dock2);
		}	
		if(index == 2){
			dockImageView3.setImageResource(R.drawable.dock3);
		}
		if(index == 3){
			dockImageView4.setImageResource(R.drawable.dock4);
		}		
		//set the page property	
       SystemProperties.set("tv.launcher_page", "" + index);       
		mCurSel = index;
	}

	@Override
	public void OnViewChange(int view, boolean hasFocus) {
		Log.d(TAG, "====screen index===" + view + "====hasFocus====" + hasFocus);
		if (!hasFocus && view == mTvPriviewIndex) {
			UpdateTvPerviewHandler.removeCallbacks(UpdateTvPerviewRunnable);
			mTvPreview.DisablePerview();			
		} else if (hasFocus && view == mTvPriviewIndex) {
			if((mTvPreview.tv.QueryResourceState("wallpaper").owner_name).equals("atv")){
		  		//mTvPreview.ShowPerview();
		  	}else{
		  		//UpdateTvPerviewHandler.postDelayed(UpdateTvPerviewRunnable,2500);
		  		mTvPreview.startTvPreview(firstPageFirstLineIcon1,mCurSel * screenWidth);
		  	}
			mTvPreview.ShowPerview();					
		}
		setCurPoint(view);
	}
	
	private Handler UpdateTvPerviewHandler = new Handler();
	private Runnable UpdateTvPerviewRunnable = new Runnable(){
		public void run(){
			mScrollLayout.invalidate();//refresh for display tvPreview
		}
	};
	
	private Handler SetVideoSizeHandler = new Handler();
	private Runnable SetVideoSizeRunnable = new Runnable(){
		public void run(){
			mTvPreview.startTvPreview(firstPageFirstLineIcon1,mCurSel * screenWidth);

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
		if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.TV.toInt()) {// tv
			lastSource = Tv.SrcInput.TV.toInt();
			SystemProperties.set("sys.show_pic", "/system/etc/tv.png");        
			Runtime.getRuntime().exec("/system/bin/showPic");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.AV1.toInt()) {//av1
			lastSource = Tv.SrcInput.AV1.toInt();
			SystemProperties.set("sys.show_pic", "/system/etc/av1.png");                
			Runtime.getRuntime().exec("/system/bin/showPic");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.AV2.toInt()) {//av2
			lastSource = Tv.SrcInput.AV2.toInt();
			SystemProperties.set("sys.show_pic", "/system/etc/av2.png");
			Runtime.getRuntime().exec("/system/bin/showPic");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.YPBPR1.toInt()) {//YPBPR
			lastSource = Tv.SrcInput.YPBPR1.toInt();        
			SystemProperties.set("sys.show_pic", "/system/etc/ypbpr.png");
			Runtime.getRuntime().exec("/system/bin/showPic");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.HDMI1.toInt()) {//HDMI1
			lastSource = Tv.SrcInput.HDMI1.toInt();    
			SystemProperties.set("sys.show_pic", "/system/etc/hdmi1.png");
			Runtime.getRuntime().exec("/system/bin/showPic");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.HDMI2.toInt()) {//HDMI2
			lastSource = Tv.SrcInput.HDMI2.toInt();    
			SystemProperties.set("sys.show_pic", "/system/etc/hdmi2.png");
			Runtime.getRuntime().exec("/system/bin/showPic");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.HDMI3.toInt()) {//HDMI3
			lastSource = Tv.SrcInput.HDMI3.toInt(); 
			SystemProperties.set("sys.show_pic", "/system/etc/hdmi3.png");
			Runtime.getRuntime().exec("/system/bin/showPic");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.VGA.toInt()) {//VGA0
			lastSource = Tv.SrcInput.VGA.toInt();
			SystemProperties.set("sys.show_pic", "/system/etc/vga.png");
			Runtime.getRuntime().exec("/system/bin/showPic");
		} else if (mTvPreview.tv.GetCurrentSourceInput() == Tv.SrcInput.MPEG.toInt()){//MPEG
		   setSourceImage(lastSource);     
		} else {//default
			lastSource = Tv.SrcInput.TV.toInt();
			SystemProperties.set("sys.show_pic", "/system/etc/tv.png");
			Runtime.getRuntime().exec("/system/bin/showPic");        
		}
	}

	//overload to set the source icon when in MPEG source
	private synchronized void setSourceImage(int source)throws IOException {
		if (lastSource == Tv.SrcInput.TV.toInt()) {// tv
			lastSource = Tv.SrcInput.TV.toInt();
			SystemProperties.set("sys.show_pic", "/system/etc/tv.png");        
		} else if (lastSource == Tv.SrcInput.AV1.toInt()) {//av1
			lastSource = Tv.SrcInput.AV1.toInt();
			SystemProperties.set("sys.show_pic", "/system/etc/av1.png");                
		} else if (lastSource == Tv.SrcInput.AV2.toInt()) {//av2
			lastSource = Tv.SrcInput.AV2.toInt();
			SystemProperties.set("sys.show_pic", "/system/etc/av2.png");
		} else if (lastSource == Tv.SrcInput.YPBPR1.toInt()) {//YPBPR
			lastSource = Tv.SrcInput.YPBPR1.toInt();        
			SystemProperties.set("sys.show_pic", "/system/etc/ypbpr.png");
		} else if (lastSource == Tv.SrcInput.HDMI1.toInt()) {//HDMI1
			lastSource = Tv.SrcInput.HDMI1.toInt();    
			SystemProperties.set("sys.show_pic", "/system/etc/hdmi1.png");
		} else if (lastSource == Tv.SrcInput.HDMI2.toInt()) {//HDMI2
			lastSource = Tv.SrcInput.HDMI2.toInt();    
			SystemProperties.set("sys.show_pic", "/system/etc/hdmi2.png");
		} else if (lastSource == Tv.SrcInput.HDMI3.toInt()) {//HDMI3
			lastSource = Tv.SrcInput.HDMI3.toInt(); 
			SystemProperties.set("sys.show_pic", "/system/etc/hdmi3.png");
		} else if (lastSource == Tv.SrcInput.VGA.toInt()) {//VGA0
			lastSource = Tv.SrcInput.VGA.toInt();
			SystemProperties.set("sys.show_pic", "/system/etc/vga.png");
		} else {//default 
			lastSource = Tv.SrcInput.TV.toInt();
			SystemProperties.set("sys.show_pic", "/system/etc/tv.png");
		}
		Runtime.getRuntime().exec("/system/bin/showPic");		
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
		resumeCount ++;
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
		SetVideoSizeHandler.removeCallbacks(SetVideoSizeRunnable);
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
		unregisterReceiver(ethernetReceiver);
		unregisterReceiver(wifiReceiver);
		unregisterReceiver(usbReceiver);		
		unregisterReceiver(timeReceiver);
		unregisterReceiver(weatherReceiver);
		unregisterReceiver(startTvReceiver);
		unregisterReceiver(userAppReceiver);
		unregisterReceiver(userAppReceiver2);		
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
		/*
		//slow down the focus move
		if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
			if(event.getRepeatCount() % maxSpeedOfFocusMove == 0){
				mScrollLayout.onKeyDown(keyCode,event);
			}else{
				return true;
			}
		}else{
			mScrollLayout.onKeyDown(keyCode, event);
		}
		*/
		View focusView = mScrollLayout.findFocus();
		Log.d(TAG,"userAppSize = " + userAppSize);
		if( userAppSize == 0 ){
			if( focusView == fourthPageFirstLineIcon2 && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
				mScrollLayout.snapToScreen(0);	
			}				
		}else if( userAppSize == 1){
			if( focusView == fourthPageFirstLineIcon3 && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
				mScrollLayout.snapToScreen(0);	
			}	
		}else if( userAppSize == 2){
			if( focusView == fourthPageFirstLineIcon4 && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
				mScrollLayout.snapToScreen(0);	
			}	
		}else if( userAppSize == 3){
			if( (focusView == fourthPageSecondLineIcon1 || focusView == fourthPageFirstLineIcon4) && 
					keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
				mScrollLayout.snapToScreen(0);	
			}		
		}else if( userAppSize == 4){
			if( (focusView == fourthPageSecondLineIcon2 || focusView == fourthPageFirstLineIcon4) && 
					keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
				mScrollLayout.snapToScreen(0);	
			}	
		}else if( userAppSize == 5){
			if(  (focusView == fourthPageSecondLineIcon3 || focusView == fourthPageFirstLineIcon4) && 
					keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
				mScrollLayout.snapToScreen(0);	
			}	
		}else if( userAppSize == 6){
			if(  (focusView == fourthPageSecondLineIcon4 || focusView == fourthPageFirstLineIcon4) && 
					keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
				mScrollLayout.snapToScreen(0);	
			}	
		}else if( userAppSize == 7){
			if(  (focusView == fourthPageSecondLineIcon5 || focusView == fourthPageFirstLineIcon4)  
					&& keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
				mScrollLayout.snapToScreen(0);	
			}	
		}else if( userAppSize >= 8){
			if(  (focusView == fourthPageSecondLineIcon6 || focusView == fourthPageFirstLineIcon4) 
					&& keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
				mScrollLayout.snapToScreen(0);	
			}	
		}
		if(userAppSize >= 3){
			if( (focusView == fourthPageSecondLineIcon1) && 
					keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
				mScrollLayout.snapToScreen(2);	
			}		
		}
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
								|| apinfo.processName.equals("com.amlogic.tvservice")){//process "com.lfzd.enews" started at  onResume
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
	
	private void registerStatusbarEthernetReceiver(){
		IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(ethernetReceiver, filter);
	}

	private void registerStatusbarWifiReceiver(){
		IntentFilter filter = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
		registerReceiver(wifiReceiver, filter);
	}


	private void registerStatusbarUsbReceiver(){
		IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);
		filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		filter.addDataScheme("file");
		registerReceiver(usbReceiver, filter);
	}

	private void registerStatusbarTimeReceiver(){
		IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
		registerReceiver(timeReceiver, filter);
	}	

	private void registerWeatherReceiver(){
		IntentFilter filter = new IntentFilter("com.thtfce.weathers");
		registerReceiver(weatherReceiver, filter);
	}

	private void registerStartTvReceiver(){
		IntentFilter filter = new IntentFilter("com.amlogic.tv.requestStartTV");
		registerReceiver(startTvReceiver, filter);
	}

	private void registerVoiceCommandReceiver(){
		IntentFilter filter = new IntentFilter("com.amlogic.tv.requestStartApp");
		registerReceiver(voiceCommandReceiver,filter);
	}
	
	private void registerUserAppReceiver() {
		IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
		filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
		filter.addDataScheme("package");
		registerReceiver(userAppReceiver, filter);
	}

	private void registerUserAppReceiver2(){
		IntentFilter filter = new IntentFilter("org.thtfce.appstore.update.groupdata");
		registerReceiver(userAppReceiver2, filter);
	}

	private class StartPlayerHandler extends Handler{
		@Override
		public void handleMessage(Message msg)
		{
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
