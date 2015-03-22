package programmingproject;

import controlP5.ControlP5;
import controlP5.DropdownList;

/**
 *
 * @author cal
 */
public class MapGraphsGUI
{

    RenderArea renderArea;
    ControlP5 cp5;
    
    DropdownList visList;
    public static final String VIS_LIST_LABEL = "visList";
    public static final String[] VISUAL_LABELS = new String[] {
        "Heat Map",
        "Taxi Animator",
        "Area Map Graph",
        "Query comparison",
        "Line Pie Chart",
        "Stats Visual",
    };

    public MapGraphsGUI(RenderArea renderArea, ControlP5 cp5)
    {
        this.renderArea = renderArea;
        this.cp5 = cp5;

        setup();
    }

    public void setup()
    {
        int width = 200;
        visList = cp5.addDropdownList(VIS_LIST_LABEL)
                .setCaptionLabel("Visualisations")
                .setSize(width, 400)
                .setBarHeight(20)
                .setItemHeight(20)
                .setPosition(renderArea.width - 200, 20)
                .addItems(VISUAL_LABELS);
        
/*  Visualisation selection using buttons:
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
*/
    }
}
