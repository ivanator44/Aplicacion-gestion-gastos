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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Acount;
import model.AcountDAOException;

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
    
    String interfaz;
    public void initInterfaz(String i){
        interfaz = i;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            nombreTextField.setText(Acount.getInstance().getLoggedUser().getName());
            apellidoTextField.setText(Acount.getInstance().getLoggedUser().getSurname());
            nicknameTextField.setText(Acount.getInstance().getLoggedUser().getSurname());
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
        Acount.getInstance().getLoggedUser().setName(nombreTextField.getText());
        Acount.getInstance().getLoggedUser().setSurname(apellidoTextField.getText());
        Acount.getInstance().getLoggedUser().setEmail(emailTextField.getText());
        Acount.getInstance().getLoggedUser().setPassword(passwordField.getText());
        Acount.getInstance().getLoggedUser().setImage(avatarImageView.getImage());
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
}
