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

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main{
    public static void main(String[] args) throws IOException, InterruptedException{





        new WeatherWear().recommendAtArrival();

    }
}