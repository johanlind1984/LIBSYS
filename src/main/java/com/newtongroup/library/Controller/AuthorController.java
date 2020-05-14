package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Repository.AuthorRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/author")
public class AuthorController {


    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/new")
    public String getAuthorForm(Model model, Principal principal){
        model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

        Author author = new Author();
        model.addAttribute("author",author);

        return "object/add-author";
    }

    @PostMapping("/save-author")
    public String saveAuthor(@RequestParam("firstname") String firstName,
                             @RequestParam("lastname") String lastName,
                             @RequestParam ("birthYear") String birthYear,
                             Model model, Principal principal,  Author author) {
        model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

        List<Author> authorList = authorRepository.findAll();
        for (Author authors : authorList) {
            if (authors.getFirstname().equals(firstName) && lastName.equals(authors.getLastname()) && birthYear.equals(authors.getBirthYear())) {
                return "error/author-already-exist";
            } else {
                authorRepository.save(author);
                return "object/author-confirmation";

            }
        }
        return "redirect:/author/new";
    }
}