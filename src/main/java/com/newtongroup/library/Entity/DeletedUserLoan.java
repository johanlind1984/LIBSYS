package com.newtongroup.library.Entity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "deleted_visitor_loans")
public class DeletedUserLoan extends AbstractLoan {

    @Transient
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Column(name="library_card")
    private String libraryCard;

    public DeletedUserLoan() {
    }

    public void hashLibraryCard(LibraryCard libraryCard) {
        this.libraryCard = passwordEncoder.encode("" + libraryCard.getLibraryCardNumber());
    }

    public String getLibraryCard() {
        return libraryCard;
    }

    public void setLibraryCard(String libraryCard) {
        this.libraryCard = libraryCard;
    }
}
