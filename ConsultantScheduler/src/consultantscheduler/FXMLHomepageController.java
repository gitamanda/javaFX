/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultantscheduler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amanda
 */


public class FXMLHomepageController implements Initializable {

    @FXML
    Hyperlink hlNewCust;
    
    @FXML
    Hyperlink hlMaintainCust;
    
    @FXML
    Hyperlink hlNewAppt;
    
    @FXML
    Hyperlink hlReportsMenu;
    
    @FXML
    Hyperlink hlViewCalendar;
    
    @FXML
    Button btnExit;
    
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
    private void btnExitOnClick(){
      System.exit(0);
    }
    
    @FXML
    private void goToNewCust()throws IOException{
    
        String pageName = "FXMLNewCust.fxml";
        Stage stage;
        stage= (Stage)hlNewCust.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pageName));        
        Parent root = (Parent)fxmlLoader.load();
        FXMLNewCustController doc = fxmlLoader.getController();
        doc.setUser(currentUser);
        System.out.println(currentUser);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void goToMaintainCust()throws IOException{
    
        String pageName = "FXMLCustMaintenance.fxml";
        Stage stage;
        stage= (Stage)hlNewCust.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pageName));        
        Parent root = (Parent)fxmlLoader.load();
        FXMLCustMaintenanceController doc = fxmlLoader.getController();
        doc.setUser(currentUser);        
        System.out.println(currentUser);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void goToNewAppt()throws IOException{
    
        String pageName = "FXMLNewAppt.fxml";
        Stage stage;
        stage= (Stage)hlNewCust.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pageName));        
        Parent root = (Parent)fxmlLoader.load();
        FXMLNewApptController doc = fxmlLoader.getController();
        doc.setUser(currentUser);        
        System.out.println(currentUser);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
    @FXML
    private void goToReports()throws IOException{
    
        String pageName = "FXMLReports.fxml";
        Stage stage;
        stage= (Stage)hlNewCust.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pageName));        
        Parent root = (Parent)fxmlLoader.load();
        FXMLReportsController doc = fxmlLoader.getController();
        doc.setUser(currentUser);        
        System.out.println(currentUser);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void goToCalendar()throws IOException{
    
        String pageName = "FXMLViewCalendar.fxml";
        Stage stage;
        stage= (Stage)hlNewCust.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pageName));        
        Parent root = (Parent)fxmlLoader.load();
        FXMLViewCalendarController doc = fxmlLoader.getController();
        doc.setCurrentUser(currentUser);
        System.out.println(currentUser);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
   
    
    public void setCurrentUser(String username){
        currentUser = username;
    }
    
}
