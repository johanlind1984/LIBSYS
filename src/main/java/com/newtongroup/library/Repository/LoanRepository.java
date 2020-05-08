package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

}
