package com.WeatherWear.Recommendation;

public class FutureRecommendationService extends RecommendationService{
    @Override
    void recommendTemperature(int temp){
        if (temp <= 15)
            System.out.println("It will be cold so you should wear warm clothing.");
        else
            System.out.println("It will be warm so you should wear light clothing.");
    }

    @Override
    void recommendRain(float rain){
        if (rain <= 0)
            System.out.println("It will not be raining so you don't need an umbrella.");
        else
            System.out.println("It will be raining so you do need an umbrella.");
    }
}
