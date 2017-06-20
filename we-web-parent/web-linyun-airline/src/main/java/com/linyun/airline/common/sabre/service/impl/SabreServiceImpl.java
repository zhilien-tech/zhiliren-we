/**
 * SabreServiceImpl.java
 * com.linyun.airline.common.sabre.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.context.ApplicationContext;

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
import com.sabre.api.sacs.contract.bargainfindermax.AirSearchPrefsType;
import com.sabre.api.sacs.contract.bargainfindermax.CabinPrefType;
import com.sabre.api.sacs.contract.bargainfindermax.CabinType;
import com.sabre.api.sacs.contract.bargainfindermax.CompanyNameType;
import com.sabre.api.sacs.contract.bargainfindermax.ExchangeOriginDestinationInformationType.SegmentType;
import com.sabre.api.sacs.contract.bargainfindermax.IncludeVendorPrefType;
import com.sabre.api.sacs.contract.bargainfindermax.OTAAirLowFareSearchRQ;
import com.sabre.api.sacs.contract.bargainfindermax.OTAAirLowFareSearchRQ.OriginDestinationInformation;
import com.sabre.api.sacs.contract.bargainfindermax.OTAAirLowFareSearchRQ.TPAExtensions;
import com.sabre.api.sacs.contract.bargainfindermax.OTAAirLowFareSearchRS;
import com.sabre.api.sacs.contract.bargainfindermax.OriginDestinationInformationType.DestinationLocation;
import com.sabre.api.sacs.contract.bargainfindermax.OriginDestinationInformationType.OriginLocation;
import com.sabre.api.sacs.contract.bargainfindermax.POSType;
import com.sabre.api.sacs.contract.bargainfindermax.PassengerTypeQuantityType;
import com.sabre.api.sacs.contract.bargainfindermax.PreferLevelType;
import com.sabre.api.sacs.contract.bargainfindermax.SourceType;
import com.sabre.api.sacs.contract.bargainfindermax.TransactionType;
import com.sabre.api.sacs.contract.bargainfindermax.TransactionType.RequestType;
import com.sabre.api.sacs.contract.bargainfindermax.TravelerInfoSummaryType;
import com.sabre.api.sacs.contract.bargainfindermax.TravelerInformationType;
import com.sabre.api.sacs.contract.bargainfindermax.UniqueIDType;
import com.sabre.api.sacs.soap.orchestratedflow.BargainFinderMaxSoapActivity;
import com.sabre.api.sacs.workflow.SharedContext;
import com.sabre.api.sacs.workflow.Workflow;
import com.uxuexi.core.common.util.Util;

/**
 * @author   朱晓川
 * @Date	 2016年11月17日 	 
 */
@IocBean(name = "sabreService")
public class SabreServiceImpl implements SabreService {

	//打log用
	private static Log log = Logs.getLog(SabreServiceImpl.class);

	@Override
	public SabreResponse instaFlightsSearch(InstaFlightsSearchForm paramForm) {
		SabreResponse resp = new SabreResponse();

		String searchUrl = SabreConfig.environment + SabreConfig.INSTAL_FLIGHTS_URl
				+ HttpClientUtil.getParams(paramForm);

		HttpGet httpget = new HttpGet(searchUrl);

		SabreAccessToken accessToken = SabreTokenFactory.getAccessToken();
		String token = accessToken.getAccess_token();
		httpget.addHeader("Authorization", "Bearer " + token);
		log.info("executing request " + httpget.getRequestLine());

		HttpResult hr = HttpClientUtil.httpsGet(httpget);
		String result = hr.getResult();
		log.info(result);

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

				log.info("sequenceNumber:" + sequenceNumber);
				log.info("airlineCode:" + airlineCode);
				log.info("ticketType:" + ticketType);
				log.info("======================================");

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
		final ApplicationContext ctx = SabreApplicationContext.context;
		SacsConfiguration sabreCfg = ctx.getBean(SacsConfiguration.class);

		BargainFinderMaxSoapActivity bfmAcitivity = ctx.getBean(BargainFinderMaxSoapActivity.class);
		bfmAcitivity.setRequest(getRequestBody(sabreCfg, paramForm));
		Workflow workflow = new Workflow(bfmAcitivity);
		SharedContext sCtx = workflow.run();
		String rq = (String) sCtx.getResult("BargainFinderMaxRQ");
		log.info("bargainFinderMax request:" + rq);
		OTAAirLowFareSearchRS rsObj = (OTAAirLowFareSearchRS) sCtx.getResult("BargainFinderMaxRSObj");
		String rs = (String) sCtx.getResult("BargainFinderMaxRS");
		String result = Json.toJson(rsObj, JsonFormat.compact());
		log.info("bargainFinderMax result:" + result);

		SabreResponse resp = new SabreResponse();
		if (!Util.isEmpty(rsObj)) {
			List<Map<String, Object>> pricedItineraries = JsonPath.read(result,
					"$.pricedItineraries.pricedItinerary[*]");

			List<BFMAirItinerary> list = Lists.newArrayList();

			if (!Util.isEmpty(pricedItineraries)) {
				for (Map<String, Object> map : pricedItineraries) {
					//不换行，不忽略空值
					String json = Json.toJson(map, JsonFormat.tidy());
					BFMAirItinerary ir = new BFMAirItinerary();

					//航空公司代码(两字)
					String airlineCode = JsonPath.read(json, "$.tpaExtensions.validatingCarrier.code");
					int sequenceNumber = JsonPath.read(json, "$.sequenceNumber");
					String ticketType = JsonPath.read(json, "$.ticketingInfo.ticketType");

					//TODO 航空公司名称、图片

					//价格信息 
					//TODO 类型转换异常 java.lang.String cannot be cast to java.lang.Double
					readBFMPriceInfo(json, ir);

					ir.setAirlineCode(airlineCode);
					ir.setSequenceNumber(sequenceNumber);
					ir.setTicketType(ticketType);

					log.info("sequenceNumber:" + sequenceNumber);
					log.info("airlineCode:" + airlineCode);
					log.info("ticketType:" + ticketType);
					log.info("======================================");

					readBFMSegmentsInfo(json, ir);

					list.add(ir);
				}
			}
			resp.setStatusCode(200);
			resp.setData(list);
		}
		return resp;
	}

