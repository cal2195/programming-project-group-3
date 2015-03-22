/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmingproject;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import processing.opengl.PGraphics3D;

/**
 *
 * @author Shane
 */
public class StatsVisual
{
    RenderArea renderArea;
    int[] passengerTotals;
    int[] vendorTotals;
    float longestTrip;
    float shortestTrip;
    float averageDuration;
    float averageDistance;
    int averagePassengers;
    float totPassengers;
    float totVendors;
    ArrayList<Trip> data;
    
    public StatsVisual(RenderArea area)
    {
        renderArea = area;
        passengerTotals = new int[7];
        vendorTotals = new int[2];
        longestTrip = 0;
        shortestTrip = 100000;
        averageDuration = 0;
        averageDistance = 0;
        averagePassengers = 0;
        totVendors = 0;
        totPassengers = 0;
    }
    
    public void draw(PGraphics3D buffer){
        buffer.background(48);
        buffer.text("Average passengers: " + averagePassengers,550,350);
        buffer.text("Average distance: " + averageDistance,550,380);
        buffer.text("Average Duration: " + averageDuration,550,410);
        buffer.text("Longest Trip: " + longestTrip,50,350);
        buffer.text("Shortest Trip: " + shortestTrip,50,380);
        
    }
    
    public void setData(ArrayList<Trip> data){
        this.data = data;
        float totalDistance = 0;
        float totalTime = 0;
        float totalPassengers = 0;
        for(Trip t : this.data){
            if(t.distance > longestTrip){
                longestTrip=t.distance;
            }
            if(t.distance < shortestTrip){
                shortestTrip = t.distance;
            }
            passengerTotals[t.passengers-1]++;
            totalDistance += t.distance;
            totalTime += t.time;
            totalPassengers += t.passengers;
            if(t.vendorID){
                vendorTotals[0]++;
            } else{
                vendorTotals[1]++;
            }
            totVendors++;
        }
        totPassengers = totalPassengers;
        averageDistance = totalDistance / this.data.size();
        averageDuration = totalTime / this.data.size();
        averagePassengers = (int) totalPassengers / this.data.size();
    }
    
    public void reset(){
        for(int i = 0; i < passengerTotals.length; i++){
            passengerTotals[i] = 0;
        }
        vendorTotals[0] = 0;
        vendorTotals[1] = 0;
        longestTrip = 0;
        shortestTrip = 100000;
        averageDuration = 0;
        averageDistance = 0;
        averagePassengers = 0;
        totVendors = 0;
        totPassengers = 0;
    }
    
     public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_1)
        {
            setData(renderArea.query.getTripsForMonth(1, 1000));
        } else if (e.getKeyCode() == KeyEvent.VK_2)
        {
            setData(renderArea.query.getTripsForMonth(2, 1000));
        } else if (e.getKeyCode() == KeyEvent.VK_3)
        {
            setData(renderArea.query.GIVEME500LATENIGHTTAXISPLEASE(true));
        }
    }    
}
