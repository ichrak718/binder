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
import java.time.Month;
import java.util.List;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.services.ServicesPupils;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class UpdatePupilController implements Initializable {

    @FXML
    private AnchorPane anchorAffiches;
    @FXML
    private Button btUpdate;
    @FXML
    private Button btCancel;
    @FXML
    private TextField namePupTxt;
    @FXML
    private TextField emailTxt;

    @FXML
    private DatePicker datePicker;
    @FXML
    private Label birthdayTxt;
    @FXML
    private Label error;
    @FXML
    private Label errorEmail;

    private Pupils pupil;
    private ServicesPupils servicePupil = new ServicesPupils();
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
        birthdayTxt.setTooltip(new Tooltip("Double Click To Edit"));
        birthdayTxt.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                birthdayTxt.setVisible(false);
                datePicker.setVisible(true);
            }
        });
        anchorAffiches.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                birthdayTxt.setVisible(true);
                datePicker.setVisible(false);
                birthdayTxt.setText(datePicker.getValue().toString());
            }
        });

    }

    @FXML
    private void btUpdateOnClick(ActionEvent event) throws IOException, ParseException {

        try {

            pupil.setFullname(namePupTxt.getText());
            if (datePicker.getValue() != null) {
                birthdayTxt.setText(datePicker.getValue().toString());
            }

            if (namePupTxt.getText().equals("")
                    || emailTxt.getText().equals("") ) {
                error.setVisible(true);
            }
            if (valitadeEmail() == false) {

                errorEmail.setVisible(true);

            } else {
                pupil.setBirthday(birthdayTxt.getText());
                pupil.setEmail(emailTxt.getText());
                servicePupil.updatePupils(pupil);
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("gestionPupil.fxml"));

            Parent root = loader.load();
            GestionPupilController ugc = loader.getController();
                                ugc.setGrade(gr);


                btUpdate.getScene().setRoot(root);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
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

    public void setPupil(Pupils p) throws ParseException {

        this.pupil = p;
        namePupTxt.setText(p.getFullname());
        emailTxt.setText(p.getEmail());
        birthdayTxt.setText(p.getBirthday());
        System.out.println(p);
    }

    private boolean valitadeEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9.-]*@[a-zA-Z]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(emailTxt.getText());
        if (m.find() && m.group().equals(emailTxt.getText())) {
            return true;
        } else {
            return false;
        }

    }
                public void setGrade(Classes gr) throws ParseException, SQLException {
        this.gr = gr;
        id_class = gr.getId();
    //    pupils.setClasses(id_class);
            System.out.println(id_class);
       // actualiserTable();
   
}

}
