package com.droidvision.activities;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.droidvision.R;
import com.droidvision.interfaces.FilmListener;
import com.droidvision.model.Film;
import com.droidvision.utils.DroidvisionApplication;
import com.droidvision.utils.StreamUtils;
import com.droidvision.view.BookshelfView;
import com.droidvision.view.MediaControllerView;

public class MainActivity extends Activity implements FilmListener{
	public static VideoView mVideoView;
	public static MediaController mediaController;
	
	private RelativeLayout rootLayout;
	private BookshelfView mBookshelfView;
	private FilmListener listener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listener = this;
		initLayout(R.layout.activity_main);
	}

	@SuppressWarnings("deprecation")
	private void initLayout(int layout) {

		// Inflate root
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		rootLayout = (RelativeLayout) inflater.inflate(layout, null);
		
		mBookshelfView = new BookshelfView(getApplicationContext());
		mBookshelfView.setListener(listener);
		RelativeLayout.LayoutParams bookshelfParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		mBookshelfView.setLayoutParams(bookshelfParams);
		RelativeLayout viewHolder = new RelativeLayout(getApplicationContext());
		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		viewHolder.setLayoutParams(layoutParams);

		// create Video View
		mVideoView = new VideoView(getApplicationContext());
		RelativeLayout.LayoutParams videoParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,(int)(DroidvisionApplication.getScreenHeight()/3));
		videoParams.setMargins(20, 20, 70, 20);
		videoParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mVideoView.setLayoutParams(videoParams);
		mVideoView.setMediaController(new MediaControllerView(this));
		mVideoView.setVideoURI(Uri.parse(StreamUtils.path1));
		mVideoView.requestFocus();
		viewHolder.addView(mVideoView);
		// set touch to create a simpler UI to enable/disable the visualization of the video
		mVideoView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				
				switch(action){
				case MotionEvent.ACTION_DOWN:{
		
					if(mVideoView.isPlaying()){
						mVideoView.pause();
					}else{
						Log.w("event ", "touch"); 
						mVideoView.start();
					}
				}
				
				break;
				}
				return true;
			}
		});
		
		// create Tv layout
		View tvImg = new View(getApplicationContext());
		tvImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.old_tv));
		RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,(int)(DroidvisionApplication.getScreenHeight()/3));
		tvParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		tvImg.setLayoutParams(tvParams);
		viewHolder.addView(tvImg);
				
		
//		
//		View trailer1 = (View) findViewById(R.id.trailer_1);
//		trailer1.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				mVideoView.setVideoURI(Uri.parse(StreamUtils.path1));
//				mVideoView.start();
//			}
//		});
//		
//		View trailer2 = (View) findViewById(R.id.trailer_2);
//		trailer2.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				
//			}
//		});
//	
		
		
		rootLayout.addView(mBookshelfView);
		rootLayout.addView(viewHolder);
		setContentView(rootLayout);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void filmIsInserted(Film film) {
		mVideoView.setVideoURI(Uri.parse(film.getLink()));
		mVideoView.start();
	}

}
