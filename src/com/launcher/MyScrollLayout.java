package com.launcher;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.GridView;
import android.widget.Scroller;
import android.widget.VideoView;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.LayoutInflater;
import android.view.animation.TranslateAnimation;

import com.launcher.utils.LauncherUtil;
import com.launcher.utils.ResourceManager;
import android.app.ActivityManager;
import android.os.SystemProperties;
import android.content.SharedPreferences;

public class MyScrollLayout extends ViewGroup implements ResourceManager.ResourceHasReleaseListener{

	private static final String TAG = "ScrollLayout";
	private int mCurScreen;
	private int mDefaultScreen = 0;
	private LayoutInflater layoutInflater;
	public boolean resumeFromAtvScreen = true;
	public boolean resumeFromSinaGallery = false;
	public boolean resumeFromPaopaole = false;
	public boolean inAtvScreen = false;
	public Context context;
	private Scroller mScroller;
	
	//first page
	public VideoView firstPageFirstLineIcon1;
	private ImageView firstPageFirstLineIcon2;
	private ImageView firstPageFirstLineIcon3;	
	private ImageView firstPageSecondLineIcon1;
	private ImageView firstPageSecondLineIcon2;
	private ImageView firstPageSecondLineIcon3;
	private ImageView firstPageSecondLineIcon4;	
	private ImageView firstPageSecondLineIcon5;	
	private ImageView firstPageSecondLineIcon6;	
	public ImageView firstPageFirstLineIcon1HighLight;
	private ImageView firstPageFirstLineIcon2HighLight;
	private ImageView firstPageFirstLineIcon3HighLight;	
	private ImageView firstPageSecondLineIcon1HighLight;
	private ImageView firstPageSecondLineIcon2HighLight;
	private ImageView firstPageSecondLineIcon3HighLight;
	private ImageView firstPageSecondLineIcon4HighLight;	
	private ImageView firstPageSecondLineIcon5HighLight;	
	private ImageView firstPageSecondLineIcon6HighLight;
	
	//second page
	private ImageView secondPageFirstLineIcon1;
	private ImageView secondPageFirstLineIcon2;
	private ImageView secondPageFirstLineIcon3;
	private ImageView secondPageSecondLineIcon1;
	private ImageView secondPageSecondLineIcon2;
	private ImageView secondPageSecondLineIcon3;
	private ImageView secondPageSecondLineIcon4;
	private ImageView secondPageFirstLineIcon1HighLight;
	private ImageView secondPageFirstLineIcon2HighLight;
	private ImageView secondPageFirstLineIcon3HighLight;
	private ImageView secondPageSecondLineIcon1HighLight;
	private ImageView secondPageSecondLineIcon2HighLight;
	private ImageView secondPageSecondLineIcon3HighLight;
	private ImageView secondPageSecondLineIcon4HighLight;

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
	private ImageView thirdPageFirstLineIcon1HighLight;
	private ImageView thirdPageFirstLineIcon2HighLight;
	private ImageView thirdPageFirstLineIcon3HighLight;
	private ImageView thirdPageFirstLineIcon4HighLight;
	private ImageView thirdPageFirstLineIcon5HighLight;
	private ImageView thirdPageSecondLineIcon1HighLight;
	private ImageView thirdPageSecondLineIcon2HighLight;
	private ImageView thirdPageSecondLineIcon3HighLight;
	private ImageView thirdPageSecondLineIcon4HighLight;
	private ImageView thirdPageSecondLineIcon5HighLight;

	//fourth page
	private ImageView fourthPageFirstLineIcon1;
	private ImageView fourthPageFirstLineIcon2;
	private ImageView fourthPageFirstLineIcon3;
	private ImageView fourthPageFirstLineIcon4;
	private ImageView fourthPageSecondLineIcon1;
	private ImageView fourthPageFirstLineIcon1HighLight;
	private ImageView fourthPageFirstLineIcon2HighLight;
	private ImageView fourthPageFirstLineIcon3HighLight;

	private LinearLayout userAppParent;
	private GridView userApp;

	//Take close to minimum width of the icon of a value as a reference
	private int basicIconWith = 1920 / 6;
		
