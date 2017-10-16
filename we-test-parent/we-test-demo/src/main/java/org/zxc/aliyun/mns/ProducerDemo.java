/**
 * ProducerDemo.java
 * org.zxc.aliyun.mns
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.aliyun.mns;

import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;

/**
 * 阿里云消息队列发送消息示例
 * <p>
 *
 * @author   朱晓川
 * @Date	 2017年9月22日 	 
 */
public class ProducerDemo {

	private static Log log = Logs.get();

	private static final String accessKeyId = "EkKyoEDW2mksVqaD";
	private static final String accessKeySecret = "ennGwoc2bSd7yrWd2WLlmozU29EgEd";
	private static final String endpoint = "http://1000306809770040.mns.cn-qingdao.aliyuncs.com/";

	public static void main(String[] args) {
		CloudAccount account = new CloudAccount(accessKeyId, accessKeySecret, endpoint);
		//这个client仅初始化一次
		MNSClient client = account.getMNSClient();
		//循环发送10条消息
		try {
			//TestQueue是你的测试队列，请提前创建
			CloudQueue queue = client.getQueueRef("TestQueue");
			for (int i = 0; i < 10; i++) {
				Message message = new Message();
				message.setMessageBody("I am test message " + i);
				message.setPriority(8);
				Message putMsg = queue.putMessage(message);
				System.out.println("Send message id is: " + putMsg.getMessageId());
			}
		} catch (ClientException ce) {
			System.out.println("Something wrong with the network connection between client and MNS service."
					+ "Please check your network and DNS availablity.");
			ce.printStackTrace();
		} catch (ServiceException se) {
			se.printStackTrace();
			log.error("MNS exception requestId:" + se.getRequestId(), se);
			if (se.getErrorCode() != null) {
				if (se.getErrorCode().equals("QueueNotExist")) {
					System.out.println("Queue is not exist.Please create before use");
				} else if (se.getErrorCode().equals("TimeExpired")) {
					System.out.println("The request is time expired. Please check your local machine timeclock");
				}
				/*
				you can get more MNS service error code from following link:
				https://help.aliyun.com/document_detail/mns/api_reference/error_code/error_code.html
				*/
			}
		} catch (Exception e) {
			System.out.println("Unknown exception happened!");
			e.printStackTrace();
		}
		client.close();
	}

}
