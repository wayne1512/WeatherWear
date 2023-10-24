package com.WeatherWear.Weather;

public class WeatherResult{
    final boolean success;
    final int temp;
    final float rain;

    public WeatherResult(boolean success, int temp, float rain){
        this.success = success;
        this.temp = temp;
        this.rain = rain;
    }

    public boolean isSuccess(){
        return success;
    }

    public int getTemp(){
        return temp;
    }

    public float getRain(){
        return rain;
    }
}
