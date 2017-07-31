/**
 * MakeFile.java
 * com.we.generator.util
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.we.generator.util;

import java.io.File;

/**
 * 根据目录创建文件、文件夹
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年7月31日 	 
 */
public class MakeFile {

	//根据文件目录创建
	public static void makeFile(String filePath) {
		File file = new File(filePath);
		file.mkdirs();
	}
}
