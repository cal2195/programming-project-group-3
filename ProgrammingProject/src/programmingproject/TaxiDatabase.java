package programmingproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    String IP = "cal2195.no-ip.biz";

    public TaxiDatabase()
    {

    }

    public void connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://" + IP + ":995/taxiproject", "taxi", "whatthehellarewesupposedtodowithallthisdata");
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

    public void grabTestData()
    {
        try
        {
            System.out.println("Grabbing test data...");
            statement = (PreparedStatement) con.prepareStatement("SELECT * FROM taxi_data WHERE medallion = 20 LIMIT 0,500");
            result = statement.executeQuery();
            while (result.next())
            {
                System.out.println(result);
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(TaxiDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
