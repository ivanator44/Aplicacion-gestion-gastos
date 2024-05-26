package javafxmlapplication;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
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
            
            //Actualizar la foto de perfil y el nickname para que lo muestre la ventana
            avatar.setImage(Acount.getInstance().getLoggedUser().getImage());
            nickname.setText(Acount.getInstance().getLoggedUser().getNickName());
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
    private void generarReporte(ActionEvent event) throws FileNotFoundException, DocumentException, BadElementException, IOException, AcountDAOException {
        
        // Exportar gráficos como imágenes
        exportarGraficoComoImagen(barChart, "barChart.png");
        exportarGraficoComoImagen(pieChart, "pieChart.png");

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Reporte.pdf"));

        document.open();

        com.lowagie.text.Font boldFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 12, com.lowagie.text.Font.BOLD);

        // Título del documento
        Paragraph title = new Paragraph("Reporte de Gastos", boldFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" ")); // Espacio en blanco

        // Agregar gráfico de barras
        document.add(new Paragraph("Gráfico de Barras:"));
        Image barChartImage = Image.getInstance("barChart.png");
        barChartImage.scaleToFit(500, 300);
        document.add(barChartImage);

        document.add(new Paragraph(" ")); // Espacio en blanco

        // Agregar gráfico de pastel
        document.add(new Paragraph("Gráfico de Pastel:"));
        Image pieChartImage = Image.getInstance("pieChart.png");
        pieChartImage.scaleToFit(500, 300);
        document.add(pieChartImage);

        document.add(new Paragraph(" ")); // Espacio en blanco

        // Agregar tabla de gastos
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Encabezados de la tabla
        PdfPCell cell1 = new PdfPCell(new Phrase("Nombre", boldFont));
        PdfPCell cell2 = new PdfPCell(new Phrase("Costo", boldFont));
        PdfPCell cell3 = new PdfPCell(new Phrase("Fecha", boldFont));
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);

        // Agregar filas con datos de gastos
        for (Charge gasto : gastos) {
            table.addCell(gasto.getName());
            table.addCell(String.valueOf(gasto.getCost()));
            table.addCell(gasto.getDate().toString());
        }

        document.add(table);

        // Total de gastos
        document.add(new Paragraph("Total de gastos: " + totalGastos, boldFont));

        document.close();
        System.out.println("Impreso");
        
    }

    private void exportarGraficoComoImagen(BarChart<String, Double> chart, String path) throws IOException {
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bufferedImage, "png", new File(path));
    }

    private void exportarGraficoComoImagen(PieChart chart, String path) throws IOException {
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bufferedImage, "png", new File(path));
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
