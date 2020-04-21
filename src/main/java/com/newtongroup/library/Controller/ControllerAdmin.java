package com.newtongroup.library.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerAdmin {

    @RequestMapping("/")
    public String mainView (){
        return "index";
    }
}
