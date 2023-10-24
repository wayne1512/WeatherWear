package com.WeatherWear.Location;

public class LocationResult{
    final boolean success;
    final double lat,lon;

    public LocationResult(boolean success, double lat, double lon){
        this.success = success;
        this.lat = lat;
        this.lon = lon;
    }

    public boolean isSuccess(){
        return success;
    }

    public double getLat(){
        return lat;
    }

    public double getLon(){
        return lon;
    }
}
