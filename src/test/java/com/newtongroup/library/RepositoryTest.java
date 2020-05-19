package com.newtongroup.library;

import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.Placement;
import com.newtongroup.library.Entity.User;
import com.newtongroup.library.Repository.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private PlacementRepository placementRepository;

    @Test
    public void testUserRepository() {
        User user = new User();
        user.setUsername("pelle@pelle.se");
        user.setPassword("test");
        user.setEnabled(true);
        user.setAuthority(userAuthorityRepository.findById((long) 3).orElse(null));

        entityManager.persist(user);
        entityManager.flush();

        User userToTest = userRepository.findByUsername("pelle@pelle.se");

        assertThat(userToTest.getAuthority()).isEqualTo(user.getAuthority());
        assertThat(userToTest.getPassword()).isEqualTo(user.getPassword());
        assertThat(userToTest.getUsername()).isEqualTo(user.getUsername());
        assertThat(userToTest.isEnabled()).isEqualTo(user.getAuthority());

    }

    @Test
    public void testBookAuthorPlacementLibraryCardBookLoanRepository() {
        Visitor visitor = new Visitor();
        Author author = new Author();
        Book book = new Book();
        Placement placement = new Placement();
        BookLoan bookLoan = new BookLoan();
        LibraryCard libraryCard = new LibraryCard();

        author.setFirstname("Aysen");
        author.setLastname("Furhoff");

        placement.setDdk("1001");
        placement.setTitle("Programmering");

        book.setAvailable(true);
        book.setAuthorList(new ArrayList<>());
        book.getAuthorList().add(author);
        book.setPlacement(placement);
        book.setDescription("Test");
        book.setIsbn("9999");
        book.setPublisher("Testbolaget");
        book.setPurchasePrice("2000");
        book.setTitle("Master Java With Aysen Furhoff");


        libraryCard.setActive(true);
        ArrayList libraryCards = new ArrayList();
        libraryCards.add(libraryCard);


        visitor.setEmail("palle@palle.se");
        visitor.setPersonalNumber("0000");
        visitor.setPhone("1234");
        visitor.setCity("Hofors");
        visitor.setPostalCode("12345");
        visitor.setStreet("Hellostreet1");
        visitor.setLastName("Palle");
        visitor.setLastName("Pallesson");

        bookLoan.setBookReturned(true);
        bookLoan.setBook(book);
        Calendar cal = Calendar.getInstance();
        bookLoan.setDateLoanStart(new Date(Calendar.getInstance().getTime().getTime()));
        cal.add(Calendar.MONTH, 1);
        bookLoan.setDateLoanEnd(new Date(cal.getTime().getTime()));
        bookLoan.setLibraryCard(libraryCard);

        ArrayList<BookLoan> bookLoans = new ArrayList<>();
        bookLoans.add(bookLoan);
        libraryCard.setBookLoans(bookLoans);
        visitor.setLibraryCards(libraryCards);

        entityManager.persist(author);
        entityManager.persist(placement);
        entityManager.persist(book);
        entityManager.persist(visitor);
        entityManager.persist(bookLoan);
        entityManager.flush();

        Book bookToTest = bookRepository.findById((long) 1).orElse(null);
        Visitor visitorToTest = visitorRepository.findById("palle@palle.se").orElse(null);
        BookLoan bookLoanToTest = bookLoanRepository.findById((long) 1).orElse(null);

        assertThat(visitorToTest.getCity()).isEqualTo(visitor.getCity());
        assertThat(visitorToTest.getFirstName()).isEqualTo(visitor.getFirstName());
        assertThat(visitorToTest.getLastName()).isEqualTo(visitor.getLastName());
        assertThat(visitorToTest.getPersonalNumber()).isEqualTo(visitor.getPersonalNumber());
        assertThat(visitorToTest.getPhone()).isEqualTo(visitor.getPhone());
        assertThat(visitorToTest.getPostalCode()).isEqualTo(visitor.getPostalCode());
        assertThat(visitorToTest.getStreet()).isEqualTo(visitor.getStreet());
        assertThat(visitorToTest.getEmail()).isEqualTo(visitor.getEmail());
        assertThat(bookLoanToTest.getBook()).isEqualTo(bookLoan.getBook());
        assertThat(bookLoanToTest.getDateLoanStart()).isEqualTo(bookLoan.getDateLoanStart());
        assertThat(bookLoanToTest.getDateLoanEnd()).isEqualTo(bookLoan.getDateLoanEnd());
        assertThat(bookLoanToTest.getBookReturned()).isEqualTo(bookLoan.getBookReturned());
        assertThat(bookLoanToTest.getLibraryCard().getLibraryCardNumber()).isEqualTo(bookLoan.getLibraryCard().getLibraryCardNumber());
        assertThat(bookToTest.isAvailable()).isEqualTo(book.isAvailable());
        assertThat(bookToTest.getAuthorList()).isEqualTo(book.getAuthorList());
        assertThat(bookToTest.getPlacement()).isEqualTo(book.getPlacement());
        assertThat(bookToTest.getDescription()).isEqualTo(book.getDescription());
        assertThat(bookToTest.getIsbn()).isEqualTo(book.getIsbn());
        assertThat(bookToTest.getPublisher()).isEqualTo(book.getPublisher());
        assertThat(bookToTest.getPurchasePrice()).isEqualTo(book.getPurchasePrice());
        assertThat(bookToTest.getTitle()).isEqualTo(book.getTitle());
    }
}
