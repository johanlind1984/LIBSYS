package com.newtongroup.library;

import com.newtongroup.library.Controller.AdminController;
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

import java.util.ArrayList;

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


    }

    // Testa null bok i param

    // Testa bok som inte finns i param

    // Testa lämna tillbaka existerande bok med fel användare


    // Testa lämna tillbaka icke utlånad bok som librarian
    @Test
    @WithMockUser(username = "librarianUser@gmail.com", roles = { "LIBRARIAN" })
    public void testReturnBookNotLeantAsLibrarian() throws Exception {
        // FAILAR: Inget BookLoan i modellen resulterar i NullPointerException, måste reda ut hur vi lägger till objekt i modellen.
        Book book = bookRepository.findById((long) 1).orElse(null);
        this.mockMvc.perform(get("/librarian/return-book")
                .param("bookId", String.valueOf(book.getId())))
                .andExpect(view().name("error/book-or-no-active-librarycard"));
    }

    @Test
    @WithMockUser(username = "visitorUser@gmail.com", roles = { "VISITOR" })
    public void testDeleteUserAsVisitor() throws Exception {
        this.mockMvc.perform(get("/admin/delete-user")
                .param("email", "librarianUser@gmail.com"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "librarianUser@gmail.com", roles = { "LIBRARIAN" })
    public void testDeleteUserAsLibrarian() throws Exception {
        this.mockMvc.perform(get("/librarian/delete-visitor")
                .param("email", "visitorUser@gmail.com"))
                .andExpect(status().isOk());

        // Användaren "User" togs aldrig bort i koden innan. Detta test upptäckte felet och nu är det årgärdat.
        // kod tillagt på rad 87 i LibrarianController.
        User user = userRepository.findByUsername("visitorUser@gmail.com");
        Assert.assertNull(userRepository.findByUsername("visitorUser@gmail.com"));
    }

    @Test
    @WithMockUser(username = "librarianUser@gmail.com", roles = { "LIBRARIAN" })
    public void testDeleteNonExistingUserAsLibrarian() throws Exception {
        this.mockMvc.perform(get("/librarian/delete-visitor")
                .param("email", "nonexistinguser@gmail.com"))
                .andExpect(view().name("error/email-cannot-be-found"));
    }

    @Test
    @WithMockUser(username = "librarianUser@gmail.com", roles = { "LIBRARIAN" })
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
