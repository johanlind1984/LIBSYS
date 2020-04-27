package com.newtongroup.library.Entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book extends AbstractBook {

	@ManyToOne()
	@JoinColumn(name = "book_id")
	private Placement placement;

	public Placement getPlacement() {
		return placement;
	}

	public void setPlacement(Placement placement) {
		this.placement = placement;
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
