package com.newtongroup.library;

import com.newtongroup.library.Controller.LibrarianController;
import com.newtongroup.library.Entity.Librarian;
import com.newtongroup.library.Entity.Seminary;
import com.newtongroup.library.Repository.LibrarianRepository;
import com.newtongroup.library.Repository.SeminaryRepository;
import com.newtongroup.library.Repository.UserAuthorityRepository;
import com.newtongroup.library.Repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AddObjectControllerTest {

    @Test
    public void testReturn (){
        assertTrue(true);

    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LibrarianController librarianController;

    @Autowired
    private SeminaryRepository seminaryRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(librarianController).build();
    }
    @Before
    public void init() {
        // Setting up authorities
        InitUtil.setupAuthorities(userAuthorityRepository);
        Librarian librarian = InitUtil.setupAndReturnLibrarian(userAuthorityRepository, librarianRepository, userRepository, "librarian@gmail.com");
    }
    @Test
    @WithMockUser (username = "librarian@gmail.com", roles ={"LIBRARIAN"})
    public void seminaryIsSavedWithCorrectInput () throws Exception{
        Seminary seminary=new Seminary();
        this.mockMvc.perform(get("/new-object/new-seminar")
                    .param ("seminary", seminary)
//                    .flashAttr("book", book)
//                    .flashAttr("libraryCard", libraryCard)
//                    .param("bookId", String.valueOf(book.getId())))
                    .andExpect(status().isOk())
                    .andExpect(view().name("loan/loan-success"));

//
//        public void testBorrowBookAsVisitor() throws Exception {
//            Book book = bookRepository.findById((long) 1).orElse(null);
//            LibraryCard libraryCard = libraryCardRepository.findById((long) 1).orElse(null);
//            this.mockMvc.perform(get("/loan/register-loan")
//                    .flashAttr("book", book)
//                    .flashAttr("libraryCard", libraryCard)
//                    .param("bookId", String.valueOf(book.getId())))
//                    .andExpect(status().isOk())
//                    .andExpect(view().name("loan/loan-success"));
//
//            Visitor visitor = visitorRepository.findByEmail("visitorUserLoan@gmail.com");
//            Assert.assertFalse(visitor.getActiveLibraryCard().getBookLoans().isEmpty());
//            Assert.assertFalse(visitor.getActiveLibraryCard().getBookLoans().get(0).getBookReturned());
//            Assert.assertFalse(visitor.getActiveLibraryCard().getBookLoans().get(0).getBook().isAvailable());
//        }


    }







}