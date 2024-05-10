package org.example.hotelreservationsystem.models;

public class Bill {
    private int id;
    private Booking booking;
    private int discount;
    private double price;

    public Bill(int id, Booking booking, int discount, double price) {
        this.id = id;
        this.booking = booking;
        this.discount = discount;
        this.price = price;
    }

    public Bill(Booking booking, int discount, double price) {
        this.booking = booking;
        this.discount = discount;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
