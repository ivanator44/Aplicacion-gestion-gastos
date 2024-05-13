
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
        FXMLLoader loader;
        Parent  root;
          
        loader = new  FXMLLoader(getClass().getResource("Añadir_categoria.fxml"));
        root = loader.load();
        roots.put("Añadir_categoria", root);
        loader = new  FXMLLoader(getClass().getResource("Añadir_gasto.fxml"));
        root = loader.load();
        roots.put("Añadir_gasto", root); 
        loader = new  FXMLLoader(getClass().getResource("Graficos.fxml"));
        root = loader.load();
        roots.put("RGraficos", root);
        loader = new  FXMLLoader(getClass().getResource("Ventana_categorias.fxml"));
        root = loader.load();
        roots.put("Ventana_categorias", root);
        loader = new  FXMLLoader(getClass().getResource("Ventana_gastos.fxml"));
        root = loader.load();
        roots.put("Ventana_gastos", root);
        loader = new  FXMLLoader(getClass().getResource("Registro.fxml"));
        root = loader.load();
        roots.put("Registro", root);loader = new  FXMLLoader(getClass().getResource("Autenticacion.fxml"));
        root = loader.load();
        roots.put("Autenticacion", root);
        
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("start PROJECT - IPC:");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);        
    }
}
