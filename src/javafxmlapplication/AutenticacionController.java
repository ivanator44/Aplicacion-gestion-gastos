package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.User;


public class AutenticacionController implements Initializable {

    @FXML
    private TextField nicknameTextField;
    @FXML
    private Label nicknameErrText;
    @FXML
    private TextField passwordField;
    @FXML
    private Label passwordErrText;
    @FXML
    private Button entrarButton;
    @FXML
    private Hyperlink registrarseHL;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void entrar(ActionEvent event) throws AcountDAOException, IOException {
        // Si se puede iniciar sesión cambiaremos de ventana
        if (Acount.getInstance().logInUserByCredentials(nicknameTextField.getText(), passwordField.getText())){
            User user = Acount.getInstance().getLoggedUser();
            
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Ventana_categorias.fxml"));
            Parent root = miCargador.load();
            Ventana_categoriasController controlador = miCargador.getController();
            //controlador.initUsuario(user);
            JavaFXMLApplication.setRoot(root);
        }
        else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("Datos incorrectos");
            alert.setContentText("Por favor, revise que ha introducido la información correcta antes de proseguir");
            nicknameTextField.setText("");
            passwordField.setText("");
            alert.showAndWait();
        }
    }

    @FXML
    private void hyperlinkFuncion(MouseEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Registro.fxml"));
        Parent root = miCargador.load();
        JavaFXMLApplication.setRoot(root);
    } 
}
