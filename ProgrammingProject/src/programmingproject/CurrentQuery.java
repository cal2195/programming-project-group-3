
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
    
    public ArrayList<Trip> active()
    {
        return activeQuery;
    }
}
