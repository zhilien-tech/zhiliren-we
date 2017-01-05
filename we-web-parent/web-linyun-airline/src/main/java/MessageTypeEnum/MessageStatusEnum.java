/**
 * MessageStatusEnum.java
 * MessageTypeEnum
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package MessageTypeEnum;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年1月4日 	 
 */
public enum MessageStatusEnum implements IEnum {

	ACTIVEMSG(1, "告知型消息"), DELETEMSG(2, "用户处理型消息");

	private int key;
	private String value;

	private MessageStatusEnum(final int key, final String value) {
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
