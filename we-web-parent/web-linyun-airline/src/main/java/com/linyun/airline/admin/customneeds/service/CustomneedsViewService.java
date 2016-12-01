package com.linyun.airline.admin.customneeds.service;

import java.util.Date;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linyun.airline.entities.TCustomerneedsEntity;
import com.linyun.airline.forms.TCustomerneedsAddForm;
import com.linyun.airline.forms.TCustomerneedsUpdateForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class CustomneedsViewService extends BaseService<TCustomerneedsEntity> {
	private static final Log log = Logs.get();

	/**
	 *
	 * TODO添加客户需求信息
	 * <p>
	 * TODO客户需求
	 *
	 * @param addForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("deprecation")
	public Object addCustomNeedsInfo(TCustomerneedsAddForm addForm) {
		if (!Util.isEmpty(addForm.getLeavedateString())) {
			addForm.setLeavedate(new Date(addForm.getLeavedateString()));
		}
		if (!Util.isEmpty(addForm.getBackdateString())) {
			addForm.setBackdate(new Date(addForm.getBackdateString()));
		}
		addForm.setOptime(new Date());
		return this.add(addForm);
	}

	/**
	 * 
	 * TODO修改客户需求信息
	 * <p>
	 * TODO修改客户需求信息
	 *
	 * @param updateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateCustomNeedsInfo(TCustomerneedsUpdateForm updateForm) {
		if (!Util.isEmpty(updateForm.getLeavedateString())) {
			updateForm.setLeavedate(new Date(updateForm.getLeavedateString()));
		}
		if (!Util.isEmpty(updateForm.getBackdateString())) {
			updateForm.setBackdate(new Date(updateForm.getBackdateString()));
		}
		updateForm.setLastupdatetime(new Date());
		return this.update(updateForm);
	}

	/**
	 * 
	 * TODO关闭客户需求信息
	 * <p>
	 * TODO关闭客户需求信息
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object closeCustomNeeds(long id) {
		TCustomerneedsEntity customerneedsEntity = this.fetch(id);
		customerneedsEntity.setIsclose(1);
		return dbDao.update(customerneedsEntity);

	}
}