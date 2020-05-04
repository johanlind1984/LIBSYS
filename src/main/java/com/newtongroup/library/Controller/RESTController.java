package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.EBook;
import com.newtongroup.library.Repository.BookRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Service.SearchService;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RESTController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/search-book")
    public List<Book> postSearchFormBook(@RequestParam(value = "search", required = false, defaultValue = "")
                                                     String searchText) {
        return searchService.searchBooks(searchText);
    }

    @GetMapping("/search-ebook")
    public List<EBook> postSearchFormEBook(@RequestParam(value = "search", required = false, defaultValue = "")
                                                       String searchText) {
        return searchService.searchEBooks(searchText);
    }


}
