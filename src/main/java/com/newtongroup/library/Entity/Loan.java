package com.newtongroup.library.Entity;


import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "librarycardnumber")
    private LibraryCard libraryCard;

    @ManyToOne()
    @JoinColumn(name="book_id")
    private Book book;

    @Column(name = "datelonestart")
    private Date dateLoanStart;

    @Column(name = "dateloanend")
    private Date dateLoanEnd;

    @Column(name = "returned")
    private Date returned;

    public Loan() {
    }

    public LibraryCard getLibraryCard() {
        return libraryCard;
    }

    public void setLibraryCard(LibraryCard libraryCard) {
        this.libraryCard = libraryCard;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDateLoanStart() {
        return dateLoanStart;
    }

    public void setDateLoanStart(Date dateLoanStart) {
        this.dateLoanStart = dateLoanStart;
    }

    public Date getDateLoanEnd() {
        return dateLoanEnd;
    }

    public void setDateLoanEnd(Date dateLoanEnd) {
        this.dateLoanEnd = dateLoanEnd;
    }

    public Date getReturned() {
        return returned;
    }

    public void setReturned(Date returned) {
        this.returned = returned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
