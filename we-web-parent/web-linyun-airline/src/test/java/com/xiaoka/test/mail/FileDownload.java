/**
 * FileDownload.java
 * com.xiaoka.test.mail
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.test.mail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.linyun.airline.common.util.ZipUtil;

/**
 * TODO测试文件打包下载
 * @author   崔建斌
 * @Date	 2017年3月2日 	 
 */
public class FileDownload {

	public static void downLoadZipFile() throws IOException {
		List<File> fileList = Lists.newArrayList();
		fileList.add(new File("F:\\a.txt"));
		fileList.add(new File("F:\\b.txt"));

		String zipName = "F:\\myfile.zip";
		ZipUtil.zipFiles(fileList, new File(zipName));

	}

	public static void main(String[] args) throws IOException {
		downLoadZipFile();
		System.out.println("打包成功！");
	}
}
