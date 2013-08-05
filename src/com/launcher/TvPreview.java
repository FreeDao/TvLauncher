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


public class TvPreview implements Tv.ResourceStateCallback, Tv.RequestReleaseSourcesListener{
	private Context mContext;
	static Tv tv = null;
	private final String TAG = "TvPreview";
	private static final String Request_Owner = "atv";
	private static final int open_mode=1;
	public static final int close_mode=0;
	private static final String TV_SCREEN_ACTIVITY = "android.intent.action.AtvScreenActivity";
  int res_status = 0;
	int m_x=0;
	int m_y=0;
	int m_w=0;
	int m_h=0;
	int mmm=0;
	int CurIntput=0;
	public TvPreview(Context context){
		mContext = context;
		init();
	}
	
	private void init(){
		//Log.d(TAG, "====init===");
        OpenTv();
        
        tv.SetRequestReleaseSourcesListener(this);
		//StartTvHandler.postDelayed(StartTvRunnable,0);
	}
	public void onStateChanged(String sType, int state) {
		//Log.d(TAG,"sType===="+sType+"!!!!state====="+state);
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
		}
		//Log.d(TAG,"onStateChanged=========res_status"+res_status);
		if (res_status == 0xf) {
			//Log.d(TAG,"onStateChanged====res_status == 0xf");
			res_status = 0;
			SourcePlay();
			DelayHandler.postDelayed(DelayRunnable,500);
			//SetBypassPost("1");
			//SetBypassHD("0");
			//SetAllbypass("1");
			SetDisplayModeTimes=0;
			set3DAndDipostHandler.postDelayed(set3DAndDipostRunnable,500);
			}
			
