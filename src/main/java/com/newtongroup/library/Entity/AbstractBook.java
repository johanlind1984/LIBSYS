package com.newtongroup.library.Entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractBook extends AbstractRental {
	
	@Column
	private String isbn;

//	@ManyToMany
//	@JoinTable(
//			name="book_author",
//			joinColumns = {@JoinColumn(name="idbook_author_book_id")},
//			inverseJoinColumns = {@JoinColumn(name="idbook_author_author_id")})
//	private List<Author>authorList;

	public AbstractBook() {
		
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

//	public List<Author> getAuthorList() {
//		return authorList;
//	}
//
//	public void setAuthorList(List<Author> authorList) {
//		this.authorList = authorList;
//	}

}
