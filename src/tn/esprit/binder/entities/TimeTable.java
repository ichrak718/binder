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
public class TimeTable {

    private String seance1;
    private String seance2;
    private String seance3;
    private String seance4;
    private String seance5;
    private int id;
    private int idClasse;
    String day;
    String subject;
    int idSubject;

    public TimeTable() {
    }

    public TimeTable(String seance1, String seance2, String seance3, String seance4, String seance5, int id, int idClasse,String day) {
        this.seance1 = seance1;
        this.seance2 = seance2;
        this.seance3 = seance3;
        this.seance4 = seance4;
        this.seance5 = seance5;
        this.id = id;
        this.idClasse = idClasse;
        this.day=day;
        
    }
        public TimeTable(String seance1, String seance2, String seance3, String seance4, String seance5,  int idClasse,String day) {
        this.seance1 = seance1;
        this.seance2 = seance2;
        this.seance3 = seance3;
        this.seance4 = seance4;
        this.seance5 = seance5;
        this.idClasse = idClasse;
        this.day=day;
    }
        

    public TimeTable(String seance1, String seance2, String seance3, String seance4, String seance5) {
        this.seance1 = seance1;
        this.seance2 = seance2;
        this.seance3 = seance3;
        this.seance4 = seance4;
        this.seance5 = seance5;
    }

    public TimeTable(String day) {
        this.day = day;
    }

   public TimeTable(String day,String seance1, String seance2, String seance3, String seance4, String seance5) {
        this.seance1 = seance1;
        this.seance2 = seance2;
        this.seance3 = seance3;
        this.seance4 = seance4;
        this.seance5 = seance5;
        this.day=day;
    }
    
    

    public String getSeance1() {
        return seance1;
    }

    public void setSeance1(String seance1) {
        this.seance1 = seance1;
    }

    public String getSeance2() {
        return seance2;
    }

    public void setSeance2(String seance2) {
        this.seance2 = seance2;
    }

    public String getSeance3() {
        return seance3;
    }

    public void setSeance3(String seance3) {
        this.seance3 = seance3;
    }

    public String getSeance4() {
        return seance4;
    }

    public void setSeance4(String seance4) {
        this.seance4 = seance4;
    }

    public String getSeance5() {
        return seance5;
    }

    public void setSeance5(String seance5) {
        this.seance5 = seance5;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    @Override
    public String toString() {
        return "TimeTable{" + "seance1=" + seance1 + ", seance2=" + seance2 + ", seance3=" + seance3 + ", seance4=" + seance4 + ", seance5=" + seance5 + ", id=" + id + ", idClasse=" + idClasse + ", day=" + day + ", subject=" + subject + ", idSubject=" + idSubject + '}';
    }

    


}
