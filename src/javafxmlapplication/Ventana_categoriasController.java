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
    private ImageView lapizImageView;
    @FXML
    private Button modificarButton;
    @FXML
    private Button borrarButton;
    @FXML
    private Button añadirButton;
    @FXML
    private Button verGraficosButton;
    @FXML
    private Button atrasButton;
    
    private ObservableList<Category> datos = null;
    List<Category> userCategories;
    private User usuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datos = categoriasListView.getItems();
        
        try {
            usuario = Acount.getInstance().getLoggedUser();
            nickname.setText(usuario.getNickName());
            avatar.setImage(usuario.getImage());
            actualizarLista();
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(Ventana_categoriasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        categoriasListView.setCellFactory((c)->{return new CategoryListCell();});
        
        // Listeners
        categoriasListView.getSelectionModel().selectedIndexProperty().addListener((a, b, c) -> {
            if (categoriasListView.getSelectionModel().getSelectedIndex() != -1){
                modificarButton.setDisable(false);
                borrarButton.setDisable(false);
            }else{
                modificarButton.setDisable(true);
                borrarButton.setDisable(true);
            }
        });  
    }    
    
    // Funciones alternativas :)    
    @FXML
    private void ayudaLabelFuncion(MouseEvent event) {
        // Menú de información/alerta
        Alert alert = new Alert(AlertType.INFORMATION);
        // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
        alert.setHeaderText("INFORMACIÓN ADICIONAL");
        // ó null si no queremos cabecera
        alert.setContentText("•Añadir categoría: Permite agregar una nueva categoría"
                + "\n•Modificar categoría: Permite modificar la categoría seleccionada"
                + "\n•Borrar categoría: Elimina la categoría seleccionada"
                + "\n•Controles alternativos: 'Perfil de usuario' y 'Ver gráficos'");
        alert.showAndWait();
    }
    
    //Funcion para refrescar la lista una vez se hayan hecho cambios
    private void actualizarLista() throws AcountDAOException, IOException {
        // Obtén la lista de categorías del usuario
        userCategories = Acount.getInstance().getUserCategories();
        // Agrega cada categoría a la lista observable
        if (userCategories != null){
        datos.addAll(userCategories);
        }
    }
    
    // Funcion de los botones
    @FXML
    private void añadir(ActionEvent event) throws IOException, AcountDAOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Añadir_categoria.fxml"));
        Parent root = miCargador.load();
        
        Scene scene = new Scene(root, 291, 278);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir nuevo gasto");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        actualizarLista();
    }
    
    @FXML
    private void modificar(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Añadir_categoria.fxml"));
        Parent root = miCargador.load();
        Añadir_categoriaController controlador = miCargador.getController();
        controlador.initCategoria(categoriasListView.getSelectionModel().getSelectedItem());
        
        Scene scene = new Scene(root, 291, 278);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir nuevo gasto");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void borrar(ActionEvent event) throws AcountDAOException, IOException { 
        Category seleccion = categoriasListView.getSelectionModel().getSelectedItem();
        int indice = categoriasListView.getSelectionModel().getSelectedIndex();
        
        Acount.getInstance().removeCategory(seleccion);
        datos.remove(indice);
    }
    
    private void verGastos(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Ventana_gastos.fxml"));
        Parent root = miCargador.load();
        JavaFXMLApplication.setRoot(root);
    }

    @FXML
    private void verGraficos(ActionEvent event) {
    }

    @FXML
    private void atras(ActionEvent event) {
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
