/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultantscheduler;

import com.sun.javafx.collections.ElementObservableListDecorator;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amanda
 */
public class FXMLApptMaintenanceController implements Initializable {

    
    @FXML
    TextField txtApptTitle;
    
    @FXML
    TextField txtCustName;
    
    @FXML
    DatePicker dpApptDate;
    
    @FXML
    ComboBox cbApptTime;    
    
    @FXML
    TextField txtContact;
    
    @FXML
    TextField txtUrl;
    
    @FXML
    ComboBox cbLocation;
    
    @FXML
    TextArea taDescription;
    
    @FXML
    Button btnCancel;
    
    @FXML
    Button btnSave;
    
    @FXML
    Label lblCurrDate;
    
    @FXML
    Label lblCurrTime;
    
    String currentUser;

    
    Appointment currentAppt;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       /* try{
        String query = "SELECT * from appointment WHERE appointmentid = ?";        
        DBConnection.createConn();
        PreparedStatement ps = DBConnection.conn.prepareStatement(query);
        ps.setInt(1,currentAppt.getAptID());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            //TODO - code RS next
            }
        }
        
        catch(Exception e){
                    
                     }
*/
    }
    
    public void setUser(String user){
        
        currentUser = user;
        
    }
    
    @FXML
    public LocalDate getDate(){
    
        LocalDate ld = dpApptDate.getValue();
        return ld;
    }
    
    public void loadTimeBoxes(){
        LocalDate ld = getDate();
        ObservableList<Timestamp> timeList = FXCollections.observableArrayList();
        
        ObservableList<LocalTime> timeListEnd = FXCollections.observableArrayList();
        
        ObservableList<String> allApptTimes = FXCollections.observableArrayList();
        
        
        String localTimeZone = TimeZone.getDefault().getID();

        
        LocalDateTime sT1 = LocalDateTime.of(ld, LocalTime.of(16, 0));
        LocalDateTime sT2 = LocalDateTime.of(ld, LocalTime.of(17, 0));
        LocalDateTime sT3 = LocalDateTime.of(ld, LocalTime.of(18, 0));
        LocalDateTime sT4 = LocalDateTime.of(ld, LocalTime.of(20, 0));
        LocalDateTime sT5 = LocalDateTime.of(ld, LocalTime.of(21, 0));
        LocalDateTime sT6 = LocalDateTime.of(ld, LocalTime.of(22, 0));
        LocalDateTime sT7 = LocalDateTime.of(ld, LocalTime.of(23, 0));
       
        
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        
        Timestamp ts = Timestamp.valueOf(sT1);
        
        int year, month, day, startHour, endHour;
        long timestampLongStart = ts.getTime();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(timestampLongStart);
        startHour = calendar.get(Calendar.HOUR);
        
        timeList.add(Timestamp.valueOf(sT1));
        
        //by the time it gets here, value is correct
        timeList.add(Timestamp.valueOf(sT2));       
        timeList.add(Timestamp.valueOf(sT3));       
        timeList.add(Timestamp.valueOf(sT4));        
        timeList.add(Timestamp.valueOf(sT5));        
        timeList.add(Timestamp.valueOf(sT6));        
        timeList.add(Timestamp.valueOf(sT7));
       /* timeList.add(sT3); 
        timeList.add(sT4); 
        timeList.add(sT5); 
        timeList.add(sT6); 
        timeList.add(sT7); */
        
        TimeZone.setDefault(TimeZone.getTimeZone(localTimeZone));
        
        
        
        cbApptTime.setItems(timeList);
    }
    
   
      public void btnSaveOnClick(){
        int year, month, apptDate, startHr = 0, endHr = 0, min=0;
        
        switch (cbApptTime.getSelectionModel().getSelectedIndex()){
            case 0: startHr = 9; endHr = 10;
            break;
            case 1: startHr = 10; endHr = 11;
            break;
            case 2: startHr = 11; endHr = 12;
            break;
            case 3: startHr = 13; endHr = 14;
            break;
            case 4: startHr = 14; endHr = 15;
            break;
            case 5: startHr = 15; endHr = 16;
            break;
            case 6: startHr = 16; endHr = 17;
            break;
            default: 
                break;
        }
        
        
        //currentCustomer.setCustName(txtCustName.getText());
        Boolean isNamePresent = true;
        if(txtCustName.getText().equals(""))
                {isNamePresent=false;}
        
        
        currentAppt.setTitle(txtApptTitle.getText());        
        Boolean isApptTitlePresent = true;
         if(txtApptTitle.getText().equals(""))
                {isApptTitlePresent=false;}        
        
        //currentAppt.setApptDate((Date)dpApptDate.getValue());
        Boolean isDatePresent = true;
        if(dpApptDate.getValue()==null){
            isDatePresent = false;
        }
        Boolean isStartTimePresent = true;
         if(cbApptTime.getValue()==null)
                {isStartTimePresent=false;}
         
        
         
        currentAppt.setContact(txtContact.getText());
        Boolean isContactPresent = true;
        if(txtContact.getText().equals("")){
            isContactPresent = false;
        }
        
        currentAppt.setLocation(cbLocation.getSelectionModel().getSelectedItem().toString());
        Boolean isLocationPresent = true;
         if(cbLocation.getValue()==null)
                {isLocationPresent=false;}
         
        currentAppt.setURL(txtUrl.getText());
        Boolean isURLPresent = true;
         if(txtUrl.getText().equals(""))
                {isURLPresent=false;}
         
        currentAppt.setDescription(taDescription.getText());
        Boolean isDescriptionPresent = true;
         if(taDescription.getText().equals(""))
                {isDescriptionPresent=false;}
                
       
        
        String resultMsg;
        
        if(isNamePresent==false||isApptTitlePresent==false||isContactPresent==false||isLocationPresent==false||isURLPresent==false||isDescriptionPresent==false||isStartTimePresent==false||isDatePresent==false){
        resultMsg="Please enter a value for: ";
        
        if(isNamePresent==false){
            resultMsg+="\nName";
        }        
        if(isApptTitlePresent==false){
            resultMsg+="\nAppointment Title";
        }
        if(isContactPresent==false){
            resultMsg+="\nContact";
        }
        if(isLocationPresent==false){
            resultMsg+="\nLocation";
        }
        if(isURLPresent==false){
            resultMsg+="\nURL";
        }
        if(isDescriptionPresent==false){
            resultMsg+="\nDescription";
        }
        if(isDatePresent == false){
            resultMsg="\nDate";
        }
        if(isStartTimePresent == false){
            resultMsg+="\nAppointment Time";
        }
            showErrorDialog(resultMsg);
        }
        
        
        else{
         
        try {
            String localTimeZone = TimeZone.getDefault().getID();
            apptDate = dpApptDate.getValue().getDayOfMonth();
            year = dpApptDate.getValue().getYear();
            month = dpApptDate.getValue().getMonthValue();
            LocalDateTime startTime = LocalDateTime.of(year,month,apptDate,startHr,min);
            //ZonedDateTime zdt = ZonedDateTime.of(startTime, ZoneId.of(TimeZone.getDefault().getID()));
            LocalDateTime endTime = LocalDateTime.of(year,month,apptDate,endHr,min);
            
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            DBConnection.createConn();
            String query = "update appointment set title = ?, description = ?, location = ?, contact = ?, url = ?, start = ?, end = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? WHERE appointmentid = ?";
            System.out.println("TimeZone: " + TimeZone.getDefault().getDisplayName());
            PreparedStatement statement = DBConnection.conn.prepareStatement(query);
            
              
            
            String checkForOverlap = "SELECT * FROM appointment WHERE start = ? OR end = ?";
            Boolean isDuplicateApptTime = false;
            PreparedStatement ps = DBConnection.conn.prepareStatement(checkForOverlap);
            
            TimeZone.setDefault(TimeZone.getTimeZone(localTimeZone));
            ps.setTimestamp(1,Timestamp.valueOf(startTime));
            //need to convert these to UTC
            ps.setTimestamp(2,Timestamp.valueOf(endTime));
            //System.out.println("Time: " + endTime.atZone(ZoneId.of("UTC")));
           System.out.println("query: " + ps);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                isDuplicateApptTime = true;
            }
            if(isDuplicateApptTime){
                showErrorDialog("This appointment time is unavailable. Please choose another date/time.");
            }
            else{
   
            statement.setString(1, currentAppt.getTitle());
            statement.setString(2, currentAppt.getDescription());
            statement.setString(3, currentAppt.getLocation());
            statement.setString(4, currentAppt.getContact());            
            statement.setString(5,currentAppt.getURL());           
            statement.setTimestamp(6, Timestamp.valueOf(startTime));
            statement.setTimestamp(7, Timestamp.valueOf(endTime));
            statement.setString(8,currentUser);
            statement.setInt(9,currentAppt.getAptID());
            System.out.println(statement.toString());

            
            statement.executeUpdate();
            
            DBConnection.closeConnection();
            resultMsg = "Appointment saved.";
            
            showConfirmationDialog(resultMsg);  
            
            returnToMainScreen();
            }
            
        } catch (Exception ex) {
            resultMsg="Error: "+ex.getMessage();
            showConfirmationDialog(resultMsg);
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
    }
   
    
        
    public void clearTextBoxes(){
        txtApptTitle.clear();
        txtContact.clear();
        txtCustName.clear();
        txtCustName.clear();
        taDescription.clear();
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
    public void btnCancelOnClick(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Cancel editing?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
    // ... user chose OK
    returnToCalendarPage();
    
    
    } else {
        alert.close();
    
    }
    
    }
    
    @FXML
    public void setAppointmentToEdit(Appointment _appointment){
        currentAppt=_appointment;
        
        txtCustName.setText(currentAppt.getCustName());
        txtApptTitle.setText(currentAppt.getTitle());
        
        String startDetail, endDetail;
        
        
        /*if(currentAppt.getStartTime().getHour()==9||currentAppt.getStartTime().getHour()==10||currentAppt.getStartTime().getHour()==11){
            startDetail = "AM";
        }
        else{
            startDetail = "PM";
        }
        if (currentAppt.getEndTime().getHour()==10||currentAppt.getEndTime().getHour()==11){
            endDetail = "AM";
        }
        else{
            endDetail = "PM";
        }
*/
        
        
        lblCurrTime.setText("Current appointment time: " + currentAppt.getStartTime().toString() + " - " + currentAppt.getEndTime().toString() + " ");
        txtContact.setText(currentAppt.getContact());
        txtUrl.setText(currentAppt.getURL());
        cbLocation.setValue(currentAppt.getLocation());
        taDescription.setText(currentAppt.getDescription());
        dpApptDate.setValue(currentAppt.getApptDate());
        lblCurrDate.setText("Current appointment date: " + currentAppt.getApptDate().toString());
    }
    
    public void returnToMainScreen() throws IOException{
      
        Stage stage;
        stage= (Stage)btnSave.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLHomepage.fxml"));
        
        Parent root = (Parent)fxmlLoader.load();
        FXMLHomepageController doc = fxmlLoader.getController();
        doc.setCurrentUser(currentUser);
        //FXMLDocumentController doc = fxmlLoader.getController();
        //doc.setPartsList(partsList);
        //doc.setProductList(productsList);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
       public void returnToCalendarPage() throws IOException{
      
        Stage stage;
        stage= (Stage)btnSave.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLViewCalendar.fxml"));
        
        Parent root = (Parent)fxmlLoader.load();
        
        FXMLViewCalendarController doc = fxmlLoader.getController();
        doc.setCurrentUser(currentUser);
        //FXMLDocumentController doc = fxmlLoader.getController();
        //doc.setPartsList(partsList);
        //doc.setProductList(productsList);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
