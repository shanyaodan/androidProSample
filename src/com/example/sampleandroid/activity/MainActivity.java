package com.example.sampleandroid.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.sampleandroid.R;
import com.example.sampleandroid.base.BaseActivity;
import com.example.sampleandroid.http.API;
import com.example.sampleandroid.mode.AppVersion;
import com.example.sampleandroid.mode.BaseEntity;
import com.example.sampleandroid.util.CommonUtils;
import com.example.sampleandroid.util.DownloadHandler;
import com.example.sampleandroid.util.L;
import com.example.sampleandroid.util.PreferencesUtils;
import com.example.sampleandroid.widget.CommAlertDialog;
import com.example.sampleandroid.widget.DialogButtonsListener;

public class MainActivity extends BaseActivity {

	private static final int CHECK_VERSION = 0;

	private Handler mHandler = new Handler() {
		@Override
		public void dispatchMessage(android.os.Message msg) {
			L.v(TAG, "dispatchMessage", msg.what);
			switch (msg.what) {

			case API.REQUEST_NO_NETWORK:
				CommonUtils.showToast(R.string.network_error);
				break;
			case API.REQUEST_BEGIN:

				break;
			case CHECK_VERSION:
				showWaiting();
				break;

			case API.REQUEST_SUCCESS:
				if (msg.arg1 == CHECK_VERSION) {
					if (null == (BaseEntity) msg.obj) {
						clearWaiting();
						break;
					}
					final AppVersion version = (AppVersion) ((BaseEntity) msg.obj).content;
					if (null != version) {
						if (!"0".equals(version.update)
								&& !TextUtils.isEmpty(version.downLoadUrl)) {
							final CommAlertDialog dialog = new CommAlertDialog(
									MainActivity.this);
							if ("2".equals(version.update)) {
								dialog.DisMissRightBut();
							}
							dialog.setButtonsListener(new DialogButtonsListener() {
								@Override
								public void onOKClick() {
									final DownloadHandler handler = DownloadHandler
											.getInstance(MainActivity.this);
									new Thread() {
										@Override
										public void run() {
											String url = PreferencesUtils
													.getString("shost")
													+ version.downLoadUrl;
											handler.downApk(url, "正本下载");
										}
									}.start();
									dialog.dismiss();
								}

								@Override
								public void onCancleClick() {
									dialog.dismiss();
								}
							});
							dialog.setTitleTv("升级提示");
							// dialog.setContentInfo(version.updateDesc);
							dialog.setContentInfo("检测到新版本，是否升级？");
							dialog.show();
						}
					}
					clearWaiting();
				}
				break;
			case API.REQUEST_FAIL:
				switch (msg.arg1) {
				case CHECK_VERSION:
					clearWaiting();
					break;
				}

				break;
			default:
				break;
			}

		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		checkVersion();
	}

	private void checkVersion() {
		Message msage = new Message();
		msage.arg1 = CHECK_VERSION;
		msage.setTarget(mHandler);
		// API.checkVersion(msage);
	}
}
