package com.newtongroup.library.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "placements")
public class Placement {

    @Id
    @Column(name = "placement_id")
    private Long placementId;

    @Column(name = "ddk")
    private String ddk;

    @Column(name = "title")
    private String title;

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
}
