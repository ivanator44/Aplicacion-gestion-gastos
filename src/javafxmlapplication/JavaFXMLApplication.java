/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.util.HashMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Acount;


public class JavaFXMLApplication extends Application {
    
    private static Scene scene;
    private static HashMap<String, Parent> roots = new HashMap<>();

    static void setRoot(Parent root) {
        scene.setRoot(root);
    }
    static void setRoot(String clave) {
        Parent root = roots.get(clave);
        if (root != null) {
            setRoot(root);
        }
        else {
            System.err.printf("No se encuentra la escena: %s", clave);
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader;
        Parent  root;
        loader = new  FXMLLoader(getClass().getResource("/javafxmlapplication/Registro.fxml"));
        root = loader.load();
        roots.put("Registro", root);
        loader = new  FXMLLoader(getClass().getResource("/javafxmlapplication/Autenticacion.fxml"));
        root = loader.load();
        roots.put("Autenticacion", root);
        loader = new  FXMLLoader(getClass().getResource("/javafxmlapplication/Añadir_categoria.fxml"));
        root = loader.load();
        roots.put("Añadir_categoria", root);
        loader = new  FXMLLoader(getClass().getResource("/javafxmlapplication/Añadir_gasto.fxml"));
        root = loader.load();
        roots.put("Añadir_gasto", root); 
        loader = new  FXMLLoader(getClass().getResource("/javafxmlapplication/Graficos.fxml"));
        root = loader.load();
        roots.put("RGraficos", root);
        loader = new  FXMLLoader(getClass().getResource("/javafxmlapplication/Ventana_categorias.fxml"));
        root = loader.load();
        roots.put("Ventana_categorias", root);
        loader = new  FXMLLoader(getClass().getResource("/javafxmlapplication/Ventana_gastos.fxml"));
        root = loader.load();
        roots.put("Ventana_gastos", root);
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root);
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
        stage.setScene(scene);
        stage.setTitle("start PROJECT - IPC:");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }


    
}
