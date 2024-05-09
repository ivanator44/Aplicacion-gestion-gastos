/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
public class AutenticacionController implements Initializable {

    @FXML
    private PasswordField passwordField;
    @FXML
    private Button autenticarButton;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private Label nickErrText;
    @FXML
    private Label passwordErrText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void autenticar(ActionEvent event) {
    }
    
}
