/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmingproject;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import static processing.core.PConstants.TRIANGLE_FAN;

/**
 *
 * @author Shane
 */
public class LocationDrawable {
    float lat;
    float lon;
    float x;
    float y;
    int relX;
    int relY;
    String name;
    
    
    public LocationDrawable(float lat, float lon, String name, UnfoldingMap map)
    {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        ScreenPosition pos = map.getScreenPosition(new Location(lat, lon));
        relX = (int) (pos.x);
        relY = (int) (pos.y);
    }
    
    public void draw(RenderArea renderArea)
    {
        renderArea.translate(relX, relY, 4);
        renderArea.stroke(0);
        renderArea.fill(255, 240, 0);
        renderArea.box(3, 3, 8);
        renderArea.noStroke();
        //drawPyramid(x,y,10,area);
    }
    
    //code pulled from http://www.openprocessing.org/sketch/54574
    public void drawPyramid(float x, float y, float z, RenderArea renderArea) {
      /*  renderArea.beginShape(TRIANGLE_FAN);
        renderArea.fill(150, 0, 150, 127);
        renderArea.vertex(x, y, z);
        renderArea.vertex(x+10, y+20, z+10);
        renderArea.vertex(x+10, y-20, z+10);
        renderArea.vertex(x-10, y+20, z+10);
        renderArea.vertex(x-10, y-20, z+10);
        renderArea.endShape();
      */
        
    }  

}
