package com.linyun.airline.admin.customer.module;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
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
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.base.Uploader;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TCustomerInfoAddForm;
import com.linyun.airline.forms.TCustomerInfoQueryForm;
import com.linyun.airline.forms.TCustomerInfoUpdateForm;
import com.uxuexi.core.common.util.Util;
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

	@Inject
	private UploadService fdfsUploadService;

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add(@Param("id") final long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		List<TUserEntity> userlist = dbDao.query(TUserEntity.class, null, null);
		obj.put("userlist", userlist);
		return obj;
	}

	/**
	 * 添加
	 * 
	 * @param addForm 添加表单对象
	 */
	@At
	@POST
	public Object add(@Param("..") TCustomerInfoAddForm addForm) throws Exception {
		addForm.setCreateTime(new Date());
		customerViewService.addCustomInfo(addForm);
		return JsonResult.success("添加成功");
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) throws Exception {
		return customerViewService.toUpdatePage(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TCustomerInfoUpdateForm updateForm) {
		customerViewService.updateCustomInfo(updateForm);
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
		//TODO 设置 列表展示的负责人名称
		//queryForm.setAgentName();

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
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
	@Ok("json")
	public Object upload(final @Param("fileId") File file, final HttpSession session) {
		return customerViewService.upload(file, session);
	}

	//客户公司查询
	@At
	@POST
	public Object company(@Param("q") final String comName) {
		return customerViewService.company(comName);
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
	public Object goCity(@Param("q") final String name, @Param("ids") final String ids) throws Exception {
		return customerViewService.goCity(name, ids);
	}

	//國内线路查询
	@At
	@POST
	public Object isLine(@Param("q") final String name, @Param("ids") final String ids) throws Exception {
		return customerViewService.isLine(name, ids);
	}

	//國際线路查询
	@At
	@POST
	public Object international(@Param("q") final String name, @Param("ids") final String ids) throws Exception {
		return customerViewService.international(name, ids);
	}

	//发票项目查询
	@At
	@POST
	public Object isInvioce(@Param("q") final String name, @Param("ids") final String ids) throws Exception {
		return customerViewService.isInvioce(name, ids);
	}

	/**
	 * 上传文件
	 */
	@At
	@Ok("json")
	public String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Uploader uploader = new Uploader(request, fdfsUploadService);
		uploader.upload();
		String url = CommonConstants.IMAGES_SERVER_ADDR + uploader.getUrl();
		return url;
	}

	/**
	 * 公司名称唯一性校验
	 */
	@At
	@POST
	public Object checkComNameExist(@Param("name") final String comId, @Param("cid") final String id) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<TCustomerInfoEntity> companys = dbDao.query(TCustomerInfoEntity.class, Cnd.where("agentId", "=", comId),
				null);
		List<TCustomerInfoEntity> comNameList = dbDao.query(TCustomerInfoEntity.class, Cnd.where("agentId", "=", comId)
				.and("id", "=", id), null);
		if (!Util.isEmpty(companys)) {
			if (Util.isEmpty(id)) {
				map.put("valid", false);
			} else if (!Util.isEmpty(comNameList)) {
				map.put("valid", true);
			}
		} else {
			map.put("valid", true);
		}

		return map;
	}

	/**
	 * 联系电话唯一性校验
	 */
	@At
	@POST
	public Object checkTelephoneExist(@Param("telephone") final String phoneNum, @Param("aId") final String id) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<TCustomerInfoEntity> customer = dbDao.query(TCustomerInfoEntity.class,
				Cnd.where("telephone", "=", phoneNum), null);
		List<TCustomerInfoEntity> phoneNumList = dbDao.query(TCustomerInfoEntity.class,
				Cnd.where("telephone", "=", phoneNum).and("id", "=", id), null);

		if (!Util.isEmpty(customer)) {
			if (Util.isEmpty(id)) {
				map.put("valid", false);
			} else if (!Util.isEmpty(phoneNumList)) {
				map.put("valid", true);
			}
		} else {
			map.put("valid", true);
		}

		return map;
	}

}