/**
 * Generator.java
 * com.we.generator
 * Copyright (c) 2016, 北京科技有限公司版权所有.
 */

package com.we.generator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.velocity.VelocityContext;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.json.JsonLoader;
import org.nutz.lang.Mirror;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.we.generator.config.LoadConfigWeb;
import com.we.generator.core.ActionDesc;
import com.we.generator.core.ModuleDesc;
import com.we.generator.core.PageFieldDesc;
import com.we.generator.core.ServiceDesc;
import com.we.generator.core.VelocityHandler;
import com.we.generator.core.enums.LogicEnum;
import com.we.generator.load.EntityDescriptor;
import com.we.generator.load.EntityLoader;
import com.we.generator.util.CopyFile;
import com.we.generator.util.ExcelReader;
import com.we.generator.util.MakeFile;
import com.we.generator.util.Utils;

/**
 * 
 * 代码生成器入口
 * 
 * @author   朱晓川
 * @Date	 2016年9月3日 	 
 */
public class Generator {

	private static final Log log = Logs.get();

	public void generateEntity() throws Exception {
		Ioc ioc = new NutIoc(new JsonLoader(LoadConfigWeb.IOC_DBCFG_PATH));
		PropertiesProxy propConfig = ioc.get(PropertiesProxy.class, "propConfig");
		boolean useLombok = false;//是否使用lombok注解
		boolean forceCover = false; //是否覆盖已经存在的文件 
		useLombok = Boolean.valueOf(propConfig.get("use_lombok"));
		forceCover = Boolean.valueOf(propConfig.get("force_cover"));
		String templatePackage = propConfig.get("template_package");

		String template = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/entity.vm";
		String formTemplate = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/form.vm";
		String addFormTemplate = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/addForm.vm";
		String updateFormTemplate = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/updateForm.vm";
		if (useLombok) {
			template = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/entity4lombok.vm";
			formTemplate = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/form4lombok.vm";
			addFormTemplate = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/addForm4lombok.vm";
			updateFormTemplate = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/updateForm4lombok.vm";
		}

		Pattern includePattern = Pattern.compile(".*");
		String entityPkgName = LoadConfigWeb.ENTITY_PKG_NAME; //entity所在的包名 
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;

		String baseUri = "/";
		String basePkg = propConfig.get("base_package");
		//web项目的输出目录
		String webOut = webOutput + "/" + basePkg.replace(".", "-") + "/" + javaOutput;
		String pkgName = basePkg + "." + entityPkgName;//实体包
		String formPkgName = basePkg + "." + LoadConfigWeb.FORM_PKG_NAME;//form包

		EntityLoader loader = ioc.get(EntityLoader.class, "loader");
		Map<String, EntityDescriptor> entityMapping = loader.load(ioc, basePkg, baseUri, entityPkgName);

		List<String> outList = Lists.newArrayList();
		outList.add(javaOutput);
		outList.add(webOut);

		for (Map.Entry<String, EntityDescriptor> entry : entityMapping.entrySet()) {
			String tableName = entry.getKey();
			if (includePattern != null) {
				if (!includePattern.matcher(tableName).find()) {
					log.info("skip " + tableName);
					continue;
				}
			}

			EntityDescriptor entityDesc = entry.getValue();
			VelocityHandler handler = new VelocityHandler();

			String packagePath = Utils.getPath4Pkg(pkgName);
			String entityClassName = entityDesc.getEntityClassName();
			String addFormClassName = entityDesc.getAddFormName();
			String updateFormClassName = entityDesc.getUpdateFormName();

			VelocityContext context = getVContext();
			context.put("table", entityDesc);
			context.put("packageName", pkgName);

			for (String out : outList) {
				//entity
				File file = new File(out, packagePath + "/" + entityClassName + ".java");
				log.info("generate " + file.getName() + " for table :" + tableName);
				handler.writeToFile(context, template, file, forceCover);
				//form
				String formPkgPath = Utils.getPath4Pkg(formPkgName);
				String formClassName = entityDesc.getEntityClassName().split("Entity")[0] + "Form"; //entity所对应的form类名
				File formFile = new File(out, formPkgPath + "/" + formClassName + ".java");
				File addFormFile = new File(out, formPkgPath + "/" + addFormClassName + ".java");
				File updateFormFile = new File(out, formPkgPath + "/" + updateFormClassName + ".java");
				VelocityContext formContext = getVContext();
				formContext.put("form", entityDesc);
				formContext.put("packageName", formPkgName);
				handler.writeToFile(formContext, formTemplate, formFile, forceCover);
				handler.writeToFile(formContext, addFormTemplate, addFormFile, forceCover);
				handler.writeToFile(formContext, updateFormTemplate, updateFormFile, forceCover);
			}

		}
		log.info("done!");

	}

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

