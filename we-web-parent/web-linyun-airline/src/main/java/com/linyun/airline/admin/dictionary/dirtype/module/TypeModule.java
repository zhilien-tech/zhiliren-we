/**
 * TypeModule.java
 * com.xiaoka.template.admin.dictionary.dirtype.module
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirtype.module;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.dictionary.dirtype.service.ITypeService;
import com.linyun.airline.forms.DictTypeAddForm;
import com.linyun.airline.forms.DictTypeForm;
import com.linyun.airline.forms.DictTypeUpdateForm;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@IocBean
@At("/admin/dictionary/dirtype")
public class TypeModule {

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	@Inject
	private ITypeService iTypeService;

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
	 * 
	 * TODO 添加
	 * @param addForm
	 * @return TODO(添加表单对象)
	 */
	@At
	@POST
	public Object add(@Param("..") final DictTypeAddForm addForm) {
		iTypeService.add(addForm);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return iTypeService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") DictTypeUpdateForm updateForm) {
		iTypeService.update(updateForm);
		return JsonResult.success("修改成功!");
	}

	/**
	 * 分页查询
	 * <P>
	 * 
	 * @param queryForm  查询表单
	 * @param pager      分页对象
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final DictTypeForm sqlParamForm, @Param("..") final Pager pager) {
		return iTypeService.listPage(sqlParamForm, pager);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		iTypeService.deleteById(id);
		return JsonResult.success("删除成功！");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		iTypeService.batchDelete(ids);
		return JsonResult.success("删除成功！");
	}

}
