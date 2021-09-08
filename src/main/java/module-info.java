module com.example.sensormesilly {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sensormesilly to javafx.fxml;
    exports com.example.sensormesilly;
}