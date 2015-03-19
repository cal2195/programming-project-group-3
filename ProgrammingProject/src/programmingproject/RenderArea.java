package programmingproject;

import controlP5.ControlEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 *
 * @author cal
 */
public class RenderArea extends PApplet
{

    int currentScreen = 1;
    HeatMapGraph heightMapGraph;
    VendorVisual vis1;
    TripAnimator tripAnimator;
    MapGraphs mapGraphs;
    Data data;
    LinePieChart linePieChart;

    GUI gui;

    Query query;

    @Override
    public void setup()
    {
        size(width, height, P3D);
        
        gui = new GUI(this);
        query = new Query();

        mapGraphs = new MapGraphs(this);
        //heightMapGraph = new HeatMapGraph(this, mapGraphs);
        //vis1 = new VendorVisual(this);
       // tripAnimator = new TripAnimator(this, mapGraphs);
        linePieChart = new LinePieChart(this);
    }

    @Override
    public void draw()
    {
        switch (currentScreen)
        {
            case 0:
                mapGraphs.draw();

                break;
                
            case 1:
                linePieChart.draw();
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        super.mousePressed(e);
        if (!gui.cp5.isMouseOver())
        {
            switch (currentScreen)
            {
                case 0:
                    mapGraphs.mousePressed(e);
                    break;
                
                case 1:
                    linePieChart.mousePressed(e);
                    break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        super.mouseDragged(e);
        if (!gui.cp5.isMouseOver())
        {
            switch (currentScreen)
            {
                case 0:
                    mapGraphs.mouseDragged(e);
                    break;
                case 1:
                    linePieChart.mouseDragged(e);
                    break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        super.mouseReleased(e);
        if (!gui.cp5.isMouseOver())
        {
            switch (currentScreen)
            {
                case 0:
                    mapGraphs.mouseReleased(e);
                    break;
                case 1:
                    linePieChart.mouseReleased(e);
                    break;
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        super.mouseWheelMoved(e);
        if (!gui.cp5.isMouseOver())
        {
            switch (currentScreen)
            {
                case 0:
                    mapGraphs.mouseWheelMoved(e);
                    break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            currentScreen++;
            if (currentScreen > 2)
            {
                currentScreen = 0;
            }
        }
        switch (currentScreen)
        {
            case 0:
                mapGraphs.keyPressed(e);

                break;
        }
    }

    public void controlEvent(ControlEvent theEvent)
    {
        switch (theEvent.getController().getLabel())
        {
            case "Heat Map":
                mapGraphs.currentGraph = 0;
                mapGraphs.heatMapGraph.setData(query.getTaxisAtHour(9, 5000));
                break;
            case "Taxi Animator":
                mapGraphs.currentGraph = 1;
                mapGraphs.tripAnimator.setData(query.getTripsForMonth(1, 250000));
                break;
            case "Area Map Graph":
                mapGraphs.currentGraph = 2;
                mapGraphs.areaMapGraph.setData(query.getTripsForMonth(1, 250000));
                break;
        }
    }

    @Override
    protected void resizeRenderer(int newWidth, int newHeight)
    {
        super.resizeRenderer(newWidth, newHeight);
        if (mapGraphs != null)
        {
            mapGraphs.heatMapGraph.buffer = createGraphics(newWidth, newHeight, RenderArea.P3D);
        }
    }
}
