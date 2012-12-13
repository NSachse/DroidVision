package com.droidvision.utils;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.WindowManager;

import com.droidvision.R;
import com.droidvision.models.Film;

/**
 * Class to obtain metrics use throughout the app and to initiliaze the data.
 * 
 * @author Nelson Sachse
 * @version 1.0
 *
 */
public class DroidvisionApplication extends Application{
	public static ArrayList<Film> filmsList;
	private static Bitmap posterImg;
	private Display display;
	private static int mScreenWidth;
	private static int mScreenHeight;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		initVars();
		createContent();
	}
	
	/**
	 * Initialize the variables use throughout the application
	 */
	private void initVars(){
		filmsList = new ArrayList<Film>(2);

		WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		display = wm.getDefaultDisplay();
		mScreenWidth = display.getWidth();
		mScreenHeight = display.getHeight();
	}
	
	/**
	 * Creates the content for the films and inserts them into the films list array
	 */
	private void createContent(){
		posterImg = decodeResource(R.drawable.superman);
		filmsList.add(new Film("Superman", StreamUtils.path1, posterImg, 1));
		
		posterImg = decodeResource(R.drawable.total);
		filmsList.add(new Film("Total Recal", StreamUtils.path2, posterImg, 2));
	}
	
	/**
	 * Decode the resource to a bitmap
	 * 
	 * @param res
	 * @return bitmap
	 */
	private Bitmap decodeResource(int res){
		return BitmapFactory.decodeResource(getResources(),res);
	}
	
	// screen width getter
	public static int getScreenWidth(){
		return mScreenWidth;
	}

	// screen height getter
	public static int getScreenHeight(){
		return mScreenHeight;
	}
}
