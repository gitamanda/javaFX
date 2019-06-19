/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultantscheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Amanda
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    Button btnLogin;
    
    @FXML
    TextField txtUserName;
    
    @FXML
    PasswordField txtPassword;
    
    @FXML
    Label lblPword;
    
    @FXML
    Label lblUname;
    
    
    Locale locale;
    
    String currentUser;
    
    
    ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException{
        
        locale = Locale.getDefault();
        Boolean isValidated = false;
        String uName = txtUserName.getText();
        String pWord = txtPassword.getText();
        if((uName.equals("")||pWord.equals(""))&&locale.getLanguage().equals("en")){
            showErrorDialog("All fields are required.");
        }
        else if((uName.equals("")||pWord.equals(""))&&locale.getLanguage().equals("fr")){
            showErrorDialog("Tous les champs sont requis.");
        }
        else{
        try {
            allAppts.clear();
            DBConnection.createConn();
            Statement statement = DBConnection.conn.createStatement();
            String query2 ="SELECT * FROM user u INNER JOIN appointment a ON u.userName =  a.contact INNER JOIN customer c ON a.customerId = c.customerid WHERE u.userName = ? AND u.password = ?"; 
            PreparedStatement ps1 = DBConnection.conn.prepareStatement(query2);
            //setString param counting starts at 1, not 0
            ps1.setString(1, uName);
            ps1.setString(2, pWord);
           // ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()==true){
                isValidated=true;
            }
            DBConnection.closeConnection();
            
            if(isValidated==true){
            currentUser= uName;
            DBConnection.createConn();
            String query3 ="SELECT * FROM user u INNER JOIN appointment a ON u.userName =  a.contact INNER JOIN customer c ON a.customerId = c.customerid WHERE u.userName = ?"; 
            PreparedStatement ps3 = DBConnection.conn.prepareStatement(query3);
            //setString param counting starts at 1, not 0
            ps3.setString(1, uName);
            ResultSet rs = ps3.executeQuery();
            
            while(rs.next()==true){
                
                Appointment appt = new Appointment();
                appt.setAptID(rs.getInt("appointmentid"));
                appt.setContact(rs.getString("contact"));
                appt.setCustID(rs.getInt("customerId"));
                appt.setDescription(rs.getString("description"));
                appt.setLocation(rs.getString("location"));
                appt.setCustName(rs.getString("customerName"));
                appt.setTitle(rs.getString("title"));
                appt.setURL(rs.getString("url"));
                Timestamp tsStart = rs.getTimestamp("start");
                Timestamp tsEnd = rs.getTimestamp("end");
                int year, month, day, startHour, endHour;
                long timestampLongStart = tsStart.getTime();
                
                String localTimeZone = TimeZone.getDefault().getID();
               // TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTimeInMillis(timestampLongStart);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(GregorianCalendar.MONTH);
                if(month+1==13){month=1;}
                else{month++;}
                day = calendar.get(Calendar.DAY_OF_MONTH);
                startHour = calendar.get(Calendar.HOUR);
                long timestampLongEnd = tsEnd.getTime();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTimeInMillis(timestampLongEnd);
                endHour = calendar2.get(Calendar.HOUR);
                
                appt.setApptDate(LocalDate.of(year, month, day));
               
                appt.setStartTime(LocalTime.of(startHour,0));
                
                appt.setEndTime(LocalTime.of(endHour, 0));
                allAppts.add(appt);
                TimeZone.setDefault(TimeZone.getTimeZone(localTimeZone));
            }
            DBConnection.closeConnection();
            allAppts.forEach((appt) -> {
                LocalTime apptTime = LocalTime.of(appt.getStartTime().getHour(), appt.getStartTime().getMinute());
                LocalDate apptDate = LocalDate.of(appt.getApptDate().getYear(), appt.getApptDate().getMonth(), appt.getApptDate().getDayOfMonth());
                LocalDate today = LocalDate.now();
                LocalTime rightNow = LocalTime.now();
                if (today.equals(apptDate)) {
                    if(rightNow.plusHours(1).getHour()==apptTime.getHour()){
                    for(int x = 0; x<16;x++){
                        if(rightNow.plusMinutes(x).getMinute()==(apptTime.getMinute())){
                            showConfirmationDialog("Appointment soon! " + appt.getCustName()+ " is scheduled at " + appt.getStartTime());
                        }
                    }
                }}
            });
         try {
            // TODO
            logActivityToFile();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Stage stage;
        stage= (Stage)btnLogin.getScene().getWindow();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLHomepage.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        FXMLHomepageController doc = fxmlLoader.getController();
        doc.setUser(currentUser);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            }
            else if(isValidated==false&&locale.getLanguage().toLowerCase().equals("en")){
            showErrorDialog("Incorrect username/password. Try again.");
        }
        else if(isValidated==false&&locale.getLanguage().toLowerCase().equals("fr")){
            showErrorDialog("Nom d'utilisateur/Mot de passe incorrect. Réessayer.");
        }  
        } catch (Exception ex) {
            String resultMsg="Error: "+ex.getMessage();
            showErrorDialog(resultMsg);
            System.out.println("Error: "+ex.getMessage());
        }   
        
        
        }
    }
    
    public void showErrorDialog(String msg){
    
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    public void showConfirmationDialog(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");      
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    public void logActivityToFile() throws FileNotFoundException, UnsupportedEncodingException{
        
        File file = new File("activityLog.txt");
        if(!file.exists()&&(!file.isDirectory())){
        PrintWriter writer = new PrintWriter("activityLog.txt", "UTF-8");
        writer.println("\nLogin by " +currentUser + " on "+ LocalDateTime.now().toString());        
        writer.close();
        }
        else{
            try {
    Files.write(Paths.get("activityLog.txt"), ("\nLogin by "+currentUser+ " on " + LocalDateTime.now().toString()).getBytes(), StandardOpenOption.APPEND);
}catch (IOException e) {
                showErrorDialog(e.getMessage());
}
        
        }
    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       locale = Locale.getDefault();
       if(locale.getLanguage().equals("fr")){
           lblUname.setText("Nom d'utilisateur");
           lblPword.setText("Mot de passe");
           btnLogin.setText("S'identifier");
       }
    
    }
}
