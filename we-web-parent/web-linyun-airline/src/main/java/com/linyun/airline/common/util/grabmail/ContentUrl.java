/**
 * ContentUrl.java
 * com.linyun.airline.common.util
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.util.grabmail;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * <p>
 *工具类，用来抓取下载链接中的pdf文档
 * @author   孙斌
 * @Date	 2017年5月8日 	 
 */
public class ContentUrl {
	/*****
	 * 用正则匹配a标签
	 */

	public static String getUrl(Message message) {
		String str = "";
		try {
			str = message.getContent() + "";
		} catch (IOException e) {

			e.printStackTrace();

		} catch (MessagingException e) {

			e.printStackTrace();

		}
		String regEx = "<a\\shref=.+";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(str);
		matcher.matches();
		while (matcher.find()) {
			//System.out.println(matcher.group());
			String temp = matcher.group();
			if (temp.contains("Download Tax Invoice")) {
				int a = temp.indexOf("href");
				int b = temp.indexOf("\"", a + 8);
				str = temp.substring(a + 6, b);
				//System.out.println(temp.substring(a + 6, b));
			}
		}
		String str1 = System.getProperty("java.io.tmpdir");
		DownLoadPdfTransfer.downloadpdf(str, str1 + File.separator + "12.pdf");

		return str;
	}
}
