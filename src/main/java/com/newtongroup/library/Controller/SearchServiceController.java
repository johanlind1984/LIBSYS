package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Service.SearchService;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchServiceController {
	
	@Autowired
	private SearchService searchService;

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping()
	public String searchForm(@RequestParam(value="search", required =false) String searchText, Model model, Principal principal) {
		model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
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
