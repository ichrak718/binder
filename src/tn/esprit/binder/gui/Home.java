/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.binder.entities.Pupils;

/**
 *
 * @author Asus
 */
public class Home extends Application{

 public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("homeBinder.fxml"));
            Parent n;
            n = (Parent) loader.load();
            HomeBinderController GP = loader.getController();
          //  GP.getStage(primaryStage);

            Scene scene = new Scene(n);
            primaryStage.setTitle("pupils");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    
}
