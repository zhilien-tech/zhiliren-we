/**
 * SabreServiceImpl.java
 * com.linyun.airline.common.sabre.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.jsoup.helper.StringUtil;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;
import com.linyun.airline.common.result.HttpResult;
import com.linyun.airline.common.sabre.SabreConfig;
import com.linyun.airline.common.sabre.SabreTokenFactory;
import com.linyun.airline.common.sabre.bean.SabreAccessToken;
import com.linyun.airline.common.sabre.ctx.SabreApplicationContext;
import com.linyun.airline.common.sabre.dto.BFMAirItinerary;
import com.linyun.airline.common.sabre.dto.FlightPriceInfo;
import com.linyun.airline.common.sabre.dto.FlightSegment;
import com.linyun.airline.common.sabre.dto.InstalFlightAirItinerary;
import com.linyun.airline.common.sabre.dto.OriginDest;
import com.linyun.airline.common.sabre.dto.SabreExResponse;
import com.linyun.airline.common.sabre.dto.SabreResponse;
import com.linyun.airline.common.sabre.form.BargainFinderMaxSearchForm;
import com.linyun.airline.common.sabre.form.InstaFlightsSearchForm;
import com.linyun.airline.common.sabre.service.SabreService;
import com.linyun.airline.common.util.HttpClientUtil;
import com.linyun.airline.common.util.JsonPathGeneric;
import com.sabre.api.sacs.configuration.SacsConfiguration;
import com.sabre.api.sacs.rest.domain.bargainfindermax.BargainFinderMaxRequest;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.AirTravelerAvail;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.CabinPref;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.CompanyName;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.DestinationLocation;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.IncludeVendorPref;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.IntelliSellTransaction;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.OTAAirLowFareSearchRQ;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.OriginDestinationInformation;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.OriginLocation;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.POS;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.PassengerTypeQuantity;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.RequestType;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.RequestorID;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.SegmentType;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.Source;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.TPAExtensions;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.TPAExtensions___;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.TravelPreferences;
import com.sabre.api.sacs.rest.domain.bargainfindermax.rq.TravelerInfoSummary;
import com.uxuexi.core.common.util.Util;

/**
 * @author   朱晓川
 * @Date	 2016年11月17日 	 
 */
@IocBean(name = "restSabreService")
public class RestSabreServiceImpl implements SabreService {

	//打log用
	private static Log log = Logs.getLog(RestSabreServiceImpl.class);

	private static final String default_prefer_level = "Preferred";

	@Override
	public SabreResponse instaFlightsSearch(InstaFlightsSearchForm paramForm) {
		final ApplicationContext ctx = SabreApplicationContext.context;
		SacsConfiguration sabreCfg = ctx.getBean(SacsConfiguration.class);
		String searchUrl = sabreCfg.getRestProperty("environment") + SabreConfig.INSTAL_FLIGHTS_URl
				+ HttpClientUtil.getParams(paramForm);

		HttpGet httpget = new HttpGet(searchUrl);

		SabreAccessToken accessToken = SabreTokenFactory.getAccessToken();
		String token = accessToken.getAccess_token();
		httpget.addHeader("Authorization", "Bearer " + token);
		log.info("executing request " + httpget.getRequestLine());

		HttpResult hr = HttpClientUtil.httpsGet(httpget);
		String result = hr.getResult();
		log.info(result);

		SabreResponse resp = new SabreResponse();
		int statusCode = hr.getStatusCode();
		if (HttpClientUtil.SUCCESS_CODE != statusCode) {
			SabreExResponse exResp = new JsonPathGeneric().getGenericObject(result, "$", SabreExResponse.class);
			resp.setStatusCode(statusCode);
			resp.setData(exResp);
			return resp;
		}

		List<Map<String, Object>> pricedItineraries = JsonPath.read(result, "$.PricedItineraries[*]");

		List<InstalFlightAirItinerary> list = Lists.newArrayList();
		if (!Util.isEmpty(pricedItineraries)) {
			for (Map<String, Object> map : pricedItineraries) {
				//不换行，不忽略空值
				String json = Json.toJson(map, JsonFormat.tidy());
				InstalFlightAirItinerary ir = new InstalFlightAirItinerary();

				//航空公司代码(两字)
				String airlineCode = JsonPath.read(json, "$.TPA_Extensions.ValidatingCarrier.Code");
				int sequenceNumber = JsonPath.read(json, "$.SequenceNumber");
				String ticketType = JsonPath.read(json, "$.TicketingInfo.TicketType");

				//TODO 航空公司名称、图片

				//价格信息 
				//TODO 类型转换异常 java.lang.String cannot be cast to java.lang.Double
				readPriceInfo(json, ir);

				ir.setAirlineCode(airlineCode);
				ir.setSequenceNumber(sequenceNumber);
				ir.setTicketType(ticketType);

				log.debug("sequenceNumber:" + sequenceNumber);
				log.debug("airlineCode:" + airlineCode);
				log.debug("ticketType:" + ticketType);
				log.debug("======================================");

				readSegmentsInfo(json, ir);

				list.add(ir);
			}
		}

		resp.setStatusCode(statusCode);
		resp.setData(list);
		return resp;
	}

