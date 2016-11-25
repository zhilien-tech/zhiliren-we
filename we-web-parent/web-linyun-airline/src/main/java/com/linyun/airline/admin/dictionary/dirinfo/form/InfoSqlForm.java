/**
 * InfoSqlForm.java
 * com.xiaoka.template.admin.dictionary.dirinfo.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirinfo.form;

import java.util.Date;

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
 * @Date	 2016年11月3日 	 
 */
@Data
public class InfoSqlForm implements ISqlForm {

	//字典类别编码
	private String typeCode;
	//字典代码
	private String dictCode;
	//字典信息
	private String dictName;
	//描述
	private String description;
	//状态
	private int status;
	//创建时间
	private Date createTime;

	@Override
	public Sql createPagerSql(IDbDao dbDao, SqlManager sqlManager) {
		Sql sql = Sqls.create(sqlManager.get("dict_info_list"));
		sql.setCondition(cnd());
		return sql;

	}

	@Override
	public Sql createCountSql(IDbDao dbDao, SqlManager sqlManager) {
		Sql sql = Sqls.create(sqlManager.get("dict_info_list_count"));
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.limit();
		if (!Util.isEmpty(dictName)) {
			cnd.and("i.dictName", "LIKE", "%" + dictName + "%");
		}
		return cnd;
	}
}
