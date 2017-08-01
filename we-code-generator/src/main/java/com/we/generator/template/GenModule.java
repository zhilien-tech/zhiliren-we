/**
 * GenModule.java
 * com.we.generator.template
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.we.generator.template;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.VelocityContext;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.json.JsonLoader;

import com.google.common.collect.Lists;
import com.we.generator.config.GetExcelInfo;
import com.we.generator.config.GetVelocityContext;
import com.we.generator.config.LoadConfigWeb;
import com.we.generator.config.SetWebDirectory;
import com.we.generator.fileDesc.web.ModuleDesc;
import com.we.generator.load.ExcelLoader;
import com.we.generator.util.Utils;

/**
 * 根据模板，生成对应的Module代码
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GenModule {

	public static void genModule() throws IOException, ClassNotFoundException {
		Ioc ioc = new NutIoc(new JsonLoader(LoadConfigWeb.IOC_KVCFG_PATH));
		PropertiesProxy propConfig = ioc.get(PropertiesProxy.class, "propConfig");

		boolean forceCover = false; //是否覆盖已经存在的文件 
		String basePkg = propConfig.get("base_package");
		forceCover = Boolean.valueOf(propConfig.get("force_cover"));
		String templatePackage = propConfig.get("template_package");
		String moduleTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/module.vm";

		//读取excel功能模块信息
		InputStream ins = GetExcelInfo.getExcelIns();
		Map<Integer, String[]> map = ExcelLoader.loadExcel(ins);

		genModuleCode(forceCover, basePkg, moduleTpl, map, propConfig);
	}

	//ModuleCode
	public static void genModuleCode(boolean force, String basePkg, String moduleTpl,
			Map<Integer, String[]> moduleInfo, PropertiesProxy propConfig) throws IOException, ClassNotFoundException {

		//创建web项目的目录结构
		SetWebDirectory.makeFiles(basePkg);

		String webOutput = LoadConfigWeb.WEB_OUTPUT;

		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
		javaOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + javaOutput;

		//获取Module集合
		Map<String, ModuleDesc> moduleMap = ExcelLoader.getModuleMap(moduleInfo, basePkg);

		VelocityHandler writer = new VelocityHandler();
		List<VelocityContext> vcList = Lists.newArrayList();
		Set<String> modules = moduleMap.keySet();
		for (String mkey : modules) {
			ModuleDesc md = moduleMap.get(mkey);

			VelocityContext context = GetVelocityContext.getVContext(md);

			//module
			String moduleFilePath = Utils.getPath4Pkg(md.getPackageName());
			File file = new File(javaOutput, moduleFilePath + "/" + md.getModuleClassName() + ".java");
			writer.writeToFile(context, moduleTpl, file, force);

			//jsp
			GenJsp.genJspCode(force, writer, md, propConfig);

			//js
			GenJs.genJsCode(force, writer, md, propConfig);

			vcList.add(context);
		}

		//pom.xml
		GenPomXml.genXmlFile(force, writer, propConfig);

		//web.xml
		GenWebXml.genXmlFile(force, writer, propConfig);

		//MainModule
		GenMainSetup.genCode(force, writer, propConfig);

		//public页面
		GenPublicPage.genPublicPage(force, writer, propConfig, vcList);
	}
}
