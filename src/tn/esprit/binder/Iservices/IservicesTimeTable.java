/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.Iservices;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javafx.collections.ObservableList;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.entities.TimeTable;

/**
 *
 * @author Asus
 */
public interface IservicesTimeTable<T> {
    public ObservableList<T> readAll(TimeTable t)throws SQLException ,ParseException;
    public void addTimeTable(TimeTable t);
    public void updateTimeTable(TimeTable t) throws SQLException;
    public void deleteTimeTable(int i) throws SQLException;  
    public ObservableList<String> readAllSubject()throws SQLException ,ParseException;
    
}
