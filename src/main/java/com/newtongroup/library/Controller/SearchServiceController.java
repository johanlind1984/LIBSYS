package com.newtongroup.library.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.EBook;
import com.newtongroup.library.Repository.BookRepository;
import com.newtongroup.library.Repository.EBookRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Service.SearchService;
import com.newtongroup.library.Utils.HeaderUtils;

@Controller
@RequestMapping("/search")
public class SearchServiceController {

	@Autowired
	private SearchService searchService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private EBookRepository ebookRepository;

	@GetMapping()
	public String getSearchForm(Model model, Principal principal) {
		model.addAttribute("header", HeaderUtils.getHeaderString(userRepository, principal));
		return "/search/searchview";
	}

	@PostMapping()
	public String postSearchForm(@RequestParam(value = "search", required = false) String searchText, Model model,
			Principal principal) {
		model.addAttribute("header", HeaderUtils.getHeaderString(userRepository, principal));

		List<Book> bResults = searchService.searchBooks(searchText);
		List<EBook> ebResults = searchService.searchEBooks(searchText);
		model.addAttribute("bResults", bResults);
		model.addAttribute("ebResults", ebResults);

		model.addAttribute("search", searchText);

		return "/search/searchview";
	}

	@GetMapping("/book/{id}/detailedview")
	public String getBookView(@PathVariable("id") long id,
			@RequestParam(value = "searchText", required = false) String searchText, Model model, Principal principal) {
		model.addAttribute("header", HeaderUtils.getHeaderString(userRepository, principal));
		model.addAttribute("searchText", searchText != null ? searchText : "");
		Optional<Book> book = bookRepository.findById(id);

		model.addAttribute("book", book.orElse(null));

		return book.isPresent() ? "/search/detailedview" : "/error/id-error";

	}
	
	@GetMapping("/ebook/{id}/detailedview")
	public String getEBookView(@PathVariable("id") long id,
			@RequestParam(value = "searchText", required = false) String searchText, Model model, Principal principal) {
		model.addAttribute("header", HeaderUtils.getHeaderString(userRepository, principal));
		model.addAttribute("searchText", searchText != null ? searchText : "");
		Optional<EBook> eBook = ebookRepository.findById(id);
		
		model.addAttribute("book", eBook.orElse(null));
		
		return eBook.isPresent() ? "/search/detailedview" : "/error/id-error";
		
	}

}
