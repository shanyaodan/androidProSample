package com.example.sampleandroid.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sampleandroid.R;
import com.example.sampleandroid.activity.MainActivity;
import com.example.sampleandroid.widget.GifView;

public abstract class BaseFragment extends Fragment {
	public String TAG = ((Object) this).getClass().getSimpleName();
	private View waitinglayout, errorlayout;
	private GifView loadinggif;

	protected void initCommView(View parentView) {
		waitinglayout = parentView.findViewById(R.id.waitinglayout);
		loadinggif = (GifView) parentView.findViewById(R.id.loading_gif);
		errorlayout = parentView.findViewById(R.id.errorlayout);
	}

	public void getListData(Handler handler, int type, int listSign) {

	}

	public void setCurrentPage(String current, int sign) {

	}

	/**
	 * 显示等待
	 */
	public void showWaiting() {

		if (null != waitinglayout) {
			waitinglayout.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 消除等待
	 */
	public void clearWaiting() {

		if (null != waitinglayout) {
			waitinglayout.setVisibility(View.GONE);
		}

	}

	public void showErrorLayout() {
		if (null != errorlayout) {
			errorlayout.setVisibility(View.VISIBLE);
		}
	}

	public void dismissErrorLayout() {
		if (null != errorlayout) {
			errorlayout.setVisibility(View.GONE);
		}
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = getActivity();
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	protected Context context;

	public static BaseFragment getInstance(Context context,
			String fragmentName, Bundle bundle) {
		BaseFragment instance = null;

		instance = (BaseFragment) Fragment.instantiate(context, fragmentName,
				bundle);
		instance.context = context;
		return instance;
	}

	public static BaseFragment getInstance(Context context, String fragmentName) {

		return getInstance(context, fragmentName, null);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	private void dissmissall() {
		waitinglayout.setVisibility(View.GONE);
		errorlayout.setVisibility(View.GONE);
		loadinggif.setVisibility(View.GONE);

	}

}
