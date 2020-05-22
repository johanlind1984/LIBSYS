package com.newtongroup.library.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Field
    @Column(name="birthyear")
    private String birthYear;

    @JsonBackReference
    @ContainedIn
    @ManyToMany(mappedBy = "authorList")
    private List<Book>bookList;

    @JsonBackReference
    @ContainedIn
    @ManyToMany(mappedBy = "authorList")
    private List<EBook>eBookList;

    @ManyToMany(mappedBy = "authorList")
    private List<RemovedBook>removedBookList;

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

    public List<EBook> geteBookList() {
        return eBookList;
    }

    public void seteBookList(List<EBook> eBookList) {
        this.eBookList = eBookList;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public List<RemovedBook> getRemovedBookList() {
        return removedBookList;
    }

    public void setRemovedBookList(List<RemovedBook> removedBookList) {
        this.removedBookList = removedBookList;
    }
}
