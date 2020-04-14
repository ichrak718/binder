/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.entities;

import java.util.Date;

/**
 *
 * @author Asus
 */
public class Pupils {
   private int id ;
    private String fullname;
  private String email;
  private String birthday;
  private int classes;
  

    public Pupils() {
    }

    public Pupils(String email) {
        this.email = email;
    }
    
    
    

    public Pupils(int id, String fullname, String email, String birthday, int classes) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.birthday = birthday;
        this.classes = classes;
    }

    public Pupils(String name, String date, String email, int classes) {
        this.fullname=name;
        this.birthday=date;
        this.email=email;
        this.classes=classes;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getClasses() {
        return classes;
    }

    public void setClasses(int classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Pupils{" + "id=" + id + ", fullname=" + fullname + ", email=" + email + ", birthday=" + birthday + ", classes=" + classes + '}';
    }

    
}
