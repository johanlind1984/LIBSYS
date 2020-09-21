package com.newtongroup.library;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.AssertTrue;

import com.newtongroup.library.Entity.Placement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.newtongroup.library.Controller.EditObjectController;
import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Repository.AuthorRepository;
import com.newtongroup.library.Repository.BookRepository;
import com.newtongroup.library.Repository.LibrarianRepository;
import com.newtongroup.library.Repository.PlacementRepository;
import com.newtongroup.library.Repository.UserAuthorityRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Repository.VisitorRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EditObjectControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	PlacementRepository placementRepository;

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	UserAuthorityRepository userAuthorityRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	LibrarianRepository librarianRepository;

	@Autowired
	VisitorRepository visitorRepository;

	@Autowired
	EditObjectController editObjectController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(editObjectController).build();
	}

	@Before
	public void init() {
		// Setting up authorities
		InitUtil.setupAuthorities(userAuthorityRepository);

		// Creating all users
		InitUtil.setupAndReturnLibrarian(userAuthorityRepository, librarianRepository, userRepository,
				"librarianUser@gmail.com");
		InitUtil.setupAndReturnVisitor(userAuthorityRepository, visitorRepository, userRepository,
				"visitorUser@gmail.com");

		// Creating, Author and book
		Author author = InitUtil.setupAndReturnAuthor(authorRepository, "Peter", "LeMarc");
		Placement placement = InitUtil.setUpAndReturnPlacement(placementRepository);
		InitUtil.setupAndReturnBook(bookRepository, author,placement, "Sagan om ringen");
		InitUtil.setupAndReturnAuthor(authorRepository, "Stephen", "Kung");

	}

	// Testa att ladda sidan för edit-book med ett id som finns och ett id som inte
	// finns
	@Test
	@WithMockUser(username = "librarianUser@gmail.com", roles = { "LIBRARIAN" })
	public void testEditBookViewAsLibrarian() throws Exception {
		this.mockMvc.perform(get("/edit-object/edit-book/{id}", 1)).andExpect(status().isOk());

		// Skickar man in ett id som inte finns så crashar thymeleaf med ett konstigt
		// fel som var svårt att identifiera.

		// TODO redirect till id-error template i controllern om id = null

		// this.mockMvc.perform(get("/edit-object/edit-book/{id}", 2))
		// .andExpect(status().isNotFound());

	}

	// Vyn går att komma åt trots att man saknar rätt roll
	@Test
	@WithMockUser(username = "visitorUser@gmail.com", roles = { "VISITOR" })
	public void testEditBookViewAsVisitor() throws Exception {
		this.mockMvc.perform(get("/edit-object/edit-book/{id}", 1)).andExpect(status().isForbidden());

	}

	// Testa ändra i edit-book formuläret genom att ändra författare i bok-objektet
	// och anropa
	// controller metoden
	@Test
	@WithMockUser(username = "librarianUser@gmail.com", roles = { "LIBRARIAN" })
	public void testEditBookAsLibrarian() throws Exception {
		Book book = bookRepository.findById(1l).get();
		Author newAuthor = authorRepository.findById(2).get();
		List<Author> authorList = book.getAuthorList();
		Author oldAuthor = authorList.remove(0);
		authorList.add(newAuthor);

		this.mockMvc.perform(post("/edit-object/edit-book/").flashAttr("book", book))
				.andExpect(view().name("redirect:/edit-object/edit-book/1?success=true")).andExpect(status().isFound());

		Book book2 = bookRepository.findById(1l).get();

		Assert.assertEquals(newAuthor.getAuthorId(), book2.getAuthorList().get(0).getAuthorId());
		Assert.assertNotEquals(oldAuthor.getAuthorId(), newAuthor.getAuthorId());

	}
	
	//Testa att ändra namn/efternamn och födelseår på författare i edit-author formuläret
	@Test
	@WithMockUser(username = "librarianUser@gmail.com", roles = { "LIBRARIAN" })
	public void testEditAuthorAsLibrarian() throws Exception {
		Author author = authorRepository.findById(1).get();
		author.setFirstname("Roboute");
		author.setLastname("Gulliman");
		author.setBirthYear("40,000");
		this.mockMvc.perform(post("/edit-object/edit-author").flashAttr("author", author))
				.andExpect(view().name("redirect:/edit-object/edit-author/1?success=true"))
				.andExpect(status().isFound());

		Author authorWithnewName = authorRepository.findById(1).get();

		Assert.assertEquals(author.getAuthorId(), authorWithnewName.getAuthorId());

	}

}
