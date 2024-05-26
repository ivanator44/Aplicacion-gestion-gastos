package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
    @FXML
    private Text labelTipoVentana;
    public boolean isOKPressed(){
        return aceptarPulsado;
    }
    private Category categoria;
    private boolean camposInicializados = false;
    public void initCategoria(Category c){
        categoria = c;
        nombreCategoriaTextField.setText(categoria.getName());
        descripcionTextField.setText(categoria.getDescription());
        labelTipoVentana.setText("Modificar categoría");
        camposInicializados = true;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void aceptar(ActionEvent event) throws AcountDAOException, IOException {
        if (camposInicializados){
            if (!nombreCategoriaTextField.getText().isEmpty() && !descripcionTextField.getText().isEmpty()) {
                categoria.setName(nombreCategoriaTextField.getText());
                categoria.setDescription(descripcionTextField.getText());
                nombreCategoriaTextField.getScene().getWindow().hide();
            }else{
                Alert camposVacios = new Alert(AlertType.ERROR);
                camposVacios.setTitle("ERROR");
                camposVacios.setHeaderText(null);
                camposVacios.setContentText("¡Completa los campos si están vacíos!");
                camposVacios.showAndWait();
            }
        }else{
            if (!nombreCategoriaTextField.getText().isEmpty() && !descripcionTextField.getText().isEmpty()) {
                if (Acount.getInstance().registerCategory(nombreCategoriaTextField.getText(), descripcionTextField.getText())){
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
                camposVacios.setHeaderText(null);
                camposVacios.setContentText("¡Completa los campos si están vacíos!");
                camposVacios.showAndWait();
            }
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        nombreCategoriaTextField.getScene().getWindow().hide();
    }
}
