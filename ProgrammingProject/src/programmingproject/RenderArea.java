package programmingproject;

import controlP5.ControlEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import processing.core.PApplet;

/**
 *
 * @author cal
 */
public class RenderArea extends PApplet
{

    int currentScreen = 0;
    HeatMapGraph heightMapGraph;
    VendorVisual vis1;
    TripAnimator tripAnimator;
    MapGraphs mapGraphs;
    

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
    }

    @Override
    public void draw()
    {
        switch (currentScreen)
        {
            case 0:
                mapGraphs.draw();

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
        if (theEvent.getController().getLabel().equals("Heat Map"))
        {
            mapGraphs.currentGraph = 0;
            mapGraphs.heatMapGraph.setData(query.getTaxisAtHour(9, 50000));
        } else if (theEvent.getController().getLabel().equals("Taxi Animator"))
        {
            mapGraphs.currentGraph = 1;
            mapGraphs.tripAnimator.setData(query.getTripsForMonth(1, 500000));
        } else if (theEvent.getController().getLabel().equals("Area Map Graph"))
        {
            mapGraphs.currentGraph = 2;
            mapGraphs.areaMapGraph.setData(query.getTripsForMonth(1, 500000));
        }
    }

    @Override
    protected void resizeRenderer(int newWidth, int newHeight)
    {
        super.resizeRenderer(newWidth, newHeight); //To change body of generated methods, choose Tools | Templates.
        if (mapGraphs != null)
        {
            mapGraphs.heatMapGraph.buffer = createGraphics(newWidth, newHeight, RenderArea.P3D);
        }
    }
}
