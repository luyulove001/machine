package com.qc.application;

import android.app.Application;
import android.content.Context;

import com.pgyersdk.crash.PgyCrashManager;

import com.qc.help.DbConfig;
import com.qc.utils.CrashHandler;

public class App extends Application{
	private static Context appContext;
	private static App mInstance;
//	private MyHandler handler = null;
	
	public static App getInstance(){
		return mInstance;
	}
	
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		appContext = this;
		DbConfig.getInstance().getDb().configDebug(true);
		
		CrashHandler crashHandler = CrashHandler.getInstance();  
	    crashHandler.init(getApplicationContext());
		
		//�ѹ�Ӣ
		PgyCrashManager.register(this);
	}
}
