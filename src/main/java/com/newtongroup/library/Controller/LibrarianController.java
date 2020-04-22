package com.newtongroup.library.Controller;

import com.newtongroup.library.Repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    @Autowired
    private VisitorRepository visitorRepository;

    @RequestMapping("/")
    public String mainView(){

        return "librarian/start";
    }
}
