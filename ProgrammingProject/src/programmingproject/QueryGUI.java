package programmingproject;

import controlP5.*;
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
    public static final String[] DAYS = {"mo", "tu", "we", "th", "fr", "sa", "su"};
    public static final String[] MONTHS = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
    
    RenderArea renderArea;
    ControlP5 cp5;
    Group queryWindow;
    Button janButton, febButton;
    Slider sampleSlider;
    RadioButton sampleRadio, vendorID;
    Button setQueryOne, setQueryTwo;
    CheckBox months, days;
    Range hours, passengers;
    Slider hourFrom, hourTo;
    
    final int SAMPLE_POS = 10;
    

    public QueryGUI(RenderArea renderArea, ControlP5 cp5)
    {
        this.renderArea = renderArea;
        this.cp5 = cp5;

        height = renderArea.height - 20;
        width = 400;
        
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
                .setBackgroundHeight(height)
                .close();

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
                .setBroadcast(false) 
                .setCaptionLabel("Sample Size")
                .setPosition(10, SAMPLE_POS)
                .setSize(300, 15)
                .setRange(0, 100)
                .setDecimalPrecision(0)
                .showTickMarks(false)
                .setValue(50)
                .setBroadcast(true)
                .moveTo(queryWindow);
        sampleRadio = cp5.addRadioButton("sampleRadio")
                .setSize(15, 15)
                .setPosition(10, SAMPLE_POS + 20)
                .setItemsPerRow(6)
                .setSpacingColumn(50)
                .addItem("10", 1)
                .addItem("100", 2)
                .addItem("1k", 3)
                .addItem("10k", 4)
                .addItem("100k", 5)
                .addItem("1M", 6)
                .moveTo(queryWindow)
                .activate(2);
                       
        // Months checkboxs:
        months = cp5.addCheckBox("months")
                .setPosition(10, 80)
                .setSize(20, 20)
                .setItemsPerRow(6)
                .setSpacingColumn(40)
                .setSpacingRow(5)
                .moveTo(queryWindow);
        for (int i = 0; i < MONTHS.length; i++)
        {
            months.addItem(MONTHS[i], i).activate(i);
        }

        // Days checkboxs:
        days = cp5.addCheckBox("days")
                .setPosition(10, 150)
                .setSize(20, 20)
                .setItemsPerRow(7)
                .setSpacingColumn(30)
                .setSpacingRow(5)
                .moveTo(queryWindow);
        for (int i = 0; i < DAYS.length; i++)
        {
            days.addItem(DAYS[i], i).activate(i);
        }

        // Hours range:
//        hourFrom = cp5.addSlider("hourFrom")
//                .setCaptionLabel("Hour From")
//                .setPosition(10, 150)
//                .setSize(130, 15)
//                .setRange(0, 23)
//                .setNumberOfTickMarks(24)
//                .showTickMarks(false)
//                .setDecimalPrecision(0)
//                .moveTo(queryWindow)
//                ;
//        hourTo = cp5.addSlider("hourTo")
//                .setCaptionLabel("Hour To")
//                .setPosition(10, 170)
//                .setSize(130, 15)
//                .setRange(0, 23)
//                .setNumberOfTickMarks(24)
//                .showTickMarks(false)
//                .setDecimalPrecision(0)
//                .moveTo(queryWindow)
//                ;
        
        hours = cp5.addRange("hours")
                .setCaptionLabel("Hours")
                .setPosition(10, 200)
                .setSize(300, 15)
                .setDecimalPrecision(0)
                .setRange(0, 23)
                .moveTo(queryWindow)
                ;

        passengers = cp5.addRange("passengers")
                .setCaptionLabel("Passengers")
                .setPosition(10, 240)
                .setSize(275, 15)
                .setDecimalPrecision(0)
                .setRange(0, 6)
                .moveTo(queryWindow)
                ;

        vendorID = cp5.addRadioButton("vendorID")
                .setSize(15, 15)
                .setPosition(90, 280)
                .setItemsPerRow(6)
                .setSpacingColumn(50)
                .addItem("CMT", 1)
                .addItem("VTS", 2)
                .addItem("Both", 3)
                .setGroup(LABEL)
                .activate(2);
        cp5.addTextlabel("vendorIDLabel")
                .setText("Vendor ID:")
                .setPosition(10, 280)
                .moveTo(queryWindow);
        
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
