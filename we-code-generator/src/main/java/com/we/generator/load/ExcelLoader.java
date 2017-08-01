/**
 * ExcelLoader.java
 * com.we.generator.load
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.we.generator.load;

import java.io.InputStream;
import java.util.Map;

import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.we.generator.config.LoadConfigWeb;
import com.we.generator.core.ActionDesc;
import com.we.generator.core.ModuleDesc;
import com.we.generator.core.enums.LogicEnum;
import com.we.generator.util.ExcelReader;
import com.we.generator.util.Utils;

/**
 * 根据输入流，加载Excel信息
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 * @param ins 
 */
public class ExcelLoader {

	private static final Log log = Logs.get();

	//InputStream ins = ClassLoader.getSystemResourceAsStream("code-generator/module.xlsx");
	public static Map<Integer, String[]> loadExcel(InputStream ins) {
		Map<Integer, String[]> map = null;
		try {
			ExcelReader excelReader = new ExcelReader();
			map = excelReader.readExcelContent(ins);
		} catch (Exception e) {
			log.info("文件格式错误，请使用模板文件进行操作");
		}
		return map;
	}

	//获取模块信息
	public static Map<String, ModuleDesc> getModuleMap(Map<Integer, String[]> moduleInfo, String basePkg) {

		String baseUri = "/";
		Map<String, ModuleDesc> moduleMap = Maps.newHashMap();

		for (int i = 1; i <= moduleInfo.size(); i++) {
			String[] rowArr = moduleInfo.get(i); //每一行
			//逻辑划分
			String logic = rowArr[0];
			//模块名称
			String moduleName = rowArr[1];
			//模块code
			String moduleCode = rowArr[2];
			//功能code
			String functionCode = rowArr[4];
			//视图类型
			String viewType = rowArr[5];

			ModuleDesc md = moduleMap.get(moduleCode);
			if (Util.isEmpty(md)) {
				md = new ModuleDesc();
				moduleMap.put(moduleCode, md);
			} else {
				if (!LoadConfigWeb.defaultMethods.contains(functionCode)) {
					ActionDesc ad = new ActionDesc();
					ad.setActionName(functionCode);
					ad.setViewType(viewType);
					md.getActionList().add(ad);
				}
				continue;
			}

			double dl = Double.valueOf(logic);
			int intL = (int) dl;
			LogicEnum le = EnumUtil.get(LogicEnum.class, intL);
			String logicPath = le.value();

			String mdPkgName = basePkg + "." + logicPath + "." + moduleCode + "." + LoadConfigWeb.MODULE_PKG_NAME;
			String moduleClassName = Utils.upperFirst(moduleCode) + "Module";
			String serviceClassName = Utils.upperFirst(moduleCode) + "ViewService";
			String serviceFullClassName = Joiner.on(".").join(basePkg, logicPath, moduleCode,
					LoadConfigWeb.SERVICE_PKG_NAME, serviceClassName);

			md.setModuleName(moduleName);
			md.setModuleCode(moduleCode);
			md.setPackageName(mdPkgName.toLowerCase());
			md.setServiceClassName(serviceClassName);
			String atUrl = baseUri + logicPath + "/" + moduleCode;
			md.setAtUrl(atUrl);
			md.setModuleClassName(moduleClassName);
			md.setPackageName(mdPkgName);

			md.setServiceFullClassName(serviceFullClassName);
			md.setServiceInstanceName(Utils.lowerFirst(serviceClassName));
			md.setViewType(viewType);

			//默认实体
			String entityClassName = rowArr[6];
			String entityPkgName = Joiner.on(".").join(basePkg, LoadConfigWeb.ENTITY_PKG_NAME);
			String fullEntityClassName = Joiner.on(".").join(entityPkgName, entityClassName);
			md.setEntityClassName(entityClassName);
			md.setFullEntityClassName(fullEntityClassName);
		}

		return moduleMap;
	}
}
