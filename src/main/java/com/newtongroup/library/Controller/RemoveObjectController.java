package com.newtongroup.library.Controller;


import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Repository.BookRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Wrapper.UserPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/remove-object")
public class RemoveObjectController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    private String adminheader = "admin/adminheader.html";
    private String librarianheader = "librarian/librarianheader.html";

    @RequestMapping("/remove-book")
    public String test(Model theModel, Principal principal){

        theModel.addAttribute("header", getHeader(principal));



        return "remove-objects/remove-book";
    }

    @RequestMapping("/delete-book")
    public String deleteBook(@ModelAttribute("isbn") Model theModel, Principal principal){
        theModel.addAttribute("header", getHeader(principal));

        System.out.println(theModel.containsAttribute("isbn"));
        return null;
    }

    @RequestMapping("/delete-e-book")
    public String deleteEBook(@ModelAttribute("isbn") Model theModel, Principal principal){
        theModel.addAttribute("header", getHeader(principal));

        System.out.println(theModel.containsAttribute("isbn"));
        return null;
    }

    @RequestMapping("/delete-seminarie")
    public String deleteSeminarie(@ModelAttribute("isbn") Model theModel, Principal principal){
        theModel.addAttribute("header", getHeader(principal));

        System.out.println(theModel.containsAttribute("isbn"));
        return null;
    }

    private String getHeader(Principal principal) {
        String header = new String();
        if(userRepository.findByUsername(principal.getName()).getAuthority().getAuthorityName().equals("ROLE_ADMIN")) {
            return adminheader;
        } else if (userRepository.findByUsername(principal.getName()).getAuthority().getAuthorityName().equals("ROLE_LIBRARIAN")){
            return librarianheader;
        }

        return header;
    }


}
