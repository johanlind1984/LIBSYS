package com.newtongroup.library.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register-visitor")
public class RegisterVisitorController {

    @RequestMapping("/")
    public String registerStaff(Model theModel) {
        System.out.println("registerVisitor");
        return "register-complete";
    }

}
