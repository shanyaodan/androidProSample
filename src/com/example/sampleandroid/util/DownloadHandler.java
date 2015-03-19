package com.example.sampleandroid.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.widget.RemoteViews;

import com.example.sampleandroid.App;
import com.example.sampleandroid.R;
import com.example.sampleandroid.mode.DownloadItem;

public class DownloadHandler {

	private static DownloadHandler instance;
	public static final int DOWNLOAD_UPDATE = 1; // 更新下载进度
	public static final int DOWNLOAD_COMPLETE = 2; // 下载完成
	public static final int DOWNLOAD_FAIL = 3; // 下载失败
	public static final int REQEUSTFAIL = 4;
	public static final int SDCARD_NOSPACE = 5;
	public static final int NETWORK_ERROR = 6;

	private Context mContext;

	private NotificationManager mNotificationManager;
	// private Notification mNotification;

	private RemoteViews mRemoveViews;
	private int mNotificationId = 1000;

	// private int mDownloadPrecent = 0; // 下载进度

	private CommAlertDialog customDialog;
	private ArrayList<String> urlList = new ArrayList<String>();
	private final String APK_PATH = Environment.getExternalStorageDirectory()
			+ App.getContext().getString(R.string.saveDataPathRoot)
			+ App.getContext().getString(R.string.saveDataPathAPK);
	private HashMap<String, Notification> notificationMap = new HashMap<String, Notification>();

	// private HashMap<String, Notification> downloadItems=new HashMap<String,
	// Notification>();

	public static DownloadHandler getInstance(Context mContext) {
		if (null == instance) {
			synchronized (DownloadHandler.class) {
				if (null == instance) {
					instance = new DownloadHandler(mContext);
				}
			}
		}
		return instance;
	}

	public DownloadHandler(Context mContext) {
		this.mContext = mContext;
	}

	private Handler mHandler = new Handler() {
		@Override
		public synchronized void handleMessage(Message msg) {
			super.handleMessage(msg);
			DownloadItem downitem = (DownloadItem) msg.obj;
			if (msg != null) {

				switch (msg.what) {

				case DOWNLOAD_UPDATE:

					if (mNotificationManager == null) {
						// L.i("init mNotificationManager", "1111111111");
						mNotificationManager = (NotificationManager) mContext
								.getSystemService(Context.NOTIFICATION_SERVICE);
						// mNotification = new Notification();
						// mNotification.icon =
						// android.R.drawable.stat_sys_download;
						// // mNotification.tickerText =
						// mContext.getString(R.string.app_name) + "更新";
						// mNotification.tickerText = msg.obj.toString();
						// mNotification.when = System.currentTimeMillis();
						// mNotification.defaults = Notification.DEFAULT_LIGHTS;
						// Intent intent = new
						// Intent(mContext.getApplicationContext(),mContext.getClass());
						// PendingIntent contentIntent =
						// PendingIntent.getActivity(mContext, 0,
						// intent,PendingIntent.FLAG_UPDATE_CURRENT);
						// mNotification.contentIntent = contentIntent;
						// mRemoveViews = new
						// RemoteViews(mContext.getPackageName(),
						// R.layout.update);
						// mNotification.contentView = mRemoveViews;
						// mNotificationManager.notify(mNotificationId,mNotification);
					}

					Notification notifcation = getNotifcation(downitem.url);
					notifcation.tickerText = downitem.itemName + "";
					mRemoveViews = new RemoteViews(mContext.getPackageName(),
							R.layout.update);
					mRemoveViews.setTextViewText(R.id.down_title,
							downitem.itemName);
					mRemoveViews.setTextViewText(R.id.tvProcess, "已下载"
							+ downitem.itemPercent + "%");
					mRemoveViews.setProgressBar(R.id.pbDownload, 100,
							downitem.itemPercent, false);

//					if (null != downitem.type
//							&& DOWNLOAD_MM.equals(downitem.type)) {
					mRemoveViews.setImageViewResource(R.id.ivLogo,
							R.drawable.ic_launcher);
//					}
					// else {
					// mRemoveViews.setImageViewResource(R.id.ivLogo,
					// R.drawable.about_tv_logo);
					// }
					notifcation.contentView = mRemoveViews;
					mNotificationManager.notify(downitem.url.hashCode(),
							notifcation);

					// if (mNotificationManager != null) {
					// mNotification.tickerText = msg.obj.toString();
					// mRemoveViews.setTextViewText(R.id.tvProcess, "已下载"+
					// mDownloadPrecent + "%");
					// mRemoveViews.setProgressBar(R.id.pbDownload,
					// 100,mDownloadPrecent, false);
					// mNotification.contentView = mRemoveViews;
					// mNotificationManager.notify(mNotificationId,mNotification);
					// }
					break;
				case DOWNLOAD_COMPLETE:

					if (mNotificationManager != null
							&& notificationMap.get(downitem.url) != null) {
						mNotificationManager.cancel(downitem.url.hashCode());
						notificationMap.remove(downitem.url);
					}
					File newFile = new File(downitem.localFilePath);
					install(newFile, mContext);
					break;
				case DOWNLOAD_FAIL:
					if (mNotificationManager != null
							&& notificationMap.get(downitem.url) != null) {
						mNotificationManager.cancel(downitem.url.hashCode());
						notificationMap.remove(downitem.url);
					}
					if (customDialog != null)
						customDialog.dismiss();
					CommonUtils.showToast(R.string.downloadfail);
					break;
				case NETWORK_ERROR:
					if (mNotificationManager != null
							&& notificationMap.get(downitem.url) != null) {
						mNotificationManager.cancel(downitem.url.hashCode());
						notificationMap.remove(downitem.url);
					}
					if (customDialog != null)
						customDialog.dismiss();
					CommonUtils.showToast(R.string.network_error);
					break;
				// case SHOW_WAITING:
				// customDialog.show();
				// break;
				// case DISMISS_WAITINGL:
				// customDialog.dismiss();
				// break;
				case REQEUSTFAIL:
					CommonUtils.showToast(R.string.downloadfail);

				case SDCARD_NOSPACE:

					if (mNotificationManager != null
							&& notificationMap.get(downitem.url) != null) {
						mNotificationManager.cancel(downitem.url.hashCode());
						notificationMap.remove(downitem.url);
					}
					if (customDialog != null)
						customDialog.dismiss();
					// CommonUtil.showToast(R.string.downloadfail, 0);
					CommonUtils.showToast(R.string.nofreespace);
					break;
				default:
					break;
				}
			}
		}
	};

