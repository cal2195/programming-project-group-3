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

    float x, y, endx, endy, startx, starty;
    float dx, dy;
    boolean dead;
    short deathFrames;
    int startTime;
    private final short DEATHFRAMES = 10;

    TaxiDrawable(Trip trip, UnfoldingMap map)
    {
        float latitude = trip.pickupLat;
        float longitude = trip.pickupLong;
        ScreenPosition screenPosition = map.getScreenPosition(new Location(latitude, longitude));
        x = screenPosition.x;
        startx = x;
        y = screenPosition.y;
        starty = y;
        startTime = trip.pickupTime % DateTime.SECONDS_PER_DAY;
        
        latitude = trip.dropoffLat;
        longitude = trip.dropoffLong;
        screenPosition = map.getScreenPosition(new Location(latitude, longitude));
        endx = screenPosition.x;
        endy = screenPosition.y;

        dx = TripAnimator.SPEEDFACTOR * (endx - x) / trip.time;
        dy = TripAnimator.SPEEDFACTOR * (endy - y) / trip.time;
    }

    public void draw(RenderArea renderArea, int time)
    {
        renderArea.translate(x, y, 4);
        if (dead)
        {
            if (time > startTime && deathFrames < DEATHFRAMES)
            {
                renderArea.fill(0, 0, 0);
                renderArea.box(3, 3, 8);
                deathFrames += TripAnimator.SPEEDFACTOR;
            }
            if(time <= TripAnimator.SPEEDFACTOR + 1)
            {
                dead = false;
                dx = startx;
                dy = starty;
            }

        } else if (time > startTime)
        {
            renderArea.fill(255, 240, 0);
            renderArea.box(3, 3, 8);
        }

    }

    public void moveAndCheck(int time)
    {
        if (time > startTime)
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
}
