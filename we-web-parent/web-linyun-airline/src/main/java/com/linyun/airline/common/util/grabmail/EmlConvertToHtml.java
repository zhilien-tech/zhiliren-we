/**
 * EmlConvertToHtml.java
 * com.linyun.airline.common.util
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.util.grabmail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.base.impl.QiniuUploadServiceImpl;
import com.linyun.airline.common.constants.CommonConstants;
import com.uxuexi.core.common.util.FileUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年5月9日 	 
 */
public class EmlConvertToHtml {
	private Map<String, String> map = new HashMap<String, String>();

	public String display(File emlFile) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.host", "smtp.dummydomain.com");
		props.put("mail.transport.protocol", "smtp");

		Session mailSession = Session.getDefaultInstance(props, null);
		InputStream source = new FileInputStream(emlFile);
		MimeMessage message = new MimeMessage(mailSession, source);
		return getContentHtml(message);
	}

	/***
	 * 
	 * 
	 * 获取文件的内容
	 * 
	 */
	public String getContentHtml(Message msg) throws Exception {
		// 发件人信息  
		Address[] froms = msg.getFrom();
		if (froms != null) {
			//System.out.println("发件人信息:" + froms[0]);  
			InternetAddress addr = (InternetAddress) froms[0];
			System.out.println("发件人地址:" + addr.getAddress());
			System.out.println("发件人显示名:" + addr.getPersonal());
		}
		System.out.println("邮件主题:" + msg.getSubject());
		String subjectStr = msg.getSubject();
		// getContent() 是获取包裹内容, Part相当于外包装  
		Object o = msg.getContent();
		//String PNR = subjectStr.substring(subjectStr.indexOf("#"), subjectStr.indexOf(")", subjectStr.indexOf("#")));
		//System.out.println("PNR+++++++++++++++++++++++++++++" + PNR);
		//System.out.println(o + "");
		//getUrl(msg.getContent() + "");
		if (o instanceof Multipart) {
			Multipart multipart = (Multipart) o;

			/*if(){
				
			}*/
			reMultipart(multipart);
		} else if (o instanceof Part) {
			Part part = (Part) o;
			rePart(part);
		} else {
			System.out.println("类型" + msg.getContentType());
			System.out.println("内容" + msg.getContent());
			map.put("content", msg.getContent() + "");
		}
		String content = map.get("content");

		/*String str = content.substring(content.indexOf("cid:"), content.indexOf("\"", content.indexOf("cid:")));
		String pictureUrl = map.get("pictureUrl");
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(content);
		content = matcher.replaceAll(pictureUrl);*/
		return content;
		//用jsoup优化格式
		/*org.jsoup.nodes.Document doc = Jsoup.parse(content);
		content = doc.html();
		Htmltoword.writeWordFile(content);*/
		/*String str1 = System.getProperty("java.io.tmpdir");
		File file = new File(str1 + File.separator + "12.html");
		OutputStream os = new FileOutputStream(file);
		byte[] b = content.getBytes();
		os.write(b);
		HtmlToPdf.htmlConvertToPdf(str1 + File.separator + "12.html", str1 + File.separator + "12.pdf");
		UploadService u = new QiniuUploadServiceImpl();*/
		/*File file1 = new File(str1 + File.separator + "12.html");

		String fileURL = CommonConstants.IMAGES_SERVER_ADDR
				+ u.uploadImage(new FileInputStream(file1), "12.pdf", "12.pdf");

		Office2PDF.urlToFile(fileURL, str + File.separator + "12.pdf");
		System.out.println();*/
		//Office2PDF.urlToFile(url, str1 + File.separator + "12.pdf");
		//System.out.println(content);
		/*System.out.println(map.get("content"));
		System.out.println(map.remove("content"));
		System.out.println(map.get("content"));
		System.out.println(map.get("pictureUrl"));
		System.out.println(map.remove("pictureUrl"));
		System.out.println(map.get("pictureUrl"));*/

	}

	/** 
	 * @param part 解析内容 
	 * @throws Exception 
	 */
	private void rePart(Part part) throws MessagingException, UnsupportedEncodingException, IOException,
			FileNotFoundException {
		if (part.getDisposition() != null) {

			String strFileNmae = MimeUtility.decodeText(part.getFileName()); //MimeUtility.decodeText解决附件名乱码问题  
			System.out.println("发现附件: " + MimeUtility.decodeText(part.getFileName()));
			System.out.println("内容类型: " + MimeUtility.decodeText(part.getContentType()));
			System.out.println("附件内容:" + part.getContent());
			InputStream in = part.getInputStream();// 打开附件的输入流  
			// 读取附件字节并存储到文件中  
			java.io.FileOutputStream out = new FileOutputStream(strFileNmae);
			int data;
			while ((data = in.read()) != -1) {
				out.write(data);
			}
			in.close();
			out.close();
		} else {
			if (part.getContentType().startsWith("text/plain")) {
				System.out.println("文本内容：" + part.getContent());
				map.put("content", part.getContent() + "");
			} else {
				//System.out.println("HTML内容：" + part.getContent());
				if (part.getContentType().startsWith("text/html")) {

					map.put("content", part.getContent() + "");
				}
				// TODO:	

			}
		}
	}

	/** 
	 * @param multipart // 接卸包裹（含所有邮件内容(包裹+正文+附件)） 
	 * @throws Exception 
	 */
	private void reMultipart(Multipart multipart) throws Exception {
		//System.out.println("邮件共有" + multipart.getCount() + "部分组成");  
		// 依次处理各个部分  

		for (int j = 0, n = multipart.getCount(); j < n; j++) {
			//System.out.println("处理第" + j + "部分");  
			Part part = multipart.getBodyPart(j);//解包, 取出 MultiPart的各个部分, 每部分可能是邮件内容,  
			// 也可能是另一个小包裹(MultipPart)  
			// 判断此包裹内容是不是一个小包裹, 一般这一部分是 正文 Content-Type: multipart/alternative  
			if (part.getContent() instanceof Multipart) {
				//获取所需的图片的路径
				/*String pictureUrl = saveAttachment(part);
				if (!Util.isEmpty(pictureUrl)) {
					map.put("pictureUrl", pictureUrl);
				}*/
				//System.out.println("======================" + pictureUrl);
				Multipart p = (Multipart) part.getContent();// 转成小包裹  
				//递归迭代  
				reMultipart(p);
			} else {

				rePart(part);
			}
		}
	}

	/***
	 * 将附件中的图片保存下来放到html中
	 * @throws  
	 * @throws IOException 
	 */
	private String saveAttachment(Part part) throws Exception {
		Multipart multipart = (Multipart) part.getContent(); //复杂体邮件  
		//复杂体邮件包含多个邮件体  
		String fileUrl = null;
		int partCount = multipart.getCount();
		for (int i = 0; i < partCount; i++) {
			//获得复杂体邮件中其中一个邮件体  
			BodyPart bodyPart = multipart.getBodyPart(i);

			String fileName = bodyPart.getFileName();
			String fileExt = FileUtil.getSuffix(fileName);//获取附件后缀名
			//某一个邮件体也有可能是由多个邮件体组成的复杂体  

			String disp = bodyPart.getDisposition();
			//向网络上上传一个文件，然后返回一个地址存储到数据库中
			InputStream is = bodyPart.getInputStream();
			if ("PNG".equalsIgnoreCase(fileExt)) {

				fileUrl = uploadFile(is, fileExt);
			}

		}
		return fileUrl;
	}

	/**  
	 * 向网络上上传一个文件，然后返回一个地址存储到数据库中
	 * @param is 输入流  
	 * @param fileName 文件名  
	 * @return 文件URL地址
	 */
	private String uploadFile(InputStream is, String fileName) {
		UploadService u = new QiniuUploadServiceImpl();

		String fileURL = CommonConstants.IMAGES_SERVER_ADDR + u.uploadImage(is, fileName, fileName);
		//文件存储地址

		return fileURL;
	}
}
