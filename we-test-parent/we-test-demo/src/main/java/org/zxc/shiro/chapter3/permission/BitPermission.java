package org.zxc.shiro.chapter3.permission;

import org.apache.shiro.authz.Permission;

import com.alibaba.druid.util.StringUtils;

/**
 *  规则
 *    +资源字符串+权限位+实例ID
 *
 *  以+开头 中间通过+分割
 *
 *  权限：
 *     0 表示所有权限
 *     1 新增 0001
 *     2 修改 0010
 *     4 删除 0100
 *     8 查看 1000
 *
 *  如 +user+10 表示对资源user拥有修改/查看权限
 *
 *  不考虑一些异常情况
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-26
 * <p>Version: 1.0
 */
public class BitPermission implements Permission {

	private String resourceIdentify;//资源标志
	private int permissionBit; //权限位
	private String instanceId; //实例id

	/**
	 * 创建 BitPermission对象.
	 * 将权限字符串进行拆分，赋值到对象属性，以便匹配的时候使用
	 */
	public BitPermission(String permissionString) {
		String[] array = permissionString.split("\\+");

		if (array.length > 1) {
			resourceIdentify = array[1];
		}

		if (StringUtils.isEmpty(resourceIdentify)) {
			resourceIdentify = "*";
		}

		if (array.length > 2) {
			permissionBit = Integer.valueOf(array[2]);
		}

		if (array.length > 3) {
			instanceId = array[3];
		}

		if (StringUtils.isEmpty(instanceId)) {
			instanceId = "*";
		}

	}

	/**
	 * 自定义授权是否匹配的逻辑
	 * @param Permission p  当前需要校验的权限，可理解为用户正在使用的权限
	 */
	@Override
	public boolean implies(Permission p) {
		if (!(p instanceof BitPermission)) {
			return false;
		}
		BitPermission other = (BitPermission) p;

		if (!("*".equals(this.resourceIdentify) || this.resourceIdentify.equals(other.resourceIdentify))) {
			return false;
		}

		if (!(this.permissionBit == 0 || (this.permissionBit & other.permissionBit) != 0)) {
			return false;
		}

		if (!("*".equals(this.instanceId) || this.instanceId.equals(other.instanceId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "BitPermission{" + "resourceIdentify='" + resourceIdentify + '\'' + ", permissionBit=" + permissionBit
				+ ", instanceId='" + instanceId + '\'' + '}';
	}
}
