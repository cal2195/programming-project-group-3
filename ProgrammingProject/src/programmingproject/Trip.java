
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
    float distance, pickupLat, pickupLong, dropoffLat, dropoffLong;
    boolean accountedFor = false;

    public Trip(int rateCode, String storeAndFwdFlag, String pickupTime, String dropoffTime, int passengers, 
            int time, float distance, float pickupLong, float pickupLat, float dropoffLong, float dropoffLat)
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
    
    public String toString(){
        String result = "";
        result += pickupTime + " ";
        result += dropoffTime + " ";
        result += storeAndFwdFlag ? "Y " : "N ";
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
