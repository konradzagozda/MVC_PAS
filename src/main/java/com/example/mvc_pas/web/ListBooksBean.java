package com.example.mvc_pas.web;


import com.example.mvc_pas.ApiRequester;
import com.example.mvc_pas.model.Book;
import com.example.mvc_pas.model.Rent;
import com.example.mvc_pas.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.client.Entity;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class ListBooksBean implements Serializable {
    private final ApiRequester requester = new ApiRequester("books");
    private final ApiRequester requesterRents = new ApiRequester("rents");
    private Book selectedBook;
    private List<Rent> selectedBooksRents = new ArrayList<>();
    private String uuidFilter = "";
    private List<Book> bookList = getBooksFromApi();

    public List<Rent> getSelectedBooksRents() {
        return selectedBooksRents;
    }

    public void setSelectedBooksRents() throws JsonProcessingException {
        String json = requesterRents.get();
        ObjectMapper mapper = new ObjectMapper();
        selectedBooksRents = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Rent.class));
        Predicate<Rent> byBook = rent -> rent.getBook().getUuid().equals(selectedBook.getUuid());
        selectedBooksRents = selectedBooksRents.stream().filter(byBook).collect(Collectors.toList());
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    public List<Book> getBookList() {
        return new ArrayList<>(bookList);
    }

    public List<Book> getBooksFromApi() {
        System.out.println("books from api called");
        String json = requester.get();

        ObjectMapper mapper = new ObjectMapper();

        try{
            List<Book> books = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Book.class));
            if (!uuidFilter.isEmpty()) {
                Predicate<Book> byUuid = book -> book.getUuid().toString().equals(uuidFilter);
                books = books.stream().filter(byUuid).collect(Collectors.toList());
            }
            return books;
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    public String remove(Book book) throws InterruptedException {
        System.out.println(book);
        requester.delete(book.getUuid().toString());
        bookList = getBooksFromApi();
        return "/book/list.xhtml?faces-redirect=true";
    }

    public String moveToEdit(Book book) {
        setSelectedBook(book);
        bookList = getBooksFromApi();
        return "/book/modifyBook.xhtml?faces-redirect=true";
    }

    public String moveToDetails(Book book) throws JsonProcessingException {
        setSelectedBook(book);
        setSelectedBooksRents();
        bookList = getBooksFromApi();
        return "/book/details.xhtml?faces-redirect=true";
    }

    public String edit() throws JsonProcessingException {
        String uuid = selectedBook.getUuid().toString();
        String out = requester.put(uuid, Entity.json(selectedBook));
        getBooksFromApi();
        if (out.equals("200")){
            return "bookList";
        }
        return "";
    }

    public String getUuidFilter() {
        return uuidFilter;
    }

    public void setUuidFilter(String uuidFilter) {
        this.uuidFilter = uuidFilter;
    }

    public String applyFilter() {
        bookList = getBooksFromApi();
        return "";
    }

    public void refreshBookList() {
        bookList = getBooksFromApi();
    }
}
