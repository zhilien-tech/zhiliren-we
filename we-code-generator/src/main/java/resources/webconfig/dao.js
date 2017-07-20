/*
 * 本配置文件声明了整个应用的数据库连接部分。
 */
var ioc = {
	dbDao : {
		type : "com.uxuexi.core.db.dao.impl.DbDao",
		args : [ {
			refer : "nutDao"
		}]
	}
};