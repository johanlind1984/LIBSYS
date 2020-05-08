package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.LibraryCard;
import com.newtongroup.library.Entity.Loan;
import com.newtongroup.library.Entity.User;
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
        User user = userRepository.findByUsername(principal.getName());
        System.out.println(user.getUsername());
        System.out.println(visitorRepository.findById(principal.getName()).orElse(null).getFirstName());
        Book book = bookrepository.findById((bookId)).orElse(null);

        if(visitorRepository.findById(principal.getName()).orElse(null).getActiveLibraryCard() == null
        || book == null) {
            return "error/error-book-or-no-active-librarycard";
        }

        Loan loanToRegister = new Loan();
        Calendar calendar = Calendar.getInstance();
        LibraryCard libraryCard = visitorRepository.findById(principal.getName()).orElse(null).getActiveLibraryCard();
        loanToRegister.setBook(bookrepository.findById(bookId).orElse(null));
        loanToRegister.setDateLoanStart(new Date(calendar.getTime().getTime()));
        loanToRegister.setLibraryCard(libraryCard);
        calendar.add(Calendar.MONTH,1 );
        loanToRegister.setDateLoanEnd(new Date(calendar.getTime().getTime()));
        loanRepository.save(loanToRegister);

        return "loan/loan-success";

    }
}
