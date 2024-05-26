package javafxmlapplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

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
    private Button aceptarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private Label urlFactura;
    
    //--------------------------------------------------------------------------
    private boolean pulsadoOK = false;
    public boolean isOKPressed(){
        return pulsadoOK;
    }
    
    Charge cargo;
    public Charge getCargo(){
        return cargo;
    }
    private boolean camposInicializados = false;
    public void initCharge(Charge c){
        cargo = c;
        nombreGastoTextField.setText(cargo.getName());
        unidadesTextField.setText(Integer.toString(cargo.getUnits()));
        costeTextField.setText(Double.toString(cargo.getCost()));
        fechaGastoTextField.setValue(cargo.getDate());
        categoriaCB.setValue(cargo.getCategory());
        descripcionTextField.setText(cargo.getDescription());
        Image imagen = cargo.getImageScan();
        if (imagen != null) {
            imagenFactura.setImage(imagen);
            urlFactura.setText("-Imagen subida-");
        }else{urlFactura.setText("-No hay imagen-");}
        camposInicializados = true;
    }
    
    Acount cuenta;
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
        try {
            // TODO
            categoriaCB.setCellFactory(c -> new CategoryListCell());
            categoriaCB.setButtonCell(new CategoryListCell());
            actualizarCategorias();
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(Añadir_gastoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    private void subirFactura(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir fichero");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Imágenes", "*.png", "*.jpg"));
        
        File selectedFile = fileChooser.showOpenDialog(
        ((Node)event.getSource()).getScene().getWindow());
        
        if (selectedFile != null) {
            try {
                FileInputStream inputStream = new FileInputStream(selectedFile);
                Image imagen = new Image(inputStream, 100, 100, true, true);
                imagenFactura.setImage(imagen);
                urlFactura.setText(selectedFile.getAbsolutePath());
            } catch (FileNotFoundException e) {
                System.err.println("Error al cargar la imagen");
            }
        } 
    }
    
    @FXML
    private void aceptar(ActionEvent event) throws IOException, AcountDAOException {
        if(!camposInicializados){
            if(!nombreGastoTextField.getText().isEmpty() && !descripcionTextField.getText().isEmpty() 
                    && !costeTextField.getText().isEmpty() && !unidadesTextField.getText().isEmpty()
                    && (fechaGastoTextField.getValue()!= null) && categoriaCB.getValue()!=null ) {
                boolean cargoAceptado = Acount.getInstance().registerCharge(nombreGastoTextField.getText(),
                  descripcionTextField.getText(), parseDouble(costeTextField.getText()),
                   parseInt(unidadesTextField.getText()), imagenFactura.getImage(),
                    fechaGastoTextField.getValue(),categoriaCB.getValue());
                if (cargoAceptado ){
                    pulsadoOK = true;
                    cancelarButton.getScene().getWindow().hide();
                }else{
                    Alert alert = new Alert(AlertType.INFORMATION);
                    // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
                    alert.setHeaderText("¡Campos incompletos!");
                    // ó null si no queremos cabecera
                    alert.setContentText("¡Faltan campos por completar!");
                    alert.showAndWait();
                } 
            }else{
                    Alert alert = new Alert(AlertType.INFORMATION);
                    // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
                    alert.setHeaderText(null);
                    // ó null si no queremos cabecera
                    alert.setContentText("¡Faltan campos por completar!");
                    alert.showAndWait();
            }
        }else{
            if(!nombreGastoTextField.getText().isEmpty() && !descripcionTextField.getText().isEmpty() 
                && !costeTextField.getText().isEmpty() && !unidadesTextField.getText().isEmpty()
                && (fechaGastoTextField.getValue()!= null) && categoriaCB.getValue()!=null ){
                cargo.setName(nombreGastoTextField.getText());
                cargo.setDescription(descripcionTextField.getText());
                cargo.setCost(parseDouble(costeTextField.getText()));
                cargo.setUnits(parseInt(unidadesTextField.getText()));
                cargo.setImageScan(imagenFactura.getImage());
                cargo.setDate(fechaGastoTextField.getValue());
                cargo.setCategory(categoriaCB.getValue());
                pulsadoOK=true;
                cancelarButton.getScene().getWindow().hide();
            }else{
                Alert alert = new Alert(AlertType.INFORMATION);
                // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
                alert.setHeaderText("¡Campos incompletos!");
                // ó null si no queremos cabecera
                alert.setContentText("¡Faltan campos por completar!");
                alert.showAndWait();
            }
        }  
    }

    @FXML
    private void cancelar(ActionEvent event) {
        cancelarButton.getScene().getWindow().hide();
    } 

    private void actualizarCategorias() throws AcountDAOException, IOException {
        cuenta = Acount.getInstance();
        List<Category> categorias = cuenta.getUserCategories();
        ObservableList<Category> categoriasObservable = FXCollections.observableList(categorias);
        categoriaCB.setItems(categoriasObservable);
    }
    
    // Clase para observar las categorías correctamente
    class CategoryListCell extends ListCell<Category> {
        @Override
        protected void updateItem(Category t, boolean bln) {
            super.updateItem(t, bln);
            if (t==null || bln) setText(null);
            else setText(t.getName());
        }
    }
}