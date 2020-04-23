package com.newtongroup.library.Entity;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book extends AbstractBook {
	
	@Column(name="classication_system")
	private String classificationSystem;
	

	public String getClassificationSystem() {
		return classificationSystem;
	}

	public void setClassificationSystem(String classificationSystem) {
		this.classificationSystem = classificationSystem;
	}
	
	

	
	

}
