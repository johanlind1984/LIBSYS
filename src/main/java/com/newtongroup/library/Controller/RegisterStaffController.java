package com.newtongroup.library.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterStaffController {

    @RequestMapping("/librarian")
    public String registerStaff(Model theModel) {
        System.out.println("registerStaff");
        return "register-complete";
    }

    @RequestMapping("/admin")
    public String registerAdmin(Model theModel) {
        System.out.println("registerAdmin");
        return "register-complete";
    }
}
