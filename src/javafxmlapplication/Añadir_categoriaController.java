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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Acount;
import model.AcountDAOException;
import model.Category;

public class Añadir_categoriaController implements Initializable {

    @FXML
    private TextField nombreCategoriaTextField;
    @FXML
    private TextField descripcionTextField;
    
    @FXML
    private Button aceptarButton;
    @FXML
    private Button cancelarButton;
    
    
    //--------------------------------------------------------------------------
    private boolean aceptarPulsado = false;
    public boolean isOKPressed(){
        return aceptarPulsado;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void aceptar(ActionEvent event) throws AcountDAOException, IOException {
        Acount cuenta = Acount.getInstance();
        /*if (cuenta.registerCategory(nombreCategoriaTextField.getText(), descripcionTextField.getText())){
            nombreCategoriaTextField.getScene().getWindow().hide();
            Alert alert = new Alert(AlertType.INFORMATION);
            // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
            alert.setHeaderText(null);
            // ó null si no queremos cabecera
            alert.setContentText("Categoría registrada correctamente.");
            alert.showAndWait();
        }*/
        Alert alert = new Alert(AlertType.INFORMATION);
        // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
        alert.setHeaderText(null);
        // ó null si no queremos cabecera
        alert.setContentText("Categoría registrada correctamente.");
        alert.showAndWait();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        nombreCategoriaTextField.getScene().getWindow().hide();
    }
}
