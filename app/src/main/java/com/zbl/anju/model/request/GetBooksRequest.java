package com.zbl.anju.model.request;

/**
 * Created by James on 2017/6/17.
 */

public class GetBooksRequest {
	private String name;//书名

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GetBooksRequest(String name) {
		this.name = name;
	}
}
