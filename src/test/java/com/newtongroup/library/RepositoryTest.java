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

import java.util.ArrayList;

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
    public void testBookAuthorAndPlacementRepository() {
        Author author = new Author();
        author.setFirstname("Aysen");
        author.setLastname("Furhoff");
        Book book = new Book();
        Placement placement = new Placement();

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

        entityManager.persist(author);
        entityManager.persist(placement);
        entityManager.persist(book);
        entityManager.flush();

        Book bookToTest = bookRepository.findById((long) 1).orElse(null);

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
