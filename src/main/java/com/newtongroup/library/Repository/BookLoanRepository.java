package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.BookLoan;
import com.newtongroup.library.Entity.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
    BookLoan findByBookAndIsBookReturned(Book book, boolean returned);
    List<BookLoan> findByLibraryCardAndIsBookReturnedOrderByDateLoanStartAsc(LibraryCard libraryCard, boolean isBookReturned);
}