	private void readPriceInfo(String json, InstalFlightAirItinerary ir) {
		String currencyCode = JsonPath.read(json, "$.AirItineraryPricingInfo.ItinTotalFare.TotalFare.CurrencyCode");
		double totalAmount = readDouble(json, "$.AirItineraryPricingInfo.ItinTotalFare.TotalFare.Amount");
		double baseAmount = readDouble(json, "$.AirItineraryPricingInfo.ItinTotalFare.BaseFare.Amount");
		double equivFareAmount = readDouble(json, "$.AirItineraryPricingInfo.ItinTotalFare.EquivFare.Amount");

		log.debug("currencyCode:" + currencyCode);
		log.debug("totalAmount:" + totalAmount);
		log.debug("baseAmount:" + baseAmount);
		log.debug("equivFareAmount:" + equivFareAmount);

		FlightPriceInfo fi = new FlightPriceInfo();
		fi.setCurrencyCode(currencyCode);
		fi.setTotalAmount(totalAmount);
		fi.setBaseAmount(baseAmount);
		fi.setEquivFareAmount(equivFareAmount);
		ir.setPriceInfo(fi);
	}

	/***
	 * 读取航段信息
	 */
	private void readSegmentsInfo(String json, InstalFlightAirItinerary ir) {

		List<Map<String, Object>> odopts = JsonPath.read(json,
				"$.AirItinerary.OriginDestinationOptions.OriginDestinationOption[*]");
		if (!Util.isEmpty(odopts)) {
			List<FlightSegment> segLst = Lists.newArrayList();
			for (Map<String, Object> each : odopts) {
				String optJ = Json.toJson(each, JsonFormat.tidy());

				List<Map<String, Object>> segments = JsonPath.read(optJ, "$.FlightSegment[*]");
				if (!Util.isEmpty(segments)) {

					for (Map<String, Object> eachSeg : segments) {
						String segJ = Json.toJson(eachSeg, JsonFormat.tidy());

						FlightSegment seg = new FlightSegment();

						//经停次数
						int StopQuantity = JsonPath.read(segJ, "$.StopQuantity");
						//出发机场
						String DepartureAirport = JsonPath.read(segJ, "$.DepartureAirport.LocationCode");
						//到达机场
						String ArrivalAirport = JsonPath.read(segJ, "$.ArrivalAirport.LocationCode");
						//航段合并数
						String MarriageGrp = JsonPath.read(segJ, "$.MarriageGrp");
						//出发时间
						String DepartureDateTime = JsonPath.read(segJ, "$.DepartureDateTime");
						//抵达时间
						String ArrivalDateTime = JsonPath.read(segJ, "$.ArrivalDateTime");

						//航班号
						String FlightNumber = JsonPath.parse(segJ).read("$.flightNumber", String.class);
						//耗时
						int ElapsedTime = JsonPath.read(segJ, "$.ElapsedTime");
						//准点率(%)
						//						int OnTimePerformance = JsonPath.read(segJ, "$.OnTimePerformance.Percentage");
						/**实际执行的航空公司代码*/
						String opAirlineCode = JsonPath.read(segJ, "$.OperatingAirline.Code");
						/**实际乘坐的航班号*/
						int opFlightNumber = JsonPath.read(segJ, "$.OperatingAirline.FlightNumber");
						/**出发时区*/
						int deTimeZone = JsonPath.parse(segJ).read("$.DepartureTimeZone.GMTOffset", Integer.class);
						/**到达时区*/
						int ArrivalTimeZone = JsonPath.parse(segJ).read("$.ArrivalTimeZone.GMTOffset", Integer.class);

						String ResBookDesigCode = JsonPath.read(segJ, "$.ResBookDesigCode");
						//						String Equipment = JsonPath.read(segJ, "$.Equipment.AirEquipType");

						seg.setStopQuantity(StopQuantity);
						seg.setArrivalAirport(ArrivalAirport);
						seg.setDepartureAirport(DepartureAirport);
						seg.setMarriageGrp(MarriageGrp);
						seg.setDepartureDateTime(DepartureDateTime);
						seg.setArrivalDateTime(ArrivalDateTime);

						seg.setFlightNumber(FlightNumber);
						seg.setElapsedTime(ElapsedTime);
						//						seg.setOnTimePerformance(OnTimePerformance);
						seg.setOpAirlineCode(opAirlineCode);
						seg.setOpFlightNumber(opFlightNumber);
						seg.setDepartureTimeZone(deTimeZone);
						seg.setArrivalTimeZone(ArrivalTimeZone);
						//扩展
						seg.setResBookDesigCode(ResBookDesigCode);
						//						seg.setEquipment(Equipment);

						log.info(seg);

						segLst.add(seg);
					}
					ir.setList(segLst);
				}//end of odopts for loop
			}
		}
	}

