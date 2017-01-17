/**
 * InfoServiceImpl.java
 * com.xiaoka.template.admin.dictionary.dirinfo.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.external;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@IocBean(name = "externalDictInfoService")
public class externalInfoServiceImpl extends BaseService<DictInfoEntity> implements externalInfoService {

	@Override
	public List<DictInfoEntity> findDictInfoByName(String name, String typeCode) throws Exception {
		List<DictInfoEntity> infoList = dbDao.query(
				DictInfoEntity.class,
				Cnd.where("dictName", "like", Strings.trim(name) + "%")
						.and("status", "=", DataStatusEnum.ENABLE.intKey()).and("typeCode", "=", typeCode), null);

		return infoList;
	}

	/**
	 * 
	 * TODO(根据字典编码 查询出发城市)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param codeName
	 * @param typeCode
	 * @return
	 * @throws Exception TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@Override
	public List<TDepartureCityEntity> findDepartureCityByCode(String codeName, String typeCode) throws Exception {
		List<TDepartureCityEntity> infoList = dbDao.query(
				TDepartureCityEntity.class,
				Cnd.where("dictCode", "like", Strings.trim(codeName) + "%")
						.and("status", "=", DataStatusEnum.ENABLE.intKey()).and("typeCode", "=", typeCode), null);

		return infoList;
	}

	@Override
	public List<DictInfoEntity> findDictInfoByText(String name, String typeCode) throws Exception {
		SqlExpressionGroup exp = new SqlExpressionGroup();
		exp.and("dictName", "like", Strings.trim(name) + "%").or("dictCode", "like", Strings.trim(name) + "%");
		List<DictInfoEntity> infoList = dbDao.query(DictInfoEntity.class,
				Cnd.where(exp).and("status", "=", DataStatusEnum.ENABLE.intKey()).and("typeCode", "=", typeCode), null);
		return infoList;
	}

	//查询多个类型
	@Override
	public List<DictInfoEntity> findDictInfoByTypes(String name, String typeCode1, String typeCode2) throws Exception {
		SqlExpressionGroup exp = new SqlExpressionGroup();
		exp.and("dictName", "like", Strings.trim(name) + "%").or("dictCode", "like", Strings.trim(name) + "%");
		List<DictInfoEntity> infoList = dbDao.query(
				DictInfoEntity.class,
				Cnd.where(exp).and("status", "=", DataStatusEnum.ENABLE.intKey()).and("typeCode", "=", typeCode1)
						.or("typeCode", "=", typeCode2), null);
		return infoList;
	}
}
