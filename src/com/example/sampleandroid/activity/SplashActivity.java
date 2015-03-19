package com.example.sampleandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.example.sampleandroid.R;
import com.example.sampleandroid.util.CommonUtils;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		View v = findViewById(R.id.splashlayout);
		CommonUtils.checkNetState(true);

		AlphaAnimation anim = new AlphaAnimation((float) 0.3, 1);
		anim.setDuration(2500);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				SplashActivity.this.startActivity(new Intent(
						SplashActivity.this, MainActivity.class));
				SplashActivity.this.finish();
			}
		});
		v.startAnimation(anim);

		// // 2秒后跳转
		// new Handler().postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// SplashActivity.this.startActivity(new Intent(SplashActivity.this,
		// MainActivity.class));
		// SplashActivity.this.finish();
		// }
		// }, 2500);
	}
}
