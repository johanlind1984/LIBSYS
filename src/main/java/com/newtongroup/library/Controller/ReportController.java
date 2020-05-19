package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.RemovedBook;
import com.newtongroup.library.Repository.RemovedBookRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping ("/report")
public class ReportController {

    @Autowired
    RemovedBookRepository removedBookRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping ("/removed-books")
    public String getBookReport (Model model, Principal principal){
        model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

        List<RemovedBook>removedBookList=removedBookRepository.findAll();
        RemovedBook removedBook=new RemovedBook();

        model.addAttribute("removedBookList", removedBookList);
        model.addAttribute("removedBook", removedBook);

        return "report/report-book";

    }


}
