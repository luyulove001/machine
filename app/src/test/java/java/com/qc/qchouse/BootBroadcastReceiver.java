package java.com.qc.qchouse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*
 * ��������
 */

public class BootBroadcastReceiver extends BroadcastReceiver {  
	@Override  
    public void onReceive(Context context, Intent intent) {  
    	Intent intent1 = new Intent (context,SplashActivity.class);
    	intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        context.startActivity(intent1);
    }  
}