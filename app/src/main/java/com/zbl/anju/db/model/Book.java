package com.zbl.anju.db.model;

import android.support.annotation.NonNull;

import org.litepal.crud.DataSupport;

/**
 * Book实体
 * Created by James on 2017/6/17.
 */

public class Book extends DataSupport implements Comparable<Book> {
	private Long bookId;
	private String name;
	private Integer number;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Book(String name, Integer number) {
		this.name = name;
		this.number = number;
	}

	public Book(Long bookId, String name, Integer number) {
		this.bookId = bookId;
		this.name = name;
		this.number = number;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null) {
			Book book = (Book) o;
			return (getBookId() != null && getBookId().equals(book.getBookId()));
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(@NonNull Book book) {
		return this.getName().compareTo(book.getName());
	}
}
