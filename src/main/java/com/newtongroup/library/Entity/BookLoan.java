package com.newtongroup.library.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "book_loans")
public class BookLoan extends AbstractLoan{

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="book_id")
    private Book book;

    @Column(name = "isbookReturned")
    private Boolean isBookReturned;


    public BookLoan() {
    }


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Boolean getBookReturned() {
        return isBookReturned;
    }

    public void setBookReturned(Boolean bookReturned) {
        isBookReturned = bookReturned;
    }
}
