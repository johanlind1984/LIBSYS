package com.newtongroup.library.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="visitors")
@PrimaryKeyJoinColumn(name="person_id")
public class Visitor extends Person{

    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LibraryCard> libraryCards;

    @Column(name="is_active")
    private boolean isActive;

    public Visitor() {
    }

    public List<LibraryCard> getLibraryCards() {
        return libraryCards;
    }

    public void setLibraryCards(List<LibraryCard> libraryCards) {
        this.libraryCards = libraryCards;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
