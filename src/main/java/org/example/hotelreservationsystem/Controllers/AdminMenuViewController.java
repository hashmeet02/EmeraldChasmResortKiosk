package org.example.hotelreservationsystem.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminMenuViewController {

    // Handler for Book Rooms button to create a new room booking.
    public void openBookRoomsView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/book-rooms-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 437);
        stage.setTitle("Book Rooms!");
        stage.setScene(scene);
        stage.show();
    }

    // Handler for Bill Service button to search bookings and adding discount to generate bills.
    public void openBillServiceView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/bill-service-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 615);
        stage.setTitle("Bill Service!");
        stage.setScene(scene);
        stage.show();
    }

    // Handler for Current Bookings button to view a list of all current bookings not yet canceled.
    public void openCurrentBookingsView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/current-bookings-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 384);
        stage.setTitle("Current Bookings!");
        stage.setScene(scene);
        stage.show();
    }

    // Handler for Available Rooms button to view a list of rooms not yet occupied by a booking.
    public void openAvailableRoomsView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/available-rooms-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 384);
        stage.setTitle("Available Rooms!");
        stage.setScene(scene);
        stage.show();
    }

    // Handler for ViewGuests button to view a list of previous or current guests.
    public void onViewGuests(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/view-guests-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 384);
        stage.setTitle("Available Rooms!");
        stage.setScene(scene);
        stage.show();
    }

    // Handler for exit button to exit application when clicked.
    public void onExitBtn(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }
}
