package com.newtongroup.library.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="visitors")
@PrimaryKeyJoinColumn(name="visitor_id")
public class Visitor extends Person{

    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL)
    private List<LibraryCard> libraryCards;

    public Visitor() {
    }

    public List<LibraryCard> getLibraryCards() {
        return libraryCards;
    }

    public void setLibraryCards(List<LibraryCard> libraryCards) {
        this.libraryCards = libraryCards;
    }

    public LibraryCard getActiveLibraryCard() {

        for (LibraryCard card : libraryCards) {
            if(card.isActive()) {
                return card;
            }
        }

        return null;
    }

}
