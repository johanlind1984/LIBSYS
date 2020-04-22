package com.newtongroup.library.Entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="visitors")
@PrimaryKeyJoinColumn(name="visitor_id")
public class Visitor extends Person{

}
