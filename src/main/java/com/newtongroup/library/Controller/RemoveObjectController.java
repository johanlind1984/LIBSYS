package com.newtongroup.library.Controller;


import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.*;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/remove-object")
public class RemoveObjectController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EBookRepository eBookRepository;
    @Autowired
    private SeminaryRepository seminaryRepository;

    @Autowired
    private RemovedBookRepository removedBookRepository;
    @Autowired
    private RemovedEBookRepository removedEBookRepository;
    @Autowired
    private RemovedSeminaryRepository removedSeminaryRepository;

    @GetMapping("/menu")
    public String getMenu(){
        return "remove-objects/remove-object-menu";
    }

    @RequestMapping("/home")
    public String goToHome(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());

        switch (user.getAuthority().getAuthorityName()) {
            case "ROLE_ADMIN":
                return "redirect:/admin/";
            case "ROLE_LIBRARIAN":
                return "redirect:/librarian/";
            default:
                break;
        }
        return null;
    }

    @RequestMapping("/book")
    public String book(Model theModel, Principal principal){

        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        theModel.addAttribute("book", new Book());
        theModel.addAttribute("removedBook", new RemovedBook());


        return "remove-objects/remove-book";
    }

    @RequestMapping("/e-book")
    public String ebook(Model theModel, Principal principal){
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        theModel.addAttribute("ebook", new EBook());
        theModel.addAttribute("removedEBook", new RemovedEBook());


        return "remove-objects/remove-e-book";
    }

    @RequestMapping("/seminary")
    public String seminarie(Model theModel, Principal principal){
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        theModel.addAttribute("seminary", new Seminary());
        theModel.addAttribute("removedSeminary", new RemovedSeminary());


        return "remove-objects/remove-seminary";
    }

    @RequestMapping("/delete-book")
    public String deleteBook(@ModelAttribute("book")Book theBook, @ModelAttribute("removedBook") RemovedBook theRemovedBook, Model theModel, Principal principal){
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        String isbn = theBook.getIsbn();

        List<Book> bookList = bookRepository.findAll();

        for(Book temp : bookList){
            if(temp.getIsbn().equals(isbn)){
                int id = temp.getId();
                String title = temp.getTitle();
                String publisher = temp.getPublisher();
                String price = temp.getPurchasePrice();
                String cause = theRemovedBook.getCause();
                String description = temp.getDescription();
                Long placement_id = temp.getPlacement().getPlacementId();

                RemovedBook removedBook = new RemovedBook(id,title,isbn,publisher,description,price, placement_id,cause);
                removedBookRepository.save(removedBook);
                bookRepository.delete(temp);
                return "remove-objects/remove-book-confirmation";
            }



        }

        return "error/isbn-error";
    }

    @RequestMapping("/delete-e-book")
    public String deleteEBook(@ModelAttribute("EBook") EBook theEBook,  @ModelAttribute("removedEBook") RemovedEBook theRemovedEBook,Model theModel, Principal principal){
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        String isbn = theEBook.getIsbn();

        List<EBook> bookList = eBookRepository.findAll();

        for(EBook temp : bookList){
            if(temp.getIsbn().equals(isbn)){
                int id = temp.getId();
                String title = temp.getTitle();
                String publisher = temp.getPublisher();
                String price = temp.getPurchasePrice();
                String cause = theRemovedEBook.getCause();
                String description = temp.getDescription();
                String download_link = temp.getDownloadLink();

                RemovedEBook removedEBook = new RemovedEBook(id,title,isbn,publisher,description,price,download_link,cause);
                removedEBookRepository.save(removedEBook);
                System.out.println(removedEBook);

                eBookRepository.delete(temp);
                System.out.println(temp);
                return "remove-objects/remove-e-book-confirmation";
            }



        }
        return "error/isbn-error";
    }

    @RequestMapping("/delete-seminary")
    public String deleteSeminarie(@ModelAttribute("seminary") Seminary theSeminary,@ModelAttribute("removedSeminary") RemovedSeminary theRemovedSeminary, Model theModel, Principal principal){
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));


            Long id = theSeminary.getSeminary_id();

        List<Seminary> seminaryList = seminaryRepository.findAll();

        for(Seminary temp : seminaryList) {
            if (temp.getSeminary_id().equals(id)) {

                String title = temp.getTitle();
                String information = temp.getInformation();
                String cause = theRemovedSeminary.getCause();
                String startTime = temp.getStartTime();
                String endTime = temp.getEndTime();
                String occurrence = temp.getOccurrence();


                RemovedSeminary removedSeminary = new RemovedSeminary(id, title, information, occurrence, startTime, endTime, cause);
                removedSeminaryRepository.save(removedSeminary);

                seminaryRepository.delete(temp);
                return "remove-objects/remove-seminary-confirmation";
            }

        }

        return "error/id-error";


    }
}
