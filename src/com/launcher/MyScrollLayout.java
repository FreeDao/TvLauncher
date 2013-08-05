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
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.animation.TranslateAnimation;

import com.launcher.utils.LauncherUtil;
import com.launcher.utils.ResourceManager;
import android.app.ActivityManager;
import android.os.SystemProperties;

public class MyScrollLayout extends ViewGroup implements ResourceManager.ResourceHasReleaseListener{

	private static final String TAG = "ScrollLayout";
	private int mCurScreen;
	private int mDefaultScreen = 0;
	private LayoutInflater layoutInflater;
	public boolean resumeFromAtvScreen = true;
	public boolean inAtvScreen = false;
	public Context context;
	private Scroller mScroller;
	
	//first page
	private ImageView firstPageFirstLineIcon1;
	private ImageView firstPageFirstLineIcon2;
	private ImageView firstPageFirstLineIcon3;	
	private ImageView firstPageSecondLineIcon1;
	private ImageView firstPageSecondLineIcon2;
	private ImageView firstPageSecondLineIcon3;
	private ImageView firstPageSecondLineIcon4;	
	private LinearLayout firstPageFirstLineIcon1Layout;
	private LinearLayout firstPageFirstLineIcon2Layout;
	private LinearLayout firstPageFirstLineIcon3Layout;
	private LinearLayout firstPageSecondLineIcon1Layout;
	private LinearLayout firstPageSecondLineIcon2Layout;
	private LinearLayout firstPageSecondLineIcon3Layout;
	private LinearLayout firstPageSecondLineIcon4Layout;
	
	//second page
	private ImageView secondPageFirstLineIcon1;
	private ImageView secondPageFirstLineIcon2;
	private ImageView secondPageFirstLineIcon3;
	private ImageView secondPageSecondLineIcon1;
	private ImageView secondPageSecondLineIcon2;
	private ImageView secondPageSecondLineIcon3;
	private LinearLayout secondPageFirstLineIcon1Layout;
	private LinearLayout secondPageFirstLineIcon2Layout;
	private LinearLayout secondPageFirstLineIcon3Layout;
	private LinearLayout secondPageSecondLineIcon1Layout;
	private LinearLayout secondPageSecondLineIcon2Layout;
	private LinearLayout secondPageSecondLineIcon3Layout;

	//third page
	private ImageView thirdPageFirstLineIcon1;
	private ImageView thirdPageFirstLineIcon2;
	private ImageView thirdPageFirstLineIcon3;
	private ImageView thirdPageFirstLineIcon4;
	private ImageView thirdPageSecondLineIcon1;
	private ImageView thirdPageSecondLineIcon2;
	private ImageView thirdPageSecondLineIcon3;
	private ImageView thirdPageSecondLineIcon4;
	private LinearLayout thirdPageFirstLineIcon1Layout;
	private LinearLayout thirdPageFirstLineIcon2Layout;
	private LinearLayout thirdPageFirstLineIcon3Layout;
	private LinearLayout thirdPageFirstLineIcon4Layout;
	private LinearLayout thirdPageSecondLineIcon1Layout;
	private LinearLayout thirdPageSecondLineIcon2Layout;
	private LinearLayout thirdPageSecondLineIcon3Layout;
	private LinearLayout thirdPageSecondLineIcon4Layout;

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
	private LinearLayout fourthPageFirstLineIcon1Layout;
	private LinearLayout fourthPageFirstLineIcon2Layout;

	private LinearLayout userAppParent;
	private GridView userApp;

	//Take close to minimum width of the icon of a value as a reference
	private int basicIconWith = 1920 / 6;
		
	private static boolean defaultfocus_isset =false;
	private OnViewChangeListener mOnViewChangeListener;

	public static ResourceManager mResourceManager;
	private String CLICK_A_APP = "click_a_app";
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
				
