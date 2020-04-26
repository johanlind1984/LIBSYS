package com.newtongroup.library.Entity;

import javax.persistence.*;

@Entity
@Table(name = "removed_books")
public class RemovedBook extends Book {

    @Column(name= "cause")
    private String cause;

    public RemovedBook() {
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
