package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.data.Names;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

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

        int maximumDayToReserve = calculateMaximumDayToReserve(seasonStart, seasonEnd);
        String randomName = names.getRandomName();
        GuestType guestType = generateRandomGuestType();
        LocalDate[] reservationPeriod = generateRandomReservationPeriodBetweenDates(maximumDayToReserve, seasonStart, seasonEnd);

        return new Guest(randomName,guestType,reservationPeriod[0],reservationPeriod[1]);
    }

    private LocalDate[] generateRandomReservationPeriodBetweenDates(int maximumDayToReserve, LocalDate seasonStart, LocalDate seasonEnd) {
        Random random = new Random();

        int reservationDayAmount = random.nextInt(1, maximumDayToReserve + 1);
        int daysBetween = (int) ChronoUnit.DAYS.between(seasonStart, seasonEnd);
        long differenceOfSeasonStartAndReservationStartingDay = random.nextInt(1,daysBetween-reservationDayAmount);

        LocalDate periodStartingDate = seasonStart.plusDays(differenceOfSeasonStartAndReservationStartingDay);
        periodStartingDate = manageMonthAndYearOverflow(seasonStart,periodStartingDate);
        LocalDate periodEndingDate = periodStartingDate.plusDays(reservationDayAmount);
        periodEndingDate = manageMonthAndYearOverflow(periodStartingDate,periodEndingDate);
        return new LocalDate[]{periodStartingDate,periodEndingDate};
    }

    private LocalDate manageMonthAndYearOverflow(LocalDate originalDate, LocalDate modifiedDate) {
        if(originalDate.getMonthValue()!= modifiedDate.getMonthValue()){
            modifiedDate = modifiedDate.plusMonths(1);
            if(originalDate.getYear()!=modifiedDate.getYear()){
                modifiedDate = modifiedDate.plusYears(1);
            }
        }
        return modifiedDate;
    }

    @Override
    public Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
        return null;
    }

    private int calculateMaximumDayToReserve(LocalDate minuendDate, LocalDate subtrahendDate) {
        long daysBetween = ChronoUnit.DAYS.between(minuendDate, subtrahendDate);
        return accommodationDayLimit > daysBetween ? (int) daysBetween : accommodationDayLimit;
    }

    private GuestType generateRandomGuestType() {
        Random random = new Random();
        GuestType[] guestTypes = GuestType.values();
        return guestTypes[random.nextInt(guestTypes.length)];
    }
}
