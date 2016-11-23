package com.linyun.airline.admin.function.module;

import java.util.HashMap;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.entities.TFunctionEntity;
import com.linyun.airline.forms.TFunctionAddForm;
import com.linyun.airline.forms.TFunctionModForm;
import com.linyun.airline.forms.TFunctionSqlForm;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

/**
 * 功能管理	控制类
* @ClassName: FunctionModule
* @author 崔建斌 
* @date 2016年11月18日 上午11:35:11
 */
@IocBean
@At("/admin/function")
@Filters({//@By(type = AuthFilter.class)
})
public class FunctionModule {

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
	public Object list(@Param("..") final TFunctionSqlForm sqlForm, @Param("..") final Pager pager) {
		Map<String, Object> obj = FormUtil.query(dbDao, sqlManager, sqlForm, pager);
		obj.put("functions", dbDao.query(TFunctionEntity.class, Cnd.where("level", "<=", 2), null));
		return obj;
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return dbDao.query(TFunctionEntity.class, null, null);
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TFunctionAddForm addForm) {
		addForm.setCreateTime(DateTimeUtil.now());
		FormUtil.add(dbDao, addForm, TFunctionEntity.class);
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
		//上级功能选择的时候要排除自己
		obj.put("list", dbDao.query(TFunctionEntity.class, Cnd.where("id", "!=", id), null));
		obj.put("function", dbDao.fetch(TFunctionEntity.class, id));
		return obj;
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TFunctionModForm modForm) {
		modForm.setUpdateTime(DateTimeUtil.now());
		FormUtil.modify(dbDao, modForm, TFunctionEntity.class);
		return JsonResult.success("修改成功!");
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		FormUtil.delete(dbDao, TFunctionEntity.class, id);
		return JsonResult.success("删除成功!");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final long[] ids) {
		FormUtil.delete(dbDao, TFunctionEntity.class, ids);
		return JsonResult.success("删除成功");
	}

}