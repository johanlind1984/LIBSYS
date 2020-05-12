package com.newtongroup.library.Entity;


import javax.persistence.*;
import java.sql.Date;

@MappedSuperclass
public class AbstractLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "librarycardnumber")
    private LibraryCard libraryCard;

    @Column(name = "date_lonestart")
    private Date dateLoanStart;

    @Column(name = "date_loanend")
    private Date dateLoanEnd;

    @Column(name = "date_returned")
    private Date dateReturned;

    public AbstractLoan() {
    }


    public LibraryCard getLibraryCard() {
        return libraryCard;
    }

    public void setLibraryCard(LibraryCard libraryCard) {
        this.libraryCard = libraryCard;
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

}
