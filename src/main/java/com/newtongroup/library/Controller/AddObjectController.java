package com.newtongroup.library.Controller;

import com.newtongroup.library.Repository.BookRepository;
import com.newtongroup.library.Repository.EBookRepository;
import com.newtongroup.library.Repository.SeminaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/new-object")
public class AddObjectController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    EBookRepository eBookRepository;

    @Autowired
    SeminaryRepository seminaryRepository;



}
