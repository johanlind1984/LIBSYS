package com.newtongroup.library.Entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.IndexedEmbedded;

@MappedSuperclass
public class AbstractBook extends AbstractRental {

	@Column
	@Field
	private String isbn;


	@IndexedEmbedded
	@ManyToMany(mappedBy = "bookList")
	private List<Author> authorList;


	public AbstractBook() {

	}

	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public List<Author> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<Author> authorList) {
		this.authorList = authorList;
	}
}