		Map<Integer, String[]> map = loadExcel(ins);
		for (int i = 1; i <= map.size(); i++) {
			String[] rowArr = map.get(i); //每一行  
			genService(forceCover, basePkg, serviceTpl, rowArr);
		}

		genModuleCode(forceCover, basePkg, moduleTpl, map, propConfig);
		log.info("done!");
	}

	private void genModuleCode(boolean force, String basePkg, String moduleTpl, Map<Integer, String[]> moduleInfo,
			PropertiesProxy propConfig) throws IOException, ClassNotFoundException {

		//创建web项目的目录结构
		makeWebFiles(basePkg);

		String webOutput = LoadConfigWeb.WEB_OUTPUT;

		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
		javaOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + javaOutput;

		//获取Module集合
		Map<String, ModuleDesc> moduleMap = getModuleMap(moduleInfo, basePkg);

		VelocityHandler writer = new VelocityHandler();
		List<VelocityContext> vcList = Lists.newArrayList();
		Set<String> modules = moduleMap.keySet();
		for (String mkey : modules) {
			ModuleDesc md = moduleMap.get(mkey);

			VelocityContext context = getVContext(md);

			//module
			String moduleFilePath = Utils.getPath4Pkg(md.getPackageName());
			File file = new File(javaOutput, moduleFilePath + "/" + md.getModuleClassName() + ".java");
			writer.writeToFile(context, moduleTpl, file, force);

			//jsp
			genJsp(force, writer, md, propConfig);

			//js
			genJS(force, writer, md, propConfig);

			vcList.add(context);
		}

		//pom
		genPomXml(force, writer, propConfig);

		//web.xml
		genWebXml(force, writer, propConfig);

		//MainModule
		genMainSetup(force, writer, propConfig);

		//public页面
		genPublicPage(force, writer, propConfig, vcList);
	}

	private void genJsp(boolean force, VelocityHandler handler, ModuleDesc md, PropertiesProxy propConfig)
			throws ClassNotFoundException, IOException {

		String pageFilePath = md.getAtUrl();

		String jspOutPut = LoadConfigWeb.JSP_OUTPUT;
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String basePkg = propConfig.get("base_package");
		String templatePackage = propConfig.get("template_package");
		jspOutPut = webOutput + "/" + basePkg.replace(".", "-") + "/" + jspOutPut;

		VelocityContext jspCtx = getVContext(md);

		String listTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/list.vm";
		File listPage = new File(jspOutPut, pageFilePath + "/" + "list.jsp");
		handler.writeToFile(jspCtx, listTpl, listPage, force);

		String updateTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/update.vm";
		File updatePage = new File(jspOutPut, pageFilePath + "/" + "update.jsp");
		handler.writeToFile(jspCtx, updateTpl, updatePage, force);

		String addTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/add.vm";
		File addPage = new File(jspOutPut, pageFilePath + "/" + "add.jsp");
		handler.writeToFile(jspCtx, addTpl, addPage, force);

		for (ActionDesc ad : md.getActionList()) {
			File commonPage = new File(jspOutPut, pageFilePath + "/" + ad.getActionName() + ".jsp");
			String commonTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common.vm";
			handler.writeToFile(jspCtx, commonTpl, commonPage, force);
		}

	}

	private void genJS(boolean force, VelocityHandler handler, ModuleDesc md, PropertiesProxy propConfig)
			throws ClassNotFoundException, IOException {

		String templatePackage = propConfig.get("template_package");

		String jsOutPut = LoadConfigWeb.JS_OUTPUT;
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String basePkg = propConfig.get("base_package");
		jsOutPut = webOutput + "/" + basePkg.replace(".", "-") + "/" + jsOutPut;
		String pageFilePath = md.getAtUrl();

		VelocityContext jspCtx = getVContext(md);

		String listJsTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/js/listJS.vm";
		File listJS = new File(jsOutPut, pageFilePath + "/" + "listTable.js");
		handler.writeToFile(jspCtx, listJsTpl, listJS, force);

		//拷贝外部引入文件
		copyFiles(webOutput, basePkg);

	}

	private void genService(boolean force, String basePkg, String serviceTpl, String[] rowArr) throws IOException {

		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		javaOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + javaOutput;

		//逻辑划分
		String logic = rowArr[0];

		//模块code
		String moduleCode = rowArr[2];

		//默认实体
		String entityClassName = rowArr[6];
		String entityPkgName = Joiner.on(".").join(basePkg, LoadConfigWeb.ENTITY_PKG_NAME);
		String fullEntityClassName = Joiner.on(".").join(entityPkgName, entityClassName);
		//form
		String formClassName = entityClassName.split("Entity")[0] + "Form";
		String formPkgName = Joiner.on(".").join(basePkg, LoadConfigWeb.FORM_PKG_NAME);
		String fullFormClassName = Joiner.on(".").join(formPkgName, formClassName);

		double dl = Double.valueOf(logic);
		int intL = (int) dl;
		LogicEnum le = EnumUtil.get(LogicEnum.class, intL);
		String logicPkg = le.value();

		String sdPkgName = basePkg + "." + logicPkg + "." + moduleCode + "." + LoadConfigWeb.SERVICE_PKG_NAME;
		String serviceClassName = Utils.upperFirst(moduleCode) + "ViewService";

		ServiceDesc sd = new ServiceDesc();
		sd.setPackageName(sdPkgName.toLowerCase());
		sd.setServiceClassName(serviceClassName);
		sd.setEntityClassName(entityClassName);
		sd.setFullEntityClassName(fullEntityClassName);
		sd.setFullFormClassName(fullFormClassName);

		VelocityContext context = getVContext();
		context.put("service", sd);
		context.put("formName", formClassName);

		//service
		String serviceFilePath = Utils.getPath4Pkg(sdPkgName);
		File file = new File(javaOutput, serviceFilePath + "/" + serviceClassName + ".java");

		VelocityHandler generator = new VelocityHandler();
		generator.writeToFile(context, serviceTpl, file, force);
	}

	private void genPublicPage(boolean force, VelocityHandler handler, PropertiesProxy propConfig,
			List<VelocityContext> vcLists) throws IOException {

		VelocityContext publicCtx = getVContext();
		publicCtx.put("vcLists", vcLists);

		String jspOutPut = LoadConfigWeb.JSP_OUTPUT;
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String basePkg = propConfig.get("base_package");
		String templatePackage = propConfig.get("template_package");
		jspOutPut = webOutput + "/" + basePkg.replace(".", "-") + "/" + jspOutPut;

		String pubilcOutput = LoadConfigWeb.PUBLIC_PAGE_OUTPUT;
		pubilcOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + pubilcOutput;

		//public页面
		String headerPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/public/header.vm";
		File headerFile = new File(pubilcOutput, "/public/" + "header.jsp");
		handler.writeToFile(publicCtx, headerPageTpl, headerFile, force);

		//左侧菜单栏
		String asidePageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/public/aside.vm";
		File asideFile = new File(pubilcOutput, "/public/" + "aside.jsp");
		handler.writeToFile(publicCtx, asidePageTpl, asideFile, force);

		//页脚
		String footerPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/public/footer.vm";
		File footerFile = new File(pubilcOutput, "/public/" + "footer.jsp");
		handler.writeToFile(publicCtx, footerPageTpl, footerFile, force);

		//main页面
		String mainPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/main.vm";
		File mainFile = new File(pubilcOutput, "/" + "main.jsp");
		handler.writeToFile(publicCtx, mainPageTpl, mainFile, force);

		//login页面
		String loginPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/login.vm";
		File loginFile = new File(jspOutPut, "/" + "login.jsp");
		handler.writeToFile(publicCtx, loginPageTpl, loginFile, force);

		//404页面
		String error404PageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/404.vm";
		File error404File = new File(jspOutPut, "/common/" + "404.jsp");
		handler.writeToFile(publicCtx, error404PageTpl, error404File, force);

		//500页面
		String error500PageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/500.vm";
		File error500File = new File(jspOutPut, "/common/" + "500.jsp");
		handler.writeToFile(publicCtx, error500PageTpl, error500File, force);

		//tld页面
		String tldPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/tld.vm";
		File tldFile = new File(jspOutPut, "/common/" + "tld.jsp");
		handler.writeToFile(publicCtx, tldPageTpl, tldFile, force);

		//we.tld标签配置文件
		String tldTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/wetld.vm";
		File tldF = new File(jspOutPut, "/common/" + "we.tld");
		handler.writeToFile(publicCtx, tldTpl, tldF, force);

	}

	private void genPomXml(boolean force, VelocityHandler handler, PropertiesProxy propConfig) throws IOException {

		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String basePkg = propConfig.get("base_package");
		String webName = basePkg.replace(".", "-");
		String pomOutput = webOutput + "/" + webName;
		String templatePackage = propConfig.get("template_package");
		String pomTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/xml/pom.vm";

		VelocityContext pomCtx = getVContext();

		File file = new File(pomOutput, "/" + "pom.xml");
		handler.writeToFile(pomCtx, pomTpl, file, force);
	}

	private void genWebXml(boolean force, VelocityHandler handler, PropertiesProxy propConfig) throws IOException {

		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String basePkg = propConfig.get("base_package");
		String templatePackage = propConfig.get("template_package");
		String Output = webOutput + "/" + basePkg.replace(".", "-") + "/" + LoadConfigWeb.JSP_OUTPUT;

		String webTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/xml/web.vm";

		VelocityContext vCtx = getVContext();

		File file = new File(Output, "/" + "web.xml");
		handler.writeToFile(vCtx, webTpl, file, force);

	}

	private void genMainSetup(boolean force, VelocityHandler handler, PropertiesProxy propConfig) throws IOException {

		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
		String basePkg = propConfig.get("base_package");
		String templatePackage = propConfig.get("template_package");
		String webName = basePkg.replace(".", "-");
		String Output = webOutput + "/" + webName + "/" + javaOutput + "/" + Utils.getPath4Pkg(basePkg);

		VelocityContext vCtx = getVContext();

		String webTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/MainModule.vm";
		File file = new File(Output, "/" + "MainModule.java");
		handler.writeToFile(vCtx, webTpl, file, force);

		String setupTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/WeSetup.vm";
		File setupFile = new File(Output, "/" + "WeSetup.java");
		handler.writeToFile(vCtx, setupTpl, setupFile, force);

	}

	//获取引擎上下文
	private VelocityContext getVContext(ModuleDesc md) throws ClassNotFoundException {

		//获取列表标题栏
		List<PageFieldDesc> fieldList = getPageFields(md);

		VelocityContext context = new VelocityContext();
		context.put("module", md);
		context.put("atUrl", md.getAtUrl());
		context.put("moudleName", md.getModuleName());
		context.put("moudleCode", md.getModuleCode());
		context.put("fieldList", fieldList);

		return context;
	}

	//获取引擎上下文
	private VelocityContext getVContext() {
		Ioc ioc = new NutIoc(new JsonLoader(LoadConfigWeb.IOC_KVCFG_PATH));
		PropertiesProxy propConfig = ioc.get(PropertiesProxy.class, "propConfig");
		String basePkg = propConfig.get("base_package");
		String company_name = propConfig.get("company_name");
		String system_name = propConfig.get("system_name");
		String pom_groupId = propConfig.get("pom_groupId");
		String pom_atrifactId = propConfig.get("pom_atrifactId");
		String pom_version = propConfig.get("pom_version");
		String webName = basePkg.replace(".", "-");

		VelocityContext context = new VelocityContext();
		context.put("basePkg", basePkg);
		context.put("webName", webName);
		context.put("company_name", company_name);
		context.put("system_name", system_name);
		context.put("groupId", pom_groupId);
		context.put("atrifactId", pom_atrifactId);
		context.put("version", pom_version);

		return context;
	}

	//获取模块信息
	private Map<String, ModuleDesc> getModuleMap(Map<Integer, String[]> moduleInfo, String basePkg) {

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

	//获取列表标题栏信息
	private static List<PageFieldDesc> getPageFields(ModuleDesc md) throws ClassNotFoundException {

		List<PageFieldDesc> fieldList = Lists.newArrayList();
		String fullEntityClassName = md.getFullEntityClassName();
		Class<?> entityClass = Class.forName(fullEntityClassName);
		Mirror<?> mirror = Mirror.me(entityClass);
		Field[] fields = mirror.getFields();

		for (Field f : fields) {
			if ("id".equals(f.getName())) {
				continue;
			}

			PageFieldDesc pd = new PageFieldDesc();
			pd.setName(f.getName());

			if (f.isAnnotationPresent(Comment.class)) {
				Comment comment = f.getDeclaredAnnotation(Comment.class);
				pd.setComment(comment.value());
			} else {
				pd.setComment("标题");
			}
			fieldList.add(pd);
		}

		return fieldList;
	}

	//创建web项目 基本目录
	private void makeWebFiles(String basePkg) {

		String webOutput = LoadConfigWeb.WEB_OUTPUT;

		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
		javaOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + javaOutput;
		MakeFile.makeFile(javaOutput);

		//java Resources
		String jResOutput = LoadConfigWeb.JAVA_RES_OUTPUT;
		jResOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + jResOutput;
		MakeFile.makeFile(jResOutput);

		//test
		String testJavaOut = LoadConfigWeb.TEST_JAVA_OUTPUT;
		testJavaOut = webOutput + "/" + basePkg.replace(".", "-") + "/" + testJavaOut;
		MakeFile.makeFile(testJavaOut);

		//test Resources
		String testResOut = LoadConfigWeb.TEST_RES_OUTPUT;
		testResOut = webOutput + "/" + basePkg.replace(".", "-") + "/" + testResOut;
		MakeFile.makeFile(testResOut);

		//target
		String targetOutput = LoadConfigWeb.TARGET_OUTPUT;
		targetOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + targetOutput;
		MakeFile.makeFile(targetOutput);
	}

	//拷贝外部文件到生成项目中
	private void copyFiles(String webOutput, String basePkg) {
		//拷贝js
		String filePath = LoadConfigWeb.REFERENCES_PATH;
		String toFilePath = webOutput + "/" + basePkg.replace(".", "-") + "/" + LoadConfigWeb.REFERENCES_OUTPUT;
		CopyFile.copyFile(filePath, toFilePath);

		//拷贝db配置信息
		String dbFilePath = LoadConfigWeb.DB_CONFIG_PATH;
		String toDBPath = webOutput + "/" + basePkg.replace(".", "-") + "/" + LoadConfigWeb.DB_CONFIG_OUTPUT;
		CopyFile.copyFile(dbFilePath, toDBPath);

		//拷贝静态样式
		String staticFilePath = LoadConfigWeb.STATIC_HTML_PATH;
		String toStaticPath = webOutput + "/" + basePkg.replace(".", "-") + "/" + LoadConfigWeb.REFERENCES_OUTPUT;
		CopyFile.copyFile(staticFilePath, toStaticPath);

		//拷贝page分页
		String pageFtlPath = LoadConfigWeb.FTL_PAGE_PATH;
		String toFtlPath = webOutput + "/" + basePkg.replace(".", "-") + "/" + LoadConfigWeb.PUBLIC_PAGE_OUTPUT;
		CopyFile.copyFile(pageFtlPath, toFtlPath);
	}

	private Map<Integer, String[]> loadExcel(InputStream ins) {
		Map<Integer, String[]> map = null;
		try {
			ExcelReader excelReader = new ExcelReader();
			map = excelReader.readExcelContent(ins);
		} catch (Exception e) {
			log.info("文件格式错误，请使用模板文件进行操作");
		}
		return map;
	}

}
