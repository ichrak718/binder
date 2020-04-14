/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Asus
 */
public class MyConnection {

    private String url="jdbc:mysql://localhost:3306/pibinder";
    private String login = "root";
    private String mdp = "";
    private static MyConnection instance;
    Connection cnx;

    private MyConnection() {
        try {
            cnx = DriverManager.getConnection(url, login, mdp);

            System.out.println("Connexion établie");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static MyConnection getInstance() {
        if (instance == null) {
            instance = new MyConnection();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

}
