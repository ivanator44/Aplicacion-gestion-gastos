/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

/**
 * FXML Controller class
 *
 * @author ivana
 */
public class RegistroController implements Initializable {
    private BooleanProperty validNickname;
    private BooleanProperty validSurname;
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty equalPasswords;  
    private BooleanProperty validName;  
    private Acount nueva;

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
    private Hyperlink hyperlink;
    @FXML
    private ImageView avatarImageView;
    @FXML
    private ComboBox<String> imagenesComboBox;
    @FXML
    private Button registrarButton;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        validName = new SimpleBooleanProperty();
        validSurname = new SimpleBooleanProperty();
        validNickname = new SimpleBooleanProperty();
        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();   
        equalPasswords = new SimpleBooleanProperty();
        
        validName.setValue(Boolean.FALSE);
        validSurname.setValue(Boolean.FALSE);
        validNickname.setValue(Boolean.FALSE);
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
                        
        /*BooleanBinding validFields = Bindings.and(validEmail, validPassword)
                 .and(equalPasswords);*/
        nombreTextField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost
                checkEditName();
            }
        });
        
        apellidoTextField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost
                checkEditSurname();
            }
        });
        
        nicknameTextField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost
                checkEditNickname();
            }
        });
        
        emailTextField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost
                checkEditEmail();
            }
        });
        
        passwordField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost
                checkEditPassword();
            }
        });
        
        passwordRepField.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost
                checkEditPasswordRep();
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
        if (validName.get() && validSurname.get() && validNickname.get() && validEmail.get() && validPassword.get() && equalPasswords.get()) {
            Acount cuenta = Acount.getInstance();
            String nombre = nombreTextField.getText();
            String apellido = apellidoTextField.getText();
            String email = emailTextField.getText();
            String login = emailTextField.getText(); // (Nickname) 
            String contraseña = passwordField.getText();
            
            Image avatar;
            if (avatarImageView.getImage() != null){
                avatar = avatarImageView.getImage();
            }else{ // Si no se ha seleccionado ninguna imagen...
                avatar = new Image(getClass().getResourceAsStream("/avatares/default.png"));
            }
            
            //cuenta.registerUser(nombre, apellido, email, login, contraseña, avatar, LocalDate.MAX);
            JavaFXMLApplication.setRoot("Autenticacion");
        }
    }

    @FXML
    private void hyperlinkFuncion(ActionEvent event) {
        JavaFXMLApplication.setRoot("Autenticacion");
    }
    
    //-----------------------------------------------
    // Métodos para comprobar que los campos son correctos
    private void checkEditName(){
        if (!checkName(nombreTextField.textProperty().getValueSafe())){
            manageError(nombreErrText, nombreTextField, validName);
        }else{
            manageCorrect(nombreErrText, nombreTextField, validName);
        }
    }
    
    private void checkEditSurname(){
        if (!checkSurname(apellidoTextField.textProperty().getValueSafe())){
            manageError(apellidoErrText, apellidoTextField, validSurname);
        }else{
            manageCorrect(apellidoErrText, apellidoTextField, validSurname);
        }
    }
    
    private void checkEditNickname(){
        if (!checkNickname(nicknameTextField.textProperty().getValueSafe())){
            manageError(nicknameErrText, nicknameTextField, validNickname);
        }else{
            manageCorrect(nicknameErrText, nicknameTextField, validNickname);
        }
    }
    
    private void checkEditEmail(){
        if (!checkEmail(emailTextField.textProperty().getValueSafe())){
            manageError(emailErrText, emailTextField, validEmail);
        }else{
            manageCorrect(emailErrText, emailTextField, validEmail);
        }
    }
    
    private void checkEditPassword(){
        if (!checkPassword(passwordField.textProperty().getValueSafe())){
            manageError(passwordErrText, passwordField, validPassword);
        }else{
            manageCorrect(passwordErrText, passwordField, validPassword);
        }
    }
    
    private void checkEditPasswordRep(){
        if (!checkPasswordRep(passwordRepField.textProperty().getValueSafe(), 
                passwordField.textProperty().getValueSafe())){
            manageError(passwordRepErrText, passwordRepField, equalPasswords);
        }else{
            manageCorrect(passwordRepErrText, passwordRepField, equalPasswords);
        }
    }
    
    //------------------------------------------
    
    /**
     * Updates the boolProp to false.Changes to red the background of the edit. 
     * Makes the error label visible and sends the focus to the edit. 
     * @param errorLabel label added to alert the user
     * @param textField edit text added to allow user to introduce the value
     * @param boolProp property which stores if the value is correct or not
     */
    private void manageError(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        if (!textField.textProperty().getValueSafe().equals("")) {
            boolProp.setValue(Boolean.FALSE);
            showErrorMessage(errorLabel,textField);
            //textField.requestFocus();
        }
    }
    
    /**
     * Updates the boolProp to true. Changes the background 
     * of the edit to the default value. Makes the error label invisible. 
     * @param errorLabel label added to alert the user
     * @param textField edit text added to allow user to introduce the value
     * @param boolProp property which stores if the value is correct or not
     */
    private void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);  
    }
    
    //----------------------------------------------------
    
    /**
     * Changes to red the background of the edit and
     * makes the error label visible
     * @param errorLabel
     * @param textField 
     */
    private void showErrorMessage(Label errorLabel,TextField textField){
        errorLabel.visibleProperty().set(true); 
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    }
    
    /**
     * Changes the background of the edit to the default value
     * and makes the error label invisible.
     * @param errorLabel
     * @param textField 
     */
    private void hideErrorMessage(Label errorLabel,TextField textField){
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }
    
    //--------------------------------------------------------------
    
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
            }
        }
    }
    
    // Clase para mostrar en la celda del comboBox cuando se seleccione alguna imagen
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
            }
        }
    }
}
