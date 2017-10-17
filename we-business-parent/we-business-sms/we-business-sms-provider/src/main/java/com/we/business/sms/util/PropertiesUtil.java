/**
 * PropertiesUtil.java
 * com.we.business.sms.util
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.we.business.sms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.testng.collections.Maps;

/**
 * 属性文件工具类
 * <p>
 *
 * @author   朱晓川
 * @Date	 2017年10月17日 	 
 */
public class PropertiesUtil {

	private static final String FILE_NAME = "/sms_config.properties";

	private static Properties props;
	private static URI uri;

	static {
		readProperties(FILE_NAME);
	}

	private static void readProperties(String fileName) {
		try {
			props = new Properties();
			InputStream fis = PropertiesUtil.class.getResourceAsStream(fileName);
			props.load(fis);
			uri = PropertiesUtil.class.getResource(fileName).toURI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**   
	 * 获取某个属性   
	 */
	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	/**   
	 * 获取所有属性，返回一个map,不常用   
	 * 可以试试props.putAll(t)   
	 */
	public static Map<String, String> getAllProperty() {
		Map<String, String> map = Maps.newHashMap();
		Enumeration<?> enu = props.propertyNames();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = props.getProperty(key);
			map.put(key, value);
		}
		return map;
	}

	/**   
	 * 在控制台上打印出所有属性，调试时用。   
	 */
	public static void printProperties() {
		props.list(System.out);
	}

	/**   
	 * 写入properties信息   
	 */
	public static void writeProperties(String key, String value) {
		try {
			OutputStream fos = new FileOutputStream(new File(uri));
			props.setProperty(key, value);
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流      
			props.store(fos, "『comments』Update key：" + key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		PropertiesUtil.printProperties();
	}

}
