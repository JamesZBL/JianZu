package com.zbl.anju.model.response;

import java.util.List;

/**
 * Created by wangmingqiang on 16/9/11.
 * Company RongCloud
 */

public class GetBooksResponse {
	private int code;
	private List<ResultEntity> result;

	public void setCode(int code) {
		this.code = code;
	}

	public void setResult(List<ResultEntity> result) {
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public List<ResultEntity> getResult() {
		return result;
	}

	public static class ResultEntity {

		private String name;
		private Long bookId;
		private Integer number;

		public String getBookName() {
			return name;
		}

		public void setBookName(String bookName) {
			this.name = bookName;
		}

		public Long getBookId() {
			return bookId;
		}

		public void setBookId(Long bookId) {
			this.bookId = bookId;
		}

		public Integer getNumber() {
			return number;
		}

		public void setNumber(Integer number) {
			this.number = number;
		}
	}
}
