package com.newtongroup.library;


import com.newtongroup.library.Controller.AddObjectController;
import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.Seminary;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AddObjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddObjectController addObjectController;

    @Autowired
    private SeminaryRepository seminaryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private final Seminary seminary = new Seminary();
    private static final String saveSeminarTemplate = "/new-object/save-seminar";
    private static final String seminarTitle = "Title 1";
    private static final String information = "Information1";
    private static final String date = "2020-02-02";
    private static final String startTime = "12:00";
    private static final String endTime = "14:00";
    private static final String seminarExpectedView = "redirect:new-object/new-seminar";
    private static final Long firstIndex = 1L;

    private static final String bookTitle = "Pippi Långstrump";
    private static final String publisher = "publisher";
    private static final String purchaseprice = "100";
    private static final String isbn = "99999999999";
    private static final String bookTemplate = "/new-object/save-book";
    private static final String bookExpectedView = "redirect:new-object/new-book";

    private List<Author> authorList = new ArrayList<>();


    /**
     * Build a mock web-form
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(addObjectController).build();
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

        //setting up author
        Author author=InitUtil.setupAndReturnAuthor(authorRepository,  "Astrid", "Lindgren");
        Author author1=InitUtil.setupAndReturnAuthor(authorRepository, "Bengt", "Öste");
        authorList.add(author);
        authorList.add(author1);
    }

    /**
     * Test the form is functional and seminary is saved
     * Discovered a mistake in SeminaryRepository where seminary_id was String but Long is used in entity Seminary/now
     * changed in the code
     * Testing if librarian is allowed to save seminar
     */

    @Test
    @WithMockUser(username = "librarian@gmail.com", roles = {"LIBRARIAN"})
    public void doesTheWebFormSaveSeminarByLibrarian() throws Exception{

        //testing web form
        mockMvc.perform(post(saveSeminarTemplate)
            .flashAttr("seminary", seminary)
            .param("title", seminarTitle)
            .param("information", information)
            .param("occurrence", date)
            .param("starttime", startTime)
            .param("endtime", endTime))
            .andExpect(view().name(seminarExpectedView));

        //Getting seminar from database and control some output
        Seminary savedSeminary=seminaryRepository.findById(firstIndex).orElse(null);
        System.out.println("Expected seminar: "+ seminary.getTitle()+ " saved seminar: "+ savedSeminary.getTitle());
        System.out.println("Expected seminary id: "+seminary.getSeminary_id()+ " saved seminar id: "+savedSeminary.getSeminary_id());

        //Test case
        assertEquals(seminary.getSeminary_id(), savedSeminary.getSeminary_id());
    }

    /**
     * Testing if admin is allowed to save seminar
     *
     */
    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void doesTheWebFormSaveSeminarByAdmin() throws Exception{

        //testing web form
        mockMvc.perform(post(saveSeminarTemplate)
                .flashAttr("seminary", seminary)
                .param("title", seminarTitle)
                .param("information", information)
                .param("occurrence", date)
                .param("starttime", startTime)
                .param("endtime", endTime))
                .andExpect(view().name(seminarExpectedView));

        //Getting seminar from database and control some output
        Seminary savedSeminary=seminaryRepository.findById(firstIndex).orElse(null);
        System.out.println("Expected seminar: "+ seminary.getTitle()+ " saved seminar: "+ savedSeminary.getTitle());
        System.out.println("Expected seminary id: "+seminary.getSeminary_id()+ " saved seminar id: "+savedSeminary.getSeminary_id());

        //Test case
        assertEquals(seminary.getSeminary_id(), savedSeminary.getSeminary_id());
    }

    /**
     * Here is a bug in the code. We do not check for correct user in method for posting and saving seminar. Visitor
     * is permitted to save seminar but in html code there is no possibility for visitor to do so.
     *
     * Testing if visitor is permitted to save seminar in controller.
     */
    @Test
    @WithMockUser(username = "vistor@gmail.com", roles = {"VISITOR"})
    public void doesTheWebFormSaveSeminarByVisitor() throws Exception{
        Seminary seminary = new Seminary();

        //testing web form
        mockMvc.perform(post(saveSeminarTemplate)
                .flashAttr("seminary", seminary)
                .param("title", seminarTitle)
                .param("information", information)
                .param("occurrence", date)
                .param("starttime", startTime)
                .param("endtime", endTime))
                .andExpect(status().isForbidden());
    }

    /*
    * Test if seminar with null values can be saved
    * */
    @Test
    @WithMockUser(username = "librarian@gmail.com", roles = {"LIBRARIAN"})
    public void theWebFormDoesNotSaveSeminarWithoutValues() throws Exception {
        Seminary seminary = new Seminary();
        seminary.setTitle(null);
        seminary.setInformation(null);
        seminary.setOccurrence(null);
        seminary.setStartTime(null);
        seminary.setEndTime(null);


        mockMvc.perform(post(saveSeminarTemplate)
                .flashAttr("seminary", seminary)
                .param("title", seminary.getTitle())
                .param("information", seminary.getInformation())
                .param("occurrence", seminary.getOccurrence())
                .param("starttime", seminary.getStartTime())
                .param("endtime", seminary.getEndTime()))
                .andExpect(status().isBadRequest());


        assertFalse(seminaryRepository.existsById(firstIndex));
        assertTrue(seminaryRepository.findById(firstIndex).isEmpty());
    }

    /**
     * Test the form is functional and book is saved by librarian
     */
    @Test
    @WithMockUser(username = "librarian@gmail.com", roles = {"LIBRARIAN"})
    public void doesTheWebFormSaveBookByLibrarian() throws Exception {
        Book book = new Book();
        book.setAuthorList(authorList);

        //testing web form
        mockMvc.perform(post(bookTemplate)
                .flashAttr("book", book)
                .param("title", bookTitle)
                .param("description", information)
                .param("publisher", publisher)
                .param("purchaseprice", purchaseprice)
                .param("isbn", isbn))
                .andExpect(view().name(bookExpectedView));

        //Getting book from database
        Book savedBook = bookRepository.findById(firstIndex).orElse(null);
        List<Author> savedBookAuthorList = savedBook.getAuthorList();

        //Test cases
        assertFalse(book.isBookTitleThisTitle(book, "Pappa Långstrump"));
        assertTrue(book.isBookTitleThisTitle(book, bookTitle));
        assertEquals(book.getId(), savedBook.getId());
        assertFalse(savedBook.getAuthorList().isEmpty());


        //Extra control of first index in list
        for(Author authors:savedBookAuthorList){
            System.out.println(authors.getAuthorId()+" "+authors.getFirstname()+ " " +authors.getLastname());
        }
        for (Author originalAuthors:authorList){
            System.out.println(originalAuthors.getAuthorId()+" "+originalAuthors.getFirstname()+" "+originalAuthors.getLastname());
        }

    }

    /**
     * Test the form is functional and book is saved by Admin
     */
    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void doesTheWebFormSaveBookByAdmin() throws Exception {
        Book book = new Book();
        book.setAuthorList(authorList);

        //testing web form
        mockMvc.perform(post(bookTemplate)
                .flashAttr("book", book)
                .param("title", bookTitle)
                .param("description", information)
                .param("publisher", publisher)
                .param("purchaseprice", purchaseprice)
                .param("isbn", isbn))
                .andExpect(view().name(bookExpectedView));

        //Getting book from database
        Book savedBook = bookRepository.findById(firstIndex).orElse(null);

        //Test cases
        assertEquals(book.getId(), savedBook.getId());
        assertFalse(savedBook.getAuthorList().isEmpty());
    }

    /**
     * Test the form is functional and book is saved by Visitor
     */
    @Test
    @WithMockUser(username = "visitor@gmail.com", roles = {"VISITOR"})
    public void doesTheWebFormSaveBookByVisitor() throws Exception {
        Book book = new Book();
        book.setAuthorList(authorList);

        //testing web form
        mockMvc.perform(post(bookTemplate)
                .flashAttr("book", book)
                .param("title", bookTitle)
                .param("description", information)
                .param("publisher", publisher)
                .param("purchaseprice", purchaseprice)
                .param("isbn", isbn))
                .andExpect(status().isForbidden());
    }

    /*
    * Test if a book with null values can be saved
    * */
    @Test
    @WithMockUser(username = "librarian@gmail.com", roles = {"LIBRARIAN"})
    public void theWebFormDoesNotSaveBookWithoutValues() throws Exception {
        Book book = new Book();
        book.setTitle(null);
        book.setDescription(null);
        book.setIsbn(null);
        book.setPublisher(null);
        book.setAuthorList(new ArrayList<>());
        book.setPurchasePrice(null);

        mockMvc.perform(post(bookTemplate)
                .flashAttr("book", book))
                .andExpect(status().isBadRequest());

        assertFalse(seminaryRepository.existsById(firstIndex));
        assertTrue(seminaryRepository.findById(firstIndex).isEmpty());
    }

}