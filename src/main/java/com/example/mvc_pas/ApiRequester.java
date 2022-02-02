package com.example.mvc_pas;


import com.example.mvc_pas.model.Book;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class ApiRequester {
    private String url;

    ClientBuilder clientBuilder = ClientBuilder.newBuilder();
    Client client = clientBuilder.build();
    WebTarget target;

    public ApiRequester(String resource) {
        url = "http://localhost:8080/rest/api/" + resource + "/";
        target = client.target(url);
    }

    public String get() {
        Response res = target.request(MediaType.APPLICATION_JSON).get();
        String output = res.readEntity(String.class);
        return output;
    }

    public String post(Entity entity) {
        Response res = target.request(MediaType.APPLICATION_JSON).post(entity);
        System.out.println(Integer.toString(res.getStatus()));
        return Integer.toString(res.getStatus());
    }

    public String put(String uuid, Entity entity) {
        WebTarget target = client.target(url + uuid + '/');
        Response res = target.request(MediaType.APPLICATION_JSON).put(entity);
        System.out.println(Integer.toString(res.getStatus()));
        return Integer.toString(res.getStatus());
    }

    public String delete(String uuid) {
//        MediaType JSON = MediaType.get("application/json; charset=utf-8");
//        Request request = new Request.Builder()
//                .url(url + uuid + "/")
//                .delete()
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            System.out.println("DELETE: \n" + response.body().string());
//            return Integer.toString(response.code());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Error";
//        }
        return "";
    }

    public String custom(String verb, String optionString) {
//        // verb: put, delete
//        // optionString examples: activate | deactivate
//        if (verb.equals("put") && optionString.contains("activate")) {
//            MediaType JSON = MediaType.get("application/json; charset=utf-8");
//            RequestBody body = RequestBody.create(JSON, "");
//            String urlToPass = url + optionString + "/";
//            System.out.println(urlToPass);
//            Request request = new Request.Builder()
//                    .url(urlToPass)
//                    .put(body)
//                    .build();
//            try (Response response = client.newCall(request).execute()) {
////            response.body().string();
//                return Integer.toString(response.code());
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "Error";
//            }
//        }
        return "";
    }
}
