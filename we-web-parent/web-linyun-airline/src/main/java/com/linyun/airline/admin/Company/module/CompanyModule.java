package com.linyun.airline.admin.Company.module;

import java.util.HashMap;
import java.util.Map;

import org.nutz.dao.SqlManager;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.Company.service.CompanyViewService;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.forms.TCompanyAddForm;
import com.linyun.airline.forms.TCompanySqlForm;
import com.linyun.airline.forms.TCompanyUpdateForm;
import com.linyun.airline.forms.TCompanyUserSqlForm;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

@IocBean
@At("/admin/Company")
@Filters({//@By(type = AuthFilter.class)
})
public class CompanyModule {

	private static final Log log = Logs.get();

	@Inject
	private CompanyViewService companyViewService;
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

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final TCompanySqlForm sqlForm, @Param("..") final Pager pager) {
		return companyViewService.listPage(sqlForm, pager);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return null;
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TCompanyAddForm addForm) {
		addForm.setCreatetime(DateTimeUtil.now());
		addForm.setLastupdatetime(DateTimeUtil.now());
		return companyViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("company", companyViewService.fetch(id));
		return obj;
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TCompanyUpdateForm updateForm) {
		//updateForm.setLastupdatetime(DateTimeUtil.now());
		companyViewService.update(updateForm);
		return JsonResult.success("修改成功");
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		try {
			companyViewService.deleteById(id);
			return JsonResult.success("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("删除失败");
		}
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		companyViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 查询公司下员工的列表
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param sqlForm
	 * @param pager
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@At
	@Ok("jsp")
	public Object userList(@Param("..") final TCompanyUserSqlForm sqlForm, @Param("..") final Pager pager) {
		Map<String, Object> obj = FormUtil.query(dbDao, sqlManager, sqlForm, pager);
		return obj;
	}

	/**
	 * 更新删除状态
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param updateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@At
	@POST
	public Object updateDeleteStatus(@Param("..") TCompanyUpdateForm updateForm) {
		try {
			TCompanyEntity companyEntity = companyViewService.fetch(updateForm.getId());
			companyEntity.setDeletestatus("1");
			dbDao.update(companyEntity);
			return JsonResult.success("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("删除失败");
		}
	}

	@At
	@Ok("jsp")
	public Object demolist() {
		return null;
	}

	/**
	 * 分页查询
	 */
	@At
	public Object listData(@Param("..") final TCompanySqlForm paramForm) {
		return companyViewService.listPage4Datatables(paramForm);
	}
}