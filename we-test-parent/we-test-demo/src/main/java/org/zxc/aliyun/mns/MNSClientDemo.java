/**
 * MNSClientDemo.java
 * org.zxc.aliyun.mns
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.aliyun.mns;

import java.util.Vector;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudPullTopic;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.QueueMeta;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.aliyun.mns.model.TopicMeta;

/**
 * 阿里云消息队列demo
 * <p>
 *
 * @author   朱晓川
 * @Date	 2017年9月22日 	 
 */
public class MNSClientDemo {

	private static final String accessKeyId = "EkKyoEDW2mksVqaD";
	private static final String accessKeySecret = "ennGwoc2bSd7yrWd2WLlmozU29EgEd";
	private static final String endpoint = "http://1000306809770040.mns.cn-qingdao.aliyuncs.com/";

	public static void main(String[] args) {

		CloudAccount account = new CloudAccount(accessKeyId, accessKeySecret, endpoint);
		MNSClient client = account.getMNSClient();
		// build consumer name list.
		Vector<String> consumerNameList = new Vector<String>();
		String consumerName1 = "consumer001";
		String consumerName2 = "consumer002";
		String consumerName3 = "consumer003";
		consumerNameList.add(consumerName1);
		consumerNameList.add(consumerName2);
		consumerNameList.add(consumerName3);

		QueueMeta queueMetaTemplate = new QueueMeta();
		queueMetaTemplate.setPollingWaitSeconds(30);
		try {
			/**producer code:*/
			// create pull topic which will send message to 3 queues for consumer.
			//创建主题，并且发送消息给3个消费者的队列
			String topicName = "demo-topic-for-pull";//主题名称
			TopicMeta topicMeta = new TopicMeta();
			topicMeta.setTopicName(topicName);
			CloudPullTopic pullTopic = client.createPullTopic(topicMeta, consumerNameList, true, queueMetaTemplate);
			//消息主体
			String messageBody = "hello the world.I'm message body";
			// if we sent raw message,then should use getMessageBodyAsRawString to parse the message body correctly.
			TopicMessage tMessage = new RawTopicMessage();
			tMessage.setBaseMessageBody(messageBody);
			pullTopic.publishMessage(tMessage);
			/**consumer code:*/
			//3 consumers receive the message.
			CloudQueue cloudQueue1 = client.getQueueRef(consumerName1);
			CloudQueue cloudQueue2 = client.getQueueRef(consumerName2);
			CloudQueue cloudQueue3 = client.getQueueRef(consumerName3);
			Message consumer1Msg = cloudQueue1.popMessage(30);
			if (consumer1Msg != null) {
				System.out.println("consumer1 receive message:" + consumer1Msg.getMessageBodyAsRawString());
			} else {
				System.out.println("the queue is empty");
			}
			Message consumer2Msg = cloudQueue2.popMessage(30);
			if (consumer2Msg != null) {
				System.out.println("consumer2 receive message:" + consumer2Msg.getMessageBodyAsRawString());
			} else {
				System.out.println("the queue is empty");
			}
			Message consumer3Msg = cloudQueue3.popMessage(30);
			if (consumer3Msg != null) {
				System.out.println("consumer3 receive message:" + consumer3Msg.getMessageBodyAsRawString());
			} else {
				System.out.println("the queue is empty");
			}
			// delete the fullTopic.
			//删除主题
			pullTopic.delete();
		} catch (ClientException ce) {
			System.out.println("Something wrong with the network connection between client and MNS service."
					+ "Please check your network and DNS availablity.");
			ce.printStackTrace();
		} catch (Exception se) {
			/*you can get more MNS service error code in following link.
			  https://help.aliyun.com/document_detail/mns/api_reference/error_code/error_code.html?spm=5176.docmns/api_reference/error_code/error_response
			*/
			se.printStackTrace();
		}
		client.close();

	}

}
