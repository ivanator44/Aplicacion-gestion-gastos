package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class Graficos_categoriasController implements Initializable {

    @FXML
    private ImageView avatar;
    @FXML
    private Label nickname;
    @FXML
    private ImageView lapizImageView;
    
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<?, ?> barChart;
    @FXML
    private Button reporteButton;
    
    @FXML
    private Button atrasButton;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void generarReporte(ActionEvent event) {
    }

    @FXML
    private void atras(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("Ventana_categorias.fxml"));
        Parent root = miCargador.load();
        JavaFXMLApplication.setRoot(root);
    }   
}
