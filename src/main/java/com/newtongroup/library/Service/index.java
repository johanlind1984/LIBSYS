package com.newtongroup.library.Service;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public class index {

    @RequestMapping("/")
    public String indexPage() {

        return "landing/index";
    }
}