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
        "Location Popularity",
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
    }
    
    public void resize()
    {
        visList.setPosition(renderArea.width - 200, 20);
    }
}
