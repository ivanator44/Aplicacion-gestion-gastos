package javafxmlapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXMLApplication extends Application {    
    private static Scene scene;

    static void setRoot(Parent root) {
        scene.setRoot(root);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("Autenticacion.fxml"));

        Parent  root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Bienvenido a CashControl");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);        
    }
}
