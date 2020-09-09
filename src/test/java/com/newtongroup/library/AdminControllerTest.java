package com.newtongroup.library;

import com.newtongroup.library.Controller.AdminController;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminController adminController;

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

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
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
        this.mockMvc.perform(get("/admin/delete-user")
                .param("email", "visitorUser@gmail.com"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "adminUser@gmail.com", roles = { "ADMIN" })
    public void testDeleteNonExistingUserAsAdmin() throws Exception {
        this.mockMvc.perform(get("/admin/delete-user")
                .param("email", "nonexistinguser@gmail.com"))
                .andExpect(view().name("error/email-cannot-be-found"));
    }

    @Test
    @WithMockUser(username = "adminUser@gmail.com", roles = { "ADMIN" })
    public void testDeleteExistingUserVisitorAsAdmin() throws Exception {
        // Failar eftersom visitor inte har bibliotekskort uppsatt i init methoden. Koden skall inte tillåta skapa en
        // användare utan bibliotekskort men skall vi testa utan bibliotekskort ändå?
        this.mockMvc.perform(get("/admin/delete-user")
                .param("email", "visitorUser@gmail.com"))
                .andExpect(view().name("admin/delete-confirmation"));

        Assert.assertNull(userRepository.findByUsername("visitorUser@gmail.com"));
    }

    @Test
    @WithMockUser(username = "adminUser@gmail.com", roles = { "ADMIN" })
    public void testDeleteExistingUserLibrarianAsAdmin() throws Exception {
        this.mockMvc.perform(get("/admin/delete-user")
                .param("email", "librarianUser@gmail.com"))
                .andExpect(view().name("admin/delete-confirmation"));

        Assert.assertNull(userRepository.findByUsername("librarianUser@gmail.com"));
    }

    @Test
    @WithMockUser(username = "adminUser@gmail.com", roles = { "ADMIN" })
    public void testDeleteExistingUserVisitorIsDataHashed() throws Exception {
        Visitor visitor = visitorRepository.findByEmail("visitorUser@gmail.com");
        this.mockMvc.perform(get("/admin/delete-user")
                .param("email", "visitorUser@gmail.com"))
                .andExpect(view().name("admin/delete-confirmation"));

        Visitor visitorAfterHash = visitorRepository.findById(visitor.getPersonId()).orElse(null);
        Assert.assertNotEquals(visitor.getCity(), visitorAfterHash.getCity());
        Assert.assertNotEquals(visitor.getFirstName(), visitorAfterHash.getFirstName());
        Assert.assertNotEquals(visitor.getLastName(), visitorAfterHash.getLastName());
        Assert.assertNotEquals(visitor.getPersonalNumber(), visitorAfterHash.getPersonalNumber());
        Assert.assertNotEquals(visitor.getStreet(), visitorAfterHash.getStreet());
        Assert.assertNotEquals(visitor.getPostalCode(), visitorAfterHash.getPostalCode());
        Assert.assertNotEquals(visitor.getPhone(), visitorAfterHash.getPhone());
        Assert.assertNotEquals(visitor.getEmail(), visitorAfterHash.getEmail());

    }



}
