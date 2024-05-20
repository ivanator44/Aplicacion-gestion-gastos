package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;


public class Añadir_gastoController implements Initializable {

    private TextField nombreGastoTextField;
    private TextField unidadesTextField;
    private DatePicker fechaGastoTextField;
    private ComboBox<Category> categoriaCB;
    private TextField costeTextField;
    @FXML
    private TextField descripcionTextField;
    
    private ImageView imagenFactura;
    
    @FXML
    private Button aceptarButton;
    @FXML
    private Button cancelarButton;
    
    @FXML
    private TextField nombreCategoriaTextField;
    @FXML
    private ImageView imagenCategoria;
    @FXML
    private ComboBox<String> imagenCategoriaCB;
    
    //--------------------------------------------------------------------------
    private boolean pulsadoOK = false;
    public boolean isOKPressed(){
        return pulsadoOK;
    }
    
    Charge cargo;
    public Charge getCargo(){
        return cargo;
    }
    
    Acount cuenta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
    }    
    
    private void subirFactura(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir fichero");
        fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Ficheros de texto", "*.txt"),
        new ExtensionFilter("Imágenes", "*.png", "*.jpg"));
        
        File selectedFile = fileChooser.showOpenDialog(
        ((Node)event.getSource()).getScene().getWindow()); 
    }

    @FXML
    private void aceptar(ActionEvent event) throws IOException, AcountDAOException {
        
        cuenta = Acount.getInstance();
        Boolean cargoAceptado = cuenta.registerCharge(nombreGastoTextField.getText(),
              descripcionTextField.getText(), parseDouble(costeTextField.getText()),
               parseInt(unidadesTextField.getText()), imagenFactura.getImage(),
                fechaGastoTextField.getValue(), (Category) categoriaCB.getButtonCell().getItem());

        if (cargoAceptado){
            nombreGastoTextField.clear();
            descripcionTextField.clear();
            costeTextField.clear();
            unidadesTextField.clear();
            // Añadir gasto a la categoría
            // FCUNCIÓN...
            pulsadoOK = true;
            FXMLLoader loader = new  FXMLLoader(getClass().getResource("Ventana_cgasto.fxml"));
            Parent  root = loader.load();
            JavaFXMLApplication.setRoot(root);
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
            alert.setHeaderText("Cabecera");
            // ó null si no queremos cabecera
            alert.setContentText("¡Faltan campos por completar!");
            alert.showAndWait();
        } 
    }

    @FXML
    private void cancelar(ActionEvent event) {
        cancelarButton.getScene().getWindow().hide();
    } 
}