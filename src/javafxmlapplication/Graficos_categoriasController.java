package javafxmlapplication;

import java.io.File;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javax.imageio.ImageIO;
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
            
            //Actualizar la foto de perfil y el nickname para que lo muestre la ventana
            avatar.setImage(Acount.getInstance().getLoggedUser().getImage());
            nickname.setText(Acount.getInstance().getLoggedUser().getNickName());
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(Graficos_categoriasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    private void generarReporte(ActionEvent event) throws IOException {
        /*saveAsImage(pieChart, "pieChart.png");
        saveAsImage(barChart, "barChart.png");
        createPdfWithCharts("charts_report.pdf");*/
    }

    public void saveAsImage(Node node, String filePath) throws IOException {
        WritableImage image = node.snapshot(new SnapshotParameters(), null);
        File file = new File(filePath);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    }
    
    /*public void createPdfWithCharts(String dest) {
        try {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Añadir las imágenes al PDF
            ImageData imageData1 = ImageDataFactory.create("chart1.png");
            Image chart1 = new Image(imageData1);
            document.add(chart1);

            ImageData imageData2 = ImageDataFactory.create("chart2.png");
            Image chart2 = new Image(imageData2);
            document.add(chart2);

            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/
    
    @FXML
    private void atras(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("Ventana_categorias.fxml"));
        Parent root = miCargador.load();
        JavaFXMLApplication.setRoot(root);
    }   

    @FXML
    private void modificarPerfil(MouseEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Modificar_perfil.fxml"));
        Parent root = miCargador.load();
        Modificar_perfilController controlador = miCargador.getController();
        controlador.initInterfaz("Graficos_categorias");
        JavaFXMLApplication.setRoot(root);
    }
}
