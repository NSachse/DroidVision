package com.droidvision.view;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.droidvision.R;
import com.droidvision.interfaces.FilmListener;
import com.droidvision.model.Film;
import com.droidvision.utils.DroidvisionApplication;

public class BookshelfView extends SurfaceView implements SurfaceHolder.Callback {
	private static final int INVALID_POINTER_ID = -1;
	// The Ôactive pointerÕ is the one currently moving our object.
	private int mActivePointerId = INVALID_POINTER_ID;
    private float mPosX;
    private float mPosY;
    
    private float mLastTouchX;
    private float mLastTouchY;
    
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    private MyThread thread;
    private Bitmap mBackground;
    
    private Film mFilm1;
    private Film mFilm2;
    private Film mFilm3;
    private static Bitmap mFilmPoster1;
    private static Bitmap mFilmPoster2;
    
    private FilmListener listener;
    
    SurfaceView mSurfaceView;
	public BookshelfView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BookshelfView(Context context) {
		super(context);
		
		mSurfaceView = (SurfaceView) this;
		
		// Create our ScaleGestureDetector
	    mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
	    
		// set trailer poster
//		mTrailer = BitmapFactory.decodeResource(getResources(), R.drawable.total);
	    
	    mFilm1 = DroidvisionApplication.filmsList.get(0);
	    mFilm2 = DroidvisionApplication.filmsList.get(1);
	    mFilm1.setPosX(0);
	    mFilm1.setPosY(0);
	    mFilm2.setPosX(300);
	    mFilm2.setPosY(0);
	    
	    mFilmPoster1 = Bitmap.createScaledBitmap(mFilm1.getPosterImg(), 300, 400, true);
	    mFilmPoster2 = Bitmap.createScaledBitmap(mFilm2.getPosterImg(), 300, 400, true);
		
		// set mBackground
		mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.bookshelf);
		
		// init thread
		thread = new MyThread(getHolder(), this);
		getHolder().addCallback(this);
		setFocusable(true);
	}


	
	@Override
	protected void onDraw(Canvas canvas) {
	
		if(canvas!=null){
			canvas.save();
			canvas.scale((float) DroidvisionApplication.getScreenWidth() / mBackground.getWidth(), (float) DroidvisionApplication.getScreenHeight()/ mBackground.getHeight());
			canvas.drawBitmap(mBackground, 0, 0, null);
			canvas.restore();
			
			canvas.save();
			canvas.drawBitmap(mFilmPoster1, mFilm1.getPosX(), mFilm1.getPosY(), null);
			canvas.restore();
			
			canvas.save();
			canvas.drawBitmap(mFilmPoster2, mFilm2.getPosX(), mFilm2.getPosY(), null);
			canvas.restore();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int value = event.getAction() & MotionEvent.ACTION_MASK;
		
		// Let the ScaleGestureDetector inspect all events.
	    mScaleDetector.onTouchEvent(event);
	    
	 // Handle touch events here...
	    switch (value) {
	    case MotionEvent.ACTION_DOWN:{
	    	final float x = event.getX();
	        final float y = event.getY();
	        
	        mLastTouchX = x;
	        mLastTouchY = y;

	        // Save the ID of this pointer
	        mActivePointerId = event.getPointerId(0);
	        float diff1 = mFilm1.getPosX()+mFilmPoster1.getWidth();
	        float diff2 = mFilm1.getPosY()+mFilmPoster1.getHeight();
	        
	        Log.d("diff2",""+diff2);
	        Log.d("diff1",""+diff1);
	        
	        Log.d("mPosX",""+mPosX);
	        Log.d("mPosY",""+mPosY);
	        
	        if((mLastTouchX>mFilm1.getPosX() && mLastTouchX<diff1) && (mLastTouchY>mFilm1.getPosY() && mLastTouchY<diff2)){
	        	mFilm1.setActive(true);
	        	mFilm2.setActive(false);
	        }
	        else if((mLastTouchX>mFilm2.getPosX() && mLastTouchX<(mFilm2.getPosX()+mFilmPoster2.getWidth())) &&
	        		(mLastTouchY>mFilm2.getPosY() && mLastTouchY<(mFilm2.getPosY()+mFilmPoster2.getHeight()))){
	        	mFilm2.setActive(true);
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

	        final float dx = x - mLastTouchX;
	        final float dy = y - mLastTouchY;
	        
	        mPosX += dx;
	        mPosY += dy;
	        
	        mLastTouchX = x;
	        mLastTouchY = y;
	        
	        Log.w("mFilm1.isActive()",""+mFilm1.isActive());
	        
		    if(mFilm1.isActive()){
	    	   mFilm1.setPosX(mPosX);
	    	   mFilm1.setPosY(mPosY);
	    	   if(isInserted(mPosY))
	        		listener.filmIsInserted(mFilm1);
	        }
	       if(mFilm2.isActive()){
	    	   mFilm2.setPosX(mPosX);
	    	   mFilm2.setPosY(mPosY);
	    	   if(isInserted(mPosY))
	        		listener.filmIsInserted(mFilm2);
	       }
	       break;
	    }
	    }
		return true;
	}
	
	private boolean isInserted(float y) {
		//Get the SurfaceView layout parameters
	    android.view.ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();

		return y>lp.height;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
			
	    //Get the dimensions of the video (aprox value)
	    int videoWidth = 200;
	    int videoHeight = 200;

	    //Get the width of the screen
	    int screenWidth = DroidvisionApplication.getScreenWidth();

	    //Get the SurfaceView layout parameters
	    android.view.ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();

	    //Set the width of the SurfaceView to the width of the screen
	    lp.width = screenWidth;

	    //Set the height of the SurfaceView to match the aspect ratio of the video 
	    lp.height = (int) (((float)videoHeight / (float)videoWidth) * (float)screenWidth);

		thread.startrun(true);
		// prevent to start a new thread when screen is draw again
		if(!thread.isAlive())
			thread.start();
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
	 * 
	 *  ### INNER CLASSES ### 
	 * 
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
	
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	        mScaleFactor *= detector.getScaleFactor();
	        
	        // Don't let the object get too small or too large.
	        mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

	        return true;
	    }
	}

	public void setListener(FilmListener listener) {
		this.listener = listener;
	}
}
