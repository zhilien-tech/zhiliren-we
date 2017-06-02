/**
 * MailUtils.java
 * com.linyun.airline.admin.drawback.grabfile.timer
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.drawback.grabfile.timer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.linyun.airline.admin.drawback.grabfile.commonmail.GrabMailConstants;
import com.linyun.airline.admin.drawback.grabfile.entity.TGrabFileEntity;
import com.linyun.airline.admin.drawback.grabfile.enums.FileNavalEnum;
import com.linyun.airline.admin.drawback.grabfile.enums.FileTypeEnum;
import com.linyun.airline.admin.drawback.grabfile.enums.GrabTypeEnum;
import com.linyun.airline.admin.drawback.grabmail.entity.TGrabMailEntity;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.common.util.grabmail.ContentImage;
import com.linyun.airline.common.util.grabmail.ContentUrl;
import com.linyun.airline.common.util.grabmail.EmlConvertToHtml;
import com.linyun.airline.common.util.grabmail.HtmlToPdf;
import com.uxuexi.core.common.util.BeanUtil;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.FileUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 邮件抓取
 * @author   崔建斌
 * @Date	 2017年2月8日 	 
 */
@SuppressWarnings("rawtypes")
@IocBean(name = "grabMailService")
public class MailScrabService extends BaseService {
	Object obj = new Object();
	@Inject
	private UploadService qiniuUploadService;
	private Map<String, Long> map = new HashMap<String, Long>();
	private Logger logger = LoggerFactory.getLogger(MailScrabService.class);
	private ContentImage ctim = new ContentImage();
	private EmlConvertToHtml ect = new EmlConvertToHtml();

	public MailScrabService() {

	}

	/** 
	 * 接收邮件备用
	 */
	public void receivePop3old(String user, String passwd) throws Exception {
		String pop3Server = "pop3.sina.com";
		String pop3Port = "110";
		//对于user和password，qq邮箱的password填写授权key
		/*String user = "liuxuli232@sina.cn";
		String passwd = "6832156775";*/
		/*String user = "in2020072@sina.com";
		String passwd = "tlywy2017jan";*/
		/*String user = "lftravel@sina.com";
		String passwd = "mar2016lywy";*/

		String userTeam = "";
		if ("in2020072@sina.com".equals(user)) {
			userTeam = "team";
		} else if ("lftravel@sina.com".equals(user)) {
			userTeam = "fit";
		}

		// 准备连接服务器的会话信息  
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "pop3"); // 协议  
		/* 新浪:imap.sina.com
		 * 腾讯:imap.qq.com
		 */
		props.setProperty("mail.pop3.host", pop3Server); // imap服务器
		props.setProperty("mail.pop3.port", pop3Port); // 端口  

		// 创建Session实例对象  
		Session session = Session.getInstance(props);
		Store store = session.getStore("pop3");
		store.connect(user, passwd);

		// 获得收件箱  
		Folder folder = store.getFolder("INBOX");
		/* Folder.READ_ONLY：只读权限 
		 * Folder.READ_WRITE：可读可写（可以修改邮件的状态） 
		 */
		folder.open(Folder.READ_WRITE); //打开收件箱  

		// 由于POP3协议无法获知邮件的状态,所以getUnreadMessageCount得到的是收件箱的邮件总数  
		System.out.println("未读邮件数: " + folder.getUnreadMessageCount());

		// 由于POP3协议无法获知邮件的状态,所以下面得到的结果始终都是为0  
		System.out.println("删除邮件数: " + folder.getDeletedMessageCount());
		System.out.println("新邮件: " + folder.getNewMessageCount());

		// 获得收件箱中的邮件总数  
		System.out.println("邮件总数: " + folder.getMessageCount());

		// 得到收件箱中的所有邮件,并解析  
		Message[] messages = folder.getMessages();
		//parseMessage(userTeam, messages);

