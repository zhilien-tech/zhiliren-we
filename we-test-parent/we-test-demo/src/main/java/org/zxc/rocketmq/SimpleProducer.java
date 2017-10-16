/**
 * SimpleProducer.java
 * org.zxc.rocketmq
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2017年10月13日 	 
 */
public class SimpleProducer {

	public static void main(String[] args) {
		String namesrvAddr = "192.168.1.27:9876";
		DefaultMQProducer producer = new DefaultMQProducer("Producer");
		// 必须要设置nameserver地址  
		producer.setNamesrvAddr(namesrvAddr);
		try {
			producer.start();
			for (long i = 0l; i < 3; i++) {
				Message msg = new Message("topic" + i, "push" + i, "1", ("第" + i + "内容").getBytes());
				SendResult result = producer.send(msg);
				System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.shutdown();
		}
	}

}
