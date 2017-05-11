package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_back_ticket_file")
public class TBackTicketFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("文件名")
	private String filename;

	@Column
	@Comment("文件路径")
	private String fileurl;

	@Column
	@Comment("退票ID")
	private Integer backticketid;

	@Column
	@Comment("操作人")
	private Integer opid;

	@Column
	@Comment("操作时间")
	private Date optime;

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
		TBackTicketFileEntity other = (TBackTicketFileEntity) obj;
		if (backticketid == null) {
			if (other.backticketid != null)
				return false;
		} else if (!backticketid.equals(other.backticketid))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (fileurl == null) {
			if (other.fileurl != null)
				return false;
		} else if (!fileurl.equals(other.fileurl))
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
		result = prime * result + ((backticketid == null) ? 0 : backticketid.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((fileurl == null) ? 0 : fileurl.hashCode());
		return result;
	}

}