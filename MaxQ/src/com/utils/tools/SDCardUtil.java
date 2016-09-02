package com.utils.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
//			data = EncodingUtils.getString(buffer, "utf-8");
			data=new String(buffer, "utf-8");
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
    /**
     * 检查路径文件或者文件夹是否存在
     */
    public static boolean isDirectory(String path){
    	File file = new File(path);
    	  if (file.exists()) {
    	   return false;
    	  }
    	  return true;
    }
    /**
     * 创建文件
     */
    public static void createFile(String path){
    	if(!isDirectory(path)){
    		File file=new File(path); 
    		file.mkdir(); 
    	}
    }
    /**
     * 创建文件夹
     */
    public static void createDirectory(String path){
    	if(!isDirectory(path)){
    		File file=new File(path); 
    		file.mkdirs(); 
    	}
    }
    /**
     * 根据url得到文件名
     * @param url
     * @return
     */
    public static String getFileName(String url) {
        String filename = "";
        boolean isok = false;
        // 从UrlConnection中获取文件名称
        try {
            URL myURL = new URL(url);

            URLConnection conn = myURL.openConnection();
            if (conn == null) {
                return null;
            }
            Map<String, List<String>> hf = conn.getHeaderFields();
            if (hf == null) {
                return null;
            }
            Set<String> key = hf.keySet();
            if (key == null) {
                return null;
            }
            // Log.i("test", "getContentType:" + conn.getContentType() + ",Url:"
            // + conn.getURL().toString());
            for (String skey : key) {
                List<String> values = hf.get(skey);
                for (String value : values) {
                    String result;
                    try {
                        result = new String(value.getBytes("ISO-8859-1"), "GBK");
                        int location = result.indexOf("filename");
                        if (location >= 0) {
                            result = result.substring(location
                                    + "filename".length());
                            filename = result
                                    .substring(result.indexOf("=") + 1);
                            isok = true;
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }// ISO-8859-1 UTF-8 gb2312
                }
                if (isok) {
                    break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 从路径中获取
        if (filename == null || "".equals(filename)) {
            filename = url.substring(url.lastIndexOf("/") + 1);
        }
        return filename;

    }

}
