package com.WeatherWear.Location;

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

class IPApiLocationServiceTest{

    private IPApiLocationService ipApiLocationService;

    @BeforeEach
    void setup(){
        ipApiLocationService = new IPApiLocationService();
    }

    @Test
    void GetLocationReturnLocationWhenApiIsSuccessful() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String locationJson = "{lat:35.902029,lon:14.480334}";
        Mockito.when(mockResponse.body()).thenReturn(locationJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        ipApiLocationService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        ipApiLocationService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);
        JsonNode mockApiResponseLat = mock(JsonNode.class);
        JsonNode mockApiResponseLon = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(locationJson)).thenReturn(mockApiResponseBodyRoot);

        Mockito.when(mockApiResponseBodyRoot.get("lat")).thenReturn(mockApiResponseLat);
        Mockito.when(mockApiResponseBodyRoot.get("lon")).thenReturn(mockApiResponseLon);

        Mockito.when(mockApiResponseLat.doubleValue()).thenReturn(35.902029);
        Mockito.when(mockApiResponseLon.doubleValue()).thenReturn(14.480334);

        //Exercise
        LocationResult res = ipApiLocationService.getLocation();

        //Verify
        assertTrue(res.isSuccess());
        assertEquals(35.902029, res.getLat());
        assertEquals(14.480334, res.getLon());

    }

    @Test
    void GetLocationUnsuccessfulWhenApiDoesntReturnLat() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String locationJson = "{lon:14.480334}";
        Mockito.when(mockResponse.body()).thenReturn(locationJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        ipApiLocationService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        ipApiLocationService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);
        JsonNode mockApiResponseLon = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(locationJson)).thenReturn(mockApiResponseBodyRoot);

        Mockito.when(mockApiResponseBodyRoot.get("lon")).thenReturn(mockApiResponseLon);

        Mockito.when(mockApiResponseLon.doubleValue()).thenReturn(14.480334);

        //Exercise
        LocationResult res = ipApiLocationService.getLocation();

        //Verify
        assertFalse(res.isSuccess());
    }

    @Test
    void GetLocationUnsuccessfulWhenApiDoesntReturnLon() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String locationJson = "{lat:14.480334}";
        Mockito.when(mockResponse.body()).thenReturn(locationJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        ipApiLocationService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        ipApiLocationService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);
        JsonNode mockApiResponseLat = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(locationJson)).thenReturn(mockApiResponseBodyRoot);

        Mockito.when(mockApiResponseBodyRoot.get("lat")).thenReturn(mockApiResponseLat);

        Mockito.when(mockApiResponseLat.doubleValue()).thenReturn(14.480334);

        //Exercise
        LocationResult res = ipApiLocationService.getLocation();

        //Verify
        assertFalse(res.isSuccess());
    }

    @Test
    void GetLocationUnsuccessfulWhenApiDoesntReturnLatAndLon() throws Exception{
        //Setup
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);


        String locationJson = "{}";
        Mockito.when(mockResponse.body()).thenReturn(locationJson);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        ipApiLocationService.setHttpClient(mockClient);

        ObjectMapper mockMapper = mock(ObjectMapper.class);
        ipApiLocationService.setMapper(mockMapper);

        JsonNode mockApiResponseBodyRoot = mock(JsonNode.class);

        Mockito.when(mockMapper.readTree(locationJson)).thenReturn(mockApiResponseBodyRoot);


        //Exercise
        LocationResult res = ipApiLocationService.getLocation();

        //Verify
        assertFalse(res.isSuccess());
    }
}