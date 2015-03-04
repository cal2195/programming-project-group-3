package programmingproject;

import java.util.ArrayList;

/**
 *
 * @author Dan
 */
public class Taxi
{
    private String medallion, hack_license, vendor_id;
    private ArrayList<Trip> trips;

    public Taxi(String medallion, String hackLicense, String vendorID)
    {
        this.medallion = medallion;
        this.hack_license = hackLicense;
        this.vendor_id = vendorID;
    }
}
