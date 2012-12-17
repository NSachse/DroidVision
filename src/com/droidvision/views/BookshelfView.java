package com.droidvision.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.droidvision.R;
import com.droidvision.activities.DroidvisionApplication;
import com.droidvision.debug.Debug;
import com.droidvision.interfaces.FilmListener;
import com.droidvision.models.Film;

/**
 * This view will allow the user to drag and drop the film to the player.
 * 
 * Class that extends the SurfaceView so it's possible to draw the necessary bitmaps on the screen
 * 
 * @author Nelson Sachse
 * @version 1.0
 *
 */
public class BookshelfView extends SurfaceView implements SurfaceHolder.Callback {
	private static final int FILM_POSTER_WIDTH = 300;
	private static final int FILM_POSTER_HEIGHT = 400;
	
	private static final int INVALID_POINTER_ID = -1;
	private int mActivePointerId = INVALID_POINTER_ID;
    private float mPosX;
    private float mPosY;
    private float mLastTouchX;
    private float mLastTouchY;

    private MyThread thread;
    private Bitmap mBackground;
    
    private Film mFilm1;
    private Film mFilm2;
    private static Bitmap mFilmPoster1;
    private static Bitmap mFilmPoster2;
    private FilmListener listener;
    
    private static int screenHeight;
    private static int screenWidth;
    
    private static android.view.ViewGroup.LayoutParams mSurfaceParams;
    private SurfaceView mSurfaceView;
    
	public BookshelfView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public BookshelfView(Context context) {
		super(context);
		
		// get instance of this surface
		mSurfaceView = (SurfaceView) this;
		
		// get screen sizes
		screenHeight = DroidvisionApplication.getScreenHeight();
		screenWidth = DroidvisionApplication.getScreenWidth();
		
		// set mBackground
		mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		
		// initialize the content
		initFilmContent();
		
		// initialize thread
		thread = new MyThread(getHolder(), this);
		getHolder().addCallback(this);
		setFocusable(true);
	}