	private void readBFMPriceInfo(String json, BFMAirItinerary ir) {
		//airItineraryPricingInfo  是数组

		List<Map<String, Object>> prices = JsonPath.read(json, "$.airItineraryPricingInfo[*]");
		if (!Util.isEmpty(prices)) {
			List<FlightPriceInfo> fiLst = Lists.newArrayList();
			for (Map<String, Object> map : prices) {
				//不换行，不忽略空值
				String jprice = Json.toJson(map, JsonFormat.tidy());

				double totalAmount = JsonPath.parse(jprice).read("$.itinTotalFare.totalFare.amount", Double.class);
				double baseAmount = JsonPath.parse(jprice).read("$.itinTotalFare.baseFare.amount", Double.class);
				double equivFareAmount = JsonPath.parse(jprice).read("$.itinTotalFare.equivFare.amount", Double.class);

				//数组字符串
				String[] readVal = JsonPath.parse(jprice).read(".itinTotalFare.totalFare.currencyCode", String[].class);
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
				"$.airItinerary.originDestinationOptions.originDestinationOption[*]");
		if (!Util.isEmpty(odopts)) {
			List<FlightSegment> segLst = Lists.newArrayList();
			for (Map<String, Object> each : odopts) {
				String optJ = Json.toJson(each, JsonFormat.tidy());

				List<Map<String, Object>> segments = JsonPath.read(optJ, "$.flightSegment[*]");
				if (!Util.isEmpty(segments)) {

					for (Map<String, Object> eachSeg : segments) {
						String segJ = Json.toJson(eachSeg, JsonFormat.tidy());

						FlightSegment seg = new FlightSegment();

						//经停次数
						int StopQuantity = JsonPath.parse(segJ).read("$.stopQuantity", Integer.class);
						//出发机场
						String DepartureAirport = JsonPath.read(segJ, "$.departureAirport.locationCode");
						//到达机场
						String ArrivalAirport = JsonPath.read(segJ, "$.arrivalAirport.locationCode");
						//航段合并数
						String MarriageGrp = JsonPath.read(segJ, "$.marriageGrp");
						//出发时间
						String DepartureDateTime = JsonPath.read(segJ, "$.departureDateTime");
						//抵达时间
						String ArrivalDateTime = JsonPath.read(segJ, "$.arrivalDateTime");

						//航班号
						String FlightNumber = JsonPath.parse(segJ).read("$.flightNumber", String.class);
						//耗时
						int ElapsedTime = JsonPath.parse(segJ).read("$.elapsedTime", Integer.class);

						/**实际执行的航空公司代码*/
						String opAirlineCode = JsonPath.read(segJ, "$.operatingAirline.code");
						/**实际乘坐的航班号*/
						int opFno = JsonPath.parse(segJ).read("$.operatingAirline.flightNumber", Integer.class);
						/**出发时区*/
						int deTimeZone = JsonPath.parse(segJ).read("$.departureTimeZone.gmtOffset", Integer.class);
						/**到达时区*/
						int ArrivalTimeZone = JsonPath.parse(segJ).read("$.arrivalTimeZone.gmtOffset", Integer.class);

						String ResBookDesigCode = JsonPath.read(segJ, "$.resBookDesigCode");
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

	private OTAAirLowFareSearchRQ getRequestBody(SacsConfiguration config, BargainFinderMaxSearchForm paramForm) {
		OTAAirLowFareSearchRQ result = new OTAAirLowFareSearchRQ();

		//<POS>
		POSType pos = new POSType();
		SourceType srcType = new SourceType();
		srcType.setPseudoCityCode(config.getSoapProperty("group"));//PCC 必须

		UniqueIDType uidType = new UniqueIDType();
		uidType.setType("1");
		uidType.setID("1");
		CompanyNameType compNameType = new CompanyNameType();
		compNameType.setCode("TN");
		uidType.setCompanyName(compNameType);
		srcType.setRequestorID(uidType);

		pos.getSource().add(srcType);
		result.setPOS(pos);

		List<OriginDest> originDests = paramForm.getOriginDests();
		if (!Util.isEmpty(originDests)) {
			for (int idx = 0; idx < originDests.size(); idx++) {
				OriginDest od = originDests.get(idx);
				//<OriginDestinationInformation
				OriginDestinationInformation odi = new OriginDestinationInformation();

				//出发时间
				odi.setDepartureDateTime(od.getDeparturedate());

				//出发地/机场
				OriginLocation ol = new OriginLocation();
				ol.setLocationCode(od.getOrigin());
				odi.setOriginLocation(ol);

				//目的地/机场
				DestinationLocation dl = new DestinationLocation();
				dl.setLocationCode(od.getDestination());
				odi.setDestinationLocation(dl);

				//TPAExtensions
				com.sabre.api.sacs.contract.bargainfindermax.OTAAirLowFareSearchRQ.OriginDestinationInformation.TPAExtensions odiTpa = new com.sabre.api.sacs.contract.bargainfindermax.OTAAirLowFareSearchRQ.OriginDestinationInformation.TPAExtensions();

				//航空公司
				List<String> carriers = paramForm.getCarriers();
				if (!Util.isEmpty(carriers)) {
					List<IncludeVendorPrefType> includeVendorPref = odiTpa.getIncludeVendorPref();
					for (String carrier : carriers) {
						IncludeVendorPrefType vendorPref = new IncludeVendorPrefType();
						vendorPref.setCode(carrier);
						includeVendorPref.add(vendorPref);
					}
				}

				//SegmentType
				SegmentType segType = new SegmentType();
				segType.setCode("O");//normal
				odiTpa.setSegmentType(segType);
				odi.setTPAExtensions(odiTpa);

				odi.setRPH((idx + 1) + "");
				result.getOriginDestinationInformation().add(odi);
			}
		}

		//<TravelPreferences>
		AirSearchPrefsType travelPreferences = new AirSearchPrefsType();
		//票务协议
		travelPreferences.setValidInterlineTicket(true);

		//仓位等级
		CabinPrefType cabinPref = new CabinPrefType();
		cabinPref.setCabin(CabinType.fromValue(paramForm.getAirLevel()));
		cabinPref.setPreferLevel(PreferLevelType.PREFERRED);
		travelPreferences.getCabinPref().add(cabinPref);
		result.setTravelPreferences(travelPreferences);

		//<TravelerInfoSummary>
		TravelerInfoSummaryType tiSummaryType = new TravelerInfoSummaryType();
		//座位数
		String seatsRequested = paramForm.getSeatsRequested();
		if (!Util.isEmpty(seatsRequested)) {
			tiSummaryType.getSeatsRequested().add(new BigInteger(seatsRequested));
		}
		TravelerInformationType airTravelerAvail = new TravelerInformationType();
		//成人
		Integer adt = paramForm.getAdt();
		if (!Util.isEmpty(adt)) {
			PassengerTypeQuantityType adtTypeQuantity = new PassengerTypeQuantityType();
			adtTypeQuantity.setCode("ADT");
			adtTypeQuantity.setQuantity(paramForm.getAdt());
			airTravelerAvail.getPassengerTypeQuantity().add(adtTypeQuantity);
		}

		//儿童
		Integer cnn = paramForm.getCnn();
		if (!Util.isEmpty(cnn)) {
			PassengerTypeQuantityType cnnTypeQuantity = new PassengerTypeQuantityType();
			cnnTypeQuantity.setCode("CNN");
			cnnTypeQuantity.setQuantity(cnn);
			airTravelerAvail.getPassengerTypeQuantity().add(cnnTypeQuantity);
		}

		//婴儿
		Integer inf = paramForm.getInf();
		if (!Util.isEmpty(inf)) {
			PassengerTypeQuantityType infTypeQuantity = new PassengerTypeQuantityType();
			infTypeQuantity.setCode("INF");
			infTypeQuantity.setQuantity(inf);
			airTravelerAvail.getPassengerTypeQuantity().add(infTypeQuantity);
		}
		tiSummaryType.getAirTravelerAvail().add(airTravelerAvail);
		result.setTravelerInfoSummary(tiSummaryType);

		//<TPA_Extension>
		TPAExtensions tpa = new TPAExtensions();
		TransactionType intelliSell = new TransactionType();
		RequestType reqType = new RequestType();
		reqType.setName("100ITINS");
		intelliSell.setRequestType(reqType);
		tpa.setIntelliSellTransaction(intelliSell);

		result.setTPAExtensions(tpa);
		result.setVersion(config.getSoapProperty("BargainFinderMaxRQVersion"));

		return result;
	}
}
