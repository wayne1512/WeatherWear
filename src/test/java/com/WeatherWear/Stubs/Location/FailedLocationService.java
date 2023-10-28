package com.WeatherWear.Stubs.Location;

import com.WeatherWear.Location.LocationResult;
import com.WeatherWear.Location.LocationService;

public class FailedLocationService implements LocationService{
    @Override
    public LocationResult getLocation(){
        return new LocationResult(false,0,0);
    }
}