					imageButtonFocuschange(firstPageFirstLineIcon1,firstPageFirstLineIcon1Layout);
					imageButtonFocuschange(firstPageFirstLineIcon2,firstPageFirstLineIcon2Layout);
					imageButtonFocuschange(firstPageFirstLineIcon3,firstPageFirstLineIcon3Layout);
					imageButtonFocuschange(firstPageSecondLineIcon1,firstPageSecondLineIcon1Layout);
					imageButtonFocuschange(firstPageSecondLineIcon2,firstPageSecondLineIcon2Layout);
					imageButtonFocuschange(firstPageSecondLineIcon3,firstPageSecondLineIcon3Layout);
					imageButtonFocuschange(firstPageSecondLineIcon4,firstPageSecondLineIcon4Layout);

					imageButtonClick(firstPageFirstLineIcon1);
					imageButtonClick(firstPageFirstLineIcon2);
					imageButtonClick(firstPageFirstLineIcon3);
					imageButtonClick(firstPageSecondLineIcon1);
					imageButtonClick(firstPageSecondLineIcon2);
					//imageButtonClick(firstPageSecondLineIcon3);
					imageButtonClick(firstPageSecondLineIcon4);						
				}
				
				if (i == 1) {
					initSecondPageUi(childView);	
					
					imageButtonFocuschange(secondPageFirstLineIcon1,secondPageFirstLineIcon1Layout);
					imageButtonFocuschange(secondPageFirstLineIcon2,secondPageFirstLineIcon2Layout);
					imageButtonFocuschange(secondPageFirstLineIcon3,secondPageFirstLineIcon3Layout);
					imageButtonFocuschange(secondPageSecondLineIcon1,secondPageSecondLineIcon1Layout);
					imageButtonFocuschange(secondPageSecondLineIcon2,secondPageSecondLineIcon2Layout);
					imageButtonFocuschange(secondPageSecondLineIcon3,secondPageSecondLineIcon3Layout);

					imageButtonClick(secondPageFirstLineIcon1);
					imageButtonClick(secondPageFirstLineIcon2);
					imageButtonClick(secondPageFirstLineIcon3);
					imageButtonClick(secondPageSecondLineIcon1);
					imageButtonClick(secondPageSecondLineIcon2);					
					imageButtonClick(secondPageSecondLineIcon3);					
					
					resumeFromAtvScreen = false;
					/*
					String packageName = "";
					if(position == 0){
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putString("AppCode","kids");
						intent.putExtras(bundle);
						intent.setClassName("com.amlogic.bestv", "com.amlogic.bestv.BesTVActivity");
						releaseFirstThenStartApk(intent);
					}
					if(position == 1){
						packageName = "com.thtfce.readerpen";
						Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
						releaseFirstThenStartApk(intent);
					}
					if(position == 2){
						packageName = "com.thtfce.web";
						Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
						releaseFirstThenStartApk(intent);
					}
					if(position == 3){
						packageName = "com.thtfce.edu";
						Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
						releaseFirstThenStartApk(intent);
					}
					if(position == 4){
						packageName = "com.netease.vopen.tablet";
						Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
						releaseFirstThenStartApk(intent);
					}
					if(position == 5){
						packageName = "com.cpsoft.game.paopaole3d";
						Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
						releaseFirstThenStartApk(intent);
					}
					if(position == 6){
						packageName = "thtf.cpsoft.fly3d";
						Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
						releaseFirstThenStartApk(intent);
					}							
					*/

				}


				if (i == 2) {
					initThirdPageUi(childView);	
					
					imageButtonFocuschange(thirdPageFirstLineIcon1,thirdPageFirstLineIcon1Layout);
					imageButtonFocuschange(thirdPageFirstLineIcon2,thirdPageFirstLineIcon2Layout);
					imageButtonFocuschange(thirdPageFirstLineIcon3,thirdPageFirstLineIcon3Layout);
					imageButtonFocuschange(thirdPageFirstLineIcon4,thirdPageFirstLineIcon4Layout);
					imageButtonFocuschange(thirdPageSecondLineIcon1,thirdPageSecondLineIcon1Layout);
					imageButtonFocuschange(thirdPageSecondLineIcon2,thirdPageSecondLineIcon2Layout);
					imageButtonFocuschange(thirdPageSecondLineIcon3,thirdPageSecondLineIcon3Layout);
					imageButtonFocuschange(thirdPageSecondLineIcon4,thirdPageSecondLineIcon4Layout);

					imageButtonClick(thirdPageFirstLineIcon1);
					imageButtonClick(thirdPageFirstLineIcon2);
					imageButtonClick(thirdPageFirstLineIcon3);
					imageButtonClick(thirdPageFirstLineIcon4);
					imageButtonClick(thirdPageSecondLineIcon1);
					imageButtonClick(thirdPageSecondLineIcon2);
					imageButtonClick(thirdPageSecondLineIcon3);
					imageButtonClick(thirdPageSecondLineIcon4);

					resumeFromAtvScreen = false;
					/*
					String packageName = "";
					if( arg2 == 0 ){
						packageName = "com.trans.gamehall";
						Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
						releaseFirstThenStartApk(intent);
					}
					
					if( arg2 == 1 ){
						packageName = "com.android.thtf";
						Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
						releaseFirstThenStartApk(intent);
					}
					
					if( arg2 == 2 ){
						packageName = "com.thtf.guide";
						Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
						releaseFirstThenStartApk(intent);
					}
					
					if( arg2 == 3 ){
						packageName = "com.android.thtf.thtfcookbook";
						Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
						releaseFirstThenStartApk(intent);
					}
					
					if( arg2 == 4 ){								
						packageName = "com.cvte.health";
						Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
						releaseFirstThenStartApk(intent);
					}
					*/
				}
				
				if (i == 3) {
					initFourthPageUi(childView);

					imageButtonFocuschange(fourthPageFirstLineIcon1,fourthPageFirstLineIcon1Layout);
					imageButtonFocuschange(fourthPageFirstLineIcon2,fourthPageFirstLineIcon2Layout);
					/*
					imageButtonFocuschange(fourthPageFirstLineIcon3,fourthPageFirstLineIcon2Layout);
					imageButtonFocuschange(fourthPageFirstLineIcon4,fourthPageFirstLineIcon2Layout);
					imageButtonFocuschange(fourthPageSecondLineIcon1,fourthPageSecondLineIcon1Layout);
					imageButtonFocuschange(fourthPageSecondLineIcon2,fourthPageSecondLineIcon2Layout);
					imageButtonFocuschange(fourthPageSecondLineIcon3,fourthPageSecondLineIcon3Layout);
					imageButtonFocuschange(fourthPageSecondLineIcon4,fourthPageSecondLineIcon4Layout);
					imageButtonFocuschange(fourthPageSecondLineIcon5,fourthPageSecondLineIcon5Layout);
					imageButtonFocuschange(fourthPageSecondLineIcon6,fourthPageSecondLineIcon6Layout);

					imageButtonClick(fourthPageFirstLineIcon1);
					imageButtonClick(fourthPageFirstLineIcon2);
					imageButtonClick(fourthPageFirstLineIcon3);
					imageButtonClick(fourthPageFirstLineIcon4);
					imageButtonClick(fourthPageSecondLineIcon1);
					imageButtonClick(fourthPageSecondLineIcon2);
					imageButtonClick(fourthPageSecondLineIcon3);
					imageButtonClick(fourthPageSecondLineIcon4);
					imageButtonClick(fourthPageSecondLineIcon5);
					imageButtonClick(fourthPageSecondLineIcon6);
					*/
					
				}
				
				if (childView.getVisibility() != View.GONE) {
					final int childWidth = childView.getMeasuredWidth();
					childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
					childLeft += childWidth;
				}
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
	
		firstPageFirstLineIcon1 = (ImageView) view.findViewById(R.id.first_page101);
		firstPageFirstLineIcon2 = (ImageView) view.findViewById(R.id.first_page102);
		firstPageFirstLineIcon3 = (ImageView) view.findViewById(R.id.first_page103);
		firstPageSecondLineIcon1 = (ImageView) view.findViewById(R.id.first_page201);
		firstPageSecondLineIcon2 = (ImageView) view.findViewById(R.id.first_page202);
		firstPageSecondLineIcon3 = (ImageView) view.findViewById(R.id.first_page203);		
		firstPageSecondLineIcon4 = (ImageView) view.findViewById(R.id.first_page204);

		firstPageFirstLineIcon1Layout = (LinearLayout) view.findViewById(R.id.first_page101_layout);
		firstPageFirstLineIcon2Layout = (LinearLayout) view.findViewById(R.id.first_page102_layout);
		firstPageFirstLineIcon3Layout = (LinearLayout) view.findViewById(R.id.first_page103_layout);
		firstPageSecondLineIcon1Layout = (LinearLayout) view.findViewById(R.id.first_page201_layout);
		firstPageSecondLineIcon2Layout = (LinearLayout) view.findViewById(R.id.first_page202_layout);
		firstPageSecondLineIcon3Layout = (LinearLayout) view.findViewById(R.id.first_page203_layout);		
		firstPageSecondLineIcon4Layout = (LinearLayout) view.findViewById(R.id.first_page204_layout);			
	}

	private void initSecondPageUi(View view) {	
		secondPageFirstLineIcon1 = (ImageView) view.findViewById(R.id.second_page101);
		secondPageFirstLineIcon2 = (ImageView) view.findViewById(R.id.second_page102);
		secondPageFirstLineIcon3 = (ImageView) view.findViewById(R.id.second_page103);
		secondPageSecondLineIcon1 = (ImageView) view.findViewById(R.id.second_page201);
		secondPageSecondLineIcon2 = (ImageView) view.findViewById(R.id.second_page202);
		secondPageSecondLineIcon3 = (ImageView) view.findViewById(R.id.second_page203);
		
		secondPageFirstLineIcon1Layout = (LinearLayout) view.findViewById(R.id.second_page101_layout);
		secondPageFirstLineIcon2Layout = (LinearLayout) view.findViewById(R.id.second_page102_layout);
		secondPageFirstLineIcon3Layout = (LinearLayout) view.findViewById(R.id.second_page103_layout);
		secondPageSecondLineIcon1Layout = (LinearLayout) view.findViewById(R.id.second_page201_layout);
		secondPageSecondLineIcon2Layout = (LinearLayout) view.findViewById(R.id.second_page202_layout);
		secondPageSecondLineIcon3Layout = (LinearLayout) view.findViewById(R.id.second_page203_layout);			
	}

	private void initThirdPageUi(View view){
		thirdPageFirstLineIcon1 = (ImageView) view.findViewById(R.id.third_page101);
		thirdPageFirstLineIcon2 = (ImageView) view.findViewById(R.id.third_page102);
		thirdPageFirstLineIcon3 = (ImageView) view.findViewById(R.id.third_page103);
		thirdPageFirstLineIcon4 = (ImageView) view.findViewById(R.id.third_page104);
		thirdPageSecondLineIcon1 = (ImageView) view.findViewById(R.id.third_page201);
		thirdPageSecondLineIcon2 = (ImageView) view.findViewById(R.id.third_page202);
		thirdPageSecondLineIcon3 = (ImageView) view.findViewById(R.id.third_page203);
		thirdPageSecondLineIcon4 = (ImageView) view.findViewById(R.id.third_page204);

		thirdPageFirstLineIcon1Layout = (LinearLayout) view.findViewById(R.id.third_page101_layout);
		thirdPageFirstLineIcon2Layout = (LinearLayout) view.findViewById(R.id.third_page102_layout);
		thirdPageFirstLineIcon3Layout = (LinearLayout) view.findViewById(R.id.third_page103_layout);
		thirdPageFirstLineIcon4Layout = (LinearLayout) view.findViewById(R.id.third_page104_layout);
		thirdPageSecondLineIcon1Layout = (LinearLayout) view.findViewById(R.id.third_page201_layout);
		thirdPageSecondLineIcon2Layout = (LinearLayout) view.findViewById(R.id.third_page202_layout);
		thirdPageSecondLineIcon3Layout = (LinearLayout) view.findViewById(R.id.third_page203_layout);
		thirdPageSecondLineIcon4Layout = (LinearLayout) view.findViewById(R.id.third_page204_layout);		
	}

	private void initFourthPageUi(View view){
		fourthPageFirstLineIcon1 = (ImageView) view.findViewById(R.id.fourth_page101);
		fourthPageFirstLineIcon2 = (ImageView) view.findViewById(R.id.fourth_page102);
		fourthPageFirstLineIcon1Layout = (LinearLayout) view.findViewById(R.id.fourth_page101_layout);
		fourthPageFirstLineIcon2Layout = (LinearLayout) view.findViewById(R.id.fourth_page102_layout);
	}

	private void startApks(final ImageView imageButton) {
		resumeFromAtvScreen = false;
		//imageButton.setFocusableInTouchMode(true);
		//imageButton.requestFocus();
		String packageName = "";
	
		//main app 
		/*
		if (imageButton == besTVImageView) {
			Intent intent = new Intent();
			intent.setClassName("com.amlogic.bestv", "com.amlogic.bestv.BesTVActivity");
			releaseFirstThenStartApk(intent);
		} else if (imageButton == sinaImageView) {
			packageName = "com.tencent.qqmusicpad";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == myMusicImageView) {
			packageName = "com.example.tf_tv";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == netNearImageView) {		
			packageName = "cn.kuaipan.android.tv";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == chromeImageView) {
			Intent intent = new Intent();
			intent.setClassName("com.amlogic.filebrowser", "com.amlogic.filebrowser.MediaFilebrowser");
			intent.putExtra("launch_what","samba");
			releaseFirstThenStartApk(intent);
		} else if (imageButton == photoFileImageView) {
			packageName = "com.lfzd.sinagallery";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == multiScreenImageView) {
			//this icon special processed in SwitchDemoActivity.java at  Line about 303
		} else if (imageButton == ebookImageView) {
			packageName = "viva.android.tv";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == dreamFlyImageView) {		
			packageName = "com.android.thtf.thtfcookbook";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} else if (imageButton == paopaoleImageView) {
			packageName = "com.huifutianxia";
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			releaseFirstThenStartApk(intent);
		} 
			
		//tvpreview
		else if (imageButton == tvPreView) {
		       resumeFromAtvScreen = true;
			if( !inAtvScreen ){//Stop start AtvScreen twice in a moment
		       	SwitchViewDemoActivity.mTvPreview.startAtvScreen();
				inAtvScreen = true;
			}
		}
		*/
	}

	public void imageButtonClick(final ImageView imageButton) {
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "=============onClick===============");
				startApks(imageButton);
			}
		});
	}

	public void imageButtonFocuschange(final ImageView imageButton,final LinearLayout linearLayout) {
		imageButton.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if( imageButton.getWidth() > basicIconWith){
						linearLayout.setBackgroundResource(R.drawable.item_selected_big);
					}else{
						linearLayout.setBackgroundResource(R.drawable.item_selected_little);					
					}
					/* * Animation
					TranslateAnimation animation = new TranslateAnimation(0, 30, 0, 30);
					animation.setDuration(2000);
					//animation.setFillAfter(true);
					*/
					/*
					imageButton.bringToFront();
					if( imageButton == firstPageFirstLineIcon1 || imageButton == firstPageFirstLineIcon2 || 
							imageButton == firstPageSecondLineIcon3 || imageButton == firstPageSecondLineIcon4 ||
							imageButton == firstPageSecondLineIcon5 || imageButton == firstPageSecondLineIcon6  ){
						imageButton.startAnimation(new ItemSelectedOutYRotationAnimation(imageButton.getWidth() / 2,imageButton.getHeight() / 2,    300,          0,    -35));
					}else if(imageButton == thirdPageFirstLineIcon1 || imageButton == thirdPageSecondLineIcon2 ){
						imageButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.tvpreview_selected));
					}else if( imageButton == secondPageFirstLineIcon1){
						imageButton.startAnimation(new ItemSelectedOutYRotationAnimation(imageButton.getWidth() / 2,imageButton.getHeight() / 2,    300,       360,     35));
					}else if( imageButton == secondPageFirstLineIcon2){
						imageButton.startAnimation(new ItemSelectedOutYRotationAnimation(imageButton.getWidth() / 2,imageButton.getHeight() / 2,    300,      -360,       0));
					}else if( imageButton == secondPageFirstLineIcon3){
						imageButton.startAnimation(new ItemSelectedOutYRotationAnimation(imageButton.getWidth() / 2,imageButton.getHeight() / 2,    300,        720,       0));
					}else if( imageButton == secondPageFirstLineIcon4){
						imageButton.startAnimation(new ItemSelectedOutYRotationAnimation(imageButton.getWidth() / 2,imageButton.getHeight() / 2,    300,      1080,       0));
					}else if( imageButton == secondPageSecondLineIcon5){
						imageButton.startAnimation(new ItemSelectedOutXRotationAnimation(imageButton.getWidth() / 2,imageButton.getHeight() / 2,    300,       360,       0));
					}else if( imageButton == secondPageSecondLineIcon6){
						imageButton.startAnimation(new ItemSelectedOutXRotationAnimation(imageButton.getWidth() / 2,imageButton.getHeight() / 2,    300,      -360,      0));
					}else if( imageButton == secondPageSecondLineIcon7){
						imageButton.startAnimation(new ItemSelectedOutXRotationAnimation(imageButton.getWidth() / 2,imageButton.getHeight() / 2,    300,        720,      0));
					}else if( imageButton == secondPageSecondLineIcon8){
						imageButton.startAnimation(new ItemSelectedOutXRotationAnimation(imageButton.getWidth() / 2,imageButton.getHeight() / 2,    300,       1080,      0));
					}
					*/
				} else if (hasFocus == false) {
					linearLayout.setBackgroundResource(R.drawable.nothing);
					/*
					if( imageButton == firstPageFirstLineIcon1 || imageButton == firstPageFirstLineIcon2 || 
							imageButton == firstPageSecondLineIcon3 || imageButton == firstPageSecondLineIcon4 ||
							imageButton == firstPageSecondLineIcon5 || imageButton == firstPageSecondLineIcon6 ){
						imageButton.startAnimation(new ItemSelectedInYRotationAnimation(imageButton.getWidth() / 2,imageButton.getHeight() / 2,    300,          0,    -35));
					}else if(imageButton == thirdPageFirstLineIcon1 || imageButton == thirdPageSecondLineIcon2 ){
						imageButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.nothing));
					}
					*/
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
		setViewFocus(mCurScreen + 2);
		snapToScreen(mCurScreen + 2);
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
		setViewFocus(mCurScreen - 2);
		snapToScreen(mCurScreen - 2);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG,"______________________3");		
		View focusView = MyScrollLayout.this.findFocus();
		if ( (focusView == firstPageFirstLineIcon1 || focusView == firstPageSecondLineIcon1) && 
				keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			snapToScreen(mCurScreen + 3);	
		} else if( (focusView == firstPageFirstLineIcon3 || focusView == firstPageSecondLineIcon4) && 
				keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			snapToScreen(mCurScreen + 1);	
		} else if( (focusView == secondPageFirstLineIcon1 || focusView == secondPageSecondLineIcon1) && 
				keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			snapToScreen(mCurScreen - 1);
		} else if( (focusView == secondPageFirstLineIcon3 || focusView == secondPageSecondLineIcon3 ) &&
				keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			snapToScreen(mCurScreen + 1);	
		} else if( (focusView == thirdPageFirstLineIcon1 || focusView == thirdPageSecondLineIcon1) &&
				keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
			snapToScreen(mCurScreen - 1);
		} else if((focusView == thirdPageFirstLineIcon4 || focusView == thirdPageSecondLineIcon4) &&
				keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
			snapToScreen(mCurScreen + 1);	
		} else if((focusView == fourthPageFirstLineIcon1) && keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
			snapToScreen(mCurScreen - 1);	
		}

		if (keyCode == 87) {// next music
			if(mCurScreen == 2){
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
			firstPageFirstLineIcon1.requestFocus();
			break;
		case 1:
			secondPageFirstLineIcon1.requestFocus();
			break;
		case 2:			
			thirdPageFirstLineIcon1.requestFocus();
			break;
		case 3:			
			fourthPageFirstLineIcon1.requestFocus();
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
			startActivitySafely(startApkIntent);
			mResourceManager.release();
			SwitchViewDemoActivity.mTvPreview.SetAllbypass("1");
			hasEnterApp=0;
		} 	
	}
}
