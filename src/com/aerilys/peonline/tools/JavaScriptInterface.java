package com.aerilys.peonline.tools;

import com.aerilys.peonline.Main;
import com.aerilys.peonline.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


//javascript interface
public class JavaScriptInterface{
	 Context mContext;
	    private static final int HELLO_ID = 1;

	    /** Instantiate the interface and set the context */
	    public JavaScriptInterface(Context c) {
	        mContext = c;
	    }

	    /** Show a toast from the web page */
	    public void showToast(String toast) {
	        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	    }
	    
	    public void ShowStatusNotifications(String toast)
	    {
	    	Context context = Main.context;
			NotificationManager mNotificationManager = Main.mNotificationManager;
			
			int icon = R.drawable.peonline;
			CharSequence tickerText = toast;
			long when = System.currentTimeMillis();
		
			Notification notification = new Notification(icon, tickerText, when);
			
			CharSequence contentTitle = "Peonline";
			CharSequence contentText = toast;
			Intent notificationIntent = new Intent(context, Main.class);
			PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		
			mNotificationManager.notify(HELLO_ID, notification);
	    }
}
