package com.WeatherWear;

import com.WeatherWear.Input.InputService;
import com.WeatherWear.Location.LocationResult;
import com.WeatherWear.Location.LocationService;
import com.WeatherWear.Location.WeatherApiLocationService;
import com.WeatherWear.Recommendation.FutureRecommendationService;
import com.WeatherWear.Recommendation.PresentRecommendationService;
import com.WeatherWear.Weather.WeatherApiWeatherService;
import com.WeatherWear.Weather.WeatherResult;

import java.util.concurrent.*;

public class WeatherWear{
    LocationService locationService = new WeatherApiLocationService();
    LocationService backupLocationService = new WeatherApiLocationService();
    ExecutorService executor = Executors.newSingleThreadExecutor();
    InputService inputService = new InputService();


    public void recommendCurrent(){
        Callable<LocationResult> task = locationService::getLocation;
        Future<LocationResult> future = executor.submit(task);

        LocationResult locationResult;

        try {
            locationResult = future.get(3, TimeUnit.SECONDS);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            future.cancel(true);
        }

        if (!locationResult.isSuccess()){
            locationResult = backupLocationService.getLocation();
        }

        LocationResult loc = new WeatherApiLocationService().getLocation();
        WeatherResult weatherResult = new WeatherApiWeatherService().getCurrentWeather(loc.getLat(),loc.getLon());
        new PresentRecommendationService().recommend(weatherResult.getTemp(),weatherResult.getRain());
    }

    public void recommendAtArrival(){
        var airportCode = inputService.readAirportCode();

        var arrivalDate = inputService.readArrivalDate();

        WeatherResult weatherResult = new WeatherApiWeatherService().getWeatherForecastForAirport(airportCode,arrivalDate);
        new FutureRecommendationService().recommend(weatherResult.getTemp(),weatherResult.getRain());
    }
}
