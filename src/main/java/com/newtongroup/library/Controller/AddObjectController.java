package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.*;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping ("/new-object")
public class AddObjectController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    EBookRepository eBookRepository;

    @Autowired
    SeminaryRepository seminaryRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PlacementRepository placementRepository;

    @Autowired
    UserRepository userRepository;



    List<Author> authorList;


    @GetMapping("/new-seminar")
    public String getSeminarForm(Model model, Principal principal){
        model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        Seminary seminary=new Seminary();
        model.addAttribute("seminary", seminary);
        return "object/add-seminar";
    }
    @PostMapping ("/save-seminar")
    public String saveSeminar(Seminary seminary){
        seminaryRepository.save(seminary);
        return "redirect:/new-object/new-seminar";
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
        return "/object/add-book";
    }

    @PostMapping("/save-book")
    public String saveBook(@ModelAttribute("book") Book book){

        book.setAvailable(true);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        book.setDate(dtf.format(now));
        bookRepository.save(book);
        return "redirect:/new-object/new-book";
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
        return "/object/add-ebook";
    }
    @PostMapping("/save-ebook")
    public String saveEBook(@ModelAttribute("ebook") EBook eBook){
        eBook.setAvailable(true);
        eBookRepository.save(eBook);
        return "redirect:/new-object/new-ebook";
    }
}
