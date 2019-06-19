/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultantscheduler;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author Amanda
 */
public class Appointment {
    
    private int aptID;
    private int custID;
    private String custName;
    private String phone;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String url;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdate;
    private String createdBy;
    private String lastUpdateBy;
    private LocalDate apptDate;
 
    public void setAptID(int _aptID){
        aptID = _aptID;
    }
    
    public void setApptDate(LocalDate _date){
        apptDate = _date;
        
    }
    
    public void setCustID(int _custID){
        custID = _custID;
    }
    
    public void setCustName(String _custName){
        custName = _custName;
    }
    
    public void setPhone(String _phone){
        phone = _phone;
    }
    
    public void setTitle(String _title){
        title = _title;
    }
    
    public void setDescription(String _desc){
        description = _desc;
    }
    
    public void setLocation(String _location){
        location = _location;
    }
    
    public void setContact(String _contact){
        contact = _contact;
    }
    
    public void setURL(String _url){
        url = _url;
    }    
    
    public void setLastUpdateDate(LocalDateTime _updateDate){
        lastUpdate = _updateDate;
    }
    
    public void setLastUpdatedBy(String _lastUpdatedBy){
        lastUpdateBy = _lastUpdatedBy;
    }
    
    public void setCreateDate(LocalDateTime _createDate){
        createDate = _createDate;
    }
    
    public void setCreatedBy(String _createdBy){
        createdBy = _createdBy;
    }
    
    public void setStartTime(LocalTime _startTime){
        startTime = _startTime;
    }
    
    public void setEndTime(LocalTime _endTime){
        endTime = _endTime;
    }
    
       
    public int getAptID(){
        return aptID;
    }
    
    public int getCustID(){
        return custID;
    }
    
    public String getCustName(){
        return custName;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public LocalDate getApptDate(){
        return apptDate;
    }
    
    public String getTitle(){
        return title;
    }
    
    public String getDescription(){
        return description;
    }
    
    public String getLocation(){
        return location;
    }
    
    public String getContact(){
        return contact;
    }
    
    public String getURL(){
        return url;
    }
        
    public LocalTime getStartTime(){
        return startTime;
    }
    
    public LocalTime getEndTime(){
        return endTime;
    }
       
    public LocalDateTime getCreateDate(){
        return createDate;
    }
    
    public String getCreatedBy(){
        return createdBy;
    }
   
    public LocalDateTime getLastUpdateDate(){
        return lastUpdate;
    }
    
    public String getLastUpdateBy(){
        return lastUpdateBy;
    }
    
}
