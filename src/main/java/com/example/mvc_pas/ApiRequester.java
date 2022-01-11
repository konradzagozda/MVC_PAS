package com.example.mvc_pas;

import okhttp3.*;

import java.io.IOException;

public class ApiRequester {
    private OkHttpClient client = new OkHttpClient();
    private String url;

    public ApiRequester(String resource) {
        url = "http://localhost:8080/rest/api/" + resource + "/";
    }

    public String get() {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String out = response.body().string();
            return out;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String post(String json) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
//            response.body().string();
            return Integer.toString(response.code());
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public String put(String uuid, String json) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url + uuid + '/')
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println("put response: \n" + response.body().string());
            return Integer.toString(response.code());
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public String delete(String uuid) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(url + uuid + "/")
                .delete()
                .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println("DELETE: \n" + response.body().string());
            return Integer.toString(response.code());
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public String custom(String verb, String optionString) {
        // verb: put, delete
        // optionString examples: activate | deactivate
        if (verb.equals("put") && optionString.contains("activate")) {
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, "");
            String urlToPass = url + optionString + "/";
            System.out.println(urlToPass);
            Request request = new Request.Builder()
                    .url(urlToPass)
                    .put(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
//            response.body().string();
                return Integer.toString(response.code());
            } catch (IOException e) {
                e.printStackTrace();
                return "Error";
            }
        }
        return "";
    }
}
