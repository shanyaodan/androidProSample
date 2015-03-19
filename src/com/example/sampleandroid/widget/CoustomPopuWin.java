package com.example.sampleandroid.widget;

import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.sampleandroid.App;
import com.example.sampleandroid.R;
import com.example.sampleandroid.base.BaseActivity;
import com.example.sampleandroid.util.CommonUtils;
import com.example.sampleandroid.util.L;

public class CoustomPopuWin {

	private PopupWindow popupWindow;
	private TextView loginText;
	private LinearLayout loginLayout;
	private int width;
	private TextView userName;
	private ImageView userHead;

	public CoustomPopuWin(final BaseActivity context) {
		// TODO Auto-generated constructor stub
		LayoutInflater mInflater = LayoutInflater.from(context);
		View v = mInflater.inflate(R.layout.popuwin_view, null);
		popupWindow = new PopupWindow(v, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);

		width = context.getWindowManager().getDefaultDisplay().getWidth();

		popupWindow.setWidth(width / 2 + CommonUtils.getDementions(10));
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);

		popupWindow.getContentView().setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_MENU) {
					L.v("onKey", "onKeyDown", getPopWinState());
					if (getPopWinState()) {
						popupWindow.dismiss();
						return true;
					} else {

					}
				}

				return false;
			}
		});

	}

	public void dismiss() {
		if (null != popupWindow) {
			popupWindow.dismiss();
			popupWindow.update();
		}
	}

	public void show(View anchor) {
		if (null != popupWindow) {

			if (width <= 0) {
				width = App.getContext().getResources().getDisplayMetrics().widthPixels;
			}

			popupWindow.showAsDropDown(anchor,
					width / 2 - CommonUtils.getDementions(20), 0);
			popupWindow.update();
		}
	}

	public void show(View parent, int gravity, int x, int y) {
		if (null != popupWindow) {
			popupWindow.showAtLocation(parent, gravity, x, y);
		}
	}

	public void show(View anchor, int xoff, int yoff) {
		if (null != popupWindow) {
			popupWindow.showAsDropDown(anchor, xoff, yoff);
		}
	}

	public boolean getPopWinState() {

		return popupWindow.isShowing();
	}

}
