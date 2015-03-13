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
            timeAndPassengers[count][0] = (int)(currentTrips.get(randomTrip).pickupTime)%SECONDS_PER_DAY;
            timeAndPassengers[count][1] = currentTrips.get(randomTrip).passengers;
            System.out.println("----------" + timeAndPassengers[count][0]);
        }
    }
    
    public void getPositions()
    {
        for (int count = 0; count < positions.length; count++)
        {
            
            int angle =  timeAndPassengers[count][0] *360;
            int xPosition = (int)(264*Math.sin(angle))+renderArea.width/2;
            int yPosition = (int)(264*Math.cos(angle))+renderArea.height/2;
            positions[count][0] = xPosition;
            positions[count][1] = yPosition;
            positions[count][2] = (timeAndPassengers[count][1])*8+20;
        }
    }
    
    public void plotPoints()
    {
        for(int count = 0; count < positions.length ; count++)
        {
            renderArea.fill(0,120,255);
            
            for(int zPos = 0; zPos <= positions[count][2] ; zPos++){
                renderArea.pushMatrix();
                renderArea.translate(positions[count][0],positions[count][1],zPos);
                renderArea.box(1);
                renderArea.popMatrix();
            } 
            renderArea.pushMatrix();
            renderArea.line(positions[count][0], positions[count][1], positions[count][2], renderArea.width/2, renderArea.height/2, positions[count][2]);
            renderArea.popMatrix();
        }
       
        for(int zPos = 0 ; zPos <= 52; zPos++){
            renderArea.pushMatrix();
            renderArea.translate(renderArea.width/2,renderArea.height/2,zPos);
            renderArea.box(5);
            renderArea.popMatrix();
        }
        
    }
     
    
}
