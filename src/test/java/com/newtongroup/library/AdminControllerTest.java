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
        InitUtil.setupAuthorities(userAuthorityRepository);
        InitUtil.setUpAdmin(userAuthorityRepository, adminRepository, userRepository, "adminUser@gmail.com");
        InitUtil.setUpLibrarian(userAuthorityRepository, librarianRepository, userRepository, "librarianUser@gmail.com");
        InitUtil.setUpVisitor(userAuthorityRepository, visitorRepository, userRepository, "visitorUser@gmail.com");
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
        Visitor visitorBeforeHash = visitorRepository.findByEmail("visitorUser@gmail.com");
        this.mockMvc.perform(get("/admin/delete-user")
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