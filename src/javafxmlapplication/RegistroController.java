/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Acount;

/**
 * FXML Controller class
 *
 * @author ivana
 */
public class RegistroController implements Initializable {
    private BooleanProperty validNickname;
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty equalPasswords;  
    private Acount nueva;

    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordRepField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField apellidoTextField;
    @FXML
    private Button registrarButton;
    @FXML
    private Label nombreErrText;
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        validNickname = new SimpleBooleanProperty();
        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();   
        equalPasswords = new SimpleBooleanProperty();
        
        validNickname.setValue(Boolean.FALSE);
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
                        
        /*BooleanBinding validFields = Bindings.and(validEmail, validPassword)
                 .and(equalPasswords);*/
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
    }    

    @FXML
    private void registrar(ActionEvent event) {
        if (validNickname.get() && validEmail.get() && validPassword.get() && equalPasswords.get()) {
            
        }
    }

    @FXML
    private void hyperlinkFuncion(ActionEvent event) {
        JavaFXMLApplication.setRoot("Registro.fxml");
    }
    
    // Métodos para comprobar que los campos son correctos
    public static  Boolean checkEmail (String email)
    {   if(email == null){
          return false; 
        }
       // Regex to check valid email. 
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex);
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static Boolean checkPassword(String password){     
  
        // If the password is empty , return false 
        if (password == null) { return false; } 
        // Caracterñiasticas de la contraseña 
        String regex = "^[A-Za-z0-9]{8,15}$"; 
        // Compilar reGex 
        Pattern pattern = Pattern.compile(regex); 
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(password); 
        return matcher.matches();
    }   
    
    public static Boolean checkPasswordRep(String password, String passwordRep){     
  
        // If the password is empty , return false 
        if (password == null) { 
            return false; 
        } 
        return password.equals(passwordRep);
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
    
    //-----------------------------------------------
    
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
}
