/**
 * ApplyApprovalService.java
 * com.linyun.airline.admin.applyapproval.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.applyapproval.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.admin.receivePayment.entities.TPayEntity;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.common.enums.ApprovalResultEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.entities.ApplyApprovalEntity;
import com.linyun.airline.entities.TPnrInfoEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月15日 	 
 */

public class ApplyApprovalService extends BaseService<ApplyApprovalEntity> {

	public Map<String, Object> findNums(HttpSession session) {
		String sqlStringInter = sqlManager.get("applyapproval_list_international");
		Sql sqlInter = Sqls.create(sqlStringInter);
		Cnd cnd = Cnd.NEW();
		/*TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();*/
		//国际
		cnd.and("companyId", "=", CommonConstants.UPCOMPANY_ID);
		cnd.and("orderstype", "=", OrderTypeEnum.TEAM.intKey());
		cnd.and("paystatus", "=", AccountPayEnum.APPROVAL.intKey());
		sqlInter.setCondition(cnd);
		List<Record> list = dbDao.query(sqlInter, cnd, null);
		int internationalNum = list.size();

		String sqlString = sqlManager.get("applyapproval_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd1 = Cnd.NEW();
		cnd1.and("companyId", "=", CommonConstants.UPCOMPANY_ID);
		cnd1.and("orderstype", "=", OrderTypeEnum.FIT.intKey());
		cnd1.and("orderPnrStatus", "=", AccountPayEnum.APPROVAL.intKey());
		List<Record> list1 = dbDao.query(sql, cnd1, null);
		int inlandNum = list1.size();
		Map<String, Object> re = MapUtil.map();
		re.put("internationalNum", internationalNum);
		re.put("inlandNum", inlandNum);
		return re;

	}

	public Object findData(HttpSession session, String operation, String date) {
		Integer orderType = null;
		if ("international".equalsIgnoreCase(operation)) {
			orderType = OrderTypeEnum.TEAM.intKey();
			String sqlString = sqlManager.get("applyapproval_list_international");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.NEW();
			//国际
			cnd.and("orderstype", "=", orderType);
			/*TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
			Long companyId = company.getId();*/
			//国际
			cnd.and("companyId", "=", CommonConstants.UPCOMPANY_ID);
			Map<String, Object> re = MapUtil.map();
			if (!Util.isEmpty(date)) {
				cnd.and("date(optime)", "=", date);
				re.put("date", date);
			}
			/*cnd.and("(orderPnrStatus", "=", AccountPayEnum.APPROVAL.intKey());
			cnd.or("orderPnrStatus", "=", AccountPayEnum.APPROVALPAYING.intKey());
			cnd.or("orderPnrStatus", "=", AccountPayEnum.REFUSE.intKey());*/
			sql.setCondition(cnd);
			List<Record> datalist = dbDao.query(sql, cnd, null);
			re.put("datalist", datalist);
			re.put("operation", operation);
			return re;
		} else if ("inlandNum".equalsIgnoreCase(operation)) {
			orderType = OrderTypeEnum.FIT.intKey();
			String sqlString = sqlManager.get("applyapproval_list");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.NEW();
			//国际
			cnd.and("orderstype", "=", orderType);
			/*TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
			Long companyId = company.getId();*/
			//国际
			cnd.and("companyId", "=", CommonConstants.UPCOMPANY_ID);
			Map<String, Object> re = MapUtil.map();
			if (!Util.isEmpty(date)) {
				cnd.and("date(optime)", "=", date);
				re.put("date", date);
			}
			/*cnd.and("(orderPnrStatus", "=", AccountPayEnum.APPROVAL.intKey());
			cnd.or("orderPnrStatus", "=", AccountPayEnum.APPROVALPAYING.intKey());
			cnd.or("orderPnrStatus", "=", AccountPayEnum.REFUSE.intKey());*/
			sql.setCondition(cnd);
			List<Record> datalist = dbDao.query(sql, cnd, null);
			re.put("datalist", datalist);
			re.put("operation", operation);
			return re;
		} else if ("others".equalsIgnoreCase(operation)) {

		}

		return null;
	}

	public Object findDetail(HttpSession session, String operation, String id, String date) {

		Integer orderType = null;
		if ("international".equalsIgnoreCase(operation)) {
			orderType = OrderTypeEnum.TEAM.intKey();
			String sqlString = sqlManager.get("applyapproval_list_international");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.NEW();
			/*Long companyId = 23l;*/
			/*TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
			Long companyId = company.getId();*/
			//国际
			cnd.and("companyId", "=", CommonConstants.UPCOMPANY_ID);
			/*cnd.and("orderstype", "=", orderType);*/
			/*cnd.and("(orderPnrStatus", "=", AccountPayEnum.APPROVAL.intKey());*/

			cnd.and("id", "=", id);
			sql.setCondition(cnd);
			List<Record> datalist = dbDao.query(sql, cnd, null);
			Map<String, Object> re = MapUtil.map();
			if (!Util.isEmpty(date)) {
				re.put("date", date);
			}
			re.put("detaillist", datalist.get(0));
			re.put("operation", operation);
			return re;
		} else if ("inlandNum".equalsIgnoreCase(operation)) {
			orderType = OrderTypeEnum.FIT.intKey();
			String sqlString = sqlManager.get("applyapproval_list");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.NEW();
			/*Long companyId = 23l;*/
			/*TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
			Long companyId = company.getId();*/
			//国际
			cnd.and("companyId", "=", CommonConstants.UPCOMPANY_ID);
			/*cnd.and("orderstype", "=", orderType);*/
			/*cnd.and("(orderPnrStatus", "=", AccountPayEnum.APPROVAL.intKey());*/

			cnd.and("id", "=", id);
			sql.setCondition(cnd);
			List<Record> datalist = dbDao.query(sql, cnd, null);
			Map<String, Object> re = MapUtil.map();
			if (!Util.isEmpty(date)) {
				re.put("date", date);
			}
			re.put("detaillist", datalist.get(0));
			re.put("operation", operation);
			return re;
		} else if ("others".equalsIgnoreCase(operation)) {

		}

		return null;
	}

	public Map<String, String> doAgree(HttpSession session, Long usingId, Long id, Long status, String temp,
			Long orderId, String operation) {

		Integer approvalResult = null;
		Integer approvalStatus = null;
		if ("agree".equalsIgnoreCase(temp)) {
			approvalResult = ApprovalResultEnum.ENABLE.intKey();
			approvalStatus = AccountPayEnum.APPROVALPAYING.intKey();
		} else if ("refuse".equalsIgnoreCase(temp)) {
			approvalResult = ApprovalResultEnum.DISABLE.intKey();
			approvalStatus = AccountPayEnum.REFUSE.intKey();

		}
		if ("inlandNum".equalsIgnoreCase(operation)) {

			TPnrInfoEntity pnrInfo = dbDao.fetch(TPnrInfoEntity.class, id);
			pnrInfo.setOrderPnrStatus(approvalStatus);
			pnrInfo.setOptime(new Date());
			int res1 = this.updateIgnoreNull(pnrInfo);
			//验证一个订单的pnr是否全部审核通过
			boolean flag = orderTrueOrFalse(usingId);
			if (flag) {
				TPayEntity pay = dbDao.fetch(TPayEntity.class, usingId);
				pay.setApproveResult(ApprovalResultEnum.ENABLE.intKey());
				pay.setApproveTime(new Date());
				int res = this.updateIgnoreNull(pay);
			}
			if (res1 > 0) {

				return JsonResult.success("审核通过");
			}
		} else if ("international".equalsIgnoreCase(operation)) {
			TUpOrderEntity upOrderInfo = dbDao.fetch(TUpOrderEntity.class, id);
			upOrderInfo.setPaystatus(approvalStatus);
			int res2 = this.updateIgnoreNull(upOrderInfo);
			boolean flag = orderInterTrueOrFalse(usingId);
			if (flag) {
				TPayEntity pay = dbDao.fetch(TPayEntity.class, usingId);
				pay.setApproveResult(ApprovalResultEnum.ENABLE.intKey());
				pay.setApproveTime(new Date());
				int res = this.updateIgnoreNull(pay);
			}
			if (res2 > 0) {

				return JsonResult.success("审核通过");
			}
		}
		/*pay.setApproveResult(approvalResult);
		pay.setApproveTime(new Date());
		int res = this.updateIgnoreNull(pay);*/

		return JsonResult.error("审核失败");

	}

	private boolean orderTrueOrFalse(Long orderId) {
		boolean flag = true;
		String sqlString = sqlManager.get("applyapproval_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		//国际
		cnd.and("orderId", "=", orderId);
		sql.setCondition(cnd);
		List<Record> datalist = dbDao.query(sql, cnd, null);
		for (Record record : datalist) {
			Integer status = (Integer) record.get("orderPnrStatus");
			if (status == AccountPayEnum.APPROVAL.intKey()) {
				flag = false;
				break;
			}
		}
		return flag;

	}

	//判断pay表的状态是否应该为通过
	private boolean orderInterTrueOrFalse(Long orderId) {
		boolean flag = true;
		String sqlString = sqlManager.get("applyapproval_list_international");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		//国际
		cnd.and("usingId", "=", orderId);
		sql.setCondition(cnd);
		List<Record> datalist = dbDao.query(sql, cnd, null);
		for (Record record : datalist) {
			Integer status = (Integer) record.get("paystatus");
			if (status == AccountPayEnum.APPROVAL.intKey()) {
				flag = false;
				break;
			}
		}
		return flag;

	}

}