	private static boolean defaultfocus_isset =false;
	private OnViewChangeListener mOnViewChangeListener;

	public static ResourceManager mResourceManager;
	public String CLICK_A_APP = "click_a_app";
	public Intent startApkIntent = new Intent();
	public int hasEnterApp=0;
  

	public MyScrollLayout(Context context) {
		super(context);
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		init(context);
	}

	public MyScrollLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context);

	}

	public MyScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init(context);
	}

	private void init(Context context) {
		mCurScreen = mDefaultScreen;
		mScroller = new Scroller(context);
		mResourceManager = new ResourceManager(context);
		mResourceManager.setResourceHasReleaseListener(this);
		killBrowser();// kill Browser process
	}
	
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		Log.d(TAG, "---onLayout---");
		if (changed) {
			int childLeft = 0;
			final int childCount = getChildCount();

			for (int i = 0; i < childCount; i++) {
				final View childView = getChildAt(i);

				if (i == 0) {
					initFirstPageUi(childView);
				
					imageButtonFocuschange(firstPageFirstLineIcon1,firstPageFirstLineIcon1HighLight);
					imageButtonFocuschange(firstPageFirstLineIcon2,firstPageFirstLineIcon2HighLight);
					imageButtonFocuschange(firstPageFirstLineIcon3,firstPageFirstLineIcon3HighLight);
					imageButtonFocuschange(firstPageSecondLineIcon1,firstPageSecondLineIcon1HighLight);
					imageButtonFocuschange(firstPageSecondLineIcon2,firstPageSecondLineIcon2HighLight);
					imageButtonFocuschange(firstPageSecondLineIcon3,firstPageSecondLineIcon3HighLight);
					imageButtonFocuschange(firstPageSecondLineIcon4,firstPageSecondLineIcon4HighLight);
					imageButtonFocuschange(firstPageSecondLineIcon5,firstPageSecondLineIcon5HighLight);
					imageButtonFocuschange(firstPageSecondLineIcon6,firstPageSecondLineIcon6HighLight);

					imageButtonClick(firstPageFirstLineIcon1);
					imageButtonClick(firstPageFirstLineIcon2);
					imageButtonClick(firstPageFirstLineIcon3);
					imageButtonClick(firstPageSecondLineIcon1);
					imageButtonClick(firstPageSecondLineIcon2);
					imageButtonClick(firstPageSecondLineIcon3);
					//imageButtonClick(firstPageSecondLineIcon4);						
					imageButtonClick(firstPageSecondLineIcon5);						
					imageButtonClick(firstPageSecondLineIcon6);						
				}
				
				if (i == 1) {
					initSecondPageUi(childView);	
					
					imageButtonFocuschange(secondPageFirstLineIcon1,secondPageFirstLineIcon1HighLight);
					imageButtonFocuschange(secondPageFirstLineIcon2,secondPageFirstLineIcon2HighLight);
					imageButtonFocuschange(secondPageFirstLineIcon3,secondPageFirstLineIcon3HighLight);
					imageButtonFocuschange(secondPageSecondLineIcon1,secondPageSecondLineIcon1HighLight);
					imageButtonFocuschange(secondPageSecondLineIcon2,secondPageSecondLineIcon2HighLight);
					imageButtonFocuschange(secondPageSecondLineIcon3,secondPageSecondLineIcon3HighLight);
					imageButtonFocuschange(secondPageSecondLineIcon4,secondPageSecondLineIcon4HighLight);

					imageButtonClick(secondPageFirstLineIcon1);
					imageButtonClick(secondPageFirstLineIcon2);
					imageButtonClick(secondPageFirstLineIcon3);
					imageButtonClick(secondPageSecondLineIcon1);
					imageButtonClick(secondPageSecondLineIcon2);					
					imageButtonClick(secondPageSecondLineIcon3);					
					imageButtonClick(secondPageSecondLineIcon4);					
					
				}

				if (i == 2) {
					initThirdPageUi(childView);	
					
					imageButtonFocuschange(thirdPageFirstLineIcon1,thirdPageFirstLineIcon1HighLight);
					imageButtonFocuschange(thirdPageFirstLineIcon2,thirdPageFirstLineIcon2HighLight);
					imageButtonFocuschange(thirdPageFirstLineIcon3,thirdPageFirstLineIcon3HighLight);
					imageButtonFocuschange(thirdPageFirstLineIcon4,thirdPageFirstLineIcon4HighLight);
					imageButtonFocuschange(thirdPageFirstLineIcon5,thirdPageFirstLineIcon5HighLight);
					imageButtonFocuschange(thirdPageSecondLineIcon1,thirdPageSecondLineIcon1HighLight);
					imageButtonFocuschange(thirdPageSecondLineIcon2,thirdPageSecondLineIcon2HighLight);
					imageButtonFocuschange(thirdPageSecondLineIcon3,thirdPageSecondLineIcon3HighLight);
					imageButtonFocuschange(thirdPageSecondLineIcon4,thirdPageSecondLineIcon4HighLight);
					imageButtonFocuschange(thirdPageSecondLineIcon5,thirdPageSecondLineIcon5HighLight);

					imageButtonClick(thirdPageFirstLineIcon1);
					imageButtonClick(thirdPageFirstLineIcon2);
					imageButtonClick(thirdPageFirstLineIcon3);
					imageButtonClick(thirdPageFirstLineIcon4);
					imageButtonClick(thirdPageFirstLineIcon5);
					imageButtonClick(thirdPageSecondLineIcon1);
					imageButtonClick(thirdPageSecondLineIcon2);
					imageButtonClick(thirdPageSecondLineIcon3);
					imageButtonClick(thirdPageSecondLineIcon4);
					imageButtonClick(thirdPageSecondLineIcon5);

				}
				
				if (i == 3) {
					initFourthPageUi(childView);

					imageButtonFocuschange(fourthPageFirstLineIcon1,fourthPageFirstLineIcon1HighLight);
					imageButtonFocuschange(fourthPageFirstLineIcon2,fourthPageFirstLineIcon2HighLight);
					imageButtonFocuschange(fourthPageFirstLineIcon3,fourthPageFirstLineIcon3HighLight);

					imageButtonClick(fourthPageFirstLineIcon1);
					imageButtonClick(fourthPageFirstLineIcon2);
					imageButtonClick(fourthPageFirstLineIcon3);
				}
				
				if (childView.getVisibility() != View.GONE) {
					final int childWidth = childView.getMeasuredWidth();
					childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
					childLeft += childWidth;
				}
			}
			firstPageFirstLineIcon1.setFocusable(true);
			firstPageFirstLineIcon1.setFocusableInTouchMode(true);
			firstPageFirstLineIcon1.requestFocus();	
			firstPageFirstLineIcon1HighLight.setBackgroundResource(R.drawable.item_selected_big);

			if(resumeFromSinaGallery){
				firstPageSecondLineIcon2.setFocusable(true);
				firstPageSecondLineIcon2.setFocusableInTouchMode(true);			
				firstPageSecondLineIcon2.requestFocus();
			}
			if(resumeFromPaopaole){
				secondPageSecondLineIcon3.setFocusable(true);
				secondPageSecondLineIcon3.setFocusableInTouchMode(true);			
				secondPageSecondLineIcon3.requestFocus();
			}

			if(getFocusIconName().matches("multiScreen")){
				Log.d(TAG,"Relayout multiScreen RequestFocus");
              thirdPageSecondLineIcon5.setFocusable(true);
				thirdPageSecondLineIcon5.setFocusableInTouchMode(true);
				thirdPageSecondLineIcon5.requestFocus();
			}
			
			
		}
		else{				
         	if( false == defaultfocus_isset){
				firstPageFirstLineIcon1.setFocusable(true);
				firstPageFirstLineIcon1.setFocusableInTouchMode(true);
				firstPageFirstLineIcon1.requestFocus();
				defaultfocus_isset = true ;	
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.d(TAG, "---onMeasure---");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		scrollTo(mCurScreen * width, 0);
	}

	public void snapToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		Log.d(TAG, "---snapToScreen---" + whichScreen);
		
		if (getScrollX() != (whichScreen * getWidth())) {
			final int delta = whichScreen * getWidth() - getScrollX();
			// mScroller.startScroll(getScrollX(), 0, delta, 0,
			// Math.abs(delta)*2);
			if (mOnViewChangeListener != null) {
				mOnViewChangeListener.OnViewChange(mCurScreen, false);
			}
			mScroller.startScroll(getScrollX(), 0, delta, 0, 500);
			mCurScreen = whichScreen;
			invalidate(); // Redraw the layout
			if (mOnViewChangeListener != null) {
				mOnViewChangeListener.OnViewChange(mCurScreen, true);
			}
		}
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	public void SetOnViewChangeListener(OnViewChangeListener listener) {
		mOnViewChangeListener = listener;
	}

	private void initFirstPageUi(View view) {
	
		firstPageFirstLineIcon1 = (VideoView) view.findViewById(R.id.first_page101);
		firstPageFirstLineIcon2 = (ImageView) view.findViewById(R.id.first_page102);
		firstPageFirstLineIcon3 = (ImageView) view.findViewById(R.id.first_page103);
		firstPageSecondLineIcon1 = (ImageView) view.findViewById(R.id.first_page201);
		firstPageSecondLineIcon2 = (ImageView) view.findViewById(R.id.first_page202);
		firstPageSecondLineIcon3 = (ImageView) view.findViewById(R.id.first_page203);		
		firstPageSecondLineIcon4 = (ImageView) view.findViewById(R.id.first_page204);
		firstPageSecondLineIcon5 = (ImageView) view.findViewById(R.id.first_page205);
		firstPageSecondLineIcon6 = (ImageView) view.findViewById(R.id.first_page206);

		firstPageFirstLineIcon1HighLight= (ImageView) view.findViewById(R.id.first_page101_highlight);
		firstPageFirstLineIcon2HighLight = (ImageView) view.findViewById(R.id.first_page102_highlight);
		firstPageFirstLineIcon3HighLight = (ImageView) view.findViewById(R.id.first_page103_highlight);
		firstPageSecondLineIcon1HighLight= (ImageView) view.findViewById(R.id.first_page201_highlight);
		firstPageSecondLineIcon2HighLight = (ImageView) view.findViewById(R.id.first_page202_highlight);
		firstPageSecondLineIcon3HighLight = (ImageView) view.findViewById(R.id.first_page203_highlight);		
		firstPageSecondLineIcon4HighLight = (ImageView) view.findViewById(R.id.first_page204_highlight);			
		firstPageSecondLineIcon5HighLight = (ImageView) view.findViewById(R.id.first_page205_highlight);			
		firstPageSecondLineIcon6HighLight = (ImageView) view.findViewById(R.id.first_page206_highlight);			
	}

	private void initSecondPageUi(View view) {	
		secondPageFirstLineIcon1 = (ImageView) view.findViewById(R.id.second_page101);
		secondPageFirstLineIcon2 = (ImageView) view.findViewById(R.id.second_page102);
		secondPageFirstLineIcon3 = (ImageView) view.findViewById(R.id.second_page103);
		secondPageSecondLineIcon1 = (ImageView) view.findViewById(R.id.second_page201);
		secondPageSecondLineIcon2 = (ImageView) view.findViewById(R.id.second_page202);
		secondPageSecondLineIcon3 = (ImageView) view.findViewById(R.id.second_page203);
		secondPageSecondLineIcon4 = (ImageView) view.findViewById(R.id.second_page204);
		
		secondPageFirstLineIcon1HighLight= (ImageView) view.findViewById(R.id.second_page101_highlight);
		secondPageFirstLineIcon2HighLight = (ImageView) view.findViewById(R.id.second_page102_highlight);
		secondPageFirstLineIcon3HighLight = (ImageView) view.findViewById(R.id.second_page103_highlight);
		secondPageSecondLineIcon1HighLight = (ImageView) view.findViewById(R.id.second_page201_highlight);
		secondPageSecondLineIcon2HighLight = (ImageView) view.findViewById(R.id.second_page202_highlight);
		secondPageSecondLineIcon3HighLight = (ImageView) view.findViewById(R.id.second_page203_highlight);			
		secondPageSecondLineIcon4HighLight = (ImageView) view.findViewById(R.id.second_page204_highlight);			
	}

	private void initThirdPageUi(View view){
		thirdPageFirstLineIcon1 = (ImageView) view.findViewById(R.id.third_page101);
		thirdPageFirstLineIcon2 = (ImageView) view.findViewById(R.id.third_page102);
		thirdPageFirstLineIcon3 = (ImageView) view.findViewById(R.id.third_page103);
		thirdPageFirstLineIcon4 = (ImageView) view.findViewById(R.id.third_page104);
		thirdPageFirstLineIcon5 = (ImageView) view.findViewById(R.id.third_page105);
		thirdPageSecondLineIcon1 = (ImageView) view.findViewById(R.id.third_page201);
		thirdPageSecondLineIcon2 = (ImageView) view.findViewById(R.id.third_page202);
		thirdPageSecondLineIcon3 = (ImageView) view.findViewById(R.id.third_page203);
		thirdPageSecondLineIcon4 = (ImageView) view.findViewById(R.id.third_page204);
		thirdPageSecondLineIcon5 = (ImageView) view.findViewById(R.id.third_page205);

		thirdPageFirstLineIcon1HighLight= (ImageView) view.findViewById(R.id.third_page101_highlight);
		thirdPageFirstLineIcon2HighLight = (ImageView) view.findViewById(R.id.third_page102_highlight);
		thirdPageFirstLineIcon3HighLight = (ImageView) view.findViewById(R.id.third_page103_highlight);
		thirdPageFirstLineIcon4HighLight = (ImageView) view.findViewById(R.id.third_page104_highlight);
		thirdPageFirstLineIcon5HighLight = (ImageView) view.findViewById(R.id.third_page105_highlight);
		thirdPageSecondLineIcon1HighLight= (ImageView) view.findViewById(R.id.third_page201_highlight);
		thirdPageSecondLineIcon2HighLight = (ImageView) view.findViewById(R.id.third_page202_highlight);
		thirdPageSecondLineIcon3HighLight = (ImageView) view.findViewById(R.id.third_page203_highlight);
		thirdPageSecondLineIcon4HighLight = (ImageView) view.findViewById(R.id.third_page204_highlight);		
		thirdPageSecondLineIcon5HighLight = (ImageView) view.findViewById(R.id.third_page205_highlight);		
	}

	private void initFourthPageUi(View view){
		fourthPageFirstLineIcon1 = (ImageView) view.findViewById(R.id.fourth_page101);
		fourthPageFirstLineIcon2 = (ImageView) view.findViewById(R.id.fourth_page102);
		fourthPageFirstLineIcon3 = (ImageView) view.findViewById(R.id.fourth_page103);
		fourthPageFirstLineIcon4 = (ImageView) findViewById(R.id.fourth_page104_icon);
		fourthPageSecondLineIcon1 = (ImageView) findViewById(R.id.fourth_page201_icon);		
		fourthPageFirstLineIcon1HighLight= (ImageView) view.findViewById(R.id.fourth_page101_highlight);
		fourthPageFirstLineIcon2HighLight = (ImageView) view.findViewById(R.id.fourth_page102_highlight);
		fourthPageFirstLineIcon3HighLight = (ImageView) view.findViewById(R.id.fourth_page103_highlight);
	}

	private void startApks(final ImageView imageButton) {
		resumeFromAtvScreen = false;
		resumeFromSinaGallery = false;
		resumeFromPaopaole = false;
		setFocusIconName("none");		
		imageButton.setFocusableInTouchMode(true);
		imageButton.requestFocus();
		imageButton.requestFocusFromTouch();
		String packageName = "";
	
		/***first page***/
			//firstLine
		if (imageButton == firstPageFirstLineIcon2) {
			//-----------------------------------this variable is set for BesTv
			SwitchViewDemoActivity.mTvPreview.tv.SetBestvSoundCurveEnable(1);
			//----------------------------------------------------------			
			Intent intent = new Intent();
			intent.setClassName("com.amlogic.bestv", "com.amlogic.bestv.BesTVActivity");
			releaseFirstThenStartApk(intent);
		} else if (imageButton == firstPageFirstLineIcon3) {
			//disable the Greenkey
			setColorKeyFlag("on");
		
			packageName = "com.lfzd.enews";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} 

       	//secondLine
		else if (imageButton == firstPageSecondLineIcon1) {
			packageName = "com.tencent.qqmusicpad";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == firstPageSecondLineIcon2) {
		   resumeFromSinaGallery = true;

			//disable the Greenkey
			setColorKeyFlag("on");
			
			packageName = "com.lfzd.sinagallery";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == firstPageSecondLineIcon3) {
			packageName = "com.trans.gamehall";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);	
		} else if (imageButton == firstPageSecondLineIcon4) {
			//this icon special processed in SwitchDemoActivity.java to start GreenNet
		} else if (imageButton == firstPageSecondLineIcon5) {
			Intent intent = new Intent();
			intent.setClassName("com.amlogic.filebrowser", "com.amlogic.filebrowser.MediaFilebrowser");
			intent.putExtra("launch_what","usb");
			releaseFirstThenStartApk(intent);		
		} else if (imageButton == firstPageSecondLineIcon6) {
			packageName = "com.thtf.myhouse";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		}
			

		/***second page***/
			//firstLine
		else if (imageButton == secondPageFirstLineIcon1) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("AppCode","kids");
			intent.putExtras(bundle);
			intent.setClassName("com.amlogic.bestv", "com.amlogic.bestv.BesTVActivity");
			releaseFirstThenStartApk(intent);
		} else if (imageButton == secondPageFirstLineIcon2) {
			packageName = "com.thtfce.readerpen";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == secondPageFirstLineIcon3) {

			//disable the Greenkey
			setColorKeyFlag("on");
					
			packageName = "com.thtfce.web";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
			releaseFirstThenStartApk(intent);
		} 

			//secondLine
		else if (imageButton == secondPageSecondLineIcon1) {

			//disable the Greenkey
			setColorKeyFlag("on");
						
			packageName = "com.thtfce.edu";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == secondPageSecondLineIcon2) {
			packageName = "com.netease.vopen.tablet";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == secondPageSecondLineIcon3) {
			resumeFromPaopaole = true;
			packageName = "com.cpsoft.game.paopaole3d";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == secondPageSecondLineIcon4) {			
			packageName = "thtf.cpsoft.fly3d";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		}   
		
		/***third page***/
			//firstLine
		 else if (imageButton == thirdPageFirstLineIcon1) {
			packageName = "com.cvte.health";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == thirdPageFirstLineIcon2) {

			//disable the Greenkey
			setColorKeyFlag("on");
					
			packageName = "com.thtf.guide";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == thirdPageFirstLineIcon3) {
			packageName = "com.android.thtf.thtfcookbook";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == thirdPageFirstLineIcon4) {		
			packageName = "com.android.thtf";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == thirdPageFirstLineIcon5) {		
			packageName = "com.huifutianxia";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} 

			//secondLine
		else if (imageButton == thirdPageSecondLineIcon1) {		
			Intent intent = new Intent();
			intent.setClassName("com.reconova.tongfang", "com.thtf.facerealize.FaceMainActivity");
			releaseFirstThenStartApk(intent);	
		} else if (imageButton == thirdPageSecondLineIcon2) {		
			Intent intent = new Intent();
			intent.setClassName("com.reconova.tongfang", "com.reconova.demo.SplashActivity");
			releaseFirstThenStartApk(intent);
		} else if (imageButton == thirdPageSecondLineIcon3) {
			packageName = "viva.android.tv";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == thirdPageSecondLineIcon4) {
			packageName = "com.rockitv.android";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == thirdPageSecondLineIcon5) {
		    setFocusIconName("multiScreen");		
			packageName = "com.awindinc.mirroropservice";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} 
		
		/***fourth page***/
		
	   if(imageButton == fourthPageFirstLineIcon1){
			packageName = "com.example.newthtfcemarket";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		}else if(imageButton == fourthPageFirstLineIcon2){
			packageName = "com.letv.tvos.appstore";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		}else if(imageButton == fourthPageFirstLineIcon3){

			//disable the Greenkey
			setColorKeyFlag("on");
					
			packageName = "org.thtf.myapp";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		}		
	}
	
	private void startApks(final VideoView imageButton) {
		resumeFromAtvScreen = false;
		resumeFromSinaGallery = false;
		resumeFromPaopaole = false;
		setFocusIconName("none");		
		//imageButton.setFocusableInTouchMode(true);
		//imageButton.requestFocus();
		String packageName = "";

		//tvpreview
		if (imageButton == firstPageFirstLineIcon1) {
			resumeFromAtvScreen = true;
			//Stop start AtvScreen twice in a moment
			if( !inAtvScreen ){
		       SwitchViewDemoActivity.mTvPreview.startAtvScreen();
				inAtvScreen = true;
			}
		}
	}

	public void imageButtonClick(final ImageView imageButton) {
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick");
				startApks(imageButton);
			}
		});
	}
	
	public void imageButtonClick(final VideoView imageButton) {
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick");
				startApks(imageButton);
			}
		});
        imageButton.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.d(TAG,"onTouch");
				firstPageFirstLineIcon1.setFocusableInTouchMode(true);
				firstPageFirstLineIcon1.requestFocus();					
				startApks(imageButton);
				return false;
			}     	
        });		
	}

	public void imageButtonFocuschange(final VideoView imageButton,final ImageView imageView) {
		imageButton.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if( imageButton.getWidth() > basicIconWith){
						imageView.setBackgroundResource(R.drawable.item_selected_big);
					}else{
						imageView.setBackgroundResource(R.drawable.item_selected_little);					
					}
				} else if (hasFocus == false) {
					imageView.setBackgroundResource(R.drawable.nothing);
			   }
			}
		});
	}
	
	public void imageButtonFocuschange(final ImageView imageButton,final ImageView imageView) {
		imageButton.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if( imageButton.getWidth() > basicIconWith){
						imageView.setBackgroundResource(R.drawable.item_selected_big);
					}else{
						imageView.setBackgroundResource(R.drawable.item_selected_little);					
					}
				} else if (hasFocus == false) {
					imageView.setBackgroundResource(R.drawable.nothing);
			   }
			}
		});
	}
 
   public void releaseFirstThenStartApk(Intent intent){
	 	//set the page property	if (launcher not in first page){hide the tvprevie source icon} 
		SystemProperties.set("tv.launcher_page", "xiangxing.wu");	
	   	startApkIntent = intent;
	   	if(hasEnterApp==0){
	   		hasEnterApp=1;
	   	 	mResourceManager.releaseAllResource(CLICK_A_APP);
	   	}
    }
	 
	public void startActivitySafely(Intent intent) {
		try {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			Log.e(TAG, "Unable to launch. tag=" + TAG + " intent=" + intent, e);
		} catch (SecurityException e) {
			Log.e(TAG, "Launcher does not have the permission to launch " + intent + 
					 ". Make sure to create a MAIN intent-filter for the corresponding activity " + 
					 "or use the exported attribute for this activity. " + "tag=" + TAG + " intent=" + intent, e);
		} catch (NullPointerException e) {
			Log.d(TAG, "Intent is null");
		}
	}

	public void atFirstPageSnapLeft(){
		setViewFocus(3);
		snapToScreen(3);
	}

	public void snapNext(){
		setViewFocus(mCurScreen + 1);			
		snapToScreen(mCurScreen + 1);	
	}

	public void snapPrevious(){
		setViewFocus(mCurScreen - 1);
		snapToScreen(mCurScreen - 1);
	}

	public void atThirdPageSnapRight(){
		setViewFocus(0);
		snapToScreen(0);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		View focusView = MyScrollLayout.this.findFocus();
		if ( (focusView == firstPageFirstLineIcon1 || focusView == firstPageSecondLineIcon1) && 
				keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			snapToScreen(3);	
		} else if( (focusView == firstPageFirstLineIcon3 || focusView == firstPageSecondLineIcon6) && 
				keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			snapToScreen(1);	
		} else if( (focusView == secondPageFirstLineIcon1 || focusView == secondPageSecondLineIcon1) && 
				keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			snapToScreen(0);
		} else if( (focusView == secondPageFirstLineIcon3 || focusView == secondPageSecondLineIcon4 ) &&
				keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			snapToScreen(2);	
		} else if( (focusView == thirdPageFirstLineIcon1 || focusView == thirdPageSecondLineIcon1) &&
				keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
			snapToScreen(1);
		} else if((focusView == thirdPageFirstLineIcon5 || focusView == thirdPageSecondLineIcon5) &&
				keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
			snapToScreen(3);	
		} else if((focusView == fourthPageFirstLineIcon1) && keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
			snapToScreen(2);	
		} else if( focusView == fourthPageFirstLineIcon4 && 
				keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
			snapToScreen(0);	
		} else if( (focusView == fourthPageSecondLineIcon1) && 
				keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
			snapToScreen(2);	
		}

		if (keyCode == 87) {// next music
			if(mCurScreen == 3){
				atThirdPageSnapRight();
			}else{		
				snapNext();
			}
		}
		
		if (keyCode == 88) {// previous music
			Log.d(TAG,"keycode  == 88");
			if(mCurScreen == 0){
				atFirstPageSnapLeft();
			}else{
				snapPrevious();
			}		
		}
		
		if(keyCode == 183){//red
			if( mCurScreen != 0){
				setViewFocus(0);
				snapToScreen(0);
			}
		}
		
		if(keyCode == 184){//green
			if( mCurScreen != 1 ){
				setViewFocus(1);
				snapToScreen(1);
			}
		}
		
		if(keyCode == 185){//yellow
			if( mCurScreen != 2 ){
				setViewFocus(2);
				snapToScreen(2);
			}
		}
		
		if(keyCode == 186){//blue
			if( mCurScreen != 3 ){
				setViewFocus(3);
				snapToScreen(3);
			}
		}			
		return true;		
	}

	private void setViewFocus(int index){
		switch (index) {			
		case 0:		
			firstPageFirstLineIcon1.setFocusableInTouchMode(true);
			firstPageFirstLineIcon1.requestFocus();
			firstPageFirstLineIcon1.requestFocusFromTouch();			
			break;
		case 1:
			secondPageFirstLineIcon1.setFocusableInTouchMode(true);
			secondPageFirstLineIcon1.requestFocus();
			secondPageFirstLineIcon1.requestFocusFromTouch();
			break;
		case 2:			
			thirdPageFirstLineIcon1.setFocusableInTouchMode(true);
			thirdPageFirstLineIcon1.requestFocus();
			thirdPageFirstLineIcon1.requestFocusFromTouch();
			break;
		case 3:			
			fourthPageFirstLineIcon1.setFocusableInTouchMode(true);
			fourthPageFirstLineIcon1.requestFocus();
			fourthPageFirstLineIcon1.requestFocusFromTouch();
			break;
		default:
			break;
		}
	}
	public void killBrowser() {
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		am.restartPackage("com.android.browser");
	}

	public void onHasReleased(String owner, boolean hasReleased) {
		if (!hasReleased) {
			return;
		}
		Log.d(TAG, "=====owner====" + owner); 
		if (owner != null && owner.equals(CLICK_A_APP)) {
			mResourceManager.release();
			SwitchViewDemoActivity.mTvPreview.enter_app = true;
			startActivitySafely(startApkIntent);
			//mResourceManager.release();
			//SwitchViewDemoActivity.mTvPreview.SetAllbypass("1");
			hasEnterApp=0;
		} 	
	}

	public void setFocusIconName(String focusIcon){
	   SharedPreferences curFocs = context.getSharedPreferences("focus_position", Context.MODE_PRIVATE);
	   SharedPreferences.Editor writeDate = curFocs.edit();
		writeDate.putString("focus_icon",focusIcon);
		writeDate.commit();
	}

	public void setColorKeyFlag(String flag){
		SystemProperties.set("tv.color_key", flag);
	}

	public String getFocusIconName(){
		String focusIconName = null;
	   SharedPreferences curFocs = context.getSharedPreferences("focus_position", Context.MODE_PRIVATE);
		focusIconName = curFocs.getString("focus_icon","none");
		Log.d(TAG,"get focusIconName : " + focusIconName);
		return focusIconName;
	}
	
}
