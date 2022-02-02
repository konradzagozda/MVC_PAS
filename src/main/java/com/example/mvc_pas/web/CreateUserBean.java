package com.example.mvc_pas.web;


import com.example.mvc_pas.ApiRequester;
import com.example.mvc_pas.dto.UserDto;
import com.example.mvc_pas.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.client.Entity;
import java.util.regex.Pattern;

@RequestScoped
@Named
public class CreateUserBean {
    private User user = new User();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String create() throws JsonProcessingException {
        ApiRequester requester = new ApiRequester("users");
        UserDto userDto = new UserDto(user);
        String out = requester.post(Entity.json(userDto));
        if (out.equals("201")){
            return "userList";
        }
        return "";
    }

    public void validatePesel(FacesContext context, UIComponent comp,
                         Object value) {

        String pesel = (String) value;
        System.out.println(pesel.length());
        if (pesel.length() != 11) {
            ((UIInput) comp).setValid(false);

            FacesMessage message = new FacesMessage(
                    "Pesel musi mieć 11 znaków");
            context.addMessage(comp.getClientId(context), message);
        }

        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (!pattern.matcher(pesel).matches()) {
            ((UIInput) comp).setValid(false);

            FacesMessage message = new FacesMessage(
                    "Pesel może zawierać same cyfry");
            context.addMessage(comp.getClientId(context), message);
        }
    }
}
