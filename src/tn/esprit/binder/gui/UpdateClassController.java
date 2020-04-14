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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.services.ServicesClasses;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class UpdateClassController implements Initializable {

    @FXML
    private TextField sessionTxt;
    @FXML
    private TextField nameClassTxt;
    @FXML
    private Button btUpdate;
    @FXML
    private Button btCancel;
    Classes classes;
    @FXML
    private Label error;
    public List<Classes> classesL;
        Classes gr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
      
    }    

    @FXML
    private void btUpdateOnClick(ActionEvent event) throws IOException, ParseException {
                try {
               
              if (nameClassTxt.getText().equals("")|| 
                sessionTxt.getText().equals("") ){
            error.setVisible(true);
        }
              
           else{
     classes.setNameClass(nameClassTxt.getText());
                classes.setSession(sessionTxt.getText());         
                    ServicesClasses servicesClasses=new ServicesClasses();
                    servicesClasses.updateClass(classes);
                               FXMLLoader loader = new FXMLLoader(getClass().getResource("homeBinder.fxml"));

            Parent root = loader.load();
            HomeBinderController ugc = loader.getController();

            ugc.setPupil(gr);
            btUpdate.getScene().setRoot(root);
 }
           
                
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
        }
    }

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
    
    
    
     public void setClass(Classes c) throws ParseException {

        this.classes = c;
        nameClassTxt.setText(c.getNameClass());
        sessionTxt.setText(c.getSession());
        
        System.out.println(c);
    }
}
