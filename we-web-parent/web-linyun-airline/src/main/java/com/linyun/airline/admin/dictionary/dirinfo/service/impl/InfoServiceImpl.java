/**
 * InfoServiceImpl.java
 * com.xiaoka.template.admin.dictionary.dirinfo.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
<<<<<<< HEAD
*/

package com.linyun.airline.admin.dictionary.dirinfo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.dictionary.dirinfo.form.InfoModForm;
import com.linyun.airline.admin.dictionary.dirinfo.service.IInfoService;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.DictTypeEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@IocBean(name = "iInfoService")
public class InfoServiceImpl extends BaseService<DictInfoEntity> implements IInfoService {

	@Override
	public boolean update(InfoModForm form) {
		// 修改字典类型实体
		FormUtil.modify(dbDao, form, DictInfoEntity.class);
		return true;
	}

	@Override
	public Map<String, Object> findDirinfo(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("dirtype", dbDao.query(DictTypeEntity.class, null, null));
		obj.put("dirinfo", dbDao.fetch(DictInfoEntity.class, id));
		return obj;
	}

	/**
	 * 
	 * 获取字典类别名称下拉框
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 * @param sqlManager
	 * @param departmentForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("hiding")
	public List<Record> getTypeNameSelect(SqlManager sqlManager) {
		String sqlString = EntityUtil.entityCndSql(DictTypeEntity.class);
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	//分页显示实现方法
	/*public Map<String, Object> listData(@Param("..") final InfoSqlForm sqlForm) {
		Map<String, Object> map = infoService.listPage4Datatables(sqlForm);
		List<Record> list = (List<Record>) map.get("data");
		List<DictInfoDto> lst = Lists.newArrayList();
		if (!Util.isEmpty(list)) {
			for (Record r : list) {
				DictInfoDto en = new DictInfoDto();
				en.setStatus(r.getInt("status"));
				en.setId(r.getInt("id"));
				en.setDictcode(r.getString("dictCode"));
				en.setTypecode(r.getString("typeCode"));
				en.setDictname(r.getString("dictName"));
				en.setDescription(r.getString("description"));
				en.setTypename(r.getString("typeName"));
				en.setCreatetime(r.getTimestamp("createTime"));
				lst.add(en);
			}
		}
		map.put("data", lst);
	}*/
}
