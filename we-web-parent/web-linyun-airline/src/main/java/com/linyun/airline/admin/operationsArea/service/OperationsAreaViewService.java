package com.linyun.airline.admin.operationsArea.service;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linyun.airline.admin.operationsArea.form.TMessageAddForm;
import com.linyun.airline.entities.TMessageEntity;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class OperationsAreaViewService extends BaseService<TMessageEntity> {
	private static final Log log = Logs.get();

	//添加自定义事件
	public Object addCsutomerEvent(TMessageAddForm addForm) {
		addForm.getGenerateTime();
		return this.add(addForm);
	}
}