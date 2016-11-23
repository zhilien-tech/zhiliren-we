/**
 * TCustomerInfoQueryForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2016, 北京科技有限公司版权所有.
 */

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2016年11月20日 	 
 */
package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.QueryForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCustomerInfoQueryForm extends QueryForm {

	/**客户 电话 负责人*/
	private String name;

	/**是否签约（未签约、已签约、禁止合作）*/
	private String contract;

	/**是否禁用*/
	private String forbid;

	@Override
	public Cnd createCnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(name)) {
			cnd.and("name", "LIKE", "%" + name + "%").or("agent", "LIKE", "%" + name + "%")
					.or("telephone", "LIKE", "%" + name + "%");
		}
		if (!Util.isEmpty(contract)) {
			cnd.and("contract", "=", contract);
			System.out.println("ccc");
		}
		if (!Util.isEmpty(forbid)) {
			cnd.and("forbid", "=", forbid);
			System.out.println("ffff");
		}

		return cnd;
	}

}
