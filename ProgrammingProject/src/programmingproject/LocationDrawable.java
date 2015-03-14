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
    String name;
    
    
    public LocationDrawable(float lat, float lon, String name, UnfoldingMap map)
    {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        ScreenPosition screenPosition = map.getScreenPosition(new Location(lat, lon));
        x = screenPosition.x;
        y = screenPosition.y;
    }
    
    public void draw(RenderArea renderArea)
    {
        renderArea.translate(x, y, 4);
        renderArea.stroke(0);
        renderArea.fill(255, 240, 0);
        renderArea.box(3, 3, 8);
        renderArea.noStroke();
        //drawPyramid(x,y,10,area);
    }
    


}
