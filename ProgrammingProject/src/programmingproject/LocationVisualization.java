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
      MapGraphs mapGraphs;
    LocationDrawable test;
    int frames = 0;
    
    public LocationVisualization(RenderArea area, MapGraphs mapGraphs){
        this.mapGraphs = mapGraphs;
        this.renderArea = area;
        //initializes several locations
        locations = new ArrayList<LocationDrawable>();
        locations.add(new LocationDrawable((float)40.7484,(float)-73.9857,"Empire State Building", mapGraphs.map)); //Empire State building
        locations.add(new LocationDrawable((float)40.7116,(float)-74.0123, "Ground Zero", mapGraphs.map)); //ground zero
        locations.add(new LocationDrawable((float)40.7577,(float)-73.9857, "Times Square", mapGraphs.map)); //Times square
        locations.add(new LocationDrawable((float)40.7701,(float)-73.9821, "Broadway", mapGraphs.map)); //Broadway
        locations.add(new LocationDrawable((float)40.6397,(float)-73.7789, "JFK Airport", mapGraphs.map)); //JFK airport
        locations.add(new LocationDrawable((float)40.7833,(float)-73.9667, "Central Park", mapGraphs.map)); //Central park
        locations.add(new LocationDrawable((float)40.7494,(float)-73.9681,"UN HQ", mapGraphs.map)); //UN HQ
        test = new LocationDrawable((float)40.7455, (float)73.7777, "test", mapGraphs.map);
    }
    
    
    //draws each location as a box (for now) with text above with the name
    //and number of visitors for the current querey
    public void draw()
    {
        renderArea.pushStyle();
        renderArea.pushMatrix();
        for(LocationDrawable l : locations)
        {
            renderArea.pushMatrix();
            l.draw(renderArea);
            renderArea.rotateZ(-mapGraphs.cameraX);
            renderArea.rotateX(-mapGraphs.cameraY);
            renderArea.pushMatrix();
            renderArea.noStroke();
            renderArea.fill(0);
            renderArea.textFont(renderArea.createFont("Calibri", 15, false));
            renderArea.textSize(15);
            renderArea.text(l.name + " visitors " + l.visitors, -2, -2);
            renderArea.popMatrix();
            renderArea.stroke(0);
            renderArea.popMatrix();
        }    
        test.draw(renderArea);
        frames++;
        renderArea.popMatrix();
        renderArea.popStyle();
    } 
    
    //sets number of visitors for each location to zero
    public void reset()
    {
        for(LocationDrawable l : locations)
            l.visitors = 0;
    }
    
    //sets the number of visitors for each location for the trips returned
    //from a given query
    public void setData(ArrayList<Trip> trips)
    {
        for(LocationDrawable l : locations)
            l.setData(trips);
    }
    
    //gets particular queries
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_1)
        {
            reset();
            setData(renderArea.query.getTripsForMonth(1, 1000));
        } else if (e.getKeyCode() == KeyEvent.VK_2)
        {
            reset();
            setData(renderArea.query.getTripsForMonth(2, 1000)); 
        } else if (e.getKeyCode() == KeyEvent.VK_3)
        {
            reset();
            setData(renderArea.query.GIVEME500LATENIGHTTAXISPLEASE(true));
        }
    }
}    
