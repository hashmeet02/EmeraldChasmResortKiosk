package org.example.hotelreservationsystem.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainViewController {
    public void openBookRoomsView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/book-rooms-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 437);
        stage.setTitle("Book Rooms!");
        stage.setScene(scene);
        stage.show();
    }

    public void openLoginView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/login-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 376);
        stage.setTitle("Admin Login!");
        stage.setScene(scene);
        stage.show();
    }
}
