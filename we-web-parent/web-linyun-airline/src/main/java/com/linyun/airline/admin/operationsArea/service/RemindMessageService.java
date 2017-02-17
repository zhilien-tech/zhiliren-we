package com.linyun.airline.admin.operationsArea.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.operationsArea.entities.TMessageEntity;
import com.linyun.airline.admin.operationsArea.form.TMessageAddForm;
import com.linyun.airline.common.enums.MessageIsRemindEnum;
import com.linyun.airline.common.enums.MessageStatusEnum;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.entities.TUserMsgEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class RemindMessageService extends BaseService<TMessageEntity> {
	private static final Log log = Logs.get();

	/* 
	* TODO(添加消息，设置提醒方式)
	* <p>
	*
	* @param msgContent  消息内容
	* @param msgType     消息类型
	* @param remindMsgMode   提醒方式    自然月号   每周一   自定义提醒...
	* @param session //获取当前登陆用户
	* @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	*/
	/**
	 * 
	 * (添加消息，设置提醒方式)
	 * <p>
	 *
	 * @param msgContent  	//消息内容
	 * @param msgType     	//消息类型
	 * @param msgLevel    	//消息优先级
	 * @param reminderMode 	//提醒方式    自然月号   每周一  每天   每小时   每分钟提醒...
	 * @param userType    	//接受方用户类型
	 * @param msgSourceType //来源方用户类型
	 * @param session     	//获取当前登陆用户
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addMessageEvent(String msgContent, int msgType, int msgLevel, int msgStatus, long reminderMode,
			long userType, long msgSourceType, HttpSession session) {

		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();

		/*******************************操作  消息表**************************************/
		TMessageAddForm addForm = new TMessageAddForm();
		//消息内容
		addForm.setMsgContent(msgContent);
		//消息类型
		addForm.setMsgType(msgType);
		//消息状态默认为 （1：表示未删除）
		addForm.setMsgStatus(msgStatus);
		//消息优先级  MSGLEVEL1.intKey()表示等级最低 ， 由小到大
		addForm.setPriorityLevel(msgLevel);
		//消息生成日期
		Date nowDate = DateUtil.nowDate();
		if (!Util.isEmpty(nowDate)) {
			addForm.setGenerateTime(nowDate);
		}
		//消息  
		addForm.setReminderMode(reminderMode);
		//消息是否提醒  （默认为为提醒）
		addForm.setIsRemind(Long.valueOf(MessageIsRemindEnum.YES.intKey()));
		//添加消息数据
		TMessageEntity tMessageEntity = this.add(addForm);

		/*******************************操作   用户-消息  关系表**************************************/
		TUserMsgEntity tUserMsgEntity = new TUserMsgEntity();
		//消息ID
		tUserMsgEntity.setMsgId(Long.valueOf(tMessageEntity.getId()));
		//用户ID
		tUserMsgEntity.setUserId(id);
		//用户类型
		tUserMsgEntity.setUserType(userType);
		//来源方ID  TODO 
		tUserMsgEntity.setFromId(id);
		//来源方类型  自定义事件
		tUserMsgEntity.setMsgSource(msgSourceType);
		//是否阅读  默认为未读状态
		tUserMsgEntity.setIsRead(Long.valueOf(MessageStatusEnum.UNREAD.intKey()));
		//自定义事件的时间
		tUserMsgEntity.setSendTime(addForm.getGenerateTime());
		TUserMsgEntity entity = dbDao.insert(tUserMsgEntity);

		return JsonUtil.toJson(entity);
	}

}