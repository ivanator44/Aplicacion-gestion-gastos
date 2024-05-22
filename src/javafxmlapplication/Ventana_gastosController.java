package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;


public class Ventana_gastosController implements Initializable {

    @FXML
    private Label ayudaLabel;
    @FXML
    private Label numeroGastos;
    @FXML
    private ImageView avatar;
    @FXML
    private Label nickname;
    @FXML
    private ImageView lapizImageView;   
    @FXML
    private Button añadirGastoButton;
    @FXML
    private Button modificarButton;
    @FXML
    private Button borrarButton;     
    @FXML
    private TableView<Charge> gastosTableV;
    @FXML
    private TableColumn<Charge, String> Fecha;
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
    @FXML
    private Button verCategoriasButton;
    @FXML
    private Button verGraficosButton;

    private ObservableList<Charge> datos = null; // Colecci�n vinculada a la vista.
    List<Charge> userCharges;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        datos = gastosTableV.getItems(); // no creo la lista observable, utilizo la que tiene vacia el listview
        
        gastosTableV.focusedProperty().addListener( (o, oldVal, newVal) -> {
        if (!gastosTableV.isFocused()){
            añadirGastoButton.setDisable(false);
            modificarButton.setDisable(true);
            verCategoriasButton.setDisable(false);
            verGraficosButton.setDisable(false);
            borrarButton.setDisable(true);

        }else{      
            añadirGastoButton.setDisable(true);
            modificarButton.setDisable(false);
            verCategoriasButton.setDisable(true);
            verGraficosButton.setDisable(true);
            borrarButton.setDisable(false);
        }
        });
        
        Fecha.setCellValueFactory(
            chargeFila->new SimpleStringProperty(chargeFila.getValue().getDate().toString()));
        
        Valor.setCellValueFactory(
            chargeFila->new SimpleDoubleProperty(chargeFila.getValue().getCost()));
        
        NombreGasto.setCellValueFactory(
            chargeFila->new SimpleStringProperty(chargeFila.getValue().getName());
        
        Categoria.setCellValueFactory(
            chargeFila->new SimpleStringProperty(chargeFila.getValue().getCategory().toString()));
        
        Unidades.setCellValueFactory(
            chargeFila->new SimpleStringProperty(chargeFila.getValue().getUnits()));
        
        Recibo.setCellValueFactory(
            personaFila ->new SimpleStringProperty(
            personaFila.getValue().getImagenPath())); 
    
    }    

    //Funcion para refrescar la lista una vez se hayan hecho cambios
    private void actualizarLista() throws AcountDAOException, IOException {
        // Obtén la lista de categorías del usuario
        userCharges = Acount.getInstance().getUserCharges();
        // Agrega cada categoría a la lista observable
        if (userCharges != null){
            datos.setAll(userCharges);
            numeroGastos.setText("Total de categorías: " + datos.size());
        }
    }
    
    @FXML
    private void añadirGasto(ActionEvent event) throws IOException, AcountDAOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Añadir_gasto.fxml"));
        Parent root = miCargador.load();
        Añadir_gastoController controlador = miCargador.getController();
        
        Scene scene = new Scene(root, 350, 350);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir nuevo gasto");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        if (controlador.isOKPressed()){
            actualizarLista();
        }
    }

    @FXML
    private void modificar(ActionEvent event) throws IOException, AcountDAOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Añadir_gasto.fxml"));
        Parent root = miCargador.load();
        Añadir_gastoController controlador = miCargador.getController();
        
        Scene scene = new Scene(root, 350, 350);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir nuevo gasto");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        if (controlador.isOKPressed()){
            actualizarLista();
        }
    }

    @FXML
    private void borrar(ActionEvent event) throws AcountDAOException, IOException {
        Acount.getInstance().removeCharge(gastosTableV.getSelectionModel().getSelectedItem());
        actualizarLista();
    }

    @FXML
    private void verCategorias(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Añadir_gasto.fxml"));
        Parent root = miCargador.load();
        JavaFXMLApplication.setRoot(root);
    }   

    @FXML
    private void verGraficos(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Añadir_gasto.fxml"));
        Parent root = miCargador.load();
        JavaFXMLApplication.setRoot(root);
    } 
}
