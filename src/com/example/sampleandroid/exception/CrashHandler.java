package com.example.sampleandroid.exception;

import java.lang.Thread.UncaughtExceptionHandler;

import com.example.sampleandroid.activity.SplashActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Toast;

/**   
 * 	UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.  
 *  需要在Application中注册，为了要在程序启动器就监控整个程序。 
 */      
public class CrashHandler implements UncaughtExceptionHandler {      
    public static final String TAG = "CrashHandler";     
    
    /** 系统默认的UncaughtException处理类 */       
    private Thread.UncaughtExceptionHandler mDefaultHandler; 
    
    /** CrashHandler实例 */
    private static CrashHandler instance;  
    
   /** 程序的Context对象 */      
    private Context mContext;      
    
	/**
	 * 保证只有一个CrashHandler实例
	 */     
    private CrashHandler() {}      
    
	/**
	 * 获取CrashHandler实例 ,单例模式
	 */    
    public static CrashHandler getInstance() {      
        if(null == instance) {
        	synchronized (CrashHandler.class) {
        		if(null == instance) {
        			instance = new CrashHandler();     
        		}
			}
        }
        return instance;      
    }      
      
    /**   
     * 初始化   
     */      
    public void init(Context context) {      
        mContext = context;      
        //获取系统默认的UncaughtException处理器      
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();      
        //设置该CrashHandler为程序的默认处理器      
        Thread.setDefaultUncaughtExceptionHandler(this);      
    }      
      
    /**   
     * 当UncaughtException发生时会转入该函数来处理   
     */      
    @Override      
    public void uncaughtException(Thread thread, Throwable ex) {      
        if (!handleException(ex) && mDefaultHandler != null) {      
            //如果用户没有处理则让系统默认的异常处理器来处理      
            mDefaultHandler.uncaughtException(thread, ex);      
        } else {	// 一般走else      
        	SystemClock.sleep(2000);
        	Intent intent = new Intent(mContext, SplashActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent); 
			
            //退出程序      
            android.os.Process.killProcess(android.os.Process.myPid());      
            System.exit(1);      
        }      
    }      
      
    /**   
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.   
     * @param ex   
     * @return true:如果处理了该异常信息;否则返回false.   
     */      
    private boolean handleException(Throwable ex) {      
        if (ex == null) {      
            return false;      
        }      
        ExceptionLog instance = ExceptionLog.getInstance();
        //收集设备参数信息       
        instance.collectDeviceInfo(mContext); 
        //保存日志文件       
        instance.saveCatchInfo2File(ex); 
        //使用Toast来显示异常信息      
        new Thread() {	// 温馨提示      
            @Override      
            public void run() {      
                Looper.prepare();      
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();      
                Looper.loop();      
            }      
        }.start();      
        return true;      
    }      
          
}     