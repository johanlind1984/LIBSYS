package com.newtongroup.library.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "removed_seminaries")
public class RemovedSeminary {

    @Id
    @Column(name = "seminary_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seminary_id;

    @Column(name = "title")
    private String title;

    @Column(name = "information")
    private String information;

    @Column(name="starttime")
    private String starttime;

    @Column(name="endtime")
    private String endtime;

    @Column(name= "cause")
    private String cause;

    @Column(name="deleted_at")
    private String deleted_at;

    @Column(name = "occurrence")
    private String occurrence;


    public RemovedSeminary() {
    }



    public RemovedSeminary(Long id, String title, String information, String occurrence, String startTime, String endTime, String cause) {
        this.seminary_id = seminary_id;
        this.title = title;
        this.information = information;

        this.cause = cause;
        this.starttime = starttime;
        this.endtime = endtime;
        this.occurrence = occurrence;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.deleted_at = dtf.format(now);
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

    public String getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(String occurrence) {
        this.occurrence = occurrence;
    }
}

