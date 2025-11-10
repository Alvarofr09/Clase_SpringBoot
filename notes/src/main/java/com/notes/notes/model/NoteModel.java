package com.notes.notes.model;

import jakarta.persistence.*;

@Entity
public class NoteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean complete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUserModel author;


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

    public AppUserModel getAuthor() {
        return author;
    }

    public NoteModel(){};

    public NoteModel(String title, String description, boolean complete, AppUserModel author) {
        this.title = title;
        this.description = description;
        this.complete = complete;
        this.author = author;
    }
}
