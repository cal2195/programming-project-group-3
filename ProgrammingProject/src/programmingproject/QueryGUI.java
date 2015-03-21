package programmingproject;

import controlP5.ControlP5;
import controlP5.Group;

/**
 *
 * @author cal
 */
public class QueryGUI
{

    RenderArea renderArea;
    ControlP5 cp5;
    Group queryWindow;
    
    public QueryGUI(RenderArea renderArea, ControlP5 cp5)
    {
        this.renderArea = renderArea;
        this.cp5 = cp5;

        setup();
    }

    public void setup()
    {
        queryWindow = cp5.addGroup("query").setLabel("Queries").setBarHeight(15).setPosition(100, 70).setWidth(renderArea.width - 200).activateEvent(true).setBackgroundColor(renderArea.color(255, 150)).setBackgroundHeight(renderArea.height - 100);
    }
    
    public void hide()
    {
        queryWindow.hide();
    }
    
    public void show()
    {
        queryWindow.show();
    }
}
