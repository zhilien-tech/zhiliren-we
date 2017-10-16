/**
 * CustomizedPropertyConfigurer.java
 * com.we.business.sms.util
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.we.business.sms.util;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.google.common.collect.Maps;

/**
 * @author   朱晓川
 * @Date	 2017年10月16日 	 
 */
public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {

	private static Map<String, String> ctxPropertiesMap = Maps.newConcurrentMap();

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	public static String getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}

	public static String setContextProperty(String name, String value) {
		return ctxPropertiesMap.put(name, value);
	}

}
