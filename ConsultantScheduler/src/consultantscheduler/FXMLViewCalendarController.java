/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultantscheduler;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amanda
 */
public class FXMLViewCalendarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    TableView tbvAppointments;
    
    @FXML
    Button btnEditAppt;
    
    @FXML
    Button btnExitToMainMenu;
    
    @FXML
    Button btnDeleteAppt;
    
    @FXML
    Label lblConsultantName;
    
    @FXML
    RadioButton rbWeekly;
    
    @FXML
    RadioButton rbMonthly;
    
    //Appointment currentAppt;
    Customer currentCust;
    String currentUser ;
    ObservableList<Appointment> apptList = FXCollections.observableArrayList();
    ObservableList<Customer> customerList = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
       
        onRadioSelectChange();
        
    }
    
    public void setCurrentUser(String user){
        currentUser = user;        
        lblConsultantName.setText("Consultant: " + currentUser);
        
         apptList.clear();
        try{
            
        String query = "SELECT * from appointment a INNER JOIN customer c ON a.customerId = c.customerid INNER JOIN address ad on c.addressid = ad.addressid WHERE a.contact = ?";        
        DBConnection.createConn();
        PreparedStatement ps = DBConnection.conn.prepareStatement(query);
        ps.setString(1, currentUser);
        System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Appointment appt = new Appointment();
                appt.setAptID(rs.getInt("appointmentid"));
                appt.setContact(rs.getString("contact"));
                appt.setCustID(rs.getInt("customerId"));
                appt.setDescription(rs.getString("description"));
                appt.setLocation(rs.getString("location"));
                appt.setCustName(rs.getString("customerName"));
                appt.setPhone(rs.getString("phone"));
                appt.setTitle(rs.getString("title"));
                appt.setURL(rs.getString("url"));
                Timestamp tsStart = rs.getTimestamp("start");
                Timestamp tsEnd = rs.getTimestamp("end");
                int year, month, day, startHour, endHour;
                long timestampLongStart = tsStart.getTime();
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTimeInMillis(timestampLongStart);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
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
                apptList.add(appt);
            }
        }
        
        catch(Exception e){
                    showErrorDialog(e.getMessage());
                     }
        tbvAppointments.setItems(apptList);
        onRadioSelectChange();
    }
    
    @FXML
    public void onRadioSelectChange(){    
        ObservableList<Appointment> weeklyApptList = FXCollections.observableArrayList();
        ObservableList<Appointment> monthlyApptList = FXCollections.observableArrayList();
 
        LocalDate today = LocalDate.now();
        if(rbWeekly.isSelected()){
            //Here I am using Lambda expressions as a way to iterate through the appointments list. It simplifies the iteration and keeps my code clean. 
            apptList.forEach((a) -> {
                LocalDate apptDate = a.getApptDate();
                LocalDate weekFromNow = today.plusDays(7);
                if ((apptDate.isBefore(weekFromNow))&&(today.isBefore(apptDate)||today.equals(apptDate))) {
                    weeklyApptList.add(a);
                }
            });      
        tbvAppointments.setItems(weeklyApptList);  
        }
        else{
            //Here again I am using a Lambda expression for the iteration through the list. It simplifies things and keeps my code readable.
            apptList.stream().filter((a) -> (today.getMonth()==a.getApptDate().getMonth())).forEachOrdered((a) -> {
                monthlyApptList.add(a);
            });      
        tbvAppointments.setItems(monthlyApptList); 
        }
    }
    
     @FXML
    public void btnDeleteAppt(){
        if(tbvAppointments.getSelectionModel().getSelectedItem()==null){
            showErrorDialog("Please select an appointment.");
        }
        else{
        try{
            
            DBConnection.createConn();
            String query = "DELETE from appointment WHERE appointmentid = ?";
            PreparedStatement statement = DBConnection.conn.prepareStatement(query);           
            statement.setInt(1, ((Appointment)tbvAppointments.getSelectionModel().getSelectedItem()).getAptID());
            statement.executeUpdate();            
            DBConnection.closeConnection();
            String resultMsg = "Appointment deleted successfully";
            showConfirmationDialog(resultMsg);
            returnToMainScreen();
        
        }
        catch(Exception ex){
            showErrorDialog(ex.getMessage());
        }
        }
        
    
    }
    
    public void showConfirmationDialog(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");      
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    public void showErrorDialog(String msg){
    
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    @FXML
    public void returnToMainScreen() throws IOException{
      
        Stage stage;
        stage= (Stage)btnExitToMainMenu.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLHomepage.fxml"));
        
        Parent root = (Parent)fxmlLoader.load();
        FXMLHomepageController doc = fxmlLoader.getController();
        doc.setCurrentUser(currentUser);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    public void loadAllAppointments(){
    
    }
    
    @FXML
    public void btnEditApptOnClick() throws IOException{
        if(tbvAppointments.getSelectionModel().getSelectedItem()==null){
            showErrorDialog("Please select an appointment.");
        }
        else{
        Stage stage;
        stage= (Stage)btnEditAppt.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLApptMaintenance.fxml"));        
        Parent root = (Parent)fxmlLoader.load();
        FXMLApptMaintenanceController doc = fxmlLoader.getController();
        doc.setAppointmentToEdit((Appointment)tbvAppointments.getSelectionModel().getSelectedItem());
        doc.setUser(currentUser);
        doc.loadTimeBoxes();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }
    
    
}

}
