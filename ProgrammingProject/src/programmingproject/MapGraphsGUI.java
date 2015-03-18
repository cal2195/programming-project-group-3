package programmingproject;

import controlP5.ControlEvent;
import controlP5.ControlP5;

/**
 *
 * @author cal
 */
public class MapGraphsGUI
{

    RenderArea renderArea;
    ControlP5 cp5;

    public MapGraphsGUI(RenderArea renderArea, ControlP5 cp5)
    {
        this.renderArea = renderArea;
        this.cp5 = cp5;

        setup();
    }

    public void setup()
    {
        // create a new button with name 'buttonA'
        cp5.addButton("Heat Map")
                .setPosition(20, 10)
                .setSize(200, 19);

        // and add another 3 buttons
        cp5.addButton("Taxi Animator")
                .setPosition(240, 10)
                .setSize(200, 19);

        cp5.addButton("Area Map Graph")
                .setPosition(460, 10)
                .setSize(200, 19);

        cp5.addButton("Vendor comparison")
                .setPosition(680, 10)
                .setSize(200, 19);
    }
}
