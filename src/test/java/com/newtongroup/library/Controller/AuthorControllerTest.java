package com.newtongroup.library.Controller;


import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.InitUtil;
import com.newtongroup.library.Repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthorController authorController;

    @Autowired
    UserAuthorityRepository userAuthorityRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    LibrarianRepository librarianRepository;

    @Autowired
    VisitorRepository visitorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorRepository authorRepository;

    private Author author = new Author();
    private static final String firstName = "Astrid";
    private static final String lastName = "Lindgren";
    private static final String birthYear = "1945";
    private static final String urlTemplate = "/author/save-author";
    private static final String expectedView = "redirect:/author/new";

    /**
     * Build a mock web-form
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    /**
     *Initialize authorities to be able to use the form
     */
    @Before
    public void init() {
        InitUtil.setupAuthorities(userAuthorityRepository);
        InitUtil.setupAndReturnLibrarian(userAuthorityRepository, librarianRepository, userRepository, "librarian@gmail.com");
        InitUtil.setupAndReturnAdmin(userAuthorityRepository, adminRepository, userRepository, "admin@gmail.com");
        InitUtil.setupAndReturnVisitor(userAuthorityRepository, visitorRepository, userRepository, "visitor@gmail.com");
    }
    /*
    * Test if author is saved by Admin
    * Test if possible to save author twice
    * Test if author is saved by librarian
    * Test if author is saved by visitor
    * Test if author is saved with null values
    * */

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void isItPossibleToSaveAuthorByAdmin() throws Exception {

        this.mockMvc.perform(post(urlTemplate)
                .flashAttr("author", author)
                .param("firstname", firstName)
                .param("lastname", lastName)
                .param("birthYear", birthYear))
                .andExpect(view().name(expectedView));

        //Test there are an author in database
        assertFalse("list is not empty.", authorRepository.findAll().isEmpty());
    }

    @Test
    @WithMockUser(username = "librarian@gmail.com", roles = {"LIBRARIAN"})
    public void isItPossibleToSaveAuthorTwice() throws Exception{

        //Testing form against database
        this.mockMvc.perform(post(urlTemplate)
                .flashAttr("author", author)
                .param("firstname", firstName)
                .param("lastname", lastName)
                .param("birthYear", birthYear))
                .andExpect(view().name(expectedView));

        //Test there are an author in database
        assertFalse("list is not empty.", authorRepository.findAll().isEmpty());

        //Testing adding another author. Redirected to error page
        this.mockMvc.perform(post(urlTemplate)
                .flashAttr("author", author)
                .param("firstname", firstName)
                .param("lastname", lastName)
                .param("birthYear", birthYear))
                .andExpect(view().name("error/author-already-exist"));

        List<Author> authorList = authorRepository.findAll();

        //Test there are not two authors in database
        assertNotEquals(2, authorList.size());

        //Test there is one author in database
        assertEquals(1, authorList.size());
    }

    @Test
    @WithMockUser(username = "visitor@gmail.com", roles = {"VISITOR"})
    public void isItPossibleToSaveAuthorByVisitor() throws Exception {

        this.mockMvc.perform(post(urlTemplate)
                .flashAttr("author", author)
                .param("firstname", firstName)
                .param("lastname", lastName)
                .param("birthYear", birthYear))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "librarian@gmail.com", roles = {"LIBRARIAN"})
    public void isItPossibleToSaveAuthorWithoutValues() throws Exception{
        author.setFirstname(null);
        author.setLastname(null);
        author.setBirthYear(null);

        mockMvc.perform(post(urlTemplate)
        .flashAttr("author", author)
        .param("firstname", author.getFirstname())
        .param("lastname", author.getLastname())
        .param("birthYear", author.getBirthYear()))
        .andExpect(status().isBadRequest());

        //test case
        assertFalse(authorRepository.existsById(1));
    }

}