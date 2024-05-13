/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Acount;
import model.AcountDAOException;


public class AutenticacionController implements Initializable {

    @FXML
    private TextField nicknameTextField;
    @FXML
    private Label nicknameErrText;
    @FXML
    private TextField passwordField;
    @FXML
    private Label passwordErrText;
    @FXML
    private Button entrarButton;
    @FXML
    private Hyperlink registrarseHL;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nicknameErrText.setOpacity(0);
        passwordErrText.setOpacity(0);
    }    

    @FXML
    private void entrar(ActionEvent event) throws AcountDAOException, IOException {
        Acount cuenta = Acount.getInstance();
        if (cuenta.logInUserByCredentials(nicknameTextField.getText(), passwordField.getText())){
            JavaFXMLApplication.setRoot("Ventana_categorias");
        }
        else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("Datos incorrectos");
            alert.setContentText("Por favor, revise que ha introducido la información correcta antes de proseguir");
            nicknameTextField.setText("");
            passwordField.setText("");
            alert.showAndWait();
        }
    }

    @FXML
    private void hyperlinkFuncion(MouseEvent event) {
        JavaFXMLApplication.setRoot("Registro");
    } 

}
