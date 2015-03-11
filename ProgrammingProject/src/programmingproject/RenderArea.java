package programmingproject;

import java.awt.event.MouseEvent;
import processing.core.PApplet;

/**
 *
 * @author cal
 */
public class RenderArea extends PApplet
{
    int currentScreen = 0; //0: HeightMapGraph
    HeatMapGraph heightMapGraph;
    GUI gui;
    LinePieChart linePieChart;
    
    Data data;

    @Override
    public void setup()
    {
        size(width, height, P3D);
        
        //gui = new GUI(this);
        data = new Data("res/trip_data_small.csv", this);

        heightMapGraph = new HeatMapGraph(this);
        linePieChart = new LinePieChart(this);
    }

    @Override
    public void draw()
    {
        switch (currentScreen)
        {
            case 0:
                heightMapGraph.draw();
                break;
            case 1:
                linePieChart.draw();
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        switch (currentScreen)
        {
            case 0:
                heightMapGraph.mousePressed(e);
                break;
            case 1:
                linePieChart.mousePressed(e);
                break;
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e)
    {
        switch (currentScreen)
        {
            case 0:
                heightMapGraph.mouseDragged(e);
                break;
            case 1:
                linePieChart.mouseDragged(e);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        switch (currentScreen)
        {
            case 0:
                heightMapGraph.mouseReleased(e);
                break;
            case 1:
                linePieChart.mouseReleased(e);
                break;
        }
    }
}
