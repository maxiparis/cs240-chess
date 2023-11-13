package net;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import responses.LoginResponse;

public class ClientCommunicator {

//TODO: should I do an enum? The problem is that later I will have to do a switch statement to concatenate
    //the url (i am taking the url directly from the method parameters)
//    public enum urlPaths {
//        SESSION,
//        DB,
//        USER,
//        GAME,
//    }

    public LoginResponse post(String jsonString, String urlPath) throws Exception {
        String fullURL= "http://localhost:8080/" + urlPath;
        URL url = new URL(fullURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Set HTTP request headers, if necessary
        // connection.addRequestProperty("Accept", "text/html");

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            // Write request body to OutputStream ...
            requestBody.write(jsonString.getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //connection.getHeaderField("Content-Length");

            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            LoginResponse response = new Gson().fromJson(inputStreamReader, LoginResponse.class);
            return response;
        } else {
            // SERVER RETURNED AN HTTP ERROR
            System.out.println("There was an error.");
            InputStream responseBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            LoginResponse response = new Gson().fromJson(inputStreamReader, LoginResponse.class);
            return response;
        }
    }
}
