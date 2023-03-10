package com.example.oopproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//Aplication class ı abstract bir class tır.
public class JavaApplication extends Application {
    @Override

// Kod çalıştırılınca giriş yapma ekranımızı getirir.
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 450);
        stage.setResizable(false);
        stage.setTitle("ASKILARCA Giriş Ekranı");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}