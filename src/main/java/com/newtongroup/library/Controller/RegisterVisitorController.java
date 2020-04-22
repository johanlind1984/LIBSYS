package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Visitor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register-visitor")
public class RegisterVisitorController {

    @RequestMapping("/")
    public String registerStaff(Model theModel) {
        System.out.println("registerVisitor");
        theModel.addAttribute("visitor", new Visitor());
        return "register-visitor/register-visitor";
    }

}
