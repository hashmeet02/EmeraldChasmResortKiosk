package org.example.hotelreservationsystem.models;

import org.example.hotelreservationsystem.abstractClasses.Room;

public class DoubleRoom extends Room {
    static final int MAX_OCCUPANCY=4;
    static final String TYPE="double";
    static final double RATE=350.00;

    public DoubleRoom(int id, boolean isBooked) {
        super(id, TYPE, RATE, isBooked, MAX_OCCUPANCY);
    }
}
