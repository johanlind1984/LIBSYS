package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Admin;
import com.newtongroup.library.Entity.Librarian;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterStaffController {

    @RequestMapping("/librarian")
    public String registerLibrarian(Model theModel) {
        System.out.println("registerLibrarian");
        theModel.addAttribute("librarian", new Librarian());
        return "register/register-librarian";
    }

    @RequestMapping("/admin")
    public String registerAdmin(Model theModel) {
        System.out.println("registerAdmin");
        theModel.addAttribute("admin", new Admin());
        return "register/register-admin";
    }
}
