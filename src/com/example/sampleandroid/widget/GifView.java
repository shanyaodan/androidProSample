package com.example.sampleandroid.widget;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import com.example.sampleandroid.R;

/**
 * gif动画，注意: android:hardwareAccelerated="false"
 * 
 * @author Administrator
 * 
 */
public class GifView extends View {
	private boolean isDoing = false;

	/**
	 * 
	 * @param context
	 * @param attrs
	 * @param drawable gif动画图片
	 */
	public GifView(Context context, AttributeSet attrs, int drawable) {
		super(context, attrs);
		setFocusable(true);
		InputStream is = context.getResources().openRawResource(drawable);

		if (DECODE_STREAM) {
			mMovie = Movie.decodeStream(is);
		} else {
			byte[] array = streamToBytes(is);
			mMovie = Movie.decodeByteArray(array, 0, array.length);
		}

	}

	private Movie mMovie;
	private long mMovieStart;

	// Set to false to use decodeByteArray
	private static final boolean DECODE_STREAM = true;

	private static byte[] streamToBytes(InputStream is) {
		ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024];
		int len;
		try {
			while ((len = is.read(buffer)) >= 0) {
				os.write(buffer, 0, len);
			}
		} catch (java.io.IOException e) {
		}
		return os.toByteArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (null != mMovie) {
			setMeasuredDimension(mMovie.width(), mMovie.height());
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(getResources().getColor(R.color.white));

		long now = android.os.SystemClock.uptimeMillis();
		if (mMovieStart == 0) { // first time
			mMovieStart = now;
		}
		if (mMovie != null) {
			int dur = mMovie.duration();
			if (dur == 0) {
				dur = 1000;
			}
			int relTime = (int) ((now - mMovieStart) % dur);
			mMovie.setTime(relTime);
			mMovie.draw(canvas, getWidth() / 2 - mMovie.width() / 2,
					getHeight() / 2 - mMovie.height() / 2);
			if (isDoing) {
				invalidate();
			}
		}
	}

	public void stopAnim() {
		isDoing = false;
	}

	public void startAnim() {
		isDoing = true;
		invalidate();
	}

}