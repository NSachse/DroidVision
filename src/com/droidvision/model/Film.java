package com.droidvision.model;

import java.util.concurrent.atomic.AtomicInteger;

import android.graphics.Bitmap;

public class Film {
	private AtomicInteger id;
	private String name;
	private String link;
	private Bitmap posterImg;
	private float mPosX;
	private float mPosY;
	private boolean isActive;

	public Film(String name, String link, Bitmap posterImg, int id) {
		this.name = name;
		this.link = link;
		this.posterImg = posterImg;
		this.id = new AtomicInteger(id);
	}

	public AtomicInteger getId() {
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
}
