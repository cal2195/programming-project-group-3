package programmingproject;

import controlP5.ControlEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import processing.core.PApplet;
import processing.opengl.PGraphics3D;

/**
 *
 * @author cal
 */
public class RenderArea extends PApplet
{

    int currentScreen = 0;
    HeatMapGraph heightMapGraph;
    MapGraphs mapGraphs;
    Data data;
    LinePieChart linePieChart;

    PGraphics3D buffer;

    GUI gui;

    Query query;

    @Override
    public void setup()
    {
        size(width, height, P2D);

        buffer = (PGraphics3D) createGraphics(width, height, P3D);
        buffer.textFont(createFont("Calibri", 30, false));

        gui = new GUI(this);
        query = new Query();

        mapGraphs = new MapGraphs(this, buffer);
        linePieChart = new LinePieChart(this);
    }

    @Override
    public void draw()
    {
        buffer.beginDraw();

        switch (currentScreen)
        {
            case 0:
                mapGraphs.draw(buffer);
                break;

            case 1:
                linePieChart.draw(buffer);
                break;
        }

        buffer.endDraw();
        image(buffer, 0, 0); //Draw 3D offscreen buffer onto 2D onscreen buffer!

        draw2DGUI();
    }

    public void draw2DGUI()
    {
        if (mapGraphs.heatMapGraph.labelX != -1)
        {
            pushStyle();
            pushMatrix();
            translate(mapGraphs.heatMapGraph.labelX, mapGraphs.heatMapGraph.labelY);
            fill(0, 255, 0);
            rect(0, 0, 100, 50);
            
            int desiredID = mapGraphs.heatMapGraph.currentID - 1;
            int row = (desiredID / mapGraphs.heatMapGraph.GRID_WIDTH);
            int column = desiredID - (row * mapGraphs.heatMapGraph.GRID_WIDTH);
            
            fill(0);
            text((int) (mapGraphs.heatMapGraph.gridOfTowers[row][column].height / 10) + " taxis", 5, 20);
            
            popMatrix();
            popStyle();
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
            if (currentScreen >= 2)
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
        println(theEvent);
        
        if (theEvent.isFrom("visList"))
        {
            mapGraphsGUIEvent(theEvent);
        } else if (theEvent.isController() /*&& QueryGUI.LABEL.equals(theEvent.getController().getParent().getName())*/)
        {
            queryGUIEvent(theEvent);
        }
    }

    private void mapGraphsGUIEvent(ControlEvent theEvent)
    {
        switch ((int) theEvent.getValue())
        {
            case 0: //"Heat Map":
                currentScreen = 0;
                mapGraphs.currentGraph = 0;
                mapGraphs.heatMapGraph.setData(query.getTripsForMonth(1, 10000));
                break;
            case 1: //"Taxi Animator":
                currentScreen = 0;
                mapGraphs.currentGraph = 1;
                mapGraphs.tripAnimator.setData(query.getTaxisAtHour(4, 5000));
//                mapGraphs.tripAnimator.setData(query.getTripsForMonth(1, 50000));
                break;
            case 2: //"Area Map Graph":
                currentScreen = 0;
                mapGraphs.currentGraph = 2;
//                    mapGraphs.areaMapGraph.setData(query.getTripsForMonth(1, 50000));
                break;
            case 3: //"Query comparison":
                currentScreen = 0;
                mapGraphs.currentGraph = 3;
                mapGraphs.vendorVisual.setData(query.getTaxisAtHour(9, 5000), query.getTaxisAtHour(3, 5000));
                break;
            case 4: //"Line Pie Chart":
                currentScreen = 1;
            default:
                break;
        }
}
    
    private void queryGUIEvent(ControlEvent theEvent)
    {
        switch (theEvent.getController().getLabel()) {
            case "Jan":
                mapGraphs.heatMapGraph.setData(query.getTripsForMonth(1, 1000));
                break;
            case "Feb":
                mapGraphs.heatMapGraph.setData(query.getTripsForMonth(2, 5000));
                break;
            default:
                break;
        }
    }
    
    @Override
    protected void resizeRenderer(int newWidth, int newHeight) //When the window is resized, adjust all buffers accordingly!
    {
        super.resizeRenderer(newWidth, newHeight);
        if (mapGraphs != null)
        {
            mapGraphs.heatMapGraph.buffer = createGraphics(newWidth, newHeight, RenderArea.P3D);
        }
        if (buffer != null)
        {
            buffer = (PGraphics3D) createGraphics(newWidth, newHeight, RenderArea.P3D);
        }
    }
}
