package programmingproject;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Dan
 */
public class TripAnimator
{

    public static final short MAX_SPEEDFACTOR = 100;
    public static final short MIN_SPEEDFACTOR = 1;
    public static final short SPEEDSTEP = 1;

    //have tried to keep this fairly accurate but still works best with speed factors < 100, 
    public static short speedFactor = 1;
    RenderArea renderArea;
    MapGraphs mapGraphs;

    long lastTime = 0;
    static double delta;
    

    int MODE = 0;

    Trip trip;
    //this taxi for test porpoises!
    TaxiDrawable testTaxi;

    ArrayList<Trip> trips = new ArrayList<>();
    ArrayList<Trip> queuedTrips = new ArrayList<>();
    ArrayList<TaxiDrawable> cars = new ArrayList<>();

    double timeOfDay = 0;
    

    public TripAnimator(RenderArea renderArea, MapGraphs mapGraphs)
    {
        this.renderArea = renderArea;
        this.mapGraphs = mapGraphs;
    }

    public void draw()
    {
        renderArea.pushStyle();
        renderArea.pushMatrix();

        delta = 1;
        if (lastTime == 0)
        {
            lastTime = System.currentTimeMillis();
        }
        else
        {
            delta = (System.currentTimeMillis() - lastTime) / 1000f;
            lastTime = System.currentTimeMillis();
            //System.out.println(delta);
        }
        

        renderArea.stroke(0);
        //renderArea.translate(mapGraphs.mapWidth / 2, mapGraphs.mapHeight / 2, 0);
        for (TaxiDrawable car : cars)
        {
            renderArea.pushMatrix();
            car.draw(renderArea, (int)timeOfDay);
            car.moveAndCheck((int)timeOfDay);
            renderArea.popMatrix();
        }
        renderArea.popMatrix();
        renderArea.popStyle();
        timeOfDay += speedFactor * delta;

        renderArea.pushStyle();
        renderArea.pushMatrix();
        renderArea.translate(0, 0, 0);
        renderArea.fill(0);
        renderArea.textSize(50);
        renderArea.text(DateTime.secsToHourAndMinute((int)timeOfDay), -300f, 10f, 3f);
        renderArea.textSize(25);
        String percentString = String.format("%.2f", (float) speedFactor / (float) MAX_SPEEDFACTOR * 100);
        renderArea.text(percentString + "% speed", -300f, 50f, 3f);
        renderArea.popMatrix();
        renderArea.popStyle();
        if (timeOfDay >= DateTime.SECONDS_PER_DAY)
        {
            timeOfDay = 0;
        }
    }

    public void switchData(ArrayList<Trip> data)
    {
        trips = data;
        System.out.println("TRIP SIZE: " + trips.size());
    }

    public void setData(ArrayList<Trip> data)
    {
        queuedTrips = data;
        for (Trip trip : data)
        {
            System.out.println(DateTime.secsToDateTime(trip.pickupTime));
        }
        reset();
        switchData();
    }

    public void reset()
    {
        cars.clear();
        timeOfDay = 0;
    }

    public void switchData()
    {
        trips = queuedTrips;
        for (Trip trip : trips)
        {
            TaxiDrawable tempCar = new TaxiDrawable(trip, mapGraphs.map);
            cars.add(tempCar);
        }
        System.out.println("TRIP SIZE: " + trips.size());
    }

    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_1)
        {
            setData(renderArea.query.getTripsForMonth(1, 1000));
        } else if (e.getKeyCode() == KeyEvent.VK_2)
        {
            setData(renderArea.query.getTripsForMonth(2, 1000));
        } else if (e.getKeyCode() == KeyEvent.VK_3)
        {
            setData(renderArea.query.GIVEME500LATENIGHTTAXISPLEASE(true));
        } else if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            timeOfDay += DateTime.SECONDS_PER_HOUR;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if (timeOfDay > DateTime.SECONDS_PER_HOUR)
            {
                timeOfDay -= DateTime.SECONDS_PER_HOUR;
                for (TaxiDrawable car : cars)
                {
                    car.resetTaxi();
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if (speedFactor < MAX_SPEEDFACTOR)
            {
                speedFactor += SPEEDSTEP;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if (speedFactor > MIN_SPEEDFACTOR)
            {
                speedFactor -= SPEEDSTEP;
            }
        }
    }
}
