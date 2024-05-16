package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Acount;
import model.AcountDAOException;
import model.Category;


public class Añadir_gastoController implements Initializable {

    @FXML
    private TextField nombreGastoTextField;
    @FXML
    private TextField unidadesTextField;
    @FXML
    private DatePicker fechaGastoTextField;
    @FXML
    private ComboBox<Category> categoriaCB;
    @FXML
    private TextField costeTextField;
    @FXML
    private TextField descripcionTextField;
    
    @FXML
    private ImageView imagenFactura;
    @FXML
    private Label urlFactura;
    
    @FXML
    private Button aceptarButton;
    @FXML
    private Button cancelarButton;
    

    Acount cuenta;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
    }    

    @FXML
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
    private void aceptar(ActionEvent event) {
        try {
            cuenta = Acount.getInstance();
            Boolean cargo = cuenta.registerCharge(nombreGastoTextField.getText(),
                  descripcionTextField.getText(), parseDouble(costeTextField.getText()),
                   parseInt(unidadesTextField.getText()), imagenFactura.getImage(),
                    fechaGastoTextField.getValue(), (Category) categoriaCB.getButtonCell().getItem());
            
            if (cargo){
                nombreGastoTextField.clear();
                descripcionTextField.clear();
                costeTextField.clear();
                unidadesTextField.clear();
                // Añadir gasto a la categoría
                // FCUNCIÓN...
                JavaFXMLApplication.setRoot("ventana_gasto");
            }else{
                Alert alert = new Alert(AlertType.INFORMATION);
                // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
                alert.setHeaderText("Cabecera");
                // ó null si no queremos cabecera
                alert.setContentText("Aquí va el texto del mensaje");
                alert.showAndWait();
            }
            
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(Añadir_gastoController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @FXML
    private void cancelar(ActionEvent event) {
        cancelarButton.getScene().getWindow().hide();
    }
}