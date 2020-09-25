package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.*;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping ("/new-object")
public class AddObjectController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EBookRepository eBookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeminaryRepository seminaryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PlacementRepository placementRepository;

    private Book book;
    private User user;
    private String title;
    private String description;
    private String publisher;
    private String purchaseprice;
    private String isbn;
    private String dateAdded;

    private List<Author> authorList;


    @GetMapping("/new-seminar")
    public String getSeminarForm(Model model, Principal principal){
        model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        Seminary seminary = new Seminary();
        model.addAttribute("seminary", seminary);
        return "object/add-seminar";
    }
    /*
    * Corrected after testing due to visitor was able to add seminar in system
    * */
    @PostMapping ("/save-seminar")
    public String saveSeminar(@RequestParam(name = "title") String title, @RequestParam(name = "information") String information,
                              @RequestParam(name = "occurrence") String occurrence, @RequestParam(name = "starttime") String starttime,
                              @RequestParam(name = "endtime") String endtime, Seminary seminary, Principal principal, Model model){

        model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

        user = userRepository.findByUsername(principal.getName());

        if(user.getAuthority().getAuthorityName().equals("ROLE_LIBRARIAN") ||
        user.getAuthority().getAuthorityName().equals("ROLE_ADMIN")) {

            if((!title.isEmpty()) && (!information.isEmpty())
            && (!occurrence.isEmpty()) && (!starttime.isEmpty())
            && (!endtime.isEmpty())){

                seminaryRepository.save(seminary);
            }
        }
        return "redirect:new-object/new-seminar";
    }

    @GetMapping("/new-book")
    public String getBookForm(Model model, Principal principal){
        model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        Book book = new Book();
        authorList=authorRepository.findAll(Sort.by(Sort.Direction.ASC, "lastname"));
        List<Placement>placementList=placementRepository.findAll();
        model.addAttribute("book", book);
        model.addAttribute("authors", authorList);
        model.addAttribute("placements", placementList);
        return "object/add-book";
    }

    @PostMapping("/save-book")
    public String saveBook(@ModelAttribute("book") Book book, Principal principal,
    @RequestParam(name = "title") String title, @RequestParam(name = "description") String description,
                           @RequestParam(name = "publisher") String publisher,
                           @RequestParam("purchaseprice") String purchaseprice,
                           @RequestParam(name = "isbn") String isbn){

        user = userRepository.findByUsername(principal.getName());

        if(user.getAuthority().getAuthorityName().equals("ROLE_ADMIN") ||
        user.getAuthority().getAuthorityName().equals("ROLE_LIBRARIAN")){

            if((!title.isEmpty()) && (!description.isEmpty()) && (!isbn.isEmpty())
                    && (!publisher.isEmpty())
                    && (!purchaseprice.isEmpty())){

                book.setAvailable(true);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime now = LocalDateTime.now();
                book.setDate(dtf.format(now));
                bookRepository.save(book);
            }
        }

        return "redirect:new-object/new-book";
    }

    @GetMapping("/new-ebook")
    public String getEBookForm(Model model, Principal principal){
        model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        EBook ebook = new EBook();
        List<Placement>placementList=placementRepository.findAll();
        authorList=authorRepository.findAll(Sort.by(Sort.Direction.ASC, "lastname"));
        model.addAttribute("ebook", ebook);
        model.addAttribute("authors", authorList);
        model.addAttribute("placements", placementList);
        return "object/add-ebook";
    }
    @PostMapping("/save-ebook")
    public String saveEBook(@ModelAttribute("ebook") EBook eBook){
        eBook.setAvailable(true);
        eBookRepository.save(eBook);
        return "redirect:new-object/new-ebook";
    }
}
