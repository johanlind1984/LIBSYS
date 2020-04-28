package com.newtongroup.library.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "removed_seminaries")
public class RemovedSeminary extends Seminary {

    @Column(name= "cause")
    private String cause;

    public RemovedSeminary() {
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
