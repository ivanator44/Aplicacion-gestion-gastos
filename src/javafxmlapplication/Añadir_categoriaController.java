package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Acount;
import model.AcountDAOException;

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
        if (!nombreCategoriaTextField.getText().isEmpty() && !descripcionTextField.getText().isEmpty()) {
            if (cuenta.registerCategory(nombreCategoriaTextField.getText(), descripcionTextField.getText())){
                nombreCategoriaTextField.getScene().getWindow().hide();
                Alert alert = new Alert(AlertType.INFORMATION);
                // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
                alert.setHeaderText(null);
                // ó null si no queremos cabecera
                alert.setContentText("Categoría registrada correctamente.");
                alert.showAndWait();
            }
        } else {
            Alert camposVacios = new Alert(AlertType.ERROR);
            camposVacios.setTitle("ERROR");
            camposVacios.setHeaderText("¡Alguno de los campos está vacío!");
            camposVacios.setContentText("Revisa la información de los campos por favor");
            camposVacios.showAndWait();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        nombreCategoriaTextField.getScene().getWindow().hide();
    }
}
