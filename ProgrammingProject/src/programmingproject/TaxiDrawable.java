/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmingproject;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.utils.ScreenPosition;

/**
 *
 * @author Dan
 */
public class TaxiDrawable
{

    float x, y, endx, endy;
    float dx, dy;
    boolean dead;
    short deathFrames;
    private final short DEATHFRAMES = 10;
    private final short SPEEDFACTOR = 2;

    TaxiDrawable(Trip trip, UnfoldingMap map)
    {
        float latitude = trip.pickupLat;
        float longitude = trip.pickupLong;
        ScreenPosition screenPosition = map.getScreenPosition(new Location(latitude, longitude));
        x = screenPosition.x;
        y = screenPosition.y;
        
        latitude = trip.dropoffLat;
        longitude = trip.dropoffLong;
        screenPosition = map.getScreenPosition(new Location(latitude, longitude));
        endx = screenPosition.x;
        endy = screenPosition.y;

        dx = SPEEDFACTOR * (endx - x) / trip.time;
        dy = SPEEDFACTOR * (endy - y) / trip.time;
    }

    public void draw(RenderArea renderArea)
    {
        renderArea.translate(x, y, 4);
        if (dead)
        {
            if (deathFrames < DEATHFRAMES)
            {
                renderArea.fill(0, 0, 0);
                renderArea.box(3, 3, 8);
                deathFrames++;
            }

        } else
        {
            renderArea.stroke(0);
            renderArea.fill(255, 240, 0);
            renderArea.box(3, 3, 8);
            renderArea.noStroke();
        }

    }

    public void moveAndCheck()
    {
        x += dx;
        y += dy;
        if (endx - x < 5 && endx - x > -5)
        {
            dx = 0;
            dy = 0;
            dead = true;
        }

    }
}
