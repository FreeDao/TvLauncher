package com.launcher;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.amlogic.Tv;
import android.amlogic.Tv.*;
//import com.amlogic.tvutil.TVConst;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.util.Log;
import android.view.Display;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.VideoView;
import android.content.Intent;


public class TvPreview implements Tv.ResourceStateCallback, Tv.RequestReleaseSourcesListener{
	private Context mContext;
	static Tv tv = null;
	tvin_info_t tv_info=null;
	private final String TAG = "TvPreview";
	private boolean DEBUG = true;
	private static final String Request_Owner = "atv";
	private static final String Request_OwnerDtv = "dtv";
	private static final int open_mode=1;
	public static final int close_mode=0;
	private static final String TV_SCREEN_ACTIVITY = "android.intent.action.AtvScreenActivity";
	private static final String START_PLAY_DTV = "com.launcher.play.dtv";
	private static final String START_STOP_DTV = "com.launcher.stop.dtv";
  int res_status = 0;
	private int m_x=0;
	private int m_y=0;
	private int m_w=0;
	private int m_h=0;
	int mmm=0;
	int CurIntput=0;
	public VideoView firstPageFirstLineIcon1;
	public TvPreview(Context context){
		mContext = context;
		init();
	}
	private SwitchViewDemoActivity mSwitchViewDemoActivity;
	
	
	private void init(){
		//if(DEBUG) Log.d(TAG, "====init===");
        OpenTv();
        
        tv.SetRequestReleaseSourcesListener(this);
        mSwitchViewDemoActivity = new SwitchViewDemoActivity();
		//StartTvHandler.postDelayed(StartTvRunnable,0);
	}
	public void onStateChanged(String sType, int state) {
		if(DEBUG) Log.d(TAG,"sType===="+sType+"!!!!state====="+state);
		if (sType.equals("tuner")) {
			if (state == 1) {
				res_status |= 1;
			}
		} else if (sType.equals("videodisplay")) {
			if (state == 1) {
				res_status |= 2;
			}
		} else if (sType.equals("audiodecoder")) {
			if (state == 1) {
				res_status |= 4;
			}
		} else if (sType.equals("wallpaper")) {
			if (state == 1) {
				res_status |= 8;
			}
		} else if (sType.equals("videodecoder")) {
				  if (state == 1) {
				  	res_status |= 0x10;
				  }
		}
		if (res_status == 0x1f) {
			if(DEBUG) Log.d(TAG,"onStateChanged====res_status == 0x1f");
			res_status = 0;
			SourcePlay();
			SetDisplayModeTimes=0;
			set3DAndDipostHandler.postDelayed(set3DAndDipostRunnable,500);
			}
			
		//}
	}
	
