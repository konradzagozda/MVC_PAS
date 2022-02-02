package com.example.mvc_pas.dto;

import com.example.mvc_pas.model.AccessLevel;
import com.example.mvc_pas.model.User;

import java.util.UUID;

public class UserDto {

    private String login;
    private String address;
    private String pesel;
    private String accessLevel;

    public UserDto() {
    }

    public UserDto(User user){
        login = user.getLogin();
        address = user.getAddress();
        pesel = user.getPesel();
        accessLevel = user.getAccessLevel();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
}
