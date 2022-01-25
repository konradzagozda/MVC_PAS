package com.example.mvc_pas.web;


import com.example.mvc_pas.ApiRequester;
import com.example.mvc_pas.model.Rent;
import com.example.mvc_pas.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
public class ListUsersBean implements Serializable {
    private final ApiRequester requesterUsers = new ApiRequester("users");
    private final ApiRequester requesterRents = new ApiRequester("rents");
    private User selectedUser;
    private List<Rent> selectedUsersRents = new ArrayList<>();
    private String uuidFilter = "";
    private List<User> userList = getUserListFromAPI();

    public List<User> getUserList() {
        return userList;
    }

    public String getUuidFilter() {
        return uuidFilter;
    }

    public void setUuidFilter(String uuidFilter) {
        this.uuidFilter = uuidFilter;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<Rent> getSelectedUsersRents() {
        return selectedUsersRents;
    }

    public void setSelectedUsersRents() throws JsonProcessingException {
        String json = requesterRents.get();
        ObjectMapper mapper = new ObjectMapper();
        selectedUsersRents = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Rent.class));
        Predicate<Rent> byUser = rent -> rent.getClient().getUuid().equals(selectedUser.getUuid());
        selectedUsersRents = selectedUsersRents.stream().filter(byUser).collect(Collectors.toList());
    }

    public List<User> getUserListFromAPI() {
        String json = requesterUsers.get();

        ObjectMapper mapper = new ObjectMapper();

        try{
            List<User> users = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
            if (!uuidFilter.isEmpty()) {
                Predicate<User> byUuid = user -> user.getUuid().toString().equals(uuidFilter);
                users = users.stream().filter(byUuid).collect(Collectors.toList());
            }
            return users;
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    public String changeActivity(User user) {
        String action = (user.isActive() ? "deactivate" : "activate");
        String optionString = user.getUuid().toString() + "/" + action;
        requesterUsers.custom("put", optionString);
        userList = getUserListFromAPI();
        return "";
    }

    public String moveToDetails(User user) throws JsonProcessingException {
        setSelectedUser(user);
        setSelectedUsersRents();
        userList = getUserListFromAPI();
        return "/user/details.xhtml?faces-redirect=true";
    }

    public String moveToEdit(User user) {
        setSelectedUser(user);
        userList = getUserListFromAPI();
        return "/user/modifyUser.xhtml?faces-redirect=true";
    }

    public String edit() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(selectedUser);
        System.out.println(json);
        String uuid = selectedUser.getUuid().toString();
        String out = requesterUsers.put(uuid, json);
        System.out.println(out);
        userList = getUserListFromAPI();
        if (out.equals("200")){
            return "userList";
        }
        return "";
    }

    public String applyFilter() {
        // page refresh
        userList = getUserListFromAPI();
        return "";
    }
}
