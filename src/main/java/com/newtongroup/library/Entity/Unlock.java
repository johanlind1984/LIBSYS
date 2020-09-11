package com.newtongroup.library.Entity;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "unlocks")
public class Unlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unlock_id")
    private Long unlockId;

    @Column(name = "cause")
    private String cause;

    @OneToMany(mappedBy = "unlock")
    private List<LibraryCard> libraryCards;

    public Unlock() {
    }

    public Long getUnlockId() {
        return unlockId;
    }

    public void setUnlockId(Long unlockId) {
        this.unlockId = unlockId;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public List<LibraryCard> getLibraryCards() {
        return libraryCards;
    }

    public void setLibraryCards(List<LibraryCard> libraryCards) {
        this.libraryCards = libraryCards;
    }
}
