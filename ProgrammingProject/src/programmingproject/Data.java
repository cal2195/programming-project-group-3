package programmingproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.data.Table;

/**
 *
 * @author cal
 */
public class Data
{

    String[] meds, hacks;
    ConcurrentHashMap<Integer, Taxi> taxis;
    Table taxiData;
    RenderArea renderArea;
    String dataFile = "";
    Thread loadDataThread = new Thread(new Runnable()
    {

        @Override
        public void run()
        {
            loadData(dataFile);
        }
    }, "Load Data Thread");
    boolean dataLoaded = false;
    
    //these are no fun, anyone able to check? it wasnt a leap year!
    public static final int[] SECONDS_TILL_MONTH_STARTS = {0,2678400,5097600,7776000, 10368000, 13046400, 15638400,
                                                            18316800, 20995200, 23587200, 26265600, 28857600};

    //these are used so we can use our sensible names to refer to the original column names
    static final String MEDALLION = "medallion";
    static final String HACK = "hack_license";
    static final String VENDORID = "vendor_id";
    static final String RATECODE = "rate_code";
    static final String STOREANDFWDFLAG = "store_and_fwd_flag";
    static final String PICKUPTIME = "pickup_datetime";
    static final String DROPOFFTIME = "dropoff_datetime";
    static final String PASSENGER = "passenger_count";
    static final String TIME = "trip_time_in_secs";
    static final String TRIPDISTANCE = "trip_distance";
    static final String PICKUPLONG = "pickup_longitude";
    static final String PICKUPLAT = "pickup_latitude";
    static final String DROPOFFLONG = "dropoff_longitude";
    static final String DROPOFFLAT = "dropoff_latitude";

    int numberOfRecords;
    int errorCount = 0;
    //to be used to find the relative x and y of a taxi
    static float TOP_LEFT_LONGITUDE = -74.195073f;
    static float TOP_LEFT_LATITUDE = 40.874139f;

    public Data(String filename, RenderArea renderArea)
    {
        this.renderArea = renderArea;

        taxis = new ConcurrentHashMap<>();
        dataFile = "res/taxi_data.csv";
        loadMeds("res/meds.txt");
        loadHacks("res/hacks.txt");
        loadDataThread.start();
    }

    public void loadMeds(String file)
    {
        meds = renderArea.loadStrings(file);
    }

    public void loadHacks(String file)
    {
        hacks = renderArea.loadStrings(file);
    }
    
    public void loadData(String file)
    {
        BufferedReader buff = null;
        try
        {
            buff = new BufferedReader(new FileReader(file));
            String current = "";
            int count = 0;
            buff.readLine(); //Skip the headers!
            while ((current = buff.readLine()) != null)
            {
                count++;
                if (count % 10000 == 0)
                {
                    System.out.println("Loading Line " + count + "...");
                }
                if (count == 2000000)
                {
                //    return;
                }
                String[] currentLine = current.split(",");
                if (!taxis.containsKey(currentLine[0]))
                {
                    taxis.put(new Integer(currentLine[0]), new Taxi((byte) taxis.size(), currentLine[1], currentLine[2]));
                }
                Taxi temp = taxis.get(new Integer(currentLine[0]));
                try
                {
                    temp.addTrip(new Trip(Integer.parseInt(currentLine[3]), currentLine[4], Long.parseLong(currentLine[5]), Integer.parseInt(currentLine[6]), Integer.parseInt(currentLine[7]), Float.parseFloat(currentLine[8]), Float.parseFloat(currentLine[9]), Float.parseFloat(currentLine[10]), Float.parseFloat(currentLine[11]), Float.parseFloat(currentLine[12])));
                } catch (Exception exception)
                {
                    exception.printStackTrace();
                    System.out.println("With line: " + current);
                    errorCount++;
                    //Malformed data! Ignoring! ;)
                }
            }
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            try
            {
                buff.close();
            } catch (IOException ex)
            {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("TOTAL ERRORS FOUND: " + errorCount);
        dataLoaded = true;
    }

    public static float latToYPos(float latitude, int height)
    {
        float pixelLat = (latitude - TOP_LEFT_LATITUDE) * -1;
        //calculating using ratio 0.210139:height
        float pixelYPos = ((pixelLat / 0.210139f) * height);
        return pixelYPos;

    }

    public static float longToXPos(float longitude, int width)
    {
        float pixelLong = (longitude - TOP_LEFT_LONGITUDE);
        //calculating using ratio 0.495377:width 
        float pixelXPos = ((pixelLong / 0.495377f) * width);
        return pixelXPos;
    }

    public void printTaxiInfo()
    {
        for (Integer key : taxis.keySet())
        {
            Taxi temp = taxis.get(key);
            System.out.print(temp.toString());
        }
    }

    @Override
    public String toString()
    {
        String result = "";
        for (Integer key : taxis.keySet())
        {
            Taxi temp = taxis.get(key);
            result += temp.toString();
        }
        return result;
    }
}
