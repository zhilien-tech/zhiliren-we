package com.linyun.airline.admin.search.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.customer.service.CustomerViewService;
import com.linyun.airline.admin.customneeds.service.EditPlanService;
import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.operationsArea.entities.TMessageEntity;
import com.linyun.airline.admin.search.entities.ParsingSabreEntity;
import com.linyun.airline.admin.search.form.SearchTicketSqlForm;
import com.linyun.airline.common.result.Select2Option;
import com.linyun.airline.common.sabre.dto.FlightSegment;
import com.linyun.airline.common.sabre.dto.InstalFlightAirItinerary;
import com.linyun.airline.common.sabre.dto.SabreExResponse;
import com.linyun.airline.common.sabre.dto.SabreResponse;
import com.linyun.airline.common.sabre.form.InstaFlightsSearchForm;
import com.linyun.airline.common.sabre.service.SabreService;
import com.linyun.airline.common.sabre.service.impl.SabreServiceImpl;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAirlineInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TFlightInfoEntity;
import com.linyun.airline.entities.TOrderCustomneedEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUpcompanyEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class SearchViewService extends BaseService<TMessageEntity> {
	private static final Log log = Logs.get();

	@Inject
	private CustomerViewService customerViewService;

	@Inject
	private externalInfoService externalInfoService;

	@Inject
	private EditPlanService editPlanService;

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
		Map<String, Object> obj = new HashMap<String, Object>();

		TCustomerInfoEntity customerInfoEntity = dbDao.fetch(TCustomerInfoEntity.class, id);
		long responsibleId = customerInfoEntity.getResponsibleId();
		TUserEntity userEntity = dbDao.fetch(TUserEntity.class, Cnd.where("id", "=", responsibleId));
		String responsibleName = userEntity.getUserName();

		Sql citySql = Sqls.create(sqlManager.get("customer_cityOption_list"));
		Cnd cityCnd = Cnd.NEW();
		cityCnd.and("c.infoId", "=", id);
		cityCnd.orderBy("d.dictCode", "desc");
		citySql.setCondition(cityCnd);
		List<TDepartureCityEntity> outcityEntities = DbSqlUtil.query(dbDao, TDepartureCityEntity.class, citySql);
		//出发城市id 拼串
		String outcityIds = "";
		for (TDepartureCityEntity outcityEntity : outcityEntities) {
			outcityIds += outcityEntity.getId() + ",";
		}
		if (outcityIds.length() > 0) {
			outcityIds = outcityIds.substring(0, outcityIds.length() - 1);
		}
		obj.put("outcityIds", outcityIds);
		obj.put("outcitylist", Lists.transform(outcityEntities, new Function<TDepartureCityEntity, Select2Option>() {
			@Override
			public Select2Option apply(TDepartureCityEntity record) {
				Select2Option op = new Select2Option();
				String text = record.getDictCode() + " - " + record.getEnglishName() + " - " + record.getCountryName();
				op.setId(record.getId());
				op.setText(text);
				return op;
			}
		}));

		obj.put("responsibleName", responsibleName);
		obj.put("customerInfoEntity", customerInfoEntity);
		Double arrears = customerInfoEntity.getArrears();//历史欠款
		Double creditLine = customerInfoEntity.getCreditLine();//信用额度
		if (Util.isEmpty(arrears)) {
			arrears = 0.0;
		}
		if (Util.isEmpty(creditLine)) {
			creditLine = 0.0;
		}
		double subNum = creditLine - arrears;
		if (subNum < 10000) {
			obj.put("isArrearsRed", "true");
		}
		return JsonUtil.toJson(obj);
	}

	public Object getCitys(Long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		Sql citySql = Sqls.create(sqlManager.get("customer_cityOption_list"));
		Cnd cityCnd = Cnd.NEW();
		cityCnd.and("c.infoId", "=", id);
		cityCnd.orderBy("d.dictCode", "desc");
		citySql.setCondition(cityCnd);
		List<TDepartureCityEntity> outcityEntities = DbSqlUtil.query(dbDao, TDepartureCityEntity.class, citySql);
		//出发城市id 拼串
		String outcityIds = "";
		for (TDepartureCityEntity outcityEntity : outcityEntities) {
			outcityIds += outcityEntity.getId() + ",";
		}
		if (outcityIds.length() > 0) {
			outcityIds = outcityIds.substring(0, outcityIds.length() - 1);
		}
		obj.put("outcityIds", outcityIds);
		obj.put("outcitylist", Lists.transform(outcityEntities, new Function<TDepartureCityEntity, Select2Option>() {
			@Override
			public Select2Option apply(TDepartureCityEntity record) {
				Select2Option op = new Select2Option();
				String text = record.getDictCode() + " - " + record.getEnglishName() + " - " + record.getCountryName();
				op.setId(record.getId());
				op.setText(text);
				return op;
			}
		}));
		return obj;
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
			citySelect = externalInfoService.findCityByCode(cityname, typeCode);
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
			if (message.contains("Parameter 'passengercount' must be between 0 and 10")) {
				sabreExResponse.setMessage("乘客数量必须是 0 到 10 之间");
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
	 * 查询飞机库
	 */
	public Object listPage4Datatables(SearchTicketSqlForm sqlForm, HttpSession session) {
		//获得当前公司id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		sqlForm.setCompanyId(companyId);
		Map<String, Object> listPageData = this.listPage4Datatables(sqlForm);
		List<Record> list = (List<Record>) listPageData.get("data");
		boolean isOrigin = !Util.isEmpty(sqlForm.getOrigin());
		boolean isDestination = !Util.isEmpty(sqlForm.getDestination());
		boolean isDeparturedate = !Util.isEmpty(sqlForm.getDeparturedate());
		boolean isReturndate = !Util.isEmpty(sqlForm.getReturndate());
		for (Record record : list) {
			Cnd cnd = Cnd.NEW();
			cnd.and("planid", "=", record.get("id"));
			if (isOrigin || isDestination) {
				if (isOrigin) {
					cnd.and("leavecity", "=", sqlForm.getOrigin());
				}
				if (isDestination) {
					cnd.and("arrvicity", "=", sqlForm.getDestination());
				}
			}
			cnd.orderBy("leavedate", "asc");
			List<TAirlineInfoEntity> query = dbDao.query(TAirlineInfoEntity.class, cnd, null);
			record.put("airinfo", query);
		}
		listPageData.remove("data");
		List<Record> list2 = new ArrayList<Record>();
		if (isOrigin || isDestination) {
			for (Record record : list) {
				Object obj = record.get("airinfo");
				if (!Util.isEmpty(obj)) {
					list2.add(record);
				}
			}
			listPageData.put("data", list2);
			listPageData.put("recordsFiltered", list2.size());
		} else {
			listPageData.put("data", list);
		}
		return listPageData;
	}

	/**
	 * 获取城市下拉
	 * <p>
	 * 获取城市下拉
	 *
	 * @param cityname
	 * @return 返回城市下拉列表
	 */
	public Object getCustomerCitySelect(String cityname, String exname) {
		List<TDepartureCityEntity> citySelect = new ArrayList<TDepartureCityEntity>();
		try {
			citySelect = externalInfoService.findCityByCode(cityname, CITYCODE);
			//移除的城市
			TDepartureCityEntity removeinfo = new TDepartureCityEntity();
			for (TDepartureCityEntity dictInfoEntity : citySelect) {
				if (!Util.isEmpty(exname) && dictInfoEntity.getDictCode().equals(exname)) {
					removeinfo = dictInfoEntity;
				}
			}
			citySelect.remove(removeinfo);
			if (citySelect.size() > 5) {
				citySelect = citySelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return citySelect;
	}

	/**
	 * 
	 * 获取航班号下拉框
	 * <p>
	 * 获取航班号下拉框
	 *
	 * @param airlinename
	 * @param exname 
	 * @return 获取航班号下拉框
	 */
	public Object getCAirNumSelect(String airlinename, String exname) {
		//List<DictInfoEntity> airlineSelect = new ArrayList<DictInfoEntity>();
		List<TFlightInfoEntity> airlineSelect = new ArrayList<TFlightInfoEntity>();
		try {
			//airlineSelect = externalInfoService.findDictInfoByName(airlinename, this.AIRLINECODE);
			airlineSelect = dbDao.query(TFlightInfoEntity.class,
					Cnd.where("airlinenum", "like", Strings.trim(airlinename) + "%"), null);
			TFlightInfoEntity exinfo = new TFlightInfoEntity();
			for (TFlightInfoEntity tFlightInfoEntity : airlineSelect) {
				if (!Util.isEmpty(exname) && tFlightInfoEntity.getAirlinenum().equals(exname)) {
					exinfo = tFlightInfoEntity;
				}
			}
			airlineSelect.remove(exinfo);
			if (airlineSelect.size() > 5) {
				airlineSelect = airlineSelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return airlineSelect;
	}

	/**
	 * 
	 * TODO(根据航空公司代码，查询其对应的名称)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param airCompCode
	 * @param typeCode
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String getCAirNameByCode(String airCompCode, String typeCode) {
		DictInfoEntity dictInfo = dbDao.fetch(DictInfoEntity.class,
				Cnd.where("dictCode", "=", airCompCode).and("typeCode", "=", typeCode));
		return dictInfo.getDictName();
	}

	/**
	 * 
	 * TODO(解析 sabre)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param sabreText
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object parsingPNR(String sabrePNR) {

		//判断以哪种格式解析
		String parsingType = "";
		String[] sabrePnrs = sabrePNR.split("\\s+");
		String sabrePnrsStr = sabrePnrs[0];
		if (sabrePnrsStr.contains("/D￥") && sabrePnrsStr.contains("<<")) {
			parsingType = "1";
		} else if (sabrePnrsStr.contains("WP<<")) {
			parsingType = "3";
		} else {
			parsingType = "2";
		}

		ArrayList<Object> arrayList = Lists.newArrayList();
		HashMap<String, Object> map = Maps.newHashMap();

		if (parsingType == "1") {
			//分割sabre组
			//(?s)表示开启跨行匹配，\\d{1}一位数字，[A-Za-z]{2}两位字母，/斜线，\\s空白字符,.+任意字符出现1到多次，?非贪婪模式，最后以\n换行结束
			String regex = "(?s)\\d{1}[A-Za-z]{2}/.{2}\\s.+?\\d\n";
			Pattern pattern = Pattern.compile(regex);
			Matcher m = pattern.matcher(sabrePNR);
			ArrayList<String> sabreGroup = Lists.newArrayList();
			while (m.find()) {
				sabreGroup.add(m.group());
			}

			for (String pnrs : sabreGroup) {
				/***********************根据 128FEBAKLSYD/D￥VA<< 解析***********************/
				//序号
				int id = 0;
				//航空公司
				String airCompName = "";
				//航班号 
				String flightNum = "";
				//航段
				String airLine = "";
				//起飞日期
				String airLeavelDate = "";
				//起飞时间
				String airDepartureTime = "";
				//降落时间
				String airLandingTime = "";
				//舱位
				String airSeats = "";
				ParsingSabreEntity pSabreEntity = new ParsingSabreEntity();

				String[] pnr = pnrs.split(" ");
				id = Integer.parseInt(pnr[0].substring(0, 1));
				airCompName = pnr[0].substring(1);
				flightNum = pnr[1];
				airLeavelDate = sabrePnrs[1];
				String containStr = pnr[7];
				if (containStr.contains("*")) {
					String[] seatLine = containStr.split("[*]");
					for (String seat : pnr) {
						if (!seat.contains("/E") && seat.length() == 2) {
							airSeats += (" " + seat);
						}
					}
					airLine = seatLine[1];
					airSeats += (" " + seatLine[0]);
					airDepartureTime = pnr[8];
					airLandingTime = pnr[9];
				} else {
					for (String seat : pnr) {
						if (!seat.contains("/E") && seat.length() == 2) {
							airSeats += (" " + seat);
						}
					}
					airLine = pnr[8];
					airDepartureTime = pnr[9];
					airLandingTime = pnr[10];
				}
				pSabreEntity.setId(id);
				pSabreEntity.setAirlineComName(airCompName);
				pSabreEntity.setFlightNum(flightNum);
				pSabreEntity.setAirLeavelDate(airLeavelDate);
				pSabreEntity.setAirLine(airLine);
				pSabreEntity.setAirSeats(airSeats);
				pSabreEntity.setAirDepartureTime(airDepartureTime);
				pSabreEntity.setAirLandingTime(airLandingTime);

				arrayList.add(pSabreEntity);
				map.put("parsingType", "D￥");
				map.put("arrayList", arrayList);
			}
		}

		//根据07v4解析
		if (parsingType == "2") {
			ParsingSabreEntity pSabreEntity = new ParsingSabreEntity();
			int id = Integer.parseInt(sabrePnrs[1]);
			String airCompName = sabrePnrs[2];
			String flightNum = sabrePnrs[3].split("[a-zA-Z]")[0];
			int seatL = sabrePnrs[3].length();
			String airSeat = sabrePnrs[3].substring(seatL - 1, seatL);
			String airLeavelDate = sabrePnrs[4] + "(" + sabrePnrs[5] + ")";
			String airLine = sabrePnrs[6];
			String airDepartureTime = sabrePnrs[8];
			String airLandingTime = sabrePnrs[9];
			String airSeatNum = sabrePnrs[7];

			pSabreEntity.setId(id); //序号
			pSabreEntity.setAirlineComName(airCompName); //航空公司
			pSabreEntity.setFlightNum(flightNum); //航班号
			pSabreEntity.setAirSeats(airSeat); //舱位
			pSabreEntity.setAirLeavelDate(airLeavelDate); //起飞日期
			pSabreEntity.setAirLine(airLine); //航段
			pSabreEntity.setAirSeatNum(airSeatNum); //座位数
			pSabreEntity.setAirDepartureTime(airDepartureTime); //起飞时间
			pSabreEntity.setAirLandingTime(airLandingTime); //降落时间

			arrayList.add(pSabreEntity);
			map.put("parsingType", "00v0");
			map.put("arrayList", arrayList);
		}

		return map;
	}

	/**
	 * 
	 * TODO(解析etem)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param etemStr
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object parsingEtem(String etemStr) {
		//判断以哪种格式解析
		String parsingType = "";
		String[] etemPnrs = etemStr.split("\\s+");
		String etem = etemPnrs[0];
		if (etem.contains("avh/")) {
			parsingType = "1";
		} else if (etem.contains("SD")) {
			parsingType = "2";
		} else if (etem.contains("QTE:/")) {
			parsingType = "3";
		}

		ArrayList<Object> arrayList = Lists.newArrayList();
		HashMap<String, Object> map = Maps.newHashMap();

		/***********************黑屏查询：AVH/AKLSYD/28FEB/EK**************************/
		if (parsingType == "1") {

		}
		/************************输入SD5Q9来预订********************************/
		if (parsingType == "2") {
			ParsingSabreEntity pEtemEntity = new ParsingSabreEntity();
			int id = Integer.parseInt(etemPnrs[1].substring(0, 1));
			String flightNum = etemPnrs[2];
			String airSeats = etemPnrs[3];
			String presetDate = etemPnrs[4];
			String airLine = etemPnrs[5];
			String airSeatNum = etemPnrs[6];
			String airDepartureTime = etemPnrs[7];
			String airLandingTime = etemPnrs[8];

			pEtemEntity.setId(id);
			pEtemEntity.setFlightNum(flightNum);
			pEtemEntity.setAirSeats(airSeats);
			pEtemEntity.setPresetDate(presetDate);
			pEtemEntity.setAirLine(airLine);
			pEtemEntity.setAirSeatNum(airSeatNum);
			pEtemEntity.setAirDepartureTime(airDepartureTime);
			pEtemEntity.setAirLandingTime(airLandingTime);

			arrayList.add(pEtemEntity);
			map.put("parsingType", "SD0Q0");
			map.put("arrayList", arrayList);
		}
		/*********************输入QTE:/EK来查询价格*****************************/
		if (parsingType == "3") {
			ParsingSabreEntity pEtemEntity = new ParsingSabreEntity();
		}

		return map;
	}

	/**
	 * 添加查询结果
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param data
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public Object saveCustomerNeeds(String data) {

		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		Integer customerId = Integer.valueOf((String) fromJson.get("customerId"));
		boolean generateOrder = (boolean) fromJson.get("generateOrder");
		Integer orderType = Integer.valueOf((String) fromJson.get("orderType"));
		TUpOrderEntity orderinfo = new TUpOrderEntity();
		orderinfo.setUserid(customerId);
		orderinfo.setOrdersstatus(orderType);
		//生成订单号
		if (generateOrder) {
			orderinfo.setOrdersnum(editPlanService.generateOrderNum());
		}
		TUpOrderEntity insertOrder = dbDao.insert(orderinfo);
		List<Map<String, Object>> customdata = (List<Map<String, Object>>) fromJson.get("customdata");
		for (Map<String, Object> map : customdata) {
			//客户需求出发城市
			String leavecity = (String) map.get("leavecity");
			//客户需求抵达城市
			String arrivecity = (String) map.get("arrivecity");
			//日期
			String leavedate = (String) map.get("leavedate");
			Integer peoplecount = Integer.valueOf((String) map.get("peoplecount"));
			Integer tickettype = Integer.valueOf((String) map.get("tickettype"));
			TOrderCustomneedEntity customneedEntity = new TOrderCustomneedEntity();
			customneedEntity.setLeavecity(leavecity);
			customneedEntity.setArrivecity(arrivecity);
			customneedEntity.setLeavetdate(leavedate);
			customneedEntity.setPeoplecount(peoplecount);
			customneedEntity.setTickettype(tickettype);
			//与订单相关
			customneedEntity.setOrdernum(insertOrder.getId());
			TOrderCustomneedEntity insertCus = dbDao.insert(customneedEntity);
			//航班信息
			List<Map<String, Object>> airinfo = (List<Map<String, Object>>) map.get("airinfo");
			for (Map<String, Object> airmap : airinfo) {
				//航空公司
				String aircom = (String) airmap.get("aircom");
				//航班号
				String ailinenum = (String) airmap.get("ailinenum");
				//出发时间
				String leavetime = (String) airmap.get("leavetime");
				//抵达时间
				String arrivetime = (String) airmap.get("arrivetime");
				//成本价
				Double formprice = Double.valueOf((String) airmap.get("formprice"));
				//销售价
				Double price = Double.valueOf((String) airmap.get("price"));
				TAirlineInfoEntity airlineEntity = new TAirlineInfoEntity();
				airlineEntity.setAircom(aircom);
				airlineEntity.setAilinenum(ailinenum);
				airlineEntity.setLeavetime(leavetime);
				airlineEntity.setArrivetime(arrivetime);
				airlineEntity.setFormprice(formprice);
				airlineEntity.setPrice(price);
				airlineEntity.setNeedid(insertCus.getId());
				//添加航班信息
				dbDao.insert(airlineEntity);
			}
		}
		// TODO Auto-generated method stub
		return null;

	}
}