package programmingproject;

import javax.swing.JFrame;
import processing.core.PApplet;

/**
 *
 * @author cal
 */
public class ProgrammingProject extends PApplet
{
    RenderArea renderArea;
    
    //n.b. these values are currently all bullshit, to be used to find the relative x and y of a taxi
    double ORIGIN_LONGITUDE = 73.8;
    double ORIGIN_LATITUDE = 40.6;
    double SCALE_FACTOR = 1000;
    
    Data data;

    @Override
    public void setup()
    {
        createUserInterface();
    }
    
    public void createUserInterface()
    {
        JFrame mainWindow = new JFrame("Programming Project");
        
        renderArea = new RenderArea();
        
        mainWindow.setSize(1000, 600);
        mainWindow.setResizable(false);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.add(renderArea);
        
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
        
        renderArea.init();
    }
    
    public static void main(String[] args)
    {
        new ProgrammingProject();
    }
}
