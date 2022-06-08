package com.mrserg86.blog.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title, anons, fullText, userName;
    private int views;

    public Post(String title, String anons, String fullText, String userName) {
        this.title = title;
        this.anons = anons;
        this.fullText = fullText;
        this.userName = userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getAnons() {
        return anons;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getViews() {
        return views;
    }

    public Post() {
    }

    public Post(String title, String anons, String fullText) {
        this.title = title;
        this.anons = anons;
        this.fullText = fullText;
    }

}
