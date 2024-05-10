/**********************************************
 Workshop 4and5
 Course:APD - 545
 Last Name:SAINI
 First Name:HASHMEET SINGH
 ID:153070214
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date:March 20th 2024
 **********************************************/

/************ STEP-2 ***********/
// To successfully run the application please run the HotelServer.java file and then proceed to run the
// HotelApplication file. For admin access login using "admin1", "password1" or "admin2", "password2" combinations.
package org.example.hotelreservationsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HotelApplication extends Application {
    private Socket socket;
    private DataOutputStream dout;
    private DataInputStream din;
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8080;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HotelApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 671, 380);
        stage.setTitle("Emerald Chasm Resort!");
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.show();

        try
        {
            socket = new Socket(SERVER_IP, SERVER_PORT);

            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            System.out.println("connected to server: " + SERVER_IP + " on port: " + SERVER_PORT);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}