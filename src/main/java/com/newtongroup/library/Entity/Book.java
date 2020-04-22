package com.newtongroup.library.Entity;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book extends AbstractBook {
	
	@Column(name="classication_system")
	private String classificationSystem;
	

	public String getClassificationSystem() {
		return classificationSystem;
	}

	public void setClassificationSystem(String classificationSystem) {
		this.classificationSystem = classificationSystem;
	}
	
	

	
	
	/*
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "purchase_price")
    private int purchasePrice;

    public Book () {
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long id) {
        this.bookId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    
    */
}
