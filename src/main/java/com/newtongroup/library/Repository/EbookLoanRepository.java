package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.BookLoan;
import com.newtongroup.library.Entity.EBook;
import com.newtongroup.library.Entity.EbookLoan;
import com.newtongroup.library.Entity.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EbookLoanRepository extends JpaRepository<EbookLoan, Long> {
    EbookLoan findByEbookAndIsEbookReturned(EBook book, boolean returned);
    List<EbookLoan> findByLibraryCardAndIsEbookReturnedOrderByDateLoanStartAsc(LibraryCard libraryCard, boolean isBookReturned);
}
