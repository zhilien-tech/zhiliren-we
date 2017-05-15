package com.linyun.airline.admin.drawback.grabreport.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.drawback.grabfile.entity.TGrabFileEntity;
import com.linyun.airline.admin.drawback.grabfile.entity.TPnrSystemMapEntity;
import com.linyun.airline.admin.drawback.grabfile.enums.FileTypeEnum;
import com.linyun.airline.admin.drawback.grabfile.enums.PNRRelationStatusEnum;
import com.linyun.airline.admin.drawback.grabreport.entity.PNRINFOList;
import com.linyun.airline.admin.drawback.grabreport.entity.TGrabReportEntity;
import com.linyun.airline.admin.drawback.grabreport.form.TGrabReportAddForm;
import com.linyun.airline.common.result.Select2Option;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class GrabreportViewService extends BaseService<TGrabReportEntity> {

	TGrabFileEntity grabFileEntity = null;

	/**
	 * 编辑时回显数据
	 * @param id
	 */
	public Object updateFilePreview(long id) {
		Map<String, Object> obj = Maps.newHashMap();
		obj.put("reportList", dbDao.fetch(TGrabReportEntity.class, id));
		return obj;
	}

	/**
	 * 打开附件预览时查询url
	 * @param pid
	 */
	public Object addFilePreview(long pid) {
		Map<String, Object> obj = Maps.newHashMap();
		TGrabFileEntity fetch = dbDao.fetch(TGrabFileEntity.class, pid);
		obj.put("fileurl", fetch);
		obj.put("pid", pid);
		return obj;
	}

	/**
	 * 报表数据保存
	 * @param addForm
	 */
	public Object saveFilePreview(TGrabReportAddForm addForm, long pid) {
		//查询之前文件名是什么,修改后进行保存
		TGrabFileEntity oneFileNameBefore = dbDao.fetch(TGrabFileEntity.class, pid);
		String beforeFileName = oneFileNameBefore.getFileName();
		Integer fileType = oneFileNameBefore.getType();
		//欲更新为
		if (!Util.isEmpty(beforeFileName) && fileType.equals(FileTypeEnum.FILE.intKey())) {
			dbDao.update(TGrabFileEntity.class, Chain.make("fileName", addForm.getFileName()),
					Cnd.where("id", "=", pid));
		}
		//double数据四舍五入保留小数点后两位
		TGrabReportEntity report = new TGrabReportEntity();
		DecimalFormat df = new DecimalFormat("#.00");
		String pnr = addForm.getPNR();//pnr
		/*Sql sql = Sqls.create(sqlManager.get("grab_report_list"));
		sql.params().set("pnr", pnr);
		TReportDto fetchPnr = DbSqlUtil.fetchEntity(dbDao, TReportDto.class, sql);*/
		Integer peopleNum = addForm.getPeopleNum();//人数
		Double costUnitPrice = addForm.getCostUnitPrice();//成本单价
		Double paidUnitPrice = addForm.getPaidUnitPrice();//实收单价(销售价)
		Double exciseTax1 = addForm.getExciseTax1();//消费税
		Integer backStatus = addForm.getBackStatus();//退税状态
		String inAustralianTime = addForm.getInAustralianTime();//入澳时间
		String outAustralianTime = addForm.getOutAustralianTime();//出澳时间
		Double swipe = addForm.getSwipe();//刷卡费
		Double remit = addForm.getRemit();//汇款
		Double tax = addForm.getTax();//税金/杂项
		String remark = addForm.getRemark();//备注

		report.setPeopleNum(peopleNum);
		report.setCostUnitPrice(costUnitPrice);
		report.setPaidUnitPrice(paidUnitPrice);
		report.setPNR(pnr);//PNR
		report.setBackStatus(backStatus);//退税状态
		report.setInAustralianTime(inAustralianTime);//入澳时间
		report.setOutAustralianTime(outAustralianTime);//出澳时间
		report.setRemark(remark);//备注

		if (!Util.isEmpty(exciseTax1)) {
			report.setExciseTax1(Double.parseDouble(df.format(exciseTax1)));//消费税
		} else {
			report.setExciseTax1(0.0);//页面传过来的值为0时消费税默认给0
		}
		if (!Util.isEmpty(swipe)) {
			report.setSwipe(Double.parseDouble(df.format(swipe)));//刷卡费
		} else {
			report.setSwipe(0.0);//页面传过来的值为0时刷卡费默认给0
		}
		if (!Util.isEmpty(tax)) {
			report.setTax(Double.parseDouble(df.format(tax)));//税金/杂项;
		} else {
			report.setTax(0.0);//页面传过来的值为0时税金/杂项默认给0
		}
		if (!Util.isEmpty(remit)) {
			report.setRemit(Double.parseDouble(df.format(remit)));//汇款
		} else {
			report.setRemit(0.0);//页面传过来的值为0时汇款默认给0
		}
		List<TGrabReportEntity> reportList = dbDao.query(TGrabReportEntity.class, null, null);
		TGrabReportEntity lastData = reportList.get(reportList.size() - 1);
		Double boforeBalance = 7312.92;
		if (!Util.isEmpty(lastData)) {
			boforeBalance = lastData.getDepositBalance();
		}
		if (Util.isEmpty(remit)) {
			remit = 0.0;
		}
		if (Util.isEmpty(peopleNum)) {
			peopleNum = 0;
		}
		if (Util.isEmpty(costUnitPrice)) {
			costUnitPrice = 0.0;
		}
		if (!Util.isEmpty(remit) && !Util.isEmpty(peopleNum) && !Util.isEmpty(costUnitPrice)) {
			//TODO 1、备用金额=[上期备用金额+汇款-(人数*成本单价)]
			Double depositBalance = Double
					.parseDouble(df.format((boforeBalance + remit) - (peopleNum * costUnitPrice)));
			report.setDepositBalance(depositBalance);
		}
		//2、代理费=SUM(票价<含行李>*代理返点)
		Double ticketPrice = addForm.getTicketPrice();//票价<含行李>
		Double agentRebate = addForm.getAgentRebate();//代理返点
		Double agencyFee = 0.0;
		if (!Util.isEmpty(ticketPrice) && !Util.isEmpty(agentRebate)) {
			agencyFee = Double.parseDouble(df.format(ticketPrice * agentRebate));//代理费
			report.setTicketPrice(Double.parseDouble(df.format(ticketPrice)));//票价<含行李>
			report.setAgencyFee(agencyFee);//代理费
			report.setAgentRebate(Double.parseDouble(df.format(agentRebate)));//代理返点
		}

		//3、税返点=SUM(代理费*10%)
		Double taxRebate = Double.parseDouble(df.format(agencyFee * 0.1));//税返点
		report.setTaxRebate(taxRebate);
		if (!Util.isEmpty(paidUnitPrice) && !Util.isEmpty(peopleNum)) {
			//4、实收单价(含操作费)=SUM(实收单价)
			Double realIncome = paidUnitPrice;//实收单价(含操作费)
			report.setRealIncome(realIncome);
			//5、实收合计(含操作费)=SUM[实收单价(含操作费)*人数]
			Double realTotal = (realIncome * peopleNum);//实收合计(含操作费)
			report.setRealTotal(realTotal);
			/**6、代理费2 =实收票价(含行李)*代理返点
			 *          =【实收合计(含操作费)-消费税2-税金/杂项 】*代理返点
			 *          =【实收合计(含操作费)-SUM(实收合计(含操作费)/11)-税金/杂项】*代理返点
			 */
			Double agencyFee2 = Double.parseDouble(df.format((realTotal - (realTotal / 11) - tax) * agentRebate));//代理费2
			report.setAgencyFee2(agencyFee2);
		}
		TGrabReportEntity insertData = dbDao.insert(report);
		return insertData;
	}

	/***
	 * PNR select2查询
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param findCompany
	 * @param companyName
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object selectPNRNames(String findPNR, String PNRName) {
		List<Record> PNRNameList = getPNRNameList(findPNR, PNRName);
		List<Select2Option> result = transform2SelectOptions(PNRNameList);
		return result;
	}

	private List<Select2Option> transform2SelectOptions(List<Record> PNRNameList) {
		return Lists.transform(PNRNameList, new Function<Record, Select2Option>() {
			@Override
			public Select2Option apply(Record record) {
				Select2Option op = new Select2Option();
				op.setId(record.getInt("pnrId"));
				op.setText(record.getString("PNR"));
				return op;
			}
		});
	}

	/**
	 * 获取PNR下拉框
	 * @param dictName
	 */
	public List<Record> getPNRNameList(String findPNR, final String PNRName) {
		String sqlString = sqlManager.get("grab_report_addPnrSystemMap");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("PNR", "like", "%" + Strings.trim(findPNR) + "%");
		//cnd.and("typeCode", "=", "YH");
		//cnd.and("status", "=", DataStatusEnum.ENABLE.intKey());
		if (!Util.isEmpty(PNRName)) {
			cnd.and("pnrId", "NOT IN", PNRName);
		}
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();
		return list;
	}

	public void findAndShowPNR(long id, String pnr) {
		String sqlString = sqlManager.get("grab_report_addPnrSystemMap");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("PNR", "=", pnr);
		sql.setCondition(cnd);
		List<PNRINFOList> pnrList = DbSqlUtil.query(dbDao, PNRINFOList.class, sql);
		for (PNRINFOList pnrinfoList : pnrList) {
			TPnrSystemMapEntity pnrTemp = dbDao.fetch(TPnrSystemMapEntity.class,
					Cnd.where("pnrId", "=", pnrinfoList.getPnrId()));

			TPnrSystemMapEntity pnrSystemMapEntity = new TPnrSystemMapEntity();
			pnrSystemMapEntity.setFinanceId(pnrinfoList.getFinanceId());
			pnrSystemMapEntity.setGrabFileId(id);
			//通过传回来的id写递归获取顶级父id
			getTopParent(id);

			if (!Util.isEmpty(grabFileEntity)) {
				pnrSystemMapEntity.setFileName(this.grabFileEntity.getFileName());
			}

			pnrSystemMapEntity.setOrderId(pnrinfoList.getOrderId());
			pnrSystemMapEntity.setPnrId(pnrinfoList.getPnrId());
			pnrSystemMapEntity.setPayReceiveRecordId(pnrinfoList.getPayReceiveRecordId());
			pnrSystemMapEntity.setUpdateTime(new Date());
			if (!Util.isEmpty(pnrTemp)) {
				Chain chain = Chain.make("financeId", pnrSystemMapEntity.getFinanceId());
				chain.add("grabFileId", pnrSystemMapEntity.getFinanceId());
				chain.add("orderId", pnrSystemMapEntity.getOrderId());
				chain.add("pnrId", pnrSystemMapEntity.getPnrId());
				chain.add("updateTime", new Date());
				chain.add("payReceiveRecordId", pnrSystemMapEntity.getPayReceiveRecordId());
				chain.add("relationStatus", pnrTemp.getRelationStatus());
				if (!Util.isEmpty(grabFileEntity)) {
					chain.add("fileName", grabFileEntity.getFileName());
				}
				dbDao.update(TPnrSystemMapEntity.class, chain, Cnd.where("pnrId", "=", pnrinfoList.getPnrId()));
			} else {
				pnrSystemMapEntity.setCreateTime(new Date());
				pnrSystemMapEntity.setRelationStatus(PNRRelationStatusEnum.NORELATION.intKey());
				dbDao.insert(pnrSystemMapEntity);

			}
		}

	}

	private void getTopParent(long id) {
		this.grabFileEntity = dbDao.fetch(TGrabFileEntity.class, id);
		long parentId = grabFileEntity.getParentId();
		if (parentId == 0 && !Util.isEmpty(grabFileEntity)) {

			return;
		}
		getTopParent(grabFileEntity.getParentId());
	}

}