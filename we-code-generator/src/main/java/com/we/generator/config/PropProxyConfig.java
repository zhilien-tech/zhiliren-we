/**
 * FilePathConfig.java
 * com.we.generator.config
 */

package com.we.generator.config;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.json.JsonLoader;

import com.we.generator.util.Utils;

/**
 * 获取配置代理信息， 及配置文件的相关信息
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月2日 	 
 */
public class PropProxyConfig {

	private static Ioc ioc = new NutIoc(new JsonLoader(LoadConfigWeb.IOC_DBCFG_PATH));

	private static PropertiesProxy propConfig = ioc.get(PropertiesProxy.class, "propConfig");
	private static PropertiesProxy webPropConfig = ioc.get(PropertiesProxy.class, "webPropConfig");

	private PropProxyConfig() {

	}

	public static Ioc getIoc() {
		return ioc;
	}

	public static PropertiesProxy getPropConfig() {
		return propConfig;
	}

	public static PropertiesProxy getWebPropConfig() {
		return webPropConfig;
	}

	public static String baseUri = "/";
	public static String basePkg = propConfig.get("base_package");
	public static String basePkgRep = Utils.getbasePkgName(basePkg);
	public static String basePath4Pkg = Utils.getPath4Pkg(basePkg);
	public static String templatePackage = propConfig.get("template_package");

	public static boolean useLombok = Boolean.valueOf(propConfig.get("use_lombok"));//是否使用lombok注解
	public static boolean forceCover = Boolean.valueOf(propConfig.get("force_cover"));//是否覆盖已经存在的文件 

	public static String company_name = webPropConfig.get("company_name");
	public static String system_name = webPropConfig.get("system_name");
	public static String pom_groupId = webPropConfig.get("pom_groupId");
	public static String pom_atrifactId = webPropConfig.get("pom_atrifactId");
	public static String pom_version = webPropConfig.get("pom_version");

}
