package org.zxc.rocketmq.ordermessage;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

/**
 * 顺序消息Producer
 * <p>
 * 顺序，不是全局顺序，只是分区顺序。要全局顺序只能一个分区。发送消息的时候，消息发送默认是会采用轮询的方式发送到不同的queue（分区）。
 * 同一条queue里面，RocketMQ的确是能保证FIFO的。那么要做到顺序消息，只要把消息确保投递到同一条queue即可。
 * @author   朱晓川
 * @Date	 2017年11月2日
 */
public class OrderMsgProducer {

	public static void main(String[] args) throws UnsupportedEncodingException {
		try {
			DefaultMQProducer producer = new DefaultMQProducer("order_msg_producer_group_1");

			String namesrvAddr = "192.168.1.27:9876";
			producer.setNamesrvAddr(namesrvAddr);
			producer.start();

			for (int i = 0; i < 100; i++) {
				int orderId = i % 10;
				Message msg = new Message("OrderMessageTopicTest", "3C_Order_Tag", "KEY" + i,
						("order_" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

				/**
				 * MessageQueueSelector的实现决定了将消息投递到哪一个队列：
				 * 相同订单号的--->有相同的模--->有相同的queue。
				 */
				SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
					@Override
					public MessageQueue select(List<MessageQueue> mqs, Message msg, Object orderId) {
						/*
						 *  这里的arg 就是 orderId，也就是producer.send(Message,MessageQueueSelector,arg)
						 *  的第三个参数
						 */
						Integer id = (Integer) orderId;
						int index = id % mqs.size();
						return mqs.get(index);
					}
					//传递订单id用于选择消息发送到哪个队列
				}, orderId);

				System.out.printf("%s%n", sendResult);
			}

			producer.shutdown();
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
