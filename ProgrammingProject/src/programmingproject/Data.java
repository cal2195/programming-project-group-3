package programmingproject;

import processing.data.Table;

/**
 *
 * @author cal
 */
public class Data
{
    Table taxiData;
    RenderArea renderArea;
    
    //these are used so we can use our sensible names to refer to the original column names
    private final String MEDALLION = "medallion";
    private final String HACK = "hack_license";
    private final String VENDORID = "vendor_id";
    private final String RATECODE = "rate_code";
    private final String STOREANDFWDFLAG = "store_and_fwd_flag";
    private final String PICKUPTIME = "pickup_datetime";
    private final String DROPOFFTIME = "dropoff_datetime";
    private final String PASSENGER = "passenger_count";
    private final String TIME = "trip_time_in_secs";
    private final String TRIPDISTANCE = "trip_distance";
    private final String PICKUPLONG = "pickup_longitude";
    private final String PICKUPLAT = "pickup_latitude";
    private final String DROPOFFLONG = "dropoff_longitude";
    private final String DROPOFFLAT = "dropoff_latitude";

    int numberOfRecords;
    
    //used to find the relative x and y of an object
    static double TOP_LEFT_LONGITUDE = -74.212073;
    static double TOP_LEFT_LATITUDE = 40.874139;
    
    public Data(String filename, RenderArea renderArea)
    {
        this.renderArea = renderArea;
        taxiData = renderArea.loadTable(filename, "header"); //Load in the .cvs file into a table!
        // 0 = String, 1 = int, 4 = double
        int[] columnTypes = {0, 0, 0, 1, 0, 0, 0, 1, 1, 4, 4, 4, 4, 4};
        taxiData.setColumnTypes(columnTypes);
        numberOfRecords = taxiData.getRowCount();
    }
    
    public static double latToYPos(double latitude, int height){
        double pixelLat = (latitude - TOP_LEFT_LATITUDE)*-1;
        //calculating using ratio 0.210139:height
        double pixelYPos = ((pixelLat/0.210139)*height)-height/2;
        return pixelYPos;
        
    }
    public static double longToXPos(double longitude, int width){
        double pixelLong = (longitude - TOP_LEFT_LONGITUDE);
        //calculating using ratio 0.495377:width 
        double pixelXPos = ((pixelLong/0.495377)*width)-width/2;
        return pixelXPos;
    }
    
    Trip getTrip(int row){
       // TableRow tempRow = taxiData.getRow(row);
       
       return null; 
    }
}
