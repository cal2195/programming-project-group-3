package programmingproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.data.Table;
import processing.data.TableRow;

/**
 *
 * @author cal
 */
public class Data
{

    ConcurrentHashMap<String, Taxi> taxis;
    Table taxiData;
    RenderArea renderArea;
    String dataFile = "";
    Thread loadDataThread = new Thread( new Runnable()
    {

        @Override
        public void run()
        {
            loadData(dataFile);
        }
    }, "Load Data Thread");
    boolean dataLoaded = false;

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
    //n.b. these values are currently all bullshit, to be used to find the relative x and y of a taxi
    static float TOP_LEFT_LONGITUDE = -74.212073f;
    static float TOP_LEFT_LATITUDE = 40.874139f;

    public Data(String filename, RenderArea renderArea)
    {
        this.renderArea = renderArea;

        taxis = new ConcurrentHashMap<>();
        dataFile = "res/trip_data_2.csv";
        loadDataThread.start();
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
                String[] currentLine = current.split(",");
                if (!taxis.containsKey(currentLine[0]))
                {
                    taxis.put(currentLine[0], new Taxi((byte) taxis.size(), currentLine[1], currentLine[2]));
                }
                Taxi temp = taxis.get(currentLine[0]);
                try
                {
                    temp.addTrip(new Trip(Integer.parseInt(currentLine[3]), currentLine[4], currentLine[5], currentLine[6], Integer.parseInt(currentLine[7]), Integer.parseInt(currentLine[8]), Float.parseFloat(currentLine[9]), Float.parseFloat(currentLine[10]), Float.parseFloat(currentLine[11]), Float.parseFloat(currentLine[12]), Float.parseFloat(currentLine[13])));
                } catch (Exception exception)
                {
                    exception.printStackTrace();
                    System.out.println("With line: " + current);
                    //Malformed data! Ignoring! ;)
                }
//                try
//                {
//                    Thread.sleep(0, 1);
//                } catch (InterruptedException ex)
//                {
//                    Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
//                }
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

    public Trip getTrip(int row)
    {
        TableRow tempRow = taxiData.getRow(row);
        //use this to grab stuff from row...
        String medallion = tempRow.getString(MEDALLION);
        if (!taxis.containsKey(medallion))
        {
            taxis.put(medallion, new Taxi((byte) taxis.size(), tempRow.getString(HACK),
                    tempRow.getString(VENDORID)));
        }
        Taxi temp = taxis.get(medallion);
        Trip newTrip = new Trip(
                tempRow.getInt(RATECODE),
                tempRow.getString(STOREANDFWDFLAG),
                tempRow.getString(PICKUPTIME),
                tempRow.getString(DROPOFFTIME),
                tempRow.getInt(PASSENGER),
                tempRow.getInt(TIME),
                tempRow.getFloat(TRIPDISTANCE),
                tempRow.getFloat(PICKUPLAT),
                tempRow.getFloat(PICKUPLONG),
                tempRow.getFloat(DROPOFFLAT),
                tempRow.getFloat(DROPOFFLONG));
        temp.addTrip(newTrip);
        double pickupLat = (tempRow.getFloat(PICKUPLAT));  // Prints "Mosquito"
        return newTrip;
    }

    public void printTaxiInfo()
    {
        for (String key : taxis.keySet())
        {
            Taxi temp = taxis.get(key);
            System.out.print(temp.toString());
        }
    }

    @Override
    public String toString()
    {
        String result = "";
        for (String key : taxis.keySet())
        {
            Taxi temp = taxis.get(key);
            result += temp.toString();
        }
        return result;
    }
}
