package com.launcher;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class UserAppProcess {
	
	public final String TAG ="UserAppProcess";
	public final String groupDbPath = "/data/data/org.thtf.myapp/databases/myapp.db";
	public Uri GroupDetailUri = Uri.parse("content://org.thtf.myapp/myapp");	
	public Context context;

	public static ArrayList<String> allApksGroupDetailPkgName = new ArrayList<String>();
	public static ArrayList<String> allApksToDisplayInDesktop = new ArrayList<String>();	
	
	public UserAppProcess(Context context){
		this.context = context;
	}

	public boolean isDbFileExist(){
		if( !(new File(groupDbPath).exists() ) ){
			Log.d(TAG,"DB file not exist");
			return false;
		}else{
			return true;
		}
	}
	
	public void readGroupDetail(){
		//check everytime before read to avoid db be deleted
		if( !isDbFileExist() ){
			return ;
		}
		
		allApksGroupDetailPkgName.clear();
		allApksToDisplayInDesktop.clear();

		Cursor cursor = context.getContentResolver().query(GroupDetailUri, null, null, null, null);
		while(cursor.moveToNext()){
			String tempApkGroupDetailPkgName = cursor.getString(cursor.getColumnIndex("pkg_name"));
			allApksGroupDetailPkgName.add(tempApkGroupDetailPkgName);
		}
		cursor.close();
	
		for(int f = 0 ; f < allApksGroupDetailPkgName.size() ; f ++){
			String curApkGroupDetailPkgName = allApksGroupDetailPkgName.get(f);
			
			Log.d(TAG,"add PkgName = " + curApkGroupDetailPkgName);
			allApksToDisplayInDesktop.add(curApkGroupDetailPkgName);
			
		}
		
	}
	
}
