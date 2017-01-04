package com.linyun.airline.admin.operationsArea.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.base.Splitter;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.operationsArea.form.TMessageAddForm;
import com.linyun.airline.admin.operationsArea.form.TMessageUpdateForm;
import com.linyun.airline.common.admin.operationsArea.enums.MessageLevelEnum;
import com.linyun.airline.common.admin.operationsArea.enums.MessageSourceEnum;
import com.linyun.airline.common.admin.operationsArea.enums.MessageStatusEnum;
import com.linyun.airline.common.admin.operationsArea.enums.MessageTypeEnum;
import com.linyun.airline.common.admin.operationsArea.enums.MessageUserEnum;
import com.linyun.airline.entities.TCheckboxStatusEntity;
import com.linyun.airline.entities.TMessageEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.entities.TUserMsgEntity;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class OperationsAreaViewService extends BaseService<TMessageEntity> {
	private static final Log log = Logs.get();

	/**
	 * 
	 * 添加自定义事件
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param addForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addCustomEvent(TMessageAddForm addForm, HttpSession session) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();

		//消息类型
		addForm.setMsgType(MessageTypeEnum.PROCESSMSG.intKey());
		//消息状态默认为 （1：表示未删除）
		addForm.setMsgStatus(1);
		//消息优先级  MSGLEVEL1.intKey()表示等级最低
		addForm.setPriorityLevel(MessageLevelEnum.MSGLEVEL1.intKey());
		//格式化日期
		String generateTimeStr = addForm.getGenerateTimeString();
		if (!Util.isEmpty(generateTimeStr)) {
			addForm.setGenerateTime(DateUtil.string2Date(generateTimeStr, "yyyy-MM-dd hh:mm:ss"));
		}
		//添加消息数据
		TMessageEntity tMessageEntity = this.add(addForm);

		//操作关系表
		TUserMsgEntity tUserMsgEntity = new TUserMsgEntity();
		//消息ID
		tUserMsgEntity.setMsgId(Long.valueOf(tMessageEntity.getId()));
		//用户ID
		tUserMsgEntity.setUserId(id);
		//用户类型
		tUserMsgEntity.setUserType(Long.valueOf(MessageUserEnum.PERSONAL.intKey()));
		//来源方ID  TODO 
		tUserMsgEntity.setFromId(id);
		//来源方类型  自定义事件
		tUserMsgEntity.setMsgSource(Long.valueOf(MessageSourceEnum.PERSONALMSG.intKey()));
		//是否阅读  默认为未读状态
		tUserMsgEntity.setIsRead(Long.valueOf(MessageStatusEnum.UNREAD.intKey()));
		//自定义事件的时间
		tUserMsgEntity.setSendTime(addForm.getGenerateTime());
		TUserMsgEntity entity = dbDao.insert(tUserMsgEntity);

		return JsonUtil.toJson(entity);
	}

	/**
	 * 
	 * TODO(到更新自定义事件)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object toUpdateCustomEvent(Long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		//查询自定义消息
		TMessageEntity tMessage = dbDao.fetch(TMessageEntity.class, id);
		obj.put("message", tMessage);
		return obj;
	}

	/**
	 * 
	 * TODO(更新自定义事件)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param messageUpdateForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateCustom(TMessageUpdateForm messageUpdateForm) {
		TMessageEntity tMessage = dbDao.fetch(TMessageEntity.class, messageUpdateForm.getId());
		tMessage.setMsgContent(messageUpdateForm.getMsgContent());
		tMessage.setGenerateTime(DateUtil.string2Date(messageUpdateForm.getGenerateTimeString(), "yyyy-MM-dd hh:mm:ss"));
		messageUpdateForm.setMsgType(MessageTypeEnum.PROCESSMSG.intKey());
		messageUpdateForm.setPriorityLevel(MessageLevelEnum.MSGLEVEL1.intKey());
		return dbDao.update(tMessage);
	}

	public Object deleteCustom(TMessageUpdateForm messageUpdateForm) {
		TMessageEntity tMessage = dbDao.fetch(TMessageEntity.class, messageUpdateForm.getId());
		tMessage.setMsgStatus(0L);
		return dbDao.update(tMessage);
	}

	/**
	 * 
	 * 查询自定义事件
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param addForm
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getCustomEvent(HttpSession session, String start, String end) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();
		int msgstatus = 1;
		Sql sql = Sqls.create(sqlManager.get("msg_user_company"));
		if (!Util.isEmpty(id)) {
			sql.params().set("userId", id);
		}
		if (!Util.isEmpty(start)) {
			sql.params().set("start", start);
		}
		if (!Util.isEmpty(end)) {
			sql.params().set("end", end);
		}
		sql.params().set("msgStatus", msgstatus);
		Cnd cnd = Cnd.NEW();
		cnd.orderBy("m.priorityLevel", "desc");
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		List<Record> rList = dbDao.query(sql, cnd, null);
		String jsonStr = JsonUtil.toJson(rList);
		jsonStr = jsonStr.replaceAll("generatetime", "start");
		jsonStr = jsonStr.replaceAll("msgcontent", "title");

		return jsonStr;
	}

	/**
	 * 查询 任务事件
	 */
	public Object getTaskEvents(HttpSession session) {

		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();

		//DOTO 当前没有代理商任务， 所以查询自定义事件
		Sql sql = Sqls.create(sqlManager.get("msg_user_company_task"));
		if (!Util.isEmpty(id)) {
			sql.params().set("userId", id);
		}
		sql.params().set("now", DateTimeUtil.nowDateTime());
		sql.setCallback(Sqls.callback.records());
		List<Record> records = dbDao.query(sql, null, null);
		int size = records.size();
		for (Record record : records) {
			record.set("num", size);
		}

		return JsonUtil.toJson(records);
	}

	/**
	 * 自定义界面设置
	 */
	@Aop("txDb")
	public Object setCheckBox(HttpSession session, String checkboxname) {
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long userId = loginUser.getId();

		Map<String, Object> obj = new HashMap<String, Object>();
		Iterable<String> checkS = Splitter.on(",").split(checkboxname);
		TCheckboxStatusEntity checkEntity = new TCheckboxStatusEntity();

		//查询用户是否存在
		if (!Util.isEmpty(userId)) {
			checkEntity = dbDao.fetch(TCheckboxStatusEntity.class, Cnd.where("userId", "=", userId));
		}
		long task = 0;
		long maxC = 0;
		long minC = 0;
		//设置状态值
		if (!Util.isEmpty(checkS)) {
			for (String s : checkS) {
				if ("task".equals(s)) {
					task = 1;
				}
				if ("maxC".equals(s)) {
					maxC = 1;
				}
				if ("minC".equals(s)) {
					minC = 1;
				}
			}
		}
		checkEntity.setTaskShow(Long.valueOf(task)); //任务栏被勾选
		checkEntity.setMaxCShow(Long.valueOf(maxC)); //大日历被勾选
		checkEntity.setMinCShow(Long.valueOf(minC)); //小日历被勾选
		//判断id是否存在， 存在则更新
		if (!Util.isEmpty(userId)) {
			dbDao.update(checkEntity);
		} else {
			dbDao.insert(checkEntity);
		}
		obj.put("success", "保存成功");
		return obj;
	}

	/**
	 * 自定义界面获取
	 */
	public Object getCheckBox(HttpSession session) {
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long userId = loginUser.getId();

		Map<String, Object> obj = new HashMap<String, Object>();
		TCheckboxStatusEntity checkBoxEntity = dbDao.fetch(TCheckboxStatusEntity.class,
				Cnd.where("userId", "=", userId));
		obj.put("checkBox", checkBoxEntity);
		return obj;
	}

	/**
	 * 
	 * TODO(查询指定月 每天的自定义事件数)
	 * <p>
	 * TODO(以后查询 飞机票相关的事件)
	 *
	 * @param id
	 * @param timeStr   格式"2016-10","2016-12","2017-11"
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getMinCalList(HttpSession session, String timeStr) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();

		Sql sql = Sqls.create(sqlManager.get("msg_type_list"));
		Date date1 = DateUtil.string2Date(timeStr);
		Date date2 = DateUtil.addMonth(date1, 1);
		Date date3 = DateUtil.addMonth(date1, 2);
		if (!Util.isEmpty(id)) {
			sql.params().set("userId", id);
		}
		sql.params().set("MincalTimes1", date1);
		sql.params().set("MincalTimes2", date2);
		sql.params().set("MincalTimes3", date3);
		sql.setCallback(Sqls.callback.records());

		Set<String> set = new HashSet<String>();

		List<Record> rList = dbDao.query(sql, null, null);
		for (Record record : rList) {
			set.add(record.getString("gtime"));
		}
		List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
		for (String datestr : set) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gtime", datestr);
			String msgcontent = "";
			for (Record r : rList) {
				if (datestr.equals(r.getString("gtime"))) {
					msgcontent += r.getString("msgcontent") + "<br/>";
				}
			}
			map.put("msgcontent", msgcontent);
			resultlist.add(map);
		}
		return JsonUtil.toJson(resultlist);
	}

	/**
	 * 
	 * TODO(查询当天的自定义事件)
	 * <p>
	 * TODO(以后查询 飞机票相关的事件)
	 *
	 * @param id
	 * @param timeStr   格式"2016-10"
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getMinCal(HttpSession session, String timeStr) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();

		Sql sql = Sqls.create(sqlManager.get("msg_type"));
		Date date1 = DateUtil.string2Date(timeStr);
		if (!Util.isEmpty(id)) {
			sql.params().set("userId", id);
		}
		sql.params().set("MincalTimes1", date1);
		List<Record> rList = dbDao.query(sql, null, null);

		Set<String> set = new HashSet<String>();
		for (Record record : rList) {
			set.add(record.getString("gtime"));
		}
		List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
		for (String datestr : set) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gtime", datestr);
			String msgcontent = "";
			for (Record r : rList) {
				if (datestr.equals(r.getString("gtime"))) {
					msgcontent += r.getString("msgcontent") + "<br/>";
				}
			}
			map.put("msgcontent", msgcontent);
			resultlist.add(map);
		}
		return JsonUtil.toJson(resultlist);
	}
}