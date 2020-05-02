package com.newtongroup.library.Entity;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name="author")
@Indexed
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="author_id")
    private int authorId;
    
    
    @Field
    @Column(name="firstname")
    private String firstname;

    @Field
    @Column(name="lastname")
    private String lastname;

//    @ManyToMany
//    @JoinTable(
//            name="book_author",
//            joinColumns = {@JoinColumn(name="idbook_author_book_id")},
//            inverseJoinColumns = {@JoinColumn(name="idbook_author_author_id")})
    
    @ContainedIn
    @ManyToMany(mappedBy = "authorList")
    private List<Book>bookList;
    
    @ContainedIn
    @ManyToMany(mappedBy = "authorList")
    private List<EBook>eBookList;

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
