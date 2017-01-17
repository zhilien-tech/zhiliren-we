package com.linyun.airline.admin.search.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linyun.airline.admin.customer.service.CustomerViewService;
import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.operationsArea.entities.TMessageEntity;
import com.linyun.airline.common.sabre.dto.FlightSegment;
import com.linyun.airline.common.sabre.dto.InstalFlightAirItinerary;
import com.linyun.airline.common.sabre.dto.SabreExResponse;
import com.linyun.airline.common.sabre.dto.SabreResponse;
import com.linyun.airline.common.sabre.form.InstaFlightsSearchForm;
import com.linyun.airline.common.sabre.service.SabreService;
import com.linyun.airline.common.sabre.service.impl.SabreServiceImpl;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TUpcompanyEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class SearchViewService extends BaseService<TMessageEntity> {
	private static final Log log = Logs.get();

	@Inject
	private CustomerViewService customerViewService;

	@Inject
	private externalInfoService externalInfoService;

	private static final String CITYCODE = "CFCS";
	private static final String AIRCOMCODE = "HKGS";

	/**
	 * 
	 * TODO(获取客户姓名下拉列表)
	 *
	 * @param linkname
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getLinkNameSelect(String linkname, HttpSession session) {
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		TUpcompanyEntity upcompany = dbDao.fetch(TUpcompanyEntity.class, Cnd.where("comId", "=", companyId));
		long upcompanyRelationId = 0;
		if (!Util.isEmpty(upcompany)) {
			upcompanyRelationId = upcompany.getId();
		}
		List<TCustomerInfoEntity> customerInfos = new ArrayList<TCustomerInfoEntity>();
		try {
			customerInfos = dbDao
					.query(TCustomerInfoEntity.class,
							Cnd.where("linkMan", "like", Strings.trim(linkname) + "%").and("upComId", "=",
									upcompanyRelationId), null);
			if (customerInfos.size() > 5) {
				customerInfos = customerInfos.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerInfos;
	}

	/**
	 * 
	 * TODO(获取联系电话下拉列表)
	 *
	 * @param linkname
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getPhoneNumSelect(String phonenum, HttpSession session) {
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		TUpcompanyEntity upcompany = dbDao.fetch(TUpcompanyEntity.class, Cnd.where("comId", "=", companyId));
		long upcompanyRelationId = 0;
		if (!Util.isEmpty(upcompany)) {
			upcompanyRelationId = upcompany.getId();
		}
		List<TCustomerInfoEntity> customerInfos = new ArrayList<TCustomerInfoEntity>();
		try {
			customerInfos = dbDao.query(
					TCustomerInfoEntity.class,
					Cnd.where("telephone", "like", Strings.trim(phonenum) + "%").and("upComId", "=",
							upcompanyRelationId), null);
			if (customerInfos.size() > 5) {
				customerInfos = customerInfos.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerInfos;
	}

	/**
	 * 
	 * TODO(获取id查询客户信息)
	 *
	 * @param linkname
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getCustomerById(Long id) {
		return customerViewService.getCustomerById(id);
	}

	public Object getCitys(Long id) {
		return customerViewService.getOutCitys(id);
	}

	/**
	 * 获取城市下拉
	 * <p>
	 * 获取城市下拉
	 *
	 * @param cityname
	 * @return 返回城市下拉列表
	 */
	public Object getCitySelect(String cityname, String typeCode, String ids) {
		List<TDepartureCityEntity> citySelect = new ArrayList<TDepartureCityEntity>();
		try {
			citySelect = externalInfoService.findDepartureCityByCode(cityname, typeCode);
			//出发抵达城市去重
			if (!Util.isEmpty(ids)) {
				//已选中的城市
				List<TDepartureCityEntity> existCitys = new ArrayList<TDepartureCityEntity>();
				for (TDepartureCityEntity dictInfoEntity : citySelect) {
					if (dictInfoEntity.getDictCode().equals(ids)) {
						existCitys.add(dictInfoEntity);
					}
				}
				citySelect.removeAll(existCitys);
			}
			if (citySelect.size() > 5) {
				citySelect = citySelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return citySelect;
	}

	/**
	 * 获取航空公司下拉
	 * <p>
	 * 获取城市下拉
	 *
	 * @param cityname
	 * @return 返回航空下拉列表
	 */
	public Object getAirLineSelect(String airlinename, String ids) {
		List<DictInfoEntity> airlineSelect = new ArrayList<DictInfoEntity>();
		try {
			airlineSelect = externalInfoService.findDictInfoByText(airlinename, this.AIRCOMCODE);
			if (airlineSelect.size() > 5) {
				airlineSelect = airlineSelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return airlineSelect;
	}

	/**
	 * 查询跨海内陆飞机票
	 */
	public Object searchSingleTickets(InstaFlightsSearchForm searchForm) {
		InstaFlightsSearchForm form = new InstaFlightsSearchForm();
		form.setOrigin(searchForm.getOrigin());
		form.setDestination(searchForm.getDestination());
		form.setDeparturedate(searchForm.getDeparturedate());
		if (!Util.isEmpty(searchForm.getDeparturedate())) {
			//默认设置返回日期为15天
			Date outD = DateTimeUtil.string2Date(searchForm.getDeparturedate(), "yyyy-MM-dd");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String returnD = df.format(new Date(outD.getTime() + 15 * 24 * 60 * 60 * 1000));
			form.setReturndate(returnD);
		}
		//如果返回日期不为空， 则用
		if (!Util.isEmpty(searchForm.getReturndate())) {
			form.setReturndate(searchForm.getReturndate());
		}
		//乘客数量
		String childrenSelect = searchForm.getChildrenSelect();
		String agentSelect = searchForm.getAgentSelect();
		String babySelect = searchForm.getBabySelect();
		int passengercount = Integer.valueOf(childrenSelect) + Integer.valueOf(agentSelect)
				+ Integer.valueOf(babySelect);
		if (passengercount > 0) {
			form.setPassengercount(passengercount);
		}
		//航空公司
		if (!Util.isEmpty(searchForm.getIncludedcarriers())) {
			form.setIncludedcarriers(searchForm.getIncludedcarriers());
		}
		form.setPointofsalecountry("US");
		form.setOffset(1);
		form.setLimit(10);
		SabreService service = new SabreServiceImpl();
		SabreResponse resp = service.instaFlightsSearch(form);
		String departureDateTime = "";
		String arrivalDateTime = "";
		if (resp.getStatusCode() == 200) {
			List<InstalFlightAirItinerary> list = (List<InstalFlightAirItinerary>) resp.getData();
			for (InstalFlightAirItinerary instalFlightAirItinerary : list) {
				List<FlightSegment> flightSegment = instalFlightAirItinerary.getList();
				for (FlightSegment flight : flightSegment) {
					departureDateTime = flight.getDepartureDateTime();
					arrivalDateTime = flight.getArrivalDateTime();
					if (!Util.isEmpty(departureDateTime) || "" != departureDateTime) {
						departureDateTime = departureDateTime.substring(11, 16);
						flight.setDepartureDateTime(departureDateTime);
					}
					if (!Util.isEmpty(arrivalDateTime) || "" != arrivalDateTime) {
						arrivalDateTime = arrivalDateTime.substring(11, 16);
						flight.setArrivalDateTime(arrivalDateTime);
					}
				}
				String airlineCode = instalFlightAirItinerary.getAirlineCode();
				DictInfoEntity dictInfo = dbDao.fetch(DictInfoEntity.class,
						Cnd.where("typeCode", "=", "HKGS").and("dictCode", "=", airlineCode));
				String dictName = dictInfo.getDictName();
				if (!Util.isEmpty(dictName)) {
					instalFlightAirItinerary.setAirlineName(dictName);
				}

			}
		}
		if (resp.getStatusCode() == 400) {
			SabreExResponse sabreExResponse = (SabreExResponse) resp.getData();
			String message = sabreExResponse.getMessage();
			if (message.contains("Parameter 'origin' must be specified")) {
				sabreExResponse.setMessage("出发城市不能为空");
			}
			if (message.contains("Parameter 'destination' must be specified")) {
				sabreExResponse.setMessage("到达城市不能为空");
			}
			if (message.contains("Parameter 'departuredate' must be specified")) {
				sabreExResponse.setMessage("出发日期不能为空");
			}
			if (message.contains("arrivalDateTime")) {
				sabreExResponse.setMessage("返回日期不能为空");
			}
			if (message.contains("No results")) {
				sabreExResponse.setMessage("未查询到结果");
			}
			if (message.contains("Date range in 'departuredate' and 'returndate' exceeds the maximum allowed")) {
				sabreExResponse.setMessage("出发日期和返回日期之差不超过15天");
			}
		}
		if (resp.getStatusCode() == 404) {
			SabreExResponse sabreExResponse = (SabreExResponse) resp.getData();
			String message = sabreExResponse.getMessage();
			if (message.contains("No results")) {
				sabreExResponse.setMessage("未查询到结果");
			}
		}
		return resp;
	}

	/**
	 * 查询国际飞机票
	 */
	public Object searchTeamTickets(InstaFlightsSearchForm searchForm) {
		String origin = searchForm.getOrigin(); //起飞机场/出发城市
		String destination = searchForm.getDestination();//降落机场/到达城市
		String departuredate = searchForm.getDeparturedate();//出发日期
		String returndate = searchForm.getReturndate();//返回日期
		String airLevel = searchForm.getAirLevel();//舱位等级
		String includedcarriers = searchForm.getIncludedcarriers();//航空公司名称
		Sql sql = Sqls.create(sqlManager.get("team_ticket_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("tt.travelname", "=", "");
		if (!Util.isEmpty(origin)) {
			cnd.and("tt.leavescity", "=", origin);
		}
		if (!Util.isEmpty(destination)) {
			cnd.and("tt.backscity", "=", destination);
		}
		if (!Util.isEmpty(departuredate)) {
			cnd.and("date_format(tt.leavesdate,'%Y-%m-%d')", "like", departuredate);
		}
		if (!Util.isEmpty(returndate)) {
			cnd.and("date_format(tt.backsdate,'%Y-%m-%d')", "like", returndate);
		}
		if (!Util.isEmpty(includedcarriers)) {
			cnd.and("tt.airlinename", "=", includedcarriers);
		}
		List<Record> list = dbDao.query(sql, cnd, null);
		for (Record record : list) {
			String id = record.getString("opid");
			TUserEntity userEntity = dbDao.fetch(TUserEntity.class, Long.valueOf(id));
			record.set("opid", userEntity.getUserName());
			record.set("leavesdate", record.getString("leavesdate").substring(0, 10));
			record.set("orderstime", record.getString("orderstime").substring(0, 10));
			if (Util.isEmpty(record.getString("price"))) {
				record.set("price", "");
			}
			if (Util.isEmpty(record.getString("amount"))) {
				record.set("amount", "");
			}
		}

		return list;
	}

}