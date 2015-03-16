package programmingproject;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Dan
 */
public class TripAnimator
{

    //have tried to keep this fairly accurate but still works best with speed factors < 100, 
    //80 seems to be the highest factor with no obvious issues
    public static final short SPEEDFACTOR = 50;
    RenderArea renderArea;
    MapGraphs mapGraphs;

    int MODE = 0;

    Trip trip;
    //this taxi for test porpoises!
    TaxiDrawable testTaxi;

    ArrayList<Trip> trips = new ArrayList<>();
    ArrayList<Trip> queuedTrips = new ArrayList<>();
    ArrayList<TaxiDrawable> cars = new ArrayList<>();

    int frames = 0;

    public TripAnimator(RenderArea renderArea, MapGraphs mapGraphs)
    {
        this.renderArea = renderArea;
        this.mapGraphs = mapGraphs;
    }

    public void draw()
    {
        renderArea.pushStyle();
        renderArea.pushMatrix();
        renderArea.stroke(0);
        //renderArea.translate(mapGraphs.mapWidth / 2, mapGraphs.mapHeight / 2, 0);
        for (TaxiDrawable car : cars)
        {
            renderArea.pushMatrix();
            car.draw(renderArea, frames*SPEEDFACTOR);
            car.moveAndCheck(frames*SPEEDFACTOR);
            renderArea.popMatrix();
        }
        renderArea.popMatrix();
        renderArea.popStyle();
        frames++;

        renderArea.pushStyle();
        renderArea.pushMatrix();
        renderArea.translate(0, 0, 0);
        renderArea.fill(0);
        renderArea.textSize(50);
        renderArea.text(DateTime.secsToHourAndMinute(frames*SPEEDFACTOR), -300f, 10f, 3f);
        renderArea.popMatrix();
        renderArea.popStyle();
        if(frames*SPEEDFACTOR >= DateTime.SECONDS_PER_DAY)
            frames = 0;
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

    public void reset()
    {
        cars.clear();
        frames = 0;
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
        }
    }
}
