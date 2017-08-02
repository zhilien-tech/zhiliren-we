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
	public static void makeFiles() {

		String webOutput = LoadConfigWeb.WEB_OUTPUT;

		//java
		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
		javaOutput = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + javaOutput;
		MakeFile.makeFile(javaOutput);

		//java Resources
		String jResOutput = LoadConfigWeb.JAVA_RES_OUTPUT;
		jResOutput = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + jResOutput;
		MakeFile.makeFile(jResOutput);

		//webapp
		String jWebappOutput = LoadConfigWeb.JSP_OUTPUT;
		jWebappOutput = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + jWebappOutput;
		MakeFile.makeFile(jWebappOutput);

		//test
		String testJavaOut = LoadConfigWeb.TEST_JAVA_OUTPUT;
		testJavaOut = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + testJavaOut;
		MakeFile.makeFile(testJavaOut);

		//test Resources
		String testResOut = LoadConfigWeb.TEST_RES_OUTPUT;
		testResOut = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + testResOut;
		MakeFile.makeFile(testResOut);

		//target
		String targetOutput = LoadConfigWeb.TARGET_OUTPUT;
		targetOutput = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + targetOutput;
		MakeFile.makeFile(targetOutput);
	}
}
