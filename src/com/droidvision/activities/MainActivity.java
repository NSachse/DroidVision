package com.droidvision.activities;



import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.droidvision.R;
import com.droidvision.interfaces.FilmListener;
import com.droidvision.models.Film;
import com.droidvision.utils.ConnectionBroadcastHelper;
import com.droidvision.views.BookshelfView;
import com.droidvision.views.MediaControllerView;

/**
 * Activity class where all UI components are created
 * All views are created programmatically enabling a higher control over different aspect ratio of devices
 * 
 * @author Nelson Sachse
 * @version 1.0
 *
 */
public class MainActivity extends Activity implements FilmListener{
	public static VideoView mVideoView;
	public static MediaController mediaController;
	
	private View staticView;
	private RelativeLayout rootLayout;
	private BookshelfView mBookshelfView;
	private FilmListener listener;
	private static int screenHeight;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// get screen variables
		screenHeight = DroidvisionApplication.getScreenHeight();
		
		// listener
		listener = (FilmListener) this;
				
		initLayout(R.layout.activity_main);
	}

	@SuppressWarnings("deprecation")
	private void initLayout(int layout) {

		// Inflate root
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		rootLayout = (RelativeLayout) inflater.inflate(layout, null);
		
		// create surface
		mBookshelfView = new BookshelfView(getApplicationContext());
		mBookshelfView.setListener(listener);
		RelativeLayout.LayoutParams bookshelfParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		mBookshelfView.setLayoutParams(bookshelfParams);
		
		// create holder for the following views (tv + video view)
		RelativeLayout viewHolder = new RelativeLayout(getApplicationContext());
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		viewHolder.setLayoutParams(layoutParams);

		// create video View
		mVideoView = new VideoView(getApplicationContext());
		RelativeLayout.LayoutParams videoParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,(int)(screenHeight/3));
		videoParams.setMargins(40, 0, 90, 40);
		videoParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mVideoView.setLayoutParams(videoParams);
		// set custom media controller
		mVideoView.setMediaController(new MediaControllerView(this));
		mVideoView.setVisibility(View.INVISIBLE);
		
		// set touch to create a simpler UI to enable/disable the visualization of the video
		mVideoView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				
				switch(action){
				case MotionEvent.ACTION_DOWN:
					// Simple control to pause or resume the film player on touch
					if(mVideoView.isPlaying()){
						staticView.setVisibility(View.VISIBLE);
						mVideoView.pause();
					}else{
						staticView.setVisibility(View.INVISIBLE);
						mVideoView.start();
					}
				break;
				}
				return true;
			}
		});
		
		//static view
		staticView = new View(getApplicationContext());
		staticView.setBackgroundDrawable(getResources().getDrawable(R.drawable.static_img));
		RelativeLayout.LayoutParams staticViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,(int)(screenHeight/4));
		staticViewParams.setMargins(10, 0, 10, 40);
		staticViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		staticView.setLayoutParams(staticViewParams);
		staticView.setVisibility(View.VISIBLE);
		
		
		// create Tv layout
		View tvView = new View(getApplicationContext());
		tvView.setBackgroundDrawable(getResources().getDrawable(R.drawable.old_tv));
		RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,(int)(screenHeight/3));
		tvParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		tvView.setLayoutParams(tvParams);
		
		// test view 
		assert tvView!=null : "Invalided View";
		assert mVideoView!=null : "Invalided View";
		
		// add views to the holder
		viewHolder.addView(mVideoView);
		viewHolder.addView(staticView);
		viewHolder.addView(tvView);
		
		// add views to the parent view
		rootLayout.addView(mBookshelfView);
		rootLayout.addView(viewHolder);
		
		// set the content to display
		setContentView(rootLayout);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void filmIsInserted(Film film) {
		if(ConnectionBroadcastHelper.hasValidConnection(getApplicationContext())){
			mVideoView.setVideoURI(Uri.parse(film.getLink()));
			mVideoView.start();
			mVideoView.setVisibility(View.VISIBLE);
			staticView.setVisibility(View.INVISIBLE);	
		}else{
			Toast.makeText(getApplicationContext(), "No Connectivity", Toast.LENGTH_SHORT).show();
		}
		
	}

}
