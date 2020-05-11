package com.newtongroup.library.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "librarycard")
public class LibraryCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "librarycardnumber")
    private Long libraryCardNumber;

    @Column(name = "isactive")
    private boolean isActive;

    @OneToMany(mappedBy = "libraryCard")
    private List<BookLoan> bookLoans;

    @OneToMany(mappedBy = "libraryCard")
    private List<EbookLoan> ebookLoans;

    @ManyToOne()
    @JoinColumn(name="visitor_id")
    private Visitor visitor;



    public LibraryCard() {
    }

    public Long getLibraryCardNumber() {
        return libraryCardNumber;
    }

    public void setLibraryCardNumber(Long libraryCardNumber) {
        this.libraryCardNumber = libraryCardNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<BookLoan> getBookLoans() {
        return bookLoans;
    }

    public void setBookLoans(List<BookLoan> bookLoans) {
        this.bookLoans = bookLoans;
    }

    public List<EbookLoan> getEbookLoans() {
        return ebookLoans;
    }

    public void setEbookLoans(List<EbookLoan> ebookLoans) {
        this.ebookLoans = ebookLoans;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
}
