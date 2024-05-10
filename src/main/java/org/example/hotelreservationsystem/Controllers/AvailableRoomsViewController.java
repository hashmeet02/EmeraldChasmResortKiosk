package org.example.hotelreservationsystem.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class AvailableRoomsViewController {
    @FXML
    private Label avlRoomsLbl;

    @FXML
    private TableView<Room> avlRoomsTable;

    @FXML
    private TableColumn<?, ?> cRoomId, cRoomRate, cRoomType;

    public void initialize(){
        // setting columns for the table.
        cRoomId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cRoomType.setCellValueFactory(new PropertyValueFactory<>("type"));
        cRoomRate.setCellValueFactory(new PropertyValueFactory<>("rate"));

        ObservableList<Room> rooms= FXCollections.observableArrayList();

        // retrieving available rooms from the database.
        try(Connection con= DriverManager.getConnection("jdbc:sqlite:D:\\SENECA\\SEM5\\APD_545\\Apd project\\HotelReservationSytem\\HotelReservationSystem\\src\\main\\java\\org\\example\\hotelreservationsystem\\server\\hotel.db");
            PreparedStatement stm=con.prepareStatement("SELECT * FROM rooms WHERE isBooked=false;")){

            ResultSet rs= stm.executeQuery();
            while(rs.next()){
                int id= rs.getInt("id");
                String type= rs.getString("type");

                Room room;
                switch(type){
                    case "single":
                        room = new SingleRoom(id, false);
                        break;
                    case "double":
                        room = new DoubleRoom(id, false);
                        break;
                    case "delux":
                        room = new DeluxRoom(id, false);
                        break;
                    case "pentHouse":
                        room = new PentHouse(id, false);
                        break;
                    default:
                        room=null;
                }
                if(room!=null){
                    rooms.add(room);
                }
            }

            // populating the table with available rooms.
            avlRoomsTable.setItems(rooms);
            avlRoomsLbl.setText(String.valueOf(rooms.size()));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/admin-menu-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 431);
        stage.setTitle("Admin Menu");
        stage.setScene(scene);
        stage.show();
    }

}
