package com.WeatherWear;

import com.WeatherWear.Location.LocationResult;
import com.WeatherWear.Location.WeatherApiLocationService;
import com.WeatherWear.Recommendation.RecommendationService;
import com.WeatherWear.Weather.WeatherApiWeatherService;
import com.WeatherWear.Weather.WeatherResult;

import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException, InterruptedException{
        System.out.println("Hello world!");
        LocationResult loc = new WeatherApiLocationService().getLocation();
        WeatherResult weatherResult = new WeatherApiWeatherService().getCurrentWeather(loc.getLat(),loc.getLon());
        new RecommendationService().recommend(weatherResult.getTemp(),weatherResult.getRain());
        System.out.println(weatherResult.getTemp());
    }
}