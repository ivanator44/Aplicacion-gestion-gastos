/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Category;

/**
 * FXML Controller class
 *
 * @author ivana
 */
public class Ventana_categoriasController implements Initializable {

    @FXML
    private Label nombreCategoria;
    @FXML
    private Label ayudaLabel;
    @FXML
    private Label numeroGastos;
    @FXML
    private ListView<Category> categoriasListView;
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

    // DEBEN conincidir los tipo del ListView y de la lista observable
    private ObservableList<Category> datos = null; // Coleccion vinculada a la vista.
    @FXML
    private Button añadirButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // en el código de inicialización del controlador
        ArrayList<Category> misdatos = new ArrayList<>();
        
        //=======================================================
        // creamos la lista observable mediante un metodo de FXCollections
        datos = FXCollections.observableArrayList(misdatos);
        
        //=======================================================
        //=======================================================
        // vinculamos la lista observables de personas con el ListView
        categoriasListView.setItems(datos);
        
        //=======================================================
        //=======================================================
        // Hay que modificar CellFactory para mostrar el objeto Persona
        //categoriasListView.setCellFactory((c)->{return new CategoriasListCell();});
    }    

    // Funciones alternativas :)
    @FXML
    private void ayudaLabelFuncion(MouseEvent event) {
    }

    @FXML
    private void logOut(ActionEvent event) {
    }

    // Funcion de los botones
    @FXML
    private void añadir(ActionEvent event) {
    }
    
    @FXML
    private void modificar(ActionEvent event) {
    }

    @FXML
    private void borrar(ActionEvent event) {
    }
    
    // Funcion del ListView para ver los gastos de cada categoría
    @FXML
    private void seleccionarCategoria(MouseEvent event) {
    }    
}
