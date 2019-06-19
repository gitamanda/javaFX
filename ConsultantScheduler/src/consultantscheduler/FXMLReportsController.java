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
import java.sql.SQLException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amanda
 */
public class FXMLReportsController implements Initializable {

    @FXML
    TableView tbvCustomers;
    
    @FXML
    TableView tbvSchedule;
    
    @FXML
    TableView tbvApptsByLocation;
    
    @FXML
    Button btnExitToMainMenu;
    
    @FXML
    Label lblReportName;
    
    @FXML
    Hyperlink hlCustomers;
    
    @FXML
    Hyperlink hlSchedule;
    
    @FXML
    Hyperlink hlApptsByLocation;
    
    @FXML
    Label lblSelect;
    
    @FXML
    ComboBox cbLocation;
    
    
    String currentUser;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       loadLocations();
        // TODO
        
        
        
    }
    
    public void setUser(String user){
        
        currentUser = user;
        
    }
    
    @FXML
    public void generateScheduleReport() throws SQLException, Exception{
        disableHiddenControls();
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        try{
            tbvCustomers.setVisible(false);
        String query = "SELECT * from appointment a INNER JOIN customer c ON a.customerId = c.customerid INNER JOIN address ad on c.addressid = ad.addressid ORDER BY contact DESC";        
        DBConnection.createConn();
        PreparedStatement ps = DBConnection.conn.prepareStatement(query);
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
                startHour = calendar.get(Calendar.HOUR_OF_DAY);
                
                long timestampLongEnd = tsEnd.getTime();
                Calendar calendar2 = Calendar.getInstance(TimeZone.getDefault());
                calendar2.setTimeInMillis(timestampLongEnd);
                endHour = calendar2.get(Calendar.HOUR_OF_DAY);
                
                appt.setApptDate(LocalDate.of(year, month, day));
               
                appt.setStartTime(LocalTime.of(startHour,0));
                
                appt.setEndTime(LocalTime.of(endHour, 0));
                apptList.add(appt);
            }
            DBConnection.closeConnection();
            tbvSchedule.setItems(apptList);
            tbvSchedule.setVisible(true);
         
        }
        catch(Exception ex){
            showErrorDialog(ex.getMessage());
                    
                    }
    }
    
    public void loadLocations(){
        ObservableList<String> locationList = FXCollections.observableArrayList();
        locationList.add("In-Office");
        locationList.add("Phone");
        locationList.add("WebEx");
        cbLocation.setItems(locationList);
    }
    
    @FXML
    public void generateCustomersReport(){
        disableHiddenControls();
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        
        try {
            tbvSchedule.setVisible(false);
            customerList.clear();
            DBConnection.createConn();
            String query = "select * from customer c INNER JOIN address a ON c.addressId = a.addressid";
            PreparedStatement ps = DBConnection.conn.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Customer customer = new Customer();
                customer.setCustName(rs.getString("customerName"));
                customer.setCustID(rs.getInt("customerid"));
                customer.setAddress1(rs.getString("address"));
                customer.setAddress2(rs.getString("address2"));
               // customer.setCity(rs.getString("city"));
                customer.setAddressID(rs.getInt("addressid"));
               // customer.setCountry(rs.getString("country"));
               // customer.setCountryID(rs.getInt("countryid"));
              //  customer.setCityID(rs.getInt("cityid"));
               // customer.setZip(rs.getString("postalCode"));
                customer.setPhone(rs.getString("phone"));
                if(rs.getInt("active")==1){
                    customer.setIsActive(1);
                }
                else{
                    customer.setIsActive(0);
                }
                customerList.add(customer);
            }
            tbvCustomers.setItems(customerList);
            tbvCustomers.setVisible(true);
    
    }
    catch(Exception e){
        
        showErrorDialog(e.getMessage());
    
    }
    
    }
    
    @FXML
    public void enableHiddenControls(){
        lblSelect.setVisible(true);
        cbLocation.setVisible(true);
    }
    
    public void disableHiddenControls(){
        lblSelect.setVisible(false);
        cbLocation.setVisible(false);
    }
    
    
    @FXML
    public void generateScheduleByLocation() throws SQLException, Exception{
        
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        String filter = cbLocation.getSelectionModel().getSelectedItem().toString();
        try{
            tbvCustomers.setVisible(false);
        String query = "SELECT * from appointment a INNER JOIN customer c ON a.customerId = c.customerid INNER JOIN address ad on c.addressid = ad.addressid WHERE a.location = ?";        
        DBConnection.createConn();
        PreparedStatement ps = DBConnection.conn.prepareStatement(query);
        ps.setString(1, filter);
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
                startHour = calendar.get(Calendar.HOUR_OF_DAY);
                long timestampLongEnd = tsEnd.getTime();
                Calendar calendar2 = Calendar.getInstance(TimeZone.getDefault());
                calendar2.setTimeInMillis(timestampLongEnd);
                year = calendar2.get(Calendar.YEAR);
                month = calendar2.get(Calendar.MONTH);
                if(month+1==13){month=1;}
                else{month++;}
                endHour = calendar2.get(Calendar.HOUR_OF_DAY);
                
                appt.setApptDate(LocalDate.of(year, month, day));
               
                appt.setStartTime(LocalTime.of(startHour,0));
                
                appt.setEndTime(LocalTime.of(endHour, 0));
                apptList.add(appt);
            }
            DBConnection.closeConnection();
            tbvSchedule.setItems(apptList);
            tbvSchedule.setVisible(true);
         
        }
        catch(Exception ex){
            showErrorDialog(ex.getMessage());
                    
                    }
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
    
}
