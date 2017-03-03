package com.linyun.airline.admin.drawback.grabfile.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.drawback.grabfile.entity.TGrabFileEntity;
import com.linyun.airline.admin.drawback.grabfile.enums.FileTypeEnum;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.common.util.ZipUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class GrabfileViewService extends BaseService<TGrabFileEntity> {

	//添加时根据pid查询数据
	public Map<String, Object> superFolder(long pid) {
		Map<String, Object> obj = new HashMap<String, Object>();
		TGrabFileEntity dirfolder = dbDao.fetch(TGrabFileEntity.class,
				Cnd.where("status", "=", DataStatusEnum.ENABLE.intKey()).and("id", "=", pid));
		obj.put("dirfolder", dirfolder);
		return obj;
	}

	//文件移动
	public Object getFileId(long id) {
		HashMap<Object, Object> obj = Maps.newHashMap();
		List<TGrabFileEntity> allModule = dbDao.query(TGrabFileEntity.class, null, null);
		obj.put("list", allModule);
		obj.put("parentId", id);
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
	public void downLoadZipFiles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = null;
		try {
			//1、把多个文件打包成一个文件
			List<TGrabFileEntity> pathList = Lists.newArrayList();
			long parentId = 2l;
			getChidrenFiles(pathList, parentId);
			//把1中得到的路径(pathList)转为文件(file)
			List<File> fileLst = Lists.newArrayList();
			for (TGrabFileEntity dfile : pathList) {
				//判断文件类型
				int fileType = dfile.getType();
				if (FileTypeEnum.FOLDER.intKey() == fileType) {//文件夹
					File d = new File("");
					d.mkdirs();

				} else if (FileTypeEnum.FILE.intKey() == fileType) {//文件
					fileLst.add(new File(""));
				}
			}
			//2,下载1中得到的文件
			File inputFile = new File("");
			FileInputStream fis = new FileInputStream(inputFile);

			byte[] buffer = new byte[fis.available()];
			String filename = "myfile.zip";

			//将文件进行打包
			ZipUtil.zipFiles(fileLst, new File(filename));

			fis.read(buffer);
			fis.close();

			response.reset();
			// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
			response.addHeader("Content-Length", "" + inputFile.length());
			response.setContentType("application/octet-stream");
			os = new BufferedOutputStream(response.getOutputStream());
			os.write(buffer);// 输出文件
			os.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}