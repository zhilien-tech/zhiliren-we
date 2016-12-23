package com.linyun.airline.admin.area.service;

import java.util.HashMap;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.dictionary.dirinfo.service.impl.InfoServiceImpl;
import com.linyun.airline.entities.TAreaEntity;
import com.linyun.airline.forms.TAreaAddForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class AreaViewService extends BaseService<TAreaEntity> {

	@Inject
	private InfoServiceImpl iInfoService;

	//校验名称名称唯一性
	public Object checkAreaNameExist(final String areaName, final Long areaId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(areaId)) {
			//add
			count = nutDao.count(TAreaEntity.class, Cnd.where("areaName", "=", areaName));
		} else {
			//update
			count = nutDao.count(TAreaEntity.class, Cnd.where("areaName", "=", areaName).and("id", "!=", areaId));
		}
		map.put("valid", count <= 0);
		return map;
	}

	//区域名称保存
	public Object addAreaName(TAreaAddForm addForm) {
		TAreaEntity areaEntity = this.add(addForm);
		return dbDao.insert(areaEntity);
	}

}