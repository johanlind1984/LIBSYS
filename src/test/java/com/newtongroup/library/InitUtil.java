package com.newtongroup.library;


import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import com.newtongroup.library.Entity.Authority;
import com.newtongroup.library.Entity.User;
import com.newtongroup.library.Repository.AdminRepository;
import com.newtongroup.library.Repository.UserAuthorityRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Wrapper.UserPerson;
import org.hibernate.query.criteria.internal.expression.function.CurrentTimeFunction;


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
                                            AdminRepository adminRepository,
                                            UserRepository userRepository,
                                            String email) {

        userRepository.save(InitUtil.setupAndReturnUser(userAuthorityRepository.findById((long) 1).orElse(null), email));
        return adminRepository.save(initAndGetAdmin(email));
    }

    public static Librarian setupAndReturnLibrarian(UserAuthorityRepository userAuthorityRepository,
                                                    LibrarianRepository librarianRepository,
                                                    UserRepository userRepository,
                                                    String email) {

        userRepository.save(InitUtil.setupAndReturnUser(userAuthorityRepository.findById((long) 2).orElse(null), email));
        return librarianRepository.save(initAndGetLibrarian(email));
    }

    public static Visitor setupAndReturnVisitor(UserAuthorityRepository userAuthorityRepository,
                                                VisitorRepository visitorRepository,
                                                UserRepository userRepository,
                                                String email) {

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

    public static Placement setUpAndReturnPlacement(PlacementRepository placementRepository){
        Placement placement = new Placement();
        placement.setPlacementId((long)1);
        placement.setDdk("??");
        placement.setTitle("TestPlacement");


        return placementRepository.save(placement);
    }

    public static Book setupAndReturnBook(BookRepository bookRepository, Author author, Placement placement, String bookTitle) {
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
       book.setPlacement(placement);
        return bookRepository.save(book);
    }

    public static Seminary setupAndReturnSeminary(SeminaryRepository seminaryRepository, Long id) {

        Seminary seminary = new Seminary();
        seminary.setEndTime("12:50");
       seminary.setInformation("TestInformation");
       seminary.setOccurrence("2020-09-20");
        //  seminary.setSeminary_id(id);
       seminary.setStartTime("11:50");
       seminary.setTitle("TestTitle");


        return seminaryRepository.save(seminary);
    }

    public static RemovedSeminary setupAndReturnRemovedSeminary(RemovedSeminaryRepository removedSeminaryRepository,Seminary seminary, String cause) {

        RemovedSeminary removedSeminary = new RemovedSeminary();
        removedSeminary.setCause(cause);
        removedSeminary.setEndtime(seminary.getEndTime());
        removedSeminary.setStarttime(seminary.getStartTime());
         removedSeminary.setSeminary_id(seminary.getSeminary_id());
        removedSeminary.setTitle(seminary.getTitle());
        removedSeminary.setOccurrence(seminary.getOccurrence());
        removedSeminary.setInformation(seminary.getInformation());


        return removedSeminaryRepository.save(removedSeminary);


    }

    public static RemovedBook setupAndReturnRemovedBook(RemovedBookRepository removedBookRepository,Book book, String cause) {

        RemovedBook removedBook = new RemovedBook();
        removedBook.setBook_id(book.getId());
        removedBook.setTitle(book.getTitle());
        removedBook.setIsbn(book.getIsbn());
        removedBook.setPublisher(book.getPublisher());


        removedBook.setDescription(book.getDescription());
        removedBook.setPrice(book.getPurchasePrice());
        removedBook.setCause(cause);
        removedBook.setPlacement_id(book.getPlacement().getPlacementId());



        return removedBookRepository.save(removedBook);


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


