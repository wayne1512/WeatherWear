package com.WeatherWear.Weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class WeatherApiWeatherServiceTest{

    private WeatherApiWeatherService weatherApiWeatherService;

    @BeforeEach
    void setup(){
        weatherApiWeatherService = new WeatherApiWeatherService();
    }

    @Test
    void GetCurrentWeatherReturnWeatherWhenApiIsSuccessful() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String weatherJson = "{current:{precip_mm=5.1,temp_c=25}}";
        Mockito.when(mockResponse.body()).thenReturn(weatherJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        weatherApiWeatherService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        weatherApiWeatherService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);
        JsonNode mockApiResponseCurrent = mock(JsonNode.class);
        JsonNode mockApiResponseTempC = mock(JsonNode.class);
        JsonNode mockApiResponsePrecipMM = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(weatherJson)).thenReturn(mockApiResponseBodyRoot);

        Mockito.when(mockApiResponseBodyRoot.get("current")).thenReturn(mockApiResponseCurrent);

        Mockito.when(mockApiResponseCurrent.get("temp_c")).thenReturn(mockApiResponseTempC);
        Mockito.when(mockApiResponseCurrent.get("precip_mm")).thenReturn(mockApiResponsePrecipMM);

        Mockito.when(mockApiResponseTempC.intValue()).thenReturn(25);
        Mockito.when(mockApiResponsePrecipMM.floatValue()).thenReturn(5.1f);

        //Exercise
        WeatherResult res = weatherApiWeatherService.getCurrentWeather(35.902029,14.480334);

        //Verify
        assertTrue(res.isSuccess());
        assertEquals(25, res.getTemp());
        assertEquals(5.1f, res.getRain());
    }

    @Test
    void GetCurrentWeatherUnsuccessfulWhenApiReturnsNoTemp() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String weatherJson = "{current:{precip_mm=5.1}}";
        Mockito.when(mockResponse.body()).thenReturn(weatherJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        weatherApiWeatherService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        weatherApiWeatherService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);
        JsonNode mockApiResponseCurrent = mock(JsonNode.class);
        JsonNode mockApiResponsePrecipMM = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(weatherJson)).thenReturn(mockApiResponseBodyRoot);

        Mockito.when(mockApiResponseBodyRoot.get("current")).thenReturn(mockApiResponseCurrent);

        Mockito.when(mockApiResponseCurrent.get("precip_mm")).thenReturn(mockApiResponsePrecipMM);

        Mockito.when(mockApiResponsePrecipMM.floatValue()).thenReturn(5.1f);

        //Exercise
        WeatherResult res = weatherApiWeatherService.getCurrentWeather(35.902029,14.480334);

        //Verify
        assertFalse(res.isSuccess());
    }

    @Test
    void GetCurrentWeatherUnsuccessfulWhenApiReturnsNoRain() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String weatherJson = "{current:{temp_c=25}}";
        Mockito.when(mockResponse.body()).thenReturn(weatherJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        weatherApiWeatherService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        weatherApiWeatherService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);
        JsonNode mockApiResponseCurrent = mock(JsonNode.class);
        JsonNode mockApiResponseTempC = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(weatherJson)).thenReturn(mockApiResponseBodyRoot);

        Mockito.when(mockApiResponseBodyRoot.get("current")).thenReturn(mockApiResponseCurrent);

        Mockito.when(mockApiResponseCurrent.get("temp_c")).thenReturn(mockApiResponseTempC);

        Mockito.when(mockApiResponseTempC.intValue()).thenReturn(25);

        //Exercise
        WeatherResult res = weatherApiWeatherService.getCurrentWeather(35.902029,14.480334);

        //Verify
        assertFalse(res.isSuccess());
    }
    @Test
    void GetCurrentWeatherUnsuccessfulWhenApiReturnsCurrent() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String weatherJson = "{current:{temp_c=25}}";
        Mockito.when(mockResponse.body()).thenReturn(weatherJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        weatherApiWeatherService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        weatherApiWeatherService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(weatherJson)).thenReturn(mockApiResponseBodyRoot);

        //Exercise
        WeatherResult res = weatherApiWeatherService.getCurrentWeather(35.902029,14.480334);

        //Verify
        assertFalse(res.isSuccess());
    }
}