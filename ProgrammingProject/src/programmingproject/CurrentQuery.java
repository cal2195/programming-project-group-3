package programmingproject;

import java.util.ArrayList;

/**
 *
 * @author Aran Nolan <nolanar@tcd.ie>
 */
public class CurrentQuery
{

    RenderArea renderArea;
    private ArrayList<Trip> queryOne, queryTwo, activeQuery;

    public CurrentQuery(RenderArea renderArea)
    {
        this.renderArea = renderArea;

        queryOne = new ArrayList<>();
        queryTwo = new ArrayList<>();

        activeQuery = queryOne;
    }

    public void swap()
    {
        if (activeQuery == queryOne)
        {
            activeQuery = queryTwo;
        } else
        {
            activeQuery = queryOne;
        }
    }

    public void sendEventToActiveVisualisation()
    {

    }

    public void requestQuery(String query, boolean setQueryOne)
    {

        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                ArrayList<Trip> queryBuffer = renderArea.query.getTrips(query);

                if (setQueryOne)
                {
                    queryOne = queryBuffer;
                } else
                {
                    queryTwo = queryBuffer;
                }
            }
        }, "Query Thread").start();
    }
    
    public void notifyVisualisation()
    {
        
    }

    public ArrayList<Trip> active()
    {
        return activeQuery;
    }

    public void setQueryOne(ArrayList<Trip> trips)
    {
        queryOne = trips;
    }

    public void setQueryTwo(ArrayList<Trip> trips)
    {
        queryTwo = trips;
    }
}
