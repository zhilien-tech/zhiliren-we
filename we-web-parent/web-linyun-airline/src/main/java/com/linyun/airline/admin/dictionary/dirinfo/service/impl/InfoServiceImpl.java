/**
 * InfoServiceImpl.java
 * com.xiaoka.template.admin.dictionary.dirinfo.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirinfo.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.dictionary.dirinfo.form.InfoModForm;
import com.linyun.airline.admin.dictionary.dirinfo.service.IInfoService;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.DictTypeEntity;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@IocBean(name = "iInfoService")
public class InfoServiceImpl extends BaseService<DictInfoEntity> implements IInfoService {

	@Inject
	private IDbDao dbDao;

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

}
