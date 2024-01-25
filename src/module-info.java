module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires org.junit.jupiter.api;

    opens gui to javafx.fxml;
    opens main to javafx.fxml;

    exports gui;
    exports main;
}