package programmingproject;

/**
 *
 * @author Dan
 */
public class Trip
{
    int pickupTime;
    boolean vendorID;   // 'CMT' corresponds to True; 'VTS' corresponds to False
    boolean[] flags = new boolean[1]; //flags is currently[storeAndFwdFlag], but we can use this to store useful data like evening/etc
    int rateCode, passengers, time;
    float distance, pickupLat, pickupLong, dropoffLat, dropoffLong;

    public Trip(boolean vendorID, int rateCode, String storeAndFwdFlag, int pickupTime, int passengers, 
            int time, float distance, float pickupLong, float pickupLat, float dropoffLong, float dropoffLat)
    {
        this.pickupTime = pickupTime;
        this.vendorID = vendorID;
        this.flags[0] = storeAndFwdFlag.equals("Y");
        this.rateCode = rateCode;
        this.passengers = passengers;
        this.time = time;
        this.distance = distance;
        this.pickupLat = pickupLat;
        this.pickupLong = pickupLong;
        this.dropoffLat = dropoffLat;
        this.dropoffLong = dropoffLong;

    }

    public String toString()
    {
        String result = "";
        result += DateTime.secsToDateTime(pickupTime) + " ";
        result += DateTime.secsToDateTime(pickupTime + time) + " ";
        result += flags[0] ? "Y " : "N ";
        result += rateCode + " ";
        result += passengers + " ";
        result += time + " ";
        result += distance + " ";
        result += pickupLat + " ";
        result += pickupLong + " ";
        result += dropoffLat + " ";
        result += dropoffLong + " ";

        return result;
    }
}
