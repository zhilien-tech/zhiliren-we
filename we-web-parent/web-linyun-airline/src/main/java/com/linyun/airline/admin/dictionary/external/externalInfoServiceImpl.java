/**
 * InfoServiceImpl.java
 * com.xiaoka.template.admin.dictionary.dirinfo.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.external;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

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
	public List<DictInfoEntity> findDictInfoByName(String name) throws Exception {
		List<DictInfoEntity> infoList = dbDao.query(
				DictInfoEntity.class,
				Cnd.where("dictName", "like", Strings.trim(name) + "%").and("status", "=",
						DataStatusEnum.ENABLE.intKey()), null);
		return infoList;
	}
}
