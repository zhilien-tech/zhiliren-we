/**
 * TplPathConfig.java
 * com.we.generator.config
 */

package com.we.generator.config;

/**
 * 模板配置路径
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月2日 	 
 */
public class TplPathConfig {

	private static String templatePackage = PropProxyConfig.templatePackage;

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
	public static String webSocketXmlTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/xml/spring-websocket.vm";

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

	//webSocket
	public static String webSocketDemoPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage
			+ "/view/webSocketDemo.vm";
	public static String demoWSHandlerTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/DemoWSHandler.vm";
	public static String handshakeInterceptorTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage
			+ "/HandshakeInterceptor.vm";
	public static String webSocketConfigTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/WebSocketConfig.vm";

}
