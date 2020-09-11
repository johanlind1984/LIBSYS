package com.newtongroup.library;

import com.newtongroup.library.Controller.LibrarianController;
import com.newtongroup.library.Entity.*;
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
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LibrarianController librarianController;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private LibraryCardRepository libraryCardRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(librarianController).build();
    }

    @Before
    public void init() {
        // Setting up authorities
        InitUtil.setupAuthorities(userAuthorityRepository);
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
}
