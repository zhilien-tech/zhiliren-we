/**
 * GetExcelInfo.java
 * com.we.generator.config
*/

package com.we.generator.config;

import java.io.InputStream;

/**
 * 获取Excel文件,输入流
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GetExcelInfo {

	public static InputStream getExcelIns() {
		//读取excel功能模块信息
		InputStream ins = ClassLoader.getSystemResourceAsStream("code-generator/module.xlsx");
		return ins;
	}
}
