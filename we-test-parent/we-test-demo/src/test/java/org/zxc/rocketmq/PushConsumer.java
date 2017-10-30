/**
 * RocketMQConsumerDemo.java
 * org.zxc.rocketmq
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * "推"方式消费消息
 * <p>
 *
 * @author   朱晓川
 * @Date	 2017年10月13日 	 
 */
public class PushConsumer {

	public static void main(String[] args) {
		String namesrvAddr = "192.168.1.27:9876";
		//push consumer
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("push_consumer_test_group1");
		consumer.setNamesrvAddr(namesrvAddr);
		try {

			/*
			 * Subscribe one more more topics to consume.
			 * *；TagA || TagC || TagD;TagA
			 */
			//			consumer.subscribe("PushTopicTest", "*");
			consumer.subscribe("PushTopicTest", "TagA || TagC || TagD");
			// 订阅PushTopic下Tag为push的消息,都订阅消息  
			//			consumer.subscribe("PushTopicTest", "push");

			//设置第一次启动的时候队列的什么位置开始消费
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

			//可以修改每次消费消息的数量，默认设置是每次消费一条  
			// consumer.setConsumeMessageBatchMaxSize(10);  

			//注册消费的监听   
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				//在此监听中消费信息，并返回消费的状态信息  
				@Override
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
						ConsumeConcurrentlyContext context) {

					try {
						System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs);
					} catch (Exception e) {
						e.printStackTrace();
						return ConsumeConcurrentlyStatus.RECONSUME_LATER;
					}

					// msgs中只收集同一个topic，同一个tag，并且key相同的message  
					// 会把不同的消息分别放置到不同的队列中  
					for (Message msg : msgs) {
						System.out.println(new String(msg.getBody()));
					}
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});

			consumer.start();

			//5秒后挂载消费端消费，挂载之后就不会再消费了
			//			Thread.sleep(5000);
			//			consumer.suspend();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
