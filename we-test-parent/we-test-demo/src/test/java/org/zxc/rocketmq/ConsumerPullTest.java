/**
 * ConsumerPullTest.java
 * org.zxc.rocketmq
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.MessageQueueListener;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.uxuexi.core.common.util.Util;

/**
 * @author   朱晓川
 * @Date	 2017年10月24日 	 
 */
public class ConsumerPullTest {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerPullTest.class);

	public static void main(String[] args) {
		DefaultMQPullConsumer consumer = new DefaultMQPullConsumer();
		consumer.setNamesrvAddr("192.168.1.27:9876");
		consumer.setConsumerGroup("ConsumerPullTest_group");
		try {
			consumer.start();

			Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("PushTopicTest");
			int subedCnt = Util.isEmpty(messageQueues) ? 0 : messageQueues.size();
			logger.info("主题:PushTopicTest已注册队列数:" + subedCnt);
			logger.info("============================");
			for (MessageQueue messageQueue : messageQueues) {
				logger.info("queue:" + messageQueue.getQueueId());
			}

			//消息队列的监听  
			consumer.registerMessageQueueListener("", new MessageQueueListener() {

				@Override
				//消息队列有改变，就会触发  
				public void messageQueueChanged(String topic, Set<MessageQueue> mqAll, Set<MessageQueue> mqDivided) {
					logger.info("检测到队列变化，topic:" + topic);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
