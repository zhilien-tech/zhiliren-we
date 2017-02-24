package com.linyun.airline.admin.drawback.grabfile.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipOutputStream;

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
import com.linyun.airline.admin.drawback.grabfile.form.TGrabFileAddForm;
import com.linyun.airline.admin.drawback.grabfile.form.TGrabFileSqlForm;
import com.linyun.airline.admin.drawback.grabfile.form.TGrabFileUpdateForm;
import com.linyun.airline.admin.drawback.grabfile.service.GrabfileViewService;
import com.linyun.airline.admin.drawback.grabfile.timer.MailScrabService;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.base.Uploader;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.util.ZipUtils;
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
	private UploadService fdfsUploadService;//文件上传

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
		return grabfileViewService.listPage4Datatables(sqlForm);
	}

	/**
	 * 上传文件
	 */
	@At
	@Ok("json")
	public String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(CommonConstants.CHARACTER_ENCODING_PROJECT);//字符编码为utf-8
		response.setCharacterEncoding(CommonConstants.CHARACTER_ENCODING_PROJECT);
		Uploader uploader = new Uploader(request, fdfsUploadService);
		uploader.upload();
		String url = CommonConstants.IMAGES_SERVER_ADDR + uploader.getUrl();
		return url;
	}

	/**
	 * 打包压缩下载文件
	 * @param fileList
	 * @param response
	 * @throws IOException TODO
	 */
	@At
	public void downLoadZipFile(List<TGrabFileEntity> fileList, HttpServletResponse response) throws IOException {
		String zipName = "myfile.zip";
		response.setContentType("application/zip");// 设置response内容的类型
		response.setHeader("Content-Disposition", "attachment; filename=" + zipName);// 设置头部信息  
		ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
		try {
			for (Iterator<TGrabFileEntity> it = fileList.iterator(); it.hasNext();) {
				TGrabFileEntity file = it.next();
				ZipUtils.doCompress(file.getUrl() + file.getFileName(), out);
				response.flushBuffer();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		;
	}

	/**
	 * 下载单个文件
	 * @param response TODO
	 */
	/*@At
	public void downloadFile(HttpServletResponse response) {
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			File file = new File("D:/项目特殊资料/修改财务核算账簿启用日期.txt");
			// Spring工具获取项目resources里的文件
			if (!file.exists()) {
				return;
			}
			response.reset();
			response.setHeader("Content-Disposition", "attachment;filename=修改财务核算账簿启用日期.txt");
			response.setContentType("application/octet-stream; charset=utf-8");
			os.write(FileUtils.readFileToByteArray(file));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(os);
		}
	}*/
	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add(@Param("parentId") final int pid) {
		return grabfileViewService.superFolder(pid);
	}

	/**
	 * 添加
	 */
	@At
	public Object add(@Param("..") TGrabFileAddForm addForm) {
		addForm.setCreateTime(new Date());
		addForm.setStatus(DataStatusEnum.ENABLE.intKey());
		return grabfileViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final int id) {
		return grabfileViewService.superFolder(id);
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

	//文件的移动
	@At
	@POST
	public Object fileMove(@Param("id") final int id) {
		//将文件移动到新目录

		return dbDao.update(TGrabFileEntity.class, Chain.make("parentId", id), null);
	}

	//邮件抓取按钮
	@At
	@POST
	public void grabMail() throws Exception {
		grabMailService.receivePop3();
	}
}