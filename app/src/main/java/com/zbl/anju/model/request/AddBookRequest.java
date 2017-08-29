package com.zbl.anju.model.request;

/**
 * add book
 */
public class AddBookRequest {
	private String name;
	private int number;


	public AddBookRequest(String name, int number) {
		this.name = name;
		this.number = number;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
