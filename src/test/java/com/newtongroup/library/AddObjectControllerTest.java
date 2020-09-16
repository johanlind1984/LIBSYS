package com.newtongroup.library;

import com.newtongroup.library.Controller.AddObjectController;
import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.Seminary;
import com.newtongroup.library.Repository.*;
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

import java.util.ArrayList;
import java.util.List;

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
    private SeminaryRepository seminaryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;



    /**
     * Build a mock webform
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
    }

    /**
     * Test the form is functional and seminary is saved
     * Discovered a mistake in SeminaryRepository where seminary_id was String but Long is used in entity Seminary
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "librarian@gmail.com", roles = {"LIBRARIAN"})
    public void doesTheWebFormSaveSeminar() throws Exception{
        Seminary seminary = new Seminary();

        mockMvc.perform(post("/new-object/save-seminar")
            .flashAttr("seminary", seminary)
            .param("title", "Title 1")
            .param("information", "Information1"))
            .andExpect(view().name("redirect:new-object/new-seminar"));

        Seminary savedSeminary=seminaryRepository.findById(1L).orElse(null);
        System.out.println("Expected seminar "+ seminary.getTitle()+" : "+ "savedseminar "+ savedSeminary.getTitle() );
        System.out.println("Expected seminary id "+seminary.getSeminary_id()+" : "+ "savedseminar id "+savedSeminary.getSeminary_id());
        System.out.println("information: "+seminary.getInformation());
        Assert.assertEquals(seminary.getSeminary_id(), savedSeminary.getSeminary_id());
    }
    /**
     * Test the form is functional and book is saved
     */
    @Test
    @WithMockUser(username = "librarian@gmail.com", roles = {"LIBRARIAN"})
    public void doesTheWebFormSaveBook() throws Exception {
        Book book = new Book();
        List<Author> authorList=new ArrayList<>();
        Author author=InitUtil.setupAndReturnAuthor(authorRepository,  "Astrid", "Lindgren");
        authorList.add(author);
        book.setAuthorList(authorList);

        mockMvc.perform(post("/new-object/save-book")
                .flashAttr("book", book)
                .param("title", "Pippi Långstrump"))
                .andExpect(view().name("redirect:new-object/new-book"));

        Book savedBook = bookRepository.findById(1L).orElse(null);
        Assert.assertFalse(book.isBookTitleThisTitle(book, "Pappa Långstrum"));
        Assert.assertTrue(book.isBookTitleThisTitle(book, "Pippi Långstrump"));
        Assert.assertEquals(book.getId(), savedBook.getId());

        Assert.assertFalse(savedBook.getAuthorList().isEmpty());


    }


    /**
     * Test the form is functional and book is saved
     */
//    @Test
//    @WithMockUser(username = "librarian@gmail.com", roles = {"LIBRARIAN"})
//    public void doesTheWebFormSaveBook() throws Exception {
//        Book book = new Book();
////        Author Astrid = new Author();
////        Author Bengt = new Author();
//        List<Author> authorList=new ArrayList<>();
////        authorList.add(Astrid);
////        authorList.add(Bengt);
//        Author author=InitUtil.setupAndReturnAuthor(authorRepository,  "Astrid", "Lindgren");
////        authorList.add(author);
////        book.setAuthorList(authorList);
//
//        mockMvc.perform(post("/new-object/save-book")
//                .flashAttr("book", book)
//                .param("authors", String.valueOf(author))
//                .param("title", "Pippi Långstrump"))
//                .andExpect(view().name("redirect:new-object/new-book"));
//
//        Book savedBook = bookRepository.findById(1L).orElse(null);
//        Assert.assertFalse(book.isBookTitleThisTitle(book, "Pappa Långstrum"));
//        Assert.assertTrue(book.isBookTitleThisTitle(book, "Pippi Långstrump"));
//        Assert.assertEquals(book.getId(), savedBook.getId());
//
//        //System.out.println("expected boolist: "+book.getAuthorList()+"saved Booklist"+savedBook.getAuthorList());
//        Assert.assertFalse(savedBook.getAuthorList().isEmpty());
//
//
//    }

}