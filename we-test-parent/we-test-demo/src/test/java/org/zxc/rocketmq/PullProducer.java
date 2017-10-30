/**
 * PullProducer.java
 * org.zxc.rocketmq
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * @author   朱晓川
 * @Date	 2017年10月20日 	 
 */
public class PullProducer {

	public static void main(String[] args) throws MQClientException, InterruptedException {

		DefaultMQProducer producer = new DefaultMQProducer("pull_producer_test_group");
		producer.setNamesrvAddr("192.168.1.27:9876");
		producer.start();

		for (int i = 6; i < 10; i++) {
			try {
				Message msg = new Message("PullTopicTest",// topic
						"TagA",// tag
						("Hello RocketMQ " + i).getBytes()// body

				);

				SendResult sendResult = producer.send(msg);
				System.out.println(sendResult);
				Thread.sleep(6000);

			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(3000);
			}

		}

		producer.shutdown();

	}

}
