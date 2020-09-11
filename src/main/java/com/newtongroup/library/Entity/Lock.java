package com.newtongroup.library.Entity;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "locks")
public class Lock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lock_id")
    private Long lockId;

    @Column(name = "cause")
    private String cause;

    @OneToMany(mappedBy = "lock")
    private List<LibraryCard> libraryCards;

    public Lock() {
    }

    public Long getLockId() {
        return lockId;
    }

    public void setLockId(Long lockId) {
        this.lockId = lockId;
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
