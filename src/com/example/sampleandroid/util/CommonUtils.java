package com.example.sampleandroid.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleandroid.App;
import com.example.sampleandroid.R;
import com.example.sampleandroid.base.BaseActivity;
import com.example.sampleandroid.widget.CommAlertDialog;
import com.example.sampleandroid.widget.DialogButtonsListener;

@SuppressLint("SimpleDateFormat")
public class CommonUtils {

	private static final String TAG = "CommonUtils";
	private static Toast mToast;
	public static String g_ver_string = null;

	/** 根据手机的分辨率从dp 的单位 转成为px(像素) */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/** 根据手机的分辨率从px(像素) 的单位 转成为dp */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int getDementions(int value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				value, App.getContext().getResources().getDisplayMetrics());
	}

	public static int getTextViewDementions(int value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				value, App.getContext().getResources().getDisplayMetrics());
	}

	public static void skipActivity(Context context,
			Class<? extends BaseActivity> cls) {
		context.startActivity(new Intent(context, cls));
	}

	public static void skipActivity(Context context,
			Class<? extends BaseActivity> cls, Bundle bundle) {
		context.startActivity(new Intent(context, cls).putExtras(bundle));
	}

	public static String getStringById(int id) {
		return App.getContext().getResources().getString(id);
	}

	public static String getFormatTime(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date());
	}

	public static String getFormatTime(String format, String data) {
		if (TextUtils.isEmpty(data)) {
			return "";
		}
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(Long.parseLong(data));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public static String ConfigureFormatURL(Context context, String url) {
		if (null == context || null == url) {
			return "";
		}
		StringBuffer output = new StringBuffer(url);
		if (!url.endsWith("&") && !url.endsWith("?")) {
			if (url.contains("?"))
				output.append("&");
			else
				output.append("?");
		}
		output.append("cp=");
		output.append(encrypt("23E5BBF9&9#02E5B", initRawCipher(context)));
		output.append("&nt=" + NetWorkUtils.getNetworkType(context));
		L.v(TAG, "ConfigureFormatURL =", output.toString());
		return output.toString();
	}

	public static String initRawCipher(Context context) {
		StringBuffer sb = new StringBuffer();
		sb.append("cv=" + configureGetVersion(context));
		sb.append("&uid=" + configureGetUID(context));
		sb.append("&imei=" + configureGetIMEI(context));
		sb.append("&imsi=" + configureGetIMSI(context));
		sb.append("&ua=" + configureGetUa());
		sb.append("&pfv=" + configureGetPlatformVersion());
		sb.append("&vc=" + configureGetVersionCode(context));
		L.v(TAG, "initRawCipher = ", sb.toString());
		return sb.toString();
	}

	public static String configureGetUID(Context context) {
		return DeviceUuidFactory.getInstance().getUuid(context);
	}

	public static String configureGetIMEI(Context context) {
		if (null == context) {
			return "";
		}
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		if (imei == null)
			imei = "";
		return imei;
	}

	public static String configureGetIMSI(Context context) {
		if (null == context) {
			return "";
		}
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = tm.getSubscriberId();
		if (imsi == null)
			imsi = "";
		return imsi;
	}

	/**
	 * 1.2.3.4.5.6.7 1 产品id 2 主版本号 3 子版本号 4 伪版本号 5 标识开发版 发布版 6 主渠道 7 子渠道
	 */
	public static String configureGetVersion(Context context) {
		String versionName = "1.2.3.4.5.6.7";
		if (null == context) {
			return versionName;
		}
		if (null == g_ver_string) {
			try {
				AssetManager am = context.getAssets();
				DataInputStream dis = new DataInputStream(
						am.open("version.txt"));
				byte[] buffer = new byte[dis.available()];
				dis.readFully(buffer);
				versionName = new String(buffer, "utf-8");
				dis.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			g_ver_string = versionName;
		}
		return g_ver_string;
	}

	public static String configureGetPlatformVersion() {
		return new StringBuilder().append("android_")
				.append(android.os.Build.VERSION.SDK_INT).toString();
	}

	public static String configureGetUa() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static int configureGetVersionCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			int version = info.versionCode;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	/* AES encrypt | decrypt_ */
	public static String encrypt(String key, String cleartext) {
		try {
			byte[] result = encrypt(key.getBytes(), cleartext.getBytes());
			byte[] data = Base64.encode(result, Base64.URL_SAFE
					| Base64.NO_WRAP);
			String str = new String(data);
			return URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			L.v(TAG, "encrypt", e.getMessage());
		}
		return "";
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static void setTextView(TextView textView, String content) {
		if (!TextUtils.isEmpty(content)) {
			textView.setText(content);
		}
	}

	public static void showToast(String text) {
		if (null == mToast) {
			mToast = Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT);
		} else {
			mToast.setDuration(Toast.LENGTH_SHORT);
			mToast.setText(text);
		}
		mToast.show();

	}

	public static void showToast(int id) {
		showToast(App.getContext().getResources().getString(id));
	}

	public static boolean checkNetState(boolean showtoast) {
		int type = NetWorkUtils.getNetType(App.getContext());
		if (type == 0) {
			if (showtoast) {
				showToast(R.string.network_error);
			}
			return false;
		}
		return true;
	}

	public static boolean checkMobile(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(17[0,6,7,8]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		L.v(TAG, "isMobileNO", "m.matches(): " + m.matches());
		return m.matches();
	}

	public static void showErrorToast(String errorcode) {

	}

	public static String Md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			return buf.toString();// 32位的加密

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static double[] getBaiDuPos(double[] gaodePos) {

		double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
		if (null == gaodePos || gaodePos.length < 2)
			return null;
		double[] baidupos = new double[2];
		double x = gaodePos[0];// PoiLocation.longitude;
		double y = gaodePos[1];// PoiLocation.latitude;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
		baidupos[0] = z * Math.cos(theta) + 0.0065;
		baidupos[1] = z * Math.sin(theta) + 0.006;

		return baidupos;
	}

	public static double[] getGaoDePos(double[] baiDu) {
		double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
		double x = baiDu[0] - 0.0065, y = baiDu[1] - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
		double[] gaode = new double[2];
		gaode[0] = z * Math.cos(theta);
		gaode[1] = z * Math.sin(theta);
		return gaode;
	}

	/**
	 * 显示修改网络界面
	 */
	public static void showChangeNetPage(final Context context) {

		final CommAlertDialog dialog = new CommAlertDialog(context);
		dialog.setContentInfo(R.string.opennetsetting);
		dialog.setTitleTv(R.string.opennetsetting_title);
		dialog.setButtonsListener(new DialogButtonsListener() {

			@Override
			public void onOKClick() {
				// TODO Auto-generated method stub
				dialog.dismiss();
				context.startActivity(new Intent(
						android.provider.Settings.ACTION_WIRELESS_SETTINGS));
			}

			@Override
			public void onCancleClick() {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
	}

}
