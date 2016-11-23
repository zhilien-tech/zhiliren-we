/*package com.linyun.airline.admin.login.service.impl;
=======
package com.linyun.airline.admin.login.service.impl;
>>>>>>> refs/remotes/origin/dev

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.login.form.LoginForm;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.user.service.UserViewService;
import com.linyun.airline.common.access.AccessConfig;
import com.linyun.airline.common.access.sign.MD5;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean(name = "loginService")
public class LoginServiceImpl extends BaseService<TUserEntity> implements LoginService {

	@Inject
	private UserViewService userService;

	@Override
	public boolean login(final LoginForm form, final HttpSession session, final HttpServletRequest req) {

		String recode = (String) session.getAttribute(CommonConstants.CONFIRMCODE);
		String vCode = form.getValidateCode();
		if (Util.isEmpty(vCode) || !recode.equalsIgnoreCase(vCode)) {
			form.setErrMsg("验证码不正确");
			return false;
		}

		String passwd = MD5.sign(form.getPassword(), AccessConfig.password_secret, AccessConfig.INPUT_CHARSET);
		TUserEntity user = userService.findUser(form.getLoginName(), passwd);
		if (user == null) {
			form.setErrMsg("用户名或密码错误");
			return false;
		} else {
			//			if (CommonConstants.DATA_STATUS_VALID != user.getStatus()) {
			//				form.setErrMsg("账号未激活");
			//				return false;
			//			}
			//
			//			addLoginlog(user, req);
			//
			//			List<TFunctionEntity> allUserFunction = userService.findUserFunctions(user.getId());
			//
			//			//1级菜单
			//			List<TFunctionEntity> menus = new ArrayList<TFunctionEntity>();
			//			//根据菜单取功能的map
			//			Map<Long, List<TFunctionEntity>> functionMap = new HashMap<Long, List<TFunctionEntity>>();
			//			for (TFunctionEntity f : allUserFunction) {
			//				if (1 == f.getLevel()) {
			//					menus.add(f);
			//				}
			//
			//				if (2 == f.getLevel()) {
			//					List<TFunctionEntity> subList = functionMap.get(f.getParentId());
			//					if (null == subList) {
			//						subList = new ArrayList<TFunctionEntity>();
			//						subList.add(f);
			//						functionMap.put(f.getParentId(), subList);
			//					} else {
			//						subList.add(f);
			//					}
			//				}
			//			}
			//
			//			//排序functionMap
			//			Set<Long> keySet = functionMap.keySet();
			//			for (Long key : keySet) {
			//				List<TFunctionEntity> list = functionMap.get(key);
			//				if (!Util.isEmpty(list)) {
			//					Collections.sort(list, new Comparator<TFunctionEntity>() {
			//						@Override
			//						public int compare(TFunctionEntity o1, TFunctionEntity o2) {
			//							if (null == o1 || null == o1.getSort()) {
			//								return 0;
			//							}
			//							return o1.getSort().compareTo(o2.getSort());
			//						}
			//					});
			//				}
			//			}
			//
			//			//将用户权限保存到session中
			//			session.setAttribute(FUNCTION_MAP_KEY, functionMap); //功能
			//			session.setAttribute(MENU_KEY, menus); //菜单
			//			session.setAttribute(AUTHS_KEY, allUserFunction); //所有功能
			//			session.setAttribute(LOGINUSER, user);
			//			session.setAttribute(IS_LOGIN_KEY, true);
		}
		return true;
	}

	@Override
	public void logout(HttpSession session) {
		session.removeAttribute(AUTHS_KEY);
		session.removeAttribute(IS_LOGIN_KEY);
		session.invalidate();
	}

	@Override
	public Boolean isLogin(HttpSession session) {
		return !Util.isEmpty(session.getAttribute(IS_LOGIN_KEY));
	}

}
*/
