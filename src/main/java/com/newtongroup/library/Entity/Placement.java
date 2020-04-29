package com.newtongroup.library.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "placement")
    private List<Book> bookList;

    public Placement() {
    }

    public Long getPlacementId() {
        return placementId;
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