	private static double readDouble(String json, String key) {
		Object obj = JsonPath.read(json, key);
		double re = 0d;
		if (obj instanceof String) {
			re = Double.valueOf((String) obj);
		} else {
			re = Double.valueOf((Double) obj);
		}
		return re;
	}

	@Override
	public SabreResponse bargainFinderMaxSearch(BargainFinderMaxSearchForm paramForm) {
		SabreResponse resp = new SabreResponse();

		final ApplicationContext ctx = SabreApplicationContext.context;
		SacsConfiguration sabreCfg = ctx.getBean(SacsConfiguration.class);

		String searchUrl = sabreCfg.getRestProperty("environment") + SabreConfig.BARGAIN_FINDER_MAX_URl;
		HttpPost httpPost = new HttpPost(searchUrl);
		httpPost.addHeader("Accept", "*/*");
		httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");

		//添加授权信息
		SabreAccessToken accessToken = SabreTokenFactory.getAccessToken();
		String token = accessToken.getAccess_token();
		httpPost.addHeader("Authorization", "Bearer " + token);

		BargainFinderMaxRequest requestBody = getRestRequestBody(sabreCfg, paramForm);

		/**使用jackson ObjectMapper 将对象转为json，能正确处理 jsonschema2pojo自动生成的additionalProperties*/
		ObjectMapper mapper = new ObjectMapper();
		String req = "";
		try {
			req = mapper.writeValueAsString(requestBody);
			log.info("bfm request:" + req);
		} catch (JsonProcessingException e) {
			log.info("json 转换异常");
			e.printStackTrace();
			return resp;
		}

		StringEntity postEntity = new StringEntity(req, "UTF-8");
		httpPost.setEntity(postEntity);
		log.debug("executing request " + httpPost.getRequestLine());

		HttpResult hr = HttpClientUtil.httpsPost(httpPost);
		String result = hr.getResult();
		log.info("bfm result:" + result);

		int statusCode = hr.getStatusCode();
		if (HttpClientUtil.SUCCESS_CODE != statusCode) {
			SabreExResponse exResp = new JsonPathGeneric().getGenericObject(result, "$", SabreExResponse.class);
			resp.setStatusCode(statusCode);
			resp.setData(exResp);
			return resp;
		}

		List<Map<String, Object>> pricedItineraries = JsonPath.read(result,
				"$.OTA_AirLowFareSearchRS.PricedItineraries.PricedItinerary[*]");

		List<BFMAirItinerary> list = Lists.newArrayList();

		if (!Util.isEmpty(pricedItineraries)) {
			for (Map<String, Object> map : pricedItineraries) {
				//不换行，不忽略空值
				String json = Json.toJson(map, JsonFormat.tidy());
				BFMAirItinerary ir = new BFMAirItinerary();

				//航空公司代码(两字)
				String airlineCode = JsonPath.read(json, "$.TPA_Extensions.ValidatingCarrier.Code");
				int sequenceNumber = JsonPath.read(json, "$.SequenceNumber");
				String ticketType = JsonPath.read(json, "$.TicketingInfo.TicketType");

				//TODO 航空公司名称、图片

				//价格信息 
				//TODO 类型转换异常 java.lang.String cannot be cast to java.lang.Double
				readBFMPriceInfo(json, ir);

				ir.setAirlineCode(airlineCode);
				ir.setSequenceNumber(sequenceNumber);
				ir.setTicketType(ticketType);

				log.debug("sequenceNumber:" + sequenceNumber);
				log.debug("airlineCode:" + airlineCode);
				log.debug("ticketType:" + ticketType);
				log.debug("======================================");

				readBFMSegmentsInfo(json, ir);

				list.add(ir);
			}
		}
		resp.setStatusCode(200);
		resp.setData(list);

		return resp;
	}

