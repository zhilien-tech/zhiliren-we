/**
 * Generator.java
 * com.we.generator
 * Copyright (c) 2016, 北京科技有限公司版权所有.
 */

package com.we.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import com.we.generator.util.ExcelReader;
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

			VelocityContext context = new VelocityContext();
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
				File formFile = new File(javaOutput, formPkgPath + "/" + formClassName + ".java");
				File addFormFile = new File(javaOutput, formPkgPath + "/" + addFormClassName + ".java");
				File updateFormFile = new File(javaOutput, formPkgPath + "/" + updateFormClassName + ".java");
				VelocityContext formContext = new VelocityContext();
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

		genModuleCode(forceCover, basePkg, moduleTpl, map);
		log.info("done!");
	}

	private void genModuleCode(boolean force, String basePkg, String moduleTpl, Map<Integer, String[]> moduleInfo)
			throws IOException, ClassNotFoundException {

		String baseUri = "/";
		String webOutput = LoadConfigWeb.WEB_OUTPUT;

		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
		javaOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + javaOutput;

		//java Resources
		String jResOutput = LoadConfigWeb.JAVA_RES_OUTPUT;
		jResOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + jResOutput;
		new File(jResOutput).mkdirs();

		//test
		String testJavaOut = LoadConfigWeb.TEST_JAVA_OUTPUT;
		testJavaOut = webOutput + "/" + basePkg.replace(".", "-") + "/" + testJavaOut;
		new File(testJavaOut).mkdirs();

		//test Resources
		String testResOut = LoadConfigWeb.TEST_RES_OUTPUT;
		testResOut = webOutput + "/" + basePkg.replace(".", "-") + "/" + testResOut;
		new File(testResOut).mkdirs();

		//target
		String targetOutput = LoadConfigWeb.TARGET_OUTPUT;
		targetOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + targetOutput;
		new File(targetOutput).mkdirs();

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

		VelocityHandler writer = new VelocityHandler();
		Set<String> modules = moduleMap.keySet();
		for (String mkey : modules) {
			ModuleDesc md = moduleMap.get(mkey);

			VelocityContext context = new VelocityContext();
			context.put("module", md);

			//module
			String moduleFilePath = Utils.getPath4Pkg(md.getPackageName());
			File file = new File(javaOutput, moduleFilePath + "/" + md.getModuleClassName() + ".java");
			writer.writeToFile(context, moduleTpl, file, force);

			//jsp
			genJsp(force, writer, md);

			//js
			genJS(force, writer, md);

			//pom
			genPomXml(force, writer);

			//web.xml
			genWebXml(force, writer);

		}
	}

	private void genJsp(boolean force, VelocityHandler handler, ModuleDesc md) throws ClassNotFoundException,
			IOException {

		Ioc ioc = new NutIoc(new JsonLoader(LoadConfigWeb.IOC_KVCFG_PATH));
		PropertiesProxy propConfig = ioc.get(PropertiesProxy.class, "propConfig");
		String templatePackage = propConfig.get("template_package");

		String fullEntityClassName = md.getFullEntityClassName();
		Class<?> entityClass = Class.forName(fullEntityClassName);
		Mirror<?> mirror = Mirror.me(entityClass);
		Field[] fields = mirror.getFields();
		List<PageFieldDesc> fieldList = Lists.newArrayList();
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

		String jspOutPut = LoadConfigWeb.JSP_OUTPUT;
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String basePkg = propConfig.get("base_package");
		jspOutPut = webOutput + "/" + basePkg.replace(".", "-") + "/" + jspOutPut;

		String pageFilePath = md.getAtUrl();

		VelocityContext jspCtx = new VelocityContext();
		jspCtx.put("fieldList", fieldList);
		jspCtx.put("atUrl", md.getAtUrl());
		jspCtx.put("moudleName", md.getModuleName());
		jspCtx.put("moudleCode", md.getModuleCode());

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

		//404页面
		String error404PageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/404.vm";
		File error404File = new File(jspOutPut, "/admin/common/" + "404.jsp");
		handler.writeToFile(jspCtx, error404PageTpl, error404File, force);

		//500页面
		String error500PageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/500.vm";
		File error500File = new File(jspOutPut, "/admin/common/" + "500.jsp");
		handler.writeToFile(jspCtx, error500PageTpl, error500File, force);
	}

	private void genJS(boolean force, VelocityHandler handler, ModuleDesc md) throws ClassNotFoundException,
			IOException {

		Ioc ioc = new NutIoc(new JsonLoader(LoadConfigWeb.IOC_KVCFG_PATH));
		PropertiesProxy propConfig = ioc.get(PropertiesProxy.class, "propConfig");
		String templatePackage = propConfig.get("template_package");

		String fullEntityClassName = md.getFullEntityClassName();
		Class<?> entityClass = Class.forName(fullEntityClassName);
		Mirror<?> mirror = Mirror.me(entityClass);
		Field[] fields = mirror.getFields();
		List<PageFieldDesc> fieldList = Lists.newArrayList();
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

		String jsOutPut = LoadConfigWeb.JS_OUTPUT;
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String basePkg = propConfig.get("base_package");
		jsOutPut = webOutput + "/" + basePkg.replace(".", "-") + "/" + jsOutPut;
		String pageFilePath = md.getAtUrl();

		VelocityContext jspCtx = new VelocityContext();
		jspCtx.put("fieldList", fieldList);
		jspCtx.put("atUrl", md.getAtUrl());
		jspCtx.put("moudleName", md.getModuleName());
		jspCtx.put("moudleCode", md.getModuleCode());

		String listJsTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/js/listJS.vm";
		File listJS = new File(jsOutPut, pageFilePath + "/" + "listTable.js");
		handler.writeToFile(jspCtx, listJsTpl, listJS, force);

		//拷贝js
		String filePath = LoadConfigWeb.REFERENCES_PATH;
		String toFilePath = webOutput + "/" + basePkg.replace(".", "-") + "/" + LoadConfigWeb.REFERENCES_OUTPUT;
		copyFile(filePath, toFilePath);
	}

	private void copyFile(String filePath, String toFilePath) {
		File file = new File(filePath);
		File toFile = new File(toFilePath);
		try {
			copy(file, toFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
	}

	//拷贝文件到固定目录下
	public static void copy(File file, File toFile) throws Exception {
		byte[] b = new byte[1024];
		int a;
		FileInputStream fis;
		FileOutputStream fos;
		if (file.isDirectory()) {
			String filepath = file.getAbsolutePath();
			filepath = filepath.replaceAll("\\\\", "/");
			String toFilepath = toFile.getAbsolutePath();
			toFilepath = toFilepath.replaceAll("\\\\", "/");
			int lastIndexOf = filepath.lastIndexOf("/");
			toFilepath = toFilepath + filepath.substring(lastIndexOf, filepath.length());
			File copy = new File(toFilepath);
			//复制文件夹  
			if (!copy.exists()) {
				copy.mkdir();
			}
			//遍历文件夹  
			for (File f : file.listFiles()) {
				copy(f, copy);
			}
		} else {
			if (toFile.isDirectory()) {
				String filepath = file.getAbsolutePath();
				filepath = filepath.replaceAll("\\\\", "/");
				String toFilepath = toFile.getAbsolutePath();
				toFilepath = toFilepath.replaceAll("\\\\", "/");
				int lastIndexOf = filepath.lastIndexOf("/");
				toFilepath = toFilepath + filepath.substring(lastIndexOf, filepath.length());

				//写文件  
				File newFile = new File(toFilepath);
				fis = new FileInputStream(file);
				fos = new FileOutputStream(newFile);
				while ((a = fis.read(b)) != -1) {
					fos.write(b, 0, a);
				}
			} else {
				fis = new FileInputStream(file);
				fos = new FileOutputStream(toFile);
				while ((a = fis.read(b)) != -1) {
					fos.write(b, 0, a);
				}
			}
		}
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

		VelocityContext context = new VelocityContext();
		context.put("service", sd);
		context.put("formName", formClassName);

		//service
		String serviceFilePath = Utils.getPath4Pkg(sdPkgName);
		File file = new File(javaOutput, serviceFilePath + "/" + serviceClassName + ".java");

		VelocityHandler generator = new VelocityHandler();
		generator.writeToFile(context, serviceTpl, file, force);
	}

	private void genPomXml(boolean force, VelocityHandler handler) throws ClassNotFoundException, IOException {

		Ioc ioc = new NutIoc(new JsonLoader(LoadConfigWeb.IOC_KVCFG_PATH));
		PropertiesProxy propConfig = ioc.get(PropertiesProxy.class, "propConfig");
		String templatePackage = propConfig.get("template_package");

		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String basePkg = propConfig.get("base_package");
		String webName = basePkg.replace(".", "-");
		String pomOutput = webOutput + "/" + webName;

		String pomTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/xml/pom.vm";

		VelocityContext pomCtx = new VelocityContext();
		pomCtx.put("webName", webName);

		File file = new File(pomOutput, "/" + "pom.xml");
		handler.writeToFile(pomCtx, pomTpl, file, force);

	}

	private void genWebXml(boolean force, VelocityHandler handler) throws ClassNotFoundException, IOException {

		Ioc ioc = new NutIoc(new JsonLoader(LoadConfigWeb.IOC_KVCFG_PATH));
		PropertiesProxy propConfig = ioc.get(PropertiesProxy.class, "propConfig");
		String templatePackage = propConfig.get("template_package");

		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String basePkg = propConfig.get("base_package");
		String Output = webOutput + "/" + basePkg.replace(".", "-") + "/" + LoadConfigWeb.JSP_OUTPUT;

		String webTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/xml/web.vm";

		VelocityContext vCtx = new VelocityContext();
		vCtx.put("webName", basePkg);

		File file = new File(Output, "/" + "web.xml");
		handler.writeToFile(vCtx, webTpl, file, force);

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
