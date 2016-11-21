/**
 * TFunctionSqlForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.forms;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.form.ISqlForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月18日 	 
 */
@Data
public class TFunctionSqlForm implements ISqlForm {

	/**
	 * 检索条件
	 */
	private String name;

	/**上级功能*/
	private Integer parentId;

	@Override
	public Sql createPagerSql(IDbDao dbDao, SqlManager sqlManager) {
		Sql sql = Sqls.create(sqlManager.get("function_manage_list"));
		sql.setCondition(cnd());
		return sql;
	}

	@Override
	public Sql createCountSql(IDbDao dbDao, SqlManager sqlManager) {
		Sql sql = Sqls.create(sqlManager.get("function_manage_list_count"));
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.limit();
		if (!Util.isEmpty(name))
			cnd.and("s.name", "LIKE", "%" + name + "%");

		if (!Util.isEmpty(parentId) && parentId > 0) {
			cnd.and("s.parentId", "=", parentId);
		}
		cnd.desc("s.id");
		return cnd;
	}

}
