package javafxmlapplication;

import static extras.Utils.checkEmail;
import static extras.Utils.checkNickname;
import static extras.Utils.checkPassword;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
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
import model.User;

public class Modificar_perfilController implements Initializable {

    @FXML
    private TextField nombreTextField;
    @FXML
    private Label nombreErrText;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private Label nicknameErrText;
    @FXML
    private TextField emailTextField;
    @FXML
    private Label emailErrText;
    @FXML
    private TextField apellidoTextField;
    @FXML
    private Label apellidoErrText;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label passwordErrText;
    @FXML
    private PasswordField passwordRepField;
    @FXML
    private Label passwordRepErrText;
    @FXML
    private Button guardarButton;
    @FXML
    private ImageView avatarImageView;
    @FXML
    private ComboBox<String> imagenesComboBox;
    @FXML
    private Button atrasButton;
    
    private boolean emailValido;
    private boolean passwordValido;
    private boolean passwordRepValido;
    
    String interfaz;
    @FXML
    private Button logOutButton;
    @FXML
    private Hyperlink ayudaLink;
    public void initInterfaz(String i){
        interfaz = i;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            
            emailValido = true;
            passwordValido = true;
            passwordRepValido = true;

            emailTextField.textProperty().addListener((observable, oldValue, newValue)->{
                if (!emailTextField.getText().equals("")){
                    if (!User.checkEmail(emailTextField.getText())){
                        emailValido = showErrorMessage(emailErrText, emailTextField);
                    }else{
                        emailValido = hideErrorMessage(emailErrText, emailTextField);
                    }
                }else{
                    hideErrorMessage(emailErrText, emailTextField);
                    emailValido = false;
                }
            });

            passwordField.textProperty().addListener((observable, oldValue, newValue)->{
                if (!passwordField.getText().equals("")){
                    if (!checkPassword(passwordField.getText())){
                        passwordValido = showErrorMessage(passwordErrText, passwordField);
                    }else{
                        passwordValido = hideErrorMessage(passwordErrText, passwordField);
                    }
                }else{
                    hideErrorMessage(passwordErrText, passwordField);
                    passwordValido = false;
                }
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
            imagenesComboBox.setCellFactory(c -> new Modificar_perfilController.ImagenComboCell());

            imagenesComboBox.valueProperty().addListener((observable, oldValue, newValue)->{
                // Obtener la ruta de la imagen seleccionada
                String imagePath = newValue;
                // Cargar la imagen y establecerla como avatar
                Image imagen = new Image(getClass().getResourceAsStream(imagePath));
                avatarImageView.setImage(imagen);
                imagenesComboBox.setButtonCell(new Modificar_perfilController.ImagenTickComboCell());
            });
            
            nombreTextField.setText(Acount.getInstance().getLoggedUser().getName());
            apellidoTextField.setText(Acount.getInstance().getLoggedUser().getSurname());
            nicknameTextField.setText(Acount.getInstance().getLoggedUser().getNickName());
            emailTextField.setText(Acount.getInstance().getLoggedUser().getEmail());
            passwordField.setText(Acount.getInstance().getLoggedUser().getPassword());
            passwordRepField.setText(Acount.getInstance().getLoggedUser().getPassword());
            avatarImageView.setImage(Acount.getInstance().getLoggedUser().getImage());
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(Modificar_perfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     

    @FXML
    private void guardar(ActionEvent event) throws IOException, AcountDAOException {
        if (!emailTextField.getText().isEmpty() || !passwordField.getText().isEmpty() || !passwordRepField.getText().isEmpty()) {
            if (emailValido && passwordValido && passwordRepValido) {
                Acount.getInstance().getLoggedUser().setName(nombreTextField.getText());
                Acount.getInstance().getLoggedUser().setSurname(apellidoTextField.getText());
                Acount.getInstance().getLoggedUser().setEmail(emailTextField.getText());
                Acount.getInstance().getLoggedUser().setPassword(passwordField.getText());
                Acount.getInstance().getLoggedUser().setImage(avatarImageView.getImage());
                // Alerta indicando que el registro se ha completado correctamente...
                Alert alert = new Alert(AlertType.INFORMATION);
                // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
                alert.setHeaderText("¡Datos Actualizados!");
                // ó null si no queremos cabecera
                alert.setContentText("Los datos se han modificado correctamente.");
                alert.showAndWait();

                // Dependiendo de la ventana desde la que se accedió a modificar el perfil
                // se retornará a un sitio u a otro...
                FXMLLoader miCargador;
                Parent root;
                switch (interfaz){
                    case "Ventana_gastos":
                        miCargador = new FXMLLoader(getClass().getResource("Ventana_gastos.fxml"));
                        root = miCargador.load();
                        JavaFXMLApplication.setRoot(root);
                    case "Ventana_categorias":
                        miCargador = new FXMLLoader(getClass().getResource("Ventana_categorias.fxml"));
                        root = miCargador.load();
                        JavaFXMLApplication.setRoot(root);
                }
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
                alert.setHeaderText("¡Campos incorrectos!");
                // ó null si no queremos cabecera
                alert.setContentText("Asegúrate de completar todos los campos correctamente."
                        + "\nPuedes consultar los requerimientos de los campos pulsando\n"
                        + "en 'Ayuda'");
                alert.showAndWait();
            }
        } else {
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
    private void atras(ActionEvent event) throws IOException {
        // Dependiendo de la ventana desde la que se accedió a modificar el perfil
        // se retornará a un sitio u a otro...
        FXMLLoader miCargador;
        Parent root;
        switch (interfaz){
            case "Ventana_gastos":
                miCargador = new FXMLLoader(getClass().getResource("Ventana_gastos.fxml"));
                root = miCargador.load();
                JavaFXMLApplication.setRoot(root);
            case "Ventana_categorias":
                miCargador = new FXMLLoader(getClass().getResource("Ventana_gastos.fxml"));
                root = miCargador.load();
                JavaFXMLApplication.setRoot(root);
        }
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException, AcountDAOException {
        Alert seguro = new Alert(AlertType.CONFIRMATION);
        seguro.setTitle("Cerrar sesión");
        seguro.setHeaderText("¿Estás seguro de que quieres salir?");
        seguro.setContentText(null);
        Optional<ButtonType> respuesta = seguro.showAndWait();
        if (respuesta.isPresent()) {
            if (respuesta.get() == ButtonType.OK) {
                Acount.getInstance().logOutUser();
                FXMLLoader loader = new  FXMLLoader(getClass().getResource("Autenticacion.fxml"));
                Parent root = loader.load();
                JavaFXMLApplication.setRoot(root);
            }
        }
    }
    
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

    @FXML
    private void sacarAyuda(ActionEvent event) {
        Alert ayuda = new Alert(AlertType.INFORMATION);
        ayuda.setHeaderText("Información");
        ayuda.setContentText("Formato correo: nombre@dominio.terminación\nRequisitos contraseña: 8 carácteres min. y al menos un carácter especial.");
        ayuda.showAndWait();
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
