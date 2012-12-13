package com.droidvision.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.MediaController;

/**
 * Class to obtain the controller of the video view
 * 
 * @author Nelson Sachse
 * @version 1.0
 *
 */

public class MediaControllerView extends MediaController {

	public MediaControllerView(Context context) {
		super(context);
	}

	public MediaControllerView(Context context, AttributeSet attrs) {
		super(context, attrs);
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
