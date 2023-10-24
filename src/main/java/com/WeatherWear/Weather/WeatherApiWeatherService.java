package com.WeatherWear.Weather;

import com.WeatherWear.Location.LocationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherApiWeatherService implements WeatherService{

    HttpClient httpClient;
    ObjectMapper mapper;


    public WeatherApiWeatherService(){
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
    public WeatherResult getCurrentWeather(double lat, double lon){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://weatherapi-com.p.rapidapi.com/current.json?q="+lat+"%2C"+"lon"))
                    .header("X-RapidAPI-Key", "28e4d7ba83mshef8904adcbdf947p11ca29jsn375fc96cc616")
                    .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();

            JsonNode root = mapper.readTree(responseBody);
            JsonNode current = root.get("current");

            return new WeatherResult(true,current.get("temp_c").intValue(), current.get("precip_mm").floatValue());
        } catch (IOException | InterruptedException | NullPointerException e) {
            e.printStackTrace();
            System.err.println("error while getting current weather using WeatherApi");
            return new WeatherResult(false,0,0);
        }
    }
}
