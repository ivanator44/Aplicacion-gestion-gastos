package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import model.Charge;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Acount;
import model.AcountDAOException;

public class Graficos_gastosController implements Initializable {   
    @FXML
    private BarChart<String, Double> barChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label totalLabel;
    @FXML
    private ImageView avatar;
    @FXML
    private Label nickname;
    @FXML
    private ImageView lapizImageView;
    @FXML
    private Button reporteButton;
    @FXML
    private Button atrasButton;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    
    private List<Charge> gastos;
    double totalGastos;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            gastos = Acount.getInstance().getUserCharges();
            // Calcular el total de gastos
            totalGastos = gastos.stream().mapToDouble(Charge::getCost).sum();
            totalLabel.setText("Total de gastos: " + totalGastos);
            
            // Obtener los gastos
            obtenerGastos();
            
            // Configurar el BarChart
            configurarBarChart();
            
            // Configurar el PieChart
            configurarPieChart();
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(Graficos_gastosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void obtenerGastos() throws AcountDAOException, IOException {
        // Lógica para obtener los gastos en una List<Charge>
        gastos = Acount.getInstance().getUserCharges();
    }
    
    private void configurarBarChart() {
        Map<String, Double> gastosPorMes = new HashMap<>();
        
        // Agrupar gastos por mes
        for (Charge gasto : gastos) {
            LocalDate fecha = gasto.getDate();
            String mes = fecha.getMonth().toString();
            double cantidad = gasto.getCost();
            
            // Agregar el gasto al mapa
            gastosPorMes.put(mes, gastosPorMes.getOrDefault(mes, 0.0) + cantidad);
        }
        
        // Crear una serie de datos para el BarChart
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        
        // Agregar los datos al BarChart
        for (String mes : gastosPorMes.keySet()) {
            series.getData().add(new XYChart.Data<>(mes, gastosPorMes.get(mes)));
        }
        
        // Agregar la serie al BarChart
        barChart.getData().add(series);
    }
    
    private void configurarPieChart() {
        // Crear una lista de datos para el PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        
        // Agregar los datos al PieChart
        for (Charge gasto : gastos) {
            pieChartData.add(new PieChart.Data(gasto.getName(), gasto.getCost()));
        }
        
        // Agregar los datos al PieChart
        pieChart.setData(pieChartData);
    }

    @FXML
    private void generarReporte(ActionEvent event) {
        // Crear un nuevo documento PDF
        /*try (PdPage writer = new PdfWriter(new File("reporte.pdf"));
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Agregar contenido al PDF
            document.add(new Paragraph("Reporte de Gastos"));

            // Obtener los datos de los gastos
            List<Charge> gastos = obtenerGastos();

            // Agregar los datos de los gastos al PDF
            for (Charge gasto : gastos) {
                document.add(new Paragraph(gasto.toString())); // Ajusta esto según tu clase Charge
            }

            // Mostrar un mensaje de éxito
            System.out.println("¡Reporte generado exitosamente!");
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de excepciones (puedes mostrar una alerta o un mensaje de error)
        }*/
    }

    @FXML
    private void atras(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Ventana_gastos.fxml"));
        Parent root = miCargador.load();
        JavaFXMLApplication.setRoot(root);
    }

    @FXML
    private void modificarPerfil(MouseEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(
                "Modificar_perfil.fxml"));
        Parent root = miCargador.load();
        Modificar_perfilController controlador = miCargador.getController();
        controlador.initInterfaz("Graficos_gastos");
        JavaFXMLApplication.setRoot(root);
    }
}
