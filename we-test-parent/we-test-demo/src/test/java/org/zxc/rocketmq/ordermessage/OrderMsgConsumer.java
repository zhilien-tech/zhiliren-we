/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zxc.rocketmq.ordermessage;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

public class OrderMsgConsumer {

	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_msg_consumer_group_1");

		String namesrvAddr = "192.168.1.27:9876";
		consumer.setNamesrvAddr(namesrvAddr);

		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

		consumer.subscribe("OrderMessageTopicTest", "3C_Order_Tag");

		consumer.registerMessageListener(new MessageListenerOrderly() {
			AtomicLong consumeTimes = new AtomicLong(0);

			@Override
			public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
				context.setAutoCommit(false);
				this.consumeTimes.incrementAndGet();

				// 设置自动提交  
				for (MessageExt msg : msgs) {
					System.out.println(Thread.currentThread().getName() + ",Receive New Messages:"
							+ new String(msg.getBody()));
				}

				if ((this.consumeTimes.get() % 2) == 0) {
					return ConsumeOrderlyStatus.SUCCESS;
				} else if ((this.consumeTimes.get() % 3) == 0) {
					return ConsumeOrderlyStatus.ROLLBACK;
				} else if ((this.consumeTimes.get() % 4) == 0) {
					return ConsumeOrderlyStatus.COMMIT;
				} else if ((this.consumeTimes.get() % 5) == 0) {
					context.setSuspendCurrentQueueTimeMillis(3000);
					return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
				}

				return ConsumeOrderlyStatus.SUCCESS;
			}
		});

		consumer.start();
		System.out.printf("Consumer Started.%n");
	}

}
