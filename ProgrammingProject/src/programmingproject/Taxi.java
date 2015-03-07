package programmingproject;

import java.util.ArrayList;

/**
 *
 * @author Dan
 */
public class Taxi
{
    private String hack;
    private boolean vendorID;
    private byte medallion;
    private ArrayList<Trip> trips;

    public Taxi(byte medallion, String hackLicense, String vendorID)
    {
        this.medallion = medallion;
        this.hack = hackLicense;
        this.vendorID = vendorID.equals("CMT");
        trips = new ArrayList<Trip>();
    }
    
    public byte getMedallion()
    {
        return medallion;
    }
    
    public boolean addTrip(Trip newTrip)
    {
       return trips.add(newTrip);
    }
    
    public ArrayList<Trip> getTrips()
    {
        return (ArrayList<Trip>) trips.clone();
    }
    
    public String toString()
    {
        String result = "";
        for(Trip t : trips)
        {
            result += medallion + " ";
            result += hack + " ";
            result += (vendorID? "CMT" : "VTS") + " ";
            result += t.toString();
            result += "\n";
        }
        return result;
    }
}
