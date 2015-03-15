package programmingproject;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Dan
 */
public class TripAnimator
{

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

        //renderArea.translate(mapGraphs.mapWidth / 2, mapGraphs.mapHeight / 2, 0);
        for (TaxiDrawable car : cars)
        {
            renderArea.pushMatrix();
            car.draw(renderArea);
            car.moveAndCheck();
            renderArea.popMatrix();
        }
        renderArea.popMatrix();
        renderArea.popStyle();
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
