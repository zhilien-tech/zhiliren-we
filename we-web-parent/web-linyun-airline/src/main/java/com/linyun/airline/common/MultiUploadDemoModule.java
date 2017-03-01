package com.linyun.airline.common;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

@IocBean
@At("/multiupload")
@Filters
public class MultiUploadDemoModule {

	private static final Log log = Logs.get();

	/**
	 * 多图上传,webuploader实际上是单图上传多次调用了后台地址
	 */
	@At
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
	@Ok("json")
	public Object demo(@Param("::form.") final Object form, @Param("file") final TempFile[] fts) {
		log.info("fts:" + fts);
		log.info("form:" + form);
		return null;
	}

}
