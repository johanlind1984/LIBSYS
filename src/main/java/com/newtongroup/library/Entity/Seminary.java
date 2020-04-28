package com.newtongroup.library.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Seminary")
public class Seminary {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seminary_id")
    private Long seminary_id;


    @Column(name = "title")
    private String title;

    @Column(name = "information")
    private String information;

    @Temporal(TemporalType.DATE)
    @Column(name = "occurrence")
    private java.util.Date occurrence;

    @Column(name="starttime")
    private String starttime;

    @Column(name="endtime")
    private String endtime;

    public Seminary() {
    }

    public Long getSeminary_id() {
        return seminary_id;
    }

    public void setSeminary_id(Long seminary_id) {
        this.seminary_id = seminary_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Date getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Date occurrence) {
        this.occurrence = occurrence;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

}
