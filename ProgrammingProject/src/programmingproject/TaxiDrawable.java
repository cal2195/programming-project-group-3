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
    int startTime, tripTime;
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
        
        tripTime = trip.time;

        dx = (endx - x) / trip.time;
        dy = (endy - y) / trip.time;
    }

    public void draw(RenderArea renderArea, int time)
    {
        renderArea.translate(x, y, 4);
        if (dead)
        {
            if (time > startTime && deathFrames < DEATHFRAMES && time < startTime + tripTime)
            {
                renderArea.fill(0, 0, 0);
                renderArea.box(3, 3, 8);
                deathFrames += TripAnimator.speedFactor;
            }
            if(time <= TripAnimator.speedFactor + 1)
            {
                this.resetTaxi();
            }

        } else if (time > startTime && time < startTime + tripTime)
        {
            renderArea.fill(255, 240, 0);
            renderArea.box(3, 3, 8);
        }

    }

    public void moveAndCheck(int time)
    {
        if (time > startTime)
        {
            x += TripAnimator.speedFactor * dx ;
            y += TripAnimator.speedFactor * dy;
            if (endx - x < TripAnimator.speedFactor/4 && endx - x > -1*(TripAnimator.speedFactor/4))
                {
                    dx = 0;
                    dy = 0;
                    dead = true;
                }
        }

    }
    
    public void resetTaxi(){
                dead = false;
                x = startx;
                y = starty;
                dx = (endx - x) / tripTime;
                dy = (endy - y) / tripTime;
    }
}