		//}
	}
	
	public void SetAllbypass(String mode) {
    	try {
			//Log.d(TAG,"Set /sys/module/di/parameters/bypass_all!!!!!!!!!");
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

	private void SetBypassPost(String mode){
		
		try {
			//Log.d(TAG,"Set /sys/module/di/parameters/bypass_post!!!!!!!!!");
    		BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/module/di/parameters/bypass_post"));
    		try {
    			writer.write(mode);
    			} finally {
    				writer.close();
    			}
    	}catch (FileNotFoundException e) {
    		e.printStackTrace();
    		}catch (Exception e) {
    			//Log.e(TAG,"set /sys/module/di/parameters/bypass_post ERROR!",e);
    			}
		}
		
		
		private void SetBypassHD(String mode){
		
		try {
			//Log.d(TAG,"Set /sys/module/di/parameters/bypass_post!!!!!!!!!");
    		BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/module/di/parameters/bypass_hd"));
    		try {
    			writer.write(mode);
    			} finally {
    				writer.close();
    			}
    	}catch (FileNotFoundException e) {
    		e.printStackTrace();
    		}catch (Exception e) {
    			//Log.e(TAG,"set /sys/module/di/parameters/bypass_hd ERROR!",e);
    			}
		}
    
  public void SetWindowSize(int mode ,int x , int y , int w , int h) {
    	//Log.d(TAG,"SetWindowSize========================"+mode+"x="+x+"y="+y+"w="+w+"h="+h);
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
    //	SystemProperties.set("hw.videohole.enable", "true");
    //	SystemProperties.set("hw.videohole.layer", "2");
    //    SystemProperties.set("hw.videohole.x", String.valueOf(x));
    //    SystemProperties.set("hw.videohole.y", String.valueOf(y));
    //    SystemProperties.set("hw.videohole.width", String.valueOf(w));
    //    SystemProperties.set("hw.videohole.height", String.valueOf(h));
    }
    
  public void SetVideoSize(int x , int y , int w , int h) {
    	String size="";
		w=x+w;
		h=y+h;
    	size=size + x + " " + y + " " + w + " " + h;
    	//Log.d(TAG,"SetVideoSize========================"+size);
    	try {
    		BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/class/video/axis"));
    		try {
    			writer.write(size);
				Log.d(TAG,"SetVideoSize========================"+size);
    			} finally {
    				writer.close();
    			}
    	}catch (FileNotFoundException e) {
    		e.printStackTrace();
    		}catch (Exception e) {
    			//Log.e(TAG,"set  /sys/class/video/axis ERROR!",e);
    			} 
    	//SystemProperties.set("tv.globalsetup_show_status", "false");
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
			//Log.d(TAG, "ReadVideoPlayState: " + "/sys/class/video/axis ok");
		} catch (IOException e) {
		//Log.e(TAG, "ReadVideoPlayState: " + "/sys/class/video/axis", e);
		}
		//Log.d(TAG,"/sys/class/video/axis=="+i);
		return i;
  	}
  private int ReadBypassPostStatus(){
  	int i=0;
	try {
		BufferedReader reader = new BufferedReader(new FileReader("/sys/module/di/parameters/bypass_post"));
		try {
			i = reader.read();
			} finally {
			reader.close();
			} 
			Log.d(TAG, "ReadVideoPlayState: " + "/sys/module/di/parameters/bypass_post ok");
		} catch (IOException e) {
		Log.e(TAG, "ReadVideoPlayState: " + "/sys/module/di/parameters/bypass_post", e);
		}
		Log.d(TAG,"/sys/module/di/parameters/bypass_post=="+i);
		return i;
  	}
  public int ReadBypassAllStatus(){
  	int i=0;
	try {
		BufferedReader reader = new BufferedReader(new FileReader("/sys/module/di/parameters/bypass_all"));
		try {
			i = reader.read();
			} finally {
			reader.close();
			} 
			Log.d(TAG, "ReadVideoPlayState: " + "/sys/module/di/parameters/bypass_all ok");
		} catch (IOException e) {
		Log.e(TAG, "ReadVideoPlayState: " + "/sys/module/di/parameters/bypass_all", e);
		}
		Log.d(TAG,"/sys/module/di/parameters/bypass_all=="+i);
		return i;
  	}


  public void startTV(){
	 //Log.d(TAG,"========================startTV");
    	 SetVideoSize(0,0,1920,1080);
    	 SetWindowSize(close_mode , 0 , 0 , 0 , 0);
	 SystemProperties.set("tv.atv_source_input",""+tv.SSMReadLastSelectSourceType());
    	 Intent temp_intent = new Intent(TV_SCREEN_ACTIVITY);
	 temp_intent.removeExtra("source");
	 temp_intent.putExtra("source", tv.GetCurrentSourceInput());
	 temp_intent.addCategory(Intent.CATEGORY_DEFAULT);
	 temp_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 mContext.startActivity(temp_intent);		
   }


   public void StartTVPreview(int x , int y , int w , int h) {
   	//if(tv.GetDisplayMode(tv.GetSrcInputType())!=0){
   		tv.SetDisplayMode(Tv.Dis_Mode.DISPLAY_MODE_169,Tv.Source_Input_Type.SOURCE_TYPE_MPEG, tv.GetCurrentSignalInfo().fmt);
   	//}
	/*if(tv.Get3DMode()!=0){
		tv.Set3DMode(Tv.Mode_3D.MODE_3D_CLOSE,Tv.Tvin_3d_Status.values()[tv.Get3DMode()]);
		}*/
   	//set2DStatus();
   	    String str = tv.QueryResourceState("tuner").owner_name;
		//Log.d(TAG,"StartTVPreview==================owner_name======"+str);
		mmm=0;
		m_x=x;
		m_y=y;
		m_w=w;
		m_h=h;
		if ((tv.QueryResourceState("wallpaper").owner_name).equals(Request_Owner)) {
			//SetBypassPost("1");
			//SetBypassHD("0");
			
			set3DAndDipostHandler.postDelayed(set3DAndDipostRunnable,0);
			SetDisplayModeTimes=0;
			SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,0);
			DelayHandler.postDelayed(DelayRunnable,500);
			//SetAllbypass("1");
			
		}
		else {
			
			tv.RequestResources(Request_Owner, this, "tuner", 1);
			tv.RequestResources(Request_Owner, this, "videodisplay", 1);
			tv.RequestResources(Request_Owner, this, "audiodecoder", 1);
			tv.RequestResources(Request_Owner, this, "wallpaper", 1);
			SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,0);
			
		    //SetWindowSize(open_mode , m_x , m_y , m_w , m_h);	
		    //SetBypassPost("1");
			
		}		
		
	}
	public void startAtvScreen(){
		//Log.d(TAG,"startAtvScreen========================");
		DelayHandler.removeCallbacks(DelayRunnable);
		SetVideoSizeHandler.removeCallbacks(SetVideoSizeRunnable);
		set3DAndDipostHandler.removeCallbacks(set3DAndDipostRunnable);
		mySetDisplayMode();
	    //SetVideoSize(0,0,1920,1080);
	    SetWindowSize(close_mode , 0 , 0 , 0 , 0);
		SystemProperties.set("tv.atv_source_input",""+tv.GetCurrentSourceInput());
	    Intent temp_intent = new Intent(TV_SCREEN_ACTIVITY);
			temp_intent.removeExtra("source");
			temp_intent.putExtra("source", tv.GetCurrentSourceInput());
			temp_intent.addCategory(Intent.CATEGORY_DEFAULT);
			temp_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(temp_intent);
		
	}
	public void mySetDisplayMode(){
		int mode = tv.GetDisplayMode(tv.GetSrcInputType());
		tv.SetDisplayMode(Tv.Dis_Mode.values()[mode]  ,tv.GetSrcInputType(), tv.GetCurrentSignalInfo().fmt);
		}
	private void SourcePlay(){
		//Log.d(TAG,"tv.GetCurrentSourceInput()======="+tv.SSMReadLastSelectSourceType());
		set_sourceInput(tv.SSMReadLastSelectSourceType());
		//tv.StartTv(tvin_status_t.TVIN_STATUS_NORMAL_START.toInt());
		
	}
	private void set_sourceInput(int input) {
		//Log.d(TAG,"set_sourceInput======="+input);
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
        } else {
            tv.SetSourceInput(Tv.SrcInput.TV);
        }
    }
    
  public void StopTvPreview() {
  	    Log.d(TAG,"StopTvPreview===============");
  		//StopTv();
  		DelayHandler.removeCallbacks(DelayRunnable);
		SetVideoSizeHandler.removeCallbacks(SetVideoSizeRunnable);
		set3DAndDipostHandler.removeCallbacks(set3DAndDipostRunnable);
		mySetDisplayMode();
		//SetVideoSize(0 , 0 , 1920 , 1080);
		SetWindowSize(close_mode , 0 , 0 , 0 , 0);
		//SetBypassPost("0");
		//SetBypassHD("1");
	}
	
	public void DisableWindow() {
		SetDisplayModeTimes=0;
		DelayHandler.removeCallbacks(DelayRunnable);
		SetVideoSizeHandler.removeCallbacks(SetVideoSizeRunnable);
		set3DAndDipostHandler.removeCallbacks(set3DAndDipostRunnable);
		CurIntput = tv.GetCurrentSourceInput();
		SetWindowSize(close_mode , 0 , 0 , 0 , 0);
		SetVideoSize(0 , 0 , 1920 , 1080);
		tv.SetSourceInput(Tv.SrcInput.MPEG);
	}
	public void ShowPerview(){
		SetDisplayModeTimes=0;
		set_sourceInput(tv.SSMReadLastSelectSourceType());
		tv.SetDisplayMode(Tv.Dis_Mode.DISPLAY_MODE_169,Tv.Source_Input_Type.SOURCE_TYPE_MPEG, tv.GetCurrentSignalInfo().fmt);
		SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,500);
		DelayHandler.postDelayed(DelayRunnable,200);
		set3DAndDipostHandler.postDelayed(set3DAndDipostRunnable,500);
		}
	private void OpenTv() {
        if (tv == null) {
            tv = Tv.open();
            //Log.d(TAG, "new Tv()");
        }
    }
	private void TvPerStartTv(){
		int curInput=0;
		if(tv != null){
			//Log.d(TAG,"TvPerStartTv======================");
			if(GetTvStatus() != Tv.tvin_status_t.TVIN_STATUS_NORMAL_START.toInt())
			  tv.StartTv(tvin_status_t.TVIN_STATUS_NORMAL_START.toInt());
			curInput = tv.SSMReadLastSelectSourceType();
//			if(curInput<0&&curInput>9)
//				curInput=0;
			}
		  //Log.d(TAG,"SSMReadLastSelectSourceType============================"+curInput);
		  set_sourceInput(curInput);
		}
	public void StopTv(){
		if(tv != null){	
			//Log.d(TAG,"StopTv===============");
			int m = tv.SSMReadLastSelectSourceType();
			//Log.d(TAG,"StopTv===============SSMReadLastSelectSourceType="+m);
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
			if(HandlerTimes<=8){
				HandlerTimes++;
				if(tv.Get3DMode()!=0){
					tv.Set3DMode(Tv.Mode_3D.MODE_3D_CLOSE,Tv.Tvin_3d_Status.values()[tv.Get3DMode()]);
					}
				if(ReadBypassPostStatus()!=49){
					SetBypassPost("1");
					Log.d(TAG,"!!!!!!!!!!!!ReadBypassPostStatus()==49");
					}
				set3DAndDipostHandler.removeCallbacks(set3DAndDipostRunnable);
				set3DAndDipostHandler.postDelayed(set3DAndDipostRunnable,3000);
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
			//Log.d(TAG,"SetVideoSizeHandler===GetTvStatus()="+tv.GetTvStatus());
			//Log.d(TAG,"SetVideoSizeHandler===GetCurrentSourceInput()="+tv.GetCurrentSourceInput());
			if(tv.GetDisplayMode(tv.GetSrcInputType())!=0 && SetDisplayModeTimes<10){
				SetDisplayModeTimes++;
				tv.SetDisplayMode(Tv.Dis_Mode.DISPLAY_MODE_169,Tv.Source_Input_Type.SOURCE_TYPE_MPEG, tv.GetCurrentSignalInfo().fmt);
				}
			int i=48;
			if(tv.GetTvStatus()==Tv.tvin_status_t.TVIN_STATUS_NORMAL_START.toInt()
				&&i==ReadVideoSize()){
				SetVideoSize(m_x , m_y , m_w , m_h);
				//Log.d(TAG,"SetVideoSizeHandler===========================");
				SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,500);
				}
			else
				SetVideoSizeHandler.postDelayed(SetVideoSizeRunnable,500);
				}
	  		};
	  private Handler DelayHandler = new Handler();
	  private Runnable DelayRunnable =new Runnable(){
	  	public void run(){
			SetWindowSize(open_mode , m_x , m_y , m_w , m_h);
			//Log.d(TAG,"DelayHandler=======");
	  		}
	  	};
	  void RleasedResource() {
	  	String str = tv.QueryResourceState("tuner").owner_name;
		  //Log.d(TAG,"RleasedResource==================owner_name======"+str);
		  //Log.d(TAG, "====timer===start==");
		  if (tv.GetTvStatus() == tvin_status_t.TVIN_STATUS_IDLE.toInt()) {
		  	tv.RleasedResource(Request_Owner,"tuner");
		  	tv.RleasedResource(Request_Owner,"videodisplay");
		  	tv.RleasedResource(Request_Owner,"audiodecoder");
		  	tv.RleasedResource(Request_Owner,"wallpaper");
		  	//SetBypassPost("1");
		  	StopTvRleasedResourceHandler.removeCallbacks(StopTvRleasedResourceRunnable);
		  	//Log.d(TAG,"RleasedResource==================owner_name!!!!!"+str);
		  	}
	  }
	  public void onRequestRelease(String sType){
		  //Log.d(TAG, "====onRequestRelease===sType===="+sType);
			  StopTv();
			  //Log.d(TAG, "====RleasedResource====");
	  }
	  public void release(){
	  	tv.RleasedResource(Request_Owner,"tuner");
		  tv.RleasedResource(Request_Owner,"videodisplay");
		  tv.RleasedResource(Request_Owner,"audiodecoder");
		  tv.RleasedResource(Request_Owner,"wallpaper");
	  }
	  public void set2DStatus(){
	  	//SetRegBit("wcb 0x2018 1 0");
	  	//SetRegBit("wcb 0x2019 1 1");

  }
  public void SetRegBit(String cmd) {
  	try {
		Log.d(TAG,"SetRegBit..........."+cmd);
  		FileOutputStream ff_cbus = new FileOutputStream("/sys/class/register/bit");        
  		ff_cbus.write(cmd.getBytes());   
  		} catch (FileNotFoundException e) {     
  			e.printStackTrace();   
  			} catch (Exception e) {     
  				e.printStackTrace();   
  				}
  }
}
