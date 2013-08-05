package com.launcher.utils;

import com.launcher.SwitchViewDemoActivity;

import android.amlogic.Tv;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.nfc.tech.IsoDep;
import android.util.Log;

public class LauncherUtil {
	private static Tv mTv = null;
	
	public static String OWNER_ATV = "atv";
	public static String OWNER_DTV = "dtv";
	public static String OWNER_VIDEO = "video_player";
	public static String OWNER_MUSIC = "music_player";
	public static String OWNER_PIC = "picture_player";
	
	public static String ResourceVideoDisplay = "videodisplay"; //0x01
	public static String ResourceAudioDecoder = "audiodecoder"; //0x010
	public static String ResourceVideoDecoder = "videodecoder"; //0x0100
	public static String ResourceWallpaper = "wallpaper"; //0x01000
	public static String ResourceTuner = "tuner"; //0x010000
	
	public static int ResourcesReleased = 1;
	public static int ResourcesUsing = 2;
	public static int ResourcesReleasing = 3;
	
	public static Tv openTv(){
		if(mTv == null){
			mTv = Tv.open();
		}
		return mTv;
	}
	
	public static String getCurOwnerName(String rType){
		return mTv.QueryResourceState(rType).owner_name;
	}
	
	public static void tvRelease(){
		mTv.release();
	}
	
	/*
	 * get round corner bitmap
	 * 
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output); 
        Log.d("LauncherUtil"," --width = "+bitmap.getWidth()+" --height = "+bitmap.getHeight());
        
        final int color = Color.RED;  
        final Paint paint = new Paint();  
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
        final RectF rectF = new RectF(rect);  
        paint.setAntiAlias(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        paint.setColor(color);  
        canvas.drawRoundRect(rectF, roundPx,roundPx, paint);  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(bitmap, rect, rect, paint);  
       
        return output;  
	}
	
	/*
	 * drawable to bitmap
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		// get drawabel width and height
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();

		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;// get drawabel color style
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);//create bitmap
		
		Canvas canvas = new Canvas(bitmap);//create bitmap's canvas
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);//draw to canvas
		return bitmap;
	}
}
