package com.newtongroup.library.Entity;


import javax.persistence.*;

@Entity
@Table(name = "ebook_loans")
public class EbookLoan extends AbstractLoan{

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="ebook_id")
    private EBook ebook;

    @Column(name = "isEbookReturned")
    private Boolean isEbookReturned;

    public EbookLoan() {
    }

    public EBook getEbook() {
        return ebook;
    }

    public void setEbook(EBook ebook) {
        this.ebook = ebook;
    }

    public Boolean getEbookReturned() {
        return isEbookReturned;
    }

    public void setEbookReturned(Boolean ebookReturned) {
        isEbookReturned = ebookReturned;
    }
}
