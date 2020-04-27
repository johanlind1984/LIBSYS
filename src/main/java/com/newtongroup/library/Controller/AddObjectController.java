package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.Seminary;
import com.newtongroup.library.Repository.AuthorRepository;
import com.newtongroup.library.Repository.BookRepository;
import com.newtongroup.library.Repository.EBookRepository;
import com.newtongroup.library.Repository.SeminaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/new-seminar")
    public String getSeminarForm(Model model){
        Seminary seminary=new Seminary();
        model.addAttribute("seminary", seminary);
        return "object/add-seminar";
    }
    @PostMapping ("/save-seminar")
    public String saveSeminar(Seminary seminary){
        seminaryRepository.save(seminary);
        return "redirect:/new-object/new-seminar";
    }

    @GetMapping("/new-author")
    public String getAuthorForm(Model model){
        Author author = new Author();
        model.addAttribute("author",author);
        return "object/add-author";
    }
    @PostMapping("/save-author")
    public String saveAuthor(Author author){
        authorRepository.save(author);
        return "redirect:/new-object/new-author";
    }

    @GetMapping("/new-book")
    public String getBookForm(Model model){
        Book book = new Book();

        List<Author> authorList=authorRepository.findAll();

        model.addAttribute("book", book);
        model.addAttribute("authors", authorList);
        return "object/add-book";
    }



//    @RequestMapping(value = "/authoesAutocomplete")
//    @ResponseBody
//    public List<Author> authorAutocomplete(Model model, @RequestParam(value = "term", required = false, defaultValue = "") String term){
//        Book book = new Book();
//        List<Author> authors = new ArrayList<Author>();
//        return book.getAuthorList();
//    }


//    @PostMapping("/save-book")
//    public String saveBook(@ModelAttribute("book") Book book, Model model){
//        bookRepository.save(book);
//        return "redirect:/new-object/save-book";
//    }
}
