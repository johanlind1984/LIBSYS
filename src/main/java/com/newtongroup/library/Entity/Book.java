package com.newtongroup.library.Entity;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.List;

@Entity
@Indexed
@Table(name = "books")
public class Book extends AbstractBook {

	@ManyToMany()
	@JoinTable(
			name="book_author",
			joinColumns = {@JoinColumn(name="idbook_author_book_id")},
			inverseJoinColumns = {@JoinColumn(name="idbook_author_author_id")})
	private List<Author> authorList;

	@ManyToOne()
	@JoinColumn(name = "placement_id")
	private Placement placement;

	public Placement getPlacement() {
		return placement;
	}

	public void setPlacement(Placement placement) {
		this.placement = placement;
	}


	public List<Author> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<Author> authorList) {
		this.authorList = authorList;
	}

}
