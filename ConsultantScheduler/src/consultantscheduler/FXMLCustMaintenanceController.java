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
import java.sql.Statement;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amanda
 */
public class FXMLCustMaintenanceController implements Initializable {

    @FXML
    TextField txtName;
    
    @FXML
    TextField txtCustID;
    
    @FXML
    TextField txtAddress1;
    
    @FXML
    TextField txtAddress2;
    
    @FXML
    TextField txtCity;
    
    @FXML
    TextField txtCountry;
    
    @FXML
    TextField txtPhone;
    
    @FXML
    TextField txtZip;
    
    @FXML
    TextField txtSearch;
    
    @FXML
    Button btnSearch;
    
    @FXML
    Button btnSave;
    
    @FXML
    Button btnCancel;
    
    @FXML
    TableView tbvResults;
    
    @FXML
    RadioButton rbIsActiveYes;
    
    @FXML
    RadioButton rbIsActiveNo;
    
    @FXML
    ToggleGroup tgIsActive;
    
    @FXML
    Button btnDelete;
    
    @FXML
    Button btnEdit;
    
    //int customerID, addressID, isActive, countryID;
    //String custName, address1, address2, city, zip, phone, country;
    public String currentUser;
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<Customer> searchedCust = FXCollections.observableArrayList();
    private Customer currentCustomer;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadCustList();
    }  
    
    public void setUser(String user){
        
        currentUser = user;
        
    }
    
    public void setCurrentUser(String username){
        currentUser = username;
    }
    
    public void clearTextBoxes(){
        txtName.clear();
        txtCustID.clear();
        txtAddress1.clear();
        txtAddress2.clear();
        txtZip.clear();
        txtCity.clear();
        txtCountry.clear();
        txtPhone.clear();        
    }
    
     @FXML 
    public void btnSaveOnClick(){
        currentCustomer.setCustName(txtName.getText());
        Boolean isNamePresent = true;
        if(txtName.getText().equals(""))
                {isNamePresent=false;}
        currentCustomer.setAddress1(txtAddress1.getText());
        Boolean isAdd1Present = true;
         if(txtAddress1.getText().equals(""))
                {isAdd1Present=false;}
        currentCustomer.setAddress2(txtAddress2.getText());
        //address2 is optional
        currentCustomer.setCity(txtCity.getText());
        Boolean isCityPresent = true;
         if(txtCity.getText().equals(""))
                {isCityPresent=false;}
        currentCustomer.setPhone(txtPhone.getText());
        Boolean isPhonePresent = true;
        if(txtPhone.getText().equals(""))
                {isPhonePresent=false;}
        currentCustomer.setZip(txtZip.getText());
        Boolean isZipPresent = true;
        if(txtZip.getText().equals(""))
                {isZipPresent=false;}
        currentCustomer.setCountry(txtCountry.getText());
        Boolean isCountryPresent = true;
        if(currentCustomer.getCountry()==null)
                {isCountryPresent=false;}
        if(rbIsActiveNo.isSelected()==true){
            currentCustomer.setIsActive(0);
        }
        if(rbIsActiveYes.isSelected()==true){
            currentCustomer.setIsActive(1);
        }
        
        String resultMsg;
        
        if(isNamePresent==false||isAdd1Present==false||isCityPresent==false||isPhonePresent==false||isZipPresent==false||isCountryPresent==false){
        resultMsg="Please enter a value for: ";
        
        if(isNamePresent==false){
            resultMsg+="\nName";
        }        
        if(isAdd1Present==false){
            resultMsg+="\nStreet Address";
        }
        if(isCityPresent==false){
            resultMsg+="\nCity";
        }
        if(isPhonePresent==false){
            resultMsg+="\nPhone";
        }
        if(isZipPresent==false){
            resultMsg+="\nZip Code";
        }
        if(isCountryPresent==false){
            resultMsg+="\nCountry";
        }
            showErrorDialog(resultMsg);
        }
        
        
        else{
         
        try {
            String localTimeZone = TimeZone.getDefault().getID();            
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            DBConnection.createConn();
            String query = "update customer set customerName= ? , active= ? , lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy=? where customerid = ? ;";
            
            PreparedStatement statement = DBConnection.conn.prepareStatement(query);
   
            statement.setString(1, currentCustomer.getCustName());
            statement.setInt(2, currentCustomer.getIsActive());
            statement.setString(3, currentUser);
            statement.setInt(4,currentCustomer.getCustID());
            
            statement.executeUpdate();
            
            query = "update address set address= ?, address2 = ? , postalCode =? ,phone=? , lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy=? where addressid = ? ;" ;
                        
            statement = DBConnection.conn.prepareStatement(query);
            
            statement.setString(1, currentCustomer.getAddress1());
            statement.setString(2, currentCustomer.getAddress2());
            statement.setString(3,currentCustomer.getZip());
            statement.setString(4, currentCustomer.getPhone());
            statement.setString(5,currentUser);
            statement.setInt(6,currentCustomer.getAddressID());
            
            statement.executeUpdate();
            query = "update country set country = ? , lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? where countryid = ? ;";
            statement = DBConnection.conn.prepareStatement(query);
            
            statement.setString(1,currentCustomer.getCountry());
            statement.setString(2,currentUser);
            statement.setInt(3, currentCustomer.getCountryID());
            
            statement.executeUpdate();
            query = "update city set city = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? where cityid = ? ;";
            statement = DBConnection.conn.prepareStatement(query);
            statement.setString(1, currentCustomer.getCity());
            statement.setString(2, currentUser);
            statement.setInt(3, currentCustomer.getCityID());
            
            statement.executeUpdate();
            TimeZone.setDefault(TimeZone.getTimeZone(localTimeZone));
            DBConnection.closeConnection();
            resultMsg = "Customer saved.";
            clearTextBoxes();
            loadCustList();
            showConfirmationDialog(resultMsg);
            
            
            
        } catch (Exception ex) {
            resultMsg="Error: "+ex.getMessage();
            showConfirmationDialog(resultMsg);
            System.out.println("Error: "+ex.getMessage());
        }
    }
    }
    
    @FXML
    public void btnEditOnClick(){
        
        if(tbvResults.getSelectionModel().getSelectedItem()==null){
            showErrorDialog("Please select a customer.");
        }
        else{
        currentCustomer = (Customer)tbvResults.getSelectionModel().getSelectedItem();
        txtName.setText(currentCustomer.getCustName());
        txtAddress1.setText(currentCustomer.getAddress1());
        txtAddress2.setText(currentCustomer.getAddress2());
        txtCity.setText(currentCustomer.getCity());
        txtCountry.setText(currentCustomer.getCountry());
        txtZip.setText(currentCustomer.getZip());
        txtCustID.setText(String.valueOf(currentCustomer.getCustID()));
        txtPhone.setText(currentCustomer.getPhone());
        }
    }
    
    public void loadCustList(){
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
            tbvResults.setItems(customerList);
    
    }
    catch(Exception e){
        
        showErrorDialog(e.getMessage());
    
    }
    
    }
    
    @FXML
    public void btnSearchOnClick(){
        tbvResults.getItems().clear();
        customerList.clear();
        String searchString = txtSearch.getText();
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
            System.out.print(resultSet.getInt("customerid"));
            }
            tbvResults.setItems(customerList);
            DBConnection.closeConnection();
        } catch (Exception ex) {
            resultMsg="Error: "+ex.getMessage();
            showConfirmationDialog(resultMsg);
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
    public void btnDeleteOnClick()throws IOException,SQLException, Exception{
        if(tbvResults.getSelectionModel().getSelectedItem()==null){
            showErrorDialog("Please select a customer.");
        }
        else{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Deleting this customer will also delete any scheduled appointments. Delete this customer?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
        deleteCustomer((Customer)(tbvResults.getSelectionModel().getSelectedItem()));
        loadCustList();
    } else {
        alert.close();
    
    }       
        }    
        
    }
    
    public void deleteCustomer(Customer cust) throws SQLException, Exception {
            try{
                DBConnection.createConn();
            
            String query = "delete from country where countryid = ?;";            
            PreparedStatement statement = DBConnection.conn.prepareStatement(query);
            statement.setInt(1, cust.getCountryID());
            statement.executeUpdate();
            
            query = "delete from city where cityid = ?;";            
            statement = DBConnection.conn.prepareStatement(query);
            statement.setInt(1, cust.getCityID());
            statement.executeUpdate();
            
            query = "delete from address where addressid = ?;";            
            statement = DBConnection.conn.prepareStatement(query);
            statement.setInt(1, cust.getAddressID());
            statement.executeUpdate();
            
            
            query = "delete from appointment where customerid = ?";
            statement = DBConnection.conn.prepareStatement(query);
            statement.setInt(1, cust.getCustID());
            
            query = "delete from customer where customerid = ?;";            
            statement = DBConnection.conn.prepareStatement(query);
            statement.setInt(1, cust.getCustID());
            statement.executeUpdate();
            
            showConfirmationDialog("Customer/Appointments deleted successfully.");
            clearTextBoxes();
            }
            catch(Exception e){
                showErrorDialog(e.getMessage());
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
    public void btnCancelOnClick(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Cancel editing?");

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
