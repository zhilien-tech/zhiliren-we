package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 消息类型
 * <p>
 * 
 * @author   彭辉
 * @Date	 2016年12月07日
 */
public enum MessageTypeEnum implements IEnum {

	CLOSEMSG(0, "关闭"), 
	NOTICEMSG(1, "系统通知消息"), 
	PROCESSMSG(2, "客户管理消息"), 
	CUSTOMMSG(3, "自定义型消息"), 
	SEARCHMSG(4, "查询"), 
	BOOKMSG(5, "预定"),
	DRAWBILLMSG(6, "出票"), 
	MAKEOUTBILLMSG(7, "开票"),
	FIRBOOKMSG(8, "一订"),
	SECBOOKMSG(9, "二订"), 
	THRBOOKMSG(10, "三订"), 
	ALLBOOKMSG(11, "全款"), 
	LASTBOOKMSG(12, "尾款"); 
	

	private int key;
	private String value;

	private MessageTypeEnum(final int key, final String value) {
		this.value = value;
		this.key = key;
	}

	@Override
	public String key() {
		return String.valueOf(key);
	}

	@Override
	public String value() {
		return value;
	}

	public int intKey() {
		return key;
	}
}
