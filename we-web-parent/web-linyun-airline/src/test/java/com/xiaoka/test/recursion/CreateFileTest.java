/**
 * CreateFileTest.java
 * com.xiaoka.test.recursion
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.test.recursion;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.linyun.airline.common.util.ZipUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2017年3月3日 	 
 */
public class CreateFileTest {

	public static void main(String[] args) throws IOException {

		List<File> fileList = Lists.newArrayList();

		File f1 = new File("http://f12.baidu.com/it/u=661466718,1953143112&fm=72");
		File f2 = new File("http://img2.imgtn.bdimg.com/it/u=579355593,1946720831&fm=23&gp=0.jpg");

		f1.createNewFile();
		f2.createNewFile();

		//放入list结合 
		fileList.add(f1);
		fileList.add(f2);

		String fileName = "F:\\test.zip";
		//打包
		ZipUtil.zipFiles(fileList, new File(fileName));

		System.out.println(f1.isDirectory());
		System.out.println(f1.getName());
		System.out.println(f1);
		System.out.println(f2);
		System.out.println("看看：" + fileList);
	}

}
