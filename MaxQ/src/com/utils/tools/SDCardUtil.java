package com.utils.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

/**
 * 关于sdcard的工具类
 * 
 * @author wan
 * @Date 20160808
 * @UpdateLog
 */
public class SDCardUtil {
	/**
	 * 获取设备SD卡是否可用
	 */
	public static boolean isSDCardEnable() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * 获取设备SD卡路径
	 * <p>
	 * 一般是/storage/emulated/0/
	 */
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}
	
	/**
	 * sdcard写入文件
	 * @param fileName
	 * @param message
	 * @return
	 */
	public static boolean writeFileSdcard(String fileName, String message) {
		if (isSDCardEnable()) {
			try {
				FileOutputStream fout = new FileOutputStream(getSDCardPath()+"/"+ fileName);
				byte[] bytes = message.getBytes();
				fout.write(bytes);
				fout.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return false;
		}
		return false;

	}
	/**
	 * 读取sdcard文件
	 * @param fileName
	 * @param message
	 * @return
	 */
	public static String readAsste(Context context, String fileName) {
		String data = "";
		try {
			InputStream in = context.getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			data = EncodingUtils.getString(buffer, "utf-8");
			return data;
		} catch (Exception e) {
			return e.toString();
		}
	}
	/**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = stat.getAvailableBlocks();
            // 获取单个数据块的大小（byte）
            long blockSize = stat.getBlockSize();
            return blockSize * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }
}
