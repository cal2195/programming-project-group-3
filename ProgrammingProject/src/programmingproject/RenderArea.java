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

    int currentScreen = 1; //0: HeightMapGraph; 1: Visualisation1
    HeatMapGraph heightMapGraph;
    VendorVisual vis1;
    TripAnimator tripAnimator;
    
    GUI gui;

    //Data data;
    Query query;

    @Override
    public void setup()
    {
        size(width, height, P3D);

        //gui = new GUI(this);
        //data = new Data("res/taxi_data.csv", this);
        query = new Query();

        heightMapGraph = new HeatMapGraph(this);
        vis1 = new VendorVisual(this);
        tripAnimator = new TripAnimator(this);
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
                vis1.draw();
                break;
            case 2:
                tripAnimator.draw();
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
                vis1.mousePressed(e);
                break;
            case 2:
                tripAnimator.mousePressed(e);
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
                vis1.mouseDragged(e);
                break;
            case 2:
                tripAnimator.mouseDragged(e);
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
                vis1.mouseReleased(e);
                break;
            case 2:
                tripAnimator.mouseReleased(e);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode()== KeyEvent.VK_ENTER)
        {
            currentScreen++;
            if(currentScreen > 2)
            {
                currentScreen = 0;
            }
        }
        switch (currentScreen)
        {
            case 0:
                heightMapGraph.keyPressed(e);
                break;
            case 1:
                vis1.keyPressed(e);
                break;
            case 2:
                tripAnimator.keyPressed(e);
                break;
        }
    }

}
