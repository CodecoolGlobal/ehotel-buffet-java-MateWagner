package com.codecool.ehotel.service.guest;
import com.codecool.ehotel.model.Guest;
import org.junit.Assert;
import  org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    BreakfastGuestService breakfastGuestService = new BreakfastGuestService(7);
    @Test
    void generateRandomGuest() {
        Guest guest = breakfastGuestService.generateRandomGuest(LocalDate.parse("2000-01-01"),LocalDate.parse("2000-01-01"));
        assertTrue(guest.checkIn().isEqual(guest.checkOut()));
    }

    @Test
    void dateOverflow(){
        LocalDate originDate = LocalDate.parse("2000-12-30");
        LocalDate newDate = originDate.plusDays(3);
        assertEquals(LocalDate.parse("2001-01-02"),newDate);
    }
}
