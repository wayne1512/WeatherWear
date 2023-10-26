package com.WeatherWear;

import com.WeatherWear.Input.InputService;
import com.WeatherWear.Location.LocationResult;
import com.WeatherWear.Location.WeatherApiLocationService;
import com.WeatherWear.Recommendation.FutureRecommendationService;
import com.WeatherWear.Recommendation.PresentRecommendationService;
import com.WeatherWear.Recommendation.RecommendationService;
import com.WeatherWear.Weather.WeatherApiWeatherService;
import com.WeatherWear.Weather.WeatherResult;

import java.io.IOException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) throws IOException, InterruptedException{
        /*LocationResult loc = new WeatherApiLocationService().getLocation();
        WeatherResult weatherResult = new WeatherApiWeatherService().getCurrentWeather(loc.getLat(),loc.getLon());
        new RecommendationService().recommend(weatherResult.getTemp(),weatherResult.getRain());
        System.out.println(weatherResult.getTemp());*/


        InputService inputService = new InputService();
        var a = inputService.readAirportCode();

        var b = inputService.readArrivalDate();

        WeatherResult weatherResult = new WeatherApiWeatherService().getWeatherForecastForAirport(a,b);
        new FutureRecommendationService().recommend(weatherResult.getTemp(),weatherResult.getRain());
    }
}