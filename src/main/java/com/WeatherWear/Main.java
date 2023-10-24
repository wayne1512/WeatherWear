package com.WeatherWear;

import com.WeatherWear.Location.WeatherApiLocationService;

import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException, InterruptedException{
        System.out.println("Hello world!");
        System.out.println(new WeatherApiLocationService().getLocation().getLat());
        System.out.println(new WeatherApiLocationService().getLocation().getLon());
    }
}