package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
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
    private TableColumn<Charge, String> Valor;
    @FXML
    private TableColumn<Charge, String> NombreGasto;
    @FXML
    private TableColumn<Charge, String> Categoria;
    @FXML
    private TableColumn<Charge, String> Unidades;
    @FXML
    private TableColumn<Charge, String> Recibo;
    @FXML
    private Button verCategoriasButton;
    @FXML
    private Button verGraficosButton;
    boolean hayCategorias;

    private ObservableList<Charge> datos = null; // Colecci�n vinculada a la vista.
    List<Charge> userCharges;
    @Override
    public void initialize(URL url, ResourceBundle rb){
        // TODO
        datos = gastosTableV.getItems(); // no creo la lista observable, utilizo la que tiene vacia el listview
        
        try {
            nickname.setText(Acount.getInstance().getLoggedUser().getNickName());
            avatar.setImage(Acount.getInstance().getLoggedUser().getImage());
            actualizarLista();
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(Ventana_gastosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        modificarButton.disableProperty().bind(
            Bindings.equal(-1,
            gastosTableV.getSelectionModel().selectedIndexProperty()));

        borrarButton.disableProperty().bind(
            Bindings.equal(-1,
            gastosTableV.getSelectionModel().selectedIndexProperty()));
         
        Fecha.setCellValueFactory(
            chargeFila->new SimpleStringProperty(chargeFila.getValue().getDate().toString()));
        Fecha.setCellFactory(c -> new TabCell());
        
        Valor.setCellValueFactory(
            chargeFila->new SimpleStringProperty(Double.toString(chargeFila.getValue().getCost())));
        Valor.setCellFactory(c -> new TabCell());
        
        NombreGasto.setCellValueFactory(
            chargeFila->new SimpleStringProperty(chargeFila.getValue().getName()));
        NombreGasto.setCellFactory(c -> new TabCell());
        
        Categoria.setCellValueFactory(
            chargeFila->new SimpleStringProperty(chargeFila.getValue().getCategory().getName()));
        Categoria.setCellFactory(c -> new TabCell());
        
        Unidades.setCellValueFactory(
            chargeFila->new SimpleStringProperty(Integer.toString(chargeFila.getValue().getUnits())));
        Unidades.setCellFactory(c -> new TabCell());
        
        Recibo.setCellValueFactory(
            chargeFila->new SimpleStringProperty(chargeFila.getValue().getImageScan().getUrl()));
        Recibo.setCellFactory(c -> new ImagenTabCell());
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
    
    //Funcion para refrescar la lista una vez se hayan hecho cambios
    private void actualizarLista(Category categoria) throws AcountDAOException, IOException {
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
        List<Category> categorias = Acount.getInstance().getUserCategories();
        hayCategorias = !categorias.isEmpty();
        
        if(hayCategorias){
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
        }else{
            verCategoriasButton.requestFocus();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
            alert.setHeaderText(null);
            // ó null si no queremos cabecera
            alert.setContentText("¡Hay que crear categorias antes!");
            alert.showAndWait();
        }
    }

    @FXML
    private void modificar(ActionEvent event) throws IOException, AcountDAOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Añadir_gasto.fxml"));
        Parent root = miCargador.load();
        Añadir_gastoController controlador = miCargador.getController();
        controlador.initCharge(gastosTableV.getSelectionModel().getSelectedItem());
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
                "Ventana_categorias.fxml"));
        Parent root = miCargador.load();
        JavaFXMLApplication.setRoot(root);
    }   

    @FXML
    private void verGraficos(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Graficos_gastos.fxml"));
        Parent root = miCargador.load();
        JavaFXMLApplication.setRoot(root);
    } 

    @FXML
    private void modificarPerfil(MouseEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Modificar_perfil.fxml"));
        Parent root = miCargador.load();
        Modificar_perfilController controlador = miCargador.getController();
        controlador.initInterfaz("Ventana_gastos");
        JavaFXMLApplication.setRoot(root);
    }

    @FXML
    private void ayudaLabelFuncion(MouseEvent event) {
        // Menú de información/alerta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
        alert.setHeaderText("INFORMACIÓN ADICIONAL");
        // ó null si no queremos cabecera
        alert.setContentText("•Añadir gasto: Permite agregar un nuevo gasto"
                + "\n•Modificar gasto: Permite modificar el gasto seleccionado"
                + "\n•Borrar gasto: Elimina el gasto seleccionada"
                + "\n•Controles alternativos: 'Perfil de usuario', 'Ver gráficos'"
                + "\ny acceder a categorías");
        alert.showAndWait();
    }
    
    // Clase para ver los elementos de cada columna centrados
    class TabCell extends TableCell<Charge, String> {
        private ImageView view = new ImageView();
        private Image imagen;

        @Override
        protected void updateItem(String t, boolean bln) {
            super.updateItem(t, bln);
            if (t == null || bln) {
                setText(null);
                setGraphic(null);
            } else {
                setText(t); // Texto de error si la imagen no se puede cargar
                setGraphic(null);
                setAlignment(Pos.CENTER);
            }
        }  
    }
    // Clase para observar una imagen en la columna de la factura
    class ImagenTabCell extends TableCell<Charge, String> {
        private ImageView view = new ImageView();
        private Image imagen;
        @Override
        protected void updateItem(String t, boolean bln) {
            super.updateItem(t, bln);
            if (t == null || bln) {
                t = "/imagenes/cruzado.png";
                imagen = new Image(t, 20, 20, true, true);
                setText(null);
                setGraphic(null);
            } else {
                t = "/imagenes/factura.png";
                imagen = new Image(t, 20, 20, true, true);
                view.setImage(imagen);
                setGraphic(view);
                setAlignment(Pos.CENTER);
            }
        }
 
    }
}
