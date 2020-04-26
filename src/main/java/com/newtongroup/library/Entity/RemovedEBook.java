package com.newtongroup.library.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "removed_e_books")
public class RemovedEBook extends EBook{
    @Column(name= "cause")
    private String cause;

    public RemovedEBook() {
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
