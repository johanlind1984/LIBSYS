package com.newtongroup.library.Entity;

import org.hibernate.search.annotations.Field;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractBook extends AbstractRental {

	@Column
	@Field
	private String isbn;


	public AbstractBook() {

	}

	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}



}
