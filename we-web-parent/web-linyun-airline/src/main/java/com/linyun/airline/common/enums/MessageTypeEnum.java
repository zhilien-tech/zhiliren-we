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

	CLOSEMSG(0, "关闭"), NOTICEMSG(1, "系统通知消息"), PROCESSMSG(2, "客户管理消息"), CUSTOMMSG(3, "自定义型消息"), SEARCHMSG(4, "查询"), BOOKMSG(
			5, "预定"), BOOKONEMSG(6, "一订"), BOOKSECMSG(7, "二订"), FINALPAYMSG(8, "尾款"), DRAWBILLMSG(9, "出票"), MAKEOUTBILLMSG(
			10, "开票");

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
