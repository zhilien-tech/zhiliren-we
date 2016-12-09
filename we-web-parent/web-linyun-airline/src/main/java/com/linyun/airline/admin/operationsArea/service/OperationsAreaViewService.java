package com.linyun.airline.admin.operationsArea.service;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linyun.airline.admin.operationsArea.form.TMessageAddForm;
import com.linyun.airline.common.admin.operationsArea.enums.MessageLevelEnum;
import com.linyun.airline.common.admin.operationsArea.enums.MessageSourceEnum;
import com.linyun.airline.common.admin.operationsArea.enums.MessageStatusEnum;
import com.linyun.airline.common.admin.operationsArea.enums.MessageTypeEnum;
import com.linyun.airline.common.admin.operationsArea.enums.MessageUserEnum;
import com.linyun.airline.entities.TMessageEntity;
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
	public Object addCustomEvent(TMessageAddForm addForm) {
		//消息类型
		addForm.setMsgType(MessageTypeEnum.PROCESSMSG.intKey());
		//消息优先级  MSGLEVEL1.intKey()表示等级最低
		addForm.setPriorityLevel(MessageLevelEnum.MSGLEVEL1.intKey());
		//格式化日期
		String generateTimeStr = addForm.getGenerateTimeString();
		if (!Util.isEmpty(generateTimeStr)) {
			addForm.setGenerateTime(DateUtil.string2Date(generateTimeStr, "yyyy-MM-dd"));
		}
		//添加消息数据
		TMessageEntity tMessageEntity = this.add(addForm);

		//操作关系表
		TUserMsgEntity tUserMsgEntity = new TUserMsgEntity();
		//消息ID
		tUserMsgEntity.setMsgId(Long.valueOf(tMessageEntity.getId()));
		//用户ID
		tUserMsgEntity.setUserId(Long.valueOf(addForm.getRecUserId()));
		//用户类型
		tUserMsgEntity.setUserType(Long.valueOf(MessageUserEnum.PERSONAL.intKey()));
		//来源方ID  TODO 
		tUserMsgEntity.setFromId(Long.valueOf(addForm.getSendUserId()));
		//来源方类型  自定义事件
		tUserMsgEntity.setMsgSource(Long.valueOf(MessageSourceEnum.PERSONALMSG.intKey()));
		//是否阅读  默认为未读状态
		tUserMsgEntity.setIsRead(Long.valueOf(MessageStatusEnum.UNREAD.intKey()));
		//自定义事件的时间
		tUserMsgEntity.setSendTime(addForm.getGenerateTime());
		dbDao.insert(tUserMsgEntity);

		return null;
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
	public Object getCustomEvent(Long id, String start, String end) {
		Sql sql = Sqls.create(sqlManager.get("msg_user_company"));
		//格式化时间戳
		//String startStr = DateTimeUtil.format(new Timestamp(Long.valueOf(start) * 1000));
		//String endStr = DateTimeUtil.format(new Timestamp(Long.valueOf(end) * 1000));
		if (!Util.isEmpty(id)) {
			sql.params().set("userId", id);
		}
		if (!Util.isEmpty(start)) {
			sql.params().set("start", start);
		}
		if (!Util.isEmpty(end)) {
			sql.params().set("end", end);
		}
		Cnd cnd = Cnd.NEW();
		cnd.orderBy("m.priorityLevel", "desc");
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> rList = (List<Record>) sql.getResult();
		String jsonStr = JsonUtil.toJson(rList);
		jsonStr = jsonStr.replaceAll("generatetime", "start");
		jsonStr = jsonStr.replaceAll("msgcontent", "title");

		return jsonStr;
	}

	/**
	 * 查询 任务事件
	 */
	public Object getTaskEvents(Long id) {
		//DOTO 当前没有代理商任务， 所以查询自定义事件
		Sql sql = Sqls.create(sqlManager.get("msg_user_company_task testDate"));
		if (!Util.isEmpty(id)) {
			sql.params().set("userId", id);
		}
		sql.params().set("now", DateTimeUtil.nowDateTime());
		Cnd cnd = Cnd.NEW();
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		List<Record> records = (List<Record>) sql.getResult();

		//存取记录
		List<Record> list = new ArrayList<Record>();
		if (records.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				Record r = records.get(i);
				String datetimeStr = r.getString("generatetime");
				/*String date = datetimeStr.substring(5, 10);
				String time = datetimeStr.substring(11, 16);*/
				list.add(records.get(i));
			}
		} else {
			for (Record record : records) {
				list.add(record);
			}
		}

		return JsonUtil.toJson(list);
	}
}