package org.example.hotelreservationsystem.models;

import org.example.hotelreservationsystem.abstractClasses.Room;

public class SingleRoom extends Room {
        static final int MAX_OCCUPANCY=2;
        static final String TYPE="single";
        static final double RATE=150.00;

        public SingleRoom(int id, boolean isBooked) {
            super(id, TYPE, RATE, isBooked, MAX_OCCUPANCY);
        }
}
