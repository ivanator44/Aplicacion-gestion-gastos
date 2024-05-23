package javafxmlapplication;

import static extras.Utils.checkEmail;
import static extras.Utils.checkName;
import static extras.Utils.checkNickname;
import static extras.Utils.checkPassword;
import static extras.Utils.checkPasswordRep;
import static extras.Utils.checkSurname;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Acount;
import model.AcountDAOException;

public class RegistroController implements Initializable {
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidoTextField;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordRepField;
    @FXML
    private TextField emailTextField;   
    @FXML
    private Label nombreErrText;
    @FXML
    private Label apellidoErrText;
    @FXML
    private Label nicknameErrText;
    @FXML
    private Label emailErrText;
    @FXML
    private Label passwordErrText;
    @FXML
    private Label passwordRepErrText;   
    @FXML
    private ImageView avatarImageView;
    @FXML
    private ComboBox<String> imagenesComboBox;
    
    private boolean nicknameValido;
    private boolean emailValido;
    private boolean passwordValido;
    private boolean passwordRepValido;
    @FXML
    private Button registrarButton;
    @FXML
    private Hyperlink hyperlink;
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        nicknameValido = false;
        emailValido = false;
        passwordValido = false;
        passwordRepValido = false;
        
        nicknameTextField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost
                if (!nicknameTextField.getText().equals("")){
                    if (!checkNickname(nicknameTextField.getText())){
                        showErrorMessage(nicknameErrText, nicknameTextField, nicknameValido);
                    }else{
                        hideErrorMessage(nicknameErrText, nicknameTextField, nicknameValido);
                    }
                }else{
                    hideErrorMessage(nicknameErrText, nicknameTextField, nicknameValido);
                }
            }
        });
        
        emailTextField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost
                if (!emailTextField.getText().equals("")){
                    if (!checkEmail(emailTextField.getText())){
                        showErrorMessage(emailErrText, emailTextField, emailValido);
                    }else{
                        hideErrorMessage(emailErrText, emailTextField, emailValido);
                    }
                }else{
                    hideErrorMessage(emailErrText, emailTextField, emailValido);
                }
            }
        });
        
        passwordField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost
                if (!passwordField.getText().equals("")){
                    if (!checkPassword(passwordField.getText())){
                        showErrorMessage(passwordErrText, passwordField, passwordValido);
                    }else{
                        hideErrorMessage(passwordErrText, passwordField, passwordValido);
                    }
                }else{
                    hideErrorMessage(passwordErrText, passwordField, passwordValido);
                }
            }
        });
        
        passwordRepField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost
                if (!passwordRepField.getText().equals("")){
                    if (!passwordField.getText().equals(passwordRepField.getText())){
                        showErrorMessage(passwordRepErrText, passwordRepField, passwordRepValido);
                    }else{
                        hideErrorMessage(passwordRepErrText, passwordRepField, passwordRepValido);
                    }
                }else{
                    hideErrorMessage(passwordRepErrText, passwordRepField, passwordRepValido);
                }
            }
        });
        
        imagenesComboBox.getItems().addAll("/avatares/1.png", "/avatares/2.png",
                "/avatares/3.png", "/avatares/4.png", "/avatares/5.png", 
                "/avatares/6.png", "/avatares/7.png", "/avatares/8.png",
                "/avatares/9.png", "/avatares/10.png", "/avatares/11.png",
                "/avatares/default.png");
        imagenesComboBox.setCellFactory(c -> new ImagenComboCell());
        
        imagenesComboBox.valueProperty().addListener((observable, oldValue, newValue)->{
            // Obtener la ruta de la imagen seleccionada
            String imagePath = newValue;
            // Cargar la imagen y establecerla como avatar
            Image imagen = new Image(getClass().getResourceAsStream(imagePath));
            avatarImageView.setImage(imagen);
            imagenesComboBox.setButtonCell(new ImagenTickComboCell());
        });
    }    

    // Función del botón de registrar
    @FXML
    private void registrar(ActionEvent event) throws AcountDAOException, IOException {
        if (nicknameValido && emailValido && passwordValido && passwordRepValido) { 
            String nombre = nombreTextField.getText();
            String apellido = apellidoTextField.getText();
            String email = emailTextField.getText();
            String login = nicknameTextField.getText(); // (Nickname) 
            String contraseña = passwordField.getText();
            
            Image avatar;
            if (avatarImageView.getImage() != null){
                avatar = avatarImageView.getImage();
            }else{ // Si no se ha seleccionado ninguna imagen...
                avatar = new Image(getClass().getResourceAsStream("/avatares/default.png"));
            }
            
            // Si no existe ya una cuenta con ese nickname entonces registramos los datos
            if (!Acount.getInstance().existsLogin(login)){
                Acount.getInstance().registerUser(nombre, apellido, email, login, contraseña, avatar, LocalDate.MAX);
                FXMLLoader loader = new  FXMLLoader(getClass().getResource("Autenticacion.fxml"));
                Parent  root = loader.load();
                // Alerta indicando que el registro se ha completado correctamente...
                Alert alert = new Alert(AlertType.INFORMATION);
                // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
                alert.setHeaderText("REGISTRADO");
                // ó null si no queremos cabecera
                alert.setContentText("Has sido registrado correctamente.");
                alert.showAndWait();
                JavaFXMLApplication.setRoot(root);
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
                alert.setHeaderText("¡El nickname ya está en uso!");
                // ó null si no queremos cabecera
                alert.setContentText("Modifica el valor del nickname para que no coincida con el de otro usuario.");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(AlertType.WARNING);
            // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
            alert.setHeaderText("¡Campos incompletos!");
            // ó null si no queremos cabecera
            alert.setContentText("Asegúrate de completar todos los campos correctamente.");
            alert.showAndWait();

        }
    }

    @FXML
    private void hyperlinkFuncion(ActionEvent event) throws IOException {
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("Autenticacion.fxml"));
        Parent root = loader.load();
        JavaFXMLApplication.setRoot(root);
        
    }    
    //--------------------------------------------------------------------------   
    // Mostrar u ocultar los textos de error
    private void showErrorMessage(Label errorLabel, TextField textField, boolean val){
        errorLabel.visibleProperty().set(true); 
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");  
        val = false;

    } 
    private void hideErrorMessage(Label errorLabel, TextField textField, boolean valido){
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
        valido = true;
    } 
    //--------------------------------------------------------------------------
    // Clase para mostrar las imágenes correctamente
    class ImagenComboCell extends ComboBoxListCell<String> {
        private ImageView view = new ImageView();
        private Image imagen;
        
        @Override
        public void updateItem(String t, boolean bln) {
            super.updateItem(t, bln); //To change body of generated methods, choose Tools | Templates.
            if (t == null || bln) {
                setText(null);
                setGraphic(null);
            } else {
                imagen = new Image(t, 25, 25, true, true);
                view.setImage(imagen);
                setGraphic(view);
                String fileName = t.substring(t.lastIndexOf("/") + 1, t.lastIndexOf("."));
                setText(fileName);
            }
        }
    }
    
    // Clase para mostrar en la celda del comboBox un 'tick' cuando se seleccione alguna imagen
    class ImagenTickComboCell extends ComboBoxListCell<String> {
        private ImageView view = new ImageView();
        private Image imagen;
        
        @Override
        public void updateItem(String t, boolean bln) {
            super.updateItem(t, bln); //To change body of generated methods, choose Tools | Templates.
            if (t == null || bln) {
                setText(null);
                setGraphic(null);
            } else {
                t = "/imagenes/tick.png";
                imagen = new Image(t, 25, 25, true, true);
                view.setImage(imagen);
                setGraphic(view);
                String fileName = t.substring(t.lastIndexOf("/") + 1, t.lastIndexOf("."));
                setText(fileName);
            }
        }
    }  
}
