package com.newtongroup.library.Entity;

import javax.persistence.*;

@Entity
@Table(name = "seminaries")
public class Seminary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seminary_id")
    private Long seminary_id;


    @Column(name = "title")
    private String title;

    @Column(name = "information")
    private String information;

    @Column(name = "occurrence")
    private String occurrence;

    @Column(name="endtime")
    private String endTime;

    @Column(name="starttime")
    private String startTime;

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

    public String getOccurrence() { return occurrence; }

    public void setOccurrence(String occurrence) {
        this.occurrence = occurrence;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
