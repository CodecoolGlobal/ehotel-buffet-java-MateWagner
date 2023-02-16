package com.codecool.ehotel;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.service.Statistic;
import com.codecool.ehotel.service.buffet.BreakfastManager;
import com.codecool.ehotel.service.guest.BreakfastGuestService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EHotelBuffetApplication {

    public static void main(String[] args) {
        LocalDate SEASON_START = LocalDate.parse("2000-01-01");
        LocalDate SEASON_END = LocalDate.parse("2000-02-01");
        String CURRENCY = "Coins";

        // Initialize services
        Buffet buffet = new Buffet(new ArrayList<>());
        Statistic statistic = new Statistic();
        BreakfastManager buffetService = new BreakfastManager(buffet, statistic);

        // Generate guests for the season
        BreakfastGuestService breakfastGuestService = new BreakfastGuestService(SEASON_START, SEASON_END, 500, 7, 8);

        // Run breakfast buffet
        LocalDate currentDay = SEASON_START;
        while (currentDay.isBefore(SEASON_END)) {
            List<List<Guest>> dailyGuests = breakfastGuestService.getOrderedGuestForDay(currentDay);
            buffetService.serve(dailyGuests);

            statistic.gatherStatistics(breakfastGuestService.numberOfGuestAtGivenDay(currentDay));
            statistic.displayDailyStatistics(currentDay, CURRENCY);
            statistic.clearDailyStatistics();
            currentDay = currentDay.plusDays(1);
        }
        statistic.displaySeasonalStatistics(CURRENCY);

    }
}
