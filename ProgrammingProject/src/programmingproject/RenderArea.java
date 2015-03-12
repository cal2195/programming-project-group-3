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
    VendorVisual vendorVisual;
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
        vendorVisual = new VendorVisual(this);
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
                vendorVisual.draw();
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
                vendorVisual.mousePressed(e);
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
                vendorVisual.mouseDragged(e);
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
                vendorVisual.mouseReleased(e);
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
            case 1:
                vendorVisual.keyPressed(e);
                break;
        }
    }

}
