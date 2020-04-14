/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import java.io.File;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import rest.file.uploader.tn.FileUploader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.services.ServicesPupils;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class MaillingController implements Initializable {

    @FXML
    private Label error;
    @FXML
    private TextField pdfTxt;
    @FXML
    private Button btAdd1;
    @FXML
    private TextField sujetTxt;
    @FXML
    private Button btSend;
    @FXML
    private Button btCancel;
    private Stage stage;
    private String url_pdf;
    private Pupils pupils = new Pupils();
    private List<Pupils> pupilsList;

    int id_class;
    Classes gr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO



    }    

    @FXML
    private void btAddOnClick(ActionEvent event) throws ProtocolException, IOException {
                JFileChooser chooser = new JFileChooser();
 chooser.showOpenDialog(null);
 
 File f = chooser.getSelectedFile();
 url_pdf=f.getAbsolutePath();
 pdfTxt.setText(url_pdf);
 
        
    }

    @FXML
    private void btSendOnClick(ActionEvent event) throws IOException, ParseException, SQLException {
        
        final String username = "ichrak.harbaoui@esprit.tn";
		final String password = "07218374";
		String fromEmail = "ichrak.harbaoui@esprit.tn";
		String toEmail = "ichrakharbaoui4@gmail.com";
		
		Properties properties = new Properties();
        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		});
		//Start our mail message
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(fromEmail));
                                        for (int i = 0; i < pupilsList.size(); i++) {
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(pupilsList.get(i).getEmail()));

                }
			msg.setSubject("Time Table");
			
			Multipart emailContent = new MimeMultipart();
			
			//Text body part
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText("Time Table");
			
			//Attachment body part.
			MimeBodyPart pdfAttachment = new MimeBodyPart();
			pdfAttachment.attachFile(pdfTxt.getText());
			
			//Attach body parts
			emailContent.addBodyPart(textBodyPart);
			emailContent.addBodyPart(pdfAttachment);
			
			//Attach multipart to message
			msg.setContent(emailContent);
			
			Transport.send(msg);
			System.out.println("Sent message");
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                FXMLLoader loader = new FXMLLoader(getClass().getResource("gestionPupil.fxml"));

                Parent root = loader.load();
                GestionPupilController ugc = loader.getController();

                ugc.setGrade(gr);

                btCancel.getScene().setRoot(root);
	
        
    }

    @FXML
    private void btCancelOnClick(ActionEvent event) throws ParseException, SQLException {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Go Back");
        alert.setHeaderText(" interrupt");
        alert.setContentText("want to go back to the previous page ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("gestionPupil.fxml"));

                Parent root = loader.load();
                GestionPupilController ugc = loader.getController();

                ugc.setGrade(gr);

                btCancel.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }
        public void getStage(Stage stage) {
        this.stage = stage;
    }
        
        
            public void setGrade(Classes gr) throws ParseException, SQLException {
                    ServicesPupils ser = new ServicesPupils();

        this.gr = gr;
        id_class = gr.getId();
                pupils.setClasses(id_class);

        //    pupils.setClasses(id_class);
        // actualiserTable();
                pupilsList= new ServicesPupils().readAll(pupils);


    }
}
