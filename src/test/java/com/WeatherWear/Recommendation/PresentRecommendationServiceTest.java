package com.WeatherWear.Recommendation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PresentRecommendationServiceTest{

    RecommendationService recommendationService;

    @BeforeEach
    void setup(){
        recommendationService = new PresentRecommendationService();
    }

    @Test
    void RecommendRainShouldRecommendUmbrellaWhenRainIs1mm() throws Exception{
        String text = tapSystemOutNormalized(() -> {
            recommendationService.recommendRain(1);
        });


        assertEquals("It is currently raining so you do need an umbrella.\n", text);
    }

    @Test
    void RecommendRainShouldRecommendUmbrellaWhenRainIs5mm() throws Exception{
        String text = tapSystemOutNormalized(() -> {
            recommendationService.recommendRain(5);
        });


        assertEquals("It is currently raining so you do need an umbrella.\n", text);
    }

    @Test
    void RecommendRainShouldNotRecommendUmbrellaWhenRainIs0mm() throws Exception{
        String text = tapSystemOutNormalized(() -> {
            recommendationService.recommendRain(0);
        });


        assertEquals("It is not raining so you don't need an umbrella.\n", text);
    }

    @Test
    void RecommendTemperatureShouldRecommendLightClothesWhenTempIs40() throws Exception{
        String text = tapSystemOutNormalized(() -> {
            recommendationService.recommendTemperature(40);
        });


        assertEquals("It is warm so you should wear light clothing.\n", text);
    }

    @Test
    void RecommendTemperatureShouldRecommendLightClothesWhenTempIs16() throws Exception{
        String text = tapSystemOutNormalized(() -> {
            recommendationService.recommendTemperature(16);
        });


        assertEquals("It is warm so you should wear light clothing.\n", text);
    }

    @Test
    void RecommendTemperatureShouldRecommendWarmClothesWhenTempIs15() throws Exception{
        String text = tapSystemOutNormalized(() -> {
            recommendationService.recommendTemperature(15);
        });


        assertEquals("It is cold so you should wear warm clothing.\n", text);
    }

    @Test
    void RecommendTemperatureShouldRecommendWarmClothesWhenTempIs0() throws Exception{
        String text = tapSystemOutNormalized(() -> {
            recommendationService.recommendTemperature(0);
        });


        assertEquals("It is cold so you should wear warm clothing.\n", text);
    }

    @Test
    void RecommendTemperatureShouldRecommendWarmClothesWhenTempIsNegative5() throws Exception{
        String text = tapSystemOutNormalized(() -> {
            recommendationService.recommendTemperature(-5);
        });


        assertEquals("It is cold so you should wear warm clothing.\n", text);
    }

    @Test
    void RecommendShouldCallTemperatureAndRainMethods(){
        recommendationService = Mockito.spy(recommendationService);

        recommendationService.recommend(10,1.2f);
        Mockito.verify(recommendationService).recommendTemperature(10);
        Mockito.verify(recommendationService).recommendRain(1.2f);
    }
}