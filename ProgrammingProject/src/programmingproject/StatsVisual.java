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
    int[] passengerAngles;
    int[] vendorTotals;
    int[] vendorAngles;
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
        passengerAngles = new int[7];
        vendorTotals = new int[2];
        vendorAngles = new int[2];
        longestTrip = 0;
        shortestTrip = 100000;
        averageDuration = 0;
        averageDistance = 0;
        averagePassengers = 0;
        totVendors = 0;
        totPassengers = 2;
    }
    
    public void draw(PGraphics3D buffer){
        buffer.background(255);
        buffer.text("Average passengers: " + averagePassengers,550,350);
        buffer.text("Average distance: " + averageDistance,550,380);
        buffer.text("Average Duration: " + averageDuration,550,410);
        buffer.text("Longest Trip: " + longestTrip,50,350);
        buffer.text("Shortest Trip: " + shortestTrip,50,380);
        pieChart(100, passengerAngles,250,150,buffer);
        pieChart(100, vendorAngles,550,150,buffer);
    }
    
    void pieChart(float diameter, int[] data, int x, int y, PGraphics3D buffer) {
        float lastAngle = 0;
        float gray = 0;
        for (int i = 0; i < data.length; i++) {
            buffer.fill(gray);
            buffer.arc(x/2, y/2, diameter, diameter, lastAngle, lastAngle+renderArea.radians((float)data[i]));
            lastAngle += renderArea.radians((float)data[i]);
            gray += 40;
        }
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
        }
        totPassengers = totalPassengers;
        averageDistance = totalDistance / this.data.size();
        averageDuration = totalTime / this.data.size();
        averagePassengers = (int) totalPassengers / this.data.size();
        setAngles();
    }
    
    private void setAngles(){
        for(int i = 0; i < passengerTotals.length; i++){
            passengerAngles[i] = (int) ((int) passengerTotals[i] * 360 / totPassengers);
        }
        for(int i = 0; i < vendorTotals.length; i++){
            vendorAngles[i] = (int) ((int) vendorTotals[i] * 360 / totVendors);
        }
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
