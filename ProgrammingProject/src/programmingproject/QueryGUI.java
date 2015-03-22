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
    
    static final String LABEL = "queryGUI";
    public static final String[] MONTHS = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
    
    RenderArea renderArea;
    ControlP5 cp5;
    Group queryWindow;
    Button janButton, febButton;
    Slider sampleSlider;
    RadioButton sampleRadio;
    Button setQueryOne, setQueryTwo;
    CheckBox months;
    
    final int SAMPLE_POS = 10;
    

    public QueryGUI(RenderArea renderArea, ControlP5 cp5)
    {
        this.renderArea = renderArea;
        this.cp5 = cp5;

        height = renderArea.height - 20;
        width = 250;
        
        setup();
    }

    public void setup()
    {
        queryWindow = cp5.addGroup(LABEL)
                .setLabel("Queries")
                .setBarHeight(20)
                .setPosition(0, 20)
                .setWidth(width)
                .activateEvent(false)
                .setBackgroundColor(renderArea.color(48, 196))
                .setBackgroundHeight(height);

        // Set query 1 and 2 Buttons:
        setQueryOne = cp5.addButton("queryOne")
                .setCaptionLabel("Set Query 1")
                .setPosition(10, height - 30)
                .setSize(100, 20)
                .moveTo(queryWindow);
        setQueryTwo = cp5.addButton("queryTwo")
                .setCaptionLabel("Set Query 2")
                .setPosition(width - 110, height - 30)
                .setSize(100, 20)
                .moveTo(queryWindow);

        // Sample size selector:
        sampleSlider = cp5.addSlider("sampleSlider")
                .setCaptionLabel("Sample Size")
                .setPosition(10, SAMPLE_POS)
                .setSize(130, 20)
                .setRange(0, 100)
                .setNumberOfTickMarks(101)
                .showTickMarks(false)
                .setValue(50)
                .setGroup(LABEL);
        sampleRadio = cp5.addRadioButton("sampleRadio")
                .setSize(15, 15)
                .setPosition(10, SAMPLE_POS + 25)
                .setItemsPerRow(3)
                .setSpacingColumn(60)
                .addItem("10", 1)
                .addItem("100", 2)
                .addItem("1k", 3)
                .addItem("10k", 4)
                .addItem("100k", 5)
                .addItem("1M", 6)
                .setGroup(LABEL)
                .activate(2);
                       
        // Months checkboxs:
        months = cp5.addCheckBox("months")
                .setPosition(10, 100)
                .setSize(20, 20)
                .setItemsPerRow(4)
                .setSpacingColumn(35)
                .setSpacingRow(5)
                .moveTo(queryWindow);
        for (int i = 0; i < MONTHS.length; i++)
        {
            months.addItem(MONTHS[i], i).activate(i);
        }


        
        // Jan Feb buttons TEMP
        janButton = cp5.addButton("Jan")
                .setPosition(10, 400)
                .setSize(40, 20)
                .moveTo(queryWindow);
        febButton = cp5.addButton("Feb")
                .setPosition(60, 400)
                .setSize(40, 20)
                .moveTo(queryWindow);
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
