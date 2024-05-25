package javafxmlapplication;

import extras.Utils;
import static extras.Utils.checkPassword;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
import javafx.scene.input.MouseEvent;
import model.Acount;
import model.AcountDAOException;
import model.User;

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
    @FXML
    private Button registrarButton;
    @FXML
    private Hyperlink hyperlink;
    @FXML
    private Label ayudaLink;
    
    private boolean nicknameValido;
    private boolean emailValido;
    private boolean passwordValido;
    private boolean passwordRepValido;
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        nicknameValido = false;
        emailValido = false;
        passwordValido = false;
        passwordRepValido = false;
        
        nicknameTextField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if (!nicknameTextField.getText().equals("")){
                if (!User.checkNickName(nicknameTextField.getText())){
                    nicknameValido = showErrorMessage(nicknameErrText, nicknameTextField);
                }else{
                    nicknameValido = hideErrorMessage(nicknameErrText, nicknameTextField);
                }
            }else{
                hideErrorMessage(nicknameErrText, nicknameTextField);
                nicknameValido = false;
            }
        });
        
        emailTextField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if (!emailTextField.getText().equals("")){
                if (!User.checkEmail(emailTextField.getText())){
                    emailValido = showErrorMessage(emailErrText, emailTextField);
                }else{
                    emailValido = hideErrorMessage(emailErrText, emailTextField);
                }
            }else{
                emailValido = hideErrorMessage(emailErrText, emailTextField);
            }
        });
        
        passwordField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if (!passwordField.getText().equals("")){
                if (!Utils.checkPassword(passwordField.getText())){
                    passwordValido = showErrorMessage(passwordErrText, passwordField);
                }else{
                    passwordValido = hideErrorMessage(passwordErrText, passwordField);
                }
            }else{
                passwordValido = hideErrorMessage(passwordErrText, passwordField);
            }
        });
        
        passwordRepField.textProperty().addListener((observable, oldValue, newValue)->{
            if (!passwordRepField.getText().equals("")){
                if (!passwordField.getText().equals(passwordRepField.getText())){
                    passwordRepValido = showErrorMessage(passwordRepErrText, passwordRepField);
                }else{
                    passwordRepValido = hideErrorMessage(passwordRepErrText, passwordRepField);
                }
            }else{
                hideErrorMessage(passwordRepErrText, passwordRepField);
                passwordRepValido = false;
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
        if (!nicknameTextField.getText().isEmpty() || !emailTextField.getText().isEmpty() 
            || !passwordField.getText().isEmpty() || !passwordRepField.getText().isEmpty()){
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
                    alert.setContentText("Modifica el valor del nickname para que no coincida\n"
                            + "con el de otro usuario.");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(AlertType.WARNING);
                // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
                alert.setHeaderText("¡Campos incorrectos!");
                // ó null si no queremos cabecera
                alert.setContentText("Asegúrate de completar todos los campos correctamente."
                        + "\nPuedes consultar los requerimientos de los campos pulsando\n"
                        + "en 'Ayuda'");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(AlertType.WARNING);
            // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
            alert.setHeaderText("¡Faltan campos por completar!");
            // ó null si no queremos cabecera
            alert.setContentText("Asegúrate de completar todos los campos correctamente."
                    + "\nPuedes consultar los requerimientos de los campos pulsando\n"
                    + "en 'Ayuda'");
            alert.showAndWait();
        }
    }

    @FXML
    private void sacarAyuda(MouseEvent event) {
        Alert ayuda = new Alert(AlertType.INFORMATION);
        ayuda.setHeaderText("Información");
        ayuda.setContentText("Formato correo: nombre@dominio.terminación\nRequisitos contraseña: 8 carácteres min. y al menos un carácter especial.");
        ayuda.showAndWait();
    }
    
    @FXML
    private void hyperlinkFuncion(ActionEvent event) throws IOException {
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("Autenticacion.fxml"));
        Parent root = loader.load();
        JavaFXMLApplication.setRoot(root);
        
    }    
    //--------------------------------------------------------------------------   
    // Mostrar u ocultar los textos de error
    private boolean showErrorMessage(Label errorLabel, TextField textField){
        errorLabel.visibleProperty().set(true); 
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");  
        return false;

    } 
    private boolean hideErrorMessage(Label errorLabel, TextField textField){
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
        return true;
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
