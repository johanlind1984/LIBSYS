package com.newtongroup.library;

import com.newtongroup.library.Controller.LibrarianController;
import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.BookLoan;
import com.newtongroup.library.Entity.Visitor;
import com.newtongroup.library.Repository.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LibrarianControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LibrarianController librarianController;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookLoanRepository bookLoanRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(librarianController).build();
    }

    @Before
    public void init() {
        // Setting up authorities
        InitUtil.setupAuthorities(userAuthorityRepository);

        // Creating all users
        InitUtil.setupAndReturnLibrarian(userAuthorityRepository, librarianRepository, userRepository, "librarianUser@gmail.com");
        InitUtil.setupAndReturnVisitor(userAuthorityRepository, visitorRepository, userRepository, "visitorUser@gmail.com");
        Visitor visitorWithLoans = InitUtil.setupAndReturnVisitor(userAuthorityRepository, visitorRepository, userRepository, "visitorUserLoan@gmail.com");

        // Creating, Author, Book and Loan
        Author author = InitUtil.setupAndReturnAuthor(authorRepository, "Peter", "LeMarc");
        Book bookBorrowed = InitUtil.setupAndReturnBook(bookRepository, author, "Sagan om ringen");
        InitUtil.setupAndReturnBookLoan(bookLoanRepository, visitorWithLoans, bookBorrowed);
    }

    // Testa null bok i param
    // Testa bok som inte finns i param
    // Testa lämna tillbaka existerande bok med fel användare
    // Testa lämna tillbaka icke utlånad bok som librarian

    @Test
    @WithMockUser(username = "librarianUser@gmail.com", roles = {"LIBRARIAN"})
    public void testReturnRentedBookAsLibrarian() throws Exception {
        Book book = bookRepository.findById((long) 1).orElse(null);
        BookLoan bookLoan = bookLoanRepository.findById((long) 1).orElse(null);
        this.mockMvc.perform(get("/librarian/return-book")
                .flashAttr("book", book)
                .flashAttr("bookLoan", bookLoan)
                .param("bookId", String.valueOf(book.getId())))
                .andExpect(view().name("loan/return-success"));

        bookLoan = bookLoanRepository.findById((long) 1).orElse(null);
        Assert.assertTrue(bookLoan.getBookReturned());
        Assert.assertTrue(bookLoan.getBook().isAvailable());
    }

    @Test
    @WithMockUser(username = "visitorUser@gmail.com", roles = {"VISITOR"})
    public void testDeleteUserAsVisitor() throws Exception {
        this.mockMvc.perform(get("/admin/delete-user")
                .param("email", "librarianUser@gmail.com"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "librarianUser@gmail.com", roles = {"LIBRARIAN"})
    public void testDeleteUserAsLibrarian() throws Exception {
        this.mockMvc.perform(get("/librarian/delete-visitor")
                .param("email", "visitorUser@gmail.com"))
                .andExpect(status().isOk());

        Assert.assertNull(userRepository.findByUsername("visitorUser@gmail.com"));
    }

    @Test
    @WithMockUser(username = "librarianUser@gmail.com", roles = {"LIBRARIAN"})
    public void testDeleteUserWithRentalsAsLibrarian() throws Exception {
        this.mockMvc.perform(get("/librarian/delete-visitor")
                .param("email", "visitorUserLoan@gmail.com"))
                .andExpect(view().name("admin/delete-failed-user-has-loans"));

        Assert.assertNotNull(userRepository.findByUsername("visitorUserLoan@gmail.com"));
        Assert.assertNotNull(visitorRepository.findByEmail("visitorUserLoan@gmail.com"));
    }

    @Test
    @WithMockUser(username = "librarianUser@gmail.com", roles = {"LIBRARIAN"})
    public void testDeleteNonExistingUserAsLibrarian() throws Exception {
        this.mockMvc.perform(get("/librarian/delete-visitor")
                .param("email", "nonexistinguser@gmail.com"))
                .andExpect(view().name("error/email-cannot-be-found"));
    }

    @Test
    @WithMockUser(username = "librarianUser@gmail.com", roles = {"LIBRARIAN"})
    public void testDeleteExistingUserVisitorIsDataHashed() throws Exception {
        Visitor visitorBeforeHash = visitorRepository.findByEmail("visitorUser@gmail.com");
        this.mockMvc.perform(get("/librarian/delete-visitor")
                .param("email", "visitorUser@gmail.com"))
                .andExpect(view().name("admin/delete-confirmation"));

        Visitor visitorAfterHash = visitorRepository.findById(visitorBeforeHash.getPersonId()).orElse(null);
        Assert.assertNotEquals(visitorBeforeHash.getCity(), visitorAfterHash.getCity());
        Assert.assertNotEquals(visitorBeforeHash.getFirstName(), visitorAfterHash.getFirstName());
        Assert.assertNotEquals(visitorBeforeHash.getLastName(), visitorAfterHash.getLastName());
        Assert.assertNotEquals(visitorBeforeHash.getPersonalNumber(), visitorAfterHash.getPersonalNumber());
        Assert.assertNotEquals(visitorBeforeHash.getStreet(), visitorAfterHash.getStreet());
        Assert.assertNotEquals(visitorBeforeHash.getPostalCode(), visitorAfterHash.getPostalCode());
        Assert.assertNotEquals(visitorBeforeHash.getPhone(), visitorAfterHash.getPhone());
        Assert.assertNotEquals(visitorBeforeHash.getEmail(), visitorAfterHash.getEmail());
    }
}
