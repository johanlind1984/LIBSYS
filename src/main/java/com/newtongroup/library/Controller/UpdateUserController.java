package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Admin;
import com.newtongroup.library.Entity.Librarian;
import com.newtongroup.library.Entity.User;
import com.newtongroup.library.Entity.Visitor;
import com.newtongroup.library.Repository.AdminRepository;
import com.newtongroup.library.Repository.LibrarianRepository;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/update")
public class UpdateUserController {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/visitor")
    public String updateVisitor(Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        theModel.addAttribute("visitors", visitorRepository.findAll());
        return "update-visitor/update-visitor-search";
    }

    @RequestMapping("/visitor-form")
    public String visitorForm(@RequestParam(name = "visitorId", required = false) String visiorId, Model theModel, Principal principal) {
        Visitor visitor = visitorRepository.findById(visiorId).orElse(null);
        User user = userRepository.findByUsername(visiorId);
        UserPerson userPerson = new UserPerson();
        userPerson.setVisitor(visitor);
        userPerson.setUser(user);
        theModel.addAttribute("userPerson", userPerson);
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        return "update-visitor/update-visitor";
    }

    @RequestMapping("/savechanges-visitor")
    public String updateVisitorDatabase(@ModelAttribute("userPerson") UserPerson userPerson, Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        User user = userRepository.findByUsername(userPerson.getUser().getUsername());
        user.setPassword(passwordEncoder.encode(userPerson.getUser().getPassword()));
        userRepository.save(user);
        visitorRepository.save(userPerson.getVisitor());
        return "update-visitor/visitor-update-confirmation";
    }

    @RequestMapping("/librarian")
    public String updateLibrarian(Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        theModel.addAttribute("librarians", librarianRepository.findAll());
        return "update-librarian/update-librarian-search";
    }

    @RequestMapping("/librarian-form")
    public String librarianForm(@RequestParam(name = "librarianId", required = false) String visiorId, Model theModel, Principal principal) {
        Librarian librarian = librarianRepository.findById(visiorId).orElse(null);
        User user = userRepository.findByUsername(visiorId);
        UserPerson userPerson = new UserPerson();
        userPerson.setLibrarian(librarian);
        userPerson.setUser(user);
        theModel.addAttribute("userPerson", userPerson);
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        return "update-librarian/update-librarian";
    }

    @RequestMapping("/savechanges-librarian")
    public String updateLibrarianDatabase(@ModelAttribute("userPerson") UserPerson userPerson, Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        User user = userRepository.findByUsername(userPerson.getUser().getUsername());
        user.setPassword(passwordEncoder.encode(userPerson.getUser().getPassword()));
        userRepository.save(user);
        librarianRepository.save(userPerson.getLibrarian());
        return "update-librarian/update-librarian-confirmation";
    }

    @RequestMapping("/admin")
    public String updateAdmin(Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        theModel.addAttribute("admins", adminRepository.findAll());
        return "update-admin/update-admin-search";
    }

    @RequestMapping("/admin-form")
    public String adminForm(@RequestParam(name = "adminId", required = false) String visiorId, Model theModel, Principal principal) {
        Admin admin = adminRepository.findById(visiorId).orElse(null);
        User user = userRepository.findByUsername(visiorId);
        UserPerson userPerson = new UserPerson();
        userPerson.setAdmin(admin);
        userPerson.setUser(user);
        theModel.addAttribute("userPerson", userPerson);
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        return "update-admin/update-admin";
    }

    @RequestMapping("/savechanges-admin")
    public String updateAdminDatabase(@ModelAttribute("userPerson") UserPerson userPerson, Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        User user = userRepository.findByUsername(userPerson.getUser().getUsername());
        user.setPassword(passwordEncoder.encode(userPerson.getUser().getPassword()));
        userRepository.save(user);
        adminRepository.save(userPerson.getAdmin());
        return "update-admin/update-admin-confirmation";
    }

    
}
