package com.newtongroup.library.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book extends AbstractBook {



	@ManyToMany(mappedBy = "authorList")
	private List<Book> bookList;

	@ManyToOne()
	@JoinColumn(name = "placement_id")
	private Placement placement;

	public Placement getPlacement() {
		return placement;
	}

	public void setPlacement(Placement placement) {
		this.placement = placement;
	}
	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	//	@Column(name="classification_system")
//	private String classificationSystem;
//
//
//	public String getClassificationSystem() {
//		return classificationSystem;
//	}
//
//	public void setClassificationSystem(String classificationSystem) {
//		this.classificationSystem = classificationSystem;
//	}






}