	/**
	 * Method to obtain the films objects that will be present on the screen
	 */
	private void initFilmContent(){
		//get films from the list
		mFilm1 = DroidvisionApplication.filmsList.get(0);
	    mFilm2 = DroidvisionApplication.filmsList.get(1);
	
	    // get initial position
	    mFilm1.setPosX(0);
	    mFilm1.setPosY(0);
	    mFilm2.setPosX(300);
	    mFilm2.setPosY(0);
	    
	    // create a scaled poster bitmap
	    mFilmPoster1 = Bitmap.createScaledBitmap(mFilm1.getPosterImg(), FILM_POSTER_WIDTH, FILM_POSTER_HEIGHT, true);
	    mFilmPoster2 = Bitmap.createScaledBitmap(mFilm2.getPosterImg(), FILM_POSTER_WIDTH, FILM_POSTER_HEIGHT, true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
	
		if(canvas!=null){
			// draw background
			canvas.save();
			canvas.scale((float) screenWidth / mBackground.getWidth(), (float) screenHeight/ mBackground.getHeight());
			canvas.drawBitmap(mBackground, 0, 0, null);
			canvas.restore();
			
			// draw film 1 poster
			canvas.save();
			canvas.drawBitmap(mFilmPoster1, mFilm1.getPosX(), mFilm1.getPosY(), null);
			canvas.restore();
			
			// draw film 2 poster
			canvas.save();
			canvas.drawBitmap(mFilmPoster2, mFilm2.getPosX(), mFilm2.getPosY(), null);
			canvas.restore();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int value = event.getAction() & MotionEvent.ACTION_MASK;

	    switch (value) {
	    case MotionEvent.ACTION_DOWN:{
	    	final float x = event.getX();
	        final float y = event.getY();
	        mLastTouchX = x;
	        mLastTouchY = y;

	        // Save the ID of this pointer
	        mActivePointerId = event.getPointerId(0);
	        Debug.show("ACTION_DOWN : "+mActivePointerId);
	       
	        
	        if(touchSpecificView(mFilm1.getPosX(), mFilm1.getPosY())){
	        	mFilm1.setActive(true);
	        	mFilm1.setRestorePos(true);
	        	
	        	mFilm2.setActive(false);
	        }else 
	        	if(touchSpecificView(mFilm2.getPosX(), mFilm2.getPosY())){
		        	mFilm2.setActive(true);
		        	mFilm2.setRestorePos(true);
		        	
		        	mFilm1.setActive(false);
	        }else{
	        	mFilm1.setActive(false);
	        	mFilm2.setActive(false);
	        }
	        break;
	    }
	    
	    case MotionEvent.ACTION_UP:
	    	mActivePointerId = INVALID_POINTER_ID;
	    	break;
	    	
	    case MotionEvent.ACTION_CANCEL:
	        mActivePointerId = INVALID_POINTER_ID;
	        break;
	    
	    case MotionEvent.ACTION_POINTER_UP:{
	    	// Extract the index of the pointer that left the touch sensor
	        final int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
	        final int pointerId = event.getPointerId(pointerIndex);
	        
	        if (pointerId == mActivePointerId) {
	            // This was our active pointer going up. Choose a new
	            // active pointer and adjust accordingly.
	            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
	            mLastTouchX = event.getX(newPointerIndex);
	            mLastTouchY = event.getY(newPointerIndex);
	            mActivePointerId = event.getPointerId(newPointerIndex);
	        }
	        break;
	    }
	    
	    case MotionEvent.ACTION_MOVE:{
	    	// Find the index of the active pointer and fetch its position
	        final int pointerIndex = event.findPointerIndex(mActivePointerId);
	        final float x = event.getX(pointerIndex);
	        final float y = event.getY(pointerIndex);
	        float dx;
	        float dy;

    		dx = x - mLastTouchX;
	    	dy = y - mLastTouchY;
	    	
	    	mPosX = x;
		    mPosY = y;
	        
		    mLastTouchX = x;
        	mLastTouchY = y;
        	
		    if(mFilm1.isActive()){
		    	if(mFilm1.hasRestorePos()){
		    		mPosX = mFilm1.getRestorePosX();
				    mPosY = mFilm1.getRestorePosY();	   
		    	}
		        
		    	mFilm1.setPosX(mPosX);
		    	mFilm1.setPosY(mPosY);
		    	
		    	mFilm1.setRestorePos(false);
		    	mFilm1.setRestorePosX(mPosX);
		    	mFilm1.setRestorePosY(mPosY);
		    	
		    	if(isInserted(mPosY)){
		    		listener.filmIsInserted(mFilm1);
		    	}
	        }
		    if(mFilm2.isActive()){
	    	    if(mFilm2.hasRestorePos()){
	    		   mPosX = mFilm2.getRestorePosX();
			       mPosY = mFilm2.getRestorePosY();	   
	    	    }
	    	    
	    	    mFilm2.setPosX(mPosX);
		    	mFilm2.setPosY(mPosY);
		    	
		    	mFilm2.setRestorePos(false);   
		    	mFilm2.setRestorePosX(mPosX);
		    	mFilm2.setRestorePosY(mPosY);
	    	   
	    	   if(isInserted(mPosY))
	        		listener.filmIsInserted(mFilm2);
	       }
	       break;
	    }
	    }
		return true;
	}
	
	/**
	 * Validate if the respective view (film poster) is being touched
	 * 
	 * @param posX
	 * @param posY
	 * @return true|false
	 */
	private boolean touchSpecificView(float posX, float posY){
		float diffX = posX+FILM_POSTER_WIDTH;
        float diffY = posY+FILM_POSTER_HEIGHT;
        
		return ((mLastTouchX>posX && mLastTouchX<diffX) && (mLastTouchX>posY && mLastTouchY<diffY));
	}
	
	/**
	 * Identify if the view that is being drag has pass 1/3 of the screen, thus enabling the listener
	 * @param y
	 * @return true|false
	 */
	private boolean isInserted(float y) {
		//Get the SurfaceView layout parameters
		mSurfaceParams = mSurfaceView.getLayoutParams();

		return y>(mSurfaceParams.height/3);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
			
	    //Get the dimensions of the video
	    int videoWidth = screenWidth;
	    int videoHeight = (screenHeight-screenHeight/3);

	    //Get the SurfaceView layout parameters
	  	mSurfaceParams = mSurfaceView.getLayoutParams();
	  	
	    //Set the width of the SurfaceView to the width of the screen
	    mSurfaceParams.width = screenWidth;

	    //Set the height of the SurfaceView to match the aspect ratio of the video 
	    mSurfaceParams.height = (int) (((float)videoHeight / (float)videoWidth) * (float)screenWidth);

		// prevent to start a new thread when the screen is draw again
		if(!thread.isAlive()){
		    thread.startrun(true);
			thread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread.startrun(false);
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Inner Class that extends Thread so it's possible to capture and reshape the surface of the this view
	 */
	private class MyThread extends Thread {

		private SurfaceHolder msurfaceHolder;
		private BookshelfView mBookshelfView;
		private boolean mrun = false;

		public MyThread(SurfaceHolder holder, BookshelfView mBookshelfView) {
			msurfaceHolder = holder;
			this.mBookshelfView = mBookshelfView;
		}

		public void startrun(boolean run) {
			mrun = run;
		}

		@Override
		public void run() {
			super.run();
			Canvas canvas;
			while (mrun) {
				canvas = null;
				try {
					canvas = msurfaceHolder.lockCanvas(null);
					synchronized (msurfaceHolder) {
						mBookshelfView.onDraw(canvas);
					}
				} finally {
					if (canvas != null) {
						msurfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
	}
	
	/**
	 * Obtain listener to control the flow of the posters bitmap
	 * 
	 * @param listener
	 */
	public void setListener(FilmListener listener) {
		this.listener = listener;
	}
}
