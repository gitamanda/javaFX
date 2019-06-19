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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amanda
 */
public class FXMLNewApptController implements Initializable {

    @FXML
    TableView tbvCustomers;
    
    @FXML
    TextField txtApptTitle;
    
    @FXML
    TextField txtSearchText;
    
    @FXML
    TextField txtCustName;
    
    @FXML
    Button btnSearch;
    
    @FXML
    DatePicker dpApptDate;
    
    @FXML
    ComboBox cbStartTime;

    
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
    Button btnSelectCustomer;
    
    @FXML
    Button btnSave;
    
    String currentUser;
    
    Customer currentCustomer;
    
    Appointment currentAppt;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        loadCustList();
        loadLocations();
    }
    
    @FXML
    public void btnSearchOnClick(){
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        tbvCustomers.getItems().clear();
        customerList.clear();
        String searchString = txtSearchText.getText();
        String resultMsg;
        try {
            DBConnection.createConn();
            String searchMatchQuery = "select * from customer c INNER JOIN address a ON c.addressId = a.addressid INNER JOIN city ci ON ci.cityid = a.cityId LEFT OUTER JOIN country co ON ci.countryid = co.countryId WHERE customerName LIKE ? OR phone like ?;";
            PreparedStatement ps = DBConnection.conn.prepareStatement(searchMatchQuery);
            ps.setString(1, "%" + searchString + "%");
            ps.setString(2, "%" + searchString + "%");
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()==true){
                Customer customer = new Customer();
                customer.setCustName(resultSet.getString("customerName"));
                customer.setCustID(resultSet.getInt("customerid"));
                customer.setAddress1(resultSet.getString("address"));
                customer.setAddress2(resultSet.getString("address2"));
                customer.setCity(resultSet.getString("city"));
                customer.setAddressID(resultSet.getInt("addressid"));
                customer.setCountry(resultSet.getString("country"));
                customer.setCountryID(resultSet.getInt("countryid"));
                customer.setCityID(resultSet.getInt("cityid"));
                customer.setZip(resultSet.getString("postalCode"));
                customer.setPhone(resultSet.getString("phone"));
                if(resultSet.getInt("active")==1){
                    customer.setIsActive(1);
                }
                else{
                    customer.setIsActive(0);
                }
                customerList.add(customer);
            }
            tbvCustomers.setItems(customerList);
            DBConnection.closeConnection();
        } catch (Exception ex) {
            resultMsg="Error: "+ex.getMessage();
            showConfirmationDialog(resultMsg);
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
    @FXML
    public void setCustNameTxtFld(){
        if(tbvCustomers.getSelectionModel().getSelectedItem()==null){
            showErrorDialog("Please select a customer.");
        }
        else{
        currentCustomer = (Customer)tbvCustomers.getSelectionModel().getSelectedItem();
        txtCustName.setText(currentCustomer.getCustName());
        txtCustName.setDisable(false);
        txtApptTitle.setDisable(false);
        txtContact.setDisable(false);
        txtUrl.setDisable(false);
        cbStartTime.setDisable(false);
        dpApptDate.setDisable(false);
        cbLocation.setDisable(false);
        taDescription.setDisable(false);
        btnCancel.setDisable(false);
        btnSave.setDisable(false);
        }
    }
    
        @FXML
    public LocalDate getDate(){
    
        LocalDate ld = dpApptDate.getValue();
        return ld;
    }
    
    public void loadTimeBoxes(){
        LocalDate ld = getDate();
        if(ld!=null){
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
       /* timeList.add(sT3); 
        timeList.add(sT4); 
        timeList.add(sT5); 
        timeList.add(sT6); 
        timeList.add(sT7); */
        
        TimeZone.setDefault(TimeZone.getTimeZone(localTimeZone));
        
        
        
        cbStartTime.setItems(timeList);}
        else{
            showErrorDialog("Please select a date first.");
        }
    }
        
    
    
    public void loadLocations(){
        ObservableList<String> locationList = FXCollections.observableArrayList();
        locationList.add("In-Office");
        locationList.add("Phone");
        locationList.add("WebEx");
        cbLocation.setItems(locationList);
    }
    
    public void loadCustList(){
        
    ObservableList<Customer> customerList = FXCollections.observableArrayList();
    try {
            customerList.clear();
            DBConnection.createConn();
            String query = "select * from customer c INNER JOIN address a ON c.addressId = a.addressid INNER JOIN city ci ON ci.cityid = a.cityId LEFT OUTER JOIN country co ON ci.countryid = co.countryId";
            PreparedStatement ps = DBConnection.conn.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Customer customer = new Customer();
                customer.setCustName(rs.getString("customerName"));
                customer.setCustID(rs.getInt("customerid"));
                customer.setAddress1(rs.getString("address"));
                customer.setAddress2(rs.getString("address2"));
                customer.setCity(rs.getString("city"));
                customer.setAddressID(rs.getInt("addressid"));
                customer.setCountry(rs.getString("country"));
                customer.setCountryID(rs.getInt("countryid"));
                customer.setCityID(rs.getInt("cityid"));
                customer.setZip(rs.getString("postalCode"));
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
    
    }
    catch(Exception e){
        
        showErrorDialog(e.getMessage());
    
    }
    
    }
    public void setUser(String user){
        
        currentUser = user;
        
    }
    
    public void btnSaveOnClick(){
        currentAppt = new Appointment();
        int year, month, apptDate, startHr = 0, endHr = 0, min=0;
        
        switch (cbStartTime.getSelectionModel().getSelectedIndex()){
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
        
        currentAppt.setCustID(currentCustomer.getCustID());
        
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
         if(cbStartTime.getValue()==null)
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
            String query = "INSERT INTO appointment VALUES(NULL, ?, ?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,?)";
            
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
   
            statement.setInt(1, currentAppt.getCustID());
            statement.setString(2, currentAppt.getTitle());
            statement.setString(3, currentAppt.getDescription());
            statement.setString(4, currentAppt.getLocation());
            statement.setString(5, currentAppt.getContact());            
            statement.setString(6,currentAppt.getURL());
            
                     
            
            statement.setTimestamp(7, Timestamp.valueOf(startTime));
            statement.setTimestamp(8, Timestamp.valueOf(endTime));
            statement.setString(9,currentUser);
            statement.setString(10,currentUser);
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
        alert.setHeaderText("Cancel?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
    // ... user chose OK
    returnToMainScreen();
    
    
    } else {
        alert.close();
    
    }
    
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
    
}
