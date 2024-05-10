package org.example.hotelreservationsystem.models;

import org.example.hotelreservationsystem.abstractClasses.Room;

public class DeluxRoom extends Room {
    static final int MAX_OCCUPANCY=4;
    static final String TYPE="delux";
    static final double RATE=450.00;

    public DeluxRoom(int id, boolean isBooked) {
        super(id, TYPE, RATE, isBooked, MAX_OCCUPANCY);
    }
}
