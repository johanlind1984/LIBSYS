package com.newtongroup.library.Entity;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import java.util.List;

@MappedSuperclass
public class AbstractBook extends AbstractRental {
	
	@Column
	private String isbn;

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
