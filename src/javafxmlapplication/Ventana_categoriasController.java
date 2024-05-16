package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    
    // DEBEN conincidir los tipo del ListView y de la lista observable
    private ObservableList<Category> datos = null; // Coleccion vinculada a la vista.
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // en el código de inicialización del controlador
        ArrayList<Category> misdatos = new ArrayList<>();
        
        //=======================================================
        // creamos la lista observable mediante un metodo de FXCollections
        datos = FXCollections.observableArrayList(misdatos);
        
        //=======================================================
        // vinculamos la lista observables de personas con el ListView
        categoriasListView.setItems(datos);

        //=======================================================
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
        
        //=========================================================
        // Inicializamos la cuenta y sus datos en algunos campos
        try {
            User usuario = Acount.getInstance().getLoggedUser();
            System.out.println(usuario);
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(Ventana_categoriasController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }    
    
    // Funciones alternativas :)    
    @FXML
    private void ayudaLabelFuncion(MouseEvent event) {
        // Menú de información/alerta
        System.out.println("tejodes");
    }

    @FXML
    private void logOut(ActionEvent event) {
        // Menú de confirmación/alerta
        
    }

    // Funcion de los botones
    @FXML
    private void añadir(ActionEvent event) {
    }
    
    @FXML
    private void modificar(ActionEvent event) {
    }

    @FXML
    private void borrar(ActionEvent event) {
        datos.remove(categoriasListView.getSelectionModel().getSelectedIndex());
    }
    
    // Funcion del ListView para ver los gastos de cada categoría
    @FXML
    private void seleccionarCategoria(MouseEvent event) {
    } 
    
    //-----------------------------------------------------------
    // Método para tomar la información del usuario que acaba de iniciar sesión
    
    
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
