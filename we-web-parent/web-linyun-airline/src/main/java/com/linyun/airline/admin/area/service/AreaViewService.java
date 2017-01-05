package com.linyun.airline.admin.area.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.user.service.UserViewService;
import com.linyun.airline.common.result.Select2Option;
import com.linyun.airline.entities.TAreaEntity;
import com.linyun.airline.entities.TUserAreaMapEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TAreaAddForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class AreaViewService extends BaseService<TAreaEntity> {

	@Inject
	private UserViewService userViewService;

	//添加区域保存
	public Object addAreaName(TAreaAddForm addForm, final HttpSession session) {
		//通过session获取当前登录用户的id
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Long userId = user.getId();//得到用户的id
		//先添加区域
		TAreaEntity areaEntity = new TAreaEntity();
		areaEntity.setAreaName(addForm.getAreaName());
		areaEntity.setCreateTime(new Date());
		areaEntity.setRemark(addForm.getRemark());
		areaEntity = dbDao.insert(areaEntity);
		//获取到区域id
		Long areaId = areaEntity.getId();
		//往用户区域表中填数据
		TUserAreaMapEntity areaEntities = new TUserAreaMapEntity();
		areaEntities.setAreaId(areaId);
		areaEntities.setUserId(userId);
		dbDao.insert(areaEntities);
		return JsonResult.success("添加成功!");
	}

	//校验区域名称唯一性
	public Object checkAreaNameExist(final String areaName, final Long areaId, final HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		//通过session获取当前登录用户的id
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		Long userId = user.getId();//得到用户的id
		TUserAreaMapEntity singleUserArea = dbDao.fetch(TUserAreaMapEntity.class,
				Cnd.where("userId", "=", userId).and("areaId", "=", areaId));
		int count = 0;
		if (!Util.isEmpty(singleUserArea)) {
			Long uAreaId = singleUserArea.getId();//得到用户负责区域id
			if (Util.isEmpty(areaId)) {
				//add
				count = nutDao.count(TUserAreaMapEntity.class,
						Cnd.where("areaName", "=", areaName).and("areaId", "=", areaId).and("userId", "=", userId));
			} else {
				//update
				count = nutDao.count(TUserAreaMapEntity.class, Cnd.where("id", "=", uAreaId)
						.and("areaId", "!=", areaId).and("userId", "=", userId));
			}
		}
		map.put("valid", count <= 0);
		return map;
	}

	//区域
	public Object areaSelect2(String dictAreaName, final String selectedAreaIds) {
		List<Record> dictNameList = userViewService.getAreaNameList(dictAreaName, selectedAreaIds);
		List<Select2Option> result = transform2SelectOptions(dictNameList);
		return result;
	}

	private List<Select2Option> transform2SelectOptions(List<Record> dictNameList) {
		return Lists.transform(dictNameList, new Function<Record, Select2Option>() {
			@Override
			public Select2Option apply(Record record) {
				Select2Option op = new Select2Option();
				op.setId(record.getInt("areaId"));
				op.setText(record.getString("areaName"));
				return op;
			}
		});
	}
}