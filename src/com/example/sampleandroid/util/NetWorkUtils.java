 package com.example.sampleandroid.util;
 
 import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
 
public class NetWorkUtils {
	private static final String TAG = "NetMode";
	private static ConnectivityManager connManager;

	public static String getNetworkType(Context mContext) {
		if (checkWifiNetStatus(mContext)) {
			return "wifi";
		}
		if (checkMobileNetStatus(mContext)) {
			ConnectivityManager connectivityManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mobNetInfo = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mobNetInfo != null) {
				L.v(TAG, "getNetworkType : ", mobNetInfo.getExtraInfo());
				String netType = mobNetInfo.getExtraInfo().toLowerCase();
				if (null != netType && netType.contains("cmwap")) {
					return "cmwap";
				}
				if (null != netType && netType.contains("cmnet")) {
					return "cmnet";
				}
				if (null != netType && netType.contains("3gwap")) {
					return "3gwap";
				}
				if (null != netType && netType.contains("3gnet")) {
					return "3gnet";
				}
				if (null != netType && netType.contains("uniwap")) {
					return "uniwap";
				}
				if (null != netType && netType.contains("uninet")) {
					return "uninet";
				}
				if (null != netType && netType.contains("ctwap")) {
					return "ctwap";
				}
				if (null != netType && netType.contains("ctnet")) {
					return "ctnet";
				}
			}
		}
		return "unknown";
	}

	public static boolean checkWifiNetStatus(Context mContext) {
		boolean success = false;
		if (connManager == null) {
			connManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		if (connManager != null) {
			NetworkInfo ni = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (ni != null) {
				NetworkInfo.State state = ni.getState();
				if (NetworkInfo.State.CONNECTED == state) {
					success = true;
				}
			}
		}
		return success;
	}

	public static boolean checkMobileNetStatus(Context mContext) {
		boolean success = false;
		if (connManager == null) {
			connManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		if (connManager != null) {
			NetworkInfo ni = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (ni != null) {
				NetworkInfo.State state = ni.getState();
				if (NetworkInfo.State.CONNECTED == state) {
					success = true;
				}
			}
		}
		return success;
	}

	public static int getNetType(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);// 获取系统的连接服务
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();// 获取网络的连接情况
		if (null != activeNetInfo) {
			L.v("NetMode", "getNetType : ", activeNetInfo.toString());
			if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				// 判断WIFI网
				return 1;
			} else if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				TelephonyManager mTelephonyManager = (TelephonyManager) mContext
						.getSystemService(Context.TELEPHONY_SERVICE);
				int type = mTelephonyManager.getNetworkType();
				if (type == TelephonyManager.NETWORK_TYPE_UNKNOWN
						|| type == TelephonyManager.NETWORK_TYPE_GPRS
						|| type == TelephonyManager.NETWORK_TYPE_EDGE) {
					// 判断gprs网
					return 3;
				} else {
					// 判断3g网
					return 2;
				}
			}
		}
		return 0;
	}
}