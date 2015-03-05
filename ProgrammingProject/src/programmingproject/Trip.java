
package programmingproject;

/**
 *
 * @author Dan
 */
public class Trip
{
    //store_and_fwd_flag should be a boolean, storing as string atm because it's Y/N in the csv
    String pickupTime, dropoffTime;
    boolean storeAndFwdFlag;
    int rateCode, passengers, time;
    double distance, pickupLat, pickupLong, dropoffLat, dropoffLong;

    public Trip(String pickupTime, String dropoffTime,  String storeAndFwdFlag, int rateCode, int passengers, 
            int time, double distance, double pickupLat, double pickupLong, double dropoffLat, double dropoffLong)
    {

        this.pickupTime = pickupTime;
        this.dropoffTime = dropoffTime;
        this.storeAndFwdFlag = storeAndFwdFlag.equals("Y");
        this.rateCode = rateCode;
        this.passengers = passengers;
        this.time = time;
        this.distance = distance;
        this.pickupLat = pickupLat;
        this.pickupLong = pickupLong;
        this.dropoffLat = dropoffLat;
        this.dropoffLong = dropoffLong;

    }
}
