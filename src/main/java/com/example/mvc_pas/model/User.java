package com.example.mvc_pas.model;

import java.util.UUID;

public class User {

    private UUID uuid;
    private String login;
    private String address;
    private String pesel;
    private AccessLevel accessLevel = AccessLevel.CLIENT;
    private boolean active = true;

    public String getLogin() {
        return login;
    }

    public String getAddress() {
        return address;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPesel() {
        return pesel;
    }

    public String getAccessLevel() {
        return accessLevel.getAccessLevel();
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = AccessLevel.valueOf(accessLevel);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean val) { this.active = val; }

    public void changeActivity() {
        this.active = !this.active;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", login='" + login + '\'' +
                ", address='" + address + '\'' +
                ", pesel='" + pesel + '\'' +
                ", accessLevel=" + accessLevel +
                ", active=" + active +
                '}';
    }
}
