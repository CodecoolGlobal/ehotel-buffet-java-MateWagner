package com.codecool.ehotel;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.service.guest.BreakfastGuestService;

import java.time.LocalDate;

public class EHotelBuffetApplication {

    public static void main(String[] args) {

        // Initialize services


        // Generate guests for the season
        BreakfastGuestService breakfastGuestService = new BreakfastGuestService(7);
        Guest guest = breakfastGuestService.generateRandomGuest(LocalDate.parse("2000-01-01"),LocalDate.parse("2001-01-01"));
        System.out.println(guest.toString());
        // Run breakfast buffet


    }
}
