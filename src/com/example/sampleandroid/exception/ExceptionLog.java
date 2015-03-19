package com.example.sampleandroid.exception;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.example.sampleandroid.util.Constants;
import com.example.sampleandroid.util.L;

public class ExceptionLog {
	private final String TAG = getClass().getSimpleName();

	private static ExceptionLog exceptionLog;

	/** 用来存储设备信息和异常信息 */
	private Map<String, String> infos = new HashMap<String, String>();

	/** 用于格式化日期,作为日志文件名的一部分 */
	private DateFormat formatter = new SimpleDateFormat(Constants.FORMAT_E);

	private ExceptionLog() {
	}

	/**
	 * 单例
	 * 
	 * @return
	 */
	public static ExceptionLog getInstance() {
		if (null == exceptionLog) {
			synchronized (ExceptionLog.class) {
				if (null == exceptionLog) {
					exceptionLog = new ExceptionLog();
				}
			}
		}
		return exceptionLog;
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			// 获取VERSION_NAME, VERSION_CODE信息
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("VERSION_NAME", versionName);
				infos.put("VERSION_CODE", versionCode);
			}
			// 获取Build字段
			Field[] fields = Build.class.getDeclaredFields();
			String fieldName = "";
			for (Field field : fields) {
				field.setAccessible(true);
				fieldName = field.getName();
				infos.put(fieldName, field.get(null).toString());
				L.i(TAG, fieldName + " : " + field.get(null));
			}
		} catch (Exception e) {
			L.e(TAG, "an error occured while collectDeviceInfo...",
					e.getMessage());
		}
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	public void saveCatchInfo2File(Throwable ex) {
		StringBuffer sb = null;
		PrintWriter pw = null;
		FileOutputStream fos = null;
		try {
			sb = new StringBuffer();

			// 日志时间记录
			String time = formatter.format(new Date());
			sb.append("\n\n" + time + "\n");

			// 遍历设备信息
			for (Map.Entry<String, String> entry : infos.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				sb.append(key + " = " + value + "\n");
			}

			// 遍历异常信息
			Writer writer = new StringWriter();
			pw = new PrintWriter(writer);
			ex.printStackTrace(pw);
			Throwable cause = ex.getCause();
			while (cause != null) {
				cause.printStackTrace(pw);
			}
			String result = writer.toString();
			sb.append(result);

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File dir = new File(Constants.CRASH_FILE_DIR);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				fos = new FileOutputStream(Constants.CRASH_FILE_PATH);
				fos.write(sb.toString().getBytes());
			}
		} catch (Exception e) {
			L.e(TAG, "an error occured while writing file...", e.getMessage());
		} finally {
			sb.setLength(0);
			try {
				if (null != infos) {
					infos.clear();
				}
				if (null != pw) {
					pw.close();
				}
				if (null != fos) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将捕获的导致崩溃的错误信息发送给开发人员 目前只将log日志保存在sdcard 和输出到LogCat中，并未发送给后台。
	 */
	private void sendLog2Server(Context ctx) {
		if (!new File(Constants.CRASH_FILE_PATH).exists()) {
			return;
		}

		FileInputStream fis = null;
		BufferedReader reader = null;

		try {
			String s = null;
			fis = new FileInputStream(Constants.CRASH_FILE_PATH);
			reader = new BufferedReader(new InputStreamReader(fis, "GBK"));
			while (true) {
				s = reader.readLine();
				if (s == null)
					break;
				// 由于目前尚未确定以何种方式发送，所以先打出log日志。
				L.i("info", s.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 关闭流
			try {
				if (null != fis) {
					fis.close();
				}
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
