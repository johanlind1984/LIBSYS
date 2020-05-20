package com.newtongroup.library.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

import org.hibernate.search.annotations.Indexed;

import java.util.List;

@Entity
@Indexed
@Table(name = "placements")
public class Placement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "placement_id")
    private Long placementId;

    @Column(name = "ddk")
    private String ddk;

    @Column(name = "title")
    private String title;

    @JsonBackReference
    @OneToMany(mappedBy = "placement")
    private List<Book> bookList;
    

    @JsonBackReference
    @OneToMany(mappedBy = "placement")
    private List<EBook> ebookList;

    public Placement() {
    }

    public Long getPlacementId() {
        return placementId;
    }
    
    public String getIdString() {
    	return placementId.toString();
    }

    public void setPlacementId(Long placementId) {
        this.placementId = placementId;
    }

    public String getDdk() {
        return ddk;
    }

    public void setDdk(String ddk) {
        this.ddk = ddk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
