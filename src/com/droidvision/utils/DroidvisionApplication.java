package com.droidvision.utils;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.WindowManager;

import com.droidvision.R;
import com.droidvision.model.Film;

public class DroidvisionApplication extends Application{
	public Object[] FilmsCollation;
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
	
	private void initVars(){
		filmsList = new ArrayList<Film>(3);

		WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		display = wm.getDefaultDisplay();
		mScreenWidth = display.getWidth();
		mScreenHeight = display.getHeight();
	}
	
	private void createContent(){
		Film f;
		
		posterImg = decodeResource(R.drawable.superman);
		f = new Film("Superman", StreamUtils.path1, posterImg, 1);
		filmsList.add(f);
		
		posterImg = decodeResource(R.drawable.total);
		f = new Film("Total Recal", StreamUtils.path2, posterImg, 2);
		filmsList.add(f);
	}
	
	private Bitmap decodeResource(int res){
		return BitmapFactory.decodeResource(getResources(),res);
	}
	
	public static int getScreenWidth(){
		return mScreenWidth;
	}
	
	public static int getScreenHeight(){
		return mScreenHeight;
	}
}
