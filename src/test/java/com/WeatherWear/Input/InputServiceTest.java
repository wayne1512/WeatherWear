package com.WeatherWear.Input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InputServiceTest{

    InputService inputService;
    LocalDate today;
    DateTimeFormatter dateTimeFormatter;

    @BeforeEach
    void setup(){
        inputService = new InputService();
        today = LocalDate.now();
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }


    @Test
    void readAirportCodeShouldReadMLA() {

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn("MLA");

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals("MLA", inputService.readAirportCode());
            });


            assertEquals("Enter IATA code of destination airport.\n", text);

        });
    }

    @Test
    void readAirportCodeShouldReadLHR() {

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn("LHR");

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals("LHR", inputService.readAirportCode());
            });


            assertEquals("Enter IATA code of destination airport.\n", text);

        });
    }

    @Test
    void readAirportCodeShouldReadNBO() {

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn("NBO");

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals("NBO", inputService.readAirportCode());
            });


            assertEquals("Enter IATA code of destination airport.\n", text);

        });
    }

    @Test
    void readAirportCodeShouldKeepAskingUntilACorrectCodeIsEntered() {

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn("a").thenReturn("b").thenReturn("MLA");

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals("MLA", inputService.readAirportCode());
            });


            assertEquals("Enter IATA code of destination airport.\nInvalid format: try again\nInvalid format: try again\n", text);

        });
    }

    @Test
    void readAirportCodeShouldNotAcceptLowerCase(){

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn("abc").thenReturn("mla").thenReturn("MLA");

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals("MLA", inputService.readAirportCode());
            });


            assertEquals("Enter IATA code of destination airport.\nInvalid format: try again\nInvalid format: try again\n", text);

        });
    }

    @Test
    void readAirportCodeShouldNotAcceptNumbers(){

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn("1").thenReturn("2").thenReturn("MLA");

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals("MLA", inputService.readAirportCode());
            });


            assertEquals("Enter IATA code of destination airport.\nInvalid format: try again\nInvalid format: try again\n", text);

        });
    }

    @Test
    void readAirportCodeShouldNotAcceptSymbols(){

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn("/").thenReturn(".").thenReturn("MLA");

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals("MLA", inputService.readAirportCode());
            });


            assertEquals("Enter IATA code of destination airport.\nInvalid format: try again\nInvalid format: try again\n", text);

        });
    }

    @Test
    void readAirportCodeShouldNotAcceptMoreThan3Characters(){

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn("HEAT").thenReturn("MALTA").thenReturn("MLA");

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals("MLA", inputService.readAirportCode());
            });


            assertEquals("Enter IATA code of destination airport.\nInvalid format: try again\nInvalid format: try again\n", text);

        });
    }

    @Test
    void readAirportCodeShouldNotAcceptLessThan3Characters(){

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn("ML").thenReturn("EU").thenReturn("MLA");

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals("MLA", inputService.readAirportCode());
            });


            assertEquals("Enter IATA code of destination airport.\nInvalid format: try again\nInvalid format: try again\n", text);

        });
    }

    @Test
    void readAirportCodeShouldNotAcceptBlankOrWhitespace() {

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn("").thenReturn(" ").thenReturn("MLA");

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals("MLA", inputService.readAirportCode());
            });


            assertEquals("Enter IATA code of destination airport.\nInvalid format: try again\nInvalid format: try again\n", text);

        });
    }

    @Test
    void readArrivalDateShouldAcceptToday(){

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn(today.format(dateTimeFormatter));

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals(today, inputService.readArrivalDate());
            });


            assertEquals("Enter Date code of arrival in the form YYYY-MM-DD (cannot be more than 10 days in the future)\n", text);

        });
    }
    @Test
    void readArrivalDateShouldAcceptYesterday(){

        LocalDate yesterday = today.minusDays(1);

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn(yesterday.format(dateTimeFormatter));

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals(yesterday, inputService.readArrivalDate());
            });


            assertEquals("Enter Date code of arrival in the form YYYY-MM-DD (cannot be more than 10 days in the future)\n", text);

        });
    }
    @Test
    void readArrivalDateShouldAcceptTomorrow(){

        LocalDate tomorrow = today.plusDays(1);

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn(tomorrow.format(dateTimeFormatter));

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals(tomorrow, inputService.readArrivalDate());
            });


            assertEquals("Enter Date code of arrival in the form YYYY-MM-DD (cannot be more than 10 days in the future)\n", text);

        });
    }
    @Test
    void readArrivalDateShouldAccept10DaysFuture(){

        LocalDate tenDaysFuture = today.plusDays(10);

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn(tenDaysFuture.format(dateTimeFormatter));

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals(tenDaysFuture, inputService.readArrivalDate());
            });


            assertEquals("Enter Date code of arrival in the form YYYY-MM-DD (cannot be more than 10 days in the future)\n", text);

        });
    }


    @Test
    void readArrivalDateShouldNotAccept11DaysFuture(){

        LocalDate elevenDaysFuture = today.plusDays(11);

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn(elevenDaysFuture.format(dateTimeFormatter)).thenReturn(today.format(dateTimeFormatter));

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals(today, inputService.readArrivalDate());
            });


            assertEquals("Enter Date code of arrival in the form YYYY-MM-DD (cannot be more than 10 days in the future)\nDate cannot be more than 10 days in the future\n", text);

        });
    }

    @Test
    void readArrivalDateShouldNotAccept2DaysPast(){

        LocalDate twoDaysPast = today.minusDays(2);

        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn(twoDaysPast.format(dateTimeFormatter)).thenReturn(today.format(dateTimeFormatter));

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals(today, inputService.readArrivalDate());
            });


            assertEquals("Enter Date code of arrival in the form YYYY-MM-DD (cannot be more than 10 days in the future)\nDate cannot be in the past\n", text);

        });
    }


    @Test
    void readArrivalDateShouldNotAcceptMalformedDate(){


        Scanner mockScanner = mock(Scanner.class);
        Mockito.when(mockScanner.nextLine()).thenReturn("a").thenReturn(today.format(dateTimeFormatter));

        inputService.setScanner(mockScanner);

        assertTimeoutPreemptively(Duration.ofSeconds(10),()-> {

            String text = tapSystemOutNormalized(() -> {
                assertEquals(today, inputService.readArrivalDate());
            });


            assertEquals("Enter Date code of arrival in the form YYYY-MM-DD (cannot be more than 10 days in the future)\nInvalid format: try again\n", text);

        });
    }
}