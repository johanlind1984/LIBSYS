package com.newtongroup.library.Entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
@Table(name = "books")
public class Book extends AbstractBook {

//	@Column(name="classication_system")
//	private String classificationSystem;


//	public String getClassificationSystem() {
//		return classificationSystem;
//	}
//
//	public void setClassificationSystem(String classificationSystem) {
//		this.classificationSystem = classificationSystem;
//	}

}
