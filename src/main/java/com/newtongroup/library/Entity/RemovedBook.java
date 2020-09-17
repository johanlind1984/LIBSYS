package com.newtongroup.library.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "removed_books")
public class RemovedBook {

    @Id
    @Column(name = "book_id")
    private Long book_id;

    @Column(name = "title")
    private String title;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "description")
    private String description;

    @Column(name = "purchase_price")
    private String price;

    @Column(name = "placement_id")
    private Long placement_id;

    @Column(name = "cause")
    private String cause;

    @Column(name = "deleted_at")
    private String deleted_at;

    public RemovedBook() {
    }

    public RemovedBook(Long book_id, String title, String isbn, String publisher, String description, String price, Long placement_id, String cause) {
        this.book_id = book_id;
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.description = description;
        this.price = price;
        this.cause = cause;
        this.placement_id = placement_id;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.deleted_at = dtf.format(now);
    }

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public Long getPlacement_id() {
        return placement_id;
    }

    public void setPlacement_id(Long placement_id) {
        this.placement_id = placement_id;
    }


}
