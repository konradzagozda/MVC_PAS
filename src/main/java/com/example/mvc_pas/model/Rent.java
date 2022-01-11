package com.example.mvc_pas.model;

import java.time.LocalDate;
import java.util.UUID;


public class Rent {

    private UUID uuid;
    private String startDate;
    private String endDate = null;
    private User client;
    private Book book;


    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public User getClient() {
        return client;
    }

    public Book getBook() {
        return book;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
