package com.newtongroup.library.Controller;

import com.newtongroup.library.Repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    @Autowired
    private VisitorRepository visitorRepository;

    private String header = "librarian/librarianheader.html";

    @RequestMapping("/")
    public String mainView(Model theModel){
        theModel.addAttribute("header", new String(header));
        return "librarian/start";
    }
}
