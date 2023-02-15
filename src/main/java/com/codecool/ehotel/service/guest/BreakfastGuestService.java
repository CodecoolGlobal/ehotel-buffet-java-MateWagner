package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.data.Names;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class BreakfastGuestService implements GuestService {
    private final int accommodationDayLimit;

    public BreakfastGuestService(int accommodationDayLimit) {
        this.accommodationDayLimit = accommodationDayLimit;
    }

    @Override
    public Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
        Set<Guest> guestsForDay = new HashSet<>();
        for (Guest guest : guests) {
            if ((guest.getCheckIn().isEqual(date) || guest.getCheckIn().isBefore(date) &&
                    (guest.getCheckOut().isEqual(date) || guest.getCheckOut().isAfter(date)))) {
                guestsForDay.add(guest);
            }
        }
        return guestsForDay;
    }

    @Override
    public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {
        Names names = new Names();

        int maximumDayToReserve = calculateMaximumDayToReserve(seasonStart, seasonEnd);
        String randomName = names.getRandomName();
        GuestType guestType = generateRandomGuestType();
        LocalDate[] reservationPeriod = generateRandomReservationPeriodBetweenDates(maximumDayToReserve, seasonStart, seasonEnd);

        return new Guest(randomName, guestType, reservationPeriod[0], reservationPeriod[1]);
    }

    private LocalDate[] generateRandomReservationPeriodBetweenDates(int maximumDayToReserve, LocalDate seasonStart, LocalDate seasonEnd) {
        Random random = new Random();
        if (maximumDayToReserve == 1) {
            return new LocalDate[]{seasonStart, seasonEnd};
        }

        int reservationDayAmount = random.nextInt(maximumDayToReserve);
        int seasonDuration = (int) ChronoUnit.DAYS.between(seasonStart, seasonEnd) + 1;
        if (reservationDayAmount != seasonDuration) {
            long differenceOfSeasonStartAndReservationStartingDay = random.nextInt(seasonDuration - reservationDayAmount);
            LocalDate periodStartingDate = seasonStart.plusDays(differenceOfSeasonStartAndReservationStartingDay);
            LocalDate periodEndingDate = periodStartingDate.plusDays(reservationDayAmount);
            return new LocalDate[]{periodStartingDate, periodEndingDate};
        }
        else {
            LocalDate periodEndingDate = seasonStart.plusDays(reservationDayAmount);
            return new LocalDate[]{seasonStart, periodEndingDate};
        }

    }

    private int calculateMaximumDayToReserve(LocalDate minuendDate, LocalDate subtrahendDate) {
        long days = ChronoUnit.DAYS.between(minuendDate, subtrahendDate) + 1;
        return accommodationDayLimit > days ? (int) days : accommodationDayLimit;
    }

    private GuestType generateRandomGuestType() {
        Random random = new Random();
        GuestType[] guestTypes = GuestType.values();
        return guestTypes[random.nextInt(guestTypes.length)];
    }
}
