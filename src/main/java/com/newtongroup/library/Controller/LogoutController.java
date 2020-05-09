package com.newtongroup.library.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout-redirect")
public class LogoutController {

    @RequestMapping("/")
    public String logout() {
        return "logout/logout";
    }
}
