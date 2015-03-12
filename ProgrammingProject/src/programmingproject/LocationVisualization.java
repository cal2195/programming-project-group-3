/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmingproject;

/**
 *
 * @author Shane
 */
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Google;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import processing.core.PImage;

public class LocationVisualization 
{
    ArrayList<LocationDrawable> locations;
     RenderArea renderArea;

    //Constants
    UnfoldingMap map;
    int mapWidth = 2000, mapHeight = 1500;
    LocationDrawable test;
    PImage bg;

    //Camera Rotation
    float cameraX, cameraY;
    MouseEvent lastMousePosition;
    float MOUSE_SENSITIVITY = 300f;
    boolean demoMode = true;
    boolean minimize = false;
    int frames = 0;
    
    public LocationVisualization(RenderArea area){
        map = new UnfoldingMap(area, -mapWidth / 2, -mapHeight / 2, mapWidth, mapHeight, new Google.GoogleMapProvider());
        map.zoomAndPanTo(12, new Location(40.731416f, -73.990667f));
        locations = new ArrayList<LocationDrawable>();
        locations.add(new LocationDrawable((float)40.7484,(float)73.9857,"Empire State Building", map)); //Empire State building
        locations.add(new LocationDrawable((float)40.7116,(float)74.0123, "Ground Zero", map)); //ground zero
        locations.add(new LocationDrawable((float)40.7577,(float)73.9857, "Times Square", map)); //Times square
        locations.add(new LocationDrawable((float)40.7701,(float)73.9821, "Broadway", map)); //Broadway
        locations.add(new LocationDrawable((float)40.6397,(float)73.7789, "JFK Airport", map)); //JFK airport
        locations.add(new LocationDrawable((float)40.7833,(float)73.9667, "Central Park", map)); //Central park
        locations.add(new LocationDrawable((float)40.7494,(float)73.9681,"UN HQ", map)); //UN HQ
        test = new LocationDrawable((float)40.7455, (float)73.7777, "test",  map);
        this.renderArea = area;
        bg = renderArea.loadImage("res/newyork.png");
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
        map.draw();
        renderArea.fill(0);
        renderArea.noStroke();
        renderArea.translate(-renderArea.width / 2, -renderArea.height / 2, 0);
        
        for(LocationDrawable l : locations)
        {
            renderArea.pushMatrix();
            l.draw(renderArea);
            renderArea.popMatrix();
        }    
        renderArea.pushMatrix();
        test.draw(renderArea);
        renderArea.popMatrix();
        frames++;
        System.out.println(frames);
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
    
    public void keyPressed(KeyEvent e)
    {
        
    }
}    
