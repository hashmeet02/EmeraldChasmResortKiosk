package org.example.hotelreservationsystem.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookRoomsViewController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField numDaysTxt, numRoomsTxt;

    @FXML
    private Slider numGuestSlider;

    // Sets the listeners for all controls.
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        numGuestSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            int numGuests= newValue.intValue();
            int minRooms= (int) Math.ceil((double) numGuests / 4);
            numRoomsTxt.setText(String.valueOf(minRooms));
            int maxRooms= numGuests;
        }));

        numDaysTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    numDaysTxt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        numRoomsTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    numRoomsTxt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    // Function to display alert boxes/ errors to users
    private void showError(String head, String content){
        Alert alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(head);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Does the validation when select rooms button is clicked and if successful, user is taken to the select rooms
    // page.
    public void onSelectRooms(ActionEvent actionEvent) throws IOException {
        int numGuests = (int) numGuestSlider.getValue();

        int numDays;
        try{
            numDays= Integer.parseInt(numDaysTxt.getText());
        }catch( NumberFormatException e){
            showError("Invalid Number of Days", "Number of Days not Integer convertible");
            return;
        }

        int numRooms;
        try{
            numRooms = Integer.parseInt(numRoomsTxt.getText());
        }catch(NumberFormatException e){
            showError("Invalid Number of Rooms", "Number of Rooms not Integer convertible");
            return;
        }

        int minRooms= (int) Math.ceil((double) numGuests / 4);
        LocalDate date = datePicker.getValue();

        if(numRooms<minRooms){
            showError("Invalid number of rooms", "Number of rooms must be greater than " + minRooms + ".");
        } else if (numRooms>numGuests) {
            showError("Invalid number of rooms", "Number of rooms must be less than "+ numGuests + ".");
        } else if(date ==null){
            showError("Empty Date", "Please select a date to proceed.");
        } else if ( date.isBefore(LocalDate.now())) {
            showError("Invalid Date", "Date can't be in the past. Please select a future date.");
        }
        else{
            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/org/example/hotelreservationsystem/select-rooms-view.fxml"));
            Parent root = fxmlLoader.load();

            SelectRoomsViewController selectRoomsViewController= fxmlLoader.getController();
            selectRoomsViewController.initialize(numRooms, numDays, date, numGuests);

            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 671, 568);
            stage.setTitle("Select Rooms");
            stage.setScene(scene);
            stage.show();
        }
    }

    // Handler for Home button to take user back to the home page if clicked.
    public void onHome(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/main-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 431);
        stage.setTitle("Emerald Chasm Resort!");
        stage.setScene(scene);
        stage.show();
    }
}