	public void SetAllbypass(String mode) {
    	try {
			//if(DEBUG) Log.d(TAG,"Set /sys/module/di/parameters/bypass_all!!!!!!!!!");
    		BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/module/di/parameters/bypass_all"));
    		try {
    			writer.write(mode);
    			} finally {
    				writer.close();
    			}
    	}catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}catch (Exception e) {
    		//Log.e(TAG,"set /sys/module/di/parameters/bypass_all ERROR!",e);
    	}
  }
  
  public void setDiVscaleSkipEnable(String s){
  	try {
  		if(DEBUG) Log.d(TAG,"Set /sys/module/di/parameters/di_vscale_skip_enable=="+s);
  		BufferedWriter writer = new BufferedWriter(new FileWriter(
    		        "/sys/module/di/parameters/di_vscale_skip_enable"));
    	try {
    		writer.write(s);
    	} finally {
    		writer.close();
    	}
    }catch (FileNotFoundException e) {
    	e.printStackTrace();
    }catch (Exception e) {
    	Log.e(TAG,"set /sys/module/di/parameters/di_vscale_skip_enable ERROR!",e);
    }
	}

  public void SetWindowSize(int mode ,int x , int y , int w , int h) {
    	//if(DEBUG) Log.d(TAG,"SetWindowSize========================"+mode+"x="+x+"y="+y+"w="+w+"h="+h);
    	//w=w+x;
    	//h=h+y;
    	String hole=null;
    	if(mode==1)
    	   hole = ""+x+" "+y+" "+w+" "+h+" "+mode;
    	else
    	   hole = ""+x+" "+y+" "+w+" "+h+" "+mode;
    	try {
    		BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/class/graphics/fb0/video_hole"));
    		try {
    			writer.write(hole);
    			} finally {
    				writer.close();
    			}
    	}catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}catch (Exception e) {
    			Log.e(TAG,"set  /sys/class/graphics/fb0/video_hole ERROR!",e);
    	}
  }

    
  public void SetVideoSize(int x , int y , int w , int h) {
    String size="";
		w = x + w;
		h = y + h;
		size=size + x + " " + y + " " + w + " " + h;
		if(DEBUG) Log.d(TAG,"SetVideoSize========================"+size);
		try {
    		BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/class/video/axis"));
    		try {
    			writer.write(size);
    			} finally {
    				writer.close();
    			}
    	}catch (FileNotFoundException e) {
    		e.printStackTrace();
    		}catch (Exception e) {
    			Log.e(TAG,"set  /sys/class/video/axis ERROR!",e);
    			}
  }
  
  private int ReadVideoSize(){
  	int i=0;
  	try {
		BufferedReader reader = new BufferedReader(new FileReader("/sys/class/video/axis"));
		try {
			i = reader.read();
			} finally {
			reader.close();
			} 
			//if(DEBUG) Log.d(TAG, "ReadVideoPlayState: " + "/sys/class/video/axis ok");
		} catch (IOException e) {
			Log.e(TAG, "ReadVideoPlayState: " + "/sys/class/video/axis", e);
		}
		//if(DEBUG) Log.d(TAG,"/sys/class/video/axis=="+i);
		return i;
	}
	
	private static int mode = 0;
  public void startTvPreview (VideoView videoView,int offSet) {
  	setDiVscaleSkipEnable("3");
  	getVideoViewSize(videoView,offSet);
  	mode = tv.GetDisplayMode(tv.GetSrcInputType());
   	tv.SetDisplayMode(Tv.Dis_Mode.DISPLAY_MODE_169,Tv.Source_Input_Type.SOURCE_TYPE_MPEG, tv.GetCurrentSignalInfo().fmt);
   	String str = tv.QueryResourceState("tuner").owner_name;
		if(DEBUG) Log.d(TAG,"StartTVPreview==================owner_name======"+str);
		if ((tv.QueryResourceState("wallpaper").owner_name).equals(Request_Owner)
		   ||(tv.QueryResourceState("wallpaper").owner_name).equals(Request_OwnerDtv)) {
			set3DAndDipostHandler.postDelayed(set3DAndDipostRunnable,0);
			SetDisplayModeTimes=0;
			SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,1000);			
		} else if(tv.SSMReadLastSelectSourceType()== Tv.SrcInput.DTV.toInt()){
		    if(DEBUG) Log.d(TAG,"StartTVPreview....DTV");
		    tv.RequestResources(Request_OwnerDtv, this, "tuner", 1);
		    tv.RequestResources(Request_OwnerDtv, this, "videodisplay", 1);
		    tv.RequestResources(Request_OwnerDtv, this, "audiodecoder", 1);
		    tv.RequestResources(Request_OwnerDtv, this, "wallpaper", 1);
		    tv.RequestResources(Request_OwnerDtv, this, "videodecoder", 1);
		    SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,0);
    } else {
			tv.RequestResources(Request_Owner, this, "tuner", 1);
			tv.RequestResources(Request_Owner, this, "videodisplay", 1);
			tv.RequestResources(Request_Owner, this, "audiodecoder", 1);
			tv.RequestResources(Request_Owner, this, "wallpaper", 1);
			tv.RequestResources(Request_Owner, this, "videodecoder", 1);
			SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,0);
		}
	}
	
	public void startAtvScreen(){
		SetVideoSizeHandler.removeCallbacks(SetVideoSizeRunnable);
		set3DAndDipostHandler.removeCallbacks(set3DAndDipostRunnable);		
		SetVideoSize(0 , 0 , 1919 , 1079);
		mySetDisplayMode();
		//SetWindowSize(close_mode , 0 , 0 , 0 , 0);
		if(tv.GetCurrentSourceInput()==Tv.SrcInput.DTV.toInt()){
			if(DEBUG) Log.d(TAG,"start DTV.........");
			Intent intent = new Intent("android.intent.action.TvScreen2Activity");
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);
		} else {
			if(DEBUG) Log.d(TAG,"start ATV.........");
			SystemProperties.set("tv.atv_source_input",""+tv.GetCurrentSourceInput());
			Intent temp_intent = new Intent(TV_SCREEN_ACTIVITY);
			temp_intent.removeExtra("source");
			temp_intent.putExtra("source", tv.GetCurrentSourceInput());
			temp_intent.addCategory(Intent.CATEGORY_DEFAULT);
			temp_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(temp_intent);
	  }
	}
	
	public void mySetDisplayMode(){
		
		tv.SetDisplayMode(Tv.Dis_Mode.values()[mode]  ,tv.GetSrcInputType(), tv.GetCurrentSignalInfo().fmt);
	}
	
	private void SourcePlay(){
		if(DEBUG) Log.d(TAG,"tv.GetCurrentSourceInput()======="+tv.SSMReadLastSelectSourceType());
		set_sourceInput(tv.SSMReadLastSelectSourceType());
		//tv.StartTv(tvin_status_t.TVIN_STATUS_NORMAL_START.toInt());
		
	}
	private void set_sourceInput(int input) {
		if(DEBUG) Log.d(TAG,"set_sourceInput======="+input);
	  int tvNumber = 1;
	  if (input == Tv.SrcInput.TV.toInt()) {
        // tv
        tv.SetSourceInput(Tv.SrcInput.TV);
    } else if (input == Tv.SrcInput.AV1.toInt()) {
        // av 1
        tv.SetSourceInput(Tv.SrcInput.AV1);
    } else if (input == Tv.SrcInput.AV2.toInt()) {
        // av 2
        tv.SetSourceInput(Tv.SrcInput.AV2);
    } else if (input == Tv.SrcInput.YPBPR1.toInt()) {
        // YPBPR
        tv.SetSourceInput(Tv.SrcInput.YPBPR1);
    } else if (input == Tv.SrcInput.HDMI1.toInt()) {
        // HDMI1
        tv.SetSourceInput(Tv.SrcInput.HDMI1);
    } else if (input == Tv.SrcInput.HDMI2.toInt()) {
        // HDMI2
        tv.SetSourceInput(Tv.SrcInput.HDMI2);
    } else if (input == Tv.SrcInput.HDMI3.toInt()) {
        // HDMI3
        tv.SetSourceInput(Tv.SrcInput.HDMI3);
    } else if (input == Tv.SrcInput.VGA.toInt()) {
        // VGA0
        tv.SetSourceInput(Tv.SrcInput.VGA);
    }else if (input == Tv.SrcInput.MPEG.toInt()){
		    tv.SetSourceInput(Tv.SrcInput.MPEG);
		}else if (input == Tv.SrcInput.DTV.toInt()) {
			  //DTV
			  tv.SetSourceInput(Tv.SrcInput.DTV);
    } else {
        tv.SetSourceInput(Tv.SrcInput.TV);
    }
  }
    
  public void StopTvPreview() {
  	if(DEBUG) Log.d(TAG,"StopTvPreview===============");
		SetVideoSizeHandler.removeCallbacks(SetVideoSizeRunnable);
		set3DAndDipostHandler.removeCallbacks(set3DAndDipostRunnable);
		//showTvPreviewHandler.removeCallbacks(showTvPreviewRunnable);
		setScreenHandler.postDelayed(setScreenRunnable,1000);
		
		//SetWindowSize(close_mode , 0 , 0 , 0 , 0);
	}
	
	public void DisablePerview() {
		if(DEBUG) Log.d(TAG,"DisablePerview===============");
		SetDisplayModeTimes=0;
		//showTvPreviewHandler.removeCallbacks(showTvPreviewRunnable);
		SetVideoSizeHandler.removeCallbacks(SetVideoSizeRunnable);
		set3DAndDipostHandler.removeCallbacks(set3DAndDipostRunnable);
		CurIntput = tv.GetCurrentSourceInput();
		//SetWindowSize(close_mode , 0 , 0 , 0 , 0);
		SetVideoSize(0 , 0 , 1919 , 1079);
		tv.SetSourceInput(Tv.SrcInput.MPEG);
	}
	
	public void ShowPerview(){
		if(DEBUG) Log.d(TAG,"ShowPerview===============");
		SetDisplayModeTimes=0;
		set_sourceInput(tv.SSMReadLastSelectSourceType());		
		//tv.SetDisplayMode(Tv.Dis_Mode.DISPLAY_MODE_169,Tv.Source_Input_Type.SOURCE_TYPE_MPEG, tv.GetCurrentSignalInfo().fmt);
		//showTvPreviewHandler.postDelayed(showTvPreviewRunnable,200);
		//set3DAndDipostHandler.postDelayed(set3DAndDipostRunnable,0);
		SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,1000);
	}
	
	private void OpenTv() {
        if (tv == null) {
            tv = Tv.open();
            //if(DEBUG) Log.d(TAG, "new Tv()");
        }
    }

	public void StopTv(){
		if(tv != null){	
			if(DEBUG) Log.d(TAG,"StopTv===============");
			int m = tv.SSMReadLastSelectSourceType();
			set_sourceInput(Tv.SrcInput.MPEG.toInt());
			StopTvRleasedResourceHandler.postDelayed(StopTvRleasedResourceRunnable,0);			
			}
		}
	private int GetTvStatus() {
        if (tv != null) {
            int ret = tv.GetTvStatus();
            return ret;
        }
        return -1;
  }
  private boolean status_3D_Auto = false;
  //private Handler showTvPreviewHandler = new Handler();
	//private Runnable showTvPreviewRunnable = new Runnable(){
	//	public void run(){
	//		if(tv.Get3DMode()==1){
	//			status_3D_Auto = true;
	//			if(DEBUG) Log.d(TAG,"tv.Get3DMode()==1...");
	//			tv.Set3DTo2DMode(Tv.Mode_3D_2D.MODE_3D_2D_LEFT,Tv.Tvin_3d_Status.values()[tv.Get3DMode()]);
	//			SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,200);
	//			showTvPreviewHandler.removeCallbacks(showTvPreviewRunnable);
	//		}else{
	//			SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,200);
	//			showTvPreviewHandler.removeCallbacks(showTvPreviewRunnable);
	//			showTvPreviewHandler.postDelayed(showTvPreviewRunnable,200);
	//		}
	//  }
	//};


    private Handler setScreenHandler = new Handler();
	private Runnable setScreenRunnable = new Runnable(){
	  	public void run(){
			SetVideoSize(0 , 0 , 1919 , 1079);
			mySetDisplayMode();
			if(status_3D_Auto)
				tv.Set3DMode(Tv.Mode_3D.MODE_3D_AUTO,Tv.Tvin_3d_Status.values()[tv.Get3DMode()]);
	  	}
	};

	private Handler StopTvRleasedResourceHandler = new Handler();
	private Runnable StopTvRleasedResourceRunnable = new Runnable(){
	  	public void run(){
			if(GetTvStatus()==Tv.tvin_status_t.TVIN_STATUS_IDLE.toInt()){
				
				RleasedResource();
				}
			else{
				StopTvRleasedResourceHandler.removeCallbacks(StopTvRleasedResourceRunnable);
				StopTvRleasedResourceHandler.postDelayed(StopTvRleasedResourceRunnable,800);
				}
	  		}
	};
	  
  int HandlerTimes=0;
	private Handler set3DAndDipostHandler = new Handler();
	private Runnable set3DAndDipostRunnable = new Runnable(){
	  	public void run(){
			if(HandlerTimes<=11){
				HandlerTimes++;
				if(tv.Get3DMode()==1){
					//status_3D_Auto = true;
					//tv.Set3DTo2DMode(Tv.Mode_3D_2D.MODE_3D_2D_LEFT,Tv.Tvin_3d_Status.values()[tv.Get3DMode()]);
				}else if(tv.Get3DMode()!=0){
					tv.Set3DMode(Tv.Mode_3D.MODE_3D_CLOSE,Tv.Tvin_3d_Status.values()[tv.Get3DMode()]);
				}
				set3DAndDipostHandler.removeCallbacks(set3DAndDipostRunnable);
				set3DAndDipostHandler.postDelayed(set3DAndDipostRunnable,500);
				}
			else{
				set3DAndDipostHandler.removeCallbacks(set3DAndDipostRunnable);
				HandlerTimes=0;
				}
	  		}
	};

	int SetDisplayModeTimes=0;

	private Handler SetVideoSizeHandler = new Handler();
	private Runnable SetVideoSizeRunnable =new Runnable(){
		public void run(){
			getVideoViewSize(SwitchViewDemoActivity.firstPageFirstLineIcon1,0);
			if(tv.Get3DMode()==1){
				status_3D_Auto = true;
				tv.Set3DTo2DMode(Tv.Mode_3D_2D.MODE_3D_2D_LEFT,Tv.Tvin_3d_Status.values()[tv.Get3DMode()]);
			}
			if(tv.GetDisplayMode(tv.GetSrcInputType())!=0 && SetDisplayModeTimes<10){
				SetDisplayModeTimes++;
				tv.SetDisplayMode(Tv.Dis_Mode.DISPLAY_MODE_169,Tv.Source_Input_Type.SOURCE_TYPE_MPEG, tv.GetCurrentSignalInfo().fmt);
			}
			int i=48;
			if (tv.GetTvStatus() == Tv.tvin_status_t.TVIN_STATUS_NORMAL_START.toInt()
			  &&i == ReadVideoSize()){
				SetVideoSize(m_x , m_y , m_w , m_h);
				SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,500);
			} else if(tv.GetCurrentSourceInput()==Tv.SrcInput.DTV.toInt()){
				SetVideoSize(m_x , m_y , m_w , m_h);
				SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,500);
			} else {
				SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,500);
			}
		}
	};
	  
	void RleasedResource() {
		String str = tv.QueryResourceState("tuner").owner_name;
		if(tv.SSMReadLastSelectSourceType()== Tv.SrcInput.DTV.toInt()){
		  	tv.RleasedResource(Request_OwnerDtv,"tuner");
		  	tv.RleasedResource(Request_OwnerDtv,"videodisplay");
		  	tv.RleasedResource(Request_OwnerDtv,"audiodecoder");
		  	tv.RleasedResource(Request_OwnerDtv,"wallpaper");
		  	tv.RleasedResource(Request_OwnerDtv, "videodecoder");
		  	StopTvRleasedResourceHandler.removeCallbacks(StopTvRleasedResourceRunnable);
		  } else {
		  	tv.RleasedResource(Request_Owner,"tuner");
		  	tv.RleasedResource(Request_Owner,"videodisplay");
		  	tv.RleasedResource(Request_Owner,"audiodecoder");
		  	tv.RleasedResource(Request_Owner,"wallpaper");
		  	tv.RleasedResource(Request_Owner, "videodecoder");
		  	StopTvRleasedResourceHandler.removeCallbacks(StopTvRleasedResourceRunnable);
		  	//if(DEBUG) Log.d(TAG,"RleasedResource==================owner_name!!!!!"+str);
		  }
	}
	
	public void onRequestRelease(String sType){
		if(DEBUG) Log.d(TAG, "====onRequestRelease===sType===="+sType);
          //////////////////////////////////////////////////////////////
          if(tv.SSMReadLastSelectSourceType()== Tv.SrcInput.DTV.toInt()){
          	tv.RleasedResource(Request_OwnerDtv,sType);
          	if(sType.equals("tuner")){
          		Intent intent = new Intent();
              intent.setAction(START_STOP_DTV);
              mContext.sendBroadcast(intent);
              set_sourceInput(Tv.SrcInput.MPEG.toInt());
            }
          }
          else{
          	if(sType.equals("tuner")){
          		StopTv();
            }
          }
	}
	
	public void release(){
	  	String str = tv.QueryResourceState("tuner").owner_name;
        if (tv.SSMReadLastSelectSourceType()== Tv.SrcInput.DTV.toInt()) {
        	tv.RleasedResource(Request_OwnerDtv,"tuner");
        	tv.RleasedResource(Request_OwnerDtv,"videodisplay");
        	tv.RleasedResource(Request_OwnerDtv,"audiodecoder");
        	tv.RleasedResource(Request_OwnerDtv,"wallpaper");
        	tv.RleasedResource(Request_OwnerDtv, "videodecoder");
        } else {
		  	  tv.RleasedResource(Request_Owner,"tuner");
        	tv.RleasedResource(Request_Owner,"videodisplay");
        	tv.RleasedResource(Request_Owner,"audiodecoder");
        	tv.RleasedResource(Request_Owner,"wallpaper");
        	tv.RleasedResource(Request_Owner, "videodecoder");
        }
	}
	
	public void SetRegBit(String cmd) {
  	try {
		if(DEBUG) Log.d(TAG,"SetRegBit..........."+cmd);
  		FileOutputStream ff_cbus = new FileOutputStream("/sys/class/register/bit");        
  		ff_cbus.write(cmd.getBytes());   
  		} catch (FileNotFoundException e) {     
  			e.printStackTrace();   
  			} catch (Exception e) {     
  				e.printStackTrace();   
  				}
  }
  
  public void getVideoViewSize(VideoView videoView,int offset){
		int[] location = new int[2];
		videoView.getLocationOnScreen(location);
		m_x = location[0] + offset;
		m_y = location[1];
		m_w = (int)videoView.getWidth();
		m_h = (int)videoView.getHeight();
		if(DEBUG) Log.d(TAG,"size=="+m_x+" "+m_y+" "+m_w+" "+m_h);
	}
	
}
