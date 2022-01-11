package com.example.mvc_pas.model;


import java.time.LocalDate;
import java.util.UUID;

public class Book {

    private UUID uuid;
    private String title;
    private String authorName;
    private String releaseDate;
    private boolean archived = false;

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setArchived() {
        this.archived = true;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
