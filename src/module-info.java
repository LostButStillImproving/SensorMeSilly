module com.example.sensormesilly {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    opens com.example.sensormesilly to javafx.fxml, javafx.graphics;
    exports com.example.sensormesilly;
    exports com.example.sensormesilly.controllers;
    exports com.example.sensormesilly.model;
    opens com.example.sensormesilly.controllers to javafx.fxml, javafx.graphics;
    opens com.example.sensormesilly.model to javafx.fxml, javafx.graphics;
}