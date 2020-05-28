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

import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.Placement;
import com.newtongroup.library.Repository.AuthorRepository;
import com.newtongroup.library.Repository.BookRepository;
import com.newtongroup.library.Repository.PlacementRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Utils.HeaderUtils;

@Controller
@RequestMapping("/edit-object")
public class EditObjectController {

	@Autowired
	BookRepository br;

	@Autowired
	UserRepository ur;

	@Autowired
	PlacementRepository pr;

	@Autowired
	AuthorRepository ar;

	@GetMapping({ "/edit-book/{id}" })
	public String bookInfo(
			Model model, 
			Principal principal, 
			@PathVariable("id") 
			long id,
			@RequestParam(name = "success", required = false, defaultValue = "false") 
			boolean success
		) {
	
		model.addAttribute("header", HeaderUtils.getHeaderString(ur, principal));
		Optional<Book> book = br.findById(id);

		List<Author> authors = ar.findAll();
		List<Placement> placements = pr.findAll();

		model.addAttribute("book", book);

		model.addAttribute("placements", placements);
		model.addAttribute("authors", authors);

		if (success) {
			model.addAttribute("message", "Bok har blivit uppdaterad!");
		}

		return "/edit-object/edit-book";
	}

	@PostMapping("/edit-book")
	public String updateBook(Book book) {
		br.save(book);
		return "redirect:/edit-object/edit-book/" + book.getId() + "?success=true";
	}

	@GetMapping("edit-author/{id}")
	public String authorInfo(Model model, Principal principal, @PathVariable("id") int id,
			@RequestParam(value = "searchText", required = false) String searchText,
			@RequestParam(name = "success", required = false, defaultValue = "false") boolean success) {
		model.addAttribute("header", HeaderUtils.getHeaderString(ur, principal));

		Optional<Author> author = ar.findById(id);
		model.addAttribute("author", author);
		model.addAttribute("searchText", searchText != null ? searchText : "");

		if (success) {
			model.addAttribute("message", "Författare har blivit uppdaterad!");
		}

		return "/edit-object/edit-author";

	}

	@PostMapping("/edit-author")
	public String updateAuthor(Author author, Model model, Principal principal) {
		ar.save(author);
		return "redirect:/edit-object/edit-author/" + author.getAuthorId() + "?success=true";
	}

}
