/**
 * Generator.java
 * com.we.generator
 * Copyright (c) 2016, 北京科技有限公司版权所有.
 */

package com.we.generator.generator;

import java.io.InputStream;
import java.util.Map;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.json.JsonLoader;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.we.generator.config.LoadConfigWeb;
import com.we.generator.load.ExcelLoader;
import com.we.generator.template.GenEntity;
import com.we.generator.template.GenModule;
import com.we.generator.template.GenService;

/**
 * 
 * 代码生成器入口
 * 
 * @author   朱晓川
 * @Date	 2016年9月3日 	 
 */
public class Generator {

	private static final Log log = Logs.get();

	//生成实体代码
	public void generateEntity() {
		try {
			GenEntity.genEntityCode();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	//生成Module等代码
	public void generatorModule() throws Exception {
		Ioc ioc = new NutIoc(new JsonLoader(LoadConfigWeb.IOC_KVCFG_PATH));
		PropertiesProxy propConfig = ioc.get(PropertiesProxy.class, "propConfig");
		String basePkg = propConfig.get("base_package");
		boolean forceCover = false; //是否覆盖已经存在的文件 
		forceCover = Boolean.valueOf(propConfig.get("force_cover"));
		String templatePackage = propConfig.get("template_package");
		String moduleTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/module.vm";
		String serviceTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/service.vm";

		//读取excel功能模块信息
		InputStream ins = ClassLoader.getSystemResourceAsStream("code-generator/module.xlsx");

		Map<Integer, String[]> map = ExcelLoader.loadExcel(ins);
		for (int i = 1; i <= map.size(); i++) {
			String[] rowArr = map.get(i); //每一行  
			GenService.genServiceCode(forceCover, basePkg, serviceTpl, rowArr);
		}

		GenModule.genModuleCode(forceCover, basePkg, moduleTpl, map, propConfig);
		log.info("done!");
	}

}
