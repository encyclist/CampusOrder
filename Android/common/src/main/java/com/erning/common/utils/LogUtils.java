package com.erning.common.utils;

import android.util.Log;

import com.erning.common.BuildConfig;

/**
 * Created by abs on 2018/1/19.
 */

public class LogUtils {
    private static final String SEPARATOR= ",";

    public static void d(String TAG,String s){
        if(BuildConfig.DEBUG)
            Log.d(TAG,s);
    }
    public static void df(String format, Object... args) {
        if (BuildConfig.DEBUG)
            Log.d("LogUtils", String.format(format, args));
    }
    public static void d(String message) {
        if (BuildConfig.DEBUG){
            StackTraceElement stackTraceElement  = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            Log.d(tag, message+"  ----" + getLogInfo(stackTraceElement));
        }
    }
    public static void i(String TAG,String s){
        Log.i(TAG,s);
    }
    public static void w(String TAG,String s){
        Log.w(TAG,s);
    }
    public static void e(String TAG,String s){
        Log.e(TAG,s);
    }
    public static void v(String TAG,String s){
        Log.v(TAG,s);
    }

    /**
     * 获取默认的TAG名称.
     * 比如在MainActivity.java中调用了日志输出.
     * 则TAG为MainActivity
     */
    private static String getDefaultTag(StackTraceElement stackTraceElement){
        String fileName = stackTraceElement.getFileName();
        String[] stringArray = fileName.split("\\.");
        return "===="+stringArray[0];
    }

    /**
     * 输出日志所包含的信息
     */
    private static String getLogInfo(StackTraceElement stackTraceElement){
        StringBuilder logInfoStringBuilder = new StringBuilder();
        // 获取线程名
        String threadName = Thread.currentThread().getName();
        // 获取线程ID
        long threadID  = Thread.currentThread().getId();
        // 获取文件名.即xxx.java
        String fileName = stackTraceElement.getFileName();
        // 获取类名.即包名+类名
        String className = stackTraceElement.getClassName();
        // 获取方法名称
        String methodName = stackTraceElement.getMethodName();
        // 获取生日输出行数
        int lineNumber = stackTraceElement.getLineNumber();

        logInfoStringBuilder.append("[ ");
        logInfoStringBuilder.append("fileName=").append(fileName).append(SEPARATOR);
        logInfoStringBuilder.append("methodName=").append(methodName).append(SEPARATOR);
        logInfoStringBuilder.append("lineNumber=").append(lineNumber).append(SEPARATOR);
        logInfoStringBuilder.append("threadID=").append(threadID).append(SEPARATOR);
        logInfoStringBuilder.append("threadName=").append(threadName).append(SEPARATOR);
        logInfoStringBuilder.append("className=").append(className);

        logInfoStringBuilder.append(" ] ");
        return logInfoStringBuilder.toString();
    }
}
