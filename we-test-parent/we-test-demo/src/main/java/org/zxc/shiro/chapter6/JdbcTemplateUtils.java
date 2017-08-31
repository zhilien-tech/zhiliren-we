package org.zxc.shiro.chapter6;

import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 获取Spring JdbcTemplate实例用于操作数据库
 */
public class JdbcTemplateUtils {

	private static JdbcTemplate jdbcTemplate;

	public static JdbcTemplate jdbcTemplate() {
		if (jdbcTemplate == null) {
			jdbcTemplate = createJdbcTemplate();
		}
		return jdbcTemplate;
	}

	private static JdbcTemplate createJdbcTemplate() {

		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://182.92.153.33/shiro?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true");
		ds.setUsername("root");
		ds.setPassword("zxvf123mysql");

		return new JdbcTemplate(ds);
	}

}
