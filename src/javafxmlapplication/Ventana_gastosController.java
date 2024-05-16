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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ivana
 */
public class Ventana_gastosController implements Initializable {

    @FXML
    private Label nombreCategoria;
    @FXML
    private Label ayudaLabel;
    @FXML
    private Label numeroGastos;
    @FXML
    private Button añadirGastoButton;
    @FXML
    private TableView<?> gastosTableV;
    @FXML
    private ImageView avatar;
    @FXML
    private Label nickname;
    @FXML
    private Label correo;
    @FXML
    private Button logOutButton;
    @FXML
    private Button modificarButton;
    @FXML
    private Button borrarButton;
    @FXML
    private Button atrasButton;
    @FXML
    private TableColumn<?, ?> Fecha;
    @FXML
    private TableColumn<?, ?> Valor;
    @FXML
    private TableColumn<?, ?> NombreGasto;
    @FXML
    private TableColumn<?, ?> Categoria;
    @FXML
    private TableColumn<?, ?> Unidades;
    @FXML
    private TableColumn<?, ?> Recibo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ayudaLabelFuncion(MouseEvent event) {
    }

    @FXML
    private void añadirGasto(ActionEvent event) {
    }

    @FXML
    private void logOut(ActionEvent event) {
    }

    @FXML
    private void modificar(ActionEvent event) {
    }

    @FXML
    private void borrar(ActionEvent event) {
    }

    @FXML
    private void atras(ActionEvent event) {
    }
    
}
