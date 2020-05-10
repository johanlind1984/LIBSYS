package com.newtongroup.library.Entity;


import javax.persistence.*;
import java.sql.Date;

@MappedSuperclass
public class AbstractLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name="book_id")
    private Book book;

    @Column(name = "datelonestart")
    private Date dateLoanStart;

    @Column(name = "dateloanend")
    private Date dateLoanEnd;

    @Column(name = "returned")
    private Date returned;

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
