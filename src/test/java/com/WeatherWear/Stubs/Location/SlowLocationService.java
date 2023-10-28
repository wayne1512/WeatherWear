package com.WeatherWear.Stubs.Location;

import com.WeatherWear.Location.LocationResult;
import com.WeatherWear.Location.LocationService;

public class SlowLocationService implements LocationService{
    @Override
    public LocationResult getLocation(){
        try {
            Thread.sleep(7000);
            return new LocationResult(true,8.1,91.2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