		//释放资源  
		folder.close(true);
		store.close();
	}

	/**
	 * TODO(接收邮件方法)
	 * @param user
	 * @param passwd
	 * @throws Exception
	 */
	public void receivePop3(String user, String passwd) throws Exception {
		String pop3Server = "pop3.sina.com";
		String pop3Port = "110";
		Integer userTeam = null;
		if ("in2020072@sina.com".equals(user)) {
			userTeam = OrderTypeEnum.TEAM.intKey();//团队
		} else if ("lftravel@sina.com".equals(user)) {
			userTeam = OrderTypeEnum.FIT.intKey();//散客
		}

		// 准备连接服务器的会话信息  
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "pop3"); // 协议  
		/* 新浪:imap.sina.com
		 * 腾讯:imap.qq.com
		 */
		props.setProperty("mail.pop3.host", pop3Server); // imap服务器
		props.setProperty("mail.pop3.port", pop3Port); // 端口  

		// 创建Session实例对象  
		Session session = Session.getInstance(props);
		Store store = session.getStore("pop3");
		store.connect(user, passwd);

		// 获得收件箱  
		Folder folder = store.getFolder("INBOX");
		/* Folder.READ_ONLY：只读权限 
		 * Folder.READ_WRITE：可读可写（可以修改邮件的状态） 
		 */
		folder.open(Folder.READ_WRITE); //打开收件箱  

		// 由于POP3协议无法获知邮件的状态,所以getUnreadMessageCount得到的是收件箱的邮件总数  
		System.out.println("未读邮件数: " + folder.getUnreadMessageCount());

		// 由于POP3协议无法获知邮件的状态,所以下面得到的结果始终都是为0  
		System.out.println("删除邮件数: " + folder.getDeletedMessageCount());
		System.out.println("新邮件: " + folder.getNewMessageCount());

		// 获得收件箱中的邮件总数  
		System.out.println("邮件总数: " + folder.getMessageCount());

		// 得到收件箱中的所有邮件,并解析  
		Message[] messages = folder.getMessages();
		//利用多线程进行邮件的抓取，提高速度
		//=====================多线程开始=========================================
		/*List<Message[]> messageList = Lists.newArrayList();
		int count = 0;//总共要进行多少线程
		if (messages.length % 500 == 0) {
			count = messages.length / 500;
		} else {
			count = messages.length / 500 + 1;

		}
		for (int i = 0; i < count; i++) {
			Message[] message = new Message[500];
			messageList.add(message);

		}
		for (int i = 0; i < count; i++) {

			for (int j = 0; j < messages.length; j++) {
				messageList.get(i)[j % 500] = messages[j];

			}
		}
		List<MutiThreadUtil> mutiThreadUtilList = Lists.newArrayList();
		for (int i = 0; i < count; i++) {
			MutiThreadUtil m = new MutiThreadUtil();
			m.setFlag(userTeam);
			m.setMailScrabService(this);
			m.setMessages(messageList.get(i));
			mutiThreadUtilList.add(m);
		}

		try {
			ExecutorService executorService = Executors.newFixedThreadPool(mutiThreadUtilList.size());
			executorService.invokeAll(mutiThreadUtilList);
			 调用所有的多线程,必须执行完以后,main才能往下执行 
			executorService.shutdown();
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}*/

		//=====================多线程结束=========================================

		parseMessage(userTeam, messages);

		//释放资源  
		folder.close(true);
		store.close();
	}

	public void loginMailAccount() {
		String userFit = "lftravel@sina.com";
		String passwdFit = "mar2016lywy";
		String userTeam = "in2020072@sina.com";
		String passwdTeam = "tlywy2017jan";
		try {
			receivePop3(userFit, passwdFit);
			receivePop3(userTeam, passwdTeam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*****
		 * 抓取完邮件之后删除一些创建的空的文件夹
		 */
		String sqlString = sqlManager.get("grab_report_delete_empty");
		Sql sql = Sqls.create(sqlString);
		dbDao.execute(sql);

	}

	/** 
	 * 解析邮件 
	 * @param messages 要解析的邮件列表 
	 * @throws ParseException 
	 */
	public void parseMessage(Integer userTeam, Message... messages) throws MessagingException, IOException {
		if (messages == null || messages.length < 1) {
			throw new MessagingException("未找到要解析的邮件!");
		}
		// 解析所有邮件  
		for (int i = messages.length - 1, count = messages.length; i < count; i--) {
			//if (i > 364 * 100) {
			//解决Folder is not open异常
			if (!messages[i].getFolder().isOpen()) {
				messages[i].getFolder().open(Folder.READ_WRITE); //如果close，就重新open    
			} //判断是否open  
			MimeMessage msg = (MimeMessage) messages[i];
			try {
				eachHandler(msg, userTeam);
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean isRead = isRead(msg);
			if (isRead) {
				continue;
			}
			//}
		}

	}

	/**
	 * 解析每一封邮件
	 * @throws MessagingException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ParseException 
	 */
	private void eachHandler(MimeMessage msg, Integer userTeam) throws MessagingException, FileNotFoundException,
			IOException {
		//1、从邮件获取能直接保存的数据
		String theme = getSubject(msg);//主题
		String sender = getFrom(msg);//发件人
		String addressee = getReceiveAddress(msg, null);//收件人
		String sendTime = getSentDate(msg, null);//发送时间
		String fileSize = Integer.toString(msg.getSize());//邮件大小
		logger.info("邮件的主题是:" + theme);
		logger.info("发件人:" + sender);
		logger.info("收件人:" + addressee);
		logger.info("发送时间:" + sendTime);
		logger.info("邮件大小:" + fileSize + "k");
		//2、查询数据库判断是否已存在某条数据，如果存在直接返回，不存在则解析
		int count = 0;
		if (!Util.isEmpty(sender)) {
			if (sender.contains(GrabMailConstants.FIT_QF) || sender.contains(GrabMailConstants.FIT_D7)
					|| sender.contains(GrabMailConstants.FIT_VA) || sender.contains(GrabMailConstants.FIT_JQ)
					|| sender.contains(GrabMailConstants.FIT_TT) || sender.contains(GrabMailConstants.FIT_CI)
					|| sender.contains(GrabMailConstants.TEAM_JQ) || sender.contains(GrabMailConstants.TEAM_VA)
					|| sender.contains(GrabMailConstants.TEAM_TT)) {
				Sql sql = Sqls.create(sqlManager.get("grab_mail_file_level"));
				sql.params().set("theme", theme);
				sql.params().set("sender", sender);
				sql.params().set("addressee", addressee);
				sql.params().set("sendTime", sendTime);
				count = DbSqlUtil.fetchInt(dbDao, sql);
				long grabRecordId = 0;
				if (count > 0) {
					return;
				} else {
					//3-1、保存邮件抓取记录
					TGrabMailEntity mailEntity = new TGrabMailEntity();
					mailEntity.setTheme(theme);
					mailEntity.setSender(sender);
					mailEntity.setAddressee(addressee);
					mailEntity.setSendTime(sendTime);
					mailEntity.setGrabTime(new Date());//抓取时间
					mailEntity.setGrabStatus(GrabTypeEnum.ALREADYGRAB.intKey());//已抓
					mailEntity = dbDao.insert(mailEntity);
					grabRecordId = mailEntity.getId();
				}

				//3-2、保存抓取文件
				fileLevel(msg, userTeam, sender, sendTime, fileSize, grabRecordId);
				/*FileNavalEnum structureType = structureType(sender);
				switch (structureType) {
				case FITD7JQTT:
					fileLevel(msg, userTeam, sender, sendTime, fileSize, grabRecordId);
					break;
				case FITQF:
					//TODO
					fileLevel(msg, userTeam, sender, sendTime, fileSize, grabRecordId);
					break;
				case FITVA:
					//TODO
					fileLevel(msg, userTeam, sender, sendTime, fileSize, grabRecordId);
					break;
				case TEAMJQTT:
					//TODO
					fileLevel(msg, userTeam, sender, sendTime, fileSize, grabRecordId);
					break;
				case TEAMVA:
					//TODO
					fileLevel(msg, userTeam, sender, sendTime, fileSize, grabRecordId);
					break;
				default:
					break;
				}*/
			} else {
				return;
			}
		}
	}

	/**
	 * TODO(文件层次结构方法)
	 * @param msg
	 * @param userTeam
	 * @param sender
	 * @param sendTime
	 * @param fileSize
	 * @param grabRecordId
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws FileNotFoundException
	 * @throws IOException 
	 */
	@Aop("txDb")
	private void fileLevel(MimeMessage msg, Integer userTeam, String sender, String sendTime, String fileSize,
			long grabRecordId) throws UnsupportedEncodingException, MessagingException, FileNotFoundException,
			IOException {
		//
		long rootId;
		DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 E HH:mm ");
		//================================根据抓取的类型不同，date获取途径不同==============================================
		String str = null;
		String dateStr = null;
		Date date = null;
		try {
			str = ctim.getContentHtml(msg);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (sender.contains(GrabMailConstants.FIT_JQ)) {
			dateStr = this.getDate(str, 0);
			try {
				date = DateUtil.string2Date(DateUtil.getMonthByEnNameOfDate(dateStr));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (sender.contains(GrabMailConstants.FIT_TT)) {
			dateStr = this.getDate(str, 1);
			try {
				date = DateUtil.string2Date(DateUtil.getMonthByEnNameOfDate(dateStr));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				date = dateFormat.parse(sendTime);
			} catch (ParseException e) {
				e.printStackTrace();

			}
		}
		//==============================================================================
		/**************************发送邮件时间开始***************************/
		Double fileDouble = Double.valueOf(fileSize);
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM");
		String fileName = format.format(date);
		//散和团区分第一层的parentId
		rootId = getTopParentId(sender, userTeam);
		Chain chain = Chain.make("updateTime", date);
		TGrabFileEntity single = dbDao.fetch(TGrabFileEntity.class, rootId);
		/*if (Util.isEmpty(single.getFileSize())) {
			double temp = convertToM(fileDouble);//将其单位转换为kb
			temp = getFileSize(temp, chain);
			chain.add("fileSize", temp);
		} else {
			double afterConvert = 0;
			if ("K".equals(single.getUnit())) {
				afterConvert = Double.valueOf(single.getFileSize());
			} else if ("M".equals(single.getUnit())) {
				afterConvert = Double.valueOf(single.getFileSize()) * 1024;
			}

			double temp = convertToM(fileDouble) + afterConvert;
			temp = getFileSize(temp, chain);
			chain.add("fileSize", temp);
		}*/
		dbDao.update(TGrabFileEntity.class, chain, Cnd.where("id", "=", rootId));
		//父id
		TGrabFileEntity timeFile = new TGrabFileEntity();
		timeFile.setParentId(rootId);

		//创建时间
		timeFile.setCreateTime(new Date());

		timeFile.setUpdateTime(date);
		//名称
		timeFile.setFileName(fileName);
		//类型
		timeFile.setType(FileTypeEnum.FOLDER.intKey());//文件类型

		//抓取记录id
		timeFile.setMailId(grabRecordId);
		//启用
		timeFile.setStatus(DataStatusEnum.ENABLE.intKey());
		timeFile.setLevel(2);
		timeFile.setSort(0);
		timeFile.setGroupType(userTeam);//散团类型

		//synchronized (obj) {
		List<TGrabFileEntity> list = existFile(rootId, fileName);
		if (!Util.isEmpty(list)) {
			timeFile = list.get(0);
			dbDao.update(TGrabFileEntity.class, Chain.make("updateTime", date), Cnd.where("id", "=", timeFile.getId()));
		} else {
			timeFile = dbDao.insert(timeFile);
		}
		//}
		/**************************时间结束***************************/
		/**************************第二层开始***************************/

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy.MM.dd");
		String fileNameTwo = fmt.format(date);//文件名
		Long rootIdTwo = timeFile.getId();
		Chain chain2 = Chain.make("updateTime", date);
		TGrabFileEntity one = dbDao.fetch(TGrabFileEntity.class, rootIdTwo);
		/*if (Util.isEmpty(one.getFileSize())) {
			double temp = convertToM(fileDouble);//将其单位转换为kb
			temp = getFileSize(temp, chain2);
			chain2.add("fileSize", temp);
		} else {
			double afterConvert = 0;
			if ("K".equals(single.getUnit())) {
				afterConvert = Double.valueOf(single.getFileSize());
			} else if ("M".equals(single.getUnit())) {
				afterConvert = Double.valueOf(single.getFileSize()) * 1024;
			}

			double temp = convertToM(fileDouble) + afterConvert;
			temp = getFileSize(temp, chain2);
			chain2.add("fileSize", temp);
		}*/
		dbDao.update(TGrabFileEntity.class, chain2, Cnd.where("id", "=", rootIdTwo));
		//父id
		TGrabFileEntity timeFileTwo = new TGrabFileEntity();
		timeFileTwo.setParentId(rootIdTwo);

		//创建时间
		timeFileTwo.setCreateTime(new Date());

		//名称
		timeFileTwo.setFileName(fileNameTwo);
		//类型
		timeFileTwo.setType(FileTypeEnum.FOLDER.intKey());//文件类型
		timeFileTwo.setUpdateTime(date);
		//抓取记录id
		timeFileTwo.setMailId(grabRecordId);
		//启用
		timeFileTwo.setStatus(DataStatusEnum.ENABLE.intKey());
		timeFileTwo.setLevel(3);
		timeFileTwo.setSort(0);
		timeFileTwo.setGroupType(userTeam);//散团类型
		//synchronized (obj) {
		List<TGrabFileEntity> lst = existFile(rootIdTwo, fileNameTwo);
		if (!Util.isEmpty(lst)) {
			timeFileTwo = lst.get(0);
			dbDao.update(TGrabFileEntity.class, Chain.make("updateTime", date),
					Cnd.where("id", "=", timeFileTwo.getId()));
		} else {
			timeFileTwo = dbDao.insert(timeFileTwo);
		}
		//}
		/**************************第二层结束***************************/
		/**************************客户团号***************************/
		/*long sort = getSort(timeFileTwo.getId());*/
		String cusgroupnum = getcusGroupnum();//得到客户团号
		long sort = 0;

		//synchronized (obj) {

		if (Util.isEmpty(cusgroupnum)) {
			cusgroupnum = "客户团号";
			TGrabFileEntity grabFileEntity = dbDao.fetch(TGrabFileEntity.class,
					Cnd.where("id", "=", timeFileTwo.getId()));
			Integer a = grabFileEntity.getCustomnum();
			if (Util.isEmpty(a)) {
				a = 1;
				cusgroupnum += a;
			} else {
				a++;
				cusgroupnum += a;

			}
			dbDao.update(TGrabFileEntity.class, Chain.make("customnum", a), Cnd.where("id", "=", timeFileTwo.getId()));
		}
		//}
		//父id
		long pid = timeFileTwo.getId();
		Chain chain3 = Chain.make("updateTime", date);
		TGrabFileEntity cusgroupOne = dbDao.fetch(TGrabFileEntity.class, pid);
		/*	if (Util.isEmpty(cusgroupOne.getFileSize())) {
				double temp = convertToM(fileDouble);//将其单位转换为kb
				temp = getFileSize(temp, chain3);
				chain3.add("fileSize", temp);
			} else {

				double afterConvert = 0;
				if ("K".equals(single.getUnit())) {
					afterConvert = Double.valueOf(single.getFileSize());
				} else if ("M".equals(single.getUnit())) {
					afterConvert = Double.valueOf(single.getFileSize()) * 1024;
				}

				double temp = convertToM(fileDouble) + afterConvert;
				temp = getFileSize(temp, chain3);
				chain3.add("fileSize", temp);
			}*/
		dbDao.update(TGrabFileEntity.class, chain3, Cnd.where("id", "=", pid));
		TGrabFileEntity groupNumFile = new TGrabFileEntity();

		groupNumFile.setParentId(pid);

		groupNumFile.setCreateTime(new Date());

		groupNumFile.setUpdateTime(date);
		//名称
		groupNumFile.setFileName(cusgroupnum);
		//类型
		groupNumFile.setType(FileTypeEnum.FOLDER.intKey());

		//抓取记录id
		groupNumFile.setMailId(grabRecordId);
		//启用
		groupNumFile.setStatus(DataStatusEnum.ENABLE.intKey());
		groupNumFile.setLevel(4);

		groupNumFile.setSort(sort);
		groupNumFile.setGroupType(userTeam);//散团类型
		//synchronized (obj) {

		List<TGrabFileEntity> lstThree = existGroupNumFile(pid, cusgroupnum);
		if (!Util.isEmpty(lstThree)) {
			groupNumFile = lstThree.get(0);
			dbDao.update(TGrabFileEntity.class, Chain.make("updateTime", date),
					Cnd.where("id", "=", groupNumFile.getId()));
		} else {
			groupNumFile = dbDao.insert(groupNumFile);
		}
		//}

		/****************************************************客户团号结束**********************************************/

		/**********************************文件开始****************************************************/
		/*if (sender.contains(GrabMailConstants.FIT_JQ)) {
			//TODO 如果是散客下JQ航空公司就将文件正文内容转化为pdf格式，然后在本地保存下它的地址并且
			//要将此邮件下面的附件地址也要保存到本地
			String contentHtml = null;
			String fileUrl = null;
			try {
				contentHtml = ctim.getContentHtml(msg);
				fileUrl = uploadFileNew(contentHtml);
			} catch (Exception e) {
				e.printStackTrace();
			}
			saveCommonFile(msg, userTeam, fileSize, grabRecordId, date, fileDouble, single, groupNumFile, fileUrl);
		} else if (sender.contains(GrabMailConstants.FIT_TT)) {
			//TODO 如果是散客下TT航空公司,将邮件正文进行解析，抓取的附件是正文pdf文档中的下载按钮所链接的地址
			String fileUrl = null;
			try {
				fileUrl = ContentUrl.getUrl(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			saveCommonFile(msg, userTeam, fileSize, grabRecordId, date, fileDouble, single, groupNumFile, fileUrl);
		} else if (sender.contains(GrabMailConstants.FIT_CI)) {
			//TODO 如果是散客下CI航空公司,将.eml格式的文件转化为PDF格式进行保存

			String contentHtml = null;
			String fileUrl = null;
			try {
				contentHtml = ect.getContentHtml(msg);
				fileUrl = uploadFileNew(contentHtml);
			} catch (Exception e) {
				e.printStackTrace();
			}
			saveCommonFile(msg, userTeam, fileSize, grabRecordId, date, fileDouble, single, groupNumFile, fileUrl);
		} else if (sender.contains(GrabMailConstants.FIT_D7)) {
			//TODO 如果是散客下D7航空公司,将邮件的正文部分转化为pdf格式进行保存
			String contentHtml = null;
			String fileUrl = null;
			try {
				contentHtml = ctim.getContentHtml(msg);
				fileUrl = uploadFileNew(contentHtml);
			} catch (Exception e) {
				e.printStackTrace();
			}
			saveCommonFile(msg, userTeam, fileSize, grabRecordId, date, fileDouble, single, groupNumFile, fileUrl);
		} else if (sender.contains(GrabMailConstants.TEAM_JQ)) {
			//TODO 如果是团队下JQ航空公司就将文件正文内容转化为pdf格式，然后在本地保存下它的地址并且
			//要将此邮件下面的附件地址也要保存到本地
			String contentHtml = null;
			String fileUrl = null;
			try {
				contentHtml = ctim.getContentHtml(msg);
				fileUrl = uploadFileNew(contentHtml);
			} catch (Exception e) {
				e.printStackTrace();
			}
			saveCommonFile(msg, userTeam, fileSize, grabRecordId, date, fileDouble, single, groupNumFile, fileUrl);
		} else if (sender.contains(GrabMailConstants.TEAM_TT)) {
			//TODO 如果是团队下TT航空公司,将邮件正文进行解析，抓取的附件是正文pdf文档中的下载按钮所链接的地址
			String fileUrl = null;
			try {
				fileUrl = ContentUrl.getUrl(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			saveCommonFile(msg, userTeam, fileSize, grabRecordId, date, fileDouble, single, groupNumFile, fileUrl);
		} else {
			//除了上述特殊抓取之外，此处就是保存只需要邮件附件的方法
			saveCommonFile(msg, userTeam, fileSize, grabRecordId, date, fileDouble, single, groupNumFile, null);
		}*/
		saveCommonFile(msg, userTeam, fileSize, grabRecordId, date, fileDouble, single, groupNumFile, null);
	}

	private String uploadFileNew(String content, String fileName) throws FileNotFoundException, IOException {
		String str1 = System.getProperty("java.io.tmpdir");
		File file = new File(str1 + File.separator + "12.html");
		OutputStream os = new FileOutputStream(file);
		byte[] b = content.getBytes();
		os.write(b);
		File file1 = new File(str1 + File.separator + "12.html");
		String fileURL = CommonConstants.IMAGES_SERVER_ADDR
				+ qiniuUploadService.uploadImage(new FileInputStream(file1), "html", null);
		HtmlToPdf.convert(fileURL, str1 + File.separator + "12.pdf");
		File file2 = new File(str1 + File.separator + "12.pdf");
		String fileURLPdf = CommonConstants.IMAGES_SERVER_ADDR
				+ qiniuUploadService.uploadImage(new FileInputStream(file2), "pdf", null);
		return fileURLPdf;
	}

	/**
	 * 公用保存附件的方法
	 * @param msg
	 * @param userTeam
	 * @param fileSize
	 * @param grabRecordId
	 * @param date
	 * @param fileDouble
	 * @param single
	 * @param groupNumFile
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void saveCommonFile(MimeMessage msg, Integer userTeam, String fileSize, long grabRecordId, Date date,
			Double fileDouble, TGrabFileEntity single, TGrabFileEntity groupNumFile, String fileUrl)
			throws UnsupportedEncodingException, MessagingException, FileNotFoundException, IOException {
		//父id
		TGrabFileEntity fileProps = new TGrabFileEntity();
		fileProps.setParentId(groupNumFile.getId());

		Chain chain4 = Chain.make("updateTime", date);
		TGrabFileEntity filePropsOne = dbDao.fetch(TGrabFileEntity.class, groupNumFile.getId());
		/*if (Util.isEmpty(filePropsOne.getFileSize())) {
			double temp = convertToM(fileDouble);//将其单位转换为kb
			temp = getFileSize(temp, chain4);
			chain4.add("fileSize", temp);
		} else {
			double afterConvert = 0;
			if ("K".equals(single.getUnit())) {
				afterConvert = Double.valueOf(single.getFileSize());
			} else if ("M".equals(single.getUnit())) {
				afterConvert = Double.valueOf(single.getFileSize()) * 1024;
			}

			double temp = convertToM(fileDouble) + afterConvert;
			temp = getFileSize(temp, chain4);
			chain4.add("fileSize", temp);
		}*/
		dbDao.update(TGrabFileEntity.class, chain4, Cnd.where("id", "=", groupNumFile.getId()));
		//创建时间
		fileProps.setCreateTime(new Date());
		fileProps.setUpdateTime(date);
		//类型
		fileProps.setType(FileTypeEnum.FOLDER.intKey());
		/*	if (convertToM(Double.valueOf(fileSize)) > 1024) {
				fileProps.setFileSize(convertToM(convertToM(Double.valueOf(fileSize))) + "");
				fileProps.setUnit("M");
			} else {
				fileProps.setFileSize(convertToM(Double.valueOf(fileSize)) + "");
				fileProps.setUnit("K");
			}*/
		//抓取记录id
		fileProps.setMailId(grabRecordId);
		//启用
		fileProps.setStatus(DataStatusEnum.ENABLE.intKey());
		fileProps.setLevel(4);

		//散团类型
		fileProps.setGroupType(userTeam);
		//文件url
		List<TGrabFileEntity> grabFileList = Lists.newArrayList();
		saveAttachment(msg, grabFileList, fileProps);
		String sender = getFrom(msg);
		//=======根据情况选择保存附件以外的东西================================================
		if (sender.contains(GrabMailConstants.FIT_JQ)) {
			//TODO 如果是散客下JQ航空公司就将文件正文内容转化为pdf格式，然后在本地保存下它的地址并且
			//要将此邮件下面的附件地址也要保存到本地
			saveContent(grabFileList, fileProps, msg);
		} else if (sender.contains(GrabMailConstants.FIT_TT)) {
			//TODO 如果是散客下TT航空公司,将邮件正文进行解析，抓取的附件是正文pdf文档中的下载按钮所链接的地址
			saveLink(grabFileList, fileProps, msg);
		} else if (sender.contains(GrabMailConstants.FIT_CI)) {
			//TODO 如果是散客下CI航空公司,将.eml格式的文件转化为PDF格式进行保存

		} else if (sender.contains(GrabMailConstants.FIT_D7)) {
			//TODO 如果是散客下D7航空公司,将邮件的正文部分转化为pdf格式进行保存
			saveContent(grabFileList, fileProps, msg);
		} else if (sender.contains(GrabMailConstants.TEAM_JQ)) {
			//TODO 如果是团队下JQ航空公司就将文件正文内容转化为pdf格式，然后在本地保存下它的地址并且
			//要将此邮件下面的附件地址也要保存到本地
			saveContent(grabFileList, fileProps, msg);
		} else if (sender.contains(GrabMailConstants.TEAM_TT)) {
			//TODO 如果是团队下TT航空公司,将邮件正文进行解析，抓取的附件是正文pdf文档中的下载按钮所链接的地址
			saveLink(grabFileList, fileProps, msg);
		}

		if (!Util.isEmpty(grabFileList)) {
			dbDao.insert(grabFileList);
		} else {
			logger.info("没有抓取到附件!");
		}
	}

	/**
	 * TODO(文件大小转换)
	 * @param temp
	 * @param chain
	 */
	private double getFileSize(double temp, Chain chain) {
		if (temp > 1024) {
			temp = convertToM(temp);
			chain.add("unit", "M");
		}
		chain.add("unit", "K");
		return temp;
	}

	/**
	 * TODO(根据发件人区分抓取的邮件)
	 * @param sender
	 * @param userTeam 
	 */
	private long getTopParentId(String sender, Integer userTeam) {
		String fileName = null;
		TGrabMailEntity listMail = dbDao.fetch(TGrabMailEntity.class, Cnd.where("sender", "=", sender));
		String senderDb = listMail.getSender();//得到抓取保存到数据库中的发件人
		if (senderDb.contains(GrabMailConstants.FIT_D7)) {
			fileName = "D7";
		} else if (senderDb.contains(GrabMailConstants.FIT_QF)) {
			fileName = "QF";
		} else if (senderDb.contains(GrabMailConstants.FIT_VA)) {
			fileName = "VA";
		} else if (senderDb.contains(GrabMailConstants.TEAM_JQ)) {
			fileName = "JQ";
		} else if (senderDb.contains(GrabMailConstants.TEAM_VA)) {
			fileName = "VA";
		} else if (senderDb.contains(GrabMailConstants.FIT_JQ)) {
			fileName = "JQ";
		} else if (senderDb.contains(GrabMailConstants.FIT_TT)) {
			fileName = "TT";
		} else if (senderDb.contains(GrabMailConstants.TEAM_TT)) {
			fileName = "TT";
		} else if (senderDb.contains(GrabMailConstants.FIT_CI)) {
			fileName = "CI";
		}
		Cnd cnd = Cnd.NEW();
		cnd.and("fileName", "=", fileName);
		cnd.and("groupType", "=", userTeam);
		TGrabFileEntity fetch = dbDao.fetch(TGrabFileEntity.class, cnd);
		return fetch.getId();
	}

	/**
	 * 【时间文件夹】根据父级id和文件名称查询此文件是否已存在
	 * @param rootId 父级id【此时的父id为最顶层文件夹的id】
	 * @param fileName
	 */
	@Aop("txDb")
	private List<TGrabFileEntity> existFile(long rootId, String fileName) {
		List<TGrabFileEntity> list = null;
		//synchronized (obj) {

		Sql sqlCount = Sqls.create(sqlManager.get("grab_mail_count"));
		sqlCount.params().set("fileName", fileName);
		sqlCount.params().set("parentId", rootId);
		list = DbSqlUtil.query(dbDao, TGrabFileEntity.class, sqlCount);
		//}
		return list;
	}

	/**
	 * 【客户团号文件夹】根据父级id和文件名称查询此文件是否已存在
	 * @param pid  父级id【此时的父id为上级时间文件夹的id】
	 * @param cusgroupnum 文件名
	 */
	@Aop("txDb")
	private List<TGrabFileEntity> existGroupNumFile(long pid, String cusgroupnum) {
		List<TGrabFileEntity> lst = null;
		//synchronized (obj) {

		Sql sqlCount = Sqls.create(sqlManager.get("grab_mail_count"));
		sqlCount.params().set("fileName", cusgroupnum);
		sqlCount.params().set("parentId", pid);
		lst = DbSqlUtil.query(dbDao, TGrabFileEntity.class, sqlCount);
		//}
		return lst;
	}

	/**
	 * 获取客户团号
	 */
	private String getcusGroupnum() {
		//TODO
		return null;
	}

	/**
	 * 获取序号
	 * @param parentId
	 */
	private int getSort(long parentId) {
		Sql mailsql = Sqls.create(sqlManager.get("grab_mail_max_sort"));
		mailsql.params().set("pid", parentId);
		int sort = DbSqlUtil.fetchInt(dbDao, mailsql);
		return sort;
	}

	/** 
	 * 获得邮件主题 
	 * @param msg 邮件内容 
	 * @return 解码后的邮件主题 
	 */
	public String getSubject(MimeMessage msg) throws UnsupportedEncodingException, MessagingException {
		return MimeUtility.decodeText(msg.getSubject());
	}

	/** 
	 * 获得邮件发件人 
	 * @param msg 邮件内容 
	 * @return 姓名 <Email地址> 
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException  
	 */
	public String getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
		String from = "";
		Address[] froms = null;
		try {
			froms = msg.getFrom();
		} catch (Exception e) {

		}
		if (!Util.isEmpty(froms)) {
			if (froms.length < 1)
				throw new MessagingException("没有发件人!");

			InternetAddress address = (InternetAddress) froms[0];
			String person = address.getPersonal();
			if (person != null) {
				person = MimeUtility.decodeText(person) + " ";
			} else {
				person = "";
			}
			from = person + "<" + address.getAddress() + ">";
		}
		return from;
	}

	/** 
	 * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人 
	 * <p>Message.RecipientType.TO  收件人</p> 
	 * <p>Message.RecipientType.CC  抄送</p> 
	 * <p>Message.RecipientType.BCC 密送</p> 
	 * @param msg 邮件内容 
	 * @param type 收件人类型 
	 * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ... 
	 * @throws MessagingException 
	 */
	public String getReceiveAddress(MimeMessage msg, Message.RecipientType type) throws MessagingException {
		StringBuffer receiveAddress = new StringBuffer();
		Address[] addresss = null;
		if (type == null) {
			try {
				addresss = msg.getAllRecipients();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				addresss = msg.getRecipients(type);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (addresss == null || addresss.length < 1)
			/*throw new MessagingException("没有收件人!");*/
			return "不规范的收件人";
		for (Address address : addresss) {
			InternetAddress internetAddress = (InternetAddress) address;
			receiveAddress.append(internetAddress.toUnicodeString()).append(",");
		}

		receiveAddress.deleteCharAt(receiveAddress.length() - 1); //删除最后一个逗号  

		return receiveAddress.toString();
	}

	/** 
	 * 获得邮件发送时间 
	 * @param msg 邮件内容 
	 * @return yyyy年mm月dd日 星期X HH:mm 
	 * @throws MessagingException 
	 */
	public String getSentDate(MimeMessage msg, String pattern) throws MessagingException {
		Date receivedDate = msg.getSentDate();
		if (receivedDate == null)
			return "";

		if (pattern == null || "".equals(pattern))
			pattern = "yyyy年MM月dd日 E HH:mm ";

		return new SimpleDateFormat(pattern).format(receivedDate);
	}

	/** 
	 * 判断邮件中是否包含附件 
	 * @param msg 邮件内容 
	 * @return 邮件中存在附件返回true，不存在返回false 
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public boolean isContainAttachment(Part part) throws MessagingException, IOException {
		boolean flag = false;
		if (part.isMimeType("multipart/*")) {
			MimeMultipart multipart = (MimeMultipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				String disp = bodyPart.getDisposition();
				if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
					flag = true;
				} else if (bodyPart.isMimeType("multipart/*")) {
					flag = isContainAttachment(bodyPart);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.indexOf("application") != -1) {
						flag = true;
					}

					if (contentType.indexOf("name") != -1) {
						flag = true;
					}
				}

				if (flag)
					break;
			}
		} else if (part.isMimeType("message/rfc822")) {
			flag = isContainAttachment((Part) part.getContent());
		}
		return flag;
	}

	/**  
	 * 判断邮件是否已读  
	 * @param msg 邮件内容  
	 * @return 如果邮件已读返回true,否则返回false  
	 * @throws MessagingException   
	 */
	public boolean isRead(MimeMessage msg) throws MessagingException {
		return msg.getFlags().contains(Flags.Flag.SEEN);
	}

	/** 
	 * 判断邮件是否需要阅读回执 
	 * @param msg 邮件内容 
	 * @return 需要回执返回true,否则返回false 
	 * @throws MessagingException 
	 */
	public boolean isReplySign(MimeMessage msg) throws MessagingException {
		boolean replySign = false;
		String[] headers = msg.getHeader("Disposition-Notification-To");
		if (headers != null)
			replySign = true;
		return replySign;
	}

	/** 
	 * 获得邮件的优先级 
	 * @param msg 邮件内容 
	 * @return 1(High):紧急  3:普通(Normal)  5:低(Low) 
	 * @throws MessagingException  
	 */
	public String getPriority(MimeMessage msg) throws MessagingException {
		String priority = "普通";
		String[] headers = msg.getHeader("X-Priority");
		if (headers != null) {
			String headerPriority = headers[0];
			if (headerPriority.indexOf("1") != -1 || headerPriority.indexOf("High") != -1)
				priority = "紧急";
			else if (headerPriority.indexOf("5") != -1 || headerPriority.indexOf("Low") != -1)
				priority = "低";
			else
				priority = "普通";
		}
		return priority;
	}

	/** 
	 * 获得邮件文本内容 
	 * @param part 邮件体 
	 * @param content 存储邮件文本内容的字符串 
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public void getMailTextContent(Part part, StringBuffer content) throws MessagingException, IOException {
		//如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断  
		boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
		if (part.isMimeType("text/*") && !isContainTextAttach) {
			content.append(part.getContentType().toString());
		} else if (part.isMimeType("message/rfc822")) {
			getMailTextContent((Part) part.getContent(), content);
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				getMailTextContent(bodyPart, content);
			}
		}
	}

	/****
	 * 
	 * 保存内容
	 * @param grabFileLst
	 * @param fileProps
	 * @param msg TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@Aop("txDb")
	public void saveContent(List<TGrabFileEntity> grabFileLst, TGrabFileEntity fileProps, MimeMessage msg) {
		//synchronized (obj) {
		String contentHtml = null;
		String fileUrl = null;
		try {
			String subjectStr = msg.getSubject();
			// getContent() 是获取包裹内容, Part相当于外包装  
			Object o = msg.getContent();
			String fileName = "content-";
			String PNR = null;
			if (subjectStr.contains("#")) {

				PNR = subjectStr.substring(subjectStr.indexOf("#") + 2,
						subjectStr.indexOf(")", subjectStr.indexOf("#")));
			}
			if (!Util.isEmpty(PNR)) {
				fileName += PNR;
			}
			fileName += ".pdf";
			contentHtml = ctim.getContentHtml(msg);
			fileUrl = uploadFileNew(contentHtml, fileName);
			String str1 = System.getProperty("java.io.tmpdir");
			//==============================从网络地址下载文件将其变为本地文件再读进程序中，进行length的获取==================================
			InputStream is1 = null;
			OutputStream out = null;
			URL url = new URL(fileUrl);
			URLConnection connection = url.openConnection();
			is1 = connection.getInputStream();
			File file3 = new File(str1 + File.separator + "12.pdf");
			out = new FileOutputStream(file3);
			byte b[] = new byte[1024];
			int m = 0;
			while ((m = is1.read(b)) > -1) {
				out.write(b);
				out.flush();
			}
			File file4 = new File(str1 + File.separator + "12.pdf");
			InputStream is5 = new FileInputStream(file4);
			int byteNum = is5.available();
			double fileSize = Math.rint(byteNum / 1024);
			//=======================================================	

			TGrabFileEntity newFile = new TGrabFileEntity();
			BeanUtil.copyProperties(fileProps, newFile);
			String attachmentName = fileName;
			int fileSort = 0;
			if (Util.isEmpty(attachmentName)) {
				fileSort = getSort(fileProps.getParentId());
				fileSort += 1;
				attachmentName = "PNR" + fileSort;
			}
			//文件名和序号
			newFile.setFileName(attachmentName);
			fileProps.setSort(fileSort);
			newFile.setUrl(fileUrl);
			newFile.setType(FileTypeEnum.FILE.intKey());//文件
			newFile.setFileSize(fileSize);
			grabFileLst.add(newFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//}

	}

	/****
	 * 
	 * 保存链接
	 * @param grabFileLst
	 * @param fileProps
	 * @param msg TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public void saveLink(List<TGrabFileEntity> grabFileLst, TGrabFileEntity fileProps, MimeMessage msg) {
		String fileUrl = null;
		//synchronized (obj) {
		try {
			fileUrl = ContentUrl.getUrl(msg);
			String str1 = System.getProperty("java.io.tmpdir");
			File file1 = new File(str1 + File.separator + "12.html");
			fileUrl = CommonConstants.IMAGES_SERVER_ADDR
					+ qiniuUploadService.uploadImage(new FileInputStream(file1), "pdf", null);

			//==============================从网络地址下载文件将其变为本地文件再读进程序中，进行length的获取==================================
			InputStream is1 = null;
			OutputStream out = null;
			URL url = new URL(fileUrl);
			URLConnection connection = url.openConnection();
			is1 = connection.getInputStream();
			File file3 = new File(str1 + File.separator + "12.pdf");
			out = new FileOutputStream(file3);
			byte b[] = new byte[1024];
			int m = 0;
			while ((m = is1.read(b)) > -1) {
				out.write(b);
				out.flush();
			}
			File file4 = new File(str1 + File.separator + "12.pdf");
			InputStream is5 = new FileInputStream(file4);
			int byteNum = is5.available();
			double fileSize = Math.rint(byteNum / 1024);
			//=======================================================	

			String subjectStr = msg.getSubject();
			// getContent() 是获取包裹内容, Part相当于外包装  
			String PNR = null;
			if (subjectStr.contains("#")) {

				PNR = subjectStr.substring(subjectStr.indexOf("#") + 2,
						subjectStr.indexOf(")", subjectStr.indexOf("#")));
			}
			TGrabFileEntity newFile = new TGrabFileEntity();
			BeanUtil.copyProperties(fileProps, newFile);
			String fileName = "link-";
			if (!Util.isEmpty(PNR)) {
				fileName += PNR;
			}
			fileName += ".pdf";
			String attachmentName = fileName;
			int fileSort = 0;
			if (Util.isEmpty(attachmentName)) {
				fileSort = getSort(fileProps.getParentId());
				fileSort += 1;
				attachmentName = "PNR" + fileSort;
			}
			//文件名和序号
			newFile.setFileName(attachmentName);
			fileProps.setSort(fileSort);
			newFile.setUrl(fileUrl);
			newFile.setType(FileTypeEnum.FILE.intKey());//文件
			newFile.setFileSize(fileSize);
			grabFileLst.add(newFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//	}

	}

	/**  
	 * 保存附件  
	 * @param part 邮件中多个组合体中的其中一个组合体  
	 * @param fileName  附件保存目录  
	 * @throws UnsupportedEncodingException  
	 * @throws MessagingException  
	 * @throws FileNotFoundException  
	 * @throws IOException  
	 */
	private void saveAttachment(Part part, List<TGrabFileEntity> grabFileLst, TGrabFileEntity fileProps)
			throws UnsupportedEncodingException, MessagingException, FileNotFoundException, IOException {
		if (part.isMimeType("multipart/*")) {
			//TODO
			Multipart multipart = (Multipart) part.getContent(); //复杂体邮件  
			//复杂体邮件包含多个邮件体  
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				//获得复杂体邮件中其中一个邮件体  
				BodyPart bodyPart = multipart.getBodyPart(i);

				String fileName = bodyPart.getFileName();
				String fileExt = FileUtil.getSuffix(fileName);//获取附件后缀名
				//某一个邮件体也有可能是由多个邮件体组成的复杂体  
				String str1 = System.getProperty("java.io.tmpdir");
				String disp = bodyPart.getDisposition();
				if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
					//向网络上上传一个文件，然后返回一个地址存储到数据库中
					InputStream is = bodyPart.getInputStream();
					String fileUrl = null;
					if ("eml".equals(fileExt)) {
						File file = new File(str1 + File.separator + "12.eml");
						OutputStream os = new FileOutputStream(file);
						int length = 0;
						byte[] b = new byte[1024];
						while ((length = is.read(b)) != -1) {
							os.write(b);
						}
						os.flush();
						File file1 = new File(str1 + File.separator + "12.html");
						String content = null;
						try {
							content = ect.display(file1);
						} catch (Exception e) {

							// TODO Auto-generated catch block
							e.printStackTrace();

						}
						OutputStream os1 = new FileOutputStream(file1);
						byte[] b1 = content.getBytes();
						os.write(b1);

						File file3 = new File(str1 + File.separator + "12.html");

						fileUrl = CommonConstants.IMAGES_SERVER_ADDR
								+ qiniuUploadService.uploadImage(new FileInputStream(file3), "html", null);
						HtmlToPdf.convert(fileUrl, str1 + File.separator + "12.pdf");
						File file2 = new File(str1 + File.separator + "12.pdf");
						fileUrl = CommonConstants.IMAGES_SERVER_ADDR
								+ qiniuUploadService.uploadImage(new FileInputStream(file2), "pdf", null);
					} else {
						fileUrl = uploadFile(is, fileExt);
					}
					//==============================从网络地址下载文件将其变为本地文件再读进程序中，进行length的获取==================================
					InputStream is1 = null;
					OutputStream out = null;
					URL url = new URL(fileUrl);
					URLConnection connection = url.openConnection();
					is1 = connection.getInputStream();
					File file3 = new File(str1 + File.separator + "12.pdf");
					out = new FileOutputStream(file3);
					byte b[] = new byte[1024];
					int m = 0;
					while ((m = is1.read(b)) > -1) {
						out.write(b);
						out.flush();
					}
					File file4 = new File(str1 + File.separator + "12.pdf");
					InputStream is5 = new FileInputStream(file4);

					//=======================================================	
					int byteNum = is5.available();
					double fileSize = Math.rint(byteNum / 1024);
					TGrabFileEntity newFile = new TGrabFileEntity();
					BeanUtil.copyProperties(fileProps, newFile);
					String attachmentName = fileName;
					int fileSort = 0;
					if (Util.isEmpty(attachmentName)) {
						fileSort = getSort(fileProps.getParentId());
						fileSort += 1;
						attachmentName = "PNR" + fileSort;
					}
					//文件名和序号
					newFile.setFileName(attachmentName);
					fileProps.setSort(fileSort);
					newFile.setUrl(fileUrl);
					newFile.setFileSize(fileSize);
					setParentsFileSize(fileProps.getParentId(), fileSize);
					newFile.setType(FileTypeEnum.FILE.intKey());//文件
					grabFileLst.add(newFile);
				} else if (bodyPart.isMimeType("multipart/*")) {
					saveAttachment(bodyPart, grabFileLst, fileProps);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {
						InputStream is = bodyPart.getInputStream();
						String fileUrl = uploadFile(is, fileExt);

						TGrabFileEntity newFile = new TGrabFileEntity();
						BeanUtil.copyProperties(fileProps, newFile);
						String attachmentName = fileName;
						int fileSort = 0;
						if (Util.isEmpty(attachmentName)) {
							fileSort = getSort(fileProps.getParentId());
							fileSort += 1;
							attachmentName = "PNR" + fileSort;
						}
						//文件名和序号
						newFile.setFileName(attachmentName);
						fileProps.setSort(fileSort);
						newFile.setUrl(fileUrl);
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						//==============================从网络地址下载文件将其变为本地文件再读进程序中，进行length的获取==================================
						InputStream is1 = null;
						OutputStream out = null;
						URL url = new URL(fileUrl);
						URLConnection connection = url.openConnection();
						is1 = connection.getInputStream();
						File file3 = new File(str1 + File.separator + "12.pdf");
						out = new FileOutputStream(file3);
						byte b[] = new byte[1024];
						int m = 0;
						while ((m = is1.read(b)) > -1) {
							out.write(b);
							out.flush();
						}
						File file4 = new File(str1 + File.separator + "12.pdf");
						InputStream is5 = new FileInputStream(file4);

						//=======================================================	
						int byteNum = is5.available();
						double fileSize = Math.rint(byteNum / 1024);
						newFile.setFileSize(fileSize);
						setParentsFileSize(fileProps.getParentId(), fileSize);
						newFile.setType(FileTypeEnum.FILE.intKey());//文件
						grabFileLst.add(newFile);
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachment((Part) part.getContent(), grabFileLst, fileProps);
		}
	}

	/**  
	 * 向网络上上传一个文件，然后返回一个地址存储到数据库中
	 * @param is 输入流  
	 * @param fileName 文件名  
	 * @return 文件URL地址
	 */
	private String uploadFile(InputStream is, String fileExt) {
		String fileURL = CommonConstants.IMAGES_SERVER_ADDR + qiniuUploadService.uploadImage(is, fileExt, null);
		//文件存储地址
		logger.info("文件存储地址:" + fileURL);
		return fileURL;
	}

	//通过发件人确定顶层目录文件夹
	/*private TGrabFileEntity getRootFile(int sender) {
		//TODO
		TGrabFileEntity singleFile = dbDao.fetch(TGrabFileEntity.class, Cnd.where("id", "=", 1));
		return singleFile;
	}*/

	//判断文件结构类型
	private FileNavalEnum structureType(String sender) {
		//TODO
		TGrabMailEntity listMail = dbDao.fetch(TGrabMailEntity.class, Cnd.where("sender", "=", sender));
		String senderDb = listMail.getSender();//得到抓取保存到数据库中的发件人
		if (senderDb.contains(GrabMailConstants.FIT_D7) || senderDb.contains(GrabMailConstants.FIT_JQ)
				|| senderDb.contains(GrabMailConstants.FIT_TT)) {
			return FileNavalEnum.FITD7JQTT;
		} else if (senderDb.contains(GrabMailConstants.FIT_QF)) {
			return FileNavalEnum.FITQF;
		} else if (senderDb.contains(GrabMailConstants.FIT_VA)) {
			return FileNavalEnum.FITVA;
		} else if (senderDb.contains(GrabMailConstants.TEAM_JQ) || senderDb.contains(GrabMailConstants.TEAM_TT)) {
			return FileNavalEnum.TEAMJQTT;
		} else if (senderDb.contains(GrabMailConstants.TEAM_VA)) {
			return FileNavalEnum.TEAMVA;
		}
		return FileNavalEnum.FITD7JQTT;
	}

	//根据邮件附件名获取PNR
	private String getAttachmentName() {
		//TODO
		Random random = new Random();
		int ss = random.nextInt();
		if (ss % 2 == 0) {
			return "X4WRZ8";
		} else {
			return null;
		}
	}

	/** 
	 * 文本解码 
	 * @param encodeText 解码MimeUtility.encodeText(String text)方法编码后的文本 
	 * @return 解码后的文本 
	 * @throws UnsupportedEncodingException 
	 */
	public String decodeText(String encodeText) throws UnsupportedEncodingException {
		if (encodeText == null || "".equals(encodeText)) {
			return "";
		} else {
			return MimeUtility.decodeText(encodeText);
		}
	}

	private double convertToM(double fileSize) {
		double a = Math.round(fileSize / 1024);

		return a;
	}

	private String getDate(String str, int flag) {
		//==========JQ中Booking Date样式的时间抓取
		int index = str.indexOf("Booking Date");
		int index1 = str.indexOf("Booking date");
		if (flag == 0 && index != -1) {

			int a = str.indexOf("span", index);
			System.out.println(a);
			str.indexOf("<", str.indexOf(">", a));
			String date = str.substring(str.indexOf(">", a) + 1, str.indexOf("<", str.indexOf(">", a)));
			System.out.println(date);
			return date;
		}

		//==========JQ中Booking date样式的时间抓取
		if (flag == 0 && index1 != -1) {

			int indexOf = str.indexOf("<", index1);
			String substring = str.substring(index1, indexOf);
			String str1[] = substring.split("\\s+");
			String str2 = "";
			for (int j = 0; j < str1.length; j++) {
				if (j >= str1.length - 3) {
					str2 += str1[j] + " ";
				}
			}
			System.out.println(str2 + "===========================");
			System.out.println(substring);
			return str2;
		}

		//==========TT中Booking Date样式的时间抓取
		if (flag == 1 && index != -1) {
			int indexOf = str.indexOf("<", index);
			String substring = str.substring(index, indexOf);
			String str1[] = substring.split("\\s+");
			String str2 = "";
			for (int j = 0; j < str1.length; j++) {
				if (j >= str1.length - 3) {
					str2 += str1[j] + " ";
				}
			}
			System.out.println(str2 + "===========================");
			System.out.println(substring);
			//System.out.println(str);
			return str2;
		}

		return null;
	}

	//根据文件id设置之前所有父文件的大小
	@Aop("txDb")
	public void setParentsFileSize(long id, double size) {
		TGrabFileEntity tGrabFileEntity = dbDao.fetch(TGrabFileEntity.class, Cnd.where("id", "=", id));
		if (Util.isEmpty(tGrabFileEntity.getFileSize())) {

			dbDao.update(TGrabFileEntity.class, Chain.make("fileSize", size), Cnd.where("id", "=", id));
		} else {
			dbDao.update(TGrabFileEntity.class, Chain.make("fileSize", size + tGrabFileEntity.getFileSize()),
					Cnd.where("id", "=", id));

		}
		if (tGrabFileEntity.getParentId() == 0) {
			return;
		}
		setParentsFileSize(tGrabFileEntity.getParentId(), size);
	}

}
