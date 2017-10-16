/**
 * RocketMQProducer.java
 * org.zxc.rocketmq
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq;

import java.util.List;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;

/**
 * rocketmq消息生产者
 * <p>
 *
 * @author   朱晓川
 * @Date	 2017年10月13日 	 
 */
public class Producer1 {

	public static void main(String[] args) throws Exception {
		String topicName = "TopicTest1";
		String tagName = "testTag";
		String namesrvAddr = "192.168.1.27:9876";
		String key = "ID001";
		DefaultMQProducer producer = new DefaultMQProducer("Producer");
		// 必须要设置nameserver地址  
		producer.setNamesrvAddr(namesrvAddr);
		try {
			//          producer.setClientIP("**");  
			//设置producer实例名称  
			producer.setInstanceName("pd1");
			//设置重试的次数  
			producer.setRetryTimesWhenSendFailed(3);
			//开启生产者  
			producer.start();

			//创建一条消息  
			Message msg = new Message(topicName, tagName, key, "内容一".getBytes());
			//发送消息  
			SendResult result = producer.send(msg);
			//发送，并触发回调函数  
			producer.send(msg, new SendCallback() {

				@Override
				//成功的回调函数  
				public void onSuccess(SendResult sendResult) {
					System.out.println(sendResult.getSendStatus());
					System.out.println("成功了");
				}

				@Override
				//出现异常的回调函数  
				public void onException(Throwable e) {
					System.out.println("失败了" + e.getMessage());
				}
			});

			//获取某个主题的消息队列  
			List<MessageQueue> messageQueues = producer.fetchPublishMessageQueues(topicName);
			System.out.println(messageQueues.size());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.shutdown();
		}
	}

}
