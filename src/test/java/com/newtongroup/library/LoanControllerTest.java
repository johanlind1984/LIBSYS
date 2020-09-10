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

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

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
        Authority adminAuth = new Authority();
        adminAuth.setAuthorityName("ROLE_ADMIN");
        Authority librarianAuth = new Authority();
        librarianAuth.setAuthorityName("ROLE_LIBRARIAN");
        Authority visitorAuth = new Authority();
        visitorAuth.setAuthorityName("ROLE_VISITOR");
        userAuthorityRepository.save(adminAuth);
        userAuthorityRepository.save(librarianAuth);
        userAuthorityRepository.save(visitorAuth);

        // Setting up users
        User adminUser = new User();
        adminUser.setUsername("adminUser@gmail.com");
        adminUser.setPassword("test");
        adminUser.setAuthority(userAuthorityRepository.findById((long) 1).orElse(null));
        adminUser.setEnabled(true);
        userRepository.save(adminUser);

        User librarianUser = new User();
        librarianUser.setUsername("librarianUser@gmail.com");
        librarianUser.setPassword("test");
        librarianUser.setAuthority(userAuthorityRepository.findById((long) 2).orElse(null));
        librarianUser.setEnabled(true);
        userRepository.save(librarianUser);

        User visitorUser = new User();
        visitorUser.setUsername("visitorUser@gmail.com");
        visitorUser.setPassword("test");
        visitorUser.setAuthority(userAuthorityRepository.findById((long) 3).orElse(null));
        visitorUser.setEnabled(true);
        userRepository.save(visitorUser);

        User visitorUserLoan = new User();
        visitorUserLoan.setUsername("visitorUserLoan@gmail.com");
        visitorUserLoan.setPassword("test");
        visitorUserLoan.setAuthority(userAuthorityRepository.findById((long) 3).orElse(null));
        visitorUserLoan.setEnabled(true);
        userRepository.save(visitorUserLoan);

        // Setting up persons
        Admin admin = new Admin();
        admin.setEmail("adminUser@gmail.com");
        admin.setCity("Stockholm");
        admin.setFirstName("Gunnar");
        admin.setLastName("Pettersson");
        admin.setPhone("085000000");
        admin.setPostalCode("173 53");
        admin.setStreet("Gökvägen 12");
        admin.setPersonalNumber("831021-3341");
        adminRepository.save(admin);

        Librarian librarian = new Librarian();
        librarian.setEmail("librarianUser@gmail.com");
        librarian.setCity("Stockholm");
        librarian.setFirstName("Gunnar");
        librarian.setLastName("Pettersson");
        librarian.setPhone("085000000");
        librarian.setPostalCode("173 53");
        librarian.setStreet("Gökvägen 12");
        librarian.setPersonalNumber("831021-3341");
        librarianRepository.save(librarian);

        Visitor visitor = new Visitor();
        visitor.setEmail("visitorUser@gmail.com");
        visitor.setCity("Stockholm");
        visitor.setFirstName("Gunnar");
        visitor.setLastName("Pettersson");
        visitor.setPhone("085000000");
        visitor.setPostalCode("173 53");
        visitor.setStreet("Gökvägen 12");
        visitor.setPersonalNumber("831021-3341");
        visitorRepository.save(visitor);

        Visitor visitorRentedBook = new Visitor();
        visitorRentedBook.setEmail("visitorUserLoan@gmail.com");
        visitorRentedBook.setCity("Stockholm");
        visitorRentedBook.setFirstName("Gunnar");
        visitorRentedBook.setLastName("Pettersson");
        visitorRentedBook.setPhone("085000000");
        visitorRentedBook.setPostalCode("173 53");
        visitorRentedBook.setStreet("Gökvägen 12");
        visitorRentedBook.setPersonalNumber("831021-3341");
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setActive(true);
        libraryCard.setVisitor(visitorRentedBook);
        libraryCard.setBookLoans(new ArrayList<>());
        libraryCard.setEbookLoans(new ArrayList<>());
        ArrayList<LibraryCard> libraryCards = new ArrayList<>();
        libraryCards.add(libraryCard);
        visitorRentedBook.setLibraryCards(libraryCards);
        visitorRepository.save(visitorRentedBook);

        // Setting upp authors
        Author author1 = new Author();
        author1.setFirstname("Janne");
        author1.setLastname("Josefsson");
        author1.setBirthYear("1956");
        author1.setBookList(new ArrayList<>());
        Author savedAuthor = authorRepository.save(author1);

        // Setting up books
        Book book1 = new Book();
        book1.setTitle("Testbok 1");
        book1.setPurchasePrice("200");
        book1.setPublisher("Testförlaget");
        book1.setIsbn("12132131332113");
        book1.setDescription("Detta är en testbok, inget annat");
        book1.setAvailable(true);
        book1.setLoanedBooks(new ArrayList<>());
        ArrayList<Author> authors = new ArrayList<>();
        authors.add(author1);
        book1.setAuthorList(authors);
        bookRepository.save(book1);

        Book rentedBook = new Book();
        rentedBook.setTitle("Testbok 1");
        rentedBook.setPurchasePrice("200");
        rentedBook.setPublisher("Testförlaget");
        rentedBook.setIsbn("12132131332113");
        rentedBook.setDescription("Detta är en testbok, inget annat");
        rentedBook.setAvailable(false);
        rentedBook.setLoanedBooks(new ArrayList<>());
        authors.add(author1);
        rentedBook.setAuthorList(authors);

        BookLoan bookLoan = new BookLoan();
        bookLoan.setBookReturned(false);
        bookLoan.setBook(rentedBook);
        bookLoan.setLibraryCard(libraryCard);
        bookLoan.setDateLoanEnd(new Date(Calendar.getInstance().getTime().getTime()));
        bookLoan.setDateLoanStart(new Date(Calendar.getInstance().getTime().getTime()));

        rentedBook.getLoanedBooks().add(bookLoan);
        bookRepository.save(rentedBook);
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

        Assert.assertFalse(visitorRepository.findByEmail("visitorUserLoan@gmail.com").getActiveLibraryCard().getBookLoans().isEmpty());
    }
}
