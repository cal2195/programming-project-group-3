package programmingproject;

import java.util.ArrayList;

/**
 *
 * @author Dan
 */
public class Taxi
{
    private String medallion, hack, vendorID;
    private ArrayList<Trip> trips;

    public Taxi(String medallion, String hackLicense, String vendorID)
    {
        this.medallion = medallion;
        this.hack = hackLicense;
        this.vendorID = vendorID;
    }
    
    public String getMedallion()
    {
        return medallion;
    }
    
    public boolean addTrip(Trip newTrip)
    {
       return trips.add(newTrip);
    }
}
