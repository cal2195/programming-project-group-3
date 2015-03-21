package programmingproject;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import processing.opengl.PGraphics3D;

/**
 *
 * @author Dan
 */
public class TripAnimator
{

    public static final short MAX_SPEEDFACTOR = 500;
    public static final short MIN_SPEEDFACTOR = -500;
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

    public void draw(PGraphics3D buffer)
    {
        buffer.pushStyle();
        buffer.pushMatrix();

        delta = 1;
        if (lastTime == 0)
        {
            lastTime = System.currentTimeMillis();
        } else
        {
            delta = (System.currentTimeMillis() - lastTime) / 1000f;
            lastTime = System.currentTimeMillis();
            //   System.out.println(delta);
        }

        buffer.stroke(0);
//        buffer.translate(mapGraphs.mapWidth / 2, mapGraphs.mapHeight / 2, 0);
        for (TaxiDrawable car : cars)
        {
            buffer.pushMatrix();
            car.draw(buffer, (int) timeOfDay);
            car.moveAndCheck((int) timeOfDay);
            buffer.popMatrix();
        }
        timeOfDay += speedFactor * delta;

        buffer.fill(20);
        buffer.textFont(this.renderArea.createFont("Calibri", 50, false));
        buffer.textSize(50);
        buffer.text(DateTime.secsToHourAndMinute((int) timeOfDay), -300f, 10f, 3f);
        buffer.textSize(25);
        buffer.text(speedFactor + "x realtime", -300f, 50f, 3f);
        buffer.popMatrix();
        buffer.popStyle();
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
        reset();
        switchData();
    }
    
    public void reloadData()
    {
        cars.clear();
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
            TaxiDrawable tempCar = new TaxiDrawable(trip, mapGraphs.map, true);
            cars.add(tempCar);
        }
        System.out.println("TRIP SIZE: " + trips.size());
    }

    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_1)
        {
            setData(renderArea.query.getTripsForMonth(1, 500000));
        } else if (e.getKeyCode() == KeyEvent.VK_2)
        {
            setData(renderArea.query.getTripsForMonth(2, 500000));
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
            if (e.isShiftDown())
            {
                if (speedFactor < MAX_SPEEDFACTOR)
                {
                    speedFactor += SPEEDSTEP * 10;
                }
            }
            else if (speedFactor < MAX_SPEEDFACTOR)
            {
                speedFactor += SPEEDSTEP;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if (e.isShiftDown())
            {
                if (speedFactor < MAX_SPEEDFACTOR)
                {
                    speedFactor -= SPEEDSTEP * 10;
                }
            }
            else if (speedFactor > MIN_SPEEDFACTOR)
            {
                speedFactor -= SPEEDSTEP;
            }
        }
    }
}
