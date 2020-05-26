package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.LibraryCard;
import com.newtongroup.library.Entity.RemovedBook;
import com.newtongroup.library.Entity.Visitor;
import com.newtongroup.library.Repository.*;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping ("/report")
public class ReportController {

    @Autowired
    RemovedBookRepository removedBookRepository;

    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private EbookLoanRepository ebookLoanRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping ("/added-books")
    public String getBookReport (Model model, Principal principal){
        model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

        List<Book> bookList=bookRepository.findAll();
        Book book=new Book();

        model.addAttribute("bookList", bookList);
        model.addAttribute("book", book);

        return "report/report-added-books";

    }

    @GetMapping ("/removed-books")
    public String getRemovedBookReport (Model model, Principal principal){
        model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

        List<RemovedBook>removedBookList=removedBookRepository.findAll();
        RemovedBook removedBook=new RemovedBook();

        model.addAttribute("removedBookList", removedBookList);
        model.addAttribute("removedBook", removedBook);

        return "report/report-removed-book";

    }

    @RequestMapping("/visitor-search")
    public String visitorReportSearch(Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        theModel.addAttribute("visitors", visitorRepository.findByIsActive(true));
        return "report/report-visitor-search";
    }

    @RequestMapping("/visitor-loans")
    public String myLoans(@RequestParam(name = "visitorId", required = true) Long visitorId, Model theModel, Principal principal){
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        Visitor visitor = visitorRepository.findById(visitorId).orElse(null);
        LibraryCard libraryCard = visitor.getActiveLibraryCard();
        theModel.addAttribute("bookLoans",
                bookLoanRepository.findByLibraryCardAndIsBookReturnedOrderByDateLoanStartAsc(libraryCard, false));
        theModel.addAttribute("ebookLoans",
                ebookLoanRepository.findByLibraryCardAndIsEbookReturnedOrderByDateLoanStartAsc(libraryCard, false));
        theModel.addAttribute("visitor", visitor);
        return "report/report-visitor-loans";
    }


}
