package com.example.mvc_pas.web;

import com.example.mvc_pas.ApiRequester;
import com.example.mvc_pas.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


@Named
@RequestScoped
public class CreateBookBean {
    private Book book = new Book();

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiRequester requester = new ApiRequester("books");
        String json = objectMapper.writeValueAsString(book);
        String out = requester.post(json);
        if (out.equals("201")){
            return "bookList";
        }
        return "";
    }
}
