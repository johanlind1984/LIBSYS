package com.newtongroup.library.Entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractBook extends AbstractRental {
	
	@Column
	private String isbn;
	@Column
	private String author;
	
	
	public AbstractBook() {
		
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	
	public String getAuthor() {
		return author;
	}
	
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	
}
