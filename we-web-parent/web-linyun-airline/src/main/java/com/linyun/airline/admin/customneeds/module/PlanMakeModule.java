package com.linyun.airline.admin.customneeds.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;

import com.linyun.airline.admin.customneeds.service.PlanMakeService;

@IocBean
@At("/admin/customneeds")
@Filters({//@By(type = AuthFilter.class)
})
public class PlanMakeModule {

	private static final Log log = Logs.get();

	@Inject
	private PlanMakeService planMakeService;

	@At
	public Object getTravelNameSelect() {
		return planMakeService.getTravelNameSelect();
	}

}