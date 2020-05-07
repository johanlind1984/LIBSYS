package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Repository.AuthorRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


@Controller
@RequestMapping("/author")
public class AuthorController {


    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;


    private String header = "librarian/librarianheader.html";

    @RequestMapping("/")
    public String mainView(Model model) {
        model.addAttribute("header", new String(header));
        return "librarian/start";
    }

    @GetMapping("/new")
    public String NewAuthorForm(Model model, Principal principal) {

        Author author = new Author();

        model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        model.addAttribute("author", author);

        return "object/add-author";

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView saveAuthor(@RequestParam("firstName") String firstName,
                                  @RequestParam("lastName") String lastName) {

        Author author = (Author) authorRepository.findAll();

        if (firstName.equals(author.getFirstname()) && lastName.equals(author.getLastname())) {
            System.out.println("Författaren finns redan i registret.");
        } else {

            author.setFirstname(firstName);
            author.setLastname(lastName);

            authorRepository.save(author);
        }
        return new ModelAndView("redirect:/author/");
    }
}