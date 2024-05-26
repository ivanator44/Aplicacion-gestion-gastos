package javafxmlapplication;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
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
    private void generarReporte(ActionEvent event) throws IOException, DocumentException {
        saveAsImage(pieChart, "pieChart.png");
        saveAsImage(barChart, "barChart.png");
        createPdfWithCharts("charts_report.pdf");
    }

    public void saveAsImage(Node node, String filePath) throws IOException {
        WritableImage image = node.snapshot(new SnapshotParameters(), null);
        File file = new File(filePath);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    }
    
    public void createPdfWithCharts(String dest) throws DocumentException, BadElementException, IOException {
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
        for (Charge gasto : charges) {
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
    
    private void exportarGraficoComoImagen(BarChart<String, Number> chart, String path) throws IOException {
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