package com.example.mvc_pas.model;

import java.util.UUID;

public class RentToSend {
    private UUID uuid;
    private String startDate;
    private String endDate = null;
    private String clientUuid;
    private String bookUuid;

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public void setBookUuid(String bookUuid) {
        this.bookUuid = bookUuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public String getBookUuid() {
        return bookUuid;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
