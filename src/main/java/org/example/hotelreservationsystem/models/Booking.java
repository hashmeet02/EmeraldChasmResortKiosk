package org.example.hotelreservationsystem.models;

import javafx.collections.ObservableList;
import org.example.hotelreservationsystem.abstractClasses.Room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Booking {
    private int id;
    private int numGuests;
    private int numDays;
    private int numRooms;
    private LocalDate checkInDate;
    private Guest guest;
    private ObservableList<Room> rooms;
    private String guestFullName;
    private Boolean isConfirmed;

    public String getGuestFullName() {
        return guestFullName;
    }

    public void setGuestFullName( String fullName) {
        this.guestFullName = fullName;
    }

    public Booking(){}
    public Booking( int numGuests, int numDays, int numRooms, LocalDate checkInDate, Guest guest, ObservableList<Room> rooms, boolean isConfirmed) {
        this.numGuests = numGuests;
        this.numDays = numDays;
        this.numRooms = numRooms;
        this.checkInDate = checkInDate;
        this.guest = guest;
        this.rooms = rooms;
        this.isConfirmed=isConfirmed;
    }

    public void save(){
        try(Connection con= DriverManager.getConnection("jdbc:sqlite:D:\\SENECA\\SEM5\\APD_545\\Apd project\\HotelReservationSytem\\HotelReservationSystem\\src\\main\\java\\org\\example\\hotelreservationsystem\\server\\hotel.db")){
            con.setAutoCommit(false);
            int bookingID;
            try (PreparedStatement stm = con.prepareStatement("INSERT INTO bookings (guest_id, check_in_date, num_days, num_guests, num_rooms, is_confirmed) VALUES (?, ?, ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {
                stm.setInt(1, guest.getId());
                stm.setDate(2, java.sql.Date.valueOf(checkInDate));
                stm.setInt(3,numDays);
                stm.setInt(4,numGuests);
                stm.setInt(5,numRooms);
                stm.setBoolean(6, isConfirmed);
                stm.executeUpdate();

                try(var generateKeys=stm.getGeneratedKeys()){
                    if(generateKeys.next()){
                        bookingID=generateKeys.getInt(1);

                    }else{
                        throw new SQLException("Creating booking failed.");
                    }
                }
            }
            for(Room room: rooms){
                try(PreparedStatement stm=con.prepareStatement("INSERT INTO booking_rooms(booking_id, room_id) VALUES (?,?);")){
                    stm.setInt(1, bookingID);
                    stm.setInt(2, room.getId());
                    stm.executeUpdate();
                }
            }
            con.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumGuests() {
        return numGuests;
    }

    public void setNumGuests(int numGuests) {
        this.numGuests = numGuests;
    }

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public ObservableList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ObservableList<Room> rooms) {
        this.rooms = rooms;
    }


    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
}
