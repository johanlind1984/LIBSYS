package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.User;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Repository.VisitorRepository;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private UserRepository userRepository;

    private String header = "librarian/bootstrapheader.html";

    @RequestMapping("/")
    public String mainView(Model theModel, Principal principal){
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        return "librarian/start";
    }

    @RequestMapping("/delete-visitor-menu")
    public String deleteUserMenu(Model theModel, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        theModel.addAttribute("header", HeaderUtils.getHeaderString(user));
        theModel.addAttribute("email", new String());
        return "librarian/delete-user-menu";
    }

    @RequestMapping("/delete-visitor")
    public String deleteUser(@RequestParam(name="email") String email, Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        User user = userRepository.findByUsername(email);
        if(user == null) {
            return "error/email-cannot-be-found";
        } else if (user.getAuthority().getAuthorityName().equals("ROLE_VISITOR")) {
            visitorRepository.deleteById(email);
            userRepository.deleteById(user.getId());
        } else {
            return "error/email-cannot-be-found";
        }

        return "admin/delete-confirmation";
    }
}
