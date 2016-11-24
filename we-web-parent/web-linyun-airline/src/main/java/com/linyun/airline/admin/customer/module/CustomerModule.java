package com.linyun.airline.admin.customer.module;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.linyun.airline.admin.customer.service.CustomerService;
import com.linyun.airline.admin.customer.service.CustomerViewService;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.common.base.MobileResult;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAgentEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TCustomerInvoiceEntity;
import com.linyun.airline.entities.TCustomerLineEntity;
import com.linyun.airline.entities.TUpcompanyEntity;
import com.linyun.airline.forms.TCustomerInfoAddForm;
import com.linyun.airline.forms.TCustomerInfoQueryForm;
import com.linyun.airline.forms.TCustomerInfoUpdateForm;
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
	private externalInfoService externalInfoService;

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
		addForm.setCreateTime(new Date());
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
		customerViewService.deleteById(id);
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

	//附件上传 返回值文件存储地址
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
	@Ok("json")
	public Object upload(final @Param("file") File file, final HttpSession session) {
		try {
			String ext = Files.getSuffix(file);
			FileInputStream fileInputStream = new FileInputStream(file);
			String url = fdfsUploadService.uploadImage(fileInputStream, ext, null);
			//文件存储地址
			return url;
			//业务
		} catch (Exception e) {
			return MobileResult.error("操作失败", null);
		}
	}

	//公司名称模糊查询
	//TODO 接口未写

	//出发城市模糊查询
	@At
	@POST
	public Object goCity(@Param("departureCity") final String name) throws Exception {
		Set<String> set = new HashSet();

		List<TCustomerLineEntity> localLineList = dbDao.query(TCustomerLineEntity.class,
				Cnd.where("lineName", "like", "%" + name + "%"), null);

		List<DictInfoEntity> dictLineList = externalInfoService.findDictInfoByName(name);

		if (localLineList.size() > 5) {
			for (int i = 0; i < 5; i++) {
				set.add(localLineList.get(i).getLineName());
			}
		} else {
			for (TCustomerLineEntity tCustomerLineEntity : localLineList) {
				set.add(tCustomerLineEntity.getLineName());
			}
			//需要从字典表中查询的记录数   5-set.size()
			int num = 5 - set.size();
			while (num > 0) {
				set.add(dictLineList.get(num).getDictName());
				num--;
			}
		}

		return set;
	}

	//线路模糊查询
	@At
	@POST
	public Object isLine(@Param("line") final String name) throws Exception {
		Set<String> set = new HashSet();

		List<TCustomerLineEntity> localLineList = dbDao.query(TCustomerLineEntity.class,
				Cnd.where("lineName", "like", "%" + name + "%"), null);

		List<DictInfoEntity> dictLineList = externalInfoService.findDictInfoByName(name);

		if (localLineList.size() > 5) {
			for (int i = 0; i < 5; i++) {
				set.add(localLineList.get(i).getLineName());
			}
		} else {
			for (TCustomerLineEntity tCustomerLineEntity : localLineList) {
				set.add(tCustomerLineEntity.getLineName());
			}
			//需要从字典表中查询的记录数   5-set.size()
			int num = 5 - set.size();
			while (num > 0) {
				set.add(dictLineList.get(num).getDictName());
				num--;
			}
		}

		return set;
	}

	//发票项目模糊查询
	@At
	@POST
	public Object invioceType(@Param("invioce") final String name) throws Exception {
		Set<String> set = new HashSet();

		List<TCustomerInvoiceEntity> localInvioceList = dbDao.query(TCustomerInvoiceEntity.class,
				Cnd.where("invioceName", "like", "%" + name + "%"), null);
		List<DictInfoEntity> dictLineList = externalInfoService.findDictInfoByName(name);

		if (localInvioceList.size() > 5) {
			for (int i = 0; i < 5; i++) {
				set.add(localInvioceList.get(i).getInvioceName());
			}
		} else {
			for (TCustomerInvoiceEntity tCustomerInvoiceEntity : localInvioceList) {
				set.add(tCustomerInvoiceEntity.getInvioceName());
			}

			//需要从字典表中查询的记录数   5-set.size()
			int num = 5 - set.size();
			while (num > 0) {
				set.add(dictLineList.get(num).getDictName());
				num--;
			}
		}

		return set;
	}

}