	public void downApk(String url, String showInfo) {
		L.i("start apk Load", url);

		if (getFreeDiskSpace() == -1) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					CommonUtils.showToast(R.string.sdcard_nouse);
				}
			});
			return;
		}
		L.i("tesetDOn", urlList.toString() + "    " + url);
		if (!urlList.contains(url)) {
			urlList.add(url);
			DownloadItem downloadItem = DownloadItem.getInstence();
			Message message = new Message();
			message.what = DOWNLOAD_UPDATE;
			downloadItem.url = url;
//			downloadItem.type = type;
			downloadItem.itemName = showInfo;
			downloadItem.itemPercent = 0;
			message.obj = downloadItem;
			mHandler.sendMessage(message);
			HttpClient client = new DefaultHttpClient();

			// 请求超时
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					60000);
			InputStream instream = null;
			try {
				HttpGet get = new HttpGet(url);
				HttpResponse response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					// SimpleResponse simpleResponse = AppAplication.sApiClient
					// .doRequest(simpleRequest);
					long lenth = entity.getContentLength();

					instream = entity.getContent();

					if (sdCardIsAvailable()) {
						File dirFile = new File(APK_PATH);

						if (!dirFile.exists()) {
							dirFile.mkdirs();
						}
						File myCaptureFile = new File(APK_PATH + url.hashCode()
								+ ".fonedw");

						if (!myCaptureFile.exists()) {
							myCaptureFile.createNewFile();
						}
						// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
						if (getFreeDiskSpace() * 1024 > lenth) {
							try {
								//
								if (readInputStream(instream, lenth, showInfo,
										url, myCaptureFile)) {
									L.v("download", "downApk", "success");
									client.getConnectionManager().shutdown();
								} else {
									downloadItem.url = url;
									sendDwnloadFileMessage(downloadItem);
									L.v("download", "downApk", "fail");
								}
							} catch (Exception e) {
								//
								L.v("download", "downApk", "shutdown fail fail");
								downloadItem.url = url;
								sendDwnloadFileMessage(downloadItem);

								if (instream != null)
									instream.close();
								client.getConnectionManager().shutdown();
								return;
							}
						} else {
							//
							message = new Message();
							message.what = SDCARD_NOSPACE;
							message.obj = downloadItem;
							downloadItem.url = url;
							mHandler.sendMessage(message);
							urlList.remove(url);
							if (instream != null)
								instream.close();
							client.getConnectionManager().shutdown();
							return;
						}
						// byte[] data = null;
						// try {
						// data =
						// } catch (Exception e) {
						// e.printStackTrace();
						// urlList.remove(url);
						// return;
						// }
						// // new一个文件对象用来保存图片，默认保存当前工程根目录
						//
						// // 创建输出流
						//
						// outStream.write(data);
						// outStream.close();

						// String filename =
						// url.substring(url.lastIndexOf("/"));
						L.i("changName", "to create new file");
						File newFile = new File(myCaptureFile.getParent()
								+ File.separator + url.substring(url.lastIndexOf("/")+1));
						L.i("changName",
								"to create new file2"
										+ newFile.getAbsolutePath());
						if (newFile.exists()) {
							newFile.delete();
						}
						try {
							newFile.createNewFile();
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							downloadItem.url = url;
							sendDwnloadFileMessage(downloadItem);

							if (instream != null)
								instream.close();
							client.getConnectionManager().shutdown();
							return;

						}

						L.i("changName", newFile.getName());
						myCaptureFile.renameTo(newFile);

						downloadItem.itemName = showInfo;
						downloadItem.itemPercent = 0;
						downloadItem.url = url;
						downloadItem.localFilePath = newFile.getAbsolutePath();
						message = new Message();
						message.what = DOWNLOAD_COMPLETE;
						message.obj = downloadItem;
						// message =
						// mHandler.obtainMessage(DOWNLOAD_COMPLETE,downloadItem);
						mHandler.sendMessage(message);
						urlList.remove(url);

					} else {

						message = new Message();
						message.what = SDCARD_NOSPACE;
						message.obj = downloadItem;
						downloadItem.url = url;

						mHandler.sendMessage(message);
						urlList.remove(url);
						if (instream != null)
							try {
								instream.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						client.getConnectionManager().shutdown();
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
				downloadItem.url = url;
				sendDwnloadFileMessage(downloadItem);
				if (instream != null)
					try {
						instream.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				client.getConnectionManager().shutdown();
			}
		}

	}

	public boolean readInputStream(InputStream inStream, long lenth,
			String title, String url, File file) throws Exception {
		// ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		FileOutputStream outStream = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int mDownloadPrecent = 0;
		int read;
		long count = 0;
		int precent = 0;
		DownloadItem downitem = new DownloadItem();
		while ((read = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, read);
			count += read;
			precent = (int) (((double) count / lenth) * 100);
			// 每下载完成5%就通知任务栏进行修改下载进度
			if (precent - mDownloadPrecent >= 5) {
				mDownloadPrecent = precent;
				downitem.itemPercent = precent;
				downitem.itemName = title;
				downitem.url = url;
//				downitem.type = type;
				Message message = new Message();
				message.what = DOWNLOAD_UPDATE;
				message.obj = downitem;
				L.i("loadpercentloadpercent:", precent + "");
				mHandler.sendMessage(message);
			}
		}
		if (precent != 100)
			return false;

		outStream.close();
		inStream.close();
		return true;

		// return outStream.toByteArray();
	}

	// 安装下载后的apk文件
	private void install(File file, Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(intent);

	}

	private Notification getNotifcation(String url) {
		Notification notification = null;
		if (notificationMap.get(url) == null) {
			notification = new Notification();
			notification.icon = android.R.drawable.stat_sys_download;
			// mNotification.tickerText = mContext.getString(R.string.app_name)
			// + "更新";
			notification.when = System.currentTimeMillis();
			notification.defaults = Notification.DEFAULT_LIGHTS;
			Intent intent = new Intent(mContext.getApplicationContext(),
					mContext.getClass());
			PendingIntent contentIntent = PendingIntent.getActivity(mContext,
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			notification.contentIntent = contentIntent;
			notificationMap.put(url, notification);
		} else {
			notification = notificationMap.get(url);
		}

		return notification;
	}

	private void sendDwnloadFileMessage(DownloadItem item) {
		Message message = new Message();
		message.what = DOWNLOAD_FAIL;
		message.obj = item;
		mHandler.sendMessage(message);
		urlList.remove(item.url);
	}

	/**
	 * 检测sdcard是否可用
	 * 
	 * @return true为可用，否则为不可用
	 */
	public boolean sdCardIsAvailable() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return true;
	}

	/**
	 * 计算SD卡的剩余空间
	 * 
	 * @return 返回-1，说明没有安装sd卡
	 */
	public static long getFreeDiskSpace() {
		String status = Environment.getExternalStorageState();
		long freeSpace = 0;
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSize();
				long availableBlocks = stat.getAvailableBlocks();
				freeSpace = availableBlocks * blockSize / 1024;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return -1;
		}
		return (freeSpace);
	}

}
