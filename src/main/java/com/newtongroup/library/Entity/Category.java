package com.newtongroup.library.Entity;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "category_id")
    private Long idCategory;

    @Column(name = "categorytype")
    private String categoryType;

    public Category() {
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}
