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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.TimeTable;
import tn.esprit.binder.services.ServicesClasses;
import tn.esprit.binder.services.ServicesTimeTable;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class HomeBinderController implements Initializable {

    @FXML
    private Button btClass;
    @FXML
    private AnchorPane anchorClass;
    @FXML
    private AnchorPane anchorHome;
    @FXML
    private TableView<Classes> tableClass;
    @FXML
    private TableColumn<Classes, Integer> idClass;
    @FXML
    private TableColumn<Classes, String> nameClass;
    @FXML
    private TableColumn<Classes, String> session;
    @FXML
    private Button btAdd;
    @FXML
    private Button btUpdate;
    @FXML
    private Button btDelete;
    @FXML
    private Button btGoBack;
    @FXML
    private Button btTimeTable;
    @FXML
    private Button btPupilTable;
    @FXML
    private AnchorPane anchorClassGestion;
    /**
     * Initializes the controller class.
     */
    
    
    
    
    //Declaration Ichrak 
    private Stage stage;
    public Classes classes;
    public List<Classes> classesL;
    public List<String> subject;
    TimeTable t = new TimeTable();
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.RED);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Classes gr;

    public TimeTable time = new TimeTable();

    public ObservableList<TimeTable> timeTableList;
    final List<TimeTable> timeL = new ArrayList<>();

    int id_class;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                try {
                    //ichrak----------------------------------------------------------------------
            idClass.setCellValueFactory(new PropertyValueFactory<Classes, Integer>("id"));
            nameClass.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNameClass()));
            session.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSession()));

            classesL = new ServicesClasses().readAll();
            tableClass.setItems((ObservableList<Classes>) classesL);
            
           
            
            
            //--------------------------------------------------------------------------------------------
            

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        // TODO
    }    

    @FXML
    private void btClassOnClick(ActionEvent event) {
        anchorHome.setVisible(false);
        anchorClass.setVisible(true);
        anchorClassGestion.setVisible(true);
        
    }

    @FXML
    private void btAddOnClick(ActionEvent event) {
                try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addClass.fxml"));
            Parent root;
            root = loader.load();
            AddClassController afc = loader.getController();
            afc.getStage(this.stage);
            tableClass.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btUpdateOnClick(ActionEvent event) throws ParseException {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Class");
        alert.setHeaderText("Updating  "+tableClass.getSelectionModel().getSelectedItem().getNameClass());
        alert.setContentText("Would you like to update  " + tableClass.getSelectionModel().getSelectedItem().getNameClass() + "?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                classes = tableClass.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateClass.fxml"));
                Parent root = loader.load();
                UpdateClassController classController = loader.getController();
                classController.setClass(classes);
                tableClass.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    @FXML
    private void btDeleteOnClick(ActionEvent event) {
                 try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Class");
            alert.setHeaderText("Delete  " + tableClass.getSelectionModel().getSelectedItem().getNameClass());
            alert.setContentText("Would you like to delete " + tableClass.getSelectionModel().getSelectedItem().getNameClass()+ "?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                new ServicesClasses().deleteClass(tableClass.getSelectionModel().getSelectedItem().getId());
                classesL = new ServicesClasses().readAll();
                tableClass.getItems().clear();
                tableClass.getItems().addAll(classesL);
            }
            
            if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btTimeTableOnClick(ActionEvent event) throws ParseException, SQLException {
                       

     try {

            classes = tableClass.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("addtimeTable.fxml"));

            Parent root = loader.load();
            AddTimeTableController ugc = loader.getController();

            ugc.setGrade(classes);

            tableClass.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


        
    }

    @FXML
    private void btPupilsOnClick(ActionEvent event) throws ParseException, SQLException {
                     try {

            classes = tableClass.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("gestionPupil.fxml"));

            Parent root = loader.load();
            GestionPupilController ugc = loader.getController();

            ugc.setGrade(classes);

            tableClass.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    

    }

        public void getStage(Stage stage) {
        this.stage = stage;
    }

        
            
     public void setTimeTable(Classes c) throws ParseException {

      
        anchorClass.setVisible(true);
        anchorClassGestion.setVisible(true);
        anchorHome.setVisible(false);
       
    }
          public void setPupil(Classes c) throws ParseException {

      
        anchorClass.setVisible(true);
        anchorClassGestion.setVisible(true);
        anchorHome.setVisible(false);
       
    }
}
