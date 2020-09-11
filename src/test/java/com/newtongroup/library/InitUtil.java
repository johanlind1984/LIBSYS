package com.newtongroup.library;


import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.*;
import com.newtongroup.library.Wrapper.UserPerson;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;


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

    public static Admin setupAndReturnAdmin(UserAuthorityRepository userAuthorityRepository,
                                            AdminRepository adminRepository, UserRepository userRepository, String email) {

        userRepository.save(InitUtil.setupAndReturnUser(userAuthorityRepository.findById((long) 1).orElse(null), email));
        return adminRepository.save(initAndGetAdmin(email));
    }

    public static Librarian setupAndReturnLibrarian(UserAuthorityRepository userAuthorityRepository,
                                                    LibrarianRepository librarianRepository, UserRepository userRepository, String email) {

        userRepository.save(InitUtil.setupAndReturnUser(userAuthorityRepository.findById((long) 2).orElse(null), email));
        return librarianRepository.save(initAndGetLibrarian(email));
    }

    public static Visitor setupAndReturnVisitor(UserAuthorityRepository userAuthorityRepository, VisitorRepository visitorRepository,
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

    public static Book setupAndReturnBook(BookRepository bookRepository, Author author, String bookTitle) {
        Book book = new Book();
        book.setTitle(bookTitle);
        book.setPurchasePrice("200");
        book.setPublisher("Testförlaget");
        book.setIsbn("12132131332113");
        book.setDescription("Detta är en testbok, inget annat");
        book.setAvailable(true);
        book.setLoanedBooks(new ArrayList<>());
        ArrayList<Author> authors = new ArrayList<>();
        authors.add(author);
        book.setAuthorList(authors);
        return bookRepository.save(book);
    }

    public static Author setupAndReturnAuthor(AuthorRepository authorRepository, String firstName, String lastName) {
        Author author1 = new Author();
        author1.setFirstname(firstName);
        author1.setLastname(lastName);
        author1.setBirthYear("1956");
        author1.setBookList(new ArrayList<>());
        return authorRepository.save(author1);
    }

    public static BookLoan setupAndReturnBookLoan(BookLoanRepository bookLoanRepository, Visitor visitor, Book book) {
        book.setAvailable(false);
        BookLoan bookLoan = new BookLoan();
        bookLoan.setBookReturned(false);
        bookLoan.setBook(book);
        bookLoan.setLibraryCard(visitor.getActiveLibraryCard());
        bookLoan.setDateLoanEnd(new Date(Calendar.getInstance().getTime().getTime()));
        bookLoan.setDateLoanStart(new Date(Calendar.getInstance().getTime().getTime()));
        book.getLoanedBooks().add(bookLoan);
        bookLoanRepository.save(bookLoan);
        return bookLoanRepository.save(bookLoan);
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


