package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.data.Names;
import com.codecool.ehotel.model.Guest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class BreakfastGuestService implements GuestService {
    private final int accommodationDayLimit;

    public BreakfastGuestService(int accommodationDayLimit) {
        this.accommodationDayLimit = accommodationDayLimit;
    }

    @Override
    public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {
        Names names = new Names();
        int maximumDayToReserve = calculateMaximumDayToReserve(seasonStart,seasonEnd);
        String randomName = names.getRandomName();
        System.out.println(maximumDayToReserve+"\n"+randomName);
        return null;
    }

    @Override
    public Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
        return null;
    }

    private int calculateMaximumDayToReserve(LocalDate minuendDate, LocalDate subtrahendDate) {
        long daysBetween = ChronoUnit.DAYS.between(minuendDate, subtrahendDate);
        return accommodationDayLimit > daysBetween ? (int) daysBetween : accommodationDayLimit;
    }
}
