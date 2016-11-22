package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class DictRelationUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**字典id*/
	private long sourceId;

	/**关联字典id*/
	private long targetId;

}