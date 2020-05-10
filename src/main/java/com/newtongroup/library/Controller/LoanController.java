package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.BookRepository;
import com.newtongroup.library.Repository.LoanRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Repository.VisitorRepository;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.sql.Date;
import java.util.Calendar;

@Controller
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private BookRepository bookrepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @RequestMapping("/register-loan")
    public String registerLoan(@RequestParam("bookId") Integer bookId, Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        Book book = bookrepository.findById((bookId)).orElse(null);
        Visitor visitor = visitorRepository.findById(principal.getName()).orElse(null);

        if(doesVisitorHaveActiveLibraryCard(visitor) || book == null) {
            return "error/error-book-or-no-active-librarycard";
    }

        if(!book.isAvailable()) {
            return "error/book-is-not-available";
        }

        CurrentLoan currentLoanToRegister = getLoan(book, visitor);
        loanRepository.save(currentLoanToRegister);

        return "loan/loan-success";

    }

    private boolean doesVisitorHaveActiveLibraryCard(Visitor visitor) {
        return visitor.getActiveLibraryCard() == null;
    }

    private CurrentLoan getLoan(Book book, Visitor visitor) {
        CurrentLoan currentLoanToRegister = new CurrentLoan();
        Calendar calendar = Calendar.getInstance();
        LibraryCard libraryCard = visitor.getActiveLibraryCard();
        book.setAvailable(false);
        currentLoanToRegister.setBook(book);
        currentLoanToRegister.setDateLoanStart(new Date(calendar.getTime().getTime()));
        currentLoanToRegister.setLibraryCard(libraryCard);
        calendar.add(Calendar.MONTH,1 );
        currentLoanToRegister.setDateLoanEnd(new Date(calendar.getTime().getTime()));
        return currentLoanToRegister;
    }
}
