package com.newtongroup.library.Entity;


import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "loans")
public class CurrentLoan extends AbstractLoan{

    @ManyToOne()
    @JoinColumn(name = "librarycardnumber")
    private LibraryCard libraryCard;

    public CurrentLoan() {
    }

    public LibraryCard getLibraryCard() {
        return libraryCard;
    }

    public void setLibraryCard(LibraryCard libraryCard) {
        this.libraryCard = libraryCard;
    }
}
