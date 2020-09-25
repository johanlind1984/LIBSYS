package com.newtongroup.library;



import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RemoveObjectControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PlacementRepository placementRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RemovedBookRepository removedBookRepository;

    @Autowired
    private SeminaryRepository seminaryRepository;

    @Autowired
    private RemovedSeminaryRepository removedSeminaryRepository;





    @Before
    public void init() {


        InitUtil.setupAuthorities(userAuthorityRepository);
        InitUtil.setupAndReturnAdmin(userAuthorityRepository, adminRepository, userRepository, "adminUser@gmail.com");
        InitUtil.setupAndReturnLibrarian(userAuthorityRepository, librarianRepository, userRepository, "librarianUser@gmail.com");
        InitUtil.setupAndReturnVisitor(userAuthorityRepository, visitorRepository, userRepository, "visitorUser@gmail.com");


        Author author = InitUtil.setupAndReturnAuthor(authorRepository, "Peter", "LeMarc");
        Placement placement = InitUtil.setUpAndReturnPlacement(placementRepository);
        Book book = InitUtil.setupAndReturnBook(bookRepository, author,placement, "Sagan om ringen");
        InitUtil.setupAndReturnRemovedBook(removedBookRepository,book, "damage");
        Seminary seminary = InitUtil.setupAndReturnSeminary(seminaryRepository,(long)1);
        InitUtil.setupAndReturnRemovedSeminary(removedSeminaryRepository,seminary,"Trash");

    }




    @Test
    @WithMockUser(username = "visitorUser@gmail.com", roles = { "VISITOR" })
    public void testDeleteBookAsVisitor() throws Exception {
        Book book = bookRepository.findById((long) 1).orElse(null);
        RemovedBook removedBook = removedBookRepository.findById((long) 1).orElse(null);
        this.mockMvc.perform(get("/remove-object/delete-book")
                .flashAttr("book", book)
                .flashAttr("removedBook", removedBook))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "librarianUser@gmail.com", roles = { "LIBRARIAN" })
    public void testDeleteBookAsLibrarian() throws Exception {

        Book book = bookRepository.findById((long) 1).orElse(null);
        RemovedBook removedBook = removedBookRepository.findById((long) 1).orElse(null);
        this.mockMvc.perform(get("/remove-object/delete-book")
                .flashAttr("book", book)
                .flashAttr("removedBook", removedBook))
                .andExpect(status().isOk())
                .andExpect(view().name("remove-objects/remove-book-confirmation"));

          assertThat(isRemovedBookAndBookPropertiesEqual(removedBook,book)).isEqualTo(true);


    }

    @Test
    @WithMockUser(username = "adminUser@gmail.com", roles = { "ADMIN" })
    public void testDeleteBookAsAdmin() throws Exception {

        Book book = bookRepository.findById((long) 1).orElse(null);
        RemovedBook removedBook = removedBookRepository.findById((long) 1).orElse(null);
        this.mockMvc.perform(get("/remove-object/delete-book")
                .flashAttr("book", book)
                .flashAttr("removedBook", removedBook))
                .andExpect(status().isOk())
                .andExpect(view().name("remove-objects/remove-book-confirmation"));
        assertThat(isRemovedBookAndBookPropertiesEqual(removedBook,book)).isEqualTo(true);

    }

    @Test
    @WithMockUser(username = "visitorUser@gmail.com", roles = { "VISITOR" })
    public void testDeleteSeminaryAsVisitor() throws Exception {
        Seminary seminary = seminaryRepository.findById((long) 1).orElse(null);
        RemovedSeminary removedSeminary = removedSeminaryRepository.findById((long) 1).orElse(null);
        this.mockMvc.perform(get("/remove-object/delete-seminary")
                .flashAttr("book", seminary)
                .flashAttr("removedBook", removedSeminary))
                .andExpect(status().isForbidden());


    }

    @Test
    @WithMockUser(username = "librarianUser@gmail.com", roles = { "LIBRARIAN" })
    public void testDeleteSeminaryAsLibrarian() throws Exception {
        Seminary seminary = seminaryRepository.findById((long) 1).orElse(null);
        RemovedSeminary removedSeminary = removedSeminaryRepository.findById((long) 1).orElse(null);
        this.mockMvc.perform(get("/remove-object/delete-seminary")
                .flashAttr("seminary", seminary)
                .flashAttr("removedSeminary", removedSeminary))
                .andExpect(status().isOk());

        assertThat(isRemovedSeminaryAndSeminaryPropertiesEqual(removedSeminary,seminary)).isEqualTo(true);
    }

    @Test
    @WithMockUser(username = "adminUser@gmail.com", roles = { "ADMIN" })
    public void testDeleteSeminaryAsAdmin() throws Exception {
        Seminary seminary = seminaryRepository.findById((long) 1).orElse(null);
        RemovedSeminary removedSeminary = removedSeminaryRepository.findById((long) 1).orElse(null);
        this.mockMvc.perform(get("/remove-object/delete-seminary")
                .flashAttr("seminary", seminary)
                .flashAttr("removedSeminary", removedSeminary))
                .andExpect(status().isOk());

        assertThat(isRemovedSeminaryAndSeminaryPropertiesEqual(removedSeminary,seminary)).isEqualTo(true);
    }

    @Test
    @WithMockUser(username = "visitorUser@gmail.com", roles = {" VISITOR "})
    public void testDeleteAuthorAsVisitor() throws Exception{
        this.mockMvc.perform(get("/remove-object/delete-author")).andExpect(status().isForbidden());


    }

    @Test
    @WithMockUser(username = "librarianUser@gmail.com", roles = { "LIBRARIAN" })
    public void testDeleteAuthorAsLibrarian() throws Exception {
        Author author = authorRepository.findById( 1).orElse(null);

        this.mockMvc.perform(get("/remove-object/delete-author")
                .flashAttr("author", author))
                .andExpect(status().isOk());


         assertThat(author.getBookList().isEmpty()).isEqualTo(false);


    }

    @Test
    @WithMockUser(username = "adminUser@gmail.com", roles = { "ADMIN" })
    public void testDeleteAuthorAsAdmin() throws Exception {
        Author author = authorRepository.findById( 1).orElse(null);

        this.mockMvc.perform(get("/remove-object/delete-author")
                .flashAttr("author", author))
                .andExpect(status().isOk());


        assertThat(author.getBookList().isEmpty()).isEqualTo(false);


    }
    private boolean isRemovedBookAndBookPropertiesEqual(RemovedBook removedBook, Book book){

        return removedBook.getTitle() == book.getTitle() && removedBook.getBook_id() == book.getId() &&
                removedBook.getDescription() == book.getDescription() && removedBook.getIsbn() == book.getIsbn() &&
                removedBook.getPlacement_id() == book.getPlacement().getPlacementId() && removedBook.getPrice() ==
                book.getPurchasePrice() && removedBook.getPublisher() == book.getPublisher();
    }

    private boolean isRemovedSeminaryAndSeminaryPropertiesEqual(RemovedSeminary removedSeminary, Seminary seminary){

        return removedSeminary.getSeminary_id() == seminary.getSeminary_id() && removedSeminary.getInformation()
                == seminary.getInformation() && removedSeminary.getOccurrence() == seminary.getOccurrence() &&
                removedSeminary.getStarttime() == seminary.getStartTime() && removedSeminary.getEndtime() ==
                seminary.getEndTime() && removedSeminary.getTitle() == seminary.getTitle();
    }





}
