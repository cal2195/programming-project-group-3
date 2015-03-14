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
    
    public void draw()
    {
        renderArea.pushStyle();
        renderArea.pushMatrix();
        for(LocationDrawable l : locations)
        {
            renderArea.pushMatrix();
            l.draw(renderArea);
            renderArea.popMatrix();
        }    
        test.draw(renderArea);
        frames++;
        renderArea.popMatrix();
        renderArea.popStyle();
    } 
    

}    
