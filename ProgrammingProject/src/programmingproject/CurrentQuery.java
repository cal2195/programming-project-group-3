
package programmingproject;

import java.util.ArrayList;

/**
 *
 * @author Aran Nolan <nolanar@tcd.ie>
 */
public class CurrentQuery
{
    
    private ArrayList<Trip> queryOne, queryTwo, activeQuery;
    
    public CurrentQuery()
    {
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
    
    public void requestQuery(String query)
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
