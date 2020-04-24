package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/author")
public class AuthorController {


    @Autowired
    private AuthorRepository authorRepository;

    private String header = "librarian/librarianheader.html";

    @RequestMapping("/")
    public String mainView(Model model) {
        model.addAttribute("header", new String(header));
        return "librarian/start";
    }

    @GetMapping("/new")
    public String NewAuthorForm(Model model) {

        Author author = new Author();

        model.addAttribute("author", author);

        return "librarian/start";

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView saveAuthor(Model model,
                                  @RequestParam("firstName") String firstName,
                                  @RequestParam("lastName") String lastName) {

        Author author = (Author) authorRepository.findAll();

        if (author.getFirstname().equals(firstName) && author.getLastname().equals(lastName)) {
            System.out.println("Författaren finns redan i registret.");
        } else {

            author.setFirstname(firstName);
            author.setLastname(lastName);

            authorRepository.save(author);
        }
        return new ModelAndView("redirect:/author/");
    }
}