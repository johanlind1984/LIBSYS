package com.newtongroup.library.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @RequestMapping("/")
    public String indexPage() {

        return "landing/index";
    }

    @RequestMapping("/about")
    public String indexAbout() {
        return "landing/why";
    }
}