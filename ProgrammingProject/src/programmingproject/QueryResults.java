package programmingproject;

import java.util.ArrayList;

/**
 *
 * @author cal
 */
public class QueryResults
{

    Data data;
    short[][] results;

    public QueryResults(Data data, short[][] results)
    {
        this.data = data;
        this.results = results;
    }

    public ArrayList<Trip> getTripsAsArrayList()
    {
        ArrayList<Trip> trips = new ArrayList<>();
        for (short[] result : results)
        {
            trips.add(data.taxis.get(result[0]).getTrips().get(result[1]));
        }
        return trips;
    }
}
