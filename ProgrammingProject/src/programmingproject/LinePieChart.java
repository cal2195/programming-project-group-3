package programmingproject;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Random;
import processing.core.PImage;
import java.util.ArrayList;
import processing.opengl.PGraphics3D;
/**
 *
 * @author John Milsom
 */
public class LinePieChart {
    
    RenderArea renderArea;
    PImage bg;
    int[][] timeAndPassengers; 
    int[][] positions; 
    boolean doneFirstSetup;
    
    //In-case window is re-sized:
    int oldRenderHeight;
    int oldRenderWidth;
    
            
    //Data Visualisation
    int sampleSize = 300;
    
    //Camera rotation
    float cameraX, cameraY;
    MouseEvent lastMousePosition;
    float MOUSE_SENSITIVITY = 300f;
    boolean demoMode = true;
    
    //Show lines to centre
    boolean linesShowing = true;
    
    
    
    /**
     *
     * @author john
     */
    
    public LinePieChart(RenderArea renderArea)
    {
        this.renderArea = renderArea;
        bg = renderArea.loadImage("res/pieBase.png");
        timeAndPassengers = new int[sampleSize][2];
        positions = new int[sampleSize][3];
        doneFirstSetup =false;
        oldRenderHeight = renderArea.height;
        oldRenderWidth = renderArea.width;
        
    }
    public void setup()
    {
        getSamples();
        getPositions();
    }
    
    public void draw(PGraphics3D buffer)
    {
        buffer.pushStyle();
        if(!doneFirstSetup)
        {
            setup();
            doneFirstSetup = true;
        }
        buffer.pushMatrix();
        buffer.background(0);
        
        if (demoMode)
        {
            if (cameraY < 1)
            {
                cameraY += 0.005f;
            }
            cameraX += 0.001f;
        }

        buffer.translate(buffer.width / 2, buffer.height / 2, 1);
        buffer.rotateX(cameraY);
        buffer.rotateZ(cameraX);
        buffer.image(bg, -buffer.height / 2, -buffer.height / 2, buffer.height, buffer.height);
        buffer.fill(0);
        buffer.noStroke();

        buffer.translate(-buffer.width / 2, -buffer.height / 2, 0);
        
        plotPoints(buffer);
        
        buffer.popMatrix();
        
        //in-case window is resized
        if (oldRenderHeight != buffer.height || oldRenderWidth != buffer.width)
        {
            getPositions();
        }
        oldRenderHeight = buffer.height;
        oldRenderWidth = buffer.width;
        buffer.popStyle();
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
        ArrayList<Trip> currentTrips = renderArea.query.getRandomTrips(sampleSize);
        for(int count = 0; count < timeAndPassengers.length; count++ )
        {
            timeAndPassengers[count][0] = (int)(currentTrips.get(count).pickupTime)%DateTime.SECONDS_PER_DAY;
            timeAndPassengers[count][1] = currentTrips.get(count).passengers;
        }
    }
    
    public void getPositions()
    {
        for (int count = 0; count < positions.length; count++)
        {
            double angle =  ((double)timeAndPassengers[count][0])/3600/24*360 - 90;
            int xPosition = (int)((renderArea.height/2*450/600)*Math.cos(angle*Math.PI/180))+renderArea.width/2;
            int yPosition = (int)((renderArea.height/2*450/600)*Math.sin(angle*Math.PI/180))+renderArea.height/2;
            positions[count][0] = xPosition;
            positions[count][1] = yPosition;
            positions[count][2] = (timeAndPassengers[count][1])*8+20;
        }
        
    }
    
    public void plotPoints(PGraphics3D buffer)
    {
        for(int count = 0; count < positions.length ; count++)
        {
            buffer.fill(0,120,255);
            buffer.pushMatrix();
            buffer.translate(positions[count][0],positions[count][1], positions[count][2]/2 );
            buffer.box(1, 1, positions[count][2]);
            buffer.stroke(0,120,255); 
            buffer.popMatrix();
            if(linesShowing)
            {
                buffer.pushMatrix();
                buffer.translate(buffer.width/2, buffer.height/2, 34);
                buffer.box(3,3,68);
                buffer.popMatrix();
                buffer.line(positions[count][0],positions[count][1], positions[count][2], buffer.width/2, buffer.height/2, positions[count][2]);
            }
        }
    }
    
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_0)
        {
            if(linesShowing)
            {
                linesShowing = false;
            }
            else
            {
                linesShowing = true;
            }
        }
    }
    
}
