/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Charge;

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
    private TableView<Charge> gastosTableV;
    @FXML
    private ImageView avatar;
    @FXML
    private Label nickname;
    @FXML
    private Label correo;
    
    @FXML
    private Button añadirGastoButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button modificarButton;
    @FXML
    private Button borrarButton;
    @FXML
    private Button atrasButton;
    

    
    @FXML
    private TableColumn<Charge, Integer> Fecha;
    @FXML
    private TableColumn<Charge, Double> Valor;
    @FXML
    private TableColumn<Charge, String> NombreGasto;
    @FXML
    private TableColumn<Charge, String> Categoria;
    @FXML
    private TableColumn<Charge, Integer> Unidades;
    @FXML
    private TableColumn<Charge, Image> Recibo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ayudaLabelFuncion(MouseEvent event) {
    }

    @FXML
    private void añadirGasto(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Añadir_gasto.fxml"));
        Parent root = miCargador.load();
        //Añadir_categoriaController controlador = miCargador.getController();
        
        Scene scene = new Scene(root, 350, 350);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir nuevo gasto");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
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
