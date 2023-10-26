package com.WeatherWear.Location;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IPApiLocationService implements LocationService{

    HttpClient httpClient;
    ObjectMapper mapper;


    public IPApiLocationService(){
        httpClient = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    public void setHttpClient(HttpClient httpClient){
        this.httpClient = httpClient;
    }

    public void setMapper(ObjectMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public LocationResult getLocation(){

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://ip-api.com/json/"))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();

            JsonNode root = mapper.readTree(responseBody);

            return new LocationResult(true,root.get("lat").doubleValue(), root.get("lon").doubleValue());
        } catch (IOException | InterruptedException | NullPointerException e) {
            e.printStackTrace();
            System.err.println("error while getting IP location using WeatherApi");
            return new LocationResult(false,0,0);
        }
    }
}
