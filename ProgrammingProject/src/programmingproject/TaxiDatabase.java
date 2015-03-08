package programmingproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cal
 */
public class TaxiDatabase
{
    Connection con;
    PreparedStatement statement;
    ResultSet result;
    boolean connected;
    String IP = "192.168.1.113";
    
    public TaxiDatabase()
    {
        
    }
    
    public void connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://" + IP + ":3306/taxiproject", "taxi", "whatthehellarewesupposedtodowithallthisdata");
            if (!con.isClosed())
            {
                System.out.println("Successfully connected to the MySQL Database...");
                statement = con.prepareStatement("");
                connected = true;
            }
        } catch (Exception ex)
        {
            System.out.println("ERROR: Could not connect to database server at: " + IP);
            ex.printStackTrace();
            connected = false;
        }
    }
}
