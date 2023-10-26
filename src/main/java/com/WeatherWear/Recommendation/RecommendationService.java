package com.WeatherWear.Recommendation;

public abstract class RecommendationService{
    public void recommend(int temp,float rain){
        recommendTemperature(temp);
        recommendRain(rain);
    }

    abstract void recommendTemperature(int temp);

    abstract void recommendRain(float rain);
}
