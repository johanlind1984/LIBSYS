package com.newtongroup.library.Entity;

import javax.persistence.*;

@Entity
@Table(name="visitors")
@PrimaryKeyJoinColumn(name="visitor_id")
public class Visitor extends Person{

    @Column(name="librarycard")
    private long libraryCard;

    public Visitor() {
    }

    public long getLibraryCard() {
        return libraryCard;
    }

    public void setLibraryCard(long libraryCard) {
        this.libraryCard = libraryCard;
    }
}
