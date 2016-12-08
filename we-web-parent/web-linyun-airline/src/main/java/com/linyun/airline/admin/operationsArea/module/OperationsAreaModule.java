package com.linyun.airline.admin.operationsArea.module;

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

import com.linyun.airline.admin.operationsArea.form.TMessageAddForm;
import com.linyun.airline.admin.operationsArea.form.TMessageForm;
import com.linyun.airline.admin.operationsArea.form.TMessageUpdateForm;
import com.linyun.airline.admin.operationsArea.service.OperationsAreaViewService;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/operationsArea")
@Filters({//@By(type = AuthFilter.class)
})
public class OperationsAreaModule {

	private static final Log log = Logs.get();

	@Inject
	private OperationsAreaViewService operationsAreaViewService;

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
	 * 到添加自定义事件页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object customEvent(@Param("selDate") final String selDate) {
		return selDate;
	}

	/**
	 * 跳转到桌面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object desktop() {
		return null;
	}

	/**
	 * 桌面自定义事件 列表展示
	 */
	@At
	@GET
	public Object getCustomEvents(@Param("id") final Long id, @Param("start") final String start,
			@Param("end") final String end) {
		return operationsAreaViewService.getCustomEvent(Long.valueOf(1), start, end);
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
	public Object add(@Param("..") TMessageAddForm addForm) {
		return operationsAreaViewService.addCustomEvent(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return operationsAreaViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TMessageUpdateForm updateForm) {
		return operationsAreaViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		operationsAreaViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		operationsAreaViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TMessageForm sqlParamForm, @Param("..") final Pager pager) {
		return operationsAreaViewService.listPage(sqlParamForm, pager);
	}
}