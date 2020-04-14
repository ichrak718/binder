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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.services.ServicesClasses;
import tn.esprit.binder.services.ServicesPupils;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DefaultStringConverter;
import tn.esprit.binder.entities.TimeTable;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class AddClassController implements Initializable {

    private TableView<TimeTable> timeTable;
    private TableColumn<TimeTable, String> day;
    private TableColumn<TimeTable, String> science3;
    private Button btAdd;
    private TextField nameClassTxt;
    private TextField sessionTxt;
    public TimeTable classes = new TimeTable();

    Stage stage;
    public ObservableList<String> subject;
    @FXML
    private Button btCancel;
    @FXML
    private Label error;
    public List<Classes> classesL = new ArrayList<Classes>();
        Classes gr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServicesClasses servicesClasses = new ServicesClasses();
        try {
            classesL = servicesClasses.readAll();
        } catch (SQLException ex) {
            Logger.getLogger(AddClassController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btAddOnClick(ActionEvent event) throws IOException, SQLException, ParseException {
        Classes p;
        String nameClass = nameClassTxt.getText();
        String session = sessionTxt.getText();
        int i;
        String className;

        
        if (nameClassTxt.getText().equals("")
                || sessionTxt.getText().equals("")) {
            error.setVisible(true);
        }
        
       /* else if(nameClassTxt.getText()!=null){
        for (i = 0; i < classesL.size(); i++) {

            className = classesL.get(i).getNameClass();
            System.out.println(className);
            if (className.equals(nameClass)) {
                error.setVisible(true);
                error.setText("already exist");
            }
        }
        }*/
        else {

            p = new Classes(nameClass, session);
            new ServicesClasses().addClass(p);

                               FXMLLoader loader = new FXMLLoader(getClass().getResource("homeBinder.fxml"));

            Parent root = loader.load();
            HomeBinderController ugc = loader.getController();

            ugc.setPupil(gr);
            btCancel.getScene().setRoot(root);
        }

    }

// use modified standard combo cell shows its popup on startEdit
    @FXML
    private void btCancelOnClick(ActionEvent event) throws ParseException {
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

            ugc.setPupil(gr);
            btCancel.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    public void getStage(Stage stage) {
        this.stage = stage;
    }


    
}
