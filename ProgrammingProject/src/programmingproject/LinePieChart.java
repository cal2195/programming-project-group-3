package programmingproject;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import static jdk.nashorn.internal.objects.NativeMath.random;
import processing.core.PImage;
import java.util.ArrayList;
/**
 *
 * @author John Milsom
 */
public class LinePieChart {
    
    RenderArea renderArea;
    PImage bg;
    
    //Data Visualisation
    int sampleSize = 100;
    long[][] timeAndPassengers = new long[100][2];
    
    //Camera rotation
    float cameraX, cameraY;
    MouseEvent lastMousePosition;
    float MOUSE_SENSITIVITY = 300f;
    boolean demoMode = true;
    
    public LinePieChart(RenderArea renderArea)
    {
        this.renderArea = renderArea;
        bg = renderArea.loadImage("res/pieBase.png");

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
            Taxi randomTaxi = renderArea.data.taxis.get((int)random(renderArea.data.taxis.size()));
            ArrayList<Trip> currentTrips;
            currentTrips = randomTaxi.getTrips();
            int randomTrip = (int)random(currentTrips.size());
        }
    }
     
    
}
