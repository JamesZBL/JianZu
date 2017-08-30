package com.zbl.anju.db.model;

import java.io.Serializable;

/**
 *
 * Created by James on 17-8-30.
 */

public class TagHolder implements Serializable {
	private Object tag;

	public void setTag(Object tag) {
		this.tag = tag;
	}

	public Object getTag() {
		return tag;
	}

	public TagHolder(Object tag) {
		this.tag = tag;
	}
}
