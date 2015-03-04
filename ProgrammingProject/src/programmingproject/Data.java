package programmingproject;

import processing.data.Table;

/**
 *
 * @author cal
 */
public class Data
{
    Table taxiData;
    ProgrammingProject programmingProject;
    
    public Data(String filename, ProgrammingProject programmingProject)
    {
        this.programmingProject = programmingProject;
        taxiData = programmingProject.loadTable(filename); //Load in the .cvs file into a table!
    }
}
