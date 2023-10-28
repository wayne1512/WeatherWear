package com.WeatherWear.Stubs.Location;

import com.WeatherWear.Location.LocationResult;
import com.WeatherWear.Location.LocationService;

public class SuccessLocationService implements LocationService{
    @Override
    public LocationResult getLocation(){
        return new LocationResult(true,1.2,4.5);
    }
}
