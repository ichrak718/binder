/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.Iservices;

import java.sql.SQLException;
import java.util.List;
import tn.esprit.binder.entities.Pupils;

/**
 *
 * @author Asus
 */
public interface IservicesPupils<P> {
    public List<P> readAll(Pupils p)throws SQLException;
    public void addPupils(Pupils p);
    public void updatePupils(Pupils p) throws SQLException;
    public void deletePupils(int i) throws SQLException;  
    
}
