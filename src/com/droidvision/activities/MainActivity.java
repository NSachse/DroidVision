package com.droidvision.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.droidvision.R;
import com.droidvision.view.BookshelfView;

public class MainActivity extends Activity {
	public static VideoView mVideoView;
	public static MediaController mediaController;
	private RelativeLayout rootLayout;
	private BookshelfView mBookshelfView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initLayout(R.layout.activity_main);
	}

	private void initLayout(int layout) {

		// Inflate root
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		rootLayout = (RelativeLayout) inflater.inflate(layout, null);
		
		mBookshelfView = new BookshelfView(getApplicationContext());
		
//		mVideoView = (VideoView) findViewById(R.id.video_view);
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
//				mVideoView.setVideoURI(Uri.parse(StreamUtils.path2));
//				mVideoView.start();
//			}
//		});
//	
		
		
//		DroidMediaController mMediaController = (DroidMediaController) new DroidMediaController(this);
//		mVideoView.setMediaController(mMediaController);
//		mVideoView.setBackgroundColor(Color.TRANSPARENT);
//		mVideoView.requestFocus();
//		
//		// set touch to create a simpler UI to enable/disable the visualization of the video
//		mVideoView.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				int action = event.getAction();
//				
//				switch(action){
//				case MotionEvent.ACTION_DOWN:{
//		
//					if(mVideoView.isPlaying()){
//						mVideoView.pause();
//					}else{
//						mVideoView.start();
//					}
//				}
//				break;
//				}
//				return true;
//			}
//		});
		
		rootLayout.addView(mBookshelfView);
		setContentView(rootLayout);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
