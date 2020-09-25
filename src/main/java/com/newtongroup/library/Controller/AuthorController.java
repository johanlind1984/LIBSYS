package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Entity.User;
import com.newtongroup.library.Repository.AuthorRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private UserRepository userRepository;


	@GetMapping("/new")
	public String getAuthorForm(Model model, Principal principal) {
		model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

		Author author = new Author();
		model.addAttribute("author", author);

		return "object/add-author";
	}

	@PostMapping("/save-author")
	public String saveAuthor(@RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName,
			@RequestParam("birthYear") String birthYear, Author author, Principal principal, Model model) {

		model.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));

		User user = userRepository.findByUsername(principal.getName());

		if(user.getAuthority().getAuthorityName().equals("ROLE_LIBRARIAN") ||
		user.getAuthority().getAuthorityName().equals("ROLE_ADMIN")){

			if(!firstName.isEmpty() && !lastName.isEmpty() && !birthYear.isEmpty()){
		List<Author> authors = authorRepository.findAll();
		for (Author author1 : authors) {
			boolean existsAuthor = author1.getFirstname().equalsIgnoreCase(firstName)
					&& author1.getLastname().equalsIgnoreCase(lastName)
					&& author1.getBirthYear().equalsIgnoreCase(birthYear);

			if (existsAuthor) {
				return "error/author-already-exist";
			}
		}
		authorRepository.save(author);
	}
		}
		return "redirect:/author/new";
	}



}