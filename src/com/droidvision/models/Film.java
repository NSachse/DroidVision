package com.droidvision.models;

import java.util.concurrent.atomic.AtomicInteger;

import android.graphics.Bitmap;

/**
 * Data object class to obtain any necessary information about the Film in question
 * 
 * @author Nelson Sachse
 * @version 1.0
 *
 */
public class Film {
	private int id;
	private String name;
	private String link;
	private Bitmap posterImg;
	private float mPosX;
	private float mPosY;
	private boolean isActive;
	private boolean restorePos;
	private float mLastTouchX;
	private float mLastTouchY;

	/**
	 * Film contructor
	 * 
	 * @param name
	 * @param link
	 * @param posterImg
	 * @param id
	 */
	public Film(String name, String link, Bitmap posterImg, int id) {
		this.name = name;
		this.link = link;
		this.posterImg = posterImg;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLink() {
		return link;
	}

	public Bitmap getPosterImg() {
		return posterImg;
	}

	public float getPosY() {
		return mPosY;
	}
	
	public float getPosX() {
		return mPosX;
	}
	
	public void setPosY(float mPosY) {
		this.mPosY =  mPosY;
	}
	
	public void setPosX(float mPosX) {
		this.mPosX = mPosX;
	}
	
	public void setActive(boolean isActive){
		this.isActive = isActive;
	}
	
	public boolean isActive(){
		return isActive;
	}

	public boolean hasRestorePos() {
		return restorePos;
	}

	public void setRestorePos(boolean restorePos) {
		this.restorePos = restorePos;  
	}

	public void setRestorePosX(float mLastTouchX) {
		this.mLastTouchX = mLastTouchX;
	}
	
	public void setRestorePosY(float mLastTouchY) {
		this.mLastTouchY = mLastTouchY;
	}
	
	public float getRestorePosX() {
		return mLastTouchX;
	}
	
	public float getRestorePosY() {
		return mLastTouchY;
	}
}
