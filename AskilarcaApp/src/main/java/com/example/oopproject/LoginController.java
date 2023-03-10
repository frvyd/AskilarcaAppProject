package com.example.oopproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.*;
import java.util.Scanner;

public class LoginController {
    @FXML
    private TextField loginUsernameTextField;

    @FXML
    private TextField loginPasswordTextField;

    @FXML
    private PasswordField loginPasswordField;

    private Stage stage;
    private Scene scene;

    @FXML
    private Label invalidAccessLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private CheckBox loginVisiblePasswordCheck;

    public LoginController() throws IOException {
    }

    private static Scanner lg;

// Girilen kullanıcı adı ve şifreyi dosyadaki verilerle eşiyleyip kullanıcı kaydını doğrular.
    public static boolean verifyLogin(String username, String password,String filepath){
        boolean isFound = false;
        boolean validate = false;
        String tempUsername = "";
        String tempPassword = "";

        try{
            lg = new Scanner(new File(filepath));
            lg.useDelimiter("[,\n]");

            while(lg.hasNext()&&!isFound){
                tempUsername = lg.next();
                tempPassword = lg.next();

                if(tempUsername.trim().equals(username.trim())&& tempPassword.trim().equals(password.trim())){
                    isFound = true;
                    validate = true;
                    System.out.println(tempUsername + " " + tempPassword);
                }
            }

            lg.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return validate;
    }

    @FXML   // Giriş butonuna basınca gerçekleşebilecek olasılıklar
    void loginButtonClick(ActionEvent event) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("usersdata.txt"));
        boolean loginSuccess = verifyLogin(loginUsernameTextField.getText(),loginPasswordField.getText(),"usersdata.txt");
        String username = loginUsernameTextField.getText();
        String password = loginPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            // Kullanıcı adı veya şifre boş
            invalidAccessLabel.setText("Hiçbir alan boş bırakılamaz.");

        } else if (loginSuccess == true) {
            Parent root = FXMLLoader.load(getClass().getResource("wardrobe.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("ASKILARCA Gardrop Ekranı");
            stage.show();

        } else {
            invalidAccessLabel.setText("Kullanıcı adı veya şifre hatalı.");
        }
    }

    @FXML   // Kayıt Ol butonuna basınca üye kayıt ekranına yönlendirir
    void registerButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("ASKILARCA Kullanıcı Kayıt Ekranı");
        stage.show();

    }

    @FXML   // Şifre gizleme-görünür yapma
    void showPassword(ActionEvent event) {
        if (loginVisiblePasswordCheck.isSelected()){
            loginPasswordTextField.setText(loginPasswordField.getText());
            loginPasswordTextField.setVisible(true);
            loginPasswordField.setVisible(false);
        }else{
            loginPasswordTextField.setVisible(false);
            loginPasswordField.setVisible(true);

        }
    }
}












