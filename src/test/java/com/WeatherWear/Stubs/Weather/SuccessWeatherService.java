package com.WeatherWear.Stubs.Weather;

import com.WeatherWear.Weather.WeatherResult;
import com.WeatherWear.Weather.WeatherService;

import java.time.LocalDate;

public class SuccessWeatherService implements WeatherService{
    @Override
    public WeatherResult getCurrentWeather(double lat, double lon){
        return new WeatherResult(true,40,5);
    }

    @Override
    public WeatherResult getWeatherForecastForAirport(String code, LocalDate date){
        return new WeatherResult(true,40,5);
    }
}
