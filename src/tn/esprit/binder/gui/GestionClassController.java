/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.entities.TimeTable;
import tn.esprit.binder.services.ServicesClasses;
import tn.esprit.binder.services.ServicesPupils;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class GestionClassController implements Initializable {

    @FXML
    private Button btUpdate;
    @FXML
    private Button btDelete;
    private Stage stage;
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
    private Button btGoBack;
    
    @FXML
    private Button btTimeTable;
    
  public Classes classes;
    public List<Classes> classesL;
    @FXML
    private Button btPupilTable;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // 
        try {
            idClass.setCellValueFactory(new PropertyValueFactory<Classes, Integer>("id"));
            nameClass.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNameClass()));
            session.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSession()));

            classesL = new ServicesClasses().readAll();
            tableClass.setItems((ObservableList<Classes>) classesL);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
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

    public void getStage(Stage stage) {
        this.stage = stage;
    }


 
    @FXML
    private void btGoBackOnClick(ActionEvent event) throws IOException  {
         Parent root;
                root = FXMLLoader.load(getClass().getResource("homeBinder.fxml"));
                btGoBack.getScene().setRoot(root);
      
    }

    @FXML
    private void btTimeTableOnClick(ActionEvent event) throws ParseException {
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
}
