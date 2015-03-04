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
    
    public Data(String filename, RenderArea renderArea)
    {
        this.renderArea = renderArea;
        taxiData = renderArea.loadTable(filename, "header"); //Load in the .cvs file into a table!
        // 0 = String, 1 = int, 4 = double
        int[] columnTypes = {0, 0, 0, 1, 0, 0, 0, 1, 1, 4, 4, 4, 4, 4};
        taxiData.setColumnTypes(columnTypes);
        numberOfRecords = taxiData.getRowCount();
    }
}
