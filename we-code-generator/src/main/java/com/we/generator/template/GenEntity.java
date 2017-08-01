/**
 * GenEntity.java
 * com.we.generator.template
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.we.generator.template;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.velocity.VelocityContext;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.json.JsonLoader;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Lists;
import com.we.generator.config.GetVelocityContext;
import com.we.generator.config.LoadConfigWeb;
import com.we.generator.load.EntityDescriptor;
import com.we.generator.load.EntityLoader;
import com.we.generator.util.Utils;

/**
 * 根据模板，生成对应的实体类和扩展类
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GenEntity {

	private static final Log log = Logs.get();

	//实体
	public static void genEntityCode() throws Exception {
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

			VelocityContext context = GetVelocityContext.getVContext();
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
				VelocityContext formContext = GetVelocityContext.getVContext();
				formContext.put("form", entityDesc);
				formContext.put("packageName", formPkgName);
				handler.writeToFile(formContext, formTemplate, formFile, forceCover);
				handler.writeToFile(formContext, addFormTemplate, addFormFile, forceCover);
				handler.writeToFile(formContext, updateFormTemplate, updateFormFile, forceCover);
			}

		}
		log.info("done!");

	}
}
