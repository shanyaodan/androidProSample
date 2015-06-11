package com.example.sampleandroid.util;

import java.io.File;

import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

public interface Constants {

	// 日期格式
	/** 中文格式时间 */
	String FORMAT_C = "yyyy年 MM月 dd日";
	/** 英文格式时间 */
	String FORMAT_E = "yyyy-M-d HH:mm:ss";
	/** 英文简洁格式时间 */
	String FORMAT_E_SIMPLE = "yyyy-M-d";
	String FORMAT_E_SIMPLE_1 = "yyyy.M.d";
	/** 外部存储路径 */
	String EXTERNAL_STORAGE_DIR = Environment.getExternalStorageDirectory()
			.toString();
	/** SD卡根路径 */
	String ROOT_PATH = EXTERNAL_STORAGE_DIR + File.separator + "zhengben";
	/** crash文件目录 */
	String CRASH_FILE_DIR = ROOT_PATH + File.separator + "crash";
	/** crash file name */
	String CRASH_FILE_NAME = "crash.log";
	/** crash文件路径 */
	String CRASH_FILE_PATH = CRASH_FILE_DIR + File.separator + CRASH_FILE_NAME;
	/** apk文件目录 */
	String APK_FILE_DIR = ROOT_PATH + File.separator + "apk";
	/** update文件目录 */
	String UPDATE_FILE_DIR = ROOT_PATH + File.separator + "update";
	/** update文件名 */
	String UPDATE_FILE_NAME = "update.p";
	/** update文件路径 */
	String UPDATE_FILE_PATH = UPDATE_FILE_DIR + File.separator
			+ UPDATE_FILE_NAME;

	CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	int DISK_IMAGECACHE_QUALITY = 100; // PNG is lossless so quality is ignored
										// but must be provided

	String DB_SECRET = "sampleandroid";

}
