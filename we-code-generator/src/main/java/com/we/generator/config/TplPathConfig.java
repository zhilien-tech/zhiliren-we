/**
 * TplPathConfig.java
 * com.we.generator.config
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.we.generator.config;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.json.JsonLoader;

/**
 * 模板配置路径
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月2日 	 
 */
public class TplPathConfig {

	private static Ioc ioc = new NutIoc(new JsonLoader(LoadConfigWeb.IOC_DBCFG_PATH));
	private static PropertiesProxy propConfig = ioc.get(PropertiesProxy.class, "propConfig");
	public static String templatePackage = propConfig.get("template_package");

	public static String entityTemplate = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/entity.vm";
	public static String formTemplate = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/form.vm";
	public static String addFormTemplate = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/addForm.vm";
	public static String updateFormTemplate = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/updateForm.vm";

	public static String entityTemplateLombok = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/entity4lombok.vm";
	public static String formTemplateLombok = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/form4lombok.vm";
	public static String addFormTemplateLombok = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/addForm4lombok.vm";
	public static String updateFormTemplateLombok = LoadConfigWeb.TEMPLATE_PATH + templatePackage
			+ "/updateForm4lombok.vm";

	public static String listTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/list.vm";
	public static String updateTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/update.vm";
	public static String addTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/add.vm";
	public static String commonTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common.vm";

	public static String listJsTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/js/listJS.vm";

	public static String serviceTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/service.vm";
	public static String moduleTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/module.vm";

	public static String webMainTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/MainModule.vm";
	public static String weSetupTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/WeSetup.vm";

	public static String pomTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/xml/pom.vm";
	public static String webXmlTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/xml/web.vm";

	public static String headerPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/public/header.vm";
	public static String asidePageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/public/aside.vm";
	public static String footerPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/public/footer.vm";

	public static String mainPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/main.vm";
	public static String loginPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/login.vm";

	public static String error404PageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/404.vm";
	public static String error500PageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/500.vm";

	public static String tldPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/tld.vm";
	//we.tld标签配置文件
	public static String tldTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/wetld.vm";
}
