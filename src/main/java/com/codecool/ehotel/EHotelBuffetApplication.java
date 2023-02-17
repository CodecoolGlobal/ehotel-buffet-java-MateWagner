package com.codecool.ehotel;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.service.Statistic;
import com.codecool.ehotel.service.buffet.BreakfastManager;
import com.codecool.ehotel.service.guest.BreakfastGuestService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EHotelBuffetApplication {

    public static void main(String[] args) {
        LocalDate SEASON_START = LocalDate.parse(args[0]);
        LocalDate SEASON_END = LocalDate.parse(args[1]);
        String CURRENCY = "Coins";

        // Initialize services
        Buffet buffet = new Buffet(new ArrayList<>());
        Statistic statistic = new Statistic();
        BreakfastManager buffetService = new BreakfastManager(buffet, statistic, true);

        Buffet buffet2 = new Buffet(new ArrayList<>());
        Statistic statistic2 = new Statistic();
        BreakfastManager buffetService2 = new BreakfastManager(buffet2, statistic2, false);

        // Generate guests for the season
        BreakfastGuestService breakfastGuestService = new BreakfastGuestService(SEASON_START,
                SEASON_END,
                Integer.parseInt(args[2]),
                Integer.parseInt(args[3]),
                Integer.parseInt(args[4])
        );

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

        // Run breakfast advanced buffet
        currentDay = SEASON_START;
        while (currentDay.isBefore(SEASON_END)) {
            List<List<Guest>> dailyGuests = breakfastGuestService.getOrderedGuestForDay(currentDay);
            buffetService2.serve(dailyGuests);

            statistic2.gatherStatistics(breakfastGuestService.numberOfGuestAtGivenDay(currentDay));
            statistic2.displayDailyStatistics(currentDay, CURRENCY);
            statistic2.clearDailyStatistics();
            currentDay = currentDay.plusDays(1);
        }
        statistic2.displaySeasonalStatistics(CURRENCY);

    }
}
