package com.newtongroup.library;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AddObjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
        mockMvc = MockMvcBuilders.standaloneSetup(librarianController).build();
    }

    @Before
    public void init() {
        // Setting up authorities
        InitUtil.setupAuthorities(userAuthorityRepository);
        Librarian librarian = InitUtil.setupAndReturnLibrarian(userAuthorityRepository, librarianRepository, userRepository, "librarian@gmail.com");
    }
    @Test
    public void isItPossibleToSaveSeminar () throws Exception{
        Seminary seminary = new Seminary("Titel", "2020-08-08");

        mockMvc.perform(post("/new-object/save-seminar")
            .flashAttr("seminary", seminary)
            .param("title", String.valueOf(seminary.getTitle())))
            .andExpect(status().isOk())
            .andExpect(view().name("new-object/new-seminar"));

        Seminary savedSeminary=seminaryRepository.findByTitle("Titel");
//        assertEquals(savedSeminary.getOccurrence()).isEqualTo("2020-08-08");
        Assert.assertEquals(seminary, savedSeminary);

    }


//    @Mock
//    private SeminaryRepository seminaryRepository;
//
//    private AddObjectController addObjectController;
//
//
//    @Test
//    public void saveSeminarMethodTest (){
//        Seminary seminary=new Seminary("TestSeminary", "2020-09-10");
//        when(seminaryRepository.save(any(Seminary.class))).thenReturn(seminary);
//        Seminary saveSeminary= addObjectController.saveSeminar(seminary);
//        Assert.assertFalse(saveSeminary.getTitle().isEmpty());
//
//
//    }


//    @Test
//    public void testReturn (){
//        assertTrue(true);
//
//    }
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private LibrarianController librarianController;
//
//    @Autowired
//    private SeminaryRepository seminaryRepository;
//
//    @Autowired
//    private UserAuthorityRepository userAuthorityRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private LibrarianRepository librarianRepository;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(librarianController).build();
//    }
//    @Before
//    public void init() {
//        // Setting up authorities
//        InitUtil.setupAuthorities(userAuthorityRepository);
//        Librarian librarian = InitUtil.setupAndReturnLibrarian(userAuthorityRepository, librarianRepository, userRepository, "librarian@gmail.com");
//    }
//    @Test
//    @WithMockUser (username = "librarian@gmail.com", roles ={"LIBRARIAN"})
//    public void seminaryIsSavedWithCorrectInput () throws Exception{
//        Seminary seminary=new Seminary();
//        this.mockMvc.perform(get("/new-object/new-seminar")
//                    .param ("seminary", seminary)
////                    .flashAttr("book", book)
////                    .flashAttr("libraryCard", libraryCard)
////                    .param("bookId", String.valueOf(book.getId())))
//                    .andExpect(status().isOk())
//                    .andExpect(view().name("loan/loan-success"));

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


//    }







}