package com.WeatherWear.Weather;

import java.time.LocalDate;

public interface WeatherService{
    WeatherResult getCurrentWeather(double lat, double lon);
    WeatherResult getWeatherForecastForAirport(String code, LocalDate date);
}
