package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.*;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

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
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private EbookLoanRepository ebookLoanRepository;

    @Autowired
    private EBookRepository eBookRepository;

    @Autowired
    private LibraryCardRepository libraryCardRepository;

    @RequestMapping("/visitor")
    public String loanvisitor(Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

        List<Book> bookList = getActiveBookList();
        Book book = new Book();


        theModel.addAttribute("book", book);
        theModel.addAttribute("bookList", bookList);
        return "loan/register-book";
    }

    @RequestMapping("/librarian")
    public String loanlibrarian(Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));





        LibraryCard libraryCard = new LibraryCard();
        Book book = new Book();

        List<LibraryCard> libraryCardList = getActiveCardList();
        List<Book> bookList = getActiveBookList();





        theModel.addAttribute("book", book);
        theModel.addAttribute("libraryCard", libraryCard);
        theModel.addAttribute("libraryCardList", libraryCardList);
        theModel.addAttribute("bookList", bookList);
        return "loan/register-book-librarian";
    }

    @RequestMapping("/register-loan")
    public String registerLoan(@RequestParam(value = "bookId", required = false) Long bookIdParam,
                               @RequestParam(name = "eBookId", required = false) Long eBookId,
                               @RequestParam(name = "librarycardnumber", required = false) Long librarycardnumberTEST,
                               @ModelAttribute("libraryCard") LibraryCard libraryCard,
                               @ModelAttribute("book") Book book,
                               Model theModel, Principal principal) {


        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        User user = userRepository.findByUsername(principal.getName());
        Long librarycardnumber;
        Long bookId;

        if(bookIdParam == null) {
            bookId = book.getId();
            librarycardnumber = libraryCard.getLibraryCardNumber();
        } else {
            bookId = bookIdParam;
            librarycardnumber = visitorRepository.findByEmail(principal.getName()).getActiveLibraryCard().getLibraryCardNumber();
        }


        if(user.getAuthority().getAuthorityName().equals("ROLE_LIBRARIAN")){
            LibraryCard tempcard = libraryCardRepository.findById(librarycardnumber).orElse(null);
            if(tempcard == null ){
                return "error/book-or-no-active-librarycard";
            }

        }

        if(user.getAuthority().getAuthorityName().equals("ROLE_VISITOR")){

            Visitor visitor = visitorRepository.findByEmail(principal.getName());

           String result = registerLoan(visitor, bookId, eBookId);
            return result;

        } else if(user.getAuthority().getAuthorityName().equals("ROLE_LIBRARIAN")){
            LibraryCard libraryCard1 = libraryCardRepository.findById(librarycardnumber).orElse(null);

            if (libraryCard1 != null) {
                Visitor visitor1 = libraryCard1.getVisitor();
               String result = registerLoan(visitor1, bookId, eBookId);
               return result;

            }
        }

        return "/error";

    }

    public String registerLoan(Visitor visitor, Long bookId, Long eBookId) {
        if (doesVisitorHaveActiveLibraryCard(visitor) == false) {
            return "error/book-or-no-active-librarycard";
        }

        if (bookId != null) {
            Book book = bookrepository.findById((bookId)).orElse(null);
            if (!book.isAvailable()) {
                return "error/book-is-not-available";
            }
            BookLoan bookLoan = getLoan(book, visitor);
            bookLoanRepository.save(bookLoan);

        } else if (eBookId != null) {
            EBook ebook = eBookRepository.findById((eBookId)).orElse(null);

            if (!ebook.isAvailable() ) {
                return "error/book-is-not-available";
            }
            EbookLoan ebookLoan = getLoan(ebook, visitor);
            ebookLoanRepository.save(ebookLoan);
        } else {
            return "error/book-or-no-active-librarycard";
        }
        return "loan/loan-success";
    }

    private String getRole(User user) {
        if (user.getAuthority().getAuthorityName().equals("ROLE_VISITOR")) {
            return "visitor";
        } else if (user.getAuthority().getAuthorityName().equals("ROLE_LIBRARIAN")) {
            return "librarian";
        }
        return null;
    }


    private boolean doesVisitorHaveActiveLibraryCard(Visitor visitor) {

                if(visitor.getActiveLibraryCard() == null) {
                    return false;
                }
                return true;


    }

    private BookLoan getLoan(Book book, Visitor visitor) {
        BookLoan currentLoanToRegister = new BookLoan();
        Calendar calendar = Calendar.getInstance();
        LibraryCard libraryCard = visitor.getActiveLibraryCard();
        book.setAvailable(false);
        currentLoanToRegister.setBook(book);
        currentLoanToRegister.setDateLoanStart(new Date(calendar.getTime().getTime()));
        currentLoanToRegister.setLibraryCard(libraryCard);
        calendar.add(Calendar.MONTH, 1);
        currentLoanToRegister.setDateLoanEnd(new Date(calendar.getTime().getTime()));
        currentLoanToRegister.setBookReturned(false);
        return currentLoanToRegister;
    }

    private EbookLoan getLoan(EBook book, Visitor visitor) {
        EbookLoan currentLoanToRegister = new EbookLoan();
        Calendar calendar = Calendar.getInstance();
        LibraryCard libraryCard = visitor.getActiveLibraryCard();
        book.setAvailable(false);
        currentLoanToRegister.setEbook(book);
        currentLoanToRegister.setDateLoanStart(new Date(calendar.getTime().getTime()));
        currentLoanToRegister.setLibraryCard(libraryCard);
        calendar.add(Calendar.MONTH, 1);
        currentLoanToRegister.setDateLoanEnd(new Date(calendar.getTime().getTime()));
        currentLoanToRegister.setEbookReturned(false);
        return currentLoanToRegister;
    }

    private List<LibraryCard> getActiveCardList(){
        List<LibraryCard> tempList = libraryCardRepository.findAll()
                .stream()
                .filter(card -> card.getVisitor().isActive() == true)
                .collect(Collectors.toList());

        List<LibraryCard> libraryCardList = new ArrayList<>();

        for(LibraryCard temp : tempList){
            if(temp.isActive()){
                libraryCardList.add(temp);
            }
        }
        return libraryCardList;
    }

    private List<Book> getActiveBookList(){
        List<Book> tempList = bookrepository.findAll();
        List<Book> bookList = new ArrayList<>();

        for(Book temp : tempList){
            if(temp.isAvailable()){
                bookList.add(temp);
            }
        }
        return bookList;
    }
}
