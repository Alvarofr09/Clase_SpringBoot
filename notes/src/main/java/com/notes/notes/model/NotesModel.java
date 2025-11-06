package com.notes.notes.model;

import jakarta.persistence.*;

@Entity
public class NotesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private boolean complete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser author;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public AppUser getAuthor() {
        return author;
    }

    public NotesModel(){};

    public NotesModel(String title, String description, boolean complete, AppUser author) {
        this.title = title;
        this.description = description;
        this.complete = complete;
        this.author = author;
    }
}
