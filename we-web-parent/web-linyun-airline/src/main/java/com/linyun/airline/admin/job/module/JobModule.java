package com.linyun.airline.admin.job.module;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.job.service.JobViewService;
import com.linyun.airline.entities.TJobEntity;
import com.linyun.airline.forms.TJobAddForm;
import com.linyun.airline.forms.TJobModForm;
import com.linyun.airline.forms.TJobQueryForm;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

@IocBean
@At("/admin/job")
public class JobModule {

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	@Inject
	private JobViewService jobViewService;

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final TJobQueryForm queryForm, @Param("..") final Pager pager) {
		return FormUtil.query(dbDao, TJobEntity.class, queryForm, pager);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public void add() {
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") final TJobAddForm addForm) {
		//		addForm.setCreateTime(DateTimeUtil.nowDateTime());

		/*Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);*/
		//addForm.setCreateTime(time);

		addForm.setDeptId(1);
		FormUtil.add(dbDao, addForm, TJobEntity.class);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return jobViewService.findJob(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") final TJobModForm modForm) {
		jobViewService.update(modForm);
		return JsonResult.success("修改成功!");
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		FormUtil.delete(dbDao, TJobEntity.class, id);
		return JsonResult.success("删除成功!");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final long[] ids) {
		FormUtil.delete(dbDao, TJobEntity.class, ids);
		return JsonResult.success("删除成功!");
	}

}