/**
 * TxStatusHolder.java
 * org.zxc.rocketmq.tx
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq.tx.local;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 事务状态容器,保存事务执行的状态。
 * <p>
 * 也可以用数据库表的形式进行存储
 * @author   朱晓川
 * @Date	 2017年10月30日 	 
 */
public class TxStatusHolder {
	/**
	 * false 表示事务执行失败，true表示执行成功。key代表事务的id
	 */
	private static Map<String, Boolean> statMap = Maps.newConcurrentMap();

	public static void setTxStat(String txId, boolean status) {
		statMap.put(txId, status);
	}

	public static Boolean getTxStat(String txId) {
		return statMap.get(txId);
	}

}