	private void readBFMPriceInfo(String json, BFMAirItinerary ir) {
		//airItineraryPricingInfo  是数组

		List<Map<String, Object>> prices = JsonPath.read(json, "$.AirItineraryPricingInfo[*]");
		if (!Util.isEmpty(prices)) {
			List<FlightPriceInfo> fiLst = Lists.newArrayList();
			for (Map<String, Object> map : prices) {
				//不换行，不忽略空值
				String jprice = Json.toJson(map, JsonFormat.tidy());

				double totalAmount = JsonPath.parse(jprice).read("$.ItinTotalFare.TotalFare.Amount", Double.class);
				double baseAmount = JsonPath.parse(jprice).read("$.ItinTotalFare.BaseFare.Amount", Double.class);
				double equivFareAmount = JsonPath.parse(jprice).read("$.ItinTotalFare.EquivFare.Amount", Double.class);

				//数组字符串
				String[] readVal = JsonPath.parse(jprice).read(".ItinTotalFare.TotalFare.CurrencyCode", String[].class);
				String currencyCode = "";
				if (!Util.isEmpty(readVal)) {
					currencyCode = readVal[0];
				}

				log.debug("currencyCode:" + currencyCode);
				log.debug("totalAmount:" + totalAmount);
				log.debug("baseAmount:" + baseAmount);
				log.debug("equivFareAmount:" + equivFareAmount);

				FlightPriceInfo fi = new FlightPriceInfo();
				fi.setCurrencyCode(currencyCode + "");
				fi.setTotalAmount(totalAmount);
				fi.setBaseAmount(baseAmount);
				fi.setEquivFareAmount(equivFareAmount);
				fiLst.add(fi);
			}
			ir.setPriceInfos(fiLst);
		}
	}

