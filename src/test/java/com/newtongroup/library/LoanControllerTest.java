package com.newtongroup.library;

import com.newtongroup.library.Controller.LoanController;
import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.LibraryCard;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoanController loanController;

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
    private LibraryCardRepository libraryCardRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @Before
    public void init() {
        // Setting up authorities
        InitUtil.setupAuthorities(userAuthorityRepository);
        InitUtil.setupAndReturnLibrarian(userAuthorityRepository, librarianRepository, userRepository, "librarianUser@gmail.com");
        Visitor visitorWithLoans = InitUtil.setupAndReturnVisitor(userAuthorityRepository, visitorRepository, userRepository, "visitorUserLoan@gmail.com");

        // Creating, Author, Book and Loan
        Author author = InitUtil.setupAndReturnAuthor(authorRepository, "Peter", "LeMarc");
        InitUtil.setupAndReturnBook(bookRepository, author, "Sagan om ringen");
    }

    @Test
    @WithMockUser(username = "visitorUserLoan@gmail.com", roles = {"VISITOR"})
    public void testBorrowBookAsVisitor() throws Exception {
        Book book = bookRepository.findById((long) 1).orElse(null);
        LibraryCard libraryCard = libraryCardRepository.findById((long) 1).orElse(null);
        this.mockMvc.perform(get("/loan/register-loan")
                .flashAttr("book", book)
                .flashAttr("libraryCard", libraryCard)
                .param("bookId", String.valueOf(book.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("loan/loan-success"));

        Visitor visitor = visitorRepository.findByEmail("visitorUserLoan@gmail.com");
        Assert.assertFalse(visitor.getActiveLibraryCard().getBookLoans().isEmpty());
        Assert.assertFalse(visitor.getActiveLibraryCard().getBookLoans().get(0).getBookReturned());
        Assert.assertFalse(visitor.getActiveLibraryCard().getBookLoans().get(0).getBook().isAvailable());
    }

    @Test
    @WithMockUser(username = "visitorUserLoan@gmail.com", roles = {"VISITOR"})
    public void showBookListAsVisitor() throws Exception {
        this.mockMvc.perform(get("/loan/visitor"))
                .andExpect(status().isOk())
                .andExpect(view().name("loan/register-book"))
                .andExpect(model().attributeExists("bookList"));
    }

    @Test
    @WithMockUser(username = "librarianUser@gmail.com", roles = {"LIBRARIAN"})
    public void loanToVisitorAsLibrarian() throws Exception {
        this.mockMvc.perform(get("/loan/librarian"))
                .andExpect(status().isOk())
                .andExpect(view().name("loan/register-book-librarian"))
                .andExpect(model().attributeExists("bookList", "book", "libraryCard", "libraryCardList"));
    }
}
