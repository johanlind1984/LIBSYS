package com.newtongroup.library.Entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="admins")
@PrimaryKeyJoinColumn(name="admin_id")
public class Admin extends Person{
}
