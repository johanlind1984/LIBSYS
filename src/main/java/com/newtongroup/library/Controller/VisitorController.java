package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.User;
import com.newtongroup.library.Repository.AdminRepository;
import com.newtongroup.library.Repository.LibrarianRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    private String header = "visitor/visitorheader.html";

    @RequestMapping("/")
    public String mainView(Model theModel){
        theModel.addAttribute("header", new String(header));
        return "visitor/start";
    }
}
