/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.entities; 

/**
 *
 * @author Asus
 */
public class Classes {
  private int id;
  private String nameClass;
   private String session;

    public Classes() {
    }

    public Classes(int id) {
        this.id = id;
    }



    public Classes(int id, String nameClass, String session) {
        this.id = id;
        this.nameClass = nameClass;
        this.session = session;


    }


    


    


    public Classes(String nameClass, String session) {
       this.nameClass = nameClass;
        this.session = session;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Classes{" + "id=" + id + ", nameClass=" + nameClass + ", session=" + session + '}';
    }



    


}