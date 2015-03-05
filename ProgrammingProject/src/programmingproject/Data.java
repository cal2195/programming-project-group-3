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
    static double TOP_LEFT_LONGITUDE = -74.212073;
    static double TOP_LEFT_LATITUDE = 40.874139;
    
    public Data(String filename, RenderArea renderArea)
    {
        this.renderArea = renderArea;
        taxiData = renderArea.loadTable(filename, "header"); //Load in the .cvs file into a table!
        // 0 = String, 1 = int, 4 = double
        int[] columnTypes = {0, 0, 0, 1, 0, 0, 0, 1, 1, 4, 4, 4, 4, 4};
        taxiData.setColumnTypes(columnTypes);
        numberOfRecords = taxiData.getRowCount();
    }
    
    public static double latToYPos(double latitude, int height){
        double pixelLat = (latitude - TOP_LEFT_LATITUDE)*-1;
        //calculating using ratio 0.210139:height
        double pixelYPos = ((pixelLat/0.210139)*height)-height/2;
        return pixelYPos;
        
    }
    public static double longToXPos(double longitude, int width){
        double pixelLong = (longitude - TOP_LEFT_LONGITUDE);
        //calculating using ratio 0.495377:width 
        double pixelXPos = ((pixelLong/0.495377)*width)-width/2;
        return pixelXPos;
    }
    
}
