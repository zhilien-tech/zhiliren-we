package com.linyun.airline.admin.user.module;

import java.util.Map;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.user.service.UserViewService;
import com.linyun.airline.common.enums.UserStatusEnum;
import com.linyun.airline.common.enums.UserTypeEnum;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TUserAddForm;
import com.linyun.airline.forms.TUserModForm;
import com.linyun.airline.forms.TUserQueryForm;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

/**
 * 
* @ClassName: UserModule
* @author 崔建斌 
* @date 2016年11月18日 上午9:47:20
 */
@IocBean
@At("/admin/user")
@Filters({//@By(type = AuthFilter.class)
})
public class UserModule {

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	@Inject
	private UserViewService userViewService;

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final TUserQueryForm queryForm, @Param("..") final Pager pager) {
		return FormUtil.query(dbDao, TUserEntity.class, queryForm, pager);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		Map<String, Object> obj = MapUtil.map();
		obj.put("userTypeEnum", EnumUtil.enum2(UserTypeEnum.class));
		obj.put("userStatusEnum", EnumUtil.enum2(UserStatusEnum.class));
		return obj;
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") final TUserAddForm addForm) {
		addForm.setCreateTime(DateTimeUtil.now());
		addForm.setUpdateTime(DateTimeUtil.now());
		FormUtil.add(dbDao, addForm, TUserEntity.class);
		return JsonResult.success("添加成功!");
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long userId) {
		return userViewService.findUser(userId);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("::user.") final TUserModForm modForm) {
		userViewService.update(modForm);
		return JsonResult.success("修改成功!", "user.list", true);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		FormUtil.delete(dbDao, TUserEntity.class, id);
		return JsonResult.success("删除成功!");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final long[] ids) {
		FormUtil.delete(dbDao, TUserEntity.class, ids);
		return JsonResult.success("删除成功!");
	}

}