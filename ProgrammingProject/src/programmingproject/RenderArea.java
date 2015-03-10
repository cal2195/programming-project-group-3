package programmingproject;

import java.awt.event.KeyEvent;
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

    Data data;
    Query query;

    @Override
    public void setup()
    {
        size(width, height, P3D);

        //gui = new GUI(this);
        data = new Data("res/taxi_data.csv", this);
        query = new Query(data);

        heightMapGraph = new HeatMapGraph(this);
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

    @Override
    public void mousePressed(MouseEvent e)
    {
        switch (currentScreen)
        {
            case 0:
                heightMapGraph.mousePressed(e);
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

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (currentScreen)
        {
            case 0:
                heightMapGraph.keyPressed(e);
                break;
        }
    }

}
