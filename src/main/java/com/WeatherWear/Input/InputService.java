package com.WeatherWear.Input;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputService{
    Scanner s = new Scanner(System.in);

    public void setScanner(Scanner s){
        this.s = s;
    }

    public String readAirportCode(){
        System.out.println("Enter IATA code of destination airport.");

        String code = "";

        code = s.nextLine();
        while (!Pattern.matches("^[A-Z]{3}$",code)){
            System.out.println("Invalid format: try again");
            code = s.nextLine();
        }
        return code;
    }

    public LocalDate readArrivalDate(){

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        System.out.println("Enter Date code of arrival in the form YYYY-MM-DD (cannot be more than 10 days in the future)");

        String str = "";

        str = s.nextLine();

        while (true){
            try {
                LocalDate today = LocalDate.now();

                //allow user to enter yesterday's date to account for the arrival destination being in a different time zone
                LocalDate yesterday = today.minusDays(1);

                LocalDate tenDays = today.plusDays(10);

                LocalDate inputDate = LocalDate.parse(str,dateFormat);

                if (inputDate.isBefore(yesterday))
                    System.out.println("Date cannot be in the past");
                else if (inputDate.isAfter(tenDays))
                    System.out.println("Date cannot be more than 10 days in the future");
                else
                    return inputDate;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format: try again");
            }

            str = s.nextLine();
        }
    }

    public int readMenuOption(String menuString, int maxOption){
        System.out.println(menuString);

        while (true){
            try {
                int choice = s.nextInt();

                if(choice <= 0 || choice > maxOption)
                    System.out.println("Invalid input: try again");
                else return choice;

            } catch (Exception e) {
                System.out.println("Invalid input: try again");
            }

        }
    }
}
