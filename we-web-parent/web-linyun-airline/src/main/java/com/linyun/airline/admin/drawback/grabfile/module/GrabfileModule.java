package com.linyun.airline.admin.drawback.grabfile.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Lists;
import com.linyun.airline.admin.drawback.grabfile.entity.TGrabFileEntity;
import com.linyun.airline.admin.drawback.grabfile.enums.FileTypeEnum;
import com.linyun.airline.admin.drawback.grabfile.form.TGrabFileAddForm;
import com.linyun.airline.admin.drawback.grabfile.form.TGrabFileSqlForm;
import com.linyun.airline.admin.drawback.grabfile.form.TGrabFileUpdateForm;
import com.linyun.airline.admin.drawback.grabfile.service.GrabfileViewService;
import com.linyun.airline.admin.drawback.grabfile.timer.MailScrabService;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.base.Uploader;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/drawback/grabfile")
public class GrabfileModule {

	@Inject
	private IDbDao dbDao;

	@Inject
	private SqlManager sqlManager;

	@Inject
	private GrabfileViewService grabfileViewService;//邮件抓取

	@Inject
	private UploadService qiniuUploadService;//文件上传

	@Inject
	private MailScrabService grabMailService;//邮件抓取

	/**
	 * 分页查询
	 * @param sqlForm
	 * @param pager
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final TGrabFileSqlForm sqlForm, @Param("..") final Pager pager) {
		/*Map<String, Object> map = Maps.newHashMap();
		List<Record> deplist = grabfileViewService.getFolderInfo(sqlManager);
		map.put("deplist", deplist);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));*/
		return null;
	}

	/**
	 * 服务端分页查询
	 */
	@At
	public Object listData(@Param("..") final TGrabFileSqlForm sqlForm) {
		//return grabfileViewService.listPage4Datatables(sqlForm);
		return grabfileViewService.listData(sqlForm);
	}

	/**
	 * 上传文件
	 */
	@At
	@Ok("json")
	public Object uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(CommonConstants.CHARACTER_ENCODING_PROJECT);//字符编码为utf-8
		response.setCharacterEncoding(CommonConstants.CHARACTER_ENCODING_PROJECT);
		Uploader uploader = new Uploader(request, qiniuUploadService);
		uploader.upload();
		String url = CommonConstants.IMAGES_SERVER_ADDR + uploader.getUrl();
		return url;
	}

	/**
	 * 保存上传文件
	 * @param addForm
	 */
	@At
	public Object saveUploadFile(@Param("..") TGrabFileAddForm addForm, @Param("flagType") int flagType) {
		return grabfileViewService.saveUploadFile(addForm, flagType);
	}

	/**
	 * 打包压缩下载文件
	 * @param fileList
	 * @param response
	 * @throws Exception 
	 */
	@At
	public void downLoadZipFile(@Param("parentId") long id, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		grabfileViewService.downLoadZipFiles(id, request, response);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add(@Param("parentId") final long pid, @Param("flagType") final int flagType) {
		return grabfileViewService.superFolder(pid, flagType);
	}

	/**
	 * 添加
	 */
	@At
	public Object add(@Param("..") TGrabFileAddForm fileAddForm, @Param("flagType") final int flagType) {
		if (flagType == 0) {
			fileAddForm.setGroupType(OrderTypeEnum.FIT.intKey());

		} else if (flagType == 1) {
			fileAddForm.setGroupType(OrderTypeEnum.TEAM.intKey());

		}
		fileAddForm.setCreateTime(new Date());
		fileAddForm.setStatus(DataStatusEnum.ENABLE.intKey());
		fileAddForm.setMailId(fileAddForm.getId());
		fileAddForm.setType(FileTypeEnum.FOLDER.intKey());//文件夹
		return grabfileViewService.add(fileAddForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id, @Param("flagType") final int flagType) {
		return grabfileViewService.superFolder(id, flagType);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TGrabFileUpdateForm updateForm) {
		updateForm.setStatus(DataStatusEnum.ENABLE.intKey());
		return grabfileViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		grabfileViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		grabfileViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 更新删除状态
	 * @param updateForm
	 */
	@At
	@POST
	public Object updateDeleteStatus(@Param("..") TGrabFileUpdateForm updateForm) {
		try {
			dbDao.update(TGrabFileEntity.class, Chain.make("status", updateForm.getStatus()),
					Cnd.where("id", "=", updateForm.getId()));
			return JsonResult.success("操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("操作失败!");
		}
	}

	/**
	 * 批量更新删除状态
	 * @param updateForm
	 * @param ids
	 */
	@At
	@POST
	public Object updateBetchStatus(@Param("ids") String ids) {
		ArrayList<TGrabFileEntity> updatelist = Lists.newArrayList();
		List<TGrabFileEntity> fileList = dbDao.query(TGrabFileEntity.class, Cnd.where("id", "in", ids), null);
		for (TGrabFileEntity t : fileList) {
			t.setStatus(DataStatusEnum.DELETE.intKey());//已删除
			updatelist.add(t);
		}
		return dbDao.update(updatelist);
	}

	/**
	 * 移动到页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object move(@Param("id") final long id) {
		return grabfileViewService.getFileId(id);
	}

	/**
	 * 文件的移动操作
	 */
	@At
	@POST
	public Object fileMove(@Param("id") final long id, @Param("parentId") final long parentId) {
		//将文件移动到新目录
		return dbDao.update(TGrabFileEntity.class, Chain.make("parentId", id), Cnd.where("id", "=", parentId));
	}

	//邮件抓取按钮
	@At
	@POST
	public void grabMail() throws Exception {
		grabMailService.loginMailAccount();
	}

	//文件预览
	//@At
	//@POST
	/*public Object filePreview(@Param("id") final long pid) {
		Map<String, Object> obj = Maps.newHashMap();
		TGrabFileEntity fileSingle = dbDao.fetch(TGrabFileEntity.class,
				Cnd.where("id", "=", pid).and("type", "=", FileTypeEnum.FILE.intKey()));
		obj.put("filepre", fileSingle);
		return obj;
	}*/
}
