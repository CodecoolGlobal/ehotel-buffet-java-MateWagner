package com.codecool.ehotel;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.service.guest.BreakfastGuestService;

import java.time.LocalDate;
import java.util.ArrayList;

public class EHotelBuffetApplication {

    public static void main(String[] args) {

        // Initialize services


        // Generate guests for the season
        BreakfastGuestService breakfastGuestService = new BreakfastGuestService(LocalDate.parse("2000-01-01"),LocalDate.parse("2000-02-01"),500,7, 8);
        ArrayList<ArrayList<Guest>> guests = breakfastGuestService.getOrderedGuestForDay(LocalDate.parse("2000-01-10"));
        System.out.println(guests);
        // Run breakfast buffet


    }
}
