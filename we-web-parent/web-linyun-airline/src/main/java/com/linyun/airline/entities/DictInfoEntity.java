package com.linyun.airline.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("dict_info")
public class DictInfoEntity implements Serializable, Comparable<DictInfoEntity> {
	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键")
	private long id;

	@Column
	@Comment("字典类别编码")
	private String typeCode;

	@Column
	@Comment("字典代码")
	private String dictCode;

	@Column
	@Comment("字典信息")
	private String dictName;

	@Column
	@Comment("描述")
	private String description;

	@Column
	@Comment("字典信息状态,0-冻结，1-启用，2--删除")
	private int status;

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DictInfoEntity other = (DictInfoEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/**使用次数*/
	private int count;

	@Override
	public int compareTo(DictInfoEntity o) {
		if (this.count > o.count) {
			return 1;
		} else if (this.count < o.count) {
			return -1;
		} else {
			return 0;
		}
	}

}