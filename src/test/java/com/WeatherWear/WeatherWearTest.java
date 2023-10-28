package com.WeatherWear;

import com.WeatherWear.Input.InputService;
import com.WeatherWear.Location.LocationService;
import com.WeatherWear.Recommendation.PresentRecommendationService;
import com.WeatherWear.Recommendation.RecommendationService;
import com.WeatherWear.Stubs.Location.FailedLocationService;
import com.WeatherWear.Stubs.Location.SlowLocationService;
import com.WeatherWear.Stubs.Location.SuccessLocationService;
import com.WeatherWear.Stubs.Weather.SuccessWeatherService;
import com.WeatherWear.Weather.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

class WeatherWearTest{

    WeatherWear weatherWear;

    @BeforeEach
    void setup(){
        weatherWear = new WeatherWear();
    }

    @Test
    void recommendCurrentShouldCallSuccessfullyCallRequiredServices(){
        LocationService locService = spy(new SuccessLocationService());
        LocationService backupLocService = spy(new SuccessLocationService());
        WeatherService weatherService = spy(new SuccessWeatherService());
        RecommendationService presentRecommendationService = mock(RecommendationService.class);
        RecommendationService futureRecommendationService = mock(RecommendationService.class);
        weatherWear.setLocationService(locService);
        weatherWear.setBackupLocationService(backupLocService);
        weatherWear.setWeatherService(weatherService);
        weatherWear.setPresentRecommendationService(presentRecommendationService);
        weatherWear.setFutureRecommendationService(futureRecommendationService);

        weatherWear.recommendCurrent();

        verify(locService,times(1)).getLocation();
        verify(backupLocService,never()).getLocation();
        verify(weatherService,times(1)).getCurrentWeather(anyDouble(),anyDouble());
        verify(presentRecommendationService,times(1)).recommend(anyInt(),anyFloat());
        verify(futureRecommendationService,never()).recommend(anyInt(),anyFloat());
    }

    @Test
    void recommendCurrentShouldCallBackupLocationServiceIfLocationServiceFails(){
        LocationService locService = spy(new FailedLocationService());
        LocationService backupLocService = spy(new SuccessLocationService());
        WeatherService weatherService = spy(new SuccessWeatherService());
        RecommendationService presentRecommendationService = mock(RecommendationService.class);
        RecommendationService futureRecommendationService = mock(RecommendationService.class);
        weatherWear.setLocationService(locService);
        weatherWear.setBackupLocationService(backupLocService);
        weatherWear.setWeatherService(weatherService);
        weatherWear.setPresentRecommendationService(presentRecommendationService);
        weatherWear.setFutureRecommendationService(futureRecommendationService);

        weatherWear.recommendCurrent();

        verify(locService,times(1)).getLocation();
        verify(backupLocService,times(1)).getLocation();
        verify(weatherService,times(1)).getCurrentWeather(anyDouble(),anyDouble());
        verify(presentRecommendationService,times(1)).recommend(anyInt(),anyFloat());
        verify(futureRecommendationService,never()).recommend(anyInt(),anyFloat());
    }

    @Test
    void recommendCurrentShouldCallBackupLocationServiceIfLocationServiceTakesTooLong(){
        LocationService locService = spy(new SlowLocationService());
        LocationService backupLocService = spy(new SuccessLocationService());
        WeatherService weatherService = spy(new SuccessWeatherService());
        RecommendationService presentRecommendationService = mock(RecommendationService.class);
        RecommendationService futureRecommendationService = mock(RecommendationService.class);
        weatherWear.setLocationService(locService);
        weatherWear.setBackupLocationService(backupLocService);
        weatherWear.setWeatherService(weatherService);
        weatherWear.setPresentRecommendationService(presentRecommendationService);
        weatherWear.setFutureRecommendationService(futureRecommendationService);

        weatherWear.recommendCurrent();

        verify(locService,times(1)).getLocation();
        verify(backupLocService,times(1)).getLocation();
        verify(weatherService,times(1)).getCurrentWeather(anyDouble(),anyDouble());
        verify(presentRecommendationService,times(1)).recommend(anyInt(),anyFloat());
        verify(futureRecommendationService,never()).recommend(anyInt(),anyFloat());
    }
    @Test
    void recommendAtArrivalShouldCallSuccessfullyCallRequiredServices(){
        InputService inputService = mock(InputService.class);

        when(inputService.readAirportCode()).thenReturn("MLA");
        when(inputService.readArrivalDate()).thenReturn(LocalDate.now());

        WeatherService weatherService = spy(new SuccessWeatherService());
        RecommendationService presentRecommendationService = mock(RecommendationService.class);
        RecommendationService futureRecommendationService = mock(RecommendationService.class);
        weatherWear.setInputService(inputService);
        weatherWear.setWeatherService(weatherService);
        weatherWear.setPresentRecommendationService(presentRecommendationService);
        weatherWear.setFutureRecommendationService(futureRecommendationService);

        weatherWear.recommendAtArrival();

        verify(weatherService,times(1)).getWeatherForecastForAirport(any(),any());
        verify(presentRecommendationService,never()).recommend(anyInt(),anyFloat());
        verify(futureRecommendationService,times(1)).recommend(anyInt(),anyFloat());
    }

    @Test
    void menuShouldCallRecommendCurrentForChoice1(){

        WeatherWear weatherWearMock = mock(WeatherWear.class);

        InputService inputService = mock(InputService.class);
        when(inputService.readMenuOption(any(String.class),eq(3))).thenReturn(1).thenReturn(3);
        doCallRealMethod().when(weatherWearMock).setInputService(any());
        weatherWearMock.setInputService(inputService);
        doCallRealMethod().when(weatherWearMock).menu();

        weatherWearMock.menu();

        verify(weatherWearMock,times(1)).recommendCurrent();
        verify(weatherWearMock,times(0)).recommendAtArrival();

    }

    @Test
    void menuShouldCallRecommendAtArrivalForChoice2(){

        WeatherWear weatherWearMock = mock(WeatherWear.class);

        InputService inputService = mock(InputService.class);
        when(inputService.readMenuOption(any(String.class),eq(3))).thenReturn(2).thenReturn(3);
        doCallRealMethod().when(weatherWearMock).setInputService(any());
        weatherWearMock.setInputService(inputService);
        doCallRealMethod().when(weatherWearMock).menu();

        weatherWearMock.menu();

        verify(weatherWearMock,times(0)).recommendCurrent();
        verify(weatherWearMock,times(1)).recommendAtArrival();

    }

    @Test
    void menuShouldJustExitForChoice3(){

        WeatherWear weatherWearMock = mock(WeatherWear.class);

        InputService inputService = mock(InputService.class);
        when(inputService.readMenuOption(any(String.class),eq(3))).thenReturn(3);
        doCallRealMethod().when(weatherWearMock).setInputService(any());
        weatherWearMock.setInputService(inputService);
        doCallRealMethod().when(weatherWearMock).menu();

        weatherWearMock.menu();

        verify(weatherWearMock,times(0)).recommendCurrent();
        verify(weatherWearMock,times(0)).recommendAtArrival();

    }

}