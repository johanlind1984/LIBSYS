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
    private List<CurrentLoan> currentLoanList;

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

    public List<CurrentLoan> getCurrentLoanList() {
        return currentLoanList;
    }

    public void setCurrentLoanList(List<CurrentLoan> currentLoanList) {
        this.currentLoanList = currentLoanList;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
}
