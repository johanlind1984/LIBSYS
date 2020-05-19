package com.newtongroup.library.Entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="librarians")
@PrimaryKeyJoinColumn(name="person_id")
public class Librarian extends Person{
}
