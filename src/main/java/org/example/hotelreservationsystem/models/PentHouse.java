package org.example.hotelreservationsystem.models;

import org.example.hotelreservationsystem.abstractClasses.Room;

public class PentHouse extends Room {
    static final int MAX_OCCUPANCY=4;
    static final String TYPE="pentHouse";
    static final double RATE=700.00;

    public PentHouse(int id, boolean isBooked) {
        super(id, TYPE, RATE, isBooked, MAX_OCCUPANCY);
    }
}
