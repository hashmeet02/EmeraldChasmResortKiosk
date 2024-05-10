package org.example.hotelreservationsystem.Controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.hotelreservationsystem.abstractClasses.Room;
import org.example.hotelreservationsystem.models.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class BillServiceViewController {

    @FXML
    private TextField bookIdText, discountText;

    @FXML
    private TableView<Room> bookedRoomsTab;

    @FXML
    private Button addDiscountBtn;


    @FXML
    private TableColumn<?, ?> cRoomId, cRoomRate, cRoomType;

    @FXML
    private Label dateLbl, guestNameLbl, totalPerNightLbl;

    private static final String CON_STR = "jdbc:sqlite:D:\\SENECA\\SEM5\\APD_545\\Apd project\\HotelReservationSytem\\HotelReservationSystem\\src\\main\\java\\org\\example\\hotelreservationsystem\\server\\hotel.db";

    private double ogPrice=0;

    private Booking booking;

    // integer checks for bookId and discount text fields.
    public void initialize(){
        bookIdText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    bookIdText.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        discountText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    discountText.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    // Generate a bill and manipulate the database to add in the newly created Bill. Also manipulate the price per night
    // as per the added discount.
    @FXML
    void onAddDiscount(ActionEvent event) {
        String discountInput= discountText.getText().trim();
        if(discountInput.isEmpty()){
            showError("Empty discount %", "Please type a discount% to add discount");
            return;
        }else {
            double discount = Double.parseDouble(discountText.getText());
            if (discount >= 0 && discount <= 25) {
                totalPerNightLbl.setText(String.valueOf(ogPrice));
                double disPrice=ogPrice*(1-(discount/100));
                try (Connection con = DriverManager.getConnection(CON_STR);
                     PreparedStatement checkStm = con.prepareStatement("SELECT * FROM bills WHERE booking_id = ?");
                     PreparedStatement insertStm = con.prepareStatement("INSERT INTO bills (booking_id, discount, price) VALUES (?, ?, ?)")) {

                    checkStm.setInt(1, booking.getId());
                    ResultSet rs = checkStm.executeQuery();

                    if (rs.next()) {
                        // Row with the booking ID exists, perform update
                        System.out.println("Booking ID already exists, performing update.");
                        PreparedStatement updateStm = con.prepareStatement("UPDATE bills SET discount = ?, price = ? WHERE booking_id = ?");
                        updateStm.setDouble(1, discount);
                        updateStm.setDouble(2, disPrice);
                        updateStm.setInt(3, booking.getId());
                        int rowsUpdated = updateStm.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("Update successful!");
                        } else {
                            System.out.println("No records were updated.");
                        }
                    } else {
                        // Row with the booking ID does not exist, perform insert
                        System.out.println("Booking ID does not exist, performing insert.");
                        insertStm.setInt(1, booking.getId());
                        insertStm.setDouble(2, discount);
                        insertStm.setDouble(3, disPrice);
                        int rowsInserted = insertStm.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("Insert successful!");
                        } else {
                            System.out.println("Insert failed.");
                        }
                    }
                    totalPerNightLbl.setText(String.valueOf(disPrice));

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Bill created/updated");
                    alert.setHeaderText(null);
                    alert.setContentText("The bill for booking " + booking.getId() + " has been created/updated.");
                    alert.showAndWait();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                showError("Invalid Discount", "Please enter a discount rate with value between 0 and 25%");
                return;
            }
        }
    }

    // Resets everything to start fresh.
    @FXML
    void onClear(ActionEvent event) {
        bookedRoomsTab.getItems().clear();
        guestNameLbl.setText("-");
        dateLbl.setText("-");
        totalPerNightLbl.setText("-");
        bookIdText.clear();
        discountText.clear();
        addDiscountBtn.setDisable(false);
        discountText.setDisable(false);
    }

    // exits the application.
    @FXML
    void onClose(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    // Function to display alert boxes/ errors to users
    private void showError(String head, String content){
        Alert alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(head);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //take user to admin menu when user clicks on back button.
    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/admin-menu-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 431);
        stage.setTitle("Admin Menu");
        stage.setScene(scene);
        stage.show();
    }

    // Retrieves the data from database with the searched booking id. Also populates the table with the rooms associated
    // with that booking.
    @FXML
    void onSearch(ActionEvent event) {
        String bookIdInput= bookIdText.getText().trim();

        if(bookIdInput.isEmpty()){
            showError("Empty booking Id", "Please type a bookingId to Search a bill");
            return;
        } else{
            try(Connection con= DriverManager.getConnection(CON_STR);
                PreparedStatement stm= con.prepareStatement("SELECT * FROM bookings WHERE id = ?;");
                PreparedStatement billStm = con.prepareStatement("SELECT * FROM bills WHERE booking_id = ?"))
            {
                int bookingId= Integer.parseInt(bookIdInput);
                stm.setInt(1,bookingId);
                ResultSet rs= stm.executeQuery();
                booking= new Booking();
                booking.setId(bookingId);
                boolean isConfirmed=true;

                if(rs.next()){
                    int guestId= rs.getInt("guest_id");
                    LocalDate bookingDate=rs.getDate("check_in_date").toLocalDate();
                    isConfirmed=rs.getBoolean("is_confirmed");
                    if(!isConfirmed){
                        this.addDiscountBtn.setDisable(true);
                        this.discountText.setDisable(true);
                    }
                    else{
                        this.addDiscountBtn.setDisable(false);
                        this.discountText.setDisable(false);
                    }
                    String name="-";
                    try(PreparedStatement guestStm= con.prepareStatement("SELECT fname, lname FROM guests WHERE id=?;")){
                        guestStm.setInt(1,guestId);
                        ResultSet rsGuest= guestStm.executeQuery();
                        if(rsGuest.next()){
                            String fName=rsGuest.getString("fName");
                            String lName=rsGuest.getString("lName");
                            name= fName+ " "+ lName;
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                    guestNameLbl.setText(name);
                    dateLbl.setText(bookingDate.toString());

                    try(PreparedStatement roomsStm= con.prepareStatement(
                            "SELECT  rooms.id, rooms.type, rooms.rate " +
                                    "FROM booking_rooms " +
                                    "JOIN rooms ON booking_rooms.room_id= rooms.id " +
                                    "WHERE booking_rooms.booking_id= ?;"
                    )){
                        roomsStm.setInt(1, bookingId);
                        ResultSet rsRooms= roomsStm.executeQuery();

                        ObservableList<Room> rooms= FXCollections.observableArrayList();
                        double total=0;
                        while(rsRooms.next()){
                            int roomId=rsRooms.getInt("id");
                            String roomType=rsRooms.getString("type");
                            double roomRate=rsRooms.getDouble("rate");
                            total+=roomRate;
                            ogPrice=total;
                            Room room;
                            switch(roomType){
                                case "single":
                                    room = new SingleRoom(roomId, false);
                                    break;
                                case "double":
                                    room = new DoubleRoom(roomId, false);
                                    break;
                                case "delux":
                                    room = new DeluxRoom(roomId, false);
                                    break;
                                case "pentHouse":
                                    room = new PentHouse(roomId, false);
                                    break;
                                default:
                                    room=null;
                            }
                            if(room!=null){
                                rooms.add(room);
                            }

                        }
                        cRoomId.setCellValueFactory(new PropertyValueFactory<>("id"));
                        cRoomType.setCellValueFactory(new PropertyValueFactory<>("type"));
                        cRoomRate.setCellValueFactory(new PropertyValueFactory<>("rate"));

                        bookedRoomsTab.setItems(rooms);
                        totalPerNightLbl.setText(String.valueOf(total));

                        billStm.setInt(1, bookingId);
                        ResultSet billRs = billStm.executeQuery();

                        if (billRs.next()) {
                            double totalPerNight = billRs.getDouble("price");
                            double discount = billRs.getDouble("discount");

                            totalPerNightLbl.setText(String.valueOf(totalPerNight));
                            discountText.setText(String.valueOf((int) discount));
                            System.out.println(String.valueOf((int) discount));
                        } else {
                            discountText.clear();
                        }
                    }
                }else{
                    showError("Invalid Booking ID","These is no Booking with booking Id: " + bookingId);
                }

            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
