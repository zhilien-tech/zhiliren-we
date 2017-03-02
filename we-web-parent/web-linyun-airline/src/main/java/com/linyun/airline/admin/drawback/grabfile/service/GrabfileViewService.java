package com.linyun.airline.admin.drawback.grabfile.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.linyun.airline.admin.drawback.grabfile.entity.TGrabFileEntity;
import com.linyun.airline.common.enums.DataStatusEnum;
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

	//文件打包下载
	/**
	 * 文件打包下载
	 *
	 * @param tempFilePath 待创建临时压缩文件
	 * @param files        待压缩文件集合
	 * @param request      请求
	 * @param response     响应
	 * @return response
	 * @throws Exception 异常
	 */
	public static HttpServletResponse downLoadZipFiles(String tempFilePath, List<File> files,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			/**这个集合就是想要打包的所有文件，
			 * 这里假设已经准备好了所要打包的文件*/
			files = new ArrayList<File>();

			/**创建一个临时压缩文件，
			 * 把文件流全部注入到这个文件中
			 * 这里的文件可以自定义是.rar还是.zip*/
			File file = new File(tempFilePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			response.reset();

			response.getWriter();
			//创建文件输出流
			FileOutputStream fous = new FileOutputStream(file);
			/**打包的方法会用到ZipOutputStream这样一个输出流,
			 * 所以这里把输出流转换一下*/
			ZipOutputStream zipOut = new ZipOutputStream(fous);
			zipOut.setEncoding(System.getProperty("sun.jnu.encoding"));//设置文件名编码方式
			/**这个方法接收的就是一个所要打包文件的集合，
			 * 还有一个ZipOutputStream*/
			zipFile(files, zipOut);
			zipOut.close();
			fous.close();
			return downloadZip(file, response, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**直到文件的打包已经成功了，
		 * 文件的打包过程封装在FileUtil.zipFile这个静态方法中，
		 * 接下来的就是往客户端写数据了*/
		return response;
	}

	/**
	 * 把接受的全部文件打成压缩包
	 *
	 * @param files
	 * @param outputStream org.apache.tools.zip.ZipOutputStream
	 */
	public static void zipFile(List files, ZipOutputStream outputStream) {
		int size = files.size();
		for (int i = 0; i < size; i++) {
			File file = (File) files.get(i);
			zipFile(file, outputStream);
		}
	}

	public static HttpServletResponse downloadZip(File file, HttpServletResponse response, HttpServletRequest request) {
		try {
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();

			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/x-msdownload");

			//如果输出的是中文名的文件，解决乱码
			response.setHeader("Content-Disposition",
					"attachment;filename=\"" + new String(file.getName().getBytes("gbk"), "iso-8859-1") + "\"");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				File f = new File(file.getPath());
				f.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	/**
	 * 根据输入的文件与输出流对文件进行打包
	 *
	 * @param inputFile   File
	 * @param ouputStream org.apache.tools.zip.ZipOutputStream
	 */
	public static void zipFile(File inputFile, ZipOutputStream ouputStream) {
		try {
			if (inputFile.exists()) {
				/**如果是目录的话这里是不采取操作的*/
				if (inputFile.isFile()) {
					FileInputStream IN = new FileInputStream(inputFile);
					BufferedInputStream bins = new BufferedInputStream(IN, 512);
					//org.apache.tools.zip.ZipEntry
					ZipEntry entry = new ZipEntry(inputFile.getName());
					//                    ZipEntry entry = new ZipEntry(new String(inputFile.getName().getBytes(), "utf-8"));
					ouputStream.putNextEntry(entry);
					// 向压缩文件中输出数据
					int nNumber;
					byte[] buffer = new byte[512];
					while ((nNumber = bins.read(buffer)) != -1) {
						ouputStream.write(buffer, 0, nNumber);
					}
					// 关闭创建的流对象
					bins.close();
					IN.close();
				} else {
					try {
						File[] files = inputFile.listFiles();
						for (int i = 0; i < files.length; i++) {
							zipFile(files[i], ouputStream);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}