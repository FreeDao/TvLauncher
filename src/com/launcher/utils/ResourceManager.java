package com.launcher.utils;

import android.amlogic.Tv;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;

public class ResourceManager implements Tv.ResourceStateCallback{
	private final String TAG = "ResourceManager";
	private Context mContext;
	private Tv mTv = null;
	private ResourceHasReleaseListener mResourceHasReleaseListener = null;
	private int curentResource = 0;
	
	private String requestReleaseOwner = "";
	
	private Intent mIntent;
	
	public ResourceManager(Context context){
		mContext = context;
		mTv = LauncherUtil.openTv();
	}

	public String getResourceOwner(String resource){
		return LauncherUtil.getCurOwnerName(resource);
	}
	
	public void requestResource(String owner_name, String resource){
		String wallpaper_owner = getResourceOwner(LauncherUtil.ResourceWallpaper);
		if(wallpaper_owner==null || wallpaper_owner.isEmpty()){
			if(mResourceHasReleaseListener != null){
				mResourceHasReleaseListener.onHasReleased(requestReleaseOwner, true);
			}
		}else{
			mTv.RequestResources(owner_name, this,  resource, 1);
		}
	}

	public void onStateChanged(String sType, int state){
		Log.d(TAG, "===sType===="+sType+"===state====="+state);
		if(state == LauncherUtil.ResourcesReleased){
			if(LauncherUtil.ResourceAudioDecoder.equals(sType)){
				curentResource |= 2;
			}else if(LauncherUtil.ResourceVideoDecoder.equals(sType)){
				curentResource |= 4;
			}else if(LauncherUtil.ResourceVideoDisplay.equals(sType)){
				curentResource |= 1;
			}else if(LauncherUtil.ResourceWallpaper.equals(sType)){
				curentResource |= 8;
			}else if(LauncherUtil.ResourceTuner.equals(sType)){
				curentResource |= 16;
			}
			Log.d(TAG, "=====curentResource====="+curentResource);
			if((curentResource&31) == 31){
				if(mResourceHasReleaseListener != null){
					mResourceHasReleaseListener.onHasReleased(requestReleaseOwner, true);
				}
				curentResource = 0;
			}
		}
	}
	public void setResourceHasReleaseListener(ResourceHasReleaseListener l){
		mResourceHasReleaseListener = l;
	}
	
	public interface ResourceHasReleaseListener{
		void onHasReleased(String owner, boolean hasReleased);
	}
	
	public void releaseAllResource(String owner){
		requestReleaseOwner = owner;
		requestResource(requestReleaseOwner, LauncherUtil.ResourceVideoDisplay);
		requestResource(requestReleaseOwner, LauncherUtil.ResourceAudioDecoder);
		requestResource(requestReleaseOwner, LauncherUtil.ResourceVideoDecoder);
		requestResource(requestReleaseOwner, LauncherUtil.ResourceTuner);
		requestResource(requestReleaseOwner, LauncherUtil.ResourceWallpaper);
	}
	
	public void release(){
		//SystemProperties.set("hw.videohole.enable", "false");
		mTv.RleasedResource(requestReleaseOwner, LauncherUtil.ResourceAudioDecoder);
		mTv.RleasedResource(requestReleaseOwner, LauncherUtil.ResourceVideoDecoder);
		mTv.RleasedResource(requestReleaseOwner, LauncherUtil.ResourceVideoDisplay);
		mTv.RleasedResource(requestReleaseOwner, LauncherUtil.ResourceTuner);
		mTv.RleasedResource(requestReleaseOwner, LauncherUtil.ResourceWallpaper);
	}
	
	private void startBrowserActivity(){
    Intent mIntent = new Intent("android.intent.action.VIEW");
		mIntent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
		mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(mIntent);
  }
}
