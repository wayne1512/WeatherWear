package com.WeatherWear;

import com.WeatherWear.Input.InputService;
import com.WeatherWear.Location.IPApiLocationService;
import com.WeatherWear.Location.LocationResult;
import com.WeatherWear.Location.LocationService;
import com.WeatherWear.Location.WeatherApiLocationService;
import com.WeatherWear.Recommendation.FutureRecommendationService;
import com.WeatherWear.Recommendation.PresentRecommendationService;
import com.WeatherWear.Recommendation.RecommendationService;
import com.WeatherWear.Weather.WeatherApiWeatherService;
import com.WeatherWear.Weather.WeatherResult;
import com.WeatherWear.Weather.WeatherService;

import java.util.concurrent.*;

public class WeatherWear{
    LocationService locationService = new WeatherApiLocationService();
    LocationService backupLocationService = new IPApiLocationService();
    ExecutorService executor = Executors.newSingleThreadExecutor();
    InputService inputService = new InputService();
    RecommendationService presentRecommendationService = new PresentRecommendationService();
    RecommendationService futureRecommendationService = new FutureRecommendationService();
    WeatherService weatherService = new WeatherApiWeatherService();

    public void setLocationService(LocationService locationService){
        this.locationService = locationService;
    }

    public void setBackupLocationService(LocationService backupLocationService){
        this.backupLocationService = backupLocationService;
    }

    public void setInputService(InputService inputService){
        this.inputService = inputService;
    }

    public void setPresentRecommendationService(RecommendationService presentRecommendationService){
        this.presentRecommendationService = presentRecommendationService;
    }

    public void setFutureRecommendationService(RecommendationService futureRecommendationService){
        this.futureRecommendationService = futureRecommendationService;
    }

    public void setWeatherService(WeatherService weatherService){
        this.weatherService = weatherService;
    }

    public void menu(){
        int input = 0;

        do {
            input = inputService.readMenuOption("WeatherWear.com\n" +
                    "---------------\n" +
                    "1. Recommend clothing for current location\n" +
                    "2. Recommend clothing for future location\n" +
                    "3. Exit\n" +
                    "Enter choice:",3);

            switch (input){
                case 1:
                    recommendCurrent();
                    break;
                case 2:
                    recommendAtArrival();
                    break;
            }
        } while (input != 3);
    }

    public void recommendCurrent(){
        Callable<LocationResult> task = locationService::getLocation;
        Future<LocationResult> future = executor.submit(task);

        LocationResult locationResult = null;

        try {
            locationResult = future.get(3, TimeUnit.SECONDS);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            //ignore
        } finally {
            future.cancel(true);
        }

        if (locationResult == null || !locationResult.isSuccess()){
            locationResult = backupLocationService.getLocation();
        }

        if (locationResult == null || !locationResult.isSuccess()){
            //there is nothing we can do
            System.out.println("Failed to get location - cannot provide recommendation");
            return;
        }

        WeatherResult weatherResult = weatherService.getCurrentWeather(locationResult.getLat(),locationResult.getLon());

        if (weatherResult == null || !weatherResult.isSuccess()){
            //there is nothing we can do
            System.out.println("Failed to get weather for location - cannot provide recommendation");
            return;
        }

        presentRecommendationService.recommend(weatherResult.getTemp(),weatherResult.getRain());
    }

    public void recommendAtArrival(){
        var airportCode = inputService.readAirportCode();

        var arrivalDate = inputService.readArrivalDate();

        WeatherResult weatherResult = weatherService.getWeatherForecastForAirport(airportCode,arrivalDate);
        if (weatherResult == null || !weatherResult.isSuccess()){
            //there is nothing we can do
            System.out.println("Failed to get weather for location - cannot provide recommendation");
            return;
        }
        futureRecommendationService.recommend(weatherResult.getTemp(),weatherResult.getRain());
    }
}
