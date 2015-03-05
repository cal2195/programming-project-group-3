package programmingproject;

import processing.data.Table;

/**
 *
 * @author cal
 */
public class Data
{

    Table taxiData;
    RenderArea renderArea;
    int numberOfRecords;
    //n.b. these values are currently all bullshit, to be used to find the relative x and y of a taxi
    static float TOP_LEFT_LONGITUDE = -74.212073f;
    static float TOP_LEFT_LATITUDE = 40.874139f;

    public Data(String filename, RenderArea renderArea)
    {
        this.renderArea = renderArea;
        taxiData = renderArea.loadTable(filename, "header"); //Load in the .cvs file into a table!
        // 0 = String, 1 = int, 4 = double
        int[] columnTypes =
        {
            0, 0, 0, 1, 0, 0, 0, 1, 1, 4, 4, 4, 4, 4
        };
        taxiData.setColumnTypes(columnTypes);
        numberOfRecords = taxiData.getRowCount();
    }

    public static float latToYPos(float latitude, int height)
    {
        float pixelLat = (latitude - TOP_LEFT_LATITUDE) * -1;
        //calculating using ratio 0.210139:height
        float pixelYPos = ((pixelLat / 0.210139f) * height) - height / 2;
        return pixelYPos;

    }

    public static float longToXPos(float longitude, int width)
    {
        float pixelLong = (longitude - TOP_LEFT_LONGITUDE);
        //calculating using ratio 0.495377:width 
        float pixelXPos = ((pixelLong / 0.495377f) * width) - width / 2;
        return pixelXPos;
    }

}
