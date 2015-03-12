package programmingproject;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Random;
import processing.core.PImage;
import java.util.ArrayList;
/**
 *
 * @author John Milsom
 */
public class LinePieChart {
    
    RenderArea renderArea;
    PImage bg;
    int[][] timeAndPassengers; 
    int[][] positions; 
            
    //Data Visualisation
    int sampleSize = 500; 
    public static final int SECONDS_PER_DAY = 86400;
    
    //Camera rotation
    float cameraX, cameraY;
    MouseEvent lastMousePosition;
    float MOUSE_SENSITIVITY = 300f;
    boolean demoMode = true;
    
    public LinePieChart(RenderArea renderArea)
    {
        this.renderArea = renderArea;
        bg = renderArea.loadImage("res/pieBase.png");
        timeAndPassengers = new int[sampleSize][2];
        positions = new int[sampleSize][3];
        getSamples();
        getPositions();
        
    }
    
    public void draw()
    {
        renderArea.pushMatrix();
        renderArea.background(0);
        
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
        renderArea.image(bg, -renderArea.height / 2, -renderArea.height / 2, renderArea.height, renderArea.height);
        renderArea.fill(0);
        renderArea.noStroke();

        renderArea.translate(-renderArea.width / 2, -renderArea.height / 2, 0);
        
        plotPoints();
        
        renderArea.popMatrix();
        
        
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
    
    public void getSamples()
    {
        for(int count = 0; count < timeAndPassengers.length; count++ )
        {
            Taxi randomTaxi = renderArea.data.taxis.get((int)renderArea.random(renderArea.data.taxis.size()));
            ArrayList<Trip> currentTrips;
            currentTrips = randomTaxi.getTrips();
            int randomTrip = (int)renderArea.random(currentTrips.size());
            timeAndPassengers[count][0] = (int)(currentTrips.get(randomTrip).pickupTime);
            timeAndPassengers[count][1] = currentTrips.get(randomTrip).passengers;
        }
    }
    
    public void getPositions()
    {
        for (int count = 0; count < positions.length; count++)
        {
            
            int angle =  timeAndPassengers[count][0] *360;
            System.out.println(timeAndPassengers[count][0] + "----------------" + angle);
            int xPosition = (int)(250*Math.sin(angle))+675;
            int yPosition = (int)(250*Math.cos(angle))+350;
            positions[count][0] = xPosition;
            positions[count][1] = yPosition;
            positions[count][2] = (timeAndPassengers[count][1])*10+20;
            System.out.println(positions[count][0]+ "  " + positions[count][1] + "  " +positions[count][2]);
        }
    }
    
    public void plotPoints()
    {
        for(int count = 0; count < positions.length ; count++)
        {
            renderArea.pushMatrix();
            renderArea.fill(0,0,255);
            renderArea.translate(positions[count][0],positions[count][1],positions[count][2]);
            renderArea.box(2);
            renderArea.popMatrix();
        }
    }
     
    
}
