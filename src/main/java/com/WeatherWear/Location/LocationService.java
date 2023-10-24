package com.WeatherWear.Location;

import java.io.IOException;

public interface LocationService{
    LocationResult getLocation() throws IOException, InterruptedException;
}
