package programmingproject;

import controlP5.ControlP5;
import controlP5.ListBox;

/**
 *
 * @author cal
 */
public class GUI
{
    ControlP5 cp5;
    ListBox elementList;
    RenderArea renderArea;

    public GUI(RenderArea renderArea)
    {
        this.renderArea = renderArea;
        cp5 = new ControlP5(renderArea);

        cp5.setFont(renderArea.createFont("Georgia", 30));

        elementList = cp5.addListBox("Elements").setBarHeight(40).setScrollbarWidth(20).setPosition(10, 50).setSize(renderArea.displayWidth - 20, renderArea.displayHeight - 20).setItemHeight(35);
    }
}
