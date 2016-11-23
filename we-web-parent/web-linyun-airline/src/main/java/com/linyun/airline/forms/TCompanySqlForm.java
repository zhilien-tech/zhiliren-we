/**
 * TCompanySqlForm.java
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
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2016年11月21日 	 
 */
@Data
public class TCompanySqlForm implements ISqlForm {

	/**公司名称*/
	private String companyName;

	@Override
	public Sql createPagerSql(IDbDao paramIDbDao, SqlManager paramSqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		//		String sqlString = EntityUtil.entityCndSql(TCompanyEntity.class);
		String sqlString = paramSqlManager.get("company_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	@Override
	public Sql createCountSql(IDbDao paramIDbDao, SqlManager paramSqlManager) {

		String sqlString = paramSqlManager.get("company_list_count");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.limit();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(companyName)) {
			cnd.and("t.comName", "LIKE", "%" + companyName + "%").or("t.connect", "LIKE", "%" + companyName + "%")
					.or("t.mobile", "LIKE", "%" + companyName + "%");
		}
		cnd.and("t.deletestatus", "=", 0);
		return cnd;
	}
}
