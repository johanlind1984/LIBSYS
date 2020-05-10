package com.newtongroup.library.Entity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "deleted_visitor_loans")
public class RemovedUserLoan extends AbstractLoan {

    @Column(name="library_card")
    private String libraryCard;

    public RemovedUserLoan() {
    }

    public String getLibraryCard() {
        return libraryCard;
    }

    public void setLibraryCard(String libraryCard) {
        this.libraryCard = libraryCard;
    }
}
