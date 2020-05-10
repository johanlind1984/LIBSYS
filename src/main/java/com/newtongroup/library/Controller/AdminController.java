package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.*;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String mainView(Model theModel, Principal principal){
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        return "admin/start";
    }

    @RequestMapping("/delete-menu")
    public String deleteUserMenu(Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        theModel.addAttribute("email", new String());
        return "admin/delete-user-menu";
    }

    @RequestMapping("/delete-user")
    public String deleteUser(@RequestParam(name="email") String email,  Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

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
                    hashAllUSerData(user);
                    break;
                default:
                    break;
            }

            userRepository.deleteById(user.getId());
        }

        return "admin/delete-confirmation";
    }

    private void hashAllUSerData(User user) {
        Visitor visitor = visitorRepository.findById(user.getUsername()).orElse(null);
        visitor.setFirstName(passwordEncoder.encode(visitor.getFirstName()));
        visitor.setLastName(passwordEncoder.encode(visitor.getLastName()));
        visitor.setStreet(passwordEncoder.encode(visitor.getStreet()));
        visitor.setPostalCode(passwordEncoder.encode(visitor.getPostalCode()));
        visitor.setCity(passwordEncoder.encode(visitor.getCity()));
        visitor.setPhone(passwordEncoder.encode(visitor.getPhone()));
        visitor.setPersonalNumber(passwordEncoder.encode(visitor.getPersonalNumber()));
        visitorRepository.save(visitor);
    }

}
