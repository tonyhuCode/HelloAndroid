package com.scfir.ispp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class AdUtil {
	private final static String IMG_NAME = "simg.jar";
	/**
	 * 解压sdk所用到的图片压缩文件(assets/cp_jar/simg.jar)
	 * 存储位置为data/data/packagename/imgfile目录
	 */
	static void UnZipFolder(Context context) {
		File zipfile = context.getDir("imgfile", Activity.MODE_PRIVATE);//data/data/packagename/imgfile

		InputStream stream = null;
		ZipInputStream inZip = null;
		try {
			String outPathString = zipfile.getAbsolutePath();//data/data/packagename/imgfile
			
			stream = context.getAssets().open("cp_jar/" + IMG_NAME);//将Assets文件夹下面的压缩包，转换成 流
			
			inZip = new ZipInputStream(stream);
			
			ZipEntry zipEntry;
			String szName = "";
			
			while ((zipEntry = inZip.getNextEntry()) != null) {
				szName = zipEntry.getName();//szName放置在压缩文件中图片的名称
				
				if (zipEntry.isDirectory()) {
					szName = szName.substring(0, szName.length() - 1);
					//跨平台(windows系统和linux)
					File folder = new File(outPathString + File.separator
							+ szName);
					folder.mkdirs();
				} else {
					File file = new File(outPathString + File.separator
							+ szName);
					file.createNewFile();
					FileOutputStream out = new FileOutputStream(file);
					int len;
					byte[] buffer = new byte[1024];
					while ((len = inZip.read(buffer)) != -1) {
						out.write(buffer, 0, len);
						out.flush();
					}
					out.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inZip != null) {
				try {
					inZip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 读取当前软件myimgs目录下解压出来的对应图片
	 * 
	 * @param context
	 * @param fileName
	 *            图片名称
	 * @return
	 */
	public static Drawable readDrawable(Context context, String fileName) {
		File zipfile = context.getDir("imgfile", Activity.MODE_PRIVATE);//data/data/package/imgfile
		File imgFile = new File(zipfile, fileName);//data/data/package/imgfile/d_default_bg
		if (!imgFile.exists()) {
			Log.i("log", "readDrawable no exists so UnZipFolder() ");
			UnZipFolder(context);
		}
		String pathName = imgFile.getAbsolutePath();//data/data/packagename/imgfile/d_default_bg
		
		return Drawable.createFromPath(pathName);//通过当前图片的路径，返回一个bitmap对象
	}
}
