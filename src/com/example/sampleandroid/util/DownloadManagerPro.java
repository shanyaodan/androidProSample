package com.example.sampleandroid.util;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

public class DownloadManagerPro {

	private long downloadId = 0;
	/** 未下载状态ID为0 */
	private long DEFAULT_DOWNLOAD_ID = 0;
	/** sp保存的key */
	private String DOWNLOAD_KEY = "downloadId";
	private Context mContext;
	private DownloadManager mDownloadManager;

	private String apkUrl;

	public DownloadManagerPro(Context context, String url) {
		this.mContext = context;
		mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
		apkUrl = url;
		startDownload();
	}

	@SuppressLint("NewApi")
	private void startDownload() {
		downloadId = PreferencesUtils.getLong(mContext, DOWNLOAD_KEY, DEFAULT_DOWNLOAD_ID);
		if(downloadId == DEFAULT_DOWNLOAD_ID) {
			DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
			// 获取apk名称
			String apkName = mContext.getApplicationInfo().loadLabel(mContext.getPackageManager()).toString();
			// 设置下载地址
			request.setDestinationInExternalPublicDir(Constants.UPDATE_FILE_DIR, Constants.UPDATE_FILE_NAME);
		    request.setTitle(apkName);
		    request.setDescription("正在下载...");
		    // 提示一直显示,下载完成后点击则消失
		    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		    request.setVisibleInDownloadsUi(false);
		    
		    // 加入下载队列
		    downloadId = mDownloadManager.enqueue(request);
		    PreferencesUtils.putLong(mContext, DOWNLOAD_KEY, downloadId);
		}
	}

	/**
	 * 查看下载状态
	 * @param downloadId
	 * @return
	 */
	@SuppressLint("NewApi")
	public int getStatusById(long downloadId) {
		DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
		int result = -1;
		Cursor c = null;
		try {  
			c = mDownloadManager.query(query);
			if (c != null && c.moveToFirst()) {
				result = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	/**
	 * 下载完成的广播接收者
	 * @author user
	 *
	 */
	class CompleteReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
			if (completeDownloadId == downloadId) {
				// if download successful, install apk
				if (getStatusById(downloadId) == DownloadManager.STATUS_SUCCESSFUL) {
					PreferencesUtils.putLong(mContext, DOWNLOAD_KEY, DEFAULT_DOWNLOAD_ID);
					String apkFilePath = new StringBuilder(Environment
							.getExternalStorageDirectory().getAbsolutePath())
							.append(File.separator)
							.append("")
							.append(File.separator).append(mContext.getApplicationInfo().loadLabel(mContext.getPackageManager()))
							.toString();
					install(context, apkFilePath);
				}
			}
		}
	};
	
	public static boolean install(Context context, String filePath) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		File file = new File(filePath);
		if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
			i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
			return true;
		}
		return false;
	}
	
}
