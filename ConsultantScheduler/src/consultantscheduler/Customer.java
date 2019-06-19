/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultantscheduler;

import java.util.Date;

/**
 *
 * @author Amanda
 */
public class Customer {
    private int custID;
    private String custName;
    private int addressID;
    private String address1;
    private String address2;
    private String city;
    private int countryID;
    private int cityID;
    private String zip;
    private String country;
    private String phone;
    private int isActive;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdateBy;
    
    
    public void setAddress1(String _address1){
        address1 = _address1;
    }
    
    public void setAddress2(String _address2){
        address2 = _address2;
    }
    
    public void setCity(String _city){
        city = _city;
    }
    
    public void setCountry(String _country){
        country = _country;
    }
    
    public void setPhone(String _phone){
        phone = _phone;
    }
    
    public void setCustID(int _custID){
        custID = _custID;        
    }
    
    public void setZip(String _zip){
        zip = _zip;
    }
    
    public String getZip(){
        return zip;
    }
    
    public String getAddress1(){
        return address1;
    }
    
    public String getAddress2(){
        return address2;
    }
    
    public String getCity(){
        return city;
    }
    
    public String getCountry(){
        return country;
    }
    
    public String getPhone(){
        return phone;
    }
    public int getCustID(){
        return custID;
    }
    
    public int getCityID(){
        return cityID;
    }
    
    public int getCountryID(){
        return countryID;
    }
    
    public String getCustName(){
        return custName;
    }
    
    public int getAddressID(){
        return addressID;
    }
    
    public int getIsActive(){
        return isActive;
    }
    
    public Date getCreateDate(){
        return createDate;
    }
    
    public String getCreatedBy(){
        return createdBy;
    }
   
    public Date getLastUpdateDate(){
        return lastUpdate;
    }
    
    public String getLastUpdateBy(){
        return lastUpdateBy;
    }
    
    public void setCustName(String _custName){
        custName = _custName;
    }
    
    public void setAddressID(int _addressID){
        addressID = _addressID;
    }
    
    public void setIsActive(int _isActive){
        isActive = _isActive;
    }
    
    public void setCountryID(int _countryID){
        countryID = _countryID;
    }
    
    public void setCityID(int _cityID){
        cityID = _cityID;
    }
    
    public void setLastUpdateDate(Date _updateDate){
        lastUpdate = _updateDate;
    }
    
    public void setLastUpdatedBy(String _lastUpdatedBy){
        lastUpdateBy = _lastUpdatedBy;
    }
    
    public void setCreateDate(Date _createDate){
        createDate = _createDate;
    }
    
    public void setCreatedBy(String _createdBy){
        createdBy = _createdBy;
    }
}
