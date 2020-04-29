package com.newtongroup.library.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Service.SearchService;

@Controller
@RequestMapping("/search")
public class SearchServiceController {
	
	@Autowired
	private SearchService searchService;
	
	@GetMapping()
	public String searchForm( @RequestParam(value="search", required =false) String searchText, Model model) {
		
		if (searchText != null && !searchText.isEmpty() && !searchText.isBlank()) {
			
			List<Book> result = searchService.searchBooks(searchText);
			model.addAttribute("results", result);
		} else {
			searchText = "";
		}
		
		model.addAttribute("search", searchText);
		
		return "/search/searchview";
	}

}
