package com.droidvision.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.droidvision.interfaces.BroadcastListener;


/**
 * Class used to listen for broadcast intents about connectivity changes.
 * Used to determine if WiFi connection has been lost. 
 * 
 * @author Nelson Sachse
 * @version 1.0
 */
public class ConnectionBroadcastHelper extends BroadcastReceiver {

	private BroadcastListener listener;
	public static final int START = 1;
	
	public ConnectionBroadcastHelper(BroadcastListener l) {
		this.listener = l;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
	}

	/**
	 * This method is called to check the availability of wifi and 3G connection.
	 */
	public static Boolean hasValidConnection(Context context) {
		ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		return (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isConnected());
	}
}
