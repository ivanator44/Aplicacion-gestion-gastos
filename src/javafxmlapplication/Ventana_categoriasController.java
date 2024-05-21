    package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.User;

public class Ventana_categoriasController implements Initializable {

    @FXML
    private Label nombreCategoria;
    @FXML
    private Label ayudaLabel;
    @FXML
    private Label numeroGastos;
    @FXML
    private ListView<Category> categoriasListView;
    @FXML
    private ImageView avatar;
    @FXML
    private Label nickname;
    @FXML
    private Label correo;
    
    @FXML
    private Button logOutButton;
    @FXML
    private Button modificarButton;
    @FXML
    private Button borrarButton;
    @FXML
    private Button añadirButton;
    @FXML
    private Button verGastosButton;
    
    // DEBEN conincidir los tipo del ListView y de la lista observable
    private ObservableList<Category> datos = null; // Coleccion vinculada a la vista.
    
    private Acount cuenta;
    private User usuario;

    // Métodos para inicilizar la vetana con los datos correctamente
    public void initUsuario(User u){
        usuario = u;
        nickname.setText(u.getNickName());
        correo.setText(u.getEmail());
        avatar.setImage(u.getImage());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            // Inicializamos la cuenta y sus datos en algunos campos
            cuenta = Acount.getInstance();
                
            // Añadimos las categorías creadas previamente por nuestro usuario
            datos = categoriasListView.getItems(); // no creo la lista observable, utilizo la que tiene vacia el listview
            
            // Obtén la lista de categorías del usuario
            List<Category> userCategories = cuenta.getUserCategories();
            
            // Agrega cada categoría a la lista observable
            if (userCategories != null){
                datos.addAll(userCategories);
            }
            
            // Hay que modificar CellFactory para mostrar el objeto Persona
            categoriasListView.setCellFactory((c)->{return new CategoryListCell();});
            //=======================================================
            // Deshabilitar los botones modificar y borrar.
            categoriasListView.focusedProperty().addListener((a, b, c) -> {
                if (categoriasListView.isFocused()){
                    modificarButton.setDisable(false);
                    borrarButton.setDisable(false);
                }else{
                    modificarButton.setDisable(true);
                    borrarButton.setDisable(true);
                }
            });
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(Ventana_categoriasController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }    
    
    // Funciones alternativas :)    
    @FXML
    private void ayudaLabelFuncion(MouseEvent event) {
        // Menú de información/alerta
        Alert alert = new Alert(AlertType.INFORMATION);
        // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
        alert.setHeaderText("INFORMACIÓN ADICIONAL");
        // ó null si no queremos cabecera
        alert.setContentText("Añadir categoría: \nModificar categoría: \nBorrar categoría: \nControles alternativos: Perfil de usuario: \n"
                + "");
        alert.showAndWait();
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        // Menú de confirmación/alerta
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de confirmación");
        alert.setHeaderText("Cerrar sesión");
        alert.setContentText("¿Seguro que quieres continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("Autenticacion"));
            Parent root = miCargador.load();
            cuenta.logOutUser();
            JavaFXMLApplication.setRoot(root);
        }
    }

    // Funcion de los botones
    @FXML
    private void añadir(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Añadir_categoria.fxml"));
        Parent root = miCargador.load();
        Añadir_categoriaController controlador = miCargador.getController();
        
        Scene scene = new Scene(root, 500, 300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir nuevo gasto");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
    @FXML
    private void modificar(ActionEvent event) {
    }

    @FXML
    private void borrar(ActionEvent event) {
        datos.remove(categoriasListView.getSelectionModel().getSelectedIndex());
    }
    
    @FXML
    private void verGastos(ActionEvent event) throws IOException {
        // Pasamos la categoria al otro controlador
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Ventana_gastos.fxml"));
        Parent root = miCargador.load();
        JavaFXMLApplication.setRoot(root);
    }
    
    //----------------------------------------------------------
    // Clase para mostrar las celdas del ListView de manera personalizada
    class CategoryListCell extends ListCell<Category> {
        @Override
        protected void updateItem(Category c, boolean bln) {
            super.updateItem(c, bln); //To change body of generated methods, choose Tools | Templates.
            if (bln){ // Está vacía
               setText(""); 
            }else{
                setText("Objeto");
            }
        }   
    }
}
