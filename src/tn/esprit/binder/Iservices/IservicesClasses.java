/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.Iservices;

import java.sql.SQLException;
import java.util.List;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.entities.TimeTable;

/**
 *
 * @author Asus
 */
public interface IservicesClasses<C> {
    public List<C> readAll()throws SQLException;
    public void addClass(Classes c);
    public void updateClass(Classes c) throws SQLException;
    public void deleteClass(int i) throws SQLException;  
       

}
