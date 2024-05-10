module org.example.hotelreservationsystem {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.hotelreservationsystem to javafx.fxml;
    opens org.example.hotelreservationsystem.Controllers to javafx.fxml;
    opens org.example.hotelreservationsystem.abstractClasses to javafx.fxml;
    opens org.example.hotelreservationsystem.models to javafx.fxml;

    exports org.example.hotelreservationsystem;
    exports org.example.hotelreservationsystem.Controllers;
    exports org.example.hotelreservationsystem.server;
    exports org.example.hotelreservationsystem.abstractClasses;
    exports org.example.hotelreservationsystem.models;

}