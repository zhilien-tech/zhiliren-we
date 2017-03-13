/**
 * TAirlinePolicyAddForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.common.enums.AirlinePolicyEnum;
import com.linyun.airline.entities.TAirlinePolicyEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月11日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TAirlinePolicyForm extends DataTablesParamForm {
	/**id*/
	private long id;
	/**文件名字*/
	private String fileName;
	/**文件路径*/
	private String url;
	/**文件大小*/
	private String fileSize;
	/**状态*/
	private int status;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;

	@Override
	public Sql sql(SqlManager sqlManager) {

		String sqlString = EntityUtil.entityCndSql(TAirlinePolicyEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;

	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		cnd.and("status", "=", AirlinePolicyEnum.ENABLE.intKey());
		return cnd;
	}
}
