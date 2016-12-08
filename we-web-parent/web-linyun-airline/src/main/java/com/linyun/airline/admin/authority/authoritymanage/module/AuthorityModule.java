package com.linyun.airline.admin.authority.authoritymanage.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Lists;
import com.linyun.airline.admin.authority.authoritymanage.entity.AuthorityBean;
import com.linyun.airline.admin.authority.authoritymanage.form.TAuthoritySqlForm;
import com.linyun.airline.admin.authority.authoritymanage.service.AuthorityViewService;
import com.linyun.airline.admin.authority.job.entity.TJobEntity;
import com.linyun.airline.admin.authority.job.form.DeptJobForm;
import com.linyun.airline.entities.TDepartmentEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

/**
 * 功能管理	控制类
* @ClassName: AuthorityModule
* @author 崔建斌 
* @date 2016年11月18日 上午11:35:11
 */
@IocBean
@At("/admin/authority/authoritymanage")
@Filters({//@By(type = AuthFilter.class)
})
public class AuthorityModule {

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	/**
	 * 注入容器中管理sql的对象，用于从sql文件中根据key取得sql
	 */
	@Inject
	private SqlManager sqlManager;

	@Inject
	private AuthorityViewService authorityViewService;

	/**
	 * 分页查询
	 * @param sqlForm 
	 * @param pager 
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final TAuthoritySqlForm sqlForm, @Param("..") final Pager pager) {
		return null;
	}

	/**
	 * 服务端分页查询
	 */
	@SuppressWarnings("unchecked")
	@At
	public Object listData(@Param("..") final TAuthoritySqlForm sqlForm) {
		Map<String, Object> map = authorityViewService.listPage4Datatables(sqlForm);
		List<Record> list = (List<Record>) map.get("data");
		List<AuthorityBean> lst = Lists.newArrayList();
		if (!Util.isEmpty(list)) {
			for (Record r : list) {
				AuthorityBean bean = new AuthorityBean();
				bean.setDeptId(r.getInt("id"));
				bean.setDeptName(r.getString("deptName"));
				bean.setModuleName(r.getString("moduleName"));
				bean.setName(r.getString("jobName"));
				lst.add(bean);
			}
		}
		map.put("data", lst);
		return map;
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add(@Param("id") final long id) {
		return authorityViewService.findDeptModule(id);
	}

	/**
	 * 添加保存
	 */
	@At
	@POST
	public Object add(@Param("..") DeptJobForm addForm) {
		authorityViewService.saveDeptJobData(addForm);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return authorityViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") DeptJobForm updateForm) {
		return JsonResult.success("修改成功!");
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		FormUtil.delete(dbDao, TDepartmentEntity.class, id);
		return JsonResult.success("删除成功!");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final long[] ids) {
		FormUtil.delete(dbDao, TDepartmentEntity.class, ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 校验部门名称
	 */
	@At
	@POST
	public Object checkDeptNameExist(@Param("deptName") final String deptName, @Param("id") final long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TDepartmentEntity> dept = dbDao.query(TDepartmentEntity.class, Cnd.where("deptName", "=", deptName), null);
		List<TDepartmentEntity> listdept = dbDao.query(TDepartmentEntity.class, Cnd.where("deptName", "=", deptName)
				.and("id", "=", id), null);
		if (!Util.isEmpty(dept)) {
			if (Util.isEmpty(id)) {
				map.put("valid", false);
			} else if (!Util.isEmpty(listdept)) {
				map.put("valid", true);
			}
		} else {
			map.put("valid", true);
		}
		return map;
	}

	/**
	 * 校验职位名称
	 */
	@At
	@POST
	public Object checkJobNameExist(@Param("name") final String jobName, @Param("id") final long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TJobEntity> Name = dbDao.query(TJobEntity.class, Cnd.where("name", "=", jobName), null);
		List<TJobEntity> listName = dbDao.query(TJobEntity.class, Cnd.where("name", "=", jobName).and("id", "=", id),
				null);
		if (!Util.isEmpty(Name)) {
			if (Util.isEmpty(id)) {
				map.put("valid", false);
			} else if (!Util.isEmpty(listName)) {
				map.put("valid", true);
			}
		} else {
			map.put("valid", true);
		}
		return map;
	}
}