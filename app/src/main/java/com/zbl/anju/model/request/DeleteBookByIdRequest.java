package com.zbl.anju.model.request;

/**
 * delete book by id
 */
public class DeleteBookByIdRequest {
	private long bookId;

	public DeleteBookByIdRequest(long bookId) {
		this.bookId = bookId;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}
}
