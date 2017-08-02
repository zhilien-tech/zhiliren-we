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

import com.google.common.collect.Lists;
import com.we.generator.config.GetExcelInfo;
import com.we.generator.config.GetVelocityContext;
import com.we.generator.config.LoadConfigWeb;
import com.we.generator.config.PropProxyConfig;
import com.we.generator.config.TplPathConfig;
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

		boolean forceCover = false; //是否覆盖已经存在的文件
		forceCover = PropProxyConfig.forceCover;
		String basePkg = PropProxyConfig.basePkg;
		String moduleTpl = TplPathConfig.moduleTpl;

		//读取excel功能模块信息
		InputStream ins = GetExcelInfo.getExcelIns();
		Map<Integer, String[]> map = ExcelLoader.loadExcel(ins);

		genModuleCode(forceCover, basePkg, moduleTpl, map);
	}

	//ModuleCode
	public static void genModuleCode(boolean force, String basePkg, String moduleTpl, Map<Integer, String[]> moduleInfo)
			throws IOException, ClassNotFoundException {

		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
		javaOutput = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + javaOutput;

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
			GenJsp.genJspCode(force, writer, md);

			//js
			GenJs.genJsCode(force, writer, md);
			vcList.add(context);
		}

		//pom.xml
		GenPomXml.genXmlFile(force, writer);

		//web.xml
		GenWebXml.genXmlFile(force, writer);

		//MainModule
		GenMainSetup.genCode(force, writer);

		//public页面
		GenPublicPage.genPublicPage(force, writer, vcList);
	}
}
