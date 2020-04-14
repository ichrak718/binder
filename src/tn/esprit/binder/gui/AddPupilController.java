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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.services.ServicesClasses;
import tn.esprit.binder.services.ServicesPupils;
import tn.esprit.binder.services.ServicesTimeTable;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class AddPupilController implements Initializable {

    @FXML
    private AnchorPane anchorAffiches;
    @FXML
    private Button btAdd;
    @FXML
    private TextField namePupTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button btCancel;
    @FXML
    private Label error;
    @FXML
    private Label errorEmail;

    Pupils pupil;
    ServicesPupils ser = new ServicesPupils();
    Stage stage;
    public List<String> classe;

    int id_class;
    Classes gr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            classe = new ServicesPupils().readClass();
        } catch (SQLException ex) {
            Logger.getLogger(AddPupilController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btAddOnClick(ActionEvent event) throws IOException, ParseException, SQLException {
        if (namePupTxt.getText().equals("") || datePicker.getValue() == null
                || emailTxt.getText().equals("")) {
            error.setVisible(true);
        }
        if (valitadeEmail(emailTxt.getText().toString())== false) {

            errorEmail.setVisible(true);

        } else {

            String name = namePupTxt.getText();
            String date = datePicker.getValue().toString();
            String email = emailTxt.getText();

            pupil = new Pupils(name, date, email, id_class);

            new ServicesPupils().addPupils(pupil);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gestionPupil.fxml"));

            Parent root = loader.load();
            GestionPupilController ugc = loader.getController();

            ugc.setGrade(gr);

            btAdd.getScene().setRoot(root);
            error.setVisible(false);
            errorEmail.setVisible(false);
        }
    }

    @FXML
    private void cancelOnClick(ActionEvent event) throws ParseException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Go Back");
        alert.setHeaderText(" interrupt");
        alert.setContentText("want to go back to the previous page ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("gestionPupil.fxml"));

                Parent root = loader.load();
                GestionPupilController ugc = loader.getController();

                ugc.setGrade(gr);

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

    private boolean valitadeEmail(String email) {
    /*    Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9.-]*@[a-zA-Z]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(emailTxt.getText());
        if (m.find() && m.group().equals(emailTxt.getText())) {
            return true;
        } else {
            return false;
        }*/
            boolean isValid = false;
        try {
            // Create InternetAddress object and validated the supplied
            // address which is this case is an email address.
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            isValid = true;
        } catch (AddressException e) {
            e.printStackTrace();
        }
        return isValid;

    }

    public void setGrade(Classes gr) throws ParseException, SQLException {
        this.gr = gr;
        id_class = gr.getId();
        //    pupils.setClasses(id_class);
        // actualiserTable();

    }

}
