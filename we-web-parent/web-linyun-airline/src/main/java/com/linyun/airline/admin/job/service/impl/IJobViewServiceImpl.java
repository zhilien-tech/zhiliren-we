/**
 * IJobViewServiceImpl.java
 * com.linyun.airline.admin.job.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.job.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.job.service.JobViewService;
import com.linyun.airline.entities.TComFunPosMapEntity;
import com.linyun.airline.entities.TFunctionEntity;
import com.linyun.airline.entities.TJobEntity;
import com.linyun.airline.forms.TJobModForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月18日 	 
 */
@IocBean(name = "jobViewService")
public class IJobViewServiceImpl implements JobViewService {

	@Inject
	private IDbDao dbDao;

	@Override
	public boolean update(TJobModForm form) {
		//修改角色实体
		FormUtil.modify(dbDao, form, TJobEntity.class);
		updateJobFunctionMap(form);
		return true;

	}

	/**修改职位功能关系*/
	public void updateJobFunctionMap(TJobModForm form) {

		String functionIds = form.getFunctionIds();
		//根据职位查询关系
		Long jobId = form.getId();
		List<TComFunPosMapEntity> before = dbDao.query(TComFunPosMapEntity.class, Cnd.where("jobId", "=", jobId), null);
		//欲更新为
		List<TComFunPosMapEntity> after = new ArrayList<TComFunPosMapEntity>();

		if (!Util.isEmpty(functionIds)) {
			String[] funIds = functionIds.split(",");
			for (String funId : funIds) {
				TComFunPosMapEntity each = new TComFunPosMapEntity();
				each.setJobId(jobId);
				each.setFunId(Long.valueOf(funId));

				after.add(each);
			}
		}
		dbDao.updateRelations(before, after);
	}

	@Override
	public Map<String, Object> findJob(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		//全部功能
		List<TFunctionEntity> allFunc = dbDao.query(TFunctionEntity.class, null, null);
		//根据角色查询关系
		List<TComFunPosMapEntity> relation = dbDao.query(TComFunPosMapEntity.class, Cnd.where("jobId", "=", id), null);
		//如果角色有功能
		if (!Util.isEmpty(relation)) {
			//该角色的功能id集合
			List<Long> existsFuncIds = new ArrayList<Long>();
			for (TComFunPosMapEntity r : relation) {
				existsFuncIds.add(r.getFunId());
			}

			for (TFunctionEntity f : allFunc) {
				if (existsFuncIds.contains(f.getId())) {
					f.setChecked("true");
				}
			}
		}
		obj.put("list", allFunc);
		obj.put("job", dbDao.fetch(TJobEntity.class, id));
		return obj;
	}

}
