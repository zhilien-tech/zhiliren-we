/**
 * GetPageFields.java
 * com.we.generator.config
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.we.generator.config;

import java.lang.reflect.Field;
import java.util.List;

import org.nutz.dao.entity.annotation.Comment;
import org.nutz.lang.Mirror;

import com.google.common.collect.Lists;
import com.we.generator.core.ModuleDesc;
import com.we.generator.core.PageFieldDesc;

/**
 * 获取列表标题栏
 * <p>
 *
 * @author 	彭辉
 * @param  	md  模块描述
 * @Date   	2017年8月1日 	 
 */
public class GetPageFields {

	//获取列表标题栏信息
	public static List<PageFieldDesc> getPageFields(ModuleDesc md) throws ClassNotFoundException {

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
}
