package com.newtongroup.library.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "removed_e_books")
public class RemovedEBook {

    @Id
    @Column(name = "e_book_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int e_book_id;

    @Column(name="title")
    private String title;

    @Column(name="isbn")
    private String isbn;

    @Column(name="publisher")
    private String publisher;

    @Column(name="description")
    private String description;

    @Column(name="purchase_price")
    private String price;

    @Column(name="download_link")
    private String download_link;

    @Column(name= "cause")
    private String cause;

    @Column(name="deleted_at")
    private String deleted_at;

    public RemovedEBook() {
    }

    public RemovedEBook(int e_book_id, String title, String isbn, String publisher, String description, String price, String download_link, String cause) {
        this.e_book_id = e_book_id;
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.description = description;
        this.price = price;
        this.download_link = download_link;
        this.cause = cause;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.deleted_at = dtf.format(now);
    }

    public int getE_book_id() {
        return e_book_id;
    }

    public void setE_book_id(int e_book_id) {
        this.e_book_id = e_book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDownload_link() {
        return download_link;
    }

    public void setDownload_link(String download_link) {
        this.download_link = download_link;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
