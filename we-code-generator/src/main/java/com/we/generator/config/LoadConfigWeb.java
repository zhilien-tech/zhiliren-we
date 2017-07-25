/**
 * LoadConfig.java
 * com.we.generator.load
 * Copyright (c) 2016, 北京聚智未来科技有限公司版权所有.
*/

package com.we.generator.config;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * web项目 配置信息类
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年07月18日 	 
 */
public class LoadConfigWeb {

	/**模板路径*/
	public static final String TEMPLATE_PATH = "code-generator/template/";

	/**db-ioc配置文件路径*/
	public static final String IOC_DBCFG_PATH = "/code-generator/ioc/db.js";
	public static final String IOC_KVCFG_PATH = "/code-generator/ioc/kv.js";

	/**公共的Entity所在的包名*/
	public static final String ENTITY_PKG_NAME = "entities";

	/**公共的form所在包*/
	public static final String FORM_PKG_NAME = "forms";

	/**Module所在的包名*/
	public static final String MODULE_PKG_NAME = "module";

	/**Service所在的包名*/
	public static final String SERVICE_PKG_NAME = "service";

	/**WEB项目的目标路径*/
	public static final String WEB_OUTPUT = "target";

	/**java文件输出路径*/
	public static final String JAVA_OUTPUT = "src/main/java";

	/**java资源输出路径*/
	public static final String JAVA_RES_OUTPUT = "src/main/resources";

	/**jsp文件输出路径*/
	public static final String JSP_OUTPUT = "src/main/webapp/WEB-INF";

	/**js文件输出路径*/
	public static final String JS_OUTPUT = "src/main/webapp";

	/**test java文件输出路径*/
	public static final String TEST_JAVA_OUTPUT = "src/test/java";

	/**test资源输出路径*/
	public static final String TEST_RES_OUTPUT = "src/test/resources";

	/**tarter 输出路径*/
	public static final String TARGET_OUTPUT = "target";

	/**外部js路径*/
	public static final String REFERENCES_PATH = "src/main/resources/references";
	/**js输出路径*/
	public static final String REFERENCES_OUTPUT = "src/main/webapp";

	/**外部db配置信息*/
	public static final String DB_CONFIG_PATH = "src/main/resources/resources";
	/**外部db配置位置*/
	public static final String DB_CONFIG_OUTPUT = "src/main/";

	/**公共页位置*/
	public static final String PUBLIC_PAGE_OUTPUT = "src/main/webapp/WEB-INF";

	/**默认生成的方法名*/
	public static List<String> defaultMethods = Lists.newArrayList("list", "add", "update", "delete", "batchDelete");

}
