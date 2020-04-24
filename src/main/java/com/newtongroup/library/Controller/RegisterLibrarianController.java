package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.UserAuthorityRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Repository.LibrarianRepository;
import com.newtongroup.library.Wrapper.UserPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register-librarian")
public class RegisterLibrarianController {

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String adminheader = "admin/adminheader.html";

    @RequestMapping("/")
    public String registerLibrarian(Model theModel) {
        theModel.addAttribute("header", adminheader);
        theModel.addAttribute("userPerson", new UserPerson());
        return "register-librarian/register-librarian";
    }

    @RequestMapping("/save-librarian")
    public String saveLibrarianToDatabase(@ModelAttribute("userPerson") UserPerson userPerson, Model theModel) {
        theModel.addAttribute("header", adminheader);
        Librarian librarian = librarianRepository.findById(userPerson.getLibrarian().getEmail()).orElse(null);
        if(librarian == null) {
            setLibrarianValues(userPerson);
            saveToDb(userPerson);
            return "register-librarian/librarian-registration-confirmation";
        } else {
            return "error/eposten-redan-registrerad";
        }
    }

    private void setLibrarianValues(UserPerson userPerson) {
        userPerson.getUser().setAuthority(userAuthorityRepository.findById((long) 3).orElse(null));
        userPerson.getUser().setEnabled(true);
        userPerson.getUser().setUsername(userPerson.getLibrarian().getEmail());
        userPerson.getUser().setPassword(passwordEncoder.encode(userPerson.getUser().getPassword()));
    }

    private void saveToDb(UserPerson userPerson) {
        librarianRepository.saveAndFlush((userPerson.getLibrarian()));
        userRepository.saveAndFlush(userPerson.getUser());
    }
}
