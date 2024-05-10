package org.example.hotelreservationsystem.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.hotelreservationsystem.abstractClasses.Room;
import org.example.hotelreservationsystem.models.Booking;
import org.example.hotelreservationsystem.models.Guest;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class GuestDetailsViewController {

    @FXML
    private TextField addressText, emailTxt, firstNameTxt, lastNameTxt, phoneTxt;

    private int numGuests;
    private int numDays;
    private int numRooms;
    private LocalDate checkInDate;
    private ObservableList<Room> selectedRooms;


    // Listeners for only accepting valid information in the text boxes.
    public void initialize(int rooms, int days, int guests, LocalDate date, ObservableList<Room> _selectedRooms) {

        numGuests=guests;
        numDays=days;
        numRooms=rooms;
        checkInDate=date;
        selectedRooms=_selectedRooms;

        firstNameTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[a-zA-Z\\s]*")) {
                    firstNameTxt.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
                }
            }
        });
        lastNameTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[a-zA-Z\\s]*")) {
                    lastNameTxt.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
                }
            }
        });
        phoneTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Remove non-digit characters
                String cleanedValue = newValue.replaceAll("[^\\d]", "");

                // Check if the cleaned value has exactly 10 digits
                if (cleanedValue.length() <= 10) {
                    phoneTxt.setText(cleanedValue);
                } else {
                    // Truncate to 10 digits if more than 10 digits are entered
                    phoneTxt.setText(cleanedValue.substring(0, 10));
                }
            }
        });

    }

    // validation once the user confirms their booking. Proceed to manipulate database creating new booking, updating
    // rooms and adding a new guest.
    @FXML
    void OnConfirmBooking(ActionEvent event) throws IOException{
        String fName=firstNameTxt.getText().trim();
        String lName=lastNameTxt.getText().trim();
        String address=addressText.getText().trim();
        String phone= phoneTxt.getText().trim();
        String email=emailTxt.getText().trim();

        if(fName.isEmpty()){
            showError("Empty First Name", "First Name can't be empty");
            return;
        }

        if(lName.isEmpty()){
            showError("Empty Last Name", "Last Name can't be empty");
            return;
        }

        if(address.isEmpty()){
            showError("Empty Address", "Address can't be empty");
            return;
        }

        if(phone.isEmpty()){
            showError("Empty Phone number", "Phone number can't be empty");
            return;
        } else if (phone.length()!=10) {
            showError("Invalid Phone Numebr", "Phone number must be 10 digits long.");
        }

        if(email.isEmpty()){
            showError("Empty E-mail", "E-mail can't be empty");
            return;
        } else if (!isValidEmail(email)) {
            showError("Invalid E-mail", "The email entered is not of the correct format. Example of " +
                    "E-mail is example@domain.com");
            return;
        }
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:D:\\SENECA\\SEM5\\APD_545\\Apd project\\HotelReservationSytem\\HotelReservationSystem\\src\\main\\java\\org\\example\\hotelreservationsystem\\server\\hotel.db");
             Statement stm = con.createStatement();)
        {
            for( Room room : selectedRooms){
                String updateQuery="UPDATE rooms SET isBooked = true WHERE id = " + room.getId() +";";
                System.out.println(updateQuery);
                stm.executeUpdate(updateQuery);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        Guest guest= new Guest(fName, lName, address, phone, email);
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:D:\\SENECA\\SEM5\\APD_545\\Apd project\\HotelReservationSytem\\HotelReservationSystem\\src\\main\\java\\org\\example\\hotelreservationsystem\\server\\hotel.db");
             PreparedStatement stmt = con.prepareStatement("INSERT INTO guests (fName, lName, address, phone, email) VALUES (?, ?, ?, ?, ?);"))
        {
            stmt.setString(1,fName);
            stmt.setString(2,lName);
            stmt.setString(3,address);
            stmt.setString(4,phone);
            stmt.setString(5,email);

            int rowsInserted=stmt.executeUpdate();
            if(rowsInserted>0){
                ResultSet key=stmt.getGeneratedKeys();
                guest.setId(key.getInt(1));
            }
            System.out.println("User saved successfully.");

            Booking booking= new Booking(numGuests, numDays, numRooms, checkInDate, guest, selectedRooms, true);

            booking.save();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Booking complete");
            alert.setHeaderText(null);
            alert.setContentText("The booking for " + fName + " has now been confirmed.");
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/main-view.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 671, 380);
            stage.setTitle("Emerald Chasm Resort!");
            stage.setScene(scene);
            stage.show();

        }catch(SQLException e){
            e.printStackTrace();
        }



    }

    // Check to see if the email is valid.
    private boolean isValidEmail(String email)
    {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        return Pattern.compile(emailPattern).matcher(email).matches();
    }

    private void showError(String head, String content){
        Alert alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(head);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/org/example/hotelreservationsystem/select-rooms-view.fxml"));
        Parent root = fxmlLoader.load();

        SelectRoomsViewController selectRoomsViewController= fxmlLoader.getController();
        selectRoomsViewController.initialize(numRooms, numDays, checkInDate, numGuests);

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 568);
        stage.setTitle("Select Rooms");
        stage.setScene(scene);
        stage.show();
    }
}
