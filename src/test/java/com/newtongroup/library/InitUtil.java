package com.newtongroup.library;

import com.newtongroup.library.Entity.*;
import com.newtongroup.library.Repository.*;


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

    public static User setupAndReturnUser(Authority authority, String email) {
        User user = new User();
        user.setUsername(email);
        user.setPassword("test");
        user.setAuthority(authority);
        user.setEnabled(true);
        return user;
    }


//    public static void initAdminUsernamePasswordAuthority(UserAuthorityRepository userAuthorityRepository, UserRepository userRepository) {
//        // Setting up users
//        User adminUser = new User();
//        adminUser.setUsername("adminUser@gmail.com");
//        adminUser.setPassword("test");
//        adminUser.setAuthority(userAuthorityRepository.findById((long) 1).orElse(null));
//        adminUser.setEnabled(true);
//        userRepository.save(adminUser);
//    }
//
//
//    public void initLibrarianUser(UserAuthorityRepository userAuthorityRepository, UserRepository userRepository) {
//
//        User librarianUser = new User();
//        librarianUser.setUsername("librarianUser@gmail.com");
//        librarianUser.setPassword("test");
//        librarianUser.setAuthority(userAuthorityRepository.findById((long) 2).orElse(null));
//        librarianUser.setEnabled(true);
//        userRepository.save(librarianUser);
//    }
//
//
//    public void initVisitorUser(UserAuthorityRepository userAuthorityRepository, UserRepository userRepository) {
//
//        User visitorUser = new User();
//        visitorUser.setUsername("visitorUser@gmail.com");
//        visitorUser.setPassword("test");
//        visitorUser.setAuthority(userAuthorityRepository.findById((long) 3).orElse(null));
//        visitorUser.setEnabled(true);
//        userRepository.save(visitorUser);
//    }


    public static void initVisitorUserLoan(UserRepository userRepository, UserAuthorityRepository userAuthorityRepository) {

        User visitorUserLoan = new User();
        visitorUserLoan.setUsername("visitorUserLoan@gmail.com");
        visitorUserLoan.setPassword("test");
        visitorUserLoan.setAuthority(userAuthorityRepository.findById((long) 3).orElse(null));
        visitorUserLoan.setEnabled(true);
        userRepository.save(visitorUserLoan);
    }


    public static void initAdminUserDetails(AdminRepository adminRepository) {

        Admin admin = new Admin();
        admin.setEmail("adminUser@gmail.com");
        admin.setCity("Stockholm");
        admin.setFirstName("Gunnar");
        admin.setLastName("Pettersson");
        admin.setPhone("085000000");
        admin.setPostalCode("173 53");
        admin.setStreet("Gökvägen 12");
        admin.setPersonalNumber("831021-3341");
        adminRepository.save(admin);
    }


    public static void initLibrarianUserDetails(LibrarianRepository librarianRepository) {

        Librarian librarian = new Librarian();
        librarian.setEmail("librarianUser@gmail.com");
        librarian.setCity("Stockholm");
        librarian.setFirstName("Gunnar");
        librarian.setLastName("Pettersson");
        librarian.setPhone("085000000");
        librarian.setPostalCode("173 53");
        librarian.setStreet("Gökvägen 12");
        librarian.setPersonalNumber("831021-3341");
        librarianRepository.save(librarian);
    }


    public static void initVisitorUserDetails(VisitorRepository visitorRepository) {

        Visitor visitor = new Visitor();
        visitor.setEmail("visitorUser@gmail.com");
        visitor.setCity("Stockholm");
        visitor.setFirstName("Gunnar");
        visitor.setLastName("Pettersson");
        visitor.setPhone("085000000");
        visitor.setPostalCode("173 53");
        visitor.setStreet("Gökvägen 12");
        visitor.setPersonalNumber("831021-3341");
        visitorRepository.save(visitor);
    }


    public static void initVisitorRentedBook(VisitorRepository visitorRepository) {
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


    public static void initAuthorBookAndLoan(AuthorRepository authorRepository, BookRepository bookRepository) {
        Author author1 = new Author();
        author1.setFirstname("Janne");
        author1.setLastname("Josefsson");
        author1.setBirthYear("1956");
        author1.setBookList(new ArrayList<>());
        Author savedAuthor = authorRepository.save(author1);

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
    }
}