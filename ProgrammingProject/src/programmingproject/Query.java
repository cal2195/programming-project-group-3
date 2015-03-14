package programmingproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cal
 */
public class Query
{
    TaxiDatabase taxiDatabase;
    Data data;

    public Query()
    {
        taxiDatabase = new TaxiDatabase();
        taxiDatabase.connect();
    }

    public ArrayList<Trip> getTripsForTaxi(String medallion)
    {
        return getTrips("SELECT * FROM taxi_data WHERE medallion = " + medallion + " LIMIT 0,1000");
    }

    public ArrayList<Trip> getTripsForMonth(int month, int limit)
    {
        return getTrips("SELECT * FROM taxi_data WHERE pickup_datetime > " + DateTime.SECONDS_TILL_MONTH_STARTS[month - 1] + " AND pickup_datetime < " + DateTime.SECONDS_TILL_MONTH_STARTS[month] + " LIMIT 0," + limit);
    }
    
    public ArrayList<Trip> getTripsForMonth(int month)
    {
        return getTrips("SELECT * FROM taxi_data WHERE pickup_datetime > " + DateTime.SECONDS_TILL_MONTH_STARTS[month - 1] + " AND pickup_datetime < " + DateTime.SECONDS_TILL_MONTH_STARTS[month] + " LIMIT 0,5000");
    }
    
    public ArrayList<Trip> GIVEME500LATENIGHTTAXISPLEASE(boolean withACherryOnTop)
    {
        return getTrips("SELECT * FROM taxi_data WHERE (pickup_datetime % 86400) > 75600 LIMIT 0,500");
    }

    public ArrayList<Trip> getAllTrips()
    {
        ArrayList<Trip> result = new ArrayList<>();

        //System.out.println("Taxis: " + data.taxis.entrySet().size());
        for (HashMap.Entry key : data.taxis.entrySet())
        {
            Taxi taxi = (Taxi) key.getValue();
            //System.out.println("Trips: " + taxi.getTrips().size());
            for (Trip trip : taxi.getTrips())
            {
                result.add(trip);
            }
        }

        return result;
    }
    
    public ArrayList<Trip> getTrips(String query)
    {
        ArrayList<Trip> trips = null;
        try
        {
            ResultSet results = taxiDatabase.executeQuery(query);
            trips = new ArrayList<>(results.getFetchSize());
            
            while (results.next())
            {
                trips.add(new Trip(results.getString("vendor_id").equals(Data.VENDOR_CMT), results.getInt("rate_code"), results.getString("store_and_fwd_flag"), results.getInt("pickup_datetime"), results.getInt("passenger_count"), results.getInt("trip_time"), results.getFloat("trip_distance"), results.getFloat("pickup_long"), results.getFloat("pickup_lat"), results.getFloat("dropoff_long"), results.getFloat("dropoff_lat")));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trips;
    }
}
