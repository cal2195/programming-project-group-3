package programmingproject;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import processing.core.PImage;

/**
 *
 * @author Dan
 */
public class TripAnimator
{

    RenderArea renderArea;

    int MODE = 0;

    PImage bg;
    Trip trip;
    //this taxi for test porpoises!
    TaxiDrawable testTaxi;
    
    ArrayList<Trip> trips = new ArrayList<>();
    ArrayList<Trip> queuedTrips = new ArrayList<>();
    ArrayList<TaxiDrawable> cars = new ArrayList<>();
    
    //Camera Rotation
    float cameraX, cameraY;
    MouseEvent lastMousePosition;
    float MOUSE_SENSITIVITY = 300f;
    boolean demoMode = true;
    boolean minimize = false;
    int frames = 0;

    public TripAnimator(RenderArea renderArea)
    {
        this.renderArea = renderArea;
        bg = renderArea.loadImage("res/newyork.png");
        // sample trip from small csv
        trip = new Trip(1, "N", 100, 3, 600, (float) 10.5, (float) -73.978165, (float) 40.757977, (float) -73.989838, (float) 40.751171);
        testTaxi = new TaxiDrawable(trip);
        
        int count = 0;

    }

    public void draw()
    {
        renderArea.background(179, 209, 255);

        if (demoMode)
        {
            if (cameraY < 1)
            {
                cameraY += 0.005f;
            }
            cameraX += 0.001f;
        }

        renderArea.translate(renderArea.width / 2, renderArea.height / 2, 1);
        renderArea.rotateX(cameraY);
        renderArea.rotateZ(cameraX);
        renderArea.image(bg, -renderArea.width / 2, -renderArea.height / 2, renderArea.width, renderArea.height);
        renderArea.fill(0);
        renderArea.noStroke();
        renderArea.translate(-renderArea.width / 2, -renderArea.height / 2, 0);
        

        for(TaxiDrawable car : cars){
            renderArea.pushMatrix();
            car.draw(renderArea);
            car.moveAndCheck();
            renderArea.popMatrix();
        }

        
        //easy trigger for when to draw taxies
        frames++;
        if(frames == 100){
            setData(renderArea.query.getTripsForMonth(1));
            switchData();
        }
        
        testTaxi.draw(renderArea);
        testTaxi.moveAndCheck();
        
        System.out.println(frames);
//      System.out.println("frame drawn");
    }

    public void switchData(ArrayList<Trip> data)
    {
        trips = data;
        System.out.println("TRIP SIZE: " + trips.size());
    }
    
    
    public void mousePressed(MouseEvent e)
    {
        demoMode = false;
    }

    public void mouseDragged(MouseEvent e)
    {
        if (lastMousePosition == null)
        {
            lastMousePosition = e;
        }
        cameraX -= (e.getXOnScreen() - lastMousePosition.getXOnScreen()) / MOUSE_SENSITIVITY;
        cameraY -= (e.getYOnScreen() - lastMousePosition.getYOnScreen()) / MOUSE_SENSITIVITY;
        lastMousePosition = e;
    }

    public void mouseReleased(MouseEvent e)
    {
        lastMousePosition = null;
    }
    

        public void setData(ArrayList<Trip> data)
    {
        minimize = true;
        queuedTrips = data;
    }

    public void switchData()
    {
        trips = queuedTrips;
        for(Trip trip : trips){
        TaxiDrawable tempCar = new TaxiDrawable(trip);
        cars.add(tempCar);
        }
        System.out.println("TRIP SIZE: " + trips.size());
    }
    
        public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_1)
        {
            setData(renderArea.query.getTripsForMonth(1));

        } else if (e.getKeyCode() == KeyEvent.VK_2)
        {
            setData(renderArea.query.getTripsForMonth(2));
        } else if (e.getKeyCode() == KeyEvent.VK_3)
        {
            setData(renderArea.query.getTripsForMonth(3));
        }
    }
}
