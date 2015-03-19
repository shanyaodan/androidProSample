package com.example.sampleandroid.util;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sampleandroid.App;
import com.example.sampleandroid.R;
import com.example.sampleandroid.base.BaseActivity;
import com.example.sampleandroid.widget.DialogButtonsListener;

/**
 * 
 * @author Administrator
 * 
 */
public class CommAlertDialog extends Dialog {

	private static final String TAG = CommAlertDialog.class.getSimpleName();

	private LinearLayout mlayout;

	private TextView title;

	private TextView contenttv;

	/**
	 * 用setContentLayout设置内容</br> 用setTitleTv设置标题</br> 用setContentInfo设置单一文本内容
	 * 
	 * @param context
	 */
	private Context context;

	public CommAlertDialog(Context context) {
		this(context, R.style.comm_alertdialog);
		this.context = context;

	}

	public void setCannelBtnName(int stringId) {

		((Button) mlayout.findViewById(R.id.canclebut)).setText(stringId);

	}

	public void setOkBtnName(int stringId) {

		((Button) mlayout.findViewById(R.id.okbut)).setText(stringId);
	}

	public CommAlertDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		mlayout = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.comm_alertdialog, null);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(mlayout);
	}

	public void setLeftButtonName(String name) {
		Button okBut = (Button) mlayout.findViewById(R.id.okbut);
		okBut.setText(name);
	}

	public void setRightButtonName(String name) {
		Button cancleBut = (Button) mlayout.findViewById(R.id.canclebut);
		cancleBut.setText(name);
	}

	public void setContentLayout(View view) {
		ViewGroup content = ((ViewGroup) mlayout.findViewById(R.id.content));
		content.removeAllViews();

		content.addView(view);
	}

	public void setContentLayout(int id) {
		View v = LayoutInflater.from(App.getContext()).inflate(id, null);
		ViewGroup content = ((ViewGroup) mlayout.findViewById(R.id.content));
		content.removeAllViews();
		content.addView(v);
	}

	public void setTitleTv(String tString) {
		title = (TextView) mlayout.findViewById(R.id.alertTitle);
		title.setText(tString);
	}

	public void setTitleTv(int id) {
		title = (TextView) mlayout.findViewById(R.id.alertTitle);
		title.setText(id);
	}

	public void setContentInfo(int id) {
		contenttv = (TextView) mlayout.findViewById(R.id.contenttv);
		contenttv.setVisibility(View.VISIBLE);
		contenttv.setText(id);
		contenttv.setMovementMethod(ScrollingMovementMethod.getInstance());
	}

	public void setContentInfo(String msg) {
		contenttv = (TextView) mlayout.findViewById(R.id.contenttv);
		contenttv.setVisibility(View.VISIBLE);
		contenttv.setText(msg);
		contenttv.setMovementMethod(ScrollingMovementMethod.getInstance());
	}

	public void DisMissRightBut() {
		mlayout.findViewById(R.id.canclebut).setVisibility(View.GONE);
		mlayout.findViewById(R.id.divimg).setVisibility(View.GONE);
	}

	public void setButtonsListener(final DialogButtonsListener listener) {
		final Button okBut = (Button) mlayout.findViewById(R.id.okbut);
		final Button cancleBut = (Button) mlayout.findViewById(R.id.canclebut);

		android.view.View.OnClickListener onclick = new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v == okBut) {
					listener.onOKClick();
				} else if (v == cancleBut) {
					listener.onCancleClick();
				}
			}
		};
		okBut.setOnClickListener(onclick);
		cancleBut.setOnClickListener(onclick);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

		if (null == context || ((BaseActivity) context).isFinishing()) {
			return;
		}

		ActivityManager am = (ActivityManager) mlayout.getContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

		L.i("-------------", cn.getClassName() + ":"
				+ mlayout.getContext().getClass().getName()
				+ " cn.getClassName() " + cn.getClassName());

		if (cn.getClassName().equals(mlayout.getContext().getClass().getName())) {
			super.show();
			L.v(TAG, "show", " >>><<<");
			WindowManager.LayoutParams lp = getWindow().getAttributes();

			// int w =
			// View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
			// int h =
			// View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
			// mlayout.measure(w, h);
			// int height =mlayout.getMeasuredHeight();
			int width = App.getContext().getResources().getDisplayMetrics().widthPixels;
			lp.width = (int) (width * 0.8);// 定义宽度
			// lp.height=height;
			getWindow().setAttributes(lp);
		}

	}

	public void setOkClickable(boolean b) {
		Button okBut = (Button) mlayout.findViewById(R.id.okbut);
		okBut.setClickable(b);
	}

	@Override
	public View findViewById(int id) {
		// TODO Auto-generated method stub
		return mlayout.findViewById(id);
	}
}
