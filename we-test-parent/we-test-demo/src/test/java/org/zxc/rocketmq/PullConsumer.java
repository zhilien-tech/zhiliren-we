/**
 * PullConsumer.java
 * org.zxc.rocketmq
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.collections.Maps;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;

/**
 * 2 "拉"方式消费消息
 * @author   朱晓川
 * @Date	 2017年10月20日 	 
 */
public class PullConsumer {

	private static final Map<MessageQueue, Long> offseTable = Maps.newHashMap();

	public static void main(String[] args) throws MQClientException {
		//pull consumer
		DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("pull_consumer_test_group");
		consumer.setNamesrvAddr("192.168.1.27:9876");
		consumer.start();
		//获取订阅topic的queue  
		Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("PullTopicTest");
		for (MessageQueue mq : mqs) {
			System.out.println("=============Consume from the queue: " + mq.getQueueId() + " " + mq);
			SINGLE_MQ: while (true) {
				try {//阻塞的拉去消息，中止时间默认20s  
					PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
					System.out.println(Thread.currentThread().getName() + new Date() + "" + pullResult);
					putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
					switch (pullResult.getPullStatus()) {
					case FOUND://pullSataus 
						List<MessageExt> msgFoundList = pullResult.getMsgFoundList();
						for (Message msg : msgFoundList) {
							System.out.println("Consume message: " + new String(msg.getBody()));
						}
						break;
					case NO_MATCHED_MSG:
						break;
					case NO_NEW_MSG:
						break SINGLE_MQ;
					case OFFSET_ILLEGAL:
						break;
					default:
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		consumer.shutdown();
	}

	private static long getMessageQueueOffset(MessageQueue mq) {
		Long offset = offseTable.get(mq);
		if (offset != null)
			return offset;

		return 0;
	}

	private static void putMessageQueueOffset(MessageQueue mq, long offset) {
		offseTable.put(mq, offset);
	}

}
