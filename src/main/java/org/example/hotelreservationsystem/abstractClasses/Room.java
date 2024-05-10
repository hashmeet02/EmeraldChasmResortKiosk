package org.example.hotelreservationsystem.abstractClasses;

public abstract class Room {
    private int id;
    private String type;
    private double rate;
    private boolean isBooked;
    private int maxOccupancy;

    public Room(int id, String type, double rate, boolean isBooked, int maxOccupancy) {
        this.id = id;
        this.type = type;
        this.rate = rate;
        this.isBooked = isBooked;
        this.maxOccupancy=maxOccupancy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }
}
