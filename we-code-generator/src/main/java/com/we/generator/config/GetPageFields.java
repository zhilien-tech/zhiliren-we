/**
 * GetPageFields.java
 * com.we.generator.config
 */

package com.we.generator.config;

import java.lang.reflect.Field;
import java.util.List;

import org.nutz.dao.entity.annotation.Comment;
import org.nutz.lang.Mirror;

import com.google.common.collect.Lists;
import com.we.generator.fileDesc.web.ModuleDesc;
import com.we.generator.fileDesc.web.PageFieldDesc;

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
			String name = f.getName();
			pd.setName(name);

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
