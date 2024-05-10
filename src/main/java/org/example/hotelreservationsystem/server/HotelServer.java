/**********************************************
 AUTHOR
 Last Name:SAINI
 First Name:HASHMEET SINGH
 **********************************************/

package org.example.hotelreservationsystem.server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

/************ STEP-1 ***********/
// Please run this file before running HotelApplication.java file.
// This class serves as the server for my application. It has the database script in itself that will run initialize the
// database when run. It creates the tables needed and populates the room table with 20 rooms that the resort will
// offer. It will also populate the login table with 2 usernames and passwords for logging in. To test the application

public class HotelServer extends Application{
    private static int PORT = 8080;
    private ServerSocket ss;

    private TextArea ta = new TextArea();
    private Hashtable outputStream = new Hashtable();

    @Override
    public void start(Stage ps) throws Exception {

        ta.setWrapText(true);

        Scene sc = new Scene(new ScrollPane(ta), 350, 200);
        ps.setTitle("Hotel Server");
        ps.setScene(sc);
        ps.show();

        loadDatabase();

        new Thread(() -> listen()).start();
    }

    // This function loads the database to create all necessary tables and populate the romms and login tables.
    private void loadDatabase(){
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:D:\\SENECA\\SEM5\\APD_545\\Apd project\\HotelReservationSytem\\HotelReservationSystem\\src\\main\\java\\org\\example\\hotelreservationsystem\\server\\hotel.db");
             Statement stm = con.createStatement();) {
            System.out.println(stm.toString());

            String query = "CREATE TABLE IF NOT EXISTS log_in (" +
                    "username VARCHAR(50) PRIMARY KEY," +
                    "password VARCHAR(100)" +
                    ");";
            boolean result = stm.execute(query);
            if (!result) {
                System.out.println("Table log_in created successfully.");
            } else {
                System.out.println("Table log_in creation failed.");
            }

            String checkUserQuery = "SELECT COUNT(*) FROM log_in WHERE username='admin1';";
            ResultSet res = stm.executeQuery(checkUserQuery);
            res.next();
            int count = res.getInt(1);

            if (count == 0) {
                String insertUserQuery = "INSERT INTO log_in (username, password) VALUES ('admin1','password1')";
                stm.execute(insertUserQuery);
                System.out.println("admin1 created");

                insertUserQuery = "INSERT INTO log_in (username, password) VALUES ('admin2', 'password2');";
                stm.execute(insertUserQuery);
                System.out.println("admin2 created");
            } else {
                System.out.println("admin1 exists");
                System.out.println("admin2 exists");
            }

            query = "CREATE TABLE IF NOT EXISTS rooms(" +
                    "id INTEGER PRIMARY KEY," +
                    "type TEXT," +
                    "rate REAL," +
                    "isBooked BOOLEAN," +
                    "maxOccupancy INTEGER" +
                    ");";

            result = stm.execute(query);
            if (!result) {
                System.out.println("Table rooms created successfully");

                String checkEmptyQuery = "SELECT COUNT(*) FROM rooms;";
                res = stm.executeQuery(checkEmptyQuery);
                res.next();
                count = res.getInt(1);

                if (count == 0) {
                    String insertDataQuery = "INSERT INTO rooms (type, rate, isBooked, maxOccupancy) VALUES " +
                            "('single', 150, false, 2)," +
                            "('single', 150, false, 2)," +
                            "('single', 150, false, 2)," +
                            "('single', 150, false, 2)," +
                            "('single', 150, false, 2)," +
                            "('single', 150, false, 2)," +
                            "('single', 150, false, 2)," +
                            "('single', 150, false, 2)," +

                            "('double', 350, false, 4)," +
                            "('double', 350, false, 4)," +
                            "('double', 350, false, 4)," +
                            "('double', 350, false, 4)," +
                            "('double', 350, false, 4)," +
                            "('double', 350, false, 4)," +

                            "('delux', 450, false, 4)," +
                            "('delux', 450, false, 4)," +
                            "('delux', 450, false, 4)," +
                            "('delux', 450, false, 4)," +

                            "('pentHouse', 700, false, 4)," +
                            "('pentHouse', 700, false, 4);";
                    stm.execute(insertDataQuery);
                    System.out.println("Rooms table successfully populated.");
                }
            }else {
                System.out.println("Table rooms creation failed.");
            }
            query = "CREATE TABLE IF NOT EXISTS guests (" +
                    "id INTEGER PRIMARY KEY," +
                    "fName VARCHAR(50)," +
                    "lName VARCHAR(50)," +
                    "address VARCHAR(100)," +
                    "phone VARCHAR(15)," +
                    "email VARCHAR(100));";
            result = stm.execute(query);
            if (!result) {
                System.out.println("Table guests created successfully.");
            } else {
                System.out.println("Table guests creation failed.");
            }

            query = "CREATE TABLE IF NOT EXISTS bookings(" +
                    "id INTEGER PRIMARY KEY," +
                    "guest_id INTEGER," +
                    "num_days INTEGER," +
                    "num_rooms INTEGER," +
                    "check_in_date DATE," +
                    "is_confirmed BOOLEAN," +
                    "num_guests INTEGER," +
                    "FOREIGN KEY (guest_id) REFERENCES guests(id)" +
                    ");";
            result = stm.execute(query);
            if (!result) {
                System.out.println("bookings table created successfully.");
            } else {
                System.out.println("bookings table creation unsuccessful.");
            }

            query = "CREATE TABLE IF NOT EXISTS booking_rooms(" +
                    "booking_id INT," +
                    "room_id INT," +
                    "PRIMARY KEY (booking_id, room_id)," +
                    "FOREIGN KEY (booking_id) REFERENCES bookings(id)," +
                    "FOREIGN KEY (room_id) REFERENCES rooms(id)" +
                    ");";

            result= stm.execute(query);
            if(!result) {
                System.out.println("Booking_rooms table created successfully.");
            }else{
                System.out.println("Booking_rooms table creation unsuccessful.");
            }

            query = "CREATE TABLE IF NOT EXISTS bills(" +
                    "id INTEGER PRIMARY KEY," +
                    "booking_id INTEGER," +
                    "discount REAL," +
                    "price REAL, " +
                    "FOREIGN KEY (booking_id) REFERENCES bookings(id)" +
                    ");";
            result = stm.execute(query);
            if (!result) {
                System.out.println("Bills table created successfully.");
            } else {
                System.out.println("Bills table creation unsuccessful.");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void listen() {
        try {
            ss = new ServerSocket(PORT);

            Platform.runLater(() -> ta.appendText("Server started - " + new java.util.Date() + "\n"));


            while (true) {
                Socket socket = ss.accept();
                Platform.runLater(() -> ta.appendText("Connected: " + socket + " - " + new Date() + "\n"));

                DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

                outputStream.put(socket, dout);

                new ServerThread(this, socket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Enumeration getOutputStreams() {
        return outputStream.elements();
    }

    void sendToAll(String message) {
        for (Enumeration e = getOutputStreams(); e.hasMoreElements(); ) {
            DataOutputStream dout = (DataOutputStream) e.nextElement();
            try {
                dout.writeUTF(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Multi-Threaded server class.
    class ServerThread extends Thread
    {
        private HotelServer server;
        private Socket socket;

        public ServerThread(HotelServer server, Socket socket)
        {
            this.server = server;
            this.socket = socket;
            start();
        }

        public void run()
        {
            try {
                DataInputStream din = new DataInputStream(socket.getInputStream());
                while (true)
                {
                    String st = din.readUTF();
                    server.sendToAll(st);

                    ta.appendText(st + "\n");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
