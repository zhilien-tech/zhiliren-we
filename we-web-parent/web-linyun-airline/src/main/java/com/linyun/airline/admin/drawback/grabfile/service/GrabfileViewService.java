package com.linyun.airline.admin.drawback.grabfile.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.drawback.grabfile.entity.TGrabFileEntity;
import com.linyun.airline.admin.drawback.grabfile.enums.FileTypeEnum;
import com.linyun.airline.admin.drawback.grabfile.form.TGrabFileAddForm;
import com.linyun.airline.admin.drawback.grabfile.form.TGrabFileSqlForm;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.common.util.ZFile;
import com.linyun.airline.common.util.ZipUtils;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class GrabfileViewService extends BaseService<TGrabFileEntity> {

	@SuppressWarnings("unchecked")
	public Object listData(@Param("..") final TGrabFileSqlForm sqlForm) {
		Map<String, Object> DatatablesData = this.listPage4Datatables(sqlForm);
		List<Record> listdata = (List<Record>) DatatablesData.get("data");
		String sendTime = "";
		for (Record record : listdata) {
			if (!Util.isEmpty(sendTime)) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 E HH:mm ");
				Date date = null;
				try {
					date = dateFormat.parse(record.getString("sendTime"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy.MM.dd");
				sendTime = fmt.format(date);//邮件发送时间
			}
		}
		return DatatablesData;
	}

	/**
	 * 保存上传文件
	 * @param addForm
	 * @param flagType 
	 */
	public Object saveUploadFile(TGrabFileAddForm addForm, int flagType) {
		TGrabFileEntity grabfile = new TGrabFileEntity();
		grabfile.setCreateTime(new Date());
		grabfile.setStatus(DataStatusEnum.ENABLE.intKey());
		grabfile.setUrl(addForm.getUrl());
		grabfile.setFileName(addForm.getFileName());
		grabfile.setParentId(addForm.getId());
		grabfile.setMailId(addForm.getId());
		grabfile.setFileSize(addForm.getFileSize());//文件大小
		grabfile.setType(FileTypeEnum.FILE.intKey());//文件类型

		String str1 = System.getProperty("java.io.tmpdir");
		InputStream is1 = null;
		OutputStream out = null;
		URL url;
		try {
			url = new URL(addForm.getUrl());
			URLConnection connection = url.openConnection();
			is1 = connection.getInputStream();
			File file3 = new File(str1 + File.separator + "12.pdf");
			out = new FileOutputStream(file3);
			byte b[] = new byte[1024];
			int m = 0;
			while ((m = is1.read(b)) > -1) {
				out.write(b);
				out.flush();
			}
			File file4 = new File(str1 + File.separator + "12.pdf");
			InputStream is5 = new FileInputStream(file4);
			int byteNum = is5.available();
			double fileSize = Math.rint(byteNum / 1024);
			addForm.setFileSize(fileSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setParentsFileSize(addForm.getParentId(), addForm.getFileSize());
		if (flagType == 0) {
			grabfile.setGroupType(OrderTypeEnum.FIT.intKey());
		} else if (flagType == 1) {
			grabfile.setGroupType(OrderTypeEnum.TEAM.intKey());
		}
		TGrabFileEntity reportDate = dbDao.insert(grabfile);
		return reportDate;
	}

	//添加时根据pid查询数据
	public Map<String, Object> superFolder(long pid, int flagType) {
		Map<String, Object> obj = new HashMap<String, Object>();
		TGrabFileEntity dirfolder = dbDao.fetch(TGrabFileEntity.class,
				Cnd.where("status", "=", DataStatusEnum.ENABLE.intKey()).and("id", "=", pid));
		obj.put("dirfolder", dirfolder);
		obj.put("flagType", flagType);
		return obj;
	}

	//文件移动
	public Object getFileId(long id, int flag) {
		HashMap<Object, Object> obj = Maps.newHashMap();
		List<TGrabFileEntity> allModule = null;
		if (1 == flag) {
			allModule = dbDao.query(TGrabFileEntity.class, Cnd.where("groupType", "=", flag), null);
		} else if (2 == flag) {
			allModule = dbDao.query(TGrabFileEntity.class, Cnd.where("groupType", "=", flag), null);
		}
		obj.put("list", allModule);
		obj.put("parentId", id);
		obj.put("flag", flag);
		return obj;
	}

	/** 
	 * 递归获取当前节点下面的所有子节点
	 */
	private void getChildFiles(List<TGrabFileEntity> fileList, long parentId) {
		// 获取直接子节点
		List<TGrabFileEntity> chidren = dbDao.query(TGrabFileEntity.class, Cnd.where("parentId", "=", parentId), null);
		if (!Util.isEmpty(chidren)) {
			fileList.addAll(chidren);
			for (TGrabFileEntity f : chidren) {
				//递归子节点
				getChildFiles(fileList, f.getId());
			}
		}
	}

	/**
	 * 根据文件id获取文件所在目录路径
	 */
	private String getFileFullPath(long fileId) {
		String path = "";
		List<String> pathLst = Lists.newArrayList();
		recursiveGetPath(pathLst, fileId);
		if (!Util.isEmpty(pathLst)) {
			List<String> reverse = Lists.reverse(pathLst);//排序
			for (int i = 0; i < reverse.size(); i++) {
				String p = reverse.get(i);
				if (i == 0) {
					path += p;
				} else {
					path += (File.separator + p);
				}

			}
		}
		return path + (File.separator);
	}

	/**
	 * 递归查询文件路径，将每一级路径保存到集合中
	 */
	private void recursiveGetPath(List<String> pathLst, long fileId) {
		Sql sql = Sqls.create(sqlManager.get("grab_mail_filepath"));
		sql.params().set("fileId", fileId);
		TGrabFileEntity parent = DbSqlUtil.fetchEntity(dbDao, TGrabFileEntity.class, sql);
		if (!Util.isEmpty(parent)) {
			pathLst.add(parent.getFileName());
			recursiveGetPath(pathLst, parent.getId());
		}
	}

	private void getChidrenFiles(List<TGrabFileEntity> fileList, long parentId) {
		//添加当前节点
		TGrabFileEntity current = dbDao.fetch(TGrabFileEntity.class, Cnd.where("id", "=", parentId));
		fileList.add(current);
		getChildFiles(fileList, parentId);

	}

	/**
	 * 向下递归查询文件的完整路径
	 */

	/**
	 * 文件打包下载
	 * @param tempFilePath 待创建临时压缩文件
	 * @param files        待压缩文件集合
	 * @param request      请求
	 * @param response     响应
	 * @return response
	 * @throws Exception 异常
	 */
	public void downLoadZipFiles(long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String downloadName = "myfile.zip";
		ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
		response.reset();
		// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(downloadName.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
		response.setContentType("application/octet-stream");
		try {
			//1、把多个文件打包成一个文件
			List<TGrabFileEntity> gFiles = Lists.newArrayList();
			getChidrenFiles(gFiles, id);
			//把1中得到的路径(pathList)转为文件(file)
			List<ZFile> files = Lists.newArrayList();
			for (TGrabFileEntity gf : gFiles) {
				//判断文件类型
				int fileType = gf.getType();
				if (FileTypeEnum.FILE.intKey() == fileType) {//文件
					ZFile zf = new ZFile();
					URL url = new URL(gf.getUrl());
					zf.setInput(url.openStream());
					zf.setFileName(gf.getFileName());
					String fileFullPath = getFileFullPath(gf.getId());
					zf.setRelativePathInZip(fileFullPath);
					files.add(zf);
				}
			}
			//将文件进行打包(将数据写入zos)
			ZipUtils.zipFile(files, zos);
			zos.flush();
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//校验部门名称唯一性
	public Object checkFileNameExist(final String fileName, final Long id) {
		Map<String, Object> map = Maps.newHashMap();
		int count = 0;
		if (Util.isEmpty(id)) {
			//add
			count = nutDao.count(TGrabFileEntity.class, Cnd.where("fileName", "=", fileName));
		} else {
			//update
			count = nutDao.count(TGrabFileEntity.class, Cnd.where("fileName", "=", fileName).and("id", "!=", id));
		}
		map.put("valid", count <= 0);
		return map;
	}

	//根据文件id设置之前所有父文件的大小
	public void setParentsFileSize(long id, double size) {
		TGrabFileEntity tGrabFileEntity = dbDao.fetch(TGrabFileEntity.class, Cnd.where("id", "=", id));
		if (Util.isEmpty(tGrabFileEntity.getFileSize())) {

			dbDao.update(TGrabFileEntity.class, Chain.make("fileSize", size), Cnd.where("id", "=", id));
		} else {
			dbDao.update(TGrabFileEntity.class, Chain.make("fileSize", size + tGrabFileEntity.getFileSize()),
					Cnd.where("id", "=", id));

		}
		if (tGrabFileEntity.getParentId() == 0) {
			return;
		}
		setParentsFileSize(tGrabFileEntity.getParentId(), size);
	}

	//根据文件id设置之前所有父文件的大小
	public void subParentsFileSize(long id, double size) {
		TGrabFileEntity tGrabFileEntity = dbDao.fetch(TGrabFileEntity.class, Cnd.where("id", "=", id));
		if (Util.isEmpty(tGrabFileEntity.getFileSize())) {

			dbDao.update(TGrabFileEntity.class, Chain.make("fileSize", size), Cnd.where("id", "=", id));
		} else {
			dbDao.update(TGrabFileEntity.class, Chain.make("fileSize", tGrabFileEntity.getFileSize() - size),
					Cnd.where("id", "=", id));

		}
		if (tGrabFileEntity.getParentId() == 0) {
			return;
		}
		subParentsFileSize(tGrabFileEntity.getParentId(), size);
	}

	/**
	 * 根据id单条删除数据
	 * @param id
	 */
	public Object onedelete(long id) {
		TGrabFileEntity fetch = dbDao.fetch(TGrabFileEntity.class, id);
		this.deleteById(id);
		subParentsFileSize(fetch.getParentId(), fetch.getFileSize());
		return fetch;
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	public Object batchDeleteList(Long[] ids) {
		for (Long long1 : ids) {
			TGrabFileEntity fetch = dbDao.fetch(TGrabFileEntity.class, long1);
			subParentsFileSize(fetch.getParentId(), fetch.getFileSize());
		}
		this.batchDelete(ids);
		return null;
	}
}