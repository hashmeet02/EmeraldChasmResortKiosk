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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.hotelreservationsystem.models.Booking;
import org.example.hotelreservationsystem.models.Guest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class ViewGuestsViewController {

    @FXML
    private TableColumn<?, ?> cAddress, cEmail, cFName,cLName, cPhone;
    @FXML
    private TableView<Guest> guestsTable;
    @FXML
    private TextField guestSearchText;

    private ObservableList<Guest> guests = FXCollections.observableArrayList();

    // populates the guests table with information about guests received from the database.
    @FXML
    public void initialize(){

        cFName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        cLName.setCellValueFactory(new PropertyValueFactory<>("lName"));
        cAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        cPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try(Connection con= DriverManager.getConnection("jdbc:sqlite:D:\\SENECA\\SEM5\\APD_545\\Apd project\\HotelReservationSytem\\HotelReservationSystem\\src\\main\\java\\org\\example\\hotelreservationsystem\\server\\hotel.db");
            Statement stm=con.createStatement()){

            String query= "SELECT * FROM guests; ";
            ResultSet rs =stm.executeQuery(query);

            while(rs.next()){
                String fName=rs.getString("fName");
                String lName=rs.getString("lName");
                String address=rs.getString("address");
                String phone=rs.getString("phone");
                String email=rs.getString("email");

                Guest guest= new Guest(fName,lName,address,phone,email);
                guests.add(guest);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        guestsTable.setItems(guests);

    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/admin-menu-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 431);
        stage.setTitle("Admin Menu");
        stage.setScene(scene);
        stage.show();
    }

    //Listener that searches for the guest according to the name or phone number entered in the search box.
    @FXML
    void onSearch(ActionEvent event) {
        String searchText=this.guestSearchText.getText();

        if(searchText.isEmpty()){
            this.guestsTable.setItems(guests);
        }

        ObservableList<Guest> resultGuests= FXCollections.observableArrayList();
        for( Guest guest: guests){
            if(guest.getFName().toLowerCase().contains(searchText.toLowerCase()) ||
                guest.getLName().toLowerCase().contains(searchText.toLowerCase()) ||
                guest.getPhone().contains(searchText)
            ){
                resultGuests.add(guest);
            }
        }
        if(resultGuests.size()==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error: no guests found");
            alert.setHeaderText("Error: no guests found.");
            alert.setContentText("Error: no guests found.");
            alert.showAndWait();
            return;
        }
        this.guestsTable.setItems(resultGuests);
        this.guestSearchText.setText("");


    }


}
