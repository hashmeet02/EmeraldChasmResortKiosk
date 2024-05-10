package org.example.hotelreservationsystem.Controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.hotelreservationsystem.abstractClasses.Room;
import org.example.hotelreservationsystem.models.Booking;
import org.example.hotelreservationsystem.models.Guest;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class CurrentBookingsViewController {

    @FXML
    private TableColumn<?, ?> cBookId, cDate, cGuest, cNumDays, cNumRooms ;

    @FXML
    private TableView<Booking> curBookTable;

    @FXML
    private Label numBookingsLbl;

    private ObservableList<Booking> bookingList= FXCollections.observableArrayList();

    private int numBookings=0;

    // Retrieves the bookings from database that are active (not canceled.) and populates the table accordingly
    @FXML
    public void initialize(){
        cBookId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cGuest.setCellValueFactory(new PropertyValueFactory<>("guestFullName"));
        cNumRooms.setCellValueFactory(new PropertyValueFactory<>("numRooms"));
        cDate.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        cNumDays.setCellValueFactory(new PropertyValueFactory<>("numDays"));

        try(Connection con= DriverManager.getConnection("jdbc:sqlite:D:\\SENECA\\SEM5\\APD_545\\Apd project\\HotelReservationSytem\\HotelReservationSystem\\src\\main\\java\\org\\example\\hotelreservationsystem\\server\\hotel.db");
            Statement stm=con.createStatement()){

            String query= "SELECT bookings.id, bookings.check_in_date, bookings.num_days, bookings.num_rooms, guests.fName, guests.lName " +
                    "FROM bookings " +
                    "JOIN guests ON bookings.guest_id= guests.id " +
                    "WHERE bookings.is_confirmed=true;";
            ResultSet rs =stm.executeQuery(query);

            numBookings=0;
            while(rs.next()){
                numBookings++;
                int id=rs.getInt("id");
                LocalDate date=rs.getDate("check_in_date").toLocalDate();
                int numDays=rs.getInt("num_days");
                int numRooms=rs.getInt("num_rooms");
                String guestName=rs.getString("fName")+ " " + rs.getString("lName");

                Guest guest= new Guest();
                Booking booking= new Booking();
                guest.setFName(rs.getString("fName"));
                guest.setLName(rs.getString("lName"));
                booking.setId( id );
                booking.setCheckInDate(date);
                booking.setNumDays(numDays);
                booking.setNumRooms(numRooms);
                booking.setGuestFullName(guestName);
                booking.setGuest(guest);
                bookingList.add(booking);
            }
            numBookingsLbl.setText(String.valueOf(numBookings));

        }catch(SQLException e){
            e.printStackTrace();
        }
        curBookTable.setItems(bookingList);
    }
    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/admin-menu-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 431);
        stage.setTitle("Admin Menu");
        stage.setScene(scene);
        stage.show();
    }

    private void showError(String head, String content){
        Alert alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setResizable(true);
        alert.setHeaderText(head);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void onCancel(ActionEvent actionEvent) {
        Booking selectedBooking = (Booking) curBookTable.getSelectionModel().getSelectedItem();
        if(selectedBooking==null){
            showError("No booking selected", "Please select a booking to cancel ");
        }else{
            selectedBooking.setIsConfirmed(false);

            try (Connection con = DriverManager.getConnection("jdbc:sqlite:D:\\SENECA\\SEM5\\APD_545\\Apd project\\HotelReservationSytem\\HotelReservationSystem\\src\\main\\java\\org\\example\\hotelreservationsystem\\server\\hotel.db");
                 PreparedStatement updateRoomsStm = con.prepareStatement("UPDATE rooms " +
                         "SET isBooked = false " +
                         "WHERE id IN (SELECT room_id FROM booking_rooms WHERE booking_id = ?)");
                 PreparedStatement updateBookingStm = con.prepareStatement("UPDATE bookings SET is_confirmed = false " +
                         "WHERE id = ?")) {

                // Set the parameter for the update query related to rooms
                updateRoomsStm.setInt(1, selectedBooking.getId());
                updateRoomsStm.executeUpdate(); // Execute the update query for rooms using prepared statement

                // Set the parameter for the update query related to bookings
                updateBookingStm.setInt(1, selectedBooking.getId());
                updateBookingStm.executeUpdate(); // Execute the update query for bookings using prepared statement

            } catch (SQLException e) {
                e.printStackTrace();
            }

            int index = curBookTable.getSelectionModel().getSelectedIndex();
            bookingList.remove(index);
            numBookings--;
            numBookingsLbl.setText(String.valueOf(numBookings));
            curBookTable.setItems(bookingList);
        }
    }
}
