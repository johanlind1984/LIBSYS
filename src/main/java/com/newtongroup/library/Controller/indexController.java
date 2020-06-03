package com.newtongroup.library.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @RequestMapping("/")
    public String indexPage() { return "landing/index"; }

    @RequestMapping("/why")
    public String indexWhy() {
        return "landing/why";
    }

    @RequestMapping("/about")
    public String indexAbout() { return "landing/about"; }

    @RequestMapping("/aboutus")
    public String indexAboutUs() { return "landing/aboutus"; }
}