package com.linyun.airline.admin.authority.authoritymanage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.Param;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.admin.authority.job.entity.TJobEntity;
import com.linyun.airline.admin.authority.job.form.DeptJobForm;
import com.linyun.airline.admin.authority.job.form.TJobModForm;
import com.linyun.airline.entities.TComFunPosMapEntity;
import com.linyun.airline.entities.TDepartmentEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年12月5日 	 
 */
@IocBean(name = "authorityViewService")
public class AuthorityViewService extends BaseService<DeptJobForm> {
	//设置部门模块
	public Map<String, Object> findDeptModule(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		//全部模块,即就是全部功能
		List<TFunctionEntity> allModule = dbDao.query(TFunctionEntity.class, null, null);
		//根据职位查询关系
		List<TComFunPosMapEntity> relation = dbDao.query(TComFunPosMapEntity.class, Cnd.where("jobId", "=", id), null);
		//如果职位有功能
		if (!Util.isEmpty(relation)) {
			//职位功能集合
			List<TFunctionEntity> jobFuncs = Lists.transform(relation,
					new Function<TComFunPosMapEntity, TFunctionEntity>() {
						@Override
						public TFunctionEntity apply(TComFunPosMapEntity arg) {
							TFunctionEntity func = new TFunctionEntity();
							func.setId(arg.getJobId());
							return func;
						}
					});
			for (TFunctionEntity f : allModule) {
				if (jobFuncs.contains(f)) {
					f.setChecked("true");
				}
			}
		}
		obj.put("list", allModule);
		obj.put("job", dbDao.fetch(TJobEntity.class, id));
		return obj;
	}

	/**修改职位功能关系*/
	private void updateJobFunctionMap(TJobModForm form) {
		String functionIds = form.getFunctionIds();
		//根据职位查询关系
		Long jobId = form.getId();
		List<TComFunPosMapEntity> before = dbDao.query(TComFunPosMapEntity.class, Cnd.where("jobId", "=", jobId), null);
		//欲更新为
		List<TComFunPosMapEntity> after = new ArrayList<TComFunPosMapEntity>();
		if (!Util.isEmpty(functionIds)) {
			Iterable<String> funcIds = Splitter.on(",").omitEmptyStrings().split(functionIds);
			for (String fid : funcIds) {
				TComFunPosMapEntity each = new TComFunPosMapEntity();
				each.setJobId(jobId);
				each.setCompanyFunId(Long.valueOf(fid));
				after.add(each);
			}
		}
		dbDao.updateRelations(before, after);
	}

	//保存数据
	public void saveDeptJobData(@Param("..") DeptJobForm addForm) {
		//1,先添加部门，拿到部门id
		TDepartmentEntity tDepartmentEntity = new TDepartmentEntity();
		tDepartmentEntity.setDeptName(addForm.getDeptName());
		//2，再添加职位，获取公司id，同时往公司职位表添加数据
		TJobEntity tJobEntity = new TJobEntity();
		tJobEntity.setName(addForm.getJobName());
		//3，截取功能模块id，根据功能id和公司id查询出公司功能id，用公司功能id和职位id往公司功能职位表添加数据

	}
}