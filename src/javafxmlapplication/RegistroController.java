/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ivana
 */
public class RegistroController implements Initializable {

    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private TextField correoTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordRepField;
    @FXML
    private Label preguntaTextField;
    @FXML
    private Button guardarButton;
    @FXML
    private Button cancelarButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
