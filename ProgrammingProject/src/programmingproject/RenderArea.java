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
    HeightMapGraph heightMapGraph;
    
    Data data;

    @Override
    public void setup()
    {
        size(width, height, P3D);
        
        data = new Data("res/trip_data_small.csv", this);
        //data.printTaxiInfo();
        heightMapGraph = new HeightMapGraph(this);
    }

    @Override
    public void draw()
    {
        switch (currentScreen)
        {
            case 0:
                heightMapGraph.draw();
                break;
        }
    }
    
    public void mouseDragged(MouseEvent e)
    {
        switch (currentScreen)
        {
            case 0:
                heightMapGraph.mouseDragged(e);
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
        }
    }
}
