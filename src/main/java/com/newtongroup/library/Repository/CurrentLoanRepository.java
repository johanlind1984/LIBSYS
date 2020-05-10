package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.CurrentLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentLoanRepository extends JpaRepository<CurrentLoan, Integer> {

    CurrentLoan findByBookAndIsBookReturned(Book book, boolean returned);

}
