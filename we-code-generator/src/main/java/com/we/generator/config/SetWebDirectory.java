/**
 * SetWebDirectory.java
 * com.we.generator.config
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.we.generator.config;

import com.we.generator.util.MakeFile;

/**
 * 根据配置文件，创建web项目的基本目录
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class SetWebDirectory {

	//创建web项目 基本目录
	public static void makeFiles(String basePkg) {

		String webOutput = LoadConfigWeb.WEB_OUTPUT;

		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
		javaOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + javaOutput;
		MakeFile.makeFile(javaOutput);

		//java Resources
		String jResOutput = LoadConfigWeb.JAVA_RES_OUTPUT;
		jResOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + jResOutput;
		MakeFile.makeFile(jResOutput);

		//test
		String testJavaOut = LoadConfigWeb.TEST_JAVA_OUTPUT;
		testJavaOut = webOutput + "/" + basePkg.replace(".", "-") + "/" + testJavaOut;
		MakeFile.makeFile(testJavaOut);

		//test Resources
		String testResOut = LoadConfigWeb.TEST_RES_OUTPUT;
		testResOut = webOutput + "/" + basePkg.replace(".", "-") + "/" + testResOut;
		MakeFile.makeFile(testResOut);

		//target
		String targetOutput = LoadConfigWeb.TARGET_OUTPUT;
		targetOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + targetOutput;
		MakeFile.makeFile(targetOutput);
	}
}
