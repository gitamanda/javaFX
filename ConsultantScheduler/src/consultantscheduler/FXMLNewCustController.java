/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultantscheduler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import static javafx.application.Application.launch;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amanda
 */
public class FXMLNewCustController implements Initializable {

    @FXML
    TextField txtFName;
    
    @FXML
    TextField txtLName;
    
    //String name = fName + " " + lName;
    
    @FXML
    TextField txtAddress1;
    
    @FXML
    TextField txtAddress2;
    
    @FXML
    TextField txtCity;
    
    @FXML
    TextField txtPhone;
    
    @FXML
    TextField txtZip;
    
    @FXML
    TextField txtCountry;
    
    @FXML
    Button btnSave;
    
    @FXML
    Button btnCancel;
    
    String currentUser;
   
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }
    
    public void setUser(String user){
        
        currentUser = user;
        
    }
    @FXML 
    public void btnSaveOnClick(){
        String fName = txtFName.getText();
        Boolean isFNamePresent = true;
        if(fName.equals(""))
                {isFNamePresent=false;}
        String lName = txtLName.getText();
        Boolean isLNamePresent = true;
         if(lName.equals(""))
                {isLNamePresent=false;}
        String fullName = fName + " "+ lName;
        String address1 = txtAddress1.getText();
        Boolean isAdd1Present = true;
         if(address1.equals(""))
                {isAdd1Present=false;}
        String address2 = txtAddress2.getText();
        //address2 is optional
        String city = txtCity.getText();
        Boolean isCityPresent = true;
         if(city.equals(""))
                {isCityPresent=false;}
        String phone = txtPhone.getText();
        Boolean isPhonePresent = true;
        if(phone.equals(""))
                {isPhonePresent=false;}
        String zip = txtZip.getText();
        Boolean isZipPresent = true;
        if(zip.equals(""))
                {isZipPresent=false;}
        String country = txtCountry.getText();
        Boolean isCountryPresent = true;
        if(country.equals(""))
                {isCountryPresent=false;}
        
        String resultMsg;
        
        if(isFNamePresent==false||isLNamePresent==false||isAdd1Present==false||isCityPresent==false||isPhonePresent==false||isZipPresent==false||isCountryPresent==false){
        resultMsg="Please enter a value for: ";
        
        if(isFNamePresent==false){
            resultMsg+="\nFirst Name";
        }
        if(isLNamePresent==false){
            resultMsg+="\nLast Name";
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
            String localTimeZone = TimeZone.getDefault().getID();
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        String query1 = "INSERT INTO country \n" +
"VALUES (NULL,'"+country+"', CURRENT_TIMESTAMP, 'Admin', CURRENT_TIMESTAMP, 'Admin');\n" ;
        String query2 = "INSERT INTO city \n" +
"VALUES(NULL, '"+city+"', (select MAX(LAST_INSERT_ID()) from country), CURRENT_TIMESTAMP, 'Admin', CURRENT_TIMESTAMP, 'Admin');\n" ;
        String query3 = "INSERT INTO address \n" +
"VALUES(NULL,'"+address1+"','"+address2+"', (select MAX(LAST_INSERT_ID()) from city), '"+zip+"', '"+phone+"', now(), 'Admin', CURRENT_TIMESTAMP, 'Admin');\n" ;
        String query4 = "INSERT INTO customer \n" +
"VALUES (NULL,'"+fullName+"', (select MAX(LAST_INSERT_ID()) from address), 1, CURRENT_TIMESTAMP, 'Admin', CURRENT_TIMESTAMP, 'Admin')";
        TimeZone.setDefault(TimeZone.getTimeZone(localTimeZone));
        try {
            DBConnection.createConn();
            Statement statement = DBConnection.conn.createStatement();
            statement.executeUpdate(query1);//System.out.println(resultSet.toString());
            statement.executeUpdate(query2);
            statement.executeUpdate(query3);
            statement.executeUpdate(query4);
            DBConnection.closeConnection();
            resultMsg = "Customer saved.";
            showConfirmationDialog(resultMsg);
            returnToMainScreen();
        } catch (Exception ex) {
            resultMsg="Error: "+ex.getMessage();
            showConfirmationDialog(resultMsg);
            System.out.println("Error: "+ex.getMessage());
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
