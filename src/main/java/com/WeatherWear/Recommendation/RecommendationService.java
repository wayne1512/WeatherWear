package com.WeatherWear.Recommendation;

public class RecommendationService{
    public void recommend(int temp,float rain){
        recommendTemperature(temp);
        recommendRain(rain);
    }

    void recommendTemperature(int temp){
        if (temp <= 15)
            System.out.println("It is cold so you should wear warm clothing.");
        else
            System.out.println("It is warm so you should wear light clothing.");
    }

    void recommendRain(float rain){
        if (rain <= 0)
            System.out.println("It is not raining so you don't need an umbrella.");
        else
            System.out.println("It is currently raining so you do need an umbrella.");
    }
}
