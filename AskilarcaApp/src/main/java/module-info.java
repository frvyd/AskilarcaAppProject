module com.example.oopproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.example.oopproject to javafx.fxml;
    exports com.example.oopproject;
}