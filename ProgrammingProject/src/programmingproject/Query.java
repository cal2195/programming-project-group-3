package programmingproject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author cal
 */
public class Query
{

    Data data;

    public Query(Data data)
    {
        this.data = data;
    }

    public ArrayList<Trip> getTripsForTaxi(String medallion)
    {
        short taxiIndex = -1;
        for (short i = 0; i < data.meds.length; i++)
        {
            if (data.meds[i].equals(medallion))
            {
                taxiIndex = i;
                break;
            }
        }
        if (taxiIndex == -1)
        {
            return new ArrayList<Trip>(); //Empty Set!
        }
        return data.taxis.get(taxiIndex).getTrips();
    }

    public ArrayList<Trip> getTripsForMonth(int month)
    {
        ArrayList<Trip> result = new ArrayList<>();
        
        for (HashMap.Entry key : data.taxis.entrySet())
        {
            Taxi taxi = (Taxi) key.getValue();
            for (Trip trip : taxi.getTrips())
            {
                if (trip.pickupTime > DateTime.SECONDS_TILL_MONTH_STARTS[month - 1] && trip.pickupTime < DateTime.SECONDS_TILL_MONTH_STARTS[month])
                {
                    //System.out.println(DateTime.SECONDS_TILL_MONTH_STARTS[month - 1] + " > " + trip.pickupTime + " < " + DateTime.SECONDS_TILL_MONTH_STARTS[month]);
                    result.add(trip);
                }
            }
        }

        return result;
    }
}
