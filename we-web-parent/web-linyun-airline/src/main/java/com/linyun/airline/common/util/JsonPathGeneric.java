/**
 * JsonPathGeneric.java
 * com.linyun.airline.common.util
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.util;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;

/**
 * JsonPath转Java对象工具类
 * <p>
 *
@SuppressWarnings("cast")
 * @author   朱晓川
 * @Date	 2016年12月20日 	 
 */
public class JsonPathGeneric {

	@SuppressWarnings("unchecked")
	public static <T> T getGenericObject(String json, String jsonPath, Class<T> clazz) {
		String jsonResult = null;
		if (jsonPath == null || "".equals(jsonPath)) {
			jsonResult = json;
		} else {
			jsonResult = JsonPath.read(json, jsonPath).toString();
		}
		if (String.class.equals(clazz)) {
			return (T) jsonResult;
		}
		return new Gson().fromJson(jsonResult, clazz);

	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getGenericList(String json, String jsonPath, Class<T> clazz) {
		String jsonResult = null;
		if (jsonPath == null || "".equals(jsonPath)) {
			jsonResult = json;
		} else {
			jsonResult = JsonPath.read(json, jsonPath).toString();
		}

		Type listtype = new TypeToken<List<T>>() {
		}.getType();
		return (List<T>) new Gson().fromJson(jsonResult, listtype);
	}

	private JsonPathGeneric() {
	}
}
