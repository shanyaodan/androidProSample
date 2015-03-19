package com.example.sampleandroid.exception;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.sampleandroid.R;

public class AppException extends Exception {

	private final static boolean Debug = false;	// 是否保存错误日志

	/** 定义异常类型 */
	public final static byte TYPE_NETWORK = 0x01;
	public final static byte TYPE_SOCKET = 0x02;
	public final static byte TYPE_HTTP_CODE = 0x03;
	public final static byte TYPE_HTTP_ERROR = 0x04;
	public final static byte TYPE_XML = 0x05;
	public final static byte TYPE_IO = 0x06;
	public final static byte TYPE_RUN = 0x07;
	public final static byte TYPE_JSON = 0x08;

	private int type;
	private String specialDescription;

	/**
	 * @param excp	异常
	 * @param ctx
	 * @param type	异常类型
	 * @param specialDescription	特殊异常描述
	 */
	private AppException(Exception excp, Context ctx, byte type, String specialDescription) {
		super(excp);
		this.type = type;
		this.specialDescription = specialDescription;
		if (Debug) {
			ExceptionLog.getInstance().saveCatchInfo2File(excp);
		}
	}

	public int getType() {
		return type;
	}

	public String getSpecialDescription() {
		return specialDescription;
	}

	/**
	 * 提示友好的错误信息
	 * 
	 * @param ctx
	 */
	public void makeToast(Context ctx) {
		if (TextUtils.isEmpty(specialDescription)) {
			switch (this.getType()) {
			case TYPE_HTTP_CODE:
				Toast.makeText(ctx, R.string.http_status_code_error,
						Toast.LENGTH_SHORT).show();
				break;
			case TYPE_HTTP_ERROR:
				Toast.makeText(ctx, R.string.http_exception_error,
						Toast.LENGTH_SHORT).show();
				break;
			case TYPE_SOCKET:
				Toast.makeText(ctx, R.string.socket_exception_error,
						Toast.LENGTH_SHORT).show();
				break;
			case TYPE_NETWORK:
				Toast.makeText(ctx, R.string.network_not_connected,
						Toast.LENGTH_SHORT).show();
				break;
			case TYPE_XML:
				Toast.makeText(ctx, R.string.xml_parser_failed,
						Toast.LENGTH_SHORT).show();
				break;
			case TYPE_JSON:
				Toast.makeText(ctx, R.string.xml_parser_failed,
						Toast.LENGTH_SHORT).show();
				break;
			case TYPE_IO:
				Toast.makeText(ctx, R.string.io_exception_error,
						Toast.LENGTH_SHORT).show();
				break;
			case TYPE_RUN:
				Toast.makeText(ctx, R.string.app_run_code_error,
						Toast.LENGTH_SHORT).show();
				break;
			}
		} else {
			Toast.makeText(ctx, specialDescription, Toast.LENGTH_SHORT).show();
		}
	}

	public static AppException http(Context ctx, String description) {
		return new AppException(null, ctx, TYPE_HTTP_CODE, description);
	}

	public static AppException http(Exception e, Context ctx, String description) {
		return new AppException(e, ctx, TYPE_HTTP_ERROR, description);
	}

	public static AppException socket(Exception e, Context ctx, String description) {
		return new AppException(e, ctx, TYPE_SOCKET, description);
	}

	public static AppException io(Exception e, Context ctx, String description) {
		if (e instanceof UnknownHostException || e instanceof ConnectException) {
			return new AppException(e, ctx, TYPE_NETWORK, description);
		} else if (e instanceof IOException) {
			return new AppException(e, ctx, TYPE_IO, description);
		}
		return run(e, ctx, description);
	}

	public static AppException xml(Exception e, Context ctx, String description) {
		return new AppException(e, ctx, TYPE_XML, description);
	}

	public static AppException json(Exception e, Context ctx, String description) {
		return new AppException(e, ctx, TYPE_JSON, description);
	}

	public static AppException network(Exception e, Context ctx, String description) {
		if (e instanceof UnknownHostException || e instanceof ConnectException) {
			return new AppException(e, ctx, TYPE_NETWORK, description);
		}
		// else if(e instanceof HttpException){
		// return http(e, ctx);
		// }
		else if (e instanceof SocketException) {
			return socket(e, ctx, description);
		}
		return http(e, ctx, description);
	}

	public static AppException run(Exception e, Context ctx, String description) {
		return new AppException(e, ctx, TYPE_RUN, description);
	}

}
