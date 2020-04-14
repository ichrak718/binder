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
import java.util.function.Predicate;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.services.ServicesPupils;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class GestionPupilController implements Initializable {

    @FXML
    private TableView<Pupils> tablePupil;
    @FXML
    private TableColumn<Pupils, Integer> idPupil;
    @FXML
    private TableColumn<Pupils, String> namePupil;
    @FXML
    private TableColumn<Pupils, Date> birthdayPupil;
    @FXML
    private TableColumn<Pupils, String> emailPupil;
    @FXML
    private Button btAdd;
    @FXML
    private Button btUpdate;
    @FXML
    private Button btDelete;

    
       int id_class;
        Classes gr;

    
    
    private Stage stage;
    private Pupils pupils = new Pupils();
    private List<Pupils> pupilsList;
    private ServicesPupils ser = new ServicesPupils();
    @FXML
    private Button btGoBack;
    @FXML
    private TextField search;
    final ObservableList<Pupils> data = FXCollections.observableArrayList();
    @FXML
    private Button btMail;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
             
      gr.setId(id_class);
      idPupil.setCellValueFactory(new PropertyValueFactory<Pupils, Integer>("id"));
            namePupil.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFullname()));
            birthdayPupil.setCellValueFactory(new PropertyValueFactory<Pupils, Date>("birthday"));
            emailPupil.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
           // classPupil.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getClasses()));
            
            pupilsList = new ServicesPupils().readAll(pupils);
            tablePupil.setItems((ObservableList<Pupils>) pupilsList);
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
      
        

    }
        /*
        FilteredList<Pupils> filteredList = new FilteredList<>(data, e-> true);
      
      search.setOnKeyReleased(e->{
          search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((Predicate<? super Pupils>) pupils->{
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter =newValue.toLowerCase();
                if(pupils.getFullname().toLowerCase().contains(lowerCaseFilter)){
                  return true;
                }
                else if (pupils.getClasses().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
          });
          SortedList<Pupils> sortedList =new SortedList<>(filteredList);
          sortedList.comparatorProperty().bind(tablePupil.comparatorProperty());
          tablePupil.setItems(sortedList);
      });
         */       
                
            

    @FXML
    private void btAddOnClick(ActionEvent event) throws ParseException, SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addPupil.fxml"));
            Parent root;
            root = loader.load();
            AddPupilController pupilController = loader.getController();
            pupilController.getStage(this.stage);
            tablePupil.getScene().setRoot(root);
            pupilController.setGrade(gr);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btUpdateOnClick(ActionEvent event) throws ParseException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update pupils");
        alert.setHeaderText("Updating  "+tablePupil.getSelectionModel().getSelectedItem().getFullname() );
        alert.setContentText("Would you like to update  " + tablePupil.getSelectionModel().getSelectedItem().getFullname() + "?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                
                pupils = tablePupil.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updatePupil.fxml"));
                Parent root = loader.load();
                UpdatePupilController pupilController = loader.getController();
                pupilController.setPupil(pupils);
                tablePupil.getScene().setRoot(root);
                pupilController.setGrade(gr);


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
            alert.setTitle("Delete Pupil");
            alert.setHeaderText("Delete  " + tablePupil.getSelectionModel().getSelectedItem().getFullname());
            alert.setContentText("Would you like to delete " + tablePupil.getSelectionModel().getSelectedItem().getFullname() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                new ServicesPupils().deletePupils(tablePupil.getSelectionModel().getSelectedItem().getId());
                pupilsList = new ServicesPupils().readAll(pupils);
                tablePupil.getItems().clear();
                tablePupil.getItems().addAll(FXCollections.observableList(pupilsList));
            }
            
            if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    @FXML
    private void btGoBackOnClick(ActionEvent event) throws IOException, ParseException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("homeBinder.fxml"));

            Parent root = loader.load();
            HomeBinderController ugc = loader.getController();

            ugc.setPupil(gr);
            btGoBack.getScene().setRoot(root);
    }

    
    public void getStage(Stage stage) {
        this.stage = stage;
    }
                public void actualiserTable() {
        tablePupil.getItems().clear();
        tablePupil.getItems().addAll(FXCollections.observableList(pupilsList));
    }
    @FXML
    private void tfRechecherOnKeyReleased(KeyEvent event) {
           try {
            pupilsList = new ServicesPupils().search(search.getText());
            actualiserTable();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
        
            public void setGrade(Classes gr) throws ParseException, SQLException {
        this.gr = gr;
        id_class = gr.getId();
        pupils.setClasses(id_class);
       // actualiserTable();
                   idPupil.setCellValueFactory(new PropertyValueFactory<Pupils, Integer>("id"));
            namePupil.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFullname()));
            birthdayPupil.setCellValueFactory(new PropertyValueFactory<Pupils, Date>("birthday"));
            emailPupil.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
           // classPupil.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getClasses()));
            
            pupilsList = new ServicesPupils().readAll(pupils);
            tablePupil.setItems((ObservableList<Pupils>) pupilsList);           
        }

    @FXML
    private void btMailOnClick(ActionEvent event) throws IOException, ParseException, SQLException {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("mailling.fxml"));
            Parent root;
            root = loader.load();
            MaillingController maillingController = loader.getController();
            maillingController.getStage(this.stage);
            tablePupil.getScene().setRoot(root);
            maillingController.setGrade(gr);
    }


  
}
