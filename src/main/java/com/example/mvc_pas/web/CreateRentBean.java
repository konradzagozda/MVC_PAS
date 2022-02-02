package com.example.mvc_pas.web;

import com.example.mvc_pas.ApiRequester;
import com.example.mvc_pas.model.Book;
import com.example.mvc_pas.model.Rent;
import com.example.mvc_pas.model.RentToSend;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.client.Entity;

@RequestScoped
@Named
public class CreateRentBean {
    private RentToSend rent = new RentToSend();

    public RentToSend getRent() {
        return rent;
    }

    public void setRent(RentToSend rent) {
        this.rent = rent;
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiRequester requester = new ApiRequester("rents");
        String out = requester.post(Entity.json(rent));
        if (out.equals("201")){
            return "rentList";
        }
        return "";
    }
}
