package com.example.sampleandroid.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.sampleandroid.R;
import com.example.sampleandroid.widget.GifView;

public class BaseActivity extends FragmentActivity {
	public String TAG = ((Object) this).getClass().getSimpleName();

	private View waitinglayout, errorlayout;
	private GifView loadinggif;

	// protected static ActivityStack tack = ActivityStack.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	protected void initView() {
		// 等待进度条
		// 失败提示
		waitinglayout = findViewById(R.id.waitinglayout);
		loadinggif = (GifView) findViewById(R.id.loading_gif);
		errorlayout = findViewById(R.id.errorlayout);
	}

	protected void showAnimloadingView() {
		if (null != loadinggif) {
			loadinggif.startAnim();
		}
	}

	protected void DismissAnimloadingView() {
		if (null != loadinggif) {
			loadinggif.stopAnim();
		}
	}

	/**
	 * 添加actionbar返回按钮
	 */
	@SuppressLint("NewApi")
	protected void initActionBar() {

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	/**
	 * 显示等待
	 */
	public void showWaiting(String info) {

		if (null != waitinglayout) {
			waitinglayout.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 显示等待
	 */
	public void showWaiting() {
		showWaiting("");

	}

	/**
	 * 消除等待
	 */
	public void clearWaiting() {

		if (null != waitinglayout) {
			waitinglayout.setVisibility(View.GONE);
		}

	}

	public void showErrorLayout(int StringId) {
		if (null != errorlayout) {
			errorlayout.setVisibility(View.VISIBLE);
		}

	}

	public void showErrorLayout() {

	}

	public void dismissErrorLayout() {
		if (null != errorlayout) {
			errorlayout.setVisibility(View.GONE);
		}
	}

	public void getListData(int type) {

	}

}
