/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.binder.Iservices.IservicesClasses;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.entities.TimeTable;
import tn.esprit.binder.gui.AddTimeTableController;
import tn.esprit.binder.utils.MyConnection;

/**
 *
 * @author Asus
 */
public class ServicesClasses implements IservicesClasses<Classes> {

    private Connection cnx;
    private PreparedStatement ste;
    private  Statement st;
  

    public ServicesClasses() {
        cnx = MyConnection.getInstance().getCnx();

    }

    @Override
    public ObservableList<Classes> readAll() throws SQLException {
        ObservableList<Classes> arr = FXCollections.observableArrayList();
        ste = cnx.prepareStatement("SELECT * FROM classes");
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(1);
            String nameClass = rs.getString("name");
            String session = rs.getString("session");

            Classes c = new Classes(id, nameClass, session);
            arr.add(c);
        }
        return arr;
    }

    @Override
    public void addClass(Classes c) {
        try {
            String req = "INSERT INTO classes(name,session ) values (?,?)";
            ste = cnx.prepareStatement(req);
            ste.setString(1, c.getNameClass());
            ste.setString(2, c.getSession());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateClass(Classes c) throws SQLException {
        
        
        
                st = cnx.createStatement();
        String req = "update classes set name = '"
                +c.getNameClass()
                + "',session='"
                + c.getSession()
               
                + "' where id = '"
                + c.getId() + "'";
        if (st.executeUpdate(req) == 1) {
            System.out.println("modification effectué");
        }  
        
    }
    //********************************delete****************************************
    @Override
    public void deleteClass(int i) throws SQLException {
 try {
            String req = " DELETE  FROM classes WHERE id=" + i;
            PreparedStatement pt;
            pt = cnx.prepareStatement(req);
            pt.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    }   
    
    
    
 
  
}


    
        
    

