package com.newtongroup.library.Entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="bosses")
@PrimaryKeyJoinColumn(name="boss_id")
public class Boss extends Person{
}
