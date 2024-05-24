package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;


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
    private BarChart<String, Number> barChart;
    @FXML
    private Button reporteButton;
    @FXML
    private Button atrasButton;
    @FXML
    private CategoryAxis xAxis1;
    @FXML
    private NumberAxis yAxis1;
    @FXML
    private Label totalLabel;
    
    List<Charge> charges;
    List<Category> categories;
    double totalGastos;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            categories = Acount.getInstance().getUserCategories();
            charges = Acount.getInstance().getUserCharges();
            
            totalGastos = charges.stream().mapToDouble(Charge::getCost).sum();
            totalLabel.setText("Total de gastos: " + totalGastos);
            
            Map<String, Double> categoryExpenses = new HashMap<>();
            for (Category category : categories) {
                categoryExpenses.put(category.getName(), 0.0);
            }
            for (Charge charge : charges) {
                String categoryName = charge.getCategory().getName();
                categoryExpenses.put(categoryName, categoryExpenses.get(categoryName) + charge.getCost());
            }
            
            // Actualizar el BarChart
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
            barChart.getData().add(series);
            
            // Actualizar el PieChart
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }
            pieChart.setData(pieChartData);
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(Graficos_categoriasController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
