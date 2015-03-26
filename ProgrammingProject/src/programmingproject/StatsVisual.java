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
public class StatsVisual extends AbstractVisualisation
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

    public void draw(PGraphics3D buffer)
    {
        buffer.background(254);
        buffer.text("Average passengers: " + averagePassengers, 550, 350);
        buffer.text("Average distance: " + averageDistance, 550, 380);
        buffer.text("Average Duration: " + averageDuration, 550, 410);
        buffer.text("Longest Trip: " + longestTrip, 50, 350);
        buffer.text("Shortest Trip: " + shortestTrip, 50, 380);
        buffer.text("Passenger Distribution", 115,45);
        buffer.text("Vendor Distrubution",615,45);
        pieChart(200, passengerAngles, 350, 350, buffer, false);
        pieChart(200, vendorAngles, 1350, 350, buffer, true);
    }

    void pieChart(float diameter, int[] data, int x, int y, PGraphics3D buffer, boolean vendors)
    {
        float lastAngle = 0;
        float gray = 0;
        for (int i = 0; i < data.length; i++)
        {
            buffer.fill(gray);
            buffer.arc(x / 2, y / 2, diameter, diameter, lastAngle, lastAngle + RenderArea.radians((float) data[i]));

            if(data[i] != 0)
            {
                buffer.fill(0);
                float textAngle = lastAngle + (RenderArea.radians((float) data[i]) / 2);
                double textX =  x / 2 + (20 + diameter / 2) * Math.cos(textAngle);
                double textY = y /2 + (20 + diameter / 2) * Math.sin(textAngle);
                buffer.text("" + (i+1), (int) textX, (int) textY);
            } 
            lastAngle += RenderArea.radians((float) data[i]);
            gray += 40;
            if(vendors)
            {
               gray *= 3;    
            }
        }
    }

    public void setData(ArrayList<Trip> data)
    {
        reset();
        this.data = data;
        float totalDistance = 0;
        float totalTime = 0;
        float totalPassengers = 0;
        for (Trip t : this.data)
        {
            if (t.distance > longestTrip)
            {
                longestTrip = t.distance;
            }
            if (t.distance < shortestTrip)
            {
                shortestTrip = t.distance;
            }
            passengerTotals[t.passengers - 1]++;
            totalDistance += t.distance;
            totalTime += t.time;
            totalPassengers += t.passengers;
            if (t.vendorID)
            {
                vendorTotals[0]++;
            } else
            {
                vendorTotals[1]++;
            }
        }
        totPassengers = totalPassengers;
        averageDistance = totalDistance / this.data.size();
        averageDuration = totalTime / this.data.size();
        averagePassengers = (int) totalPassengers / this.data.size();
        setAngles();
    }

    private void setAngles()
    {
        for (int i = 0; i < passengerTotals.length; i++)
        {
            passengerAngles[i] = (int) ((int) passengerTotals[i] * 2 * Math.PI / 7);
        }
        for (int i = 0; i < vendorTotals.length; i++)
        {
            vendorAngles[i] = (int) ((int) vendorTotals[i] * 2 * Math.PI / 2);
        }
    }

    public void reset()
    {
        for (int i = 0; i < passengerTotals.length; i++)
        {
            passengerTotals[i] = 0;
            passengerAngles[i] = 0;
        }
        vendorTotals[0] = 0;
        vendorTotals[1] = 0;
        vendorAngles[0] = 0;
        vendorAngles[1] = 0;
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
            System.out.println("query 1");
        } else if (e.getKeyCode() == KeyEvent.VK_2)
        {
            setData(renderArea.query.getTripsForMonth(2, 1000));
            System.out.println("query 2");
        } else if (e.getKeyCode() == KeyEvent.VK_3)
        {
            setData(renderArea.query.GIVEME500LATENIGHTTAXISPLEASE(true));
            System.out.println("query 3");
        }
    }
    
    @Override
    public void reloadData()
    {
        setData(renderArea.currentQuery.active());
    }
}
