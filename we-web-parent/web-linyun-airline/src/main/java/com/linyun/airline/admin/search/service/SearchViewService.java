package com.linyun.airline.admin.search.service;

import java.util.ArrayList;
import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.base.Splitter;
import com.linyun.airline.admin.customer.service.CustomerViewService;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.operationsArea.entities.TMessageEntity;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
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
	public Object getLinkNameSelect(String linkname) {
		List<TCustomerInfoEntity> customerInfos = customerViewService.getCustomerInfoByLinkName(linkname);
		return customerInfos;
	}

	/**
	 * 
	 * TODO(获取客户姓名下拉列表)
	 *
	 * @param linkname
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getPhoneNumSelect(String phonenum) {
		List<TCustomerInfoEntity> customerInfos = customerViewService.getCustomerInfoByPhone(phonenum);
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
		List<DictInfoEntity> citySelect = new ArrayList<DictInfoEntity>();
		try {
			citySelect = externalInfoService.findDictInfoByText(cityname, typeCode);
			if (citySelect.size() > 5) {
				citySelect = citySelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//出发抵达城市去重
		if (!Util.isEmpty(ids)) {
			//已选中的城市
			Iterable<String> outcityid = Splitter.on(",").split(ids);
			List<DictInfoEntity> existCitys = new ArrayList<DictInfoEntity>();
			for (String selectId : outcityid) {
				for (DictInfoEntity dictInfoEntity : citySelect) {
					if (dictInfoEntity.getId() == Long.valueOf(selectId)) {
						existCitys.add(dictInfoEntity);
					}
				}
			}
			citySelect.removeAll(existCitys);
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

}