	/***
	 * 读取航段信息
	 */
	private void readBFMSegmentsInfo(String json, BFMAirItinerary ir) {

		List<Map<String, Object>> odopts = JsonPath.read(json,
				"$.AirItinerary.OriginDestinationOptions.OriginDestinationOption[*]");
		if (!Util.isEmpty(odopts)) {
			List<FlightSegment> segLst = Lists.newArrayList();
			for (Map<String, Object> each : odopts) {
				String optJ = Json.toJson(each, JsonFormat.tidy());

				List<Map<String, Object>> segments = JsonPath.read(optJ, "$.FlightSegment[*]");
				if (!Util.isEmpty(segments)) {

					for (Map<String, Object> eachSeg : segments) {
						String segJ = Json.toJson(eachSeg, JsonFormat.tidy());

						FlightSegment seg = new FlightSegment();

						//经停次数
						int StopQuantity = JsonPath.parse(segJ).read("$.StopQuantity", Integer.class);
						//出发机场
						String DepartureAirport = JsonPath.read(segJ, "$.DepartureAirport.LocationCode");
						//到达机场
						String ArrivalAirport = JsonPath.read(segJ, "$.ArrivalAirport.LocationCode");
						//航段合并数
						String MarriageGrp = JsonPath.read(segJ, "$.MarriageGrp");
						//出发时间
						String DepartureDateTime = JsonPath.read(segJ, "$.DepartureDateTime");
						//抵达时间
						String ArrivalDateTime = JsonPath.read(segJ, "$.ArrivalDateTime");

						//航班号
						String FlightNumber = JsonPath.parse(segJ).read("$.FlightNumber", String.class);
						//耗时
						int ElapsedTime = JsonPath.parse(segJ).read("$.ElapsedTime", Integer.class);

						/**实际执行的航空公司代码*/
						String opAirlineCode = JsonPath.read(segJ, "$.OperatingAirline.Code");
						/**实际乘坐的航班号*/
						int opFno = JsonPath.parse(segJ).read("$.OperatingAirline.FlightNumber", Integer.class);
						/**出发时区*/
						int deTimeZone = JsonPath.parse(segJ).read("$.DepartureTimeZone.GMTOffset", Integer.class);
						/**到达时区*/
						int ArrivalTimeZone = JsonPath.parse(segJ).read("$.ArrivalTimeZone.GMTOffset", Integer.class);

						String ResBookDesigCode = JsonPath.read(segJ, "$.ResBookDesigCode");
						//						String Equipment = JsonPath.read(segJ, "$.equipment.airEquipType");

						seg.setStopQuantity(StopQuantity);
						seg.setArrivalAirport(ArrivalAirport);
						seg.setDepartureAirport(DepartureAirport);
						seg.setMarriageGrp(MarriageGrp);
						seg.setDepartureDateTime(DepartureDateTime);
						seg.setArrivalDateTime(ArrivalDateTime);

						seg.setFlightNumber(FlightNumber);
						seg.setElapsedTime(ElapsedTime);
						seg.setOpAirlineCode(opAirlineCode);
						seg.setOpFlightNumber(opFno);
						seg.setDepartureTimeZone(deTimeZone);
						seg.setArrivalTimeZone(ArrivalTimeZone);
						//扩展
						seg.setResBookDesigCode(ResBookDesigCode);
						//						seg.setEquipment(Equipment);

						log.info(seg);

						segLst.add(seg);
					}
					ir.setList(segLst);
				}//end of odopts for loop
			}
		}
	}

