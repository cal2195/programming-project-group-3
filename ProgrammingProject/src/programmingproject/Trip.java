package programmingproject;

/**
 *
 * @author Dan
 */
public class Trip
{
    //store_and_fwd_flag should be a boolean, storing as string atm because it's Y/N in the csv
    String pickup_datetime, dropoff_datetime, store_and_fwd_flag;
    int rate_code, passenger_count, trip_time;
    double pickup_latitude, pickup_longitude, dropoff_latitude, dropoff_longitude;

    public Trip(String pickupTime, String dropoffTime,  String storeAndFwdFlag, int rateCode, int passengers, 
            int time, double pickupLat, double pickupLong, double dropoffLat, double dropoffLong)
    {

        this.pickup_datetime = pickupTime;
        this.dropoff_datetime = dropoffTime;
        this.store_and_fwd_flag = storeAndFwdFlag;
        this.rate_code = rateCode;
        this.passenger_count = passengers;
        this.trip_time = time;
        this.pickup_latitude = pickupLat;
        this.pickup_longitude = pickupLong;
        this.dropoff_latitude = dropoffLat;
        this.dropoff_longitude = dropoffLong;

    }
}
