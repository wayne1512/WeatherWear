package com.WeatherWear.Weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

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
    void GetCurrentWeatherUnsuccessfulWhenApiReturnsNoCurrent() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String weatherJson = "{}";
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






    @Test
    void GetWeatherForecastForAirportReturnWeatherWhenApiIsSuccessful() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String weatherJson = "{\"forecast\":{\"forecastday\":[{\"day\":{\"avgtemp_c\":25,\"totalprecip_mm\":5.1}}]}}";
        Mockito.when(mockResponse.body()).thenReturn(weatherJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        weatherApiWeatherService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        weatherApiWeatherService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);
        JsonNode mockApiResponseForecast = mock(JsonNode.class);
        JsonNode mockApiResponseForecastDay = mock(JsonNode.class);
        JsonNode mockApiResponseForecastDay0 = mock(JsonNode.class);
        JsonNode mockApiResponseDay = mock(JsonNode.class);
        JsonNode mockApiResponseTempC = mock(JsonNode.class);
        JsonNode mockApiResponsePrecipMM = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(weatherJson)).thenReturn(mockApiResponseBodyRoot);

        Mockito.when(mockApiResponseBodyRoot.get("forecast")).thenReturn(mockApiResponseForecast);
        Mockito.when(mockApiResponseForecast.get("forecastday")).thenReturn(mockApiResponseForecastDay);
        Mockito.when(mockApiResponseForecastDay.get(0)).thenReturn(mockApiResponseForecastDay0);
        Mockito.when(mockApiResponseForecastDay0.get("day")).thenReturn(mockApiResponseDay);

        Mockito.when(mockApiResponseDay.get("avgtemp_c")).thenReturn(mockApiResponseTempC);
        Mockito.when(mockApiResponseDay.get("totalprecip_mm")).thenReturn(mockApiResponsePrecipMM);

        Mockito.when(mockApiResponseTempC.intValue()).thenReturn(25);
        Mockito.when(mockApiResponsePrecipMM.floatValue()).thenReturn(5.1f);

        //Exercise
        WeatherResult res = weatherApiWeatherService.getWeatherForecastForAirport("MLA", LocalDate.now());

        //Verify
        assertTrue(res.isSuccess());
        assertEquals(25, res.getTemp());
        assertEquals(5.1f, res.getRain());
    }

    @Test
    void GetWeatherForecastForAirportUnsuccessfulWhenApiReturnsNoTemp() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String weatherJson = "{\"forecast\":{\"forecastday\":[{\"day\":{\"totalprecip_mm\":5.1}}]}}";
        Mockito.when(mockResponse.body()).thenReturn(weatherJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        weatherApiWeatherService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        weatherApiWeatherService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);
        JsonNode mockApiResponseForecast = mock(JsonNode.class);
        JsonNode mockApiResponseForecastDay = mock(JsonNode.class);
        JsonNode mockApiResponseForecastDay0 = mock(JsonNode.class);
        JsonNode mockApiResponseDay = mock(JsonNode.class);
        JsonNode mockApiResponsePrecipMM = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(weatherJson)).thenReturn(mockApiResponseBodyRoot);

        Mockito.when(mockApiResponseBodyRoot.get("forecast")).thenReturn(mockApiResponseForecast);
        Mockito.when(mockApiResponseForecast.get("forecastday")).thenReturn(mockApiResponseForecastDay);
        Mockito.when(mockApiResponseForecastDay.get(0)).thenReturn(mockApiResponseForecastDay0);
        Mockito.when(mockApiResponseForecastDay0.get("day")).thenReturn(mockApiResponseDay);

        Mockito.when(mockApiResponseDay.get("totalprecip_mm")).thenReturn(mockApiResponsePrecipMM);

        Mockito.when(mockApiResponsePrecipMM.floatValue()).thenReturn(5.1f);

        //Exercise
        WeatherResult res = weatherApiWeatherService.getWeatherForecastForAirport("MLA", LocalDate.now());

        //Verify
        assertFalse(res.isSuccess());
    }

    @Test
    void GetWeatherForecastForAirportUnsuccessfulWhenApiReturnsNoRain() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String weatherJson = "{\"forecast\":{\"forecastday\":[{\"day\":{\"avgtemp_c\":25}}]}}";
        Mockito.when(mockResponse.body()).thenReturn(weatherJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        weatherApiWeatherService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        weatherApiWeatherService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);
        JsonNode mockApiResponseForecast = mock(JsonNode.class);
        JsonNode mockApiResponseForecastDay = mock(JsonNode.class);
        JsonNode mockApiResponseForecastDay0 = mock(JsonNode.class);
        JsonNode mockApiResponseDay = mock(JsonNode.class);
        JsonNode mockApiResponseTempC = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(weatherJson)).thenReturn(mockApiResponseBodyRoot);

        Mockito.when(mockApiResponseBodyRoot.get("forecast")).thenReturn(mockApiResponseForecast);
        Mockito.when(mockApiResponseForecast.get("forecastday")).thenReturn(mockApiResponseForecastDay);
        Mockito.when(mockApiResponseForecastDay.get(0)).thenReturn(mockApiResponseForecastDay0);
        Mockito.when(mockApiResponseForecastDay0.get("day")).thenReturn(mockApiResponseDay);

        Mockito.when(mockApiResponseDay.get("avgtemp_c")).thenReturn(mockApiResponseTempC);

        Mockito.when(mockApiResponseTempC.intValue()).thenReturn(25);

        //Exercise
        WeatherResult res = weatherApiWeatherService.getWeatherForecastForAirport("MLA", LocalDate.now());

        //Verify
        assertFalse(res.isSuccess());
    }

    @Test
    void GetWeatherForecastForAirportUnsuccessfulWhenApiReturnsNoDay() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String weatherJson = "{\"forecast\":{\"forecastday\":[{}]}}";
        Mockito.when(mockResponse.body()).thenReturn(weatherJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        weatherApiWeatherService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        weatherApiWeatherService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);
        JsonNode mockApiResponseForecast = mock(JsonNode.class);
        JsonNode mockApiResponseForecastDay = mock(JsonNode.class);
        JsonNode mockApiResponseForecastDay0 = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(weatherJson)).thenReturn(mockApiResponseBodyRoot);

        Mockito.when(mockApiResponseBodyRoot.get("forecast")).thenReturn(mockApiResponseForecast);
        Mockito.when(mockApiResponseForecast.get("forecastday")).thenReturn(mockApiResponseForecastDay);
        Mockito.when(mockApiResponseForecastDay.get(0)).thenReturn(mockApiResponseForecastDay0);

        //Exercise
        WeatherResult res = weatherApiWeatherService.getWeatherForecastForAirport("MLA", LocalDate.now());

        //Verify
        assertFalse(res.isSuccess());
    }

    @Test
    void GetWeatherForecastForAirportUnsuccessfulWhenApiReturnsNoForecastday() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String weatherJson = "{\"forecast\":{}";
        Mockito.when(mockResponse.body()).thenReturn(weatherJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        weatherApiWeatherService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        weatherApiWeatherService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);
        JsonNode mockApiResponseForecast = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(weatherJson)).thenReturn(mockApiResponseBodyRoot);

        Mockito.when(mockApiResponseBodyRoot.get("forecast")).thenReturn(mockApiResponseForecast);

        //Exercise
        WeatherResult res = weatherApiWeatherService.getWeatherForecastForAirport("MLA", LocalDate.now());

        //Verify
        assertFalse(res.isSuccess());
    }

    @Test
    void GetWeatherForecastForAirportUnsuccessfulWhenApiReturnsNoForecast() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String weatherJson = "{}";
        Mockito.when(mockResponse.body()).thenReturn(weatherJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        weatherApiWeatherService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        weatherApiWeatherService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(weatherJson)).thenReturn(mockApiResponseBodyRoot);

        //Exercise
        WeatherResult res = weatherApiWeatherService.getWeatherForecastForAirport("MLA", LocalDate.now());

        //Verify
        assertFalse(res.isSuccess());
    }
}