	private BargainFinderMaxRequest getRestRequestBody(SacsConfiguration config, BargainFinderMaxSearchForm paramForm) {
		//<POS>
		List<Source> sourceList = new ArrayList<>();
		//do not forget set the PCC code
		//requestorId上不能出现PseudoCityCode
		RequestorID requestorID = new RequestorID().withID("1").withType("1")
				.withCompanyName(new CompanyName().withCode("TN"));
		Source source = new Source().withRequestorID(requestorID).withAdditionalProperty("PseudoCityCode",
				config.getRestProperty("group"));

		sourceList.add(source);
		POS pos = new POS().withSource(sourceList);

		//出发到达信息
		//OriginDestinationInformation
		List<OriginDestinationInformation> originDestinationInfos = new ArrayList<>();
		List<OriginDest> originDests = paramForm.getOriginDests();
		if (!Util.isEmpty(originDests)) {
			for (int idx = 0; idx < originDests.size(); idx++) {
				OriginDest od = originDests.get(idx);
				OriginDestinationInformation odi = new OriginDestinationInformation();

				//出发时间
				odi.setDepartureDateTime(od.getDeparturedate());

				//出发地
				OriginLocation ol = new OriginLocation();
				ol.setLocationCode(od.getOrigin());
				odi.setOriginLocation(ol);

				//目的地
				DestinationLocation dl = new DestinationLocation();
				dl.setLocationCode(od.getDestination());
				odi.setDestinationLocation(dl);

				//SegmentType
				TPAExtensions segTpa = new TPAExtensions();
				SegmentType segType = new SegmentType();
				segType.setCode("O");
				segTpa.withSegmentType(segType);

				//航空公司
				List<String> carriers = paramForm.getCarriers();
				if (!Util.isEmpty(carriers)) {
					List<IncludeVendorPref> includeVendorPrefs = Lists.newArrayList();
					for (String carrier : carriers) {
						IncludeVendorPref vendorPref = new IncludeVendorPref();
						vendorPref.setCode(carrier);
						includeVendorPrefs.add(vendorPref);
					}
					segTpa.withIncludeVendorPref(includeVendorPrefs);
				}
				odi.withTPAExtensions(segTpa);

				//rph
				String rph = (idx + 1) + "";
				odi.setRPH(rph);

				originDestinationInfos.add(odi);
			}
		}
		//TravelPreferences  
		//仓位等级
		TravelPreferences tPfs = new TravelPreferences();
		//票务协议
		tPfs.setValidInterlineTicket(true);

		//仓位等级
		/*
			"Only","Unacceptable","Preferred"
		*/
		List<String> airLevels = paramForm.getAirLevel();
		if (!Util.isEmpty(airLevels)) {
			for (String cabin : airLevels) {
				CabinPref cabinPref = new CabinPref();
				cabinPref.setCabin(cabin);
				cabinPref.setPreferLevel(default_prefer_level);
				tPfs.getCabinPref().add(cabinPref);
			}
		}

		//-------------------<TravelerInfoSummary>--------------------------
		//-------------------乘客类型和人数以及座位数--------------------------
		List<PassengerTypeQuantity> pqs = Lists.newArrayList();

		//成人
		Integer adt = paramForm.getAdt();
		if (!Util.isEmpty(adt)) {
			PassengerTypeQuantity adtTypeQuantity = new PassengerTypeQuantity();
			adtTypeQuantity.setCode("ADT");
			adtTypeQuantity.setQuantity(adt);
			pqs.add(adtTypeQuantity);
		}

		//儿童
		Integer cnn = paramForm.getCnn();
		if (!Util.isEmpty(cnn)) {
			PassengerTypeQuantity adtTypeQuantity = new PassengerTypeQuantity();
			adtTypeQuantity.setCode("CNN");
			adtTypeQuantity.setQuantity(cnn);
			pqs.add(adtTypeQuantity);
		}

		//婴儿
		Integer inf = paramForm.getInf();
		if (!Util.isEmpty(inf)) {
			PassengerTypeQuantity adtTypeQuantity = new PassengerTypeQuantity();
			adtTypeQuantity.setCode("INF");
			adtTypeQuantity.setQuantity(inf);
			pqs.add(adtTypeQuantity);
		}

		AirTravelerAvail avl = new AirTravelerAvail();
		avl.setPassengerTypeQuantity(pqs);
		List<AirTravelerAvail> avls = Lists.newArrayList();
		avls.add(avl);

		//座位数
		String seatsRequested = paramForm.getSeatsRequested();

		TravelerInfoSummary tis = new TravelerInfoSummary();
		if (StringUtil.isNumeric(seatsRequested)) {
			tis.setAdditionalProperty("SeatsRequested", new Integer[] { new Integer(seatsRequested) });
		} else {
			log.error("座位数必须是数字");
		}
		tis.setAirTravelerAvail(avls);

		//请求多少条数据
		RequestType rqt = new RequestType();
		rqt.setName("50ITINS");
		IntelliSellTransaction trans = new IntelliSellTransaction();
		trans.withRequestType(rqt);
		TPAExtensions___ tpa = new TPAExtensions___();
		tpa.withIntelliSellTransaction(trans);

		//OTAAirLowFareSearchRQ 不能出现version
		BargainFinderMaxRequest bfmreq = new BargainFinderMaxRequest()
				.withOTAAirLowFareSearchRQ(new OTAAirLowFareSearchRQ()
						.withAdditionalProperty("ResponseType", "OTA")
						.withAdditionalProperty("ResponseVersion", "3.1.0")
						.withAdditionalProperty("Target", "Production")
						//是否直飞
						.withDirectFlightsOnly(paramForm.getDirectFlightsOnly()).withPOS(pos)
						.withOriginDestinationInformation(originDestinationInfos).withTravelPreferences(tPfs)
						.withTravelerInfoSummary(tis).withTPAExtensions(tpa));
		return bfmreq;
	}
}