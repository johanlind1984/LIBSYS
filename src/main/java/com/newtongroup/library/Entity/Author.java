package com.newtongroup.library.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name="author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name="author_id")
    private int authorId;

    @Column(name="firstname")
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    @ManyToMany
    @JoinTable(
            name="book_author",
            joinColumns = {@JoinColumn(name="idbook_author_book_id")},
            inverseJoinColumns = {@JoinColumn(name="idbook_author_author_id")})

    private List<Book>bookList;

    public Author() {
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}

