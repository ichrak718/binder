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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.binder.Iservices.IservicesTimeTable;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.TimeTable;
import tn.esprit.binder.utils.MyConnection;

/**
 *
 * @author Asus
 */
public class ServicesTimeTable implements IservicesTimeTable<TimeTable>{
    
       private Connection cnx;
    private PreparedStatement ste;
    private  Statement st;
 

    public ServicesTimeTable() {
                cnx = MyConnection.getInstance().getCnx();

    }
    
//************************************READ**************************************
    public ObservableList<TimeTable> readAll(TimeTable t) throws SQLException, ParseException {
       
        ObservableList<TimeTable> arr = FXCollections.observableArrayList();
        ste = cnx.prepareStatement
        ("SELECT * FROM timetable t INNER JOIN classes c on t.id_classe=c.id  where t.id_classe=? ;");

        ste.setInt(1, t.getIdClasse());
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(1);
            String seance1 = rs.getString("seance1");
            String seance2 = rs.getString("seance2");
            String seance3 = rs.getString("seance3");
            String seance4 = rs.getString("seance4");
            String seance5 = rs.getString("seance5");
            int id_class= rs.getInt("id_classe");
            String day =rs.getString("day");
         t=new TimeTable(seance1, seance2, seance3, seance4, seance5, id,id_class,day) ;         
        arr.add(t);
            System.out.println(t);
        }
        return arr;
    }
//***************************************ADD*******************************
    @Override
    public void addTimeTable(TimeTable t) {
     try {
            String req = "INSERT INTO timetable(seance1,seance2,seance3,seance4,seance5,id_classe,day)"
                    + " VALUES (?,?,?,?,?,?,?)";
            ste = cnx.prepareStatement(req);
            ste.setString(1, t.getSeance1());
            ste.setString(2, t.getSeance2());
            ste.setString(3, t.getSeance3());
            ste.setString(4, t.getSeance4());
            ste.setString(5, t.getSeance5());
            ste.setInt(6, t.getIdClasse());
            ste.setString(7, t.getDay());    
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    }

    //************************UPDATE***************************************************
    @Override
    public void updateTimeTable(TimeTable t) throws SQLException {

                st = cnx.createStatement();
        String req = "update timetable set seance1 = '"
                +t.getSeance1()
                + "',seance2='"
                + t.getSeance2()
                  + "',seance4='"
                + t.getSeance4()
                  + "',seance5='"
                + t.getSeance5()
               
                + "' where id = '"
                + t.getId() + "'";
        if (st.executeUpdate(req) == 1) {
            System.out.println("modification effectué");
        }  
    }
//*****************************************READ******************************************
    @Override
    public void deleteTimeTable(int i) throws SQLException {
         try {
            String req = " DELETE  FROM timetable WHERE id=" + i;
            PreparedStatement pt;
            pt = cnx.prepareStatement(req);
            pt.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    }   

    

   @Override
       public ObservableList<String> readAllSubject() throws SQLException {
        ObservableList<String> arr = FXCollections.observableArrayList();
        ste = cnx.prepareStatement("SELECT * FROM subject");
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            int idSubject = rs.getInt(1);
            String subject = rs.getString("subjectName");
            arr.add(subject);
        }
        return arr;
    }
}
