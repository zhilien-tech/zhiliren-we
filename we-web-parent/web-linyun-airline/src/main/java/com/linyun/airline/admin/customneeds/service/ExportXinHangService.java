/**
 * ExportXinHangService.java
 * com.linyun.airline.admin.customneeds.module
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.customneeds.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.customneeds.util.ExportXinHang;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TPlanInfoEntity;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2016年12月19日 	 
 */
@IocBean
public class ExportXinHangService extends BaseService<TPlanInfoEntity> {
	//第一个表格标题
	private static final String ONETITLE = "Lull";
	//第二个表格标题
	private static final String TWOTITLE = "PEAK01-CNY";
	//第三个表格标题
	private static final String THREETITLE = " PEAK02- before after CNY";
	//第一个表格列标题
	private static final String[] ONEONETITLE = { "No.", "Agent", "OUTBOUND", "OUTBOUND", "OUTBOUND", "OUTBOUND",
			"OUTBOUND", "OUTBOUND", "INBOUND", "INBOUND", "INBOUND", "INBOUND", "INBOUND", "Grp Size", "Frequency",
			"Series Start Date: dd-mmm-yy", "Series End Date: dd-mmm-yy" };
	private static final String[] ONETWOTITLE = { "No.", "Agent", "OB CN Destn", "OB CN Flt No", "OB CN Flt DOP",
			"OB Destn", "OB Flt No", "OB DOP", "IB Destn", "IB Flt No", "IB DOP", "IB CN Flt No", "IB CN DOP",
			"Grp Size", "Frequency", "Series Start Date: dd-mmm-yy", "Series End Date: dd-mmm-yy" };
	//第二个表格列标题
	private static final String[] TWOONETITLE = { "No.", "Agent", "OUTBOUND", "OUTBOUND", "OUTBOUND", "OUTBOUND",
			"OUTBOUND", "OUTBOUND", "INBOUND", "INBOUND", "INBOUND", "INBOUND", "INBOUND", "Grp Size", "Frequency",
			"Fare in CNY    (Chinese newyear)  ", "Fare in SGD    (Chinese newyear) " };
	private static final String[] TWOTWOTITLE = { "No.", "Agent", "OB CN Destn", "OB CN Flt No", "OB CN Flt DOP",
			"OB Destn", "OB Flt No", "OB DOP", "IB Destn", "IB Flt No", "IB DOP", "IB CN Flt No", "IB CN DOP",
			"Grp Size", "Frequency", "Fare in CNY    (Chinese newyear)  ", "Fare in SGD    (Chinese newyear) " };
	//第三个表格列标题
	private static final String[] THREEONETITLE = { "No.", "Agent", "OUTBOUND", "OUTBOUND", "OUTBOUND", "OUTBOUND",
			"OUTBOUND", "OUTBOUND", "INBOUND", "INBOUND", "INBOUND", "INBOUND", "INBOUND", "Grp Size", "Frequency",
			"Fare in CNY    (Before/After CNY)", "Fare in SGD    (Before/After CNY)" };
	private static final String[] THREETWOTITLE = { "No.", "Agent", "OB CN Destn", "OB CN Flt No", "OB CN Flt DOP",
			"OB Destn", "OB Flt No", "OB DOP", "IB Destn", "IB Flt No", "IB DOP", "IB CN Flt No", "IB CN DOP",
			"Grp Size", "Frequency", "Fare in CNY    (Before/After CNY)", "Fare in SGD    (Before/After CNY)" };

	/**
	 * 导出新航模板
	 * <p>
	 * 导出新航模板
	 *
	 * @param session
	 * @param response
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 * @throws Exception 
	 */
	public Object exportXinHangTemplate(HttpSession session, HttpServletResponse response) {
		try {
			//准备标题
			List<String> title = new ArrayList<String>();
			title.add(this.ONETITLE);
			title.add(this.TWOTITLE);
			title.add(this.THREETITLE);
			//准备列标题
			List<String[]> rowNames = new ArrayList<String[]>();
			rowNames.add(this.ONEONETITLE);
			rowNames.add(this.TWOONETITLE);
			rowNames.add(this.THREEONETITLE);
			//准备数据
			List<List<Object[]>> data = new ArrayList<List<Object[]>>();
			//计划制作数据
			List<TPlanInfoEntity> makePlansData = this.getMakePlansData(session);
			//第一张表的数据
			List<Object[]> onedata = new ArrayList<Object[]>();
			onedata.add(ONETWOTITLE);
			int num = 1;
			for (TPlanInfoEntity tPlanInfoEntity : makePlansData) {
				Object[] obj = { num, "LYWY", };
				num++;
			}
			data.add(onedata);
			//第二张表的数据
			List<Object[]> twodata = new ArrayList<Object[]>();
			twodata.add(TWOTWOTITLE);
			data.add(twodata);
			List<Object[]> threedata = new ArrayList<Object[]>();
			threedata.add(THREETWOTITLE);
			data.add(threedata);
			ExportXinHang excel = new ExportXinHang(title, rowNames, data, response);
			excel.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取计划制作导出Excel数据
	 * @param session 
	 */
	private List<TPlanInfoEntity> getMakePlansData(HttpSession session) {
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		List<TPlanInfoEntity> rerultList = dbDao.query(TPlanInfoEntity.class,
				Cnd.where("issave", "=", 0).and("companyid", "=", company.getId()).orderBy("leavesdate", "asc"), null);
		return rerultList;
	}

}
