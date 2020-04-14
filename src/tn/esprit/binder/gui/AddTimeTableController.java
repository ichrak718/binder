/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JFileChooser;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.entities.TimeTable;
import tn.esprit.binder.services.ServicesClasses;
import tn.esprit.binder.services.ServicesTimeTable;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class AddTimeTableController implements Initializable {

    @FXML
    private TableView<TimeTable> timeTable;
    @FXML
    private TableColumn<TimeTable, String> day;
    @FXML
    private TableColumn<TimeTable, String> seance1;
    @FXML
    private TableColumn<TimeTable, String> seance2;
    @FXML
    private TableColumn<TimeTable, String> seance3;
    @FXML
    private TableColumn<TimeTable, String> seance4;
    @FXML
    private TableColumn<TimeTable, String> seance5;
    @FXML
    private Button btUpdate;
    @FXML
    private Button btDelete;
    @FXML
    private Button btConfirm;

    @FXML
    private TextField dayTxt;
    Classes gr;

    public TimeTable time = new TimeTable();

    public ObservableList<TimeTable> timeTableList;
    final List<TimeTable> timeL = new ArrayList<>();

    int id_class;

    @FXML
    private ComboBox<String> scCombo1;
    @FXML
    private ComboBox<String> scCombo2;
    @FXML
    private ComboBox<String> scCombo3;
    @FXML
    private ComboBox<String> scCombo4;
    public List<String> subject;
    TimeTable t = new TimeTable();
    @FXML
    private Button btadd;
    @FXML
    private Button btCancel;
    @FXML
    private Label error;
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD, BaseColor.RED);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    @FXML
    private Button btExport;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {

            day.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDay()));
            seance1.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSeance1()));
            seance2.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSeance2()));
            seance3.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSeance3()));
            seance4.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSeance4()));
            seance5.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSeance5()));

            subject = new ServicesTimeTable().readAllSubject();
            scCombo1.setItems(FXCollections.observableArrayList(subject));
            scCombo2.setItems(FXCollections.observableArrayList(subject));
            scCombo3.setItems(FXCollections.observableArrayList(subject));
            scCombo4.setItems(FXCollections.observableArrayList(subject));
            t = timeTable.getSelectionModel().getSelectedItem();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    @FXML
    private void btUpdateOnClick(ActionEvent event) throws ParseException, SQLException {
        t = timeTable.getSelectionModel().getSelectedItem();
        scCombo1.setDisable(false);
        scCombo2.setDisable(false);
        scCombo3.setDisable(false);
        scCombo4.setDisable(false);
        btConfirm.setDisable(false);
        scCombo1.getSelectionModel().select(t.getSeance1());
        scCombo2.getSelectionModel().select(t.getSeance2());
        scCombo3.getSelectionModel().select(t.getSeance4());
        scCombo4.getSelectionModel().select(t.getSeance5());
        dayTxt.setText(t.getDay());
        btadd.setVisible(false);
        btConfirm.setVisible(true);

    }

    @FXML
    private void btDeleteOnClick(ActionEvent event) throws ParseException {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Class");
            alert.setHeaderText("Delete  " + timeTable.getSelectionModel().getSelectedItem().getDay());
            alert.setContentText("Would you like to delete " + timeTable.getSelectionModel().getSelectedItem().getDay() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                new ServicesTimeTable().deleteTimeTable(timeTable.getSelectionModel().getSelectedItem().getId());
                refreshTable();
            }

            if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void setGrade(Classes gr) throws ParseException {
        this.gr = gr;
        id_class = gr.getId();
        time.setIdClasse(id_class);
        try {
            refreshTable();
        } catch (SQLException ex) {
            Logger.getLogger(AddTimeTableController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void refreshTable() throws SQLException, ParseException {
        timeL.clear();
        timeTable.getItems().clear();

        day.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDay()));
        seance1.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSeance1()));
        seance2.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSeance2()));
        seance3.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSeance3()));
        seance4.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSeance4()));
        seance5.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSeance5()));
        timeTableList = new ServicesTimeTable().readAll(time);

        if (timeTableList.isEmpty()) {
            dayTxt.setText("Monday");
            scCombo1.setDisable(false);
            scCombo2.setDisable(false);
            scCombo3.setDisable(false);
            scCombo4.setDisable(false);
            btConfirm.setDisable(false);
        }
        if (timeTableList.size() == 1) {
            timeTable.setItems((ObservableList<TimeTable>) timeTableList);

            dayTxt.setText("Tuesday");
            scCombo1.setDisable(false);
            scCombo2.setDisable(false);
            scCombo3.setDisable(false);
            scCombo4.setDisable(false);
            btadd.setDisable(false);

        }
        if (timeTableList.size() == 2) {
            timeTable.setItems((ObservableList<TimeTable>) timeTableList);

            dayTxt.setText("Wednesday");
            scCombo1.setDisable(false);
            scCombo2.setDisable(false);
            scCombo3.setDisable(false);
            scCombo4.setDisable(false);
            btadd.setDisable(false);
        }
        if (timeTableList.size() == 3) {
            timeTable.setItems((ObservableList<TimeTable>) timeTableList);

            dayTxt.setText("Thursday");
            scCombo1.setDisable(false);
            scCombo2.setDisable(false);
            scCombo3.setDisable(false);
            scCombo4.setDisable(false);
            btadd.setDisable(false);
        }
        if (timeTableList.size() == 4) {
            timeTable.setItems((ObservableList<TimeTable>) timeTableList);

            dayTxt.setText("Friday");
            scCombo1.setDisable(false);
            scCombo2.setDisable(false);
            scCombo3.setDisable(false);
            scCombo4.setDisable(false);
            btadd.setDisable(false);
        }

        if (timeTableList.size() > 4) {
            timeTable.setItems((ObservableList<TimeTable>) timeTableList);
            scCombo1.setDisable(true);
            scCombo2.setDisable(true);
            scCombo3.setDisable(true);
            scCombo4.setDisable(true);
            btadd.setDisable(true);
        }

    }

    @FXML
    private void btAddOnClick(ActionEvent event) throws SQLException, ParseException {
        if (scCombo1.getValue() == null || scCombo2.getValue() == null
                || scCombo3.getValue() == null || scCombo4.getValue() == null) {
            error.setVisible(true);
        } else {
            TimeTable timeTable;
            String science1 = (String) scCombo1.getSelectionModel().getSelectedItem();
            String science2 = (String) scCombo2.getSelectionModel().getSelectedItem();
            String science5 = (String) scCombo3.getSelectionModel().getSelectedItem();
            String science4 = (String) scCombo4.getSelectionModel().getSelectedItem();
            String science3 = "pause";
            String day = dayTxt.getText();

            timeTable = new TimeTable(science1, science2, science3, science4, science5, id_class, day);
            System.out.println(id_class);
            new ServicesTimeTable().addTimeTable(timeTable);

            refreshTable();

        }

    }

    @FXML
    private void btConfirmOnClick(ActionEvent event) throws SQLException, ParseException {
        t = timeTable.getSelectionModel().getSelectedItem();

        t.setSeance1(scCombo1.getSelectionModel().getSelectedItem());
        t.setSeance2(scCombo2.getSelectionModel().getSelectedItem());
        t.setSeance4(scCombo4.getSelectionModel().getSelectedItem());
        t.setSeance5(scCombo3.getSelectionModel().getSelectedItem());

        ServicesTimeTable servicesTimeTable = new ServicesTimeTable();
        servicesTimeTable.updateTimeTable(t);
        refreshTable();
        btConfirm.setVisible(false);
        btadd.setVisible(true);

    }

    @FXML
    private void btExportOnClick(ActionEvent event) throws SQLException, ParseException {

        JFileChooser dialog = new JFileChooser();
        int dialogResult = dialog.showSaveDialog(null);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            String filePath = dialog.getSelectedFile().getPath();
            try {
                Document document = new Document();
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(filePath + ".pdf"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AddTimeTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                document.open();
                PdfPTable table = new PdfPTable(6);
                ObservableList<TimeTable> timeTableList;
                timeTableList = new ServicesTimeTable().readAll(time);

                PdfPCell c1 = new PdfPCell(new Phrase("Day"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setPadding(5);

                table.addCell(c1);

                c1 = new PdfPCell(new Phrase("08:00->10:00"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);

                c1 = new PdfPCell(new Phrase("10:00->12:00"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("12:00->13:00"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("13:00->15:00"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("15:00->17:00"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                table.setHeaderRows(1);
                for (int i = 0; i < timeTableList.size(); i++) {
                    String day = timeTableList.get(i).getDay();
                    String s1 = timeTableList.get(i).getSeance1();
                    String s2 = timeTableList.get(i).getSeance2();
                    String s3 = timeTableList.get(i).getSeance3();
                    String s4 = timeTableList.get(i).getSeance4();
                    String s5 = timeTableList.get(i).getSeance5();

                    table.addCell(day);
                    table.addCell(s1);
                    table.addCell(s2);
                    table.addCell(s3);
                    table.addCell(s4);
                    table.addCell(s5);
                }
                Paragraph preface = new Paragraph();

                preface.add(new Paragraph("Time Table", catFont));

                addEmptyLine(preface, 3);
                document.add(preface);
                document.add(table);
                addMetaData(document);
                //addContent(document);
                document.close();
                document.close();
            } catch (DocumentException de) {
                de.printStackTrace();
            }

        }
    }

    @FXML
    private void btCancelOnClick(ActionEvent event) throws FileNotFoundException, BadElementException, SQLException, ParseException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Go Back");
        alert.setHeaderText(" interrupt");
        alert.setContentText("want to go back to the previous page ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("homeBinder.fxml"));

            Parent root = loader.load();
            HomeBinderController ugc = loader.getController();

            ugc.setTimeTable(gr);
            btCancel.getScene().setRoot(root);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }

    }

    /*private void Pdf() throws FileNotFoundException, DocumentException {

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/
    private static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Time Table", catFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date

        document.add(preface);
        // Start a new page

    }

    private void addContent(Document document) throws DocumentException, BadElementException, SQLException, ParseException {
        Anchor anchor = new Anchor("First Chapter", catFont);
        anchor.setName("First Chapter");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("", subFont);
        Section subCatPart = catPart.addSection(subPara);

        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 1);
        subCatPart.add(paragraph);

        // add a table
        createTable();

        // now add all this to the document
        document.add(catPart);

    }

    private void createTable()
            throws BadElementException, SQLException, ParseException {
        PdfPTable table = new PdfPTable(6);
        ObservableList<TimeTable> timeTableList;

        timeTableList = new ServicesTimeTable().readAll(time);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);
        PdfPCell c1 = new PdfPCell(new Phrase("Day"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPadding(5);

        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("08:00->10:00"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("10:00->12:00"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("12:00->13:00"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("13:00->15:00"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("15:00->17:00"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);
        for (int i = 0; i < timeTableList.size(); i++) {
            String day = timeTableList.get(i).getDay();
            String s1 = timeTableList.get(i).getSeance1();
            String s2 = timeTableList.get(i).getSeance2();
            String s3 = timeTableList.get(i).getSeance3();
            String s4 = timeTableList.get(i).getSeance4();
            String s5 = timeTableList.get(i).getSeance5();

            table.addCell(day);
            table.addCell(s1);
            table.addCell(s2);
            table.addCell(s3);
            table.addCell(s4);
            table.addCell(s5);
        }

        //    subCatPart.add(table);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }

    }

}
