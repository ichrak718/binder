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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.binder.Iservices.IservicesPupils;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.entities.TimeTable;
import tn.esprit.binder.utils.MyConnection;

/**
 *
 * @author Asus
 */
public class ServicesPupils implements IservicesPupils<Pupils>{
     private Connection cnx;
    private Statement ste;
        private PreparedStatement st;

    
        public ServicesPupils() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public ObservableList<Pupils> readAll(Pupils p) throws SQLException {

        ObservableList<Pupils> pupilsList = FXCollections.observableArrayList();
              st = cnx.prepareStatement
       ("SELECT * FROM pupils p INNER JOIN classes c on p.classes=c.id  where p.classes=? ;");

    st.setInt(1, p.getClasses());
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString("fullname");
            String birthday = rs.getString("birthday");
            String email = rs.getString("email");
            int classes = rs.getInt("classes");
             p = new Pupils(id, name, email, birthday, classes);
            pupilsList.add(p);
            System.out.println(p);
        }
        return pupilsList;
    }
 

           
    
    

    @Override
    public void addPupils(Pupils p) {
       try {
            ste = cnx.createStatement();
            String req = "INSERT INTO pupils VALUES (NULL, '" + p.getFullname() + "','" + p.getBirthday() + "','" + p.getEmail() + "','" + p.getClasses() + "')";
            ste.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    }

    @Override
    public void updatePupils(Pupils p) throws SQLException {
        ste = cnx.createStatement();
        String req = "update pupils set fullname = '"
                +p.getFullname()
                + "',birthday='"
                + p.getBirthday()
                + "',email='"
                + p.getEmail()
                + "',classes='"
                + p.getClasses()
                + "' where id = '"
                + p.getId() + "'";
        if (ste.executeUpdate(req) == 1) {
            System.out.println("modification effectué");
        }     }

    @Override
    public void deletePupils(int i) throws SQLException {
        try {
            String req = "DELETE FROM pupils WHERE id=" + i;
            PreparedStatement pt;
            pt = cnx.prepareStatement(req);
            pt.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    
    }
    

         public ObservableList<String> readClass() throws SQLException {
        ObservableList<String> arr = FXCollections.observableArrayList();
        st = cnx.prepareStatement("SELECT * FROM classes");
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            String classe = rs.getString("name");
            arr.add(classe);
        }
        return arr;
    }
         
             

         
         public List<Pupils> search(String t) throws SQLException {
             Pupils pu=new Pupils();
        List<Pupils> pupils = new ArrayList<>();
        pupils = readAll(pu);
        List<Pupils> p = pupils.stream()
                .filter(a -> a.getFullname().toString().contains(t))
                .collect(Collectors.toList());
        return p;
    
}
         
             
    public ObservableList<Pupils> readAlla(Pupils p) throws SQLException {

        ObservableList<Pupils> pupilsList = FXCollections.observableArrayList();
              st = cnx.prepareStatement
       ("SELECT email FROM pupils p INNER JOIN classes c on p.classes=c.id  where p.classes=? ;");

    st.setInt(1, p.getClasses());
        ResultSet rs = st.executeQuery();
        while (rs.next()) {


            String email = rs.getString("email");
             p = new Pupils(email);
            pupilsList.add(p);
            System.out.println(p);
        }
        return pupilsList;
    }
}
