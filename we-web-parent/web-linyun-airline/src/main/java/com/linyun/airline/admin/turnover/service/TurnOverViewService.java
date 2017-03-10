/**
 * TurnOverViewService.java
 * com.linyun.airline.admin.turnover.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.turnover.service;

import static com.uxuexi.core.common.util.ExceptionUtil.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Maps;
import com.linyun.airline.common.enums.TurnOverStatusEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TBankCardEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TTurnOverEntity;
import com.linyun.airline.forms.TTurnOverAddForm;
import com.linyun.airline.forms.TTurnOverFindForm;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.form.DataTablesParamForm;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月4日 	 
 */
@IocBean
public class TurnOverViewService extends BaseService<TTurnOverEntity> {

	/**
	 * list页面的查询
	 * @param session 
	 * @see com.uxuexi.core.web.base.service.BaseService#listPage4Datatables(com.uxuexi.core.web.form.DataTablesParamForm)
	 */

	public Map<String, Object> listPage4Datatables(@Param("..") final TTurnOverFindForm findForm,
			DataTablesParamForm sqlParamForm, HttpSession session) {

		checkNull(sqlParamForm, "sqlParamForm不能为空");

		/*String sqlString = sqlManager.get("turnoverlist_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("companyId", "=", companyId);
		cnd.and("tu.status", "=", TurnOverStatusEnum.ENABLE.intKey());*/

		String sqlString = sqlManager.get("turnoverlist_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		Integer status = findForm.getStatus();
		String bankCardName = findForm.getBankCardName();
		String projectName = findForm.getProjectName();
		String operation = findForm.getOperation();
		String currency = findForm.getCurrency();
		Date tradeDate = findForm.getTradeDate();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		String companyName = company.getComName();
		if (!Util.isEmpty(status)) {
			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.status", "=", status);
			//sql.setCondition(cnd);
		} else {

			cnd.and("tu.status", "=", TurnOverStatusEnum.ENABLE.intKey());
		}
		if (!Util.isEmpty(bankCardName)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.bankName", "=", bankCardName);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(projectName)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.projectName", "=", projectName);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(operation)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.purpose", "=", operation);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(currency)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.currency", "=", currency);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(tradeDate)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.tradeDate", "=", tradeDate);
		}
		cnd.and("tu.companyName", "=", companyName);
		/*cnd.orderBy("updateTime", "DESC");*/
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

	/**
	 * 
	 * 添加时的selected框的查询
	 */
	public Map<String, Object> addList(HttpSession session) {
		Map<String, Object> maps = Maps.newHashMap();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();
		/*Long id = 23L;*/
		//查询有哪些银行卡类型

		List<DictInfoEntity> bankCardTypeList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "YHKLX"),
				null);
		//查询有哪些项目
		List<DictInfoEntity> projectList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "FKYT"), null);
		//查询有哪些币种类型
		List<DictInfoEntity> currencyList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "BZ"), null);
		Cnd cnd = Cnd.NEW();
		cnd.and("companyId", "=", companyId);
		List<TBankCardEntity> bankNameList = dbDao.query(TBankCardEntity.class, cnd, null);
		maps.put("bankCardTypeList", bankCardTypeList);
		maps.put("projectList", projectList);
		maps.put("currencyList", currencyList);
		maps.put("bankNameList", bankNameList);
		return maps;
	}

	/*保存流水*/
	public Object addTurnOver(@Param("..") TTurnOverAddForm addForm, final HttpSession session) {
		/**
		 * 获取公司的id
		 * 
		 */
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();

		/*Long companyId = 23l;*/
		String companyName = company.getComName();
		String cardNum = addForm.getCardNum();
		String bankName = addForm.getBankName();
		String purpose = addForm.getPurpose();
		double money = addForm.getMoney();

		Cnd cnd = Cnd.NEW();
		cnd.and("cardNum", "=", cardNum);
		List<TBankCardEntity> bankCardList = dbDao.query(TBankCardEntity.class, cnd, null);
		if (!Util.isEmpty(bankCardList)) {
			addForm.setBankCardId(bankCardList.get(0).getId());
		}
		long backCardId = addForm.getBankCardId();
		//根据id查出银行卡的对象
		TBankCardEntity bankcard = dbDao.fetch(TBankCardEntity.class, backCardId);
		if ("支出".equalsIgnoreCase(purpose)) {
			//根据查出的金额进行更新
			bankcard.setBalance(bankcard.getBalance() - money);
			nutDao.update(bankcard);

		} else if ("收入".equalsIgnoreCase(purpose)) {
			bankcard.setBalance(bankcard.getBalance() + money);
			nutDao.update(bankcard);

		}

		addForm.setCompanyName(companyName);
		addForm.setStatus(TurnOverStatusEnum.ENABLE.intKey());
		addForm.setCreateTime(new Date());
		addForm.setUpdateTime(new Date());

		FormUtil.add(dbDao, addForm, TTurnOverEntity.class);
		return null;
	}

	/**
	 * 
	 * list页面上的搜索框的查询
	 */
	public Object findList(HttpSession session) {
		Map<String, Object> maps = Maps.newHashMap();
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();
		/*Long id = 23L;*/
		//查询有哪些币种
		List<DictInfoEntity> currencyList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "BZ"), null);
		List<DictInfoEntity> projectList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "FKYT"), null);
		Cnd cnd = Cnd.NEW();
		cnd.and("companyId", "=", companyId);
		List<TBankCardEntity> bankNameList = dbDao.query(TBankCardEntity.class, cnd, null);
		maps.put("currencyList", currencyList);
		maps.put("projectList", projectList);
		maps.put("bankNameList", bankNameList);

		return maps;

	}

	public Object selectTurnOverByCondition(TTurnOverFindForm findForm, DataTablesParamForm sqlParamForm) {

		String sqlString = sqlManager.get("turnoverlist_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		Integer status = findForm.getStatus();
		String bankCardName = findForm.getBankCardName();
		String projectName = findForm.getProjectName();
		String operation = findForm.getOperation();
		String currency = findForm.getCurrency();
		Date tradeDate = findForm.getTradeDate();
		if (!Util.isEmpty(status)) {
			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.status", "=", status);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(bankCardName)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.bankName", "=", bankCardName);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(projectName)) {

			/*cnd.and("companyId", "=", companyId);*/
			//cnd.and("tu.status", "=", projectName);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(operation)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.purpose", "=", operation);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(currency)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.currency", "=", currency);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(tradeDate)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("tu.tradeDate", "=", tradeDate);
		}
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

	public Object checkBankCardNumEnough(String cardNum, String purpose, double money) {
		Map<String, Object> map = new HashMap<String, Object>();
		Cnd cnd = Cnd.NEW();
		cnd.and("cardNum", "=", cardNum);
		List<TBankCardEntity> bankCardList = dbDao.query(TBankCardEntity.class, cnd, null);
		boolean flag = false;
		if ("支出".equalsIgnoreCase(purpose)) {

			if (!Util.isEmpty(bankCardList)) {
				TBankCardEntity bankcard = bankCardList.get(0);
				if (bankcard.getBalance() >= money) {
					flag = true;
				}

			}
		} else if ("收入".equalsIgnoreCase(purpose)) {
			flag = true;
		}
		map.put("valid", flag);
		return map;

	}

}
