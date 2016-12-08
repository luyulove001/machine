package com.qc.qchouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {

	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	private static final int GO_REGIST = 1002;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_GUIDE:
//				goGuide();
				break;
			case GO_REGIST:
				goRegist();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,        
                WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		setContentView(R.layout.splash);
		
		SharedPreferences sp = getSharedPreferences("ShopId", Context.MODE_PRIVATE);
		final String ShopId = sp.getString("id",null);
		if(ShopId != null){
			mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
		}else {
			mHandler.sendEmptyMessageDelayed(GO_REGIST, 2000);
		}
	}
	
	private void goHome() {
		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}
	
	/*private void goGuide() {
		Intent intent = new Intent(SplashActivity.this, FirstRegistActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}*/
	
	private void goRegist() {
		Intent intent = new Intent(SplashActivity.this, FirstRegistActivity.class);
		intent.putExtra("intent","1002");
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}
	
	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}
	
}
