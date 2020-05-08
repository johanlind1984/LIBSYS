package com.newtongroup.library.Entity;


import java.sql.Date;
import java.util.List;

public class Loan {

    private LibraryCard libraryCard;
    private Book book;
    private Date dateLoanStart;
    private Date dateLoanEnd;

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
}
