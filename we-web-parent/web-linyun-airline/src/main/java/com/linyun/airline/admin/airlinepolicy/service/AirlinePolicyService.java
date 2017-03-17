/**
 * AirlinePolicyService.java
 * com.linyun.airline.admin.airlinepolicy.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.airlinepolicy.service;

import static com.uxuexi.core.common.util.ExceptionUtil.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;

import com.google.common.collect.Maps;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.common.enums.AirlinePolicyEnum;
import com.linyun.airline.common.enums.BankCardStatusEnum;
import com.linyun.airline.common.util.HtmlToPdf;
import com.linyun.airline.common.util.POIReadExcelToHtml;
import com.linyun.airline.common.util.Word2Html;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAirlinePolicyEntity;
import com.linyun.airline.entities.TAreaEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.forms.TAirlinePolicyAddForm;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author  孙斌
 * @Date	 2017年3月8日 	 
 */
public class AirlinePolicyService extends BaseService<TAirlinePolicyEntity> {
	@Inject
	private UploadService qiniuUploadService;//文件上传

	public Map<String, Object> listPage4Datatables(DataTablesParamForm sqlParamForm, HttpSession session) {

		checkNull(sqlParamForm, "sqlParamForm不能为空");

		String sqlString = sqlManager.get("bankcardmanager_find_money");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		/*Long companyId = 23l;*/
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();
		cnd.and("companyId", "=", companyId);
		cnd.and("status", "=", BankCardStatusEnum.ENABLE.intKey());
		sql.setCondition(cnd);
		Pager pager = new OffsetPager(sqlParamForm.getStart(), sqlParamForm.getLength());
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));

		sql.setPager(pager);

		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();

		Map<String, Object> re = MapUtil.map();
		re.put("data", list);
		re.put("draw", sqlParamForm.getDraw());
		re.put("recordsTotal", pager.getPageSize());
		re.put("recordsFiltered", pager.getRecordCount());
		return re;

	}

	public Map<String, Object> findConditionList() {

		Map<String, Object> map = Maps.newHashMap();
		/*查询航空公司*/
		List<DictInfoEntity> airlineCompanyList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "HKGS"),
				null);
		/*查询地区*/
		Cnd cnd = Cnd.NEW();
		List<TAreaEntity> areaList = dbDao.query(TAreaEntity.class, cnd, null);
		map.put("airlineCompanyList", airlineCompanyList);
		map.put("areaList", areaList);
		return map;

	}

	public TAirlinePolicyEntity addFile(TAirlinePolicyAddForm addForm) {

		addForm.setCreateTime(new Date());
		addForm.setUpdateTime(new Date());
		addForm.setStatus(AirlinePolicyEnum.ENABLE.intKey());
		String url = addForm.getUrl();
		addForm.setUrl(url);
		addForm.setFileName(addForm.getFileName());
		addForm.setFileSize(addForm.getFileSize());
		String extendName = url.substring(url.lastIndexOf(".") + 1, url.length());
		Word2Html word2Html = new Word2Html();
		HtmlToPdf htmlToPdf = new HtmlToPdf();
		if ("doc".equalsIgnoreCase(extendName)) {
			try {
				word2Html.docToHtml(url, "D:\\12.html");
				htmlToPdf.htmlConvertToPdf("D:\\12.html", "D:\\12.pdf");
				File file = new File("D:\\12.pdf");
				String pdfUrl = CommonConstants.IMAGES_SERVER_ADDR
						+ qiniuUploadService.uploadImage(new FileInputStream(file), "pdf", null);
				addForm.setPdfUrl(pdfUrl);
			} catch (Exception e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			} finally {
				File file1 = new File("D:\\12.html");
				File file2 = new File("D:\\12.pdf");
				if (file1.exists()) {
					file1.delete();
				}
				if (file2.exists()) {
					file2.delete();
				}
			}
		} else if ("xls".equalsIgnoreCase(extendName) || "xlsx".equalsIgnoreCase(extendName)) {
			try {
				POIReadExcelToHtml poiReadExcelToHtml = new POIReadExcelToHtml();
				poiReadExcelToHtml.excelConvertToPdf(url, "D:\\12.html");

				htmlToPdf.htmlConvertToPdf("D:\\12.html", "D:\\12.pdf");
				File file = new File("D:\\12.pdf");
				String pdfUrl = CommonConstants.IMAGES_SERVER_ADDR
						+ qiniuUploadService.uploadImage(new FileInputStream(file), "pdf", null);
				addForm.setPdfUrl(pdfUrl);
			} catch (Exception e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			} finally {
				File file1 = new File("D:\\12.html");
				File file2 = new File("D:\\12.pdf");
				if (file1.exists()) {
					file1.delete();
				}
				if (file2.exists()) {
					file2.delete();
				}
			}
		}
		return this.add(addForm);

	}

}
