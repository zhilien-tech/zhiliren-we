package com.linyun.airline.admin.customer.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.linyun.airline.admin.customer.service.CustomerService;
import com.linyun.airline.admin.customer.service.CustomerViewService;
import com.linyun.airline.admin.dictionary.dirinfo.service.IInfoService;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAgentEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TUpcompanyEntity;
import com.linyun.airline.forms.TCustomerInfoAddForm;
import com.linyun.airline.forms.TCustomerInfoQueryForm;
import com.linyun.airline.forms.TCustomerInfoUpdateForm;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

/**
 * 
 * 功能管理	控制类
 *
 */
@IocBean
@At("/admin/customer")
@Filters({//@By(type = AuthFilter.class)
})
public class CustomerModule {

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
	 * 注入容器中的Service对象
	 */
	@Inject
	private CustomerService customerService;

	@Inject
	private CustomerViewService customerViewService;

	@Inject
	private IInfoService iInfoService;

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add(@Param("id") final long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("upCompany", dbDao.fetch(TUpcompanyEntity.class, id));
		obj.put("agent", dbDao.fetch(TAgentEntity.class, id));
		return dbDao.query(TCustomerInfoEntity.class, null, null);
	}

	/**
	 * 添加
	 * 
	 * @param addForm 添加表单对象
	 */
	@At
	@POST
	public Object add(@Param("..") TCustomerInfoAddForm addForm) throws Exception {
		//addForm.setCreateTime(DateTimeUtil.nowDateTime());
		addForm.setCreateTime(DateTimeUtil.nowDateTime());
		FormUtil.add(dbDao, addForm, TCustomerInfoEntity.class);
		return JsonResult.success("添加成功");
	}

	public void goCity(@Param("id") final String name) {
		List<DictInfoEntity> search = iInfoService.search(name);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("customer", dbDao.fetch(TCustomerInfoEntity.class, id));
		return obj;
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TCustomerInfoUpdateForm updateForm) {
		//updateForm.setCreateTime(DateTimeUtil.nowDateTime());
		FormUtil.modify(dbDao, updateForm, TCustomerInfoEntity.class);
		return JsonResult.success("修改成功");
	}

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final TCustomerInfoQueryForm queryForm, @Param("..") final Pager pager) {
		return FormUtil.query(dbDao, TCustomerInfoEntity.class, queryForm, pager);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		FormUtil.delete(dbDao, TCustomerInfoEntity.class, id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final long[] ids) {
		FormUtil.delete(dbDao, TCustomerInfoEntity.class, ids);
		return JsonResult.success("删除成功");
	}

}