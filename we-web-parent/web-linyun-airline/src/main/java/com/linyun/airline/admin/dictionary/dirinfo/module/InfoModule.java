/**
 * InfoModule.java
 * com.xiaoka.template.admin.dictionary.dirinfo.module
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirinfo.module;

import java.util.HashMap;
import java.util.Map;

import org.nutz.dao.SqlManager;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.dictionary.dirinfo.form.InfoAddForm;
import com.linyun.airline.admin.dictionary.dirinfo.form.InfoModForm;
import com.linyun.airline.admin.dictionary.dirinfo.form.InfoQueryForm;
import com.linyun.airline.admin.dictionary.dirinfo.service.IInfoService;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.DictTypeEntity;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@IocBean
@At("/admin/dictionary/dirinfo")
public class InfoModule {

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
	private IInfoService iInfoService;

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return dbDao.query(DictTypeEntity.class, null, null);
	}

	/**
	 * TODO 添加
	 * @param addForm
	 * @return TODO(添加表单对象)
	 */
	@At
	@POST
	public Object add(@Param("..") final InfoAddForm addForm) {
		FormUtil.add(dbDao, addForm, DictInfoEntity.class);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("dirtype", dbDao.query(DictTypeEntity.class, null, null));
		obj.put("dirinfo", iInfoService.findDirinfo(id));
		return obj;
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") final InfoModForm modForm) {
		iInfoService.update(modForm);
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
	public Object list(@Param("..") final InfoQueryForm queryForm, @Param("..") final Pager pager) {
		return FormUtil.query(dbDao, DictInfoEntity.class, queryForm, pager);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		FormUtil.delete(dbDao, DictInfoEntity.class, id);
		return JsonResult.success("删除成功！");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final long[] ids) {
		FormUtil.delete(dbDao, DictInfoEntity.class, ids);
		return JsonResult.success("删除成功！");
	}
}
