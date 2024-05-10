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
import org.example.hotelreservationsystem.models.DeluxRoom;
import org.example.hotelreservationsystem.models.DoubleRoom;
import org.example.hotelreservationsystem.models.PentHouse;
import org.example.hotelreservationsystem.models.SingleRoom;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class SelectRoomsViewController {

    @FXML
    private TableView<Room> avblRoomsTable;

    @FXML
    private Label remainRoomsLbl;

    @FXML
    private TableView<Room> selectedRoomsTable;

    private ObservableList<Room> selectedRoomsList = FXCollections.observableArrayList();
    private ObservableList<Room> avblRoomsList = FXCollections.observableArrayList();
    private int numRooms;
    private int orgNumRooms;
    private int orgNumGuests;
    private int numDays;
    private LocalDate checkInDate;
    private int numGuests;


    // goes to the database, gets a list of available rooms and populates the available rooms table with them.
    public void initialize(int rooms, int days, LocalDate date, int guests) {
        this.numRooms=rooms;
        this.orgNumRooms=rooms;
        this.numDays=days;
        this.checkInDate=date;
        this.numGuests=guests;
        this.orgNumGuests=guests;
        remainRoomsLbl.setText(String.valueOf(numRooms));

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:D:\\SENECA\\SEM5\\APD_545\\Apd project\\HotelReservationSytem\\HotelReservationSystem\\src\\main\\java\\org\\example\\hotelreservationsystem\\server\\hotel.db");
             Statement stm = con.createStatement();)
        {
            String query= "SELECT id, type, rate, isBooked, maxOccupancy FROM rooms WHERE isBooked=false;";
            ResultSet rs= stm.executeQuery(query);

            while(rs.next()){
                int id=rs.getInt("id");
                String type=rs.getString("type");
                double rate= rs.getDouble("rate");
                boolean isBooked= rs.getBoolean("isBooked");
                int maxOccupancy=rs.getInt("maxOccupancy");

                Room room;
                switch(type){
                    case "single":
                        room = new SingleRoom(id, isBooked);
                        break;
                    case "double":
                        room = new DoubleRoom(id, isBooked);
                        break;
                    case "delux":
                        room = new DeluxRoom(id, isBooked);
                        break;
                    case "pentHouse":
                        room = new PentHouse(id, isBooked);
                        break;
                    default:
                        room=null;
                }
                if(room!=null){
                    avblRoomsList.add(room);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        TableColumn cId = new TableColumn("Id");
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn cType = new TableColumn("Type");
        cType.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn cRate = new TableColumn("Rate");
        cRate.setCellValueFactory(new PropertyValueFactory<>("rate"));

        TableColumn cMaxOcc = new TableColumn("Max Occupancy");
        cMaxOcc.setCellValueFactory(new PropertyValueFactory<>("maxOccupancy"));

        avblRoomsTable.getColumns().addAll(cId, cType, cRate, cMaxOcc);
        avblRoomsTable.setItems(avblRoomsList);

        TableColumn cId2 = new TableColumn("Id");
        cId2.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn cType2 = new TableColumn("Type");
        cType2.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn cRate2 = new TableColumn("Rate");
        cRate2.setCellValueFactory(new PropertyValueFactory<>("rate"));

        TableColumn cMaxOcc2 = new TableColumn("Max Occupancy");
        cMaxOcc2.setCellValueFactory(new PropertyValueFactory<>("maxOccupancy"));

        selectedRoomsTable.getColumns().addAll(cId2, cType2, cRate2, cMaxOcc2);
        selectedRoomsTable.setItems(selectedRoomsList);
    }
    private void showError(String head, String content){
        Alert alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setResizable(true);
        alert.setHeaderText(head);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Makes the necessary validation to move the room from the available rooms table to the selected rooms table.
    public void onAdd(ActionEvent actionEvent){
        Room selectedRoom=(Room) avblRoomsTable.getSelectionModel().getSelectedItem();
        int index = avblRoomsTable.getSelectionModel().getSelectedIndex();

        if(numRooms==0){
            showError("Booking limit reached", "You are trying to book more rooms than you previously " +
                    "selected. Please increase the number of rooms to be booked on the previous page to book more " +
                    "rooms.");
        }
        else if(selectedRoom==null){
            showError("No room selected", "Please select a room from the available rooms table to add " +
                    "it to your selected room table.");
        }else{
            selectedRoomsList.add(selectedRoom);
            avblRoomsList.remove(index);
            remainRoomsLbl.setText(String.valueOf(--numRooms));
            System.out.println("Selected rooms list length: " + Integer.toString(selectedRoomsList.size()));
            avblRoomsTable.setItems(avblRoomsList);
            selectedRoomsTable.setItems(selectedRoomsList);
        }
    }

    // Makes the necessary validation to move the room from the selected rooms table to the available rooms table.
    public void onRemove(ActionEvent actionEvent){
        Room selectedRoom=(Room) selectedRoomsTable.getSelectionModel().getSelectedItem();
        int index = selectedRoomsTable.getSelectionModel().getSelectedIndex();

        if(selectedRoom==null){
            showError("No room selected", "Please select a room from the selected rooms table to remove " +
                    "it.");
        }else{
            avblRoomsList.add(selectedRoom);
            selectedRoomsList.remove(index);
            remainRoomsLbl.setText(String.valueOf(++numRooms));
            System.out.println("Selected rooms list length: " + Integer.toString(selectedRoomsList.size()));
            avblRoomsTable.setItems(avblRoomsList);
            selectedRoomsTable.setItems(selectedRoomsList);
        }
    }

    //Check if a valid number of rooms are selected and if yes the user is taken to the guest details view.
    public void onAddGuest(ActionEvent actionEvent) throws IOException {
        int remainingGuests=numGuests;
        for(Room room: selectedRoomsList){
            remainingGuests-=room.getMaxOccupancy();
        }
        if (numRooms>0 || remainingGuests>0){
            showError("Inadequate rooms selected", "Please go to previous page to adjust the number of " +
                    "rooms or guests accordingly.");
        } else{

            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/org/example/hotelreservationsystem/guest-details-view.fxml"));
            Parent root = fxmlLoader.load();

            GuestDetailsViewController guestDetailsViewController= fxmlLoader.getController();
            guestDetailsViewController.initialize(orgNumRooms, numDays, orgNumGuests, checkInDate, selectedRoomsList);

            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 671, 437);
            stage.setTitle("Guest Details");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/book-rooms-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 437);
        stage.setTitle("Book Rooms!");
        stage.setScene(scene);
        stage.show();
    }
}
