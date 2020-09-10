package com.newtongroup.library;


import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import com.newtongroup.library.Entity.Authority;
import com.newtongroup.library.Entity.User;
import com.newtongroup.library.Repository.AdminRepository;
import com.newtongroup.library.Repository.UserAuthorityRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Wrapper.UserPerson;


public class InitUtil {

    public static void setupAuthorities(UserAuthorityRepository userAuthorityRepository) {
        Authority adminAuth = new Authority();
        adminAuth.setAuthorityName("ROLE_ADMIN");
        Authority librarianAuth = new Authority();
        librarianAuth.setAuthorityName("ROLE_LIBRARIAN");
        Authority visitorAuth = new Authority();
        visitorAuth.setAuthorityName("ROLE_VISITOR");
        userAuthorityRepository.save(adminAuth);
        userAuthorityRepository.save(librarianAuth);
        userAuthorityRepository.save(visitorAuth);
    }

    public static Admin setUpAdmin(UserAuthorityRepository userAuthorityRepository,
                                   AdminRepository adminRepository, UserRepository userRepository, String email) {

        userRepository.save(InitUtil.setupAndReturnUser(userAuthorityRepository.findById((long) 1).orElse(null), email));
        return adminRepository.save(initAndGetAdmin(email));
    }

    public static Librarian setUpLibrarian(UserAuthorityRepository userAuthorityRepository,
                                           LibrarianRepository librarianRepository, UserRepository userRepository, String email) {

        userRepository.save(InitUtil.setupAndReturnUser(userAuthorityRepository.findById((long) 2).orElse(null), email));
        return librarianRepository.save(initAndGetLibrarian(email));
    }

    public static Visitor setUpVisitor(UserAuthorityRepository userAuthorityRepository, VisitorRepository visitorRepository,
                                       UserRepository userRepository, String email) {

        userRepository.save(InitUtil.setupAndReturnUser(userAuthorityRepository.findById((long) 3).orElse(null), email));
        return visitorRepository.save(initAndGetVisitor(email));
    }


    public static User setupAndReturnUser(Authority authority, String email) {
        User user = new User();
        user.setUsername(email);
        user.setPassword("test");
        user.setAuthority(authority);
        user.setEnabled(true);
        return user;
    }

    private static Admin initAndGetAdmin(String email) {
        UserPerson userPerson = getPersonDummyData(email);
        userPerson.setPersonAsAdmin();
        return userPerson.getAdmin();
    }

    private static Librarian initAndGetLibrarian(String email) {
        UserPerson userPerson = getPersonDummyData(email);
        userPerson.setPersonAsLibrarian();
        return userPerson.getLibrarian();
    }

    private static Visitor initAndGetVisitor(String email) {
        UserPerson userPerson = getPersonDummyData(email);
        userPerson.setPersonAsVisitor();
        return userPerson.getVisitor();
    }

    // EJ KLAR
    public static void initVisitorRentedBook(VisitorRepository visitorRepository, String visitorEmail) {
        Visitor visitorRentedBook = new Visitor();
        visitorRentedBook.setEmail("visitorUserLoan@gmail.com");
        visitorRentedBook.setCity("Stockholm");
        visitorRentedBook.setFirstName("Gunnar");
        visitorRentedBook.setLastName("Pettersson");
        visitorRentedBook.setPhone("085000000");
        visitorRentedBook.setPostalCode("173 53");
        visitorRentedBook.setStreet("Gökvägen 12");
        visitorRentedBook.setPersonalNumber("831021-3341");
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setActive(true);
        libraryCard.setVisitor(visitorRentedBook);
        libraryCard.setBookLoans(new ArrayList<>());
        libraryCard.setEbookLoans(new ArrayList<>());
        ArrayList<LibraryCard> libraryCards = new ArrayList<>();
        libraryCards.add(libraryCard);
        visitorRentedBook.setLibraryCards(libraryCards);
        visitorRepository.save(visitorRentedBook);
    }

    public static void initAuthorBookAndLoan(AuthorRepository authorRepository, BookRepository bookRepository, BookLoanRepository bookLoanRepository) {
        Author author1 = new Author();
        author1.setFirstname("Janne");
        author1.setLastname("Josefsson");
        author1.setBirthYear("1956");
        author1.setBookList(new ArrayList<>());
        authorRepository.save(author1);

        Book book1 = new Book();
        book1.setTitle("Testbok 1");
        book1.setPurchasePrice("200");
        book1.setPublisher("Testförlaget");
        book1.setIsbn("12132131332113");
        book1.setDescription("Detta är en testbok, inget annat");
        book1.setAvailable(true);
        book1.setLoanedBooks(new ArrayList<>());
        ArrayList<Author> authors = new ArrayList<>();
        authors.add(author1);
        book1.setAuthorList(authors);
        bookRepository.save(book1);

        Book rentedBook = new Book();
        rentedBook.setTitle("Testbok 1");
        rentedBook.setPurchasePrice("200");
        rentedBook.setPublisher("Testförlaget");
        rentedBook.setIsbn("12132131332113");
        rentedBook.setDescription("Detta är en testbok, inget annat");
        rentedBook.setAvailable(false);
        rentedBook.setLoanedBooks(new ArrayList<>());
        authors.add(author1);
        rentedBook.setAuthorList(authors);

        BookLoan bookLoan = new BookLoan();
        bookLoan.setBookReturned(false);
        bookLoan.setBook(rentedBook);

        /* Får se över denna del av koden.
        bookLoan.setLibraryCard(libraryCard);
         */

        bookLoan.setDateLoanEnd(new Date(Calendar.getInstance().getTime().getTime()));
        bookLoan.setDateLoanStart(new Date(Calendar.getInstance().getTime().getTime()));
        rentedBook.getLoanedBooks().add(bookLoan);
        bookRepository.save(rentedBook);
        bookLoanRepository.save(bookLoan);

    }

    private static UserPerson getPersonDummyData(String email) {
        UserPerson userPerson = new UserPerson();
        userPerson.setUser(new User());
        userPerson.getUser().setUsername(email);
        Person person = new Person();
        person.setEmail(email);
        person.setCity("Stockholm");
        person.setFirstName("Gunnar");
        person.setLastName("Pettersson");
        person.setPhone("085000000");
        person.setPostalCode("173 53");
        person.setStreet("Gökvägen 12");
        person.setPersonalNumber("831021-3341");
        userPerson.setPerson(person);
        return userPerson;
    }
}


