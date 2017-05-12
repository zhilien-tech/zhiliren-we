/**
 * UpDateTest.java
 * com.xiaoka.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.xiaoka.test;

import com.uxuexi.core.common.util.DateUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年5月12日 	 
 */
public class UpDateTest {

	public static void main(String[] args) {

		Object obj = UpgradeDateUtil.getLastWeekDay(DateUtil.nowDate());
		System.out.println(obj);

	}
}
