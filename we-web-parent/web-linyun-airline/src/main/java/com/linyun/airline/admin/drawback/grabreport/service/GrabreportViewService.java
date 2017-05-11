package com.linyun.airline.admin.drawback.grabreport.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.linyun.airline.admin.drawback.grabfile.entity.TGrabFileEntity;
import com.linyun.airline.admin.drawback.grabfile.enums.FileTypeEnum;
import com.linyun.airline.admin.drawback.grabreport.entity.TGrabReportEntity;
import com.linyun.airline.admin.drawback.grabreport.form.TGrabReportAddForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class GrabreportViewService extends BaseService<TGrabReportEntity> {

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

		if (!Util.isEmpty(exciseTax1) && !Util.isEmpty(swipe) && !Util.isEmpty(tax) && !Util.isEmpty(remit)) {
			report.setExciseTax1(Double.parseDouble(df.format(exciseTax1)));//消费税
			report.setSwipe(Double.parseDouble(df.format(swipe)));//刷卡费
			report.setTax(Double.parseDouble(df.format(tax)));//税金/杂项;
			report.setRemit(Double.parseDouble(df.format(remit)));//汇款
		}

		List<TGrabReportEntity> reportList = dbDao.query(TGrabReportEntity.class, null, null);
		TGrabReportEntity lastData = reportList.get(reportList.size() - 1);
		Double boforeBalance = 7312.92;
		if (!Util.isEmpty(lastData)) {
			boforeBalance = lastData.getDepositBalance();
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
}