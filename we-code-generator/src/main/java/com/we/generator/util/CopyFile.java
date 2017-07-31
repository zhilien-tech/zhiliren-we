/**
 * CopyFile.java
 * com.we.generator.util
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.we.generator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 复制文件、文件夹到指定目录
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年7月31日 	 
 */
public class CopyFile {

	//根据文件目录拷贝
	public static void copyFile(String filePath, String toFilePath) {
		File file = new File(filePath);
		File toFile = new File(toFilePath);
		try {
			copy(file, toFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//拷贝文件到固定目录下
	public static void copy(File file, File toFile) throws Exception {
		byte[] b = new byte[1024];
		int a;
		FileInputStream fis;
		FileOutputStream fos;
		if (file.isDirectory()) {
			String filepath = file.getAbsolutePath();
			filepath = filepath.replaceAll("\\\\", "/");
			String toFilepath = toFile.getAbsolutePath();
			toFilepath = toFilepath.replaceAll("\\\\", "/");
			int lastIndexOf = filepath.lastIndexOf("/");
			toFilepath = toFilepath + filepath.substring(lastIndexOf, filepath.length());
			File copy = new File(toFilepath);
			//复制文件夹  
			if (!copy.exists()) {
				copy.mkdir();
			}
			//遍历文件夹  
			for (File f : file.listFiles()) {
				copy(f, copy);
			}
		} else {
			if (toFile.isDirectory()) {
				String filepath = file.getAbsolutePath();
				filepath = filepath.replaceAll("\\\\", "/");
				String toFilepath = toFile.getAbsolutePath();
				toFilepath = toFilepath.replaceAll("\\\\", "/");
				int lastIndexOf = filepath.lastIndexOf("/");
				toFilepath = toFilepath + filepath.substring(lastIndexOf, filepath.length());

				//写文件  
				File newFile = new File(toFilepath);
				fis = new FileInputStream(file);
				fos = new FileOutputStream(newFile);
				while ((a = fis.read(b)) != -1) {
					fos.write(b, 0, a);
				}
			} else {
				fis = new FileInputStream(file);
				fos = new FileOutputStream(toFile);
				while ((a = fis.read(b)) != -1) {
					fos.write(b, 0, a);
				}
			}
		}
	}
}
