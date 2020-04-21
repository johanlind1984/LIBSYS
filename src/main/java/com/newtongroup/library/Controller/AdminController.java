package com.newtongroup.library.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class AdminController {

    @RequestMapping("/")
    public String mainView (Principal principal){
        System.out.println(principal.getName());
        return "index";
    }
}
