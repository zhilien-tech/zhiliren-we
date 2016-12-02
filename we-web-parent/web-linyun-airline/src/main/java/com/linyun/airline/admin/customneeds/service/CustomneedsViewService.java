package com.linyun.airline.admin.customneeds.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.upload.TempFile;

import com.linyun.airline.common.util.ExcelReader;
import com.linyun.airline.entities.TCustomerneedsEntity;
import com.linyun.airline.forms.TCustomerneedsAddForm;
import com.linyun.airline.forms.TCustomerneedsUpdateForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class CustomneedsViewService extends BaseService<TCustomerneedsEntity> {
	private static final Log log = Logs.get();

	private static final String EXCEL_PATH = "download";
	private static final String FILE_EXCEL_NAME = "客户需求导入模板.xlsx";

	/**
	 *
	 * TODO添加客户需求信息
	 * <p>
	 * TODO客户需求
	 *
	 * @param addForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings("deprecation")
	public Object addCustomNeedsInfo(TCustomerneedsAddForm addForm) {
		if (!Util.isEmpty(addForm.getLeavedateString())) {
			addForm.setLeavedate(new Date(addForm.getLeavedateString()));
		}
		if (!Util.isEmpty(addForm.getBackdateString())) {
			addForm.setBackdate(new Date(addForm.getBackdateString()));
		}
		addForm.setOptime(new Date());
		return this.add(addForm);
	}

	/**
	 * 
	 * TODO修改客户需求信息
	 * <p>
	 * TODO修改客户需求信息
	 *
	 * @param updateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateCustomNeedsInfo(TCustomerneedsUpdateForm updateForm) {
		if (!Util.isEmpty(updateForm.getLeavedateString())) {
			updateForm.setLeavedate(new Date(updateForm.getLeavedateString()));
		}
		if (!Util.isEmpty(updateForm.getBackdateString())) {
			updateForm.setBackdate(new Date(updateForm.getBackdateString()));
		}
		updateForm.setLastupdatetime(new Date());
		return this.update(updateForm);
	}

	/**
	 * 
	 * TODO关闭客户需求信息
	 * <p>
	 * TODO关闭客户需求信息
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object closeCustomNeeds(long id) {
		TCustomerneedsEntity customerneedsEntity = this.fetch(id);
		customerneedsEntity.setIsclose(1);
		return dbDao.update(customerneedsEntity);

	}

	/**
	 * 
	 * TODO导入Excel文件
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param file
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object inportExcelData(TempFile file, HttpServletRequest request) {

		try {
			InputStream is = new FileInputStream(file.getFile());
			ExcelReader excelReader = new ExcelReader();
			//获取Excel模板第二行之后的数据
			Map<Integer, String[]> map = excelReader.readExcelContent(is);
			List<TCustomerneedsEntity> customerneedsEntities = new ArrayList<TCustomerneedsEntity>();
			for (int i = 1; i <= map.size(); i++) {
				String[] row = map.get(i);
				TCustomerneedsEntity customerneeds = new TCustomerneedsEntity();
				//航空公司名称
				customerneeds.setAirline(row[0]);
				//去程日期
				customerneeds.setLeavedate(DateUtil.string2Date(row[1], DateUtil.FORMAT_YYYY_MM_DD));
				//去程航段
				customerneeds.setLeavecity(row[2]);
				//去程航班
				customerneeds.setLeaveflight(row[3]);
				//回程日期
				customerneeds.setBackdate(DateUtil.string2Date(row[4], DateUtil.FORMAT_YYYY_MM_DD));
				//返回城市
				customerneeds.setBackcity(row[5]);
				//回程航班
				customerneeds.setBackflight(row[6]);
				//人数
				customerneeds.setTotalcount(Float.valueOf(row[7]).intValue());
				//天数
				customerneeds.setTotalday(Float.valueOf(row[8]).intValue());
				//旅行社
				customerneeds.setTravel(row[9]);
				//联运要求
				customerneeds.setUniontransport(row[10]);
				customerneedsEntities.add(customerneeds);
			}
			//导入数据库
			dbDao.insert(customerneedsEntities);
			//删除备份文件
			file.delete();
		} catch (IOException e) {
			e.printStackTrace();

		}
		// TODO Auto-generated method stub
		return null;

	}

	public Object downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
		try {
			String filepath = request.getServletContext().getRealPath(this.EXCEL_PATH);
			String path = filepath + "\\" + this.FILE_EXCEL_NAME;
			File file = new File(path);// path是根据日志路径和文件名拼接出来的
			String filename = file.getName();// 获取日志文件名称
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("application/octet-stream");
			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			os.write(buffer);// 输出文件
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}