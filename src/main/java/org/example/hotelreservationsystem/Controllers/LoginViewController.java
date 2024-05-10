package org.example.hotelreservationsystem.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginViewController {

    @FXML
    private PasswordField txtPass;

    @FXML
    private TextField txtUser;

    // Goes to the database to check if the credentials entered by the user match an existing login credential. If yes
    // the user application takes the user to the admin view.
    @FXML
    void onLogin(ActionEvent event) {
        String username = txtUser.getText().trim();
        String password = txtPass.getText().trim();

        if(username.isEmpty() || password.isEmpty()){
            showWarning("Missing Username/Password.", "Please enter Username and Password to proceed. These fields " +
                    "cannot be empty.");
            return;
        }

        try(Connection con= DriverManager.getConnection("jdbc:sqlite:D:\\SENECA\\SEM5\\APD_545\\Apd project\\" +
                "HotelReservationSytem\\HotelReservationSystem\\src\\main\\java\\org\\example\\hotelreservationsystem" +
                "\\server\\hotel.db")){
            String query= "SELECT * FROM log_in WHERE username = ? AND password = ?";
            try(PreparedStatement pstm= con.prepareStatement(query)){
                pstm.setString(1,username);
                pstm.setString(2,password);

                try(ResultSet rs=pstm.executeQuery()){
                    if(rs.next()){
                        try{
                            openAdminMenuView(event);
                        }catch(IOException err){
                            err.printStackTrace();
                        }
                    }else{
                        showWarning("Invalid Username/Password", "No user with with given Username and " +
                                "Password could be found. Please enter a valid Username and Password.");
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void showWarning(String head, String content){
        Alert alert= new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(head);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void openAdminMenuView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/admin-menu-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 431);
        stage.setTitle("Admin Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void onBack(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/hotelreservationsystem/main-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 671, 380);
        stage.setTitle("Emerald Chasm Resort!");
        stage.setScene(scene);
        stage.show();
    }
}
