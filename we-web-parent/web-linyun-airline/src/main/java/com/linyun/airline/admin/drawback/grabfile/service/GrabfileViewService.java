package com.linyun.airline.admin.drawback.grabfile.service;

import java.util.HashMap;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.drawback.grabfile.entity.TGrabFileEntity;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class GrabfileViewService extends BaseService<TGrabFileEntity> {

	//添加时根据pid查询数据
	public Map<String, Object> superFolder(int pid) {
		Map<String, Object> obj = new HashMap<String, Object>();
		TGrabFileEntity dirfolder = dbDao.fetch(TGrabFileEntity.class,
				Cnd.where("status", "=", DataStatusEnum.ENABLE.intKey()).and("id", "=", pid));
		obj.put("dirfolder", dirfolder);
		return obj;
	}
}