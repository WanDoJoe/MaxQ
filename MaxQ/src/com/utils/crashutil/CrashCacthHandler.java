package com.utils.crashutil;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by sinosoft_wan on 2016/8/18.
 */
public class CrashCacthHandler implements Thread.UncaughtExceptionHandler {
    private static CrashCacthHandler instance;
    private StringBuffer carshBuffer=new StringBuffer();
    private String filePath;
    public static CrashCacthHandler getInstance() {
        if (instance == null) {
                 instance = new CrashCacthHandler();
        }
        return instance;
    }

    public void init(Context ctx) {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    public void init(Context ctx,String filePath){
    	Thread.setDefaultUncaughtExceptionHandler(this);
    	this.filePath=filePath;
    }
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

//        String logPath;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
        	filePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + File.separator
                    + File.separator
                    + "log";

            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                FileWriter fw = new FileWriter(filePath + File.separator
                        + "errorlog.log", true);
                fw.write(new Date() + "\n");
                carshBuffer.append(new Date()+"\n");
                // 错误信息
                // 这里还可以加上当前的系统版本，机型型号 等等信息
                StackTraceElement[] stackTrace = ex.getStackTrace();
                fw.write(ex.getMessage() + "\n");
                carshBuffer.append(ex.getMessage()+"\n");
                for (int i = 0; i < stackTrace.length; i++) {
                    fw.write("file:" + stackTrace[i].getFileName() + " class:"
                            + stackTrace[i].getClassName() + " method:"
                            + stackTrace[i].getMethodName() + " line:"
                            + stackTrace[i].getLineNumber() + "\n");
                    carshBuffer.append("file:" + stackTrace[i].getFileName() + " class:"
                            + stackTrace[i].getClassName() + " method:"
                            + stackTrace[i].getMethodName() + " line:"
                            + stackTrace[i].getLineNumber() + "\n");
                }
                carshBuffer.append("\n");
                fw.write("\n");
                fw.close();
                // 上传错误信息到服务器
                // uploadToServer(carshBuffer);
            } catch (IOException e) {
                Log.e("crash handler", "load file failed...", e.getCause());
            }
        }
        ex.printStackTrace();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
