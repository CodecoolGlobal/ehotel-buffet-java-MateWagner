package com.codecool.ehotel.model;

import java.time.LocalDate;
import java.util.Objects;

public final class Guest {
    private final String name;
    private final GuestType guestType;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private boolean isHappy = false;

    public Guest(String name, GuestType guestType, LocalDate checkIn, LocalDate checkOut) {
        this.name = name;
        this.guestType = guestType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }


    @Override
    public String toString() {
        return "| " + name + " | " + guestType + " | (" + checkIn + ") - (" + checkOut + ")";
    }

    public String getName() {
        return name;
    }

    public GuestType getGuestType() {
        return guestType;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public boolean isHappy() {
        return isHappy;
    }

    public void setIsHappiness(boolean isHappy) {
        this.isHappy = isHappy;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Guest) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.guestType, that.guestType) &&
                Objects.equals(this.checkIn, that.checkIn) &&
                Objects.equals(this.checkOut, that.checkOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, guestType, checkIn, checkOut);
    }


}
