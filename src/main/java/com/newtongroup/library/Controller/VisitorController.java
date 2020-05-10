package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.LibraryCard;
import com.newtongroup.library.Entity.User;
import com.newtongroup.library.Repository.*;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private EbookLoanRepository ebookLoanRepository;

    @RequestMapping("/")
    public String mainView(Model theModel, Principal principal){
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        return "visitor/start";
    }

    @RequestMapping("/my-loans")
    public String myLoans(Model theModel, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        LibraryCard libraryCard = visitorRepository.findById(user.getUsername()).orElse(null).getActiveLibraryCard();
        theModel.addAttribute("header", HeaderUtils.getHeaderString(user));
        theModel.addAttribute("books", bookLoanRepository.findByLibraryCard(libraryCard));
        theModel.addAttribute("ebooks", bookLoanRepository.findByLibraryCard(libraryCard));
        return "visitor/my-loans";
    }

}
