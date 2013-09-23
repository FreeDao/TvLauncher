package com.launcher;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class UserAppProcess {
	
	public final String TAG ="UserAppProcess";
	public final String groupDbPath = "/data/data/com.example.newthtfcemarket/databases/app_group.db";
	public Uri GroupDetailUri = Uri.parse("content://com.example.newthtfcemarket.utils.group_detail");	
	public Context context;

	public static ArrayList<String> allApksGroupDetailId = new ArrayList<String>();
	public static ArrayList<String> allApksGroupDetailPkgName = new ArrayList<String>();
	public static ArrayList<String> allApksToDisplayInDesktop = new ArrayList<String>();	
	
	public UserAppProcess(Context context){
		this.context = context;
	}
	
	public void readGroupDetail(){
		if( !(new File(groupDbPath).exists() ) ){
			return ;
		}
		
		allApksGroupDetailId.clear();
		allApksGroupDetailPkgName.clear();
		allApksToDisplayInDesktop.clear();

		Cursor cursor = context.getContentResolver().query(GroupDetailUri, null, null, null, null);
		while(cursor.moveToNext()){
			String tempApkGroupDetailId = cursor.getString(cursor.getColumnIndex("group_id"));
			String tempApkGroupDetailPkgName = cursor.getString(cursor.getColumnIndex("pkg_name"));
			allApksGroupDetailId.add(tempApkGroupDetailId);
			allApksGroupDetailPkgName.add(tempApkGroupDetailPkgName);
		}
		cursor.close();
	
		for(int f = 0 ; f < allApksGroupDetailId.size() ; f ++){
			String curApkGroupDetailId = allApksGroupDetailId.get(f);
			String curApkGroupDetailPkgName = allApksGroupDetailPkgName.get(f);
			
			if(curApkGroupDetailId.equals("-1")){
				Log.d(TAG,"______________add PkgName = " + curApkGroupDetailPkgName);
				allApksToDisplayInDesktop.add(curApkGroupDetailPkgName);
			}else{
				Log.d(TAG,"These words will never display!");			
			}
			
		}
		
	}
	
}
