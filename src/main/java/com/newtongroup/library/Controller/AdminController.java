package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @RequestMapping("/")
    public String mainView(Principal principal){
        return "admin/start";
    }

    @RequestMapping("/delete-menu")
    public String deleteUserMenu(Model theModel) {
        theModel.addAttribute("email", new String());
        return "admin/delete-user-menu";
    }

    @RequestMapping("/delete-user")
    public String deleteUser(@RequestParam(name="email") String email) {
        System.out.println(email);
        User user = userRepository.findByUsername(email);
        if(user == null) {
            return "error/email-cannot-be-found";
        } else {
            switch (user.getAuthority().getAuthorityName()) {
                case "ROLE_ADMIN":
                    adminRepository.deleteById(email);
                    break;
                case "ROLE_LIBRARIAN":
                    librarianRepository.deleteById(email);
                    break;
                case "ROLE_VISITOR":
                    visitorRepository.deleteById(email);
                    break;
                default:
                    break;
            }
            userRepository.deleteById(user.getId());
        }
        return "admin/delete-confirmation";
    }
}
