/**
 * SimpleConsumer.java
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
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2017年10月13日 	 
 */
public class SimpleConsumer {

	public static void main(String[] args) {
		String namesrvAddr = "192.168.1.27:9876";
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("broker-a");
		consumer.setNamesrvAddr(namesrvAddr);
		try {
			// 订阅PushTopic下Tag为push的消息,都订阅消息  
			consumer.subscribe("TopicTest", "TagA");

			// 程序第一次启动从消息队列头获取数据  
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			consumer.registerMessageListener(new MessageListenerConcurrently() {

				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
						ConsumeConcurrentlyContext context) {
					// msgs中只收集同一个topic，同一个tag，并且key相同的message  
					// 会把不同的消息分别放置到不同的队列中  
					for (Message msg : msgs) {

						System.out.println(new String(msg.getBody()));
					}

					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});

			consumer.resume();
			consumer.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			consumer.shutdown();
		}
	}

}
