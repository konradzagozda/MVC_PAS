package com.example.mvc_pas.web;

import com.example.mvc_pas.ApiRequester;
import com.example.mvc_pas.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Entity;


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

    @Inject
    private ListBooksBean listBooksBean;

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiRequester requester = new ApiRequester("books");
        String out = requester.post(Entity.json(book));
        if (out.equals("201")){
            listBooksBean.refreshBookList();
            return "bookList";
        }
        return "";
    }
}
