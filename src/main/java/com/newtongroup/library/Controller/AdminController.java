package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BossRepository bossRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @RequestMapping("/")
    public String mainView (Principal principal){
        return "admin/start";
    }
}
