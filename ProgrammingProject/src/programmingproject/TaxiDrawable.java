/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmingproject;

/**
 *
 * @author Dan
 */

public class TaxiDrawable {  
    float x, y, endx, endy;
    float dx, dy;
    boolean dead;
    
    
TaxiDrawable(float x0, float y0){
            x = Data.longToXPos(x, 1000);
            y = Data.latToYPos(y, 600);
}

TaxiDrawable(Trip trip){
            float latitude = trip.pickupLat;
            float longitude = trip.pickupLong;
            x = Data.longToXPos(longitude, 1000);
            y = Data.latToYPos(latitude, 600);
            latitude = trip.dropoffLat;
            longitude = trip.dropoffLong;
            endx = Data.longToXPos(longitude, 1000);
            endy = Data.latToYPos(latitude, 600);
            
            int timeScale = 10; //larger is faster!
            dx = timeScale*(endx - x) / trip.time;
            dy = timeScale*(endy - y) / trip.time;
}

public void draw(RenderArea renderArea){
        renderArea.translate(x, y, 3);
        if(dead){
            renderArea.fill(0, 0, 0);
        }
        else{
            renderArea.fill(255, 240, 0);
        }
        renderArea.box(10, 5, 4);
}

public void moveAndCheck(){
        x += dx;
        y += dy;
        if(endx - x < 5 && endx - x > -5 ){
            dx = 0;
            dy = 0;
            dead = true;
        }
        
}
}

