package com.example.oopproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.lang.String;

import java.io.*;

import java.io.IOException;

import static com.example.oopproject.LoginController.verifyLogin;

public class RegisterController {
    @FXML
    private TextField registerFirstnameTextField;

    @FXML
    private TextField registerLastnameTextField;

    @FXML
    private TextField registerUsernameTextField;

    @FXML
    private TextField registerPasswordTextField;

    @FXML
    private Label invalidRegisterLabel;

    @FXML
    private Button registerRegisterButton;

    @FXML
    private Button registerCancelButton;

    private Stage stage;
    private Scene scene;

    @FXML
    private CheckBox registerVisiblePasswordCheck;


    // Yeni kullanıcı kayıtlarının alındığı txt dosyasının olup olmadığını kontrol eder
    void checkFile() throws IOException {
        String dosyayolu = "usersdata.txt";
        File usersFile = new File(dosyayolu);

        if(usersFile.exists()) // txt dosyasının var olup olmadığı kontrol edilir.
        {
            System.out.println("Dosya zaten mevcut");
        }
        else {
            usersFile.createNewFile(); // Dosya yok ise oluşturur.
            System.out.println("Dosya oluşturuldu.");
        }
    }
    @FXML  // Yeni kullanıcı kayıtlanması tamamlandığında tekrar giriş ekranına yönlendirir.
    void turnLoginPage(ActionEvent event) throws IOException {
        checkFile();
        BufferedReader br = new BufferedReader(new FileReader("usersdata.txt"));
        boolean registerUnsuccess = verifyLogin(registerUsernameTextField.getText(),registerPasswordTextField.getText(),"usersdata.txt");

        if (registerFirstnameTextField.getText().isEmpty() || registerLastnameTextField.getText().isEmpty() || registerUsernameTextField.getText().isEmpty() || registerPasswordTextField.getText().isEmpty()) {
            invalidRegisterLabel.setText("Hiçbir alan boş bırakılamaz!");

        } else if (registerUnsuccess == true) {
            invalidRegisterLabel.setText("Girilen kullanıcı bilgileri zaten mevcut!");

        } else { // Yeni lullanıcının kaydını alır, usersdata.txt ye kaydeder.
            invalidRegisterLabel.setText("Kaydınız başarıyla alınmıştır.");
            System.out.println("Kullanıcı kaydı alındı ve userstada.txt dosyasına kaydedildi.");

            dosyayaYazma();
            registerFirstnameTextField.setText("");
            registerLastnameTextField.setText("");
            registerUsernameTextField.setText("");
            registerPasswordTextField.setText("");

        }
    }

    private static Scanner lg;    // Girilen kullanıcı bilgilerini txt dosyasıyla eşitler
    public static boolean verifyLogin(String username, String password,String filepath){
        boolean isFound = false;
        boolean validate = false;
        String uUsername = "";
        String pPassword = "";

        try{
            lg = new Scanner(new File(filepath));
            lg.useDelimiter("[,\n]");

            while(lg.hasNext() && !isFound){
                uUsername = lg.next();
                pPassword = lg.next();

                if(uUsername.trim().equals(username.trim())&& pPassword.trim().equals(password.trim())){
                    isFound = true;
                    validate = true;
                    System.out.println(uUsername + " " + pPassword);
                }
            }

            lg.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return validate;
    }

    // usersdata.txt dosyasına girilen verileri kaydeder
    void dosyayaYazma() throws IOException {
        String usersFile ="usersdata.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile,true));
        writer.write(registerFirstnameTextField.getText() + "," + registerLastnameTextField.getText() + "," + registerUsernameTextField.getText() + "," + registerPasswordTextField.getText()+"\n");
        writer.close();
    }
    @FXML
    void turnLoginPageCancel (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("ASKILARCA Giriş Ekranı");
        stage.show();
    }
}

