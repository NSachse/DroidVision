package com.droidvision.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.MediaController;

public class DroidMediaController extends MediaController {

	public DroidMediaController(Context context) {
		super(context);
	}

	public DroidMediaController(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	public void show() {
		//override the hide method to prevent the controller to show up
	}
	
	@Override
	public void hide() {
		super.hide();
	}
}
