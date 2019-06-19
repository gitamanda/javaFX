/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultantscheduler;

import static java.lang.Class.forName;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Amanda
 */
public class DBConnection {
    
    private static final String dbName = "U04ITI";
    private static final String db_URL = "jdbc:mysql://52.206.157.109/"+dbName;
    private static final String userName = "U04ITI";
    private static final String password = "53688249370";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    
    static Connection conn;
    
    public static void createConn()throws ClassNotFoundException, SQLException, Exception{
        
        Class.forName(driver);
        conn = DriverManager.getConnection(db_URL,userName, password);
        System.out.println("connection successful");
    
    }
    
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception{}
    
}
