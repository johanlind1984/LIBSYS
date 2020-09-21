package com.newtongroup.library.Controller;


import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.InitUtil;
import com.newtongroup.library.Repository.AuthorRepository;
import com.newtongroup.library.Repository.LibrarianRepository;
import com.newtongroup.library.Repository.UserAuthorityRepository;
import com.newtongroup.library.Repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
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
    LibrarianRepository librarianRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorRepository authorRepository;

    /**
     * Build a mock webform
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

    }
    /*
    * Test if possible to save author twice
    * Test if author is saved
    * We found in authorController a header we did not need in the post method
    * */

    @Test
    @WithMockUser(username = "librarian@gmail.com", roles = {"LIBRARIAN"})
    public void isItPossibleToSaveAuthorTwice() throws Exception{
        Author author = new Author();

        final String firstName = "Astrid";
        final String lastName = "Lindgren";
        final String birthYear = "1945";
        final String urlTemplate = "/author/save-author";

        //Testing form against database
        this.mockMvc.perform(post(urlTemplate)
                .flashAttr("author", author)
                .param("firstname", firstName)
                .param("lastname", lastName)
                .param("birthYear", birthYear))
                .andExpect(view().name("redirect:/author/new"));

        //Test there are an author in database
        Assert.assertFalse("list is not empty.", authorRepository.findAll().isEmpty());

        //Testing adding another author. Redirected to error page
        this.mockMvc.perform(post(urlTemplate)
                .flashAttr("author", author)
                .param("firstname", firstName)
                .param("lastname", lastName)
                .param("birthYear", birthYear))
                .andExpect(view().name("error/author-already-exist"));

        List<Author> authorList = authorRepository.findAll();

        //Test there are not two authors in databas
        Assert.assertFalse(authorList.size()==2);

        //Test there is one author in database
        Assert.assertTrue(authorList.size()==1);
    }

}