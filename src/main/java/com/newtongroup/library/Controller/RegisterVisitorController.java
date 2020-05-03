package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Visitor;
import com.newtongroup.library.Repository.UserAuthorityRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Repository.VisitorRepository;
import com.newtongroup.library.Utils.HeaderUtils;
import com.newtongroup.library.Wrapper.UserPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/register-visitor")
public class RegisterVisitorController {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String adminheader = "admin/adminheader.html";
    private String librarianheader = "librarian/librarianheader.html";



    @RequestMapping("/")
    public String registerVisitor(Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        theModel.addAttribute("userPerson", new UserPerson());
        return "register-visitor/register-visitor";
    }

    @RequestMapping("/save-visitor")
    public String saveVisitorToDatabase(@ModelAttribute("userPerson") UserPerson userPerson, Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        Visitor visitor = visitorRepository.findById(userPerson.getVisitor().getEmail()).orElse(null);
        if(visitor == null) {
            setVisitorValues(userPerson);
            saveToDb(userPerson);
            return "register-visitor/visitor-registration-confirmation";
        } else {
            return "error/eposten-redan-registrerad";
        }
    }

    private void setVisitorValues(UserPerson userPerson) {
        userPerson.getUser().setAuthority(userAuthorityRepository.findById((long) 4).orElse(null));
        userPerson.getUser().setEnabled(true);
        userPerson.getUser().setUsername(userPerson.getVisitor().getEmail());
        userPerson.getUser().setPassword(passwordEncoder.encode(userPerson.getUser().getPassword()));
    }

    private void saveToDb(UserPerson userPerson) {
        visitorRepository.saveAndFlush((userPerson.getVisitor()));
        userRepository.saveAndFlush(userPerson.getUser());
    }
}
