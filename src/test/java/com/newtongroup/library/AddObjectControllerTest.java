package com.newtongroup.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newtongroup.library.Controller.AddObjectController;
import com.newtongroup.library.Controller.LibrarianController;
import com.newtongroup.library.Entity.Librarian;
import com.newtongroup.library.Entity.Seminary;
import com.newtongroup.library.Repository.LibrarianRepository;
import com.newtongroup.library.Repository.SeminaryRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AddObjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddObjectController addObjectController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SeminaryRepository seminaryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private LibrarianController librarianController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(addObjectController).build();
    }

    @Before
    public void init() {
        // Setting up authorities
        InitUtil.setupAuthorities(userAuthorityRepository);
        Librarian librarian = InitUtil.setupAndReturnLibrarian(userAuthorityRepository, librarianRepository, userRepository, "librarian@gmail.com");
    }
    @Test
    @WithMockUser(username = "librarian@gmail.com", roles = {"LIBRARIAN"})
    public void isItPossibleToSaveSeminar () throws Exception{
        Seminary seminary = new Seminary();

        mockMvc.perform(post("/new-object/save-seminar")
            .flashAttr("seminary", seminary)
            .param("title", "Title 1")
            .param("information", "Information1"))
            .andExpect(view().name("redirect:new-object/new-seminar"));

        Seminary savedSeminary=seminaryRepository.findById(seminary.getSeminary_id()).orElse(null);
      //  assertEquals(savedSeminary.getOccurrence()).isEqualTo("2020-08-08");
        System.out.println("Expected seminar "+ seminary.getTitle()+" : "+ "savedseminar "+ savedSeminary.getTitle() );
        System.out.println("Expected seminary id "+seminary.getSeminary_id()+" : "+ "savedseminar id "+savedSeminary.getSeminary_id());
        Assert.assertEquals(seminary.getSeminary_id(), savedSeminary.getSeminary_id());

    }










}