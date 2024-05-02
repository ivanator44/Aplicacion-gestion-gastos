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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField usuario;
    @FXML
    private TextField contraseña;
    @FXML
    private Button loginOk;
    @FXML
    private Button salirApp;
    @FXML
    private Text dadaAlta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void salir(MouseEvent event) {
    }

    @FXML
    private void alta(MouseEvent event) {
    }
    
}
