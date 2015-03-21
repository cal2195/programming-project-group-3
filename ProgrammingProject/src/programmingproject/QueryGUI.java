package programmingproject;

import controlP5.Button;
import controlP5.CheckBox;
import controlP5.ControlP5;
import controlP5.Group;

/**
 *
 * @author cal
 */
public class QueryGUI
{

    static final String QUERY_GROUP = "query";
    
    RenderArea renderArea;
    ControlP5 cp5;
    Group queryWindow;
    Button janButton, febButton;
    CheckBox months;
    
    public QueryGUI(RenderArea renderArea, ControlP5 cp5)
    {
        this.renderArea = renderArea;
        this.cp5 = cp5;

        setup();
    }

    public void setup()
    {
        queryWindow = cp5.addGroup(QUERY_GROUP)
                         .setLabel("Queries")
                         .setBarHeight(20)
                         .setPosition(20, 70)
                         .setWidth(300)
                         .activateEvent(false)
                         .setBackgroundColor(renderArea.color(255, 150))
                         .setBackgroundHeight(renderArea.height - 140);
        
        janButton = cp5.addButton("Jan")
                       .setPosition(10,10)
                       .setSize(40,20)
                       .setGroup(QUERY_GROUP);
        febButton = cp5.addButton("Feb")
                       .setPosition(10,40)
                       .setSize(40,20)
                       .setGroup(QUERY_GROUP);
                       
        
//        months = cp5.addCheckBox("months")
//                .setPosition(10, 10)
//                .setColorForeground(renderArea.color(80))
//                .setColorActive(renderArea.color(180))
//                .setColorLabel(renderArea.color(0))
//                .setSize(10, 10)
//                .setItemsPerRow(1)
//                .setSpacingRow(5)
//                .addItem("0", 0)
//                .addItem("50", 50)
//                .addItem("100", 100)
//                .addItem("150", 150)
//                .addItem("200", 200)
//                .addItem("255", 255)
//                .setGroup(QUERY_GROUP)
//                ;
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
