package com.newtongroup.library.Entity;


import javax.persistence.*;
import java.sql.Date;

@MappedSuperclass
public class AbstractLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="book_id")
    private Book book;

    @Column(name = "date_lonestart")
    private Date dateLoanStart;

    @Column(name = "date_loanend")
    private Date dateLoanEnd;

    @Column(name = "date_returned")
    private Date dateReturned;

    @Column(name = "isBookReturned")
    private Boolean isBookReturned;

    public AbstractLoan() {
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

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getBookReturned() {
        return isBookReturned;
    }

    public void setBookReturned(Boolean bookReturned) {
        isBookReturned = bookReturned;
    }
}
