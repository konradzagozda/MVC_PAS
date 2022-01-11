package com.example.mvc_pas.web;


import com.example.mvc_pas.ApiRequester;
import com.example.mvc_pas.model.Book;
import com.example.mvc_pas.model.Rent;
import com.example.mvc_pas.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class ListBooksBean implements Serializable {
    private final ApiRequester requester = new ApiRequester("books");
    private final ApiRequester requesterRents = new ApiRequester("rents");
    private Book selectedBook;
    private List<Rent> selectedBooksRents = new ArrayList<>();
    private String uuidFilter = "";

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

    public String remove(Book book) {
        requester.delete(book.getUuid().toString());
        return "";
    }

    public String moveToEdit(Book book) {
        setSelectedBook(book);
        return "/book/modifyBook.xhtml?faces-redirect=true";
    }

    public String moveToDetails(Book book) throws JsonProcessingException {
        setSelectedBook(book);
        setSelectedBooksRents();
        return "/book/details.xhtml?faces-redirect=true";
    }

    public String edit() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(selectedBook);
        System.out.println(json);
        String uuid = selectedBook.getUuid().toString();
        String out = requester.put(uuid, json);
        System.out.println(out);
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
        // refresh page
        return "";
    }
}
