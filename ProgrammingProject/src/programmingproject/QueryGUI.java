package programmingproject;

import controlP5.Button;
import controlP5.CheckBox;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.RadioButton;
import controlP5.Slider;
import processing.core.PApplet;

/**
 *
 * @author cal
 */
public class QueryGUI
{
    final int height;
    final int width;
    
    static final String QUERY_GROUP = "query";
    
    RenderArea renderArea;
    ControlP5 cp5;
    Group queryWindow;
    Button janButton, febButton;
    Slider sampleSlider;
    RadioButton sampleRadio;
    Button subQuery1, subQuery2;
    
    final int SAMPLE_POS = 240;
    
//    CheckBox months;

    public QueryGUI(RenderArea renderArea, ControlP5 cp5)
    {
        this.renderArea = renderArea;
        this.cp5 = cp5;

        height = renderArea.height - 20;
        width = 300;
        
        setup();
    }

    public void setup()
    {
        queryWindow = cp5.addGroup(QUERY_GROUP)
                         .setLabel("Queries")
                         .setBarHeight(20)
                         .setPosition(0, 20)
                         .setWidth(width)
                         .activateEvent(false)
                         .setBackgroundColor(renderArea.color(48, 196))
                         .setBackgroundHeight(height);
        
        janButton = cp5.addButton("query1")
                       .setCaptionLabel("Set Query 1")
                       .setPosition(10, height - 30)
                       .setSize(100,20)
                       .moveTo(queryWindow);
        febButton = cp5.addButton("query2")
                       .setCaptionLabel("Set Query 2")
                       .setPosition(width - 110, height - 30)
                       .setSize(100, 20)
                       .moveTo(queryWindow);
        
        janButton = cp5.addButton("Jan")
                       .setPosition(10,10)
                       .setSize(40,20)
                       .moveTo(queryWindow);
        febButton = cp5.addButton("Feb")
                       .setPosition(60,10)
                       .setSize(40,20)
                       .moveTo(queryWindow);
                       
        sampleSlider = cp5.addSlider("sampleSlider")
                   .setCaptionLabel("Sample Size")
                   .setPosition(10,SAMPLE_POS)
                   .setSize(160, 20)
                   .setRange(0,100)
                   .setNumberOfTickMarks(101)
                   .showTickMarks(false)
                   .setValue(50)
                   .setGroup(QUERY_GROUP);
                
        sampleRadio = cp5.addRadioButton("sampleRadio")
                         .setSize(20,20)
                         .setPosition(10, SAMPLE_POS + 30)
                         .setItemsPerRow(3)
                         .setSpacingColumn(70)
                         .addItem("10",1)
                         .addItem("100",2)
                         .addItem("1k",3)
                         .addItem("10k",4)
                         .addItem("100k",5)
                         .addItem("1M",6)
                         .setGroup(QUERY_GROUP)
                         //.activate(2)
                         ;
        
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
