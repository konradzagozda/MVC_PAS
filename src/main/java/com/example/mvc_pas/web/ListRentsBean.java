package com.example.mvc_pas.web;


import com.example.mvc_pas.ApiRequester;
import com.example.mvc_pas.model.Book;
import com.example.mvc_pas.model.Rent;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Entity;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class ListRentsBean implements Serializable {
    private final ApiRequester requester = new ApiRequester("rents");
    private String uuidClientFilter = "";
    private String uuidBookFilter = "";
    private List<Rent> rentList = getRentListFromAPI();

    public List<Rent> getRentList() {
        return new ArrayList<>(rentList);
    }

    public String getUuidBookFilter() {
        return uuidBookFilter;
    }

    public void setUuidBookFilter(String uuidBookFilter) {
        this.uuidBookFilter = uuidBookFilter;
    }

    public String getUuidClientFilter() {
        return uuidClientFilter;
    }

    public void setUuidClientFilter(String uuidClientFilter) {
        this.uuidClientFilter = uuidClientFilter;
    }

    public List<Rent> getRentListFromAPI() {
        String json = requester.get();

        ObjectMapper mapper = new ObjectMapper();

        try{
            List<Rent> rents = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Rent.class));
            if (!uuidClientFilter.isEmpty()) {
                Predicate<Rent> byUuidClient = rent -> rent.getClient().getUuid().toString().equals(uuidClientFilter);
                rents = rents.stream().filter(byUuidClient).collect(Collectors.toList());
            }
            if (!uuidBookFilter.isEmpty()) {
                Predicate<Rent> byUuidBook = rent -> rent.getBook().getUuid().toString().equals(uuidBookFilter);
                rents = rents.stream().filter(byUuidBook).collect(Collectors.toList());
            }

            return rents;
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    public String end(Rent rent) {
        requester.put(rent.getUuid().toString(), null); // put on rent ends the rent
        rentList = getRentListFromAPI();
        return "";
    }
    public String delete(Rent rent) {
        requester.delete(rent.getUuid().toString()); // put on rent ends the rent
        rentList = getRentListFromAPI();
        return "";
    }

    public String applyFilter() {
        // just refresh page so getRentList is called again.
        rentList = getRentListFromAPI();
        return "";
    }
}
