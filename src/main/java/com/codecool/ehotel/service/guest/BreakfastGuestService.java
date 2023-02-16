package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.data.Names;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.GuestsAtDay;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BreakfastGuestService implements GuestService {
    private final int accommodationDayLimit;
    private final LocalDate seasonStart;
    private final LocalDate seasonEnd;
    private final int cycleAmount;
    private final List<GuestsAtDay> guestsAtSeason;
    private final Random random = new Random();

    public BreakfastGuestService(LocalDate seasonStart, LocalDate seasonEnd, int numberOfGuests, int accommodationDayLimit, int cycleAmount) {
        this.seasonStart = seasonStart;
        this.seasonEnd = seasonEnd;
        this.accommodationDayLimit = accommodationDayLimit;
        this.cycleAmount = cycleAmount;

        List<Guest> unOrderedGuests = generateRandomGuests(numberOfGuests);
        guestsAtSeason = orderGuestsToDays(unOrderedGuests);

    }

    public List<List<Guest>> getOrderedGuestForDay(LocalDate date){
        List<List<Guest>> guestsForDay = new ArrayList<>();
        for (GuestsAtDay guests : guestsAtSeason){
            if(guests.date().isEqual(date)){
                guestsForDay = guests.guestsAtDay();
            }
        }
        return guestsForDay;
    }

    private List<GuestsAtDay> orderGuestsToDays(List<Guest> unOrderedGuests) {
        int numberOfDays = calculateNumberOfDaysBetweenDates(seasonStart, seasonEnd);
        LocalDate date  = seasonStart;
        List<GuestsAtDay> guestsAtSeason = new ArrayList<>();
        for (int i = 0; i < numberOfDays; i++) {
            List<List<Guest>> guestAtDay = orderGuestsToCycles(getGuestsForDay(unOrderedGuests,date));

            GuestsAtDay guestsAndDay = new GuestsAtDay(date,guestAtDay);
            guestsAtSeason.add(guestsAndDay);
            date = date.plusDays(1);
        }
        return guestsAtSeason;
    }

    private List<List<Guest>> orderGuestsToCycles(Set<Guest> guestsForDay) {
        List<List<Guest>> guestsAtCycles = new ArrayList<>();
        List<Map<Integer,Guest>> guestsWithRandomCycleIndex = generateRandomCycleIndexToGuests(guestsForDay);

        for(int i = 0; i< cycleAmount; i++){
            List<Guest> guestsAtCycle = new ArrayList<>();

            for (Map<Integer,Guest> guestWithCycleIndex: guestsWithRandomCycleIndex){
                if(guestWithCycleIndex.containsKey(i)){
                    guestsAtCycle.add(guestWithCycleIndex.get(i));
                }
            }

            guestsAtCycles.add(guestsAtCycle);
        }

        return guestsAtCycles;
    }

    private List<Map<Integer, Guest>> generateRandomCycleIndexToGuests(Set<Guest> guests) {
        List<Map<Integer,Guest>> guestsWithIndex = new ArrayList<>();
        for (Guest guest:guests) {
            Map<Integer,Guest> guestWithIndex = new HashMap<>();
            guestWithIndex.put(random.nextInt(cycleAmount),guest);
            guestsWithIndex.add(guestWithIndex);
        }
        return guestsWithIndex;
    }

    private int calculateNumberOfDaysBetweenDates(LocalDate start, LocalDate end) {
        return (int) ChronoUnit.DAYS.between(start, end) + 1;
    }

    private List<Guest> generateRandomGuests(int numberOfGuests) {
        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < numberOfGuests; i++) {
            Guest guest = generateRandomGuest(seasonStart, seasonEnd);
            guests.add(guest);
        }
        return guests;
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
        if (maximumDayToReserve == 1) {
            return new LocalDate[]{seasonStart, seasonEnd};
        }

        int reservationDayAmount = random.nextInt(maximumDayToReserve);
        int seasonDuration = calculateNumberOfDaysBetweenDates(seasonStart,seasonEnd);
        if (reservationDayAmount != seasonDuration) {
            long differenceOfSeasonStartAndReservationStartingDay = random.nextInt(seasonDuration - reservationDayAmount);
            LocalDate periodStartingDate = seasonStart.plusDays(differenceOfSeasonStartAndReservationStartingDay);
            LocalDate periodEndingDate = periodStartingDate.plusDays(reservationDayAmount);
            return new LocalDate[]{periodStartingDate, periodEndingDate};
        } else {
            LocalDate periodEndingDate = seasonStart.plusDays(reservationDayAmount);
            return new LocalDate[]{seasonStart, periodEndingDate};
        }

    }

    private int calculateMaximumDayToReserve(LocalDate minuendDate, LocalDate subtrahendDate) {
        long days = calculateNumberOfDaysBetweenDates(minuendDate, subtrahendDate);
        return accommodationDayLimit > days ? (int) days : accommodationDayLimit;
    }

    private GuestType generateRandomGuestType() {
        GuestType[] guestTypes = GuestType.values();
        return guestTypes[random.nextInt(guestTypes.length)];
    }
}
