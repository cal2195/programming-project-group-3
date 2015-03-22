package programmingproject;

import controlP5.ControlP5;

/**
 *
 * @author cal
 */
public class GUI
{
    RenderArea renderArea;
    ControlP5 cp5;
    
    MapGraphsGUI mapGraphsGUI;
    QueryGUI queryGUI;

    public GUI(RenderArea renderArea)
    {
        this.renderArea = renderArea;
        cp5 = new ControlP5(renderArea);
        
        cp5.setFont(renderArea.createFont("Arial", 12));
        
        mapGraphsGUI = new MapGraphsGUI(renderArea, cp5);
        queryGUI = new QueryGUI(renderArea, cp5);
    }
    
    public void showGUI(int screen)
    {
        
    }
}
