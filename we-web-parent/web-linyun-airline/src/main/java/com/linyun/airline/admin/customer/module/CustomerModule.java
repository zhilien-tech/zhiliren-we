package com.linyun.airline.admin.customer.module;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.SqlManager;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.customer.service.CustomerViewService;
import com.linyun.airline.entities.TAgentEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TUpcompanyEntity;
import com.linyun.airline.forms.TCustomerInfoAddForm;
import com.linyun.airline.forms.TCustomerInfoQueryForm;
import com.linyun.airline.forms.TCustomerInfoUpdateForm;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;

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
	private CustomerViewService customerViewService;

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
		//保存时   获得出发城市id
		//Iterable<String> split = Splitter.on(",").split(outcity);
		//addForm.get

		customerViewService.add(addForm);
		return JsonResult.success("添加成功");
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
		customerViewService.update(updateForm);
		return JsonResult.success("修改成功");
	}

	@At
	@Ok("jsp")
	public Object list(@Param("..") final TCustomerInfoQueryForm queryForm, @Param("..") final Pager pager) {
		return null;
	}

	//服务器端分页查询
	@At
	public Object listData(@Param("..") final TCustomerInfoQueryForm queryForm) {
		return customerViewService.listPage4Datatables(queryForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		customerViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final long[] ids) {
		return customerViewService.batchDelete(ids);
	}

	//附件上传 返回值文件存储地址
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
	@Ok("json")
	public Object upload(final @Param("file") File file, final HttpSession session) {
		return customerViewService.upload(file, session);
	}

	//客户公司查询
	@At
	@POST
	public Object company() {
		return customerViewService.company();
	}

	//负责人查询
	@At
	@POST
	public Object agent() {
		return customerViewService.agent();
	}

	//出发城市查询
	@At
	@POST
	public Object goCity(@Param("q") final String name) throws Exception {
		return customerViewService.goCity(name);
	}

	//线路查询
	@At
	@POST
	public Object isLine(@Param("line") final String name) throws Exception {
		return customerViewService.isLine(name);
	}

	//发票项目查询
	@At
	@POST
	public Object isInvioce(@Param("invioce") final String name) throws Exception {
		return customerViewService.isInvioce(name);
	}

}