package net;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpCommunicator {
    private static final String MAIN_PATH = "http://localhost:1233/";
    public InputStreamReader post(String jsonString, String authTokenLoggedIn, String urlPath) throws Exception {
        String fullURL= MAIN_PATH + urlPath;
        URL url = new URL(fullURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Set HTTP request headers, if necessary
        setHeaderAuthorization(authTokenLoggedIn, connection);
        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            // Write request body to OutputStream ...
            requestBody.write(jsonString.getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            return inputStreamReader;
        } else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            return inputStreamReader;
        }
    }

    private static void setHeaderAuthorization(String authTokenLoggedIn, HttpURLConnection connection) throws IOException {
        if(authTokenLoggedIn != null){
            connection.setRequestProperty("Authorization", authTokenLoggedIn);
        }
    }

    public InputStreamReader delete(String authTokenLoggedIn, String urlPath) throws Exception {
        String fullURL= MAIN_PATH  + urlPath;
        URL url = new URL(fullURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);

        // Set HTTP request headers, if necessary
        connection.setRequestProperty("Authorization", authTokenLoggedIn);
        connection.connect();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            return inputStreamReader;
        } else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            return inputStreamReader;
        }
    }

    public InputStreamReader get(String authTokenLoggedIn, String urlPath) throws Exception {
        String fullURL= MAIN_PATH + urlPath;
        URL url = new URL(fullURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");

        setHeaderAuthorization(authTokenLoggedIn, connection);
        connection.connect();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            return inputStreamReader;
        } else { //error code returned
            InputStream responseBody = connection.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            return inputStreamReader;
        }
    }

    public InputStreamReader put(String jsonString, String authTokenLoggedIn, String urlPath) throws Exception {
        String fullURL= MAIN_PATH + urlPath;
        URL url = new URL(fullURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        setHeaderAuthorization(authTokenLoggedIn, connection);
        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            requestBody.write(jsonString.getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            return inputStreamReader;
        } else {
            InputStream responseBody = connection.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            return inputStreamReader;
        }
    }